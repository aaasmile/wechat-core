package com.d1m.wechat.controller.popup;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.controller.file.Upload;
import com.d1m.wechat.controller.file.UploadController;
import com.d1m.wechat.model.popup.PopupGoodsEntity;
import com.d1m.wechat.model.popup.PopupGoodsFilter;
import com.d1m.wechat.model.popup.PopupGoodsList;
import com.d1m.wechat.service.IPopupGoodsService;
import com.d1m.wechat.service.IPopupPayService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by Jovi gu on 2017/9/6.
 */
@Api(value="产品API", tags="产品接口")
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

    /**
     * @param file
     * @return
     */
	@ApiOperation(value="上传文件", tags="产品接口")
	@ApiResponse(code=200, message="上传文件成功")
    @RequestMapping(value = "image/add", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("product:list")
    public JSONObject uploadImage(
    		@ApiParam("上传文件")
            	@RequestParam(required = false) MultipartFile file, HttpServletRequest request) {
        try {
            Upload upload = UploadController.upload(getWechatId(request.getSession()), file, Constants.IMAGE,
                    Constants.GOODS);
            return representation(Message.FILE_UPLOAD_SUCCESS, upload.getAccessPath());
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }
	
	@ApiOperation(value="获取产品列表", tags="产品接口")
	@ApiResponse(code=200, message="1-操作成功")
    @ResponseBody
    @RequestMapping(value = "list")
    @RequiresPermissions("product:list")
    public JSONObject listProduct(HttpServletRequest request) {
        Integer wechatId = debug ? 32 : getWechatId(request.getSession());
        try {
            PopupGoodsFilter popupGoodsFilter = new PopupGoodsFilter();
            popupGoodsFilter.setWechatId(wechatId);
            Page<PopupGoodsList> data = popupGoodsService.selectGoodsList(popupGoodsFilter);
            return representation(Message.SUCCESS, data, data.getPageSize(), data.getPageNum(), data.getTotal());
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }
	
	@ApiOperation(value="查询产品", tags="产品接口")
	@ApiResponse(code=200, message="1-操作成功")
    @ResponseBody
    @RequestMapping(value = "search", method = RequestMethod.POST)
    @RequiresPermissions("popup-store:product-list")
    public JSONObject searchProduct(
    		@ApiParam(name="PopupGoodsFilter", required=true)
    			@RequestBody(required = true) PopupGoodsFilter popupGoodsFilter, HttpServletRequest request) {
        Integer wechatId = debug ? 32 : getWechatId(request.getSession());
        if (popupGoodsFilter.getPageNum() < 0 || popupGoodsFilter.getPageSize() < 0)
            return representation(Message.ILLEGAL_REQUEST);
        try {
            popupGoodsFilter.setWechatId(wechatId);
            Page<PopupGoodsList> data = popupGoodsService.selectGoodsList(popupGoodsFilter);
            return representation(Message.SUCCESS, data, data.getPageSize(), data.getPageNum(), data.getTotal());
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }
	
	@ApiOperation(value="新增产品", tags="产品接口")
	@ApiResponse(code=200, message="1-操作成功")
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @RequiresPermissions("popup-store:product-list")
    public JSONObject addProduct(
    		@ApiParam(name="PopupGoodsEntity", required=true)
    			@RequestBody(required = true) PopupGoodsEntity goodsEntity, HttpServletRequest request) {
        try {
            Integer wechatId = debug ? 32 : getWechatId(request.getSession());
            goodsEntity.getGoods().setWechatId(wechatId);
            popupGoodsService.updatePopupGoods(goodsEntity, "add");
            return representation(Message.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }
	
	@ApiOperation(value="编辑产品", tags="产品接口")
	@ApiResponse(code=200, message="1-操作成功")
    @ResponseBody
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @RequiresPermissions("popup-store:product-list")
    public JSONObject editProduct(
    		@ApiParam(name="PopupGoodsEntity", required=true)
    			@RequestBody(required = true) PopupGoodsEntity goodsEntity, HttpServletRequest request) {
        try {
            Integer wechatId = debug ? 32 : getWechatId(request.getSession());
            goodsEntity.getGoods().setWechatId(wechatId);
            popupGoodsService.updatePopupGoods(goodsEntity, "edit");
            return representation(Message.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }
	
	@ApiOperation(value="删除产品", tags="产品接口")
	@ApiResponse(code=200, message="1-操作成功")
    @ResponseBody
    @RequestMapping(value = "delete/{id}")
    @RequiresPermissions("popup-store:product-list")
    public JSONObject deleteProduct(
    		@ApiParam("商品ID")
    			@PathVariable(name = "id") Long id) {
        try {
            popupGoodsService.deletePopupGoods(id);
            return representation(Message.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }
	
	@ApiOperation(value="根据ID获取产品信息", tags="产品接口")
	@ApiResponse(code=200, message="1-操作成功")
    @ResponseBody
    @RequestMapping(value = "get/{id}")
    @RequiresPermissions("popup-store:product-list")
    public JSONObject getProduct(
    		@ApiParam("商品ID")
    			@PathVariable(name = "id") Long id) {
        try {
            PopupGoodsEntity goodsEntity = popupGoodsService.getPopupGoods(id);
            return representation(Message.SUCCESS, JSONObject.toJSONString(goodsEntity));
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }
}
