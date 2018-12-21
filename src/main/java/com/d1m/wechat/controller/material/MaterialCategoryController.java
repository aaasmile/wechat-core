package com.d1m.wechat.controller.material;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.MaterialCategoryDto;
import com.d1m.wechat.dto.QueryDto;
import com.d1m.wechat.model.MaterialCategory;

import com.d1m.wechat.service.MaterialCategoryService;
import com.d1m.wechat.service.MaterialService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: wechat-core
 * @Date: 2018/12/6 16:05
 * @Author: Liu weilin
 * @Description: 素材分类管理
 */
@Api(tags = "素材分类管理API")
@Slf4j
@RestController
@RequestMapping("/material/category")
public class MaterialCategoryController extends BaseController {

    @Autowired
    private MaterialCategoryService materialCategoryService;

    @Autowired
    private MaterialService materialService;

    /**
     * 保存
     */
    @ApiOperation(value = "添加素材分类接口")
    //@ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject save(@RequestBody MaterialCategoryDto dto) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(dto.getName()), Message.MATERIAL_CATEGORY_NAME_NOT_NULL);
            MaterialCategory materialCategory = materialCategoryService.exitsName(dto.getName());
            Preconditions.checkArgument(materialCategory == null, Message.MATERIAL_CATEGORY_NAME_EXITS);
            dto.setCreatedBy(getUser().getId());
            dto.setWechatId(getUser().getWechatId());
            materialCategoryService.save(dto);
            return representation(Message.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新素材分类接口")
    //@ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "update.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@RequestBody MaterialCategoryDto dto) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(dto.getName()), Message.MATERIAL_CATEGORY_NAME_NOT_NULL);
            MaterialCategory materialCategory = materialCategoryService.exitsName(dto.getName());
            Preconditions.checkArgument(materialCategory == null, Message.MATERIAL_CATEGORY_NAME_EXITS);
            dto.setLasteUpdatedBy(getUser().getId());
            dto.setWechatId(getUser().getWechatId());
            materialCategoryService.update(dto);
            return representation(Message.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    /**
     * 查看信息
     */
    @ApiOperation(value = "查看信息")
    //@ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "{id}/info.json", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject info(@PathVariable("id") String id) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(id), Message.MATERIAL_CATEGORY_ID_NOT_NULL);
            MaterialCategoryDto MaterialCategoryDto = materialCategoryService.queryObject(id);
            return representation(Message.SUCCESS, MaterialCategoryDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    /**
     * 删除素材分类
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除素材分类名称")
    //@ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "{id}/delete.json", method = RequestMethod.POST)
    //@RequiresPermissions("app-msg:list")
    @ResponseBody
    public JSONObject delete(
     @ApiParam("素材分类ID")
     @PathVariable String id) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(id), Message.MATERIAL_CATEGORY_ID_NOT_NULL);
            return materialCategoryService.delete(getUser().getWechatId(), id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }


    /**
     * 获取素材图文列表
     *
     * @param
     * @return
     */
    @ApiOperation(value = "获取素材图文列表")
    //@ApiResponse(code = 200, message = "1-获取素材图文列表成功")
    @RequestMapping(value = "list.json", method = RequestMethod.POST)
    //@RequiresPermissions("app-msg:list")
    @ResponseBody
    public JSONObject queryList(@RequestBody QueryDto dto) {
        try {
            PageInfo<MaterialCategory> list = materialCategoryService.queryList(dto);
            return representation(Message.SUCCESS, list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

}
