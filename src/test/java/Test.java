import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.domain.web.BaseResponse;
import com.d1m.wechat.wx.WxTemplateMsg;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: wechat-core
 * @Date: 2018/11/21 15:40
 * @Author: Liu weilin
 * @Description:
 */
@Slf4j
public class Test {

    private ObjectMapper objectMapper = new ObjectMapper();

    private LocalValidatorFactoryBean validatorFactory = new LocalValidatorFactoryBean();

    private RestTemplate restTemplate = new RestTemplate();


    public static void main(String[] args) {
        String[] split = ",,".split(",");

        System.out.println(split.length);
        final String success = JSONObject.toJSONString(BaseResponse.builder().msg("success").resultCode(1).build());
        System.out.println(success);
    }

    @org.junit.Test
    public void sendTemplate() {
        final List<TemplateExcel> templateExcels = ExcelImportUtil.importExcel(
                new File("/Users/keke/Downloads/LV_Template msg.xlsx"),
                TemplateExcel.class,
                new ImportParams());

        Assert.notEmpty(templateExcels, "模板为空！");

        log.info("共需发送模板个数：{}", templateExcels.size());

        validatorFactory.afterPropertiesSet();

        final Validator validator = validatorFactory.getValidator();


        final List<WxTemplateMsg> templateMsgs = templateExcels.stream()
                .map(t -> {
                    try {
                        return objectMapper.readValue(t.getBody(), WxTemplateMsg.class);
                    } catch (IOException e) {
                        log.error("json 反序列化错误", e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .filter(t -> {
                    t.getData().getFirst().setValue("LV Trainer秀款运动鞋限时店");
                    t.getData().getRemark().setValue("点击查看您购物袋内的LV Trainer秀款运动鞋，或致电400-6588-555路易威登服务专线为您服务。");
                    final Set<ConstraintViolation<WxTemplateMsg>> validate = validator.validate(t);
                    if (CollectionUtils.isEmpty(validate)) {
                        return true;
                    } else {
                        log.error(validate.toString());
                        return false;
                    }
                })
                .collect(Collectors.toList());

        log.info("符合格式能够发送的数量：{}", templateMsgs.size());

        templateMsgs.parallelStream().forEach(t -> {
            try {
                this.process(t);
            } catch (IOException e) {
                log.error("fail: {}", t.toString(), e);
            }
        });

    }

    private void process(WxTemplateMsg msg) throws IOException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setAccept(Collections.singletonList(MediaType.TEXT_HTML));
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        final ResponseEntity<String> result = restTemplate.exchange("https://mk.wechat.d1miao.com/api/wechat/access-token/wx42d5174eefc29f1f/c9c3d08ffc61dcc7bee9baf63c7724aa", HttpMethod.GET, request, String.class);
        final Map map = objectMapper.readValue(result.getBody(), Map.class);
        final String accessToken = (String) map.get("data");
        Assert.hasText(accessToken, "access token can not be null!");
        log.info(accessToken);
        final Map resultMap = restTemplate.postForObject("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken, msg, Map.class);
        log.info(resultMap.toString());
        if (!resultMap.containsKey("errcode") || (int) resultMap.get("errcode") != 0) {
            log.error("send template to {} fail!", msg.getTouser());
        }
    }

    @org.junit.Test
    public void sendTest() throws IOException {

        final List<String> openIds = Lists.newArrayList(
//                "oKK-FjuuELul0LF15jNs_XKbMJSw",
//                "oKK-FjqYzjpuVJIt_qKU4GaR82FA",
//                "oKK-FjoL09bzSglp9kMZWxjV5USw",
                "oKK-Fjtka4w5JeT4HDUOW5gX9Ff4"
        );

        String string = "{\"touser\":\"oKK-FjpPyc_hP1gU9_RktEXhbP-w\",\"template_id\":\"LaTEd_54EcTxCAwQb37vQXdKf3gUTyJzIwBjfnbdoyg\",\"miniprogram\":{\"appid\":\"wx774266d1f698264e\",\"pagepath\":\"pages/cart/cart\"}, \"data\":{\"first\":{\"value\":\"全新LV Trainer秀款运动鞋限时发售\",\"color\":\"#996633\"},\"keyword1\":{\"value\":\"路易威登\",\"color\":\"\"},\"keyword2\":{\"value\":\"12500元\",\"color\":\"\"},\"keyword3\":{\"value\":\"黑\",\"color\":\"\"},\"keyword4\":{\"value\":\"8\",\"color\":\"\"},\"keyword5\":{\"value\":\"已加入购物袋\",\"color\":\"\"},\"remark\":{\"value\":\"点击查看您购物袋内的LV Trainer秀款运动鞋，或致电400-6588-5555路易威登服务专线为您服务。\",\"color\":\"#996633\"}}}";
        final WxTemplateMsg templateExcel = objectMapper.readValue(string, WxTemplateMsg.class);
        openIds.stream().map(openId -> {
            final WxTemplateMsg e = new WxTemplateMsg();
            BeanUtils.copyProperties(templateExcel, e);
            e.setTouser(openId);
            e.getData().getFirst().setValue("LV Trainer秀款运动鞋限时店");
            e.getData().getRemark().setValue("点击查看您购物袋内的LV Trainer秀款运动鞋，或致电400-6588-555路易威登服务专线为您服务。");
            return e;
        }).forEach(m -> {
            try {
                this.process(m);
            } catch (IOException e) {
                log.info("发送错误", e);
            }
        });
    }


    @SuppressWarnings("WeakerAccess")
    @Data
    public static class TemplateExcel {
        @Excel(name = "OpenID")
        private String openId;
        @Excel(name = "Body")
        private String body;
    }


}