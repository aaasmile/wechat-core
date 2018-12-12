package com.d1m.wechat.controller.migrate;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.migrate.MigrateResult;
import com.d1m.wechat.model.User;
import com.d1m.wechat.service.MigrateService;
import com.d1m.wechat.util.Message;
import com.d1m.wechat.wechatclient.ConsulProperties;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * 类描述
 *
 * @author Yuan Zhen on 2016-11-28.
 */
@Controller
@RequestMapping("migrate")
@Api(value = "迁移API", tags = "同步接口")
public class MigrateController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(MigrateController.class);

    @Resource
    private MigrateService migrateService;

    @ApiOperation(value = "迁移同步", tags = "同步接口")
    @ApiResponse(code = 200, message = "1-素材同步完成")
    @RequestMapping(value = "/material.json", method = RequestMethod.GET)
    @ResponseBody
    public Object migrateMaterial(String type, Boolean update) {
        try {
            if (!StringUtils.equalsIgnoreCase("all", type) && !migrateService.isKnownMaterialType(type)) {
                return representation(Message.MATERIAL_IMAGE_TYPE_UNKNOWN);
            }
            update = update != null ? update : false;
            User current = getUser();
            MigrateResult result = migrateService.migrateMaterial(type, update, current.getId(), current.getWechatId());
            return representation(Message.MATERIAL_PULL_COMPLETE, result);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "菜单同步", tags = "同步接口")
    @ApiResponse(code = 200, message = "1-菜单同步完成")
    @RequestMapping(value = "/menu.json", method = RequestMethod.GET)
    @ResponseBody
    public Object migrateMenu(Boolean update) {
        try {
            update = update != null ? update : false;
            User current = getUser();
            MigrateResult result = migrateService.migrateMenu(update, current, current.getWechatId());
            return representation(Message.MENU_PULL_COMPLETE, result);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "拉取微信会员信息", tags = "同步接口")
    @ApiResponse(code = 200, message = "1-拉取微信会员信息成功")
    @RequestMapping(value = "/openid.json")
    @ResponseBody
    public Object pullOpenId(String nextOpenId) {
        try {
            MigrateResult result = migrateService.migrateOpenId(getWechatId(), nextOpenId);
            return representation(Message.MEMBER_PULL_WX_SUCCESS, result);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "会员同步", tags = "同步接口")
    @ApiResponse(code = 200, message = "1-会员同步完成")
    @RequestMapping(value = "/member.json", method = RequestMethod.GET)
    @ResponseBody
    public Object migrateMember() {
        try {
            MigrateResult result = migrateService.migrateUser(getWechatId());
            return representation(Message.MEMBER_PULL_COMPLETE, result);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "配置中心", tags = "同步接口")
    @ApiResponse(code = 200, message = "配置中心参数同步")
    @RequestMapping(value = "/config.json", method = RequestMethod.GET)
    @ResponseBody
    public Object migrateConfig() {
        try {
            ConsulProperties consulProperties = new ConsulProperties();
            consulProperties.onStartup();
            return "successful!";
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

}
