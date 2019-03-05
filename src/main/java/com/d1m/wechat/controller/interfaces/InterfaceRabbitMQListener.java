package com.d1m.wechat.controller.interfaces;

import cn.d1m.wechat.client.model.WxUser;
import com.alibaba.fastjson.JSONObject;
import com.d1m.common.ds.TenantContext;
import com.d1m.wechat.domain.web.BaseResponse;
import com.d1m.wechat.dto.InterfaceConfigDto;
import com.d1m.wechat.service.EventForwardService;
import com.d1m.wechat.service.InterfaceConfigService;
import com.d1m.wechat.service.InterfaceRabbit;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.Security;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.github.rholder.retry.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @program: wechat-core
 * @Date: 2019/2/20 13:36
 * @Author: Liu weilin
 * @Description: 监听/接收事件消息
 */
@Component("interfaceRabbit")
@Slf4j
public class InterfaceRabbitMQListener implements InterfaceRabbit {


    private static final String SECRET = "secret";

    private RestTemplate restTemplate;

    @SuppressWarnings("UnstableApiUsage")
    private final Retryer<Boolean> retryer = RetryerBuilder
            .<Boolean>newBuilder()
            .retryIfException() //异常重试
            .retryIfRuntimeException() //运行时异常也重试
            .retryIfResult(result -> Objects.equals(result, Boolean.FALSE)) //返回结果部位true重试
            .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.MINUTES)) //重试策略：间隔1分钟
            .withStopStrategy(StopStrategies.stopAfterAttempt(3)) //重试策略: 共重试三次
            .withRetryListener(new RetryListener() {
                @Override
                public <V> void onRetry(Attempt<V> attempt) {
                    log.info("attempt number: {}", attempt.getAttemptNumber());
                }
            })
            .build();

    @Autowired
    private InterfaceConfigService interfaceConfigService;

    @Autowired
    private EventForwardService eventForwardService;

    /**
     * 初始化restTemplate 并配置拦截器，通过拦截器加密body
     */
    public InterfaceRabbitMQListener() {

        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        poolingConnectionManager.setMaxTotal(1000); // 连接池最大连接数
        poolingConnectionManager.setDefaultMaxPerRoute(100); // 每个主机的并发

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //设置HTTP连接管理器
        httpClientBuilder.setConnectionManager(poolingConnectionManager);

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClientBuilder.build());
        clientHttpRequestFactory.setConnectTimeout(5000); // 连接超时，毫秒
        clientHttpRequestFactory.setReadTimeout(5000); // 读写超时，毫秒


        restTemplate = new RestTemplate(clientHttpRequestFactory);
        restTemplate.getInterceptors().add((request, body, execution) -> {
            final String secret = request.getHeaders().getFirst(SECRET);
            request.getHeaders().remove(SECRET);
            if (StringUtils.isEmpty(secret)) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "缺少加密secret");
            }
            if (!ObjectUtils.isEmpty(body)) {
                final String bodyStr = new String(body);
                log.info("send event info to third part: {}", bodyStr);
                final String encrypt = Security.encrypt(bodyStr, secret);
                if (StringUtils.isBlank(encrypt)) {
                    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "加密失败");
                }
                final byte[] encryptBody = encrypt.getBytes(StandardCharsets.UTF_8);
                return execution.execute(request, encryptBody);
            }
            return execution.execute(request, body);
        });
    }

    //@RabbitHandler
    @RabbitListener(queues = Constants.INTERFACE_QUEUE)
    public void process(@Payload Map<String, String> payload, @Headers Map<String, String> headers) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("payload:{} headers:{}", payload, headers);
            }
            final String receivedRoutingKey = headers.get("amqp_receivedRoutingKey");
            String eventCode = receivedRoutingKey.replace("event.", "");
            if ("subscribe".equals(receivedRoutingKey)) {
                eventCode = Optional.ofNullable(payload.get("EventKey"))
                        .filter(key -> key.startsWith("qrscene_"))
                        .map(key -> "subscribe_qrscene")
                        .orElse("subscribe");
            }

            final String wechatId = payload.get("wechatId");
            if (StringUtils.isEmpty(wechatId)) {
                log.error("Not null wechatId");
                return;
            }

            TenantContext.setCurrentTenant(wechatId);

            final List<InterfaceConfigDto> interfaceConfigDtos = interfaceConfigService.findInterfaceConfigDtoByWxEventCode(eventCode);

            if (CollectionUtils.isNotEmpty(interfaceConfigDtos)) {
                interfaceConfigDtos.parallelStream()
                        .forEach(interfaceConfigDto -> {
                            try {
                                if (Boolean.TRUE.equals(interfaceConfigDto.getRetry())) {
                                    this.retryer.call(() -> {
                                        this.sendToThirdPart(interfaceConfigDto, payload);
                                        return Boolean.TRUE;
                                    });
                                } else {
                                    this.sendToThirdPart(interfaceConfigDto, payload);
                                }
                            } catch (ExecutionException | RetryException e) {
                                log.error("事件转发失败", e);
                            }

                        });
            }


        } catch (Exception e) {
            log.error("微信事件消费异常", e);
        }

    }

    /**
     * 上游捕获异常HttpStatusCodeException({@link org.springframework.web.client.HttpStatusCodeException})，用于判断返回的httpStatueCode
     *
     * @param interfaceConfigDto 第三方接口配置
     * @param payload            报文
     * @return nullable response
     */
    @Override
    public BaseResponse sendToThirdPart(InterfaceConfigDto interfaceConfigDto, Map<String, String> payload) {
        log.info("事件转发给第三方：{}", interfaceConfigDto);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(SECRET, interfaceConfigDto.getSecret());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        this.addKeyValue(payload, interfaceConfigDto);
        HttpEntity<Object> requestEntity = new HttpEntity<>(payload, httpHeaders);
        final BaseResponse response = restTemplate.postForObject(interfaceConfigDto.getUrl(), requestEntity, BaseResponse.class);
        log.info("事件转发第三方相应： {}", response);
        return response;
    }


    /**
     * 增加内容，例如unionid
     *
     * @param body 转发给第三方的body
     */
    private void addKeyValue(Map<String, String> body, InterfaceConfigDto interfaceConfigDto) {
        log.info("增加unionid ----strat");
        try {
            TenantContext.setCurrentTenant(body.get("wechatId"));
            List<String> list = eventForwardService.queryEventForwardByInterfaceId(interfaceConfigDto.getId());
            if (null == list || list.size() < 1) {
                log.info("增加unionid------该用户不用附加uuid");
                return;
            }
            if ("true".equals(list.get(0))) {
                String wechatId = body.get("wechatId");
                String toUserName = body.get("FromUserName");
                WxUser wxUser = WechatClientDelegate.getUser(Integer.parseInt(wechatId), toUserName);
                log.info("增加unionid----wxUser--- {}", JSONObject.toJSONString(wxUser));
                body.put("unionId", wxUser.getUnionid());
            } else {
                log.info("增加unionid------该用户不用附加uuid");
            }
        } catch (Exception e) {
            log.error("增加unionId 失败", e);
        }
    }
}
