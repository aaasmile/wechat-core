package com.d1m.wechat.controller.material;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.DcrmImageTextDetailDto;
import com.d1m.wechat.dto.MemberDto;
import com.d1m.wechat.dto.QueryDto;
import com.d1m.wechat.service.ConversationService;
import com.d1m.wechat.service.DcrmImageTextDetailService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.util.CommonUtils;
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

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

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
    private DcrmImageTextDetailService dcrmImageTextDetailService;
    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MemberService memberService;

    /**
     * 保存
     */
    @ApiOperation(value = "添加接口")
    //@ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject save(@RequestBody DcrmImageTextDetailDto dto) {
        try {
            //log.info("添加非群发单图文接口入参：{}", JSON.toJSON(dto));
            Preconditions.checkArgument(StringUtils.isNotBlank(dto.getTitle()), Message.DcrmImageTextDetail_NOT_NULL);
            Preconditions.checkArgument(dto.getMaterialCoverId() != null, Message.DcrmImageTextDetail_IAMGE_NOT_NULL);
            dto.setCreatedBy(getUser().getId());
            dto.setWechatId(getUser().getWechatId());
            dcrmImageTextDetailService.save(dto);
            return representation(Message.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }


    /**
     * 更新
     */
    @ApiOperation(value = "更新接口")
    //@ApiResponse(code = 200, message = "操作成功")
    @RequestMapping(value = "update.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@RequestBody DcrmImageTextDetailDto dto) {
        try {
            //log.info("更新非群发单图文接口入参：{}", JSON.toJSON(dto));
            Preconditions.checkArgument(StringUtils.isNotBlank(dto.getTitle()), Message.DcrmImageTextDetail_NOT_NULL);
            Preconditions.checkArgument(dto.getMaterialCoverId() != null, Message.DcrmImageTextDetail_IAMGE_NOT_NULL);
            dto.setLasteUpdatedBy(getUser().getId());
            dto.setWechatId(getUser().getWechatId());
            dcrmImageTextDetailService.update(dto);
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
    public JSONObject info(@PathVariable("id") Integer id) {
        try {
            Preconditions.checkNotNull(id, Message.DCRM_IMAGE_TEXT_DETAIL_ID_NOT);
            DcrmImageTextDetailDto dto = dcrmImageTextDetailService.queryObject(id);
            return representation(Message.SUCCESS, dto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
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
        try {
            log.info("获取非群发素材图文列表:{}", dto);
            PageInfo<DcrmImageTextDetailDto> list = dcrmImageTextDetailService.queryList(dto);
            return representation(Message.SUCCESS, list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
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
            detailDto.setId(detailDto.getNewid());
            notBlank(detailDto.getId(), Message.DCRM_IMAGE_TEXT_DETAIL_ID_NOT);
            notBlank(detailDto.getMemberId(), Message.MEMBER_ID_NOT_EMPTY);
            detailDto.setWechatId(getUser().getWechatId());
            notBlank(detailDto.getMemberId(), Message.MEMBER_ID_NOT_EMPTY);
            MemberDto member = memberService.getMemberDto(getWechatId(), detailDto.getMemberId());
            notBlank(member, Message.MEMBER_NOT_EXIST);
            CommonUtils.send2SocialWechatCoreApi(getWechatId(), member, detailDto.getNewid(), detailDto.getNewtype(), conversationService);
            //更新发送数量
            int t = dcrmImageTextDetailService.updateSendTimes(detailDto.getId());
            log.debug("发送次数更新状态：{}", t);
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
        try {
            dto.setCreatedBy(getUser().getId());
            dto.setWechatId(getUser().getWechatId());
            Map<String, Object> map = dcrmImageTextDetailService.createQrcode(dto);
            return representation(Message.SUCCESS, map);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    /**
     * 发送数
     *
     * @param id 非群发单图文id
     * @return
     */
    @ApiOperation(value = "添加非群发单图文发送数接口")
    @GetMapping("/updateSendTimes/{id}")
    public JSONObject updateSendTimes(@PathVariable("id") Integer id) {
        try {
            //更新发送数量
            int t = dcrmImageTextDetailService.updateSendTimes(id);
            log.debug("发送次数更新状态：{}", t);
            return representation(Message.SUCCESS, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }


}