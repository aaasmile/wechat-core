package com.d1m.wechat.controller.popup;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.controller.file.Upload;
import com.d1m.wechat.controller.file.UploadController;
import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.model.enums.OrderEnum;
import com.d1m.wechat.model.popup.PopupGoodsEntity;
import com.d1m.wechat.model.popup.PopupGoodsFilter;
import com.d1m.wechat.model.popup.PopupGoodsList;
import com.d1m.wechat.service.IPopupGoodsService;
import com.d1m.wechat.service.IPopupPayService;
import com.d1m.wechat.service.IWechatPopupMApiService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Created by Jovi gu on 2017/9/6.
 */
@RequestMapping("/product")
@Controller
public class ProductController extends BaseController {

    private Logger log = LoggerFactory.getLogger(ProductController.class);

    private static boolean debug = false;

    @Autowired
    MemberService memberService;
    @Autowired
    IPopupPayService popupPayService;
    @Resource
    private IPopupGoodsService popupGoodsService;

    public OrderEnum getEnumByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (OrderEnum order : OrderEnum.values()) {
            if (StringUtils.equals(order.toString(), name)) {
                return order;
            }
        }
        return null;
    }

    /**
     *
     * @param file
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(value = "image/add", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadImage(
            @RequestParam(required = false) MultipartFile file,
            HttpServletRequest request, HttpServletResponse response,
            HttpSession session) {
        try {
            Upload upload = UploadController.upload(getWechatId(), file, Constants.IMAGE,
                    Constants.GOODS);
            return representation(Message.FILE_UPLOAD_SUCCESS, upload.getAccessPath());
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "list",method = RequestMethod.POST)
    public JSONObject listProduct(@CookieValue(name="wechatId",required = false) Integer wechatId, HttpServletRequest request){
        wechatId = debug?32:getWechatId(request.getSession());
        try{
            PopupGoodsFilter popupGoodsFilter = new PopupGoodsFilter();
            popupGoodsFilter.setWechatId(wechatId);
            Page<PopupGoodsList> data = popupGoodsService.selectGoodsList(popupGoodsFilter);
            return representation(Message.SUCCESS,data,data.getPageSize(),data.getPageNum(),data.getTotal());
        } catch (Exception e){
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "search",method = RequestMethod.POST)
    public JSONObject searchProduct(@RequestBody(required = true) PopupGoodsFilter popupGoodsFilter, @CookieValue(name="wechatId",required = false) Integer wechatId, HttpServletRequest request){
        wechatId = debug?32:getWechatId(request.getSession());
        if(popupGoodsFilter.getPageNum() < 0 || popupGoodsFilter.getPageSize() < 0)
            return representation(Message.ILLEGAL_REQUEST);
        try{
            popupGoodsFilter.setWechatId(wechatId);
            Page<PopupGoodsList> data = popupGoodsService.selectGoodsList(popupGoodsFilter);
            return representation(Message.SUCCESS,data,data.getPageSize(),data.getPageNum(),data.getTotal());
        } catch (Exception e){
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public JSONObject addProduct(@RequestBody(required = true) PopupGoodsEntity goodsEntity, @CookieValue(name="wechatId",required = false) Integer wechatId, HttpServletRequest request){
        try{
            popupGoodsService.updatePopupGoods(goodsEntity, "add");
            return representation(Message.SUCCESS,"");
        } catch (Exception e){
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public JSONObject editProduct(@RequestBody(required = true) PopupGoodsEntity goodsEntity, @CookieValue(name="wechatId",required = false) Integer wechatId, HttpServletRequest request){
        try{
            popupGoodsService.updatePopupGoods(goodsEntity, "edit");
            return representation(Message.SUCCESS,"");
        } catch (Exception e){
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "delete/{id}")
    public JSONObject deleteProduct(@PathVariable(name = "id") Long id, @CookieValue(name="wechatId",required = false) Integer wechatId, HttpServletRequest request){
        try{
            popupGoodsService.deletePopupGoods(id);
            return representation(Message.SUCCESS,"");
        } catch (Exception e){
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "get/{id}")
    public JSONObject getProduct(@PathVariable(name = "id") Long id, @CookieValue(name="wechatId",required = false) Integer wechatId, HttpServletRequest request){
        try{
            PopupGoodsEntity goodsEntity = popupGoodsService.getPopupGoods(id);
            return representation(Message.SUCCESS,JSONObject.toJSONString(goodsEntity));
        } catch (Exception e){
            log.error(e.getMessage());
            return wrapException(e);
        }
    }
}
