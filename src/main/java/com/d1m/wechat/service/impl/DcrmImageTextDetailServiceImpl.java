package com.d1m.wechat.service.impl;

import cn.d1m.wechat.client.model.WxMessage;
import cn.d1m.wechat.client.model.WxQRCode;
import cn.d1m.wechat.client.model.common.WxFile;
import com.alibaba.fastjson.JSON;
import com.d1m.wechat.dto.DcrmImageTextDetailDto;
import com.d1m.wechat.dto.QrcodeDto;
import com.d1m.wechat.dto.QueryDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.DcrmImageTextDetailMapper;
import com.d1m.wechat.mapper.MaterialMapper;
import com.d1m.wechat.mapper.QrcodeMapper;
import com.d1m.wechat.model.*;
import com.d1m.wechat.model.enums.MaterialStatus;
import com.d1m.wechat.pamametermodel.ConversationModel;
import com.d1m.wechat.service.*;
import com.d1m.wechat.util.*;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

/**
 * @program: wechat-core
 * @Date: 2018/12/6 16:26
 * @Author: Liu weilin
 * @Description:
 */

@Service
public class DcrmImageTextDetailServiceImpl implements DcrmImageTextDetailService {
    private Logger logger = Logger.getLogger(getClass());
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

    @Autowired
    private QrcodeMapper qrcodeMapper;

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
        return dcrmImageTextDetailMapper.queryObject(id);
    }


    @Override
    public int update(DcrmImageTextDetailDto dto) {
        DcrmImageTextDetail detail = new DcrmImageTextDetail();
        BeanUtils.copyProperties(dto, detail);
        detail.setLasteUpdatedAt(new Date());
        detail.setStatus(MaterialStatus.INUSED.getValue());
        return dcrmImageTextDetailMapper.updateByPrimaryKeySelective(detail);
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
        if (dto.getMaterialCoverId() != null) {
            material = materialService.getMaterial(detailDto.getWechatId(), dto.getMaterialCoverId());
            ConversationModel conversationModel = new ConversationModel();
            conversationModel.setMaterialId(detailDto.getMaterialCoverId());
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

    @Override
    public Map<String, Object> createQrcode(DcrmImageTextDetailDto dto) {
        Map<String, Object> map = new HashMap<>();
        String qrcodeImgUrl = null;
        Qrcode qrcode = new Qrcode();
        qrcode.setId(dto.getQrcodeId());
        qrcode = qrcodeMapper.selectByPrimaryKey(qrcode);
        if (qrcode != null) {
            //如果超过有效三天，则重新生成图文
            if (isLate(DateUtils.plusDay2(qrcode.getCreatedAt(), 3))) {
                //生成二维码并更新二维码表
                qrcodeImgUrl = updateQrcode(dto);
            } else {
                qrcodeImgUrl = qrcode.getQrcodeImgUrl();
            }
        } else {
            //生成二维码并插入数据库
            qrcodeImgUrl = addQrcode(dto);
            //更新非群发单图文表中二维码id
            DcrmImageTextDetail detail = new DcrmImageTextDetail();
            detail.setId(dto.getId());
            detail.setQrcodeId(dto.getQrcodeId());
            dcrmImageTextDetailMapper.updateByid(detail);
        }
        map.put("qrcodeImgUrl", qrcodeImgUrl);
        map.put("id", dto.getId());//非群发单图文id
        logger.info("wxQrcode二维码：" + JSON.toJSON(map));
        return map;
    }


    /**
     * 有效期判断
     *
     * @param createTime
     * @return
     */
    private static boolean isLate(Date createTime) {
        if (createTime.compareTo(new Date()) < 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 生成二维码并插入数据库
     *
     * @param dto
     * @return
     */
    private String addQrcode(DcrmImageTextDetailDto dto) {
        String format = DateUtil.yyyyMMddHHmmss.format(new Date());
        String type = Constants.IMAGE + File.separator + Constants.QRCODE;
        File root = FileUtils.getUploadPathRoot(dto.getWechatId(), type);
        File dir = new File(root, format.substring(0, 6));
        String sceneStr = Rand.getRandom(32);
        Integer expire_scends = 259200;//有效期为3天
        WxQRCode wxQrcode = WechatClientDelegate.createQRCode(dto.getWechatId(), expire_scends, sceneStr);
        Qrcode qr = new Qrcode();
        qr.setStatus((byte) 1);
        qr.setWechatId(dto.getWechatId());
        qr.setTicket(wxQrcode.getTicket());
        qr.setName(dto.getTitle());
        qr.setQrcodeUrl(wxQrcode.getUrl());
        qr.setExpireSeconds(expire_scends);
        qr.setCreatedAt(new Date());
        qr.setCreatorId(dto.getCreatedBy());
        WxFile wxFile = WechatClientDelegate.showQRCode(dto.getWechatId(), wxQrcode.getTicket());
        if (!wxFile.moveFileTo(dir)) {
            logger.error("文件移动失败! ticket =" + wxQrcode.getTicket());
        }
        qr.setQrcodeImgUrl(FileUploadConfigUtil.getInstance().getValue(dto.getWechatId(), "upload_url_base")
         + File.separator
         + type
         + File.separator
         + dir.getName()
         + File.separator + wxFile.getFilename());
        int t = qrcodeMapper.insert(qr);
        logger.info("【插入二维码】二维码图片表结果：" + t);
        String qrcodeImgUrl = qr.getQrcodeImgUrl();
        logger.info("【插入二维码】二维码图片地址：" + qrcodeImgUrl);
        return qrcodeImgUrl;
    }

    /**
     * 生成二维码并更新二维码表
     *
     * @param dto
     * @return
     */
    private String updateQrcode(DcrmImageTextDetailDto dto) {
        String format = DateUtil.yyyyMMddHHmmss.format(new Date());
        String type = Constants.IMAGE + File.separator + Constants.QRCODE;
        File root = FileUtils.getUploadPathRoot(dto.getWechatId(), type);
        File dir = new File(root, format.substring(0, 6));
        String sceneStr = Rand.getRandom(32);
        Integer expire_scends = 259200;//有效期为3天
        WxQRCode wxQrcode = WechatClientDelegate.createQRCode(dto.getWechatId(), expire_scends, sceneStr);
        Qrcode qr = new Qrcode();
        qr.setStatus((byte) 1);
        qr.setWechatId(dto.getWechatId());
        qr.setTicket(wxQrcode.getTicket());
        qr.setName(dto.getTitle());
        qr.setQrcodeUrl(wxQrcode.getUrl());
        qr.setExpireSeconds(expire_scends);
        qr.setCreatedAt(new Date());
        qr.setCreatorId(dto.getCreatedBy());
        WxFile wxFile = WechatClientDelegate.showQRCode(dto.getWechatId(), wxQrcode.getTicket());
        if (!wxFile.moveFileTo(dir)) {
            logger.error("【更新二维码】文件移动失败! ticket =" + wxQrcode.getTicket());
        }
        qr.setQrcodeImgUrl(FileUploadConfigUtil.getInstance().getValue(dto.getWechatId(), "upload_url_base")
         + File.separator
         + type
         + File.separator
         + dir.getName()
         + File.separator + wxFile.getFilename());
        int t = qrcodeMapper.updateByPrimaryKey(qr);
        logger.info("【更新二维码】二维码图片表结果：" + t);
        String qrcodeImgUrl = qr.getQrcodeImgUrl();
        logger.info("【更新二维码】二维码图片地址：" + qrcodeImgUrl);
        return qrcodeImgUrl;
    }
}
