package com.d1m.wechat.service.impl;

import cn.d1m.wechat.client.model.WxQRCode;
import cn.d1m.wechat.client.model.common.WxFile;
import com.alibaba.fastjson.JSON;
import com.d1m.wechat.dto.DcrmImageTextDetailDto;
import com.d1m.wechat.dto.QueryDto;
import com.d1m.wechat.mapper.*;
import com.d1m.wechat.model.*;
import com.d1m.wechat.model.enums.MaterialStatus;
import com.d1m.wechat.service.*;
import com.d1m.wechat.util.*;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private static final String SOCIAL_WECHAT_API = "http://social-wechat-api:10011/custom/sender/";
    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MemberService memberService;


    @Autowired
    private DcrmImageTextDetailMapper dcrmImageTextDetailMapper;

    @Autowired
    private QrcodeMapper qrcodeMapper;

    @Autowired
    private ActionEngineMapper actionEngineMapper;

    @Autowired
    private QrcodeActionEngineMapper qrcodeActionEngineMapper;

    @Autowired
    private MaterialImageTextDetailMapper materialImageTextDetailMapper;


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public int save(DcrmImageTextDetailDto dto) {
        DcrmImageTextDetail detail = new DcrmImageTextDetail();
        BeanUtils.copyProperties(dto, detail);
        detail.setCreatedAt(new Date());
        detail.setLasteUpdatedAt(new Date());
        detail.setStatus(MaterialStatus.INUSED.getValue());
        return dcrmImageTextDetailMapper.insert(detail);
    }

    @Override
    public DcrmImageTextDetailDto queryObject(Integer id) {
        List<DcrmImageTextDetailDto> list = new ArrayList<>();
        DcrmImageTextDetailDto detailDto = dcrmImageTextDetailMapper.queryObject(id);
        if (detailDto != null) {
            list.add(detailDto);
            //检验是否不完整非群发单图文
            checkIsNotComplete(list);
            //检查关联微信图文是否已更新
            checkIsWx(list);
        }
        return detailDto;
    }


    @Override
    public int update(DcrmImageTextDetailDto dto) {
        DcrmImageTextDetail detail = new DcrmImageTextDetail();
        BeanUtils.copyProperties(dto, detail);
        detail.setLasteUpdatedAt(new Date());
        detail.setStatus(MaterialStatus.INUSED.getValue());
        return dcrmImageTextDetailMapper.update(detail);
    }


    @Override
    public PageInfo<DcrmImageTextDetailDto> queryList(QueryDto dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        Map<String, Object> query = MapUtils.beanToMap(dto);
        List<DcrmImageTextDetailDto> list = dcrmImageTextDetailMapper.queryList(query);
        //检验是否不完整非群发单图文
        checkIsNotComplete(list);
        //检查微信图文是否存在
        checkIsWxImageTextExist(list);
        //检查关联微信图文是否已更新
        checkIsWx(list);
        return new PageInfo<>(list);
    }


    /**
     * 检查关联微信图文是否已更新
     */
    private void checkIsWx(List<DcrmImageTextDetailDto> list) {
        if (CollectionUtils.isNotEmpty(list))
            for (DcrmImageTextDetailDto detailDto : list) {
                detailDto.setWxImageTextUpdate(false);
                if (detailDto.getMaterialCoverId() != null) {
                    Material material = materialMapper.queryMtByDetailId(detailDto.getWxImageTextId());
                    if (material!=null)
                    if (detailDto.getWxLastPushTime() != null && material.getLastPushAt().compareTo(detailDto.getWxLastPushTime()) > 0) {
                        detailDto.setWxImageTextUpdate(true);
                    }
                }
            }

    }

    /**
     * 检验是否不完整非群发单图文
     *
     * @param list
     * @return
     */
    private void checkIsNotComplete(List<DcrmImageTextDetailDto> list) {
        if (CollectionUtils.isNotEmpty(list))
            for (DcrmImageTextDetailDto detailDto : list) {
                detailDto.setNotComplete(false);
                if (StringUtils.isEmpty(detailDto.getTitle())) {
                    detailDto.setNotComplete(true);
                    continue;
                }

                if (StringUtils.isEmpty(detailDto.getLink())
                 && detailDto.getWxImageTextId() == null) {
                    detailDto.setNotComplete(true);
                    continue;
                }

                if (StringUtils.isEmpty(detailDto.getCoverPicUrl())) {
                    detailDto.setNotComplete(true);
                    continue;
                }
            }
    }


    /**
     * 检查微信图文是否存在
     *
     * @param list
     */
    private void checkIsWxImageTextExist(List<DcrmImageTextDetailDto> list) {
        if (CollectionUtils.isNotEmpty(list))
            for (DcrmImageTextDetailDto detailDto : list) {
                detailDto.setWxImageTextExist(true);
                if (detailDto.getWxImageTextId() != null) {
                    MaterialImageTextDetail materialImageTextDetail = new MaterialImageTextDetail();
                    materialImageTextDetail.setStatus((byte) 1);
                    materialImageTextDetail.setId(detailDto.getWxImageTextId());
                    MaterialImageTextDetail m = materialImageTextDetailMapper.selectOne(materialImageTextDetail);
                    if (m == null) detailDto.setWxImageTextExist(false);
                    continue;
                }

            }
    }


    @Override
    public void previewMaterial(DcrmImageTextDetailDto detailDto) {
        try {
            List<Articles> articlesList = new ArrayList<>();
            Integer id = detailDto.getId();
            notBlank(id, Message.MATERIAL_ID_NOT_BLANK);
            notBlank(detailDto.getMemberId(), Message.MEMBER_ID_NOT_EMPTY);
            Member member = memberService.getMember(detailDto.getWechatId(),
             detailDto.getMemberId());
            notBlank(member, Message.MEMBER_NOT_EXIST);
            DcrmImageTextDetailDto dto = queryObject(id);
            logger.info("查询非群发图文结果：" + JSON.toJSON(dto));
            Material material = new Material();
            if (dto.getMaterialCoverId() != null) {
                material = materialService.getMaterial(detailDto.getWechatId(), dto.getMaterialCoverId());
                logger.info("查询素材信息：" + JSON.toJSON(material));
                //发送图文
                Articles articles = new Articles.Builder()
                 .picurl(material.getPicUrl())
                 .url(dto.getLink())
                 .description(dto.getContent())
                 .title(dto.getTitle()).build();
                articlesList.add(articles);
                News news = new News.Builder().articles(articlesList).build();
                CustomRequestBody customRequestBody = new CustomRequestBody.Builder()
                 .touser(member.getOpenId())
                 .msgtype("news")
                 .news(news)
                 .build();
                logger.info("请求发送非群发图文消息入参：" + JSON.toJSON(customRequestBody));
                String socialWechatApi = System.getProperty("social.wechat.api") == null ? SOCIAL_WECHAT_API : System.getProperty("social.wechat.api");
                String customUrl = socialWechatApi + detailDto.getWechatId();
                //String result = customService.sender(customRequestBody, dto.getWechatId());
                String result = restTemplate.postForObject(customUrl, customRequestBody, String.class);
                logger.info("调用发送非群发图文接口返回：" + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /*@Override
    public Map<String, Object> createQrcode(DcrmImageTextDetailDto dto) {
        Map<String, Object> map = new HashMap<>();
        String qrcodeImgUrl = null;
        Qrcode qrcode = new Qrcode();
        qrcode.setId(dto.getQrcodeId());
        qrcode = qrcodeMapper.selectByPrimaryKey(qrcode);

        //创建时间比当前时间大 ，删除该qrcode的关联图文数据，重新创建Qrcode关联数据
        boolean isAfter = false;
        if(qrcode.getCreatedAt() != null) {
            LocalDateTime localDateTimeToday = LocalDateTime.now();
            LocalDateTime createDateTimeToday = LocalDateTime.ofInstant(qrcode.getCreatedAt().toInstant(), ZoneId.systemDefault());
            LocalDateTime endTime = createDateTimeToday.plusDays(3);
            //创建时间比当前时间大
            isAfter = endTime.isAfter(localDateTimeToday);
        }
        //检查是否有ActionEngine以及里面的图文是否还存在，否则删除
        final Example qrcodeActionEngineExample = new Example(QrcodeActionEngine.class);
        qrcodeActionEngineExample.createCriteria().andEqualTo("qrcodeId",qrcode.getId());
        List<QrcodeActionEngine> qrcodeActionEngineList = qrcodeActionEngineMapper.selectByExample(qrcodeActionEngineExample);

        for(int i = 0; i < qrcodeActionEngineList.size(); i++) {
            try {
                QrcodeActionEngine qrcodeActionEngineR = qrcodeActionEngineList.get(i);
                ActionEngine actionEngineR = actionEngineMapper.selectByPrimaryKey(qrcodeActionEngineR.getActionEngineId());
                if(actionEngineR == null || StringUtils.isEmpty(actionEngineR.getEffect())) {
                    qrcodeActionEngineMapper.delete(qrcodeActionEngineR);
                    continue;
                }
                JSONArray jsonArray = JSONArray.parseArray(actionEngineR.getEffect());
                for(int j = 0; j < jsonArray.size(); j++) {
                    JSONObject dcrmObj = jsonArray.getJSONObject(j);
                    if(dcrmObj.containsKey("value")) {
                        String dcrmImageTextDetailIdS = dcrmObj.getString("value").replace("[","").replace("]","");
                        Integer dcrmImageTextDetailId = Integer.valueOf(dcrmImageTextDetailIdS);
                        DcrmImageTextDetail dcrmImageTextDetail = dcrmImageTextDetailMapper.selectByPrimaryKey(dcrmImageTextDetailId);
                        if(dcrmImageTextDetail == null || isAfter) {
                            actionEngineMapper.delete(actionEngineR);
                            qrcodeActionEngineMapper.delete(qrcodeActionEngineR);
                        }
                    } else {
                        actionEngineMapper.delete(actionEngineR);
                        qrcodeActionEngineMapper.delete(qrcodeActionEngineR);
                    }
                }
            } catch(Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        if (qrcode != null) {
            //如果超过有效三天，则重新生成图文
            if (isAfter) {
                //生成二维码并更新二维码表
                qrcodeImgUrl = updateQrcode(dto);
            } else {
                qrcodeImgUrl = qrcode.getQrcodeImgUrl();
            }
        } else {
            //生成二维码并插入数据库
            qrcodeImgUrl = addQrcode(dto);
        }
        map.put("qrcodeImgUrl", qrcodeImgUrl);
        map.put("id", dto.getId());//非群发单图文id
        logger.info("wxQrcode二维码：" + JSON.toJSON(map));
        return map;
    }*/

    @Override
    public Map<String, Object> createQrcode(DcrmImageTextDetailDto dto) {
        Map<String, Object> map = new HashMap<>();
        String qrcodeImgUrl = null;
        Qrcode qrcode = new Qrcode();
        qrcode.setId(dto.getQrcodeId());
        qrcode = qrcodeMapper.selectByPrimaryKey(qrcode);
        ActionEngine actionEngine = actionEngineMapper.queryByQrcodeId(dto.getQrcodeId());
        if (actionEngine != null) {
            //如果超过有效三天，则重新生成图文
            if (isLate(actionEngine.getEndAt())) {
                //生成二维码并更新二维码表
                qrcodeImgUrl = updateQrcode(dto, actionEngine);
            } else {
                qrcodeImgUrl = qrcode.getQrcodeImgUrl();
            }
        } else {
            //生成二维码并插入数据库
            qrcodeImgUrl = addQrcode(dto);

        }
        map.put("qrcodeImgUrl", qrcodeImgUrl);
        map.put("id", dto.getId());//非群发单图文id
        logger.info("wxQrcode二维码：" + JSON.toJSON(map));
        return map;
    }


    /**
     * 有效期判断
     *
     * @param dateTime
     * @return
     */
    private static boolean isLate(Date dateTime) {
        if (dateTime.compareTo(new Date()) < 0) {
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
        logger.info("wxQrcode:" + JSON.toJSON(wxQrcode));
        Qrcode qr = new Qrcode();
        qr.setStatus((byte) 1);
        qr.setWechatId(dto.getWechatId());
        qr.setTicket(wxQrcode.getTicket());
        qr.setName(dto.getTitle());
        qr.setQrcodeUrl(wxQrcode.getUrl());
        qr.setExpireSeconds(expire_scends);
        qr.setCreatedAt(new Date());
        qr.setCreatorId(dto.getCreatedBy());
        qr.setActionName((byte) 1);
        qr.setScene(sceneStr);
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
        logger.info("【插入二维码】二维码图片表结果：" + t + "二维码编号：" + qr.getId());
        //更新非群发单图文表中二维码id
        DcrmImageTextDetail detail = new DcrmImageTextDetail();
        detail.setId(dto.getId());
        detail.setQrcodeId(qr.getId());
        dcrmImageTextDetailMapper.updateByPrimaryKeySelective(detail);
        //插入effect和关系表
        saveEngine(dto, qr);
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
    private String updateQrcode(DcrmImageTextDetailDto dto, ActionEngine actionEngine) {
        String format = DateUtil.yyyyMMddHHmmss.format(new Date());
        String type = Constants.IMAGE + File.separator + Constants.QRCODE;
        File root = FileUtils.getUploadPathRoot(dto.getWechatId(), type);
        File dir = new File(root, format.substring(0, 6));
        String sceneStr = Rand.getRandom(32);
        Integer expire_scends = 259200;//有效期为3天
        WxQRCode wxQrcode = WechatClientDelegate.createQRCode(dto.getWechatId(), expire_scends, sceneStr);
        Qrcode qr = new Qrcode();
        qr.setId(dto.getQrcodeId());
        qr.setStatus((byte) 1);
        qr.setWechatId(dto.getWechatId());
        qr.setTicket(wxQrcode.getTicket());
        qr.setName(dto.getTitle());
        qr.setQrcodeUrl(wxQrcode.getUrl());
        qr.setExpireSeconds(expire_scends);
        qr.setCreatedAt(new Date());
        qr.setCreatorId(dto.getCreatedBy());
        qr.setActionName((byte) 1);
        qr.setScene(sceneStr);
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
        int t = qrcodeMapper.updateByPrimaryKeySelective(qr);
        logger.info("【更新二维码】二维码图片表结果：" + t);
        //更新effect和关系表
        updateEngine(actionEngine);
        String qrcodeImgUrl = qr.getQrcodeImgUrl();
        logger.info("【更新二维码】二维码图片地址：" + qrcodeImgUrl);
        return qrcodeImgUrl;
    }

    private void updateEngine(ActionEngine actionEngine) {
        LocalDateTime localDateTimeToday = LocalDateTime.now();
        LocalDateTime endTime = localDateTimeToday.plusDays(3);
        Date endDate = Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant());
        Date startDate = Date.from(localDateTimeToday.atZone(ZoneId.systemDefault()).toInstant());
        actionEngine.setEndAt(endDate);
        actionEngine.setStartAt(startDate);
        int t = actionEngineMapper.updateByPrimaryKeySelective(actionEngine);
        logger.info("更新updateEngine状态：" + t);

    }

    private void saveEngine(DcrmImageTextDetailDto dto, Qrcode qrcode) {
        ActionEngine actionEngine = new ActionEngine();
        String effect = "[{\"code\":301,\"value\":[" + dto.getId() + "]}]";

        LocalDateTime localDateTimeToday = LocalDateTime.now();
        LocalDateTime endTime = localDateTimeToday.plusDays(3);
        Date endDate = Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant());
        Date startDate = Date.from(localDateTimeToday.atZone(ZoneId.systemDefault()).toInstant());

        actionEngine.setEffect(effect);
        actionEngine.setEndAt(endDate);
        actionEngine.setStartAt(startDate);
        actionEngine.setRunType((byte) 1);
        actionEngine.setStatus(MaterialStatus.INUSED.getValue());
        actionEngine.setName(dto.getTitle());
        actionEngine.setWechatId(dto.getWechatId());
        actionEngine.setCreatorId(dto.getCreatedBy());
        actionEngine.setCreatedAt(new Date());
        actionEngineMapper.insert(actionEngine);

        QrcodeActionEngine qrcodeActionEngine = new QrcodeActionEngine();
        qrcodeActionEngine.setWechatId(dto.getWechatId());
        qrcodeActionEngine.setActionEngineId(actionEngine.getId());
        qrcodeActionEngine.setCreatedAt(new Date());
        qrcodeActionEngine.setCreatorId(dto.getCreatedBy());
        qrcodeActionEngine.setQrcodeId(qrcode.getId());
        qrcodeActionEngineMapper.insert(qrcodeActionEngine);

    }


    /**
     * 更新发送数量
     *
     * @param id 非群发图文id
     * @return
     */
    public int updateSendTimes(Integer id) {
        DcrmImageTextDetailDto dto = dcrmImageTextDetailMapper.queryObject(id);
        if (dto != null) {
            Integer sendTimes = dto.getSendTimes() != null ? dto.getSendTimes() : 0;
            Integer count = sendTimes + 1;
            DcrmImageTextDetail dcrmImageTextDetail = new DcrmImageTextDetail();
            dcrmImageTextDetail.setId(id);
            dcrmImageTextDetail.setSendTimes(count);
            return dcrmImageTextDetailMapper.updateByid(dcrmImageTextDetail);
        }

        return 0;
    }
}
