package com.d1m.wechat.controller.migrate;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.migrate.MigrateResult;
import com.d1m.wechat.model.User;
import com.d1m.wechat.service.MigrateService;
import com.d1m.wechat.util.Message;

/**
 * 类描述
 *
 * @author Yuan Zhen on 2016-11-28.
 */
@Slf4j
@Controller
@RequestMapping("migrate")
public class MigrateController extends BaseController {

    @Resource
    private MigrateService migrateService;

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

}
