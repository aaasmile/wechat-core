package com.d1m.wechat.controller.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.domain.web.BaseResponse;
import com.d1m.wechat.dto.EventForwardDto;
import com.d1m.wechat.dto.InterfaceConfigDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.EventForward;
import com.d1m.wechat.model.InterfaceConfig;
import com.d1m.wechat.model.InterfaceConfigBrand;
import com.d1m.wechat.model.enums.InterfaceStatus;
import com.d1m.wechat.service.EventForwardService;
import com.d1m.wechat.service.InterfaceConfigService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.*;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

@Api(value = "第三方接口API InterfaceConfigController", tags = "第三方接口API InterfaceConfigController")
@RestController
@RequestMapping("interfaceConfig")
public class InterfaceConfigController extends BaseController {

    private Logger log = LoggerFactory.getLogger(InterfaceConfigController.class);

    @Autowired
    private InterfaceConfigService interfaceConfigService;

    @Autowired
    private EventForwardService eventForwardService;

    @ApiOperation(value = "查询第三方接口", tags = "第三方接口列表")
    @ApiResponse(code = 200, message = "获取第三方接口信息成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brand", value = "品牌类别ID"),
            @ApiImplicitParam(name = "name", value = "接口名称"),
            @ApiImplicitParam(name = "method_type", value = "方法类型 POST/GET"),
            @ApiImplicitParam(name = "type", value = "类型"),
            @ApiImplicitParam(name = "created_from", value = "创建时间从-"),
            @ApiImplicitParam(name = "created_to", value = "创建时间至-"),
            @ApiImplicitParam(name = "updated_from", value = "编辑时间从-"),
            @ApiImplicitParam(name = "updated_to", value = "编辑时间至-"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @RequestMapping(value = "selectItems.json", method = RequestMethod.POST)
    public JSONObject selectItems(@RequestBody Map<String, String> query) {
        try {
            log.info(" 第三方接口查询请求参数{}", query.toString());
            PageHelper.startPage(Integer.parseInt(query.get("pageNum")), Integer.parseInt(query.get("pageSize")), true);
            Page<InterfaceConfigDto> interfaceConfigDtos = interfaceConfigService.selectItems(query);
            if (CollectionUtils.isEmpty(interfaceConfigDtos)) {
                return representation(Message.SUCCESS, interfaceConfigDtos, Integer.parseInt(query.get("pageSize")), Integer.parseInt(query.get("pageNum")), interfaceConfigDtos.getTotal());
            }
            final List<InterfaceConfigDto> collect = interfaceConfigDtos.stream().map(icd -> {
                final InterfaceConfigDto interfaceConfigDto = new InterfaceConfigDto();
                BeanUtils.copyProperties(icd, interfaceConfigDto);
                final String updatedAt = icd.getUpdatedAt().substring(0, 19);
                final String createdAt = icd.getCreatedAt().substring(0, 19);
                interfaceConfigDto.setUpdatedAt(updatedAt);
                interfaceConfigDto.setCreatedAt(createdAt);
                return interfaceConfigDto;
            }).collect(Collectors.toList());
            return representation(Message.SUCCESS, collect, Integer.parseInt(query.get("pageSize")), Integer.parseInt(query.get("pageNum")), interfaceConfigDtos.getTotal());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return representation(Message.INTERFACECONFIG_SELECT_FAIL, e.getMessage());
        }
    }

    @ApiOperation(value = "创建第三方接口", tags = "第三方接口列表")
    @ApiResponse(code = 200, message = "创建第三方接口信息成功")
    @RequestMapping(value = "newItem.json", method = RequestMethod.PUT)
    public JSONObject createItems(@RequestBody InterfaceConfig interfaceConfig) {
        try {
            final InterfaceConfig ifcf = new InterfaceConfig();
            ifcf.setName(interfaceConfig.getName());
            ifcf.setBrand(interfaceConfig.getBrand());
            int count = interfaceConfigService.checkRepeat(ifcf);
            if (count > 0) {
                return representation(Message.INTERFACECONFIG_EXIST);
            }
            interfaceConfig.setCreatedBy(String.valueOf(getUser().getId()));
            interfaceConfig.setCreatedAt(DateUtil.formatYYYYMMDDHHMMSS(new Date()));
            interfaceConfig.setStatus(InterfaceStatus.NOT_EFFECT);
            return representation(Message.SUCCESS, interfaceConfigService.create(interfaceConfig));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return representation(Message.INTERFACECONFIG_CREATE_FAIL, e.getMessage());
        }
    }

    @ApiOperation(value = "编辑第三方接口", tags = "第三方接口列表")
    @ApiResponse(code = 200, message = "编辑第三方接口信息成功")
    @RequestMapping(value = "updateItem.json", method = RequestMethod.POST)
    public JSONObject updateItems(@RequestBody InterfaceConfig interfaceConfig) {
        try {
            final InterfaceConfig ifcf = new InterfaceConfig();
            ifcf.setId(interfaceConfig.getId());
            ifcf.setName(interfaceConfig.getName());
            ifcf.setBrand(interfaceConfig.getBrand());
            int count = interfaceConfigService.checkRepeatById(ifcf.getId(), ifcf.getBrand(), ifcf.getName());
            if (count > 0) {
                return representation(Message.INTERFACECONFIG_EXIST);
            }
            interfaceConfig.setUpdatedBy(String.valueOf(getUser().getId()));
            interfaceConfig.setUpdatedAt(DateUtil.formatYYYYMMDDHHMMSS(new Date()));
            return representation(Message.SUCCESS, interfaceConfigService.update(interfaceConfig));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return representation(Message.INTERFACECONFIG_UPDATE_FAIL, e.getMessage());
        }
    }

    @ApiOperation(value = "删除第三方接口", tags = "第三方接口列表")
    @ApiResponse(code = 200, message = "删除第三方接口信息成功")
    @RequestMapping(value = "deleteItem.json", method = RequestMethod.DELETE)
    public JSONObject removeItems(@RequestParam("id") String id) {
        try {
            return representation(Message.SUCCESS, interfaceConfigService.delete(id));
        } catch (WechatException e) {
            log.error(e.getMessage(), e);
            return representation(Message.INTERFACECONFIG_IN_USED, e.getMessageInfo());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return representation(Message.INTERFACECONFIG_DELETE_FAIL, e.getMessage());
        }
    }

    @ApiOperation(value = "获取第三方接口详情", tags = "第三方接口列表")
    @ApiResponse(code = 200, message = "获取第三方接口信息成功")
    @RequestMapping(value = "getItem.json", method = RequestMethod.GET)
    public JSONObject getItems(@RequestParam("id") String id) {
        try {
            return representation(Message.SUCCESS, interfaceConfigService.get(id));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return representation(Message.INTERFACECONFIG_GET_FAIL, e.getMessage());
        }
    }

    @ApiOperation(value = "获取第三方列表", tags = "第三方接口列表")
    @ApiResponse(code = 200, message = "获取第三方列表信息成功")
    @RequestMapping(value = "listBrand.json", method = RequestMethod.GET)
    public JSONObject listBrand() {
        try {
            return representation(Message.SUCCESS, interfaceConfigService.listBrand());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return representation(Message.INTERFACECONFIG_BRAND_LIST_FAIL, e.getMessage());
        }
    }

    @ApiOperation(value = "创建第三方列表", tags = "第三方接口列表")
    @ApiResponse(code = 200, message = "创建第三方列表成功")
    @RequestMapping(value = "createBrand.json", method = RequestMethod.PUT)
    public JSONObject createBrand(@RequestBody InterfaceConfigBrand interfaceConfigBrand) {
        try {
            return representation(Message.SUCCESS, interfaceConfigService.createBrand(interfaceConfigBrand));
        } catch (WechatException e) {
            return representation(e.getMessageInfo(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return representation(Message.INTERFACECONFIG_BRAND_CREATE_FAIL, e.getMessage());
        }
    }

    @ApiOperation(value = "更新第三方列表", tags = "第三方接口列表")
    @ApiResponse(code = 200, message = "更新第三方列表成功")
    @RequestMapping(value = "updateBrand.json", method = RequestMethod.POST)
    public JSONObject updateBrand(@RequestBody InterfaceConfigBrand interfaceConfigBrand) {
        try {
            return representation(Message.SUCCESS, interfaceConfigService.updateBrand(interfaceConfigBrand));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return representation(Message.INTERFACECONFIG_BRAND_UPDATE_FAIL, e.getMessage());
        }
    }

    @ApiOperation(value = "删除第三方列表", tags = "第三方接口列表")
    @ApiResponse(code = 200, message = "删除第三方列表成功")
    @RequestMapping(value = "deleteBrand.json", method = RequestMethod.DELETE)
    public JSONObject deleteBrand(@RequestParam("id") Long brand) {
        try {
            return representation(Message.SUCCESS, interfaceConfigService.deleteBrand(brand));
        } catch (WechatException e) {
            log.error(e.getMessage(), e);
            return representation(Message.INTERFACECONFIG_BRAND_IN_USED, e.getMessageInfo());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return representation(Message.INTERFACECONFIG_BRAND_DELETE_FAIL, e.getMessage());
        }
    }

    @ApiOperation(value = "查看秘钥", tags = "第三方接口列表")
    @ApiResponse(code = 200, message = "查看秘钥成功")
    @RequestMapping(value = "getSecret.json", method = RequestMethod.GET)
    public JSONObject getSecret(@RequestParam("id") String id) {
        try {
            return representation(Message.SUCCESS, interfaceConfigService.getSecret(id));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return representation(Message.INTERFACECONFIG_SECRET_GET_FAIL, e.getMessage());
        }
    }

    @ApiOperation(value = "根据第三方找出未绑定事件转发的接口方法", tags = "第三方接口列表")
    @ApiResponse(code = 200, message = "成功")
    @GetMapping("/getByEventForward/{id}")
    public JSONObject getByEventForward(@PathVariable("id") String id) {
        try {
            return representation(Message.SUCCESS, interfaceConfigService.getByEventForward(id));
        } catch (WechatException e) {
            return wrapException(e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return wrapException(e);
        }
    }

    @ApiOperation(value = "查询第三方接口转发事件", tags = "第三方接口转发列表")
    @ApiResponse(code = 200, message = "获取第三方接口转发事件信息成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "第三方接口id"),
            @ApiImplicitParam(name = "name", value = "接口名称"),
            @ApiImplicitParam(name = "status", value = "接口状态 0 启用 1 停用"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @RequestMapping(value = "selectForwardItems.json", method = RequestMethod.POST)
    public JSONObject selectForwardItems(@RequestBody Map<String, String> query) {
        try {
            log.info(" 第三方接口转发查询请求参数{}", query.toString());
            PageHelper.startPage(Integer.parseInt(query.get("pageNum")), Integer.parseInt(query.get("pageSize")), true);
            Page<EventForwardDto> EventForwardDtos = eventForwardService.selectForwardItems(query);
            if (CollectionUtils.isEmpty(EventForwardDtos)) {
                return representation(Message.SUCCESS, EventForwardDtos, Integer.parseInt(query.get("pageSize")), Integer.parseInt(query.get("pageNum")), EventForwardDtos.getTotal());
            }
            final List<EventForwardResp> collect = EventForwardDtos.stream().map(cs -> {
                final EventForwardResp senderHistoryResp = new EventForwardResp();
                BeanUtils.copyProperties(cs, senderHistoryResp);
                final String s = cs.getUpdateAt().substring(0, 19);
                senderHistoryResp.setUpdateAt(s);
                List<String> event = eventForwardService.selectEventItems(cs.getId());
                senderHistoryResp.setEvents(event);
                return senderHistoryResp;
            }).collect(Collectors.toList());
            return representation(Message.SUCCESS, collect, Integer.parseInt(query.get("pageSize")), Integer.parseInt(query.get("pageNum")), EventForwardDtos.getTotal());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return representation(Message.INTERFACECONFIG_SELECT_FAIL, e.getMessage());
        }
    }


    @Data
    private static class EventForwardResp {

        private Integer id;
        private String name;  //接口名称
        private String brand_name;//第三方
        private int type;
        private String description;
        //转发事件状态
        private int status;
        private int isDeleted;
        //更新时间
        private String updateAt;
        //事件列表
        private List<String> events;


    }

    /**
     * 接口启用和停用
     *
     * @return
     */
    @ApiOperation(value = "接口启用和停用", tags = "第三方接口列表")
    @ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "{id}/enableOrDisable.json", method = RequestMethod.PUT)
    public JSONObject enableOrDisable(@RequestBody EnableInterface statusBody, @PathVariable("id") String id) {
        try {
            notBlank(id, Message.INTERFACECONFIG_ID_NOT_EXIST);
            notBlank(statusBody.getStatus(), Message.INTERFACECONFIG_STATUS_NOT_EXIST);
            InterfaceConfig interfaceConfig = interfaceConfigService.checkIsExist(id);
            notBlank(interfaceConfig, Message.INTERFACECONFIG_EXIST);
            interfaceConfigService.enableOrDisable(statusBody.getStatus(), id);
            return representation(Message.SUCCESS, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    //根据修改转发接口状态
    @ApiOperation(value = "转发接口启用和停用", tags = "第三方接口转发列表")
    @ApiResponse(code = 200, message = "操作成功")
    @PutMapping("eventForwardEnableOrDisable.json/{id}")
    public JSONObject updateCardStatus(@PathVariable Integer id, @RequestBody updateEventForwardStatusReq urfsr) {
        final EventForward eventForward = EventForward.builder()
                .id(id)
                .status(urfsr.status)
                .build();
        eventForwardService.updateStatus(eventForward);
        return representation(Message.SUCCESS, null);

    }

    @Data
    private static class updateEventForwardStatusReq {
        @ApiModelProperty(value = "转发接口状态", required = true, example = "停用/启用")
        private Integer status;
    }

    @Autowired
    private InterfaceRabbitMQListener interfaceRabbitMQListener;

    /**
     * 第三方接口测试
     */
    @ApiOperation(value = "接口测试", tags = "第三方接口")
    @ApiResponse(code = 200, message = "操作成功")
    @GetMapping("testThirdPartyInterface.json/{id}")
    public JSONObject testThirdPartyInterface(@PathVariable String id) {
        try {
            final Map<String, String> payload = new HashMap<>();
            payload.put("type", "testInterface");
            payload.put("ToUserName", "gh_d222d6a8e09b");
            payload.put("FromUserName", "oxOHEs4Ra-S5VUIf81mgOIa9ZAgE");
            payload.put("CreateTime", System.currentTimeMillis() / 1000 + "");
            payload.put("MsgType", "event");
            payload.put("Event", "giftcard_pay_done");
            payload.put("unionId", "sdfjfhsd");
            final InterfaceConfigDto interfaceConfigDto = interfaceConfigService.get(id);
            final BaseResponse baseResponse = interfaceRabbitMQListener.sendToThirdPart(interfaceConfigDto, payload);
            return representation(Message.SUCCESS, baseResponse);
        } catch (ResourceAccessException e) {
            log.error(e.getMessage(), e);
            return representation(Message.TEST_INTERFACE__FALL, "URL无法访问");

        } catch (HttpStatusCodeException e) {
            log.error(e.getMessage(), e);
            return representation(Message.TEST_INTERFACE__FALL, "调用接口返回错误码" + e.getStatusCode());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return representation(Message.TEST_INTERFACE__FALL, e.getMessage());
        }
    }


    /**
     * 创建EnableInterface静态内部类
     */
    static class EnableInterface {
        InterfaceStatus status;

        public InterfaceStatus getStatus() {
            return status;
        }

        public void setStatus(InterfaceStatus status) {
            this.status = status;
        }
    }
}
