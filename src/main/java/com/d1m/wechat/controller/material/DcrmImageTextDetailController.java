package com.d1m.wechat.controller.material;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.DcrmImageTextDetailDto;
import com.d1m.wechat.dto.QrcodeDto;
import com.d1m.wechat.dto.QueryDto;
import com.d1m.wechat.model.Qrcode;
import com.d1m.wechat.service.DcrmImageTextDetailService;
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

import java.util.Map;


/**
 * @program: wechat-core
 * @Date: 2018/12/7 11:12
 * @Author: Liu weilin
 * @Description: 非群发单图文
 */
@Api(value = "非群发单图文", tags = "非群发单图文API")
@Slf4j
@RestController
@RequestMapping("/dcrm")
public class DcrmImageTextDetailController extends BaseController {

    @Autowired
    private DcrmImageTextDetailService DcrmImageTextDetailService;

    /**
     * 保存
     */
    @ApiOperation(value = "添加接口")
    //@ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject save(@RequestBody DcrmImageTextDetailDto dto) {
        Preconditions.checkArgument(StringUtils.isNotBlank(dto.getTitle()), Message.DcrmImageTextDetail_NOT_NULL);
        Preconditions.checkArgument(dto.getSummary() != null, Message.DcrmImageTextDetail_IAMGE_NOT_NULL);
        dto.setCreatedBy(getUser().getId());
        dto.setWechatId(getUser().getWechatId());
        DcrmImageTextDetailService.save(dto);
        return representation(Message.SUCCESS);
    }


    /**
     * 更新
     */
    @ApiOperation(value = "更新接口")
    //@ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "update.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@RequestBody DcrmImageTextDetailDto dto) {
        Preconditions.checkArgument(StringUtils.isNotBlank(dto.getTitle()), Message.DcrmImageTextDetail_NOT_NULL);
        Preconditions.checkArgument(dto.getSummary() != null, Message.DcrmImageTextDetail_IAMGE_NOT_NULL);
        dto.setLasteUpdatedBy(getUser().getId());
        dto.setWechatId(getUser().getWechatId());
        DcrmImageTextDetailService.update(dto);
        return representation(Message.SUCCESS);
    }


    /**
     * 查看信息
     */
    @ApiOperation(value = "查看信息")
    //@ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "{id}/info.json", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject info(@PathVariable("id") Integer id) {
        Preconditions.checkNotNull(id, Message.DCRM_IMAGE_TEXT_DETAIL_ID_NOT);
        DcrmImageTextDetailDto dto = DcrmImageTextDetailService.queryObject(id);
        return representation(Message.SUCCESS, dto);
    }


    /**
     * 获取非群发素材图文列表
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "获取非群发素材图文列表")
    //@ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "list.json", method = RequestMethod.POST)
    @RequiresPermissions("app-msg:list")
    @ResponseBody
    public JSONObject queryList(@RequestBody QueryDto dto) {
        log.info("获取非群发素材图文列表:{}",dto);
        PageInfo<DcrmImageTextDetailDto> list = DcrmImageTextDetailService.queryList(dto);
        return representation(Message.SUCCESS, list);
    }


    /**
     * 素材图文推送微信（素材预览）
     *
     * @param detailDto
     * @return
     */
    @ApiOperation(value = "素材图文推送微信")
    //@ApiResponse(code = 200, message = "1-素材图文推送微信成功")
    @RequestMapping(value = "preview.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("app-msg:list")
    public JSONObject preview(
     @ApiParam(name = "DcrmImageTextDetailDto", required = false)
     @RequestBody(required = false) DcrmImageTextDetailDto detailDto) {
        try {
            detailDto.setWechatId(getUser().getWechatId());
            DcrmImageTextDetailService.previewMaterial(detailDto);
            return representation(Message.MATERIAL_IMAGE_TEXT_PUSH_WX_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }


    /**
     * 生成二维码接口
     */
    @ApiOperation(value = "生成二维码接口")
    //@ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "createQrcode.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject createQrcode(@RequestBody DcrmImageTextDetailDto dto) {
        dto.setCreatedBy(getUser().getId());
        dto.setWechatId(getUser().getWechatId());
        Map<String, Object> map = DcrmImageTextDetailService.createQrcode(dto);
        return representation(Message.SUCCESS,map);
    }


}
