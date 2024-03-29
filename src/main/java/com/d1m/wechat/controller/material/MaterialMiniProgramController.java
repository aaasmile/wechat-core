package com.d1m.wechat.controller.material;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.MiniProgramDto;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.pamametermodel.MaterialModel;
import com.d1m.wechat.pamametermodel.MiniProgramModel;
import com.d1m.wechat.service.MaterialService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * MaterialMiniProgramController
 *
 * @author f0rb on 2017-11-22.
 */
@RestController
@RequestMapping("/material/miniprogram")
@Api(value = "素材API", tags = {"素材接口", "小程序接口"})
public class MaterialMiniProgramController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(MaterialMiniProgramController.class);
    @Resource
    private MaterialService materialService;

    @ApiOperation(value = "删除小程序", tags = "小程序接口")
    @ApiResponse(code = 200, message = "删除小程序成功")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @RequiresPermissions("app-msg:list")
    public JSONObject delete(@ApiParam("素材图文ID") @PathVariable Integer id) {
        materialService.deleteMiniProgram(getUser().getId(), getWechatId(), id);
        return representation(Message.MATERIAL_MINI_PROGRAM_DELETE_SUCCESS);
    }

    @ApiOperation(value = "创建小程序", tags = "小程序接口")
    @ApiResponse(code = 200, message = "创建小程序成功")
    @RequestMapping(method = RequestMethod.POST)
    @RequiresPermissions("app-msg:list")
    public JSONObject create(@ApiParam("小程序素材") @RequestBody MaterialModel materialModel) {
        Material material = materialService.createMiniProgram(getUser().getId(), getWechatId(), materialModel);
        return representation(Message.MATERIAL_MINI_PROGRAM_CREATE_SUCCESS, material);
    }

    @ApiOperation(value = "更新小程序", tags = "小程序接口")
    @ApiResponse(code = 200, message = "更新小程序成功")
    @RequestMapping(value = "{id}", method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH})
    @RequiresPermissions("app-msg:list")
    public JSONObject update(@ApiParam("小程序素材") @RequestBody MaterialModel materialModel) {
        materialService.updateMiniProgram(getUser().getId(), getWechatId(), materialModel);
        return representation(Message.MATERIAL_MINI_PROGRAM_UPDATE_SUCCESS);
    }

    @ApiOperation(value = "查询小程序", tags = "小程序接口")
    @ApiResponse(code = 200, message = "查询小程序成功")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("app-msg:list")
    public JSONObject search(@ApiParam("小程序素材") @RequestBody MiniProgramModel miniProgramModel) {
        miniProgramModel.setWechatId(getWechatId());
        Page<MiniProgramDto> data = materialService.searchMiniProgram(miniProgramModel, true);
        return representation(Message.MATERIAL_MINI_PROGRAM_SEARCH_SUCCESS,
                              data, miniProgramModel.getPageSize(),
                              miniProgramModel.getPageNum(), data.getTotal());
    }
}
