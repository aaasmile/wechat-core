package com.d1m.wechat.controller.material;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.MiniProgramDto;
import com.d1m.wechat.pamametermodel.MaterialModel;
import com.d1m.wechat.pamametermodel.MiniProgramModel;
import com.d1m.wechat.service.MaterialService;
import com.d1m.wechat.util.Message;

/**
 * MaterialMiniProgramController
 *
 * @author f0rb on 2017-11-22.
 */
@Slf4j
@RestController
@RequestMapping("/material/miniprogram")
@Api(value = "素材API", tags = {"素材接口", "小程序接口"})
public class MaterialMiniProgramController extends BaseController {

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
        materialService.createMiniProgram(getUser().getId(), getWechatId(), materialModel);
        return representation(Message.MATERIAL_MINI_PROGRAM_CREATE_SUCCESS);
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
        Page<MiniProgramDto> data = materialService.searchMiniProgram(miniProgramModel, true);
        return representation(Message.MATERIAL_MINI_PROGRAM_SEARCH_SUCCESS, data);
    }
}
