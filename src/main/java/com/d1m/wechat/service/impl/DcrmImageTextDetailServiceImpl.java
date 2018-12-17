package com.d1m.wechat.service.impl;

import cn.d1m.wechat.client.model.WxMessage;
import com.d1m.wechat.dto.DcrmImageTextDetailDto;
import com.d1m.wechat.dto.QueryDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.DcrmImageTextDetailMapper;
import com.d1m.wechat.mapper.MaterialMapper;
import com.d1m.wechat.model.*;
import com.d1m.wechat.model.enums.MaterialStatus;
import com.d1m.wechat.pamametermodel.ConversationModel;
import com.d1m.wechat.service.*;
import com.d1m.wechat.util.MapUtils;
import com.d1m.wechat.util.Message;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

/**
 * @program: wechat-core
 * @Date: 2018/12/6 16:26
 * @Author: Liu weilin
 * @Description:
 */
@Service
public class DcrmImageTextDetailServiceImpl implements DcrmImageTextDetailService {

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MemberService memberService;
    @Autowired
    private ConversationService conversationService;

    @Autowired
    private DcrmImageTextDetailMapper dcrmImageTextDetailMapper;

    @Override
    public int save(DcrmImageTextDetailDto dto) {
        DcrmImageTextDetail detail = new DcrmImageTextDetail();
        BeanUtils.copyProperties(dto, detail);
        detail.setCreatedAt(new Date());
        detail.setStatus(MaterialStatus.INUSED.getValue());
        return dcrmImageTextDetailMapper.insert(detail);
    }

    @Override
    public DcrmImageTextDetailDto queryObject(Integer id) {
        DcrmImageTextDetailDto DcrmImageTextDetailDto = new DcrmImageTextDetailDto();
        DcrmImageTextDetail imageTextDetail = dcrmImageTextDetailMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(imageTextDetail, DcrmImageTextDetailDto);
        return DcrmImageTextDetailDto;
    }


    @Override
    public int update(DcrmImageTextDetailDto dto) {
        DcrmImageTextDetail detail = new DcrmImageTextDetail();
        BeanUtils.copyProperties(dto, detail);
        detail.setCreatedAt(new Date());
        detail.setStatus(MaterialStatus.INUSED.getValue());
        return dcrmImageTextDetailMapper.updateByPrimaryKey(detail);
    }


    @Override
    public PageInfo<DcrmImageTextDetailDto> queryList(QueryDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        Map<String, Object> query = MapUtils.beanToMap(dto);
        List<DcrmImageTextDetailDto> list = dcrmImageTextDetailMapper.queryList(query);
        PageInfo<DcrmImageTextDetailDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    public void previewMaterial(DcrmImageTextDetailDto detailDto) {
        Integer id = detailDto.getId();
        notBlank(id, Message.MATERIAL_ID_NOT_BLANK);
        notBlank(detailDto.getMemberId(), Message.MEMBER_ID_NOT_EMPTY);
        Member member = memberService.getMember(detailDto.getWechatId(),
         detailDto.getMemberId());
        notBlank(member, Message.MEMBER_NOT_EXIST);
        DcrmImageTextDetailDto dto = queryObject(id);
        Material material = new Material();
        if (dto.getMaterialId() != null) {
            material = materialService.getMaterial(detailDto.getWechatId(), dto.getMaterialId());
            ConversationModel conversationModel = new ConversationModel();
            conversationModel.setMaterialId(detailDto.getId());
            conversationModel.setMemberId(detailDto.getMemberId());
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            conversationService.wechatToMember(detailDto.getWechatId(), user, conversationModel);
            //发送图文
            if (member.getWechatId() != null) {
                WxMessage wxMessage = WechatClientDelegate.previewMessage(detailDto.getWechatId(), member.getOpenId()
                 , "mpnews", material.getMediaId());
                if (wxMessage.fail()) {
                    throw new WechatException(Message.WEIXIN_HTTPS_REQUEST_ERROR);
                }
            }
        }


    }

   public Qrcode createQrcode(DcrmImageTextDetailDto dto){
       Qrcode qrcode = new Qrcode();

       return qrcode;
    }

}
