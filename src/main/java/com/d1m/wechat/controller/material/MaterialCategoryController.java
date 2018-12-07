package com.d1m.wechat.controller.material;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.model.MaterialCategory;
import com.d1m.wechat.pamametermodel.MaterialCategoryEntity;
import com.d1m.wechat.service.MaterialCategoryService;
import com.d1m.wechat.util.Message;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * @program: wechat-core
 * @Date: 2018/12/6 16:05
 * @Author: Liu weilin
 * @Description: 素材分类管理
 */
@Slf4j
@RestController
@RequestMapping("/material/category")
@Api(value = "素材分类API", tags = "素材分类")
public class MaterialCategoryController extends BaseController {

    @Autowired
    private MaterialCategoryService materialCategoryService;

    /**
     * 保存
     */
    @ApiOperation(value = "添加素材分类接口", tags = "素材分类")
    @ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject save(@RequestBody MaterialCategoryEntity dto) {
        Preconditions.checkArgument(StringUtils.isNotBlank(dto.getName()), "素材类型名称不能为空");
        MaterialCategory materialCategory = materialCategoryService.exitsName(dto.getName());
        Preconditions.checkArgument(materialCategory != null, "分类名称已存在！");
        materialCategoryService.save(dto);
        return representation(Message.SUCCESS);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新素材分类接口", tags = "素材分类")
    @ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "update.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@RequestBody MaterialCategoryEntity dto) {
        Preconditions.checkArgument(StringUtils.isNotBlank(dto.getName()), "素材类型名称不能为空");
        MaterialCategory materialCategory = materialCategoryService.exitsName(dto.getName());
        Preconditions.checkArgument(materialCategory != null, "分类名称已存在！");
        materialCategoryService.update(dto);
        return representation(Message.SUCCESS);
    }

    /**
     * 查看信息
     */
    @ApiOperation(value = "查看信息", tags = "素材分类接口")
    @ApiResponse(code = 200, message = "操作成功")
    @RequestMapping("{id}/info.json")
    @ResponseBody
    public JSONObject info(@PathVariable("id") String id) {
        Preconditions.checkArgument(StringUtils.isNotBlank(id), "id不能为空");
        MaterialCategoryEntity materialCategoryEntity = materialCategoryService.queryObject(id);
        return representation(Message.SUCCESS, materialCategoryEntity);
    }

    /**
     * 删除素材分类
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除素材分类名称", tags = "素材分类接口")
    @ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "{id}/delete.json", method = RequestMethod.DELETE)
    @RequiresPermissions("app-msg:list")
    @ResponseBody
    public JSONObject delete(
     @ApiParam("素材分类ID")
     @PathVariable String id) {
        try {
            materialCategoryService.delete(id);
            return representation(Message.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }


    /**
     * 获取素材图文列表
     * @param params
     * @return
     */
    @ApiOperation(value="获取素材图文列表", tags="素材接口")
    @ApiResponse(code=200, message="1-获取素材图文列表成功")
    @RequestMapping(value = "list.json")
    @RequiresPermissions("app-msg:list")
    @ResponseBody
    public JSONObject queryList(
     @ApiParam(name="params",required=false)
     @RequestBody(required = false) @RequestParam Map<String, Object> params) {
        List<MaterialCategory> list = materialCategoryService.queryList(params);
        return representation(Message.SUCCESS,list);
    }

}
