package com.d1m.wechat.controller.popup;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.model.popup.dao.PopupPayConfig;
import com.d1m.wechat.util.Message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.d1m.wechat.service.IPopupPayService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(value="微店API", tags="微店接口")
public class PopupController extends BaseController {
    private Logger log = LoggerFactory.getLogger(PopupController.class);
    static boolean debug = false;
    @Autowired
    IPopupPayService popupPayServiceImpl;

    public PopupController() {
    }
    
	@ApiOperation(value="更新支付配置", tags="微店接口")
	@ApiResponse(code=200, message="1-操作成功")
    @ResponseBody
    @RequestMapping(
            value = {"pay/config/{id}"},
            method = {RequestMethod.POST}
    )
    @RequiresPermissions("popup-store:pay-config")
    public JSONObject updatePayConfig(
    		@ApiParam("支付配置ID")
    			@PathVariable(name = "id") long id, @RequestBody(required = false) PopupPayConfig model) {
        boolean state = true;
        if (model.getCompanyId() == 0) {
            model.setCompanyId(null);
        }

        if (id == 0L) {
            state = this.popupPayServiceImpl.insertPopupPayConfig(model);
        } else {
            model.setId(id);
            state = this.popupPayServiceImpl.updatePopupPayConfig(model);
        }

        return state ? this.representation(Message.SUCCESS) : this.representation(Message.SYSTEM_ERROR);
    }
	
	@ApiOperation(value="获取支付配置", tags="微店接口")
	@ApiResponse(code=200, message="1-操作成功")
    @ResponseBody
    @RequestMapping(
            value = {"pay/config"},
            method = {RequestMethod.GET}
    )
    @RequiresPermissions("popup-store:pay-config")
    public JSONObject getPayConfig(HttpServletRequest request) {
        int wechatId = debug ? 29 : getWechatId(request.getSession());
        List<PopupPayConfig> results = this.popupPayServiceImpl.queryPopupPayConfig(wechatId);
        List<PopupPayConfig> list = new ArrayList<>();
        if (results == null) {
            return this.representation(Message.SYSTEM_ERROR);
        } else {
            Iterator var5 = results.iterator();

            while (var5.hasNext()) {
                PopupPayConfig result = (PopupPayConfig) var5.next();
                String config = result.getConfig().replaceAll("'", "\"");
                result.setConfig(config);
                list.add(result);
            }

            return this.representation(Message.SUCCESS, list);
        }
    }
}
