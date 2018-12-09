package com.d1m.wechat.controller.material;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.dto.NofSendDto;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.model.MaterialCategory;

import com.d1m.wechat.pamametermodel.MaterialModel;
import com.d1m.wechat.service.NofSendService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @program: wechat-core
 * @Date: 2018/12/7 11:12
 * @Author: Liu weilin
 * @Description: 非群发单图文
 */
@Slf4j
@RestController
@RequestMapping("/noFsend")
@Api(value = "非群发单图文API", tags = "非群发单图文")
public class NoFsendController extends BaseController {

    @Autowired
    private NofSendService nofSendService;
    /**
     * 保存
     */
    @ApiOperation(value = "添加接口", tags = "非群发单图文")
    @ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject save(@RequestBody NofSendDto dto) {
        Preconditions.checkArgument(StringUtils.isNotBlank(dto.getTitle()), "标题不能为空");
        Preconditions.checkArgument(dto.getText()!=null, Message.MATERIAL_IMAGE_TEXT_NOT_BLANK);
        Preconditions.checkArgument(dto.getItems()!=null, "封面图不能为空");
        dto.setCreatorId(getUser().getCreatorId());
        dto.setWechatId(getUser().getWechatId());
        nofSendService.save(dto);
        return representation(Message.SUCCESS);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新接口", tags = "非群发单图文")
    @ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "update.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@RequestBody NofSendDto dto) {
        Preconditions.checkArgument(StringUtils.isNotBlank(dto.getTitle()), "标题不能为空");
        Preconditions.checkArgument(dto.getText()!=null, Message.MATERIAL_IMAGE_TEXT_NOT_BLANK);
        Preconditions.checkArgument(dto.getItems()!=null, "封面图不能为空");
        dto.setCreatorId(getUser().getCreatorId());
        dto.setWechatId(getUser().getWechatId());
        nofSendService.update(dto);
        return representation(Message.SUCCESS);
    }
    /**
     * 查看信息
     */
    @ApiOperation(value = "查看信息", tags = "非群发单图文")
    @ApiResponse(code = 200, message = "操作成功")
    @RequestMapping("{id}/info.json")
    @ResponseBody
    public JSONObject info(@PathVariable("id") String id) {
        Preconditions.checkArgument(StringUtils.isNotBlank(id), "id不能为空");
        NofSendDto dto = nofSendService.queryObject(id);
        return representation(Message.SUCCESS, dto);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除", tags = "非群发单图文")
    @ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "{id}/delete.json", method = RequestMethod.DELETE)
    @RequiresPermissions("app-msg:list")
    @ResponseBody
    public JSONObject delete(
     @ApiParam("素材分类ID")
     @PathVariable String id) {
        try {
            nofSendService.delete(id);
            return representation(Message.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    /**
     * 获取非群发素材图文列表
     * @param params
     * @return
     */
    @ApiOperation(value="获取非群发素材图文列表", tags="非群发单图文")
    @ApiResponse(code=200, message="操作成功")
    @RequestMapping(value = "list.json")
    @RequiresPermissions("app-msg:list")
    @ResponseBody
    public JSONObject queryList(
     @ApiParam(name="params",required=false)
     @RequestBody(required = false) @RequestParam Map<String, Object> params) {
        List<NofSendDto> list = nofSendService.queryList(params);
        return representation(Message.SUCCESS,list);
    }



}
