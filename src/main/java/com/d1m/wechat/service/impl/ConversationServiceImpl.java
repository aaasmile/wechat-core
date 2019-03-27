package com.d1m.wechat.service.impl;

import cn.d1m.wechat.client.model.WxMessage;
import cn.d1m.wechat.client.model.request.WxArticleMessage;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.common.ds.TenantContext;
import com.d1m.wechat.dto.*;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.*;
import com.d1m.wechat.model.*;
import com.d1m.wechat.model.enums.*;
import com.d1m.wechat.pamametermodel.ConversationActivityModel;
import com.d1m.wechat.pamametermodel.ConversationModel;
import com.d1m.wechat.pamametermodel.MassConversationModel;
import com.d1m.wechat.pamametermodel.MemberModel;
import com.d1m.wechat.schedule.SchedulerRestService;
import com.d1m.wechat.service.*;
import com.d1m.wechat.util.*;
import com.d1m.wechat.wechatclient.CustomService;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.xxl.job.core.biz.model.ReturnT;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

@Service
public class ConversationServiceImpl extends BaseService<Conversation> implements ConversationService {

    private static Logger log = LoggerFactory.getLogger(ConversationServiceImpl.class);

    private Gson gson = new Gson();

    @Autowired
    private ConversationMapper conversationMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialTextDetailMapper materialTextDetailMapper;

    @Autowired
    private ConversationImageTextDetailMapper conversationImageTextDetailMapper;

    @Autowired
    private MassConversationMapper massConversationMapper;

    @Autowired
    private MassConversationResultMapper massConversationResultMapper;

    @Autowired
    private SchedulerRestService schedulerRestService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private MassConversationBatchResultService massConversationBatchResultService;

    @Autowired
    private MaterialImageTextDetailService materialImageTextDetailService;
    @Autowired
    private DcrmImageTextDetailService dcrmImageTextDetailService;
    @Autowired
    private CustomService customService;
    @Autowired
    private ConversationService conversationService;

    @Value("${access-trace-oauth-url}")
    private String traceUrl;

    @Override
    public Mapper<Conversation> getGenericMapper() {
        return conversationMapper;
    }

    @Override
    public Conversation wechatToMember(Integer wechatId, User user, ConversationModel conversationModel, MemberDto member) {
        log.info("wechat to member start.");
        if (conversationModel == null) {
            conversationModel = new ConversationModel();
        }
        notBlank(conversationModel.getMemberId(), Message.MEMBER_ID_NOT_EMPTY);
        if (conversationModel.getMaterialId() == null && StringUtils.isBlank(conversationModel.getContent())) {
            throw new WechatException(Message.CONVERSATION_CONTENT_NOT_BLANK);
        }
        notBlank(member, Message.MEMBER_NOT_EXIST);
        Object message;
        MsgType msgType = null;
        Date current = new Date();
        Material material = null;
        List<ConversationImageTextDetail> conversationImageTextDetails = new ArrayList<ConversationImageTextDetail>();
        ConversationImageTextDetail conversationImageTextDetail = null;
        if (conversationModel.getMaterialId() != null) {
            material = materialService.getMaterial(wechatId, conversationModel.getMaterialId());
            notBlank(material, Message.MATERIAL_NOT_EXIST);
            log.info("material mediaId : {}", material.getMediaId());
            if (material.getMaterialType() == MaterialType.IMAGE_TEXT.getValue()) {
                MaterialDto materialDto = materialService.getImageText(wechatId, conversationModel.getMaterialId(), true);
                log.info("items : {}", materialDto.getItems() != null ? materialDto.getItems().size() : "");
                List<ImageTextDto> items = materialDto.getItems();
                if (items != null && !items.isEmpty()) {
                    JSONArray itemArray = new JSONArray();
                    JSONObject itemJson = null;
                    for (ImageTextDto imageTextDto : items) {
                        conversationImageTextDetail = new ConversationImageTextDetail.Builder().build();
                        conversationImageTextDetail.setWechatId(imageTextDto.getWechatId());
                        conversationImageTextDetail.setAuthor(imageTextDto.getAuthor());
                        conversationImageTextDetail.setContent(imageTextDto.getContent());
                        conversationImageTextDetail.setContentSourceChecked(imageTextDto.getContentSourceChecked());
                        conversationImageTextDetail.setContentSourceUrl(imageTextDto.getContentSourceUrl());
                        conversationImageTextDetail.setShowCover(imageTextDto.isShowCover());
                        conversationImageTextDetail.setSummary(imageTextDto.getSummary());
                        conversationImageTextDetail.setTitle(imageTextDto.getTitle());
                        conversationImageTextDetail.setMaterialCoverUrl(imageTextDto.getMaterialCoverUrl());
                        conversationImageTextDetails.add(conversationImageTextDetail);
                        itemJson = new JSONObject();
                        itemJson.put("title", imageTextDto.getTitle());
                        itemJson.put("summary", imageTextDto.getSummary());
                        itemJson.put("materialCoverUrl", imageTextDto.getMaterialCoverUrl());
                        itemArray.add(itemJson);
                    }
                    log.info("content : {}", itemArray.toJSONString());
                    conversationModel.setContent(itemArray.toJSONString());
                }
                String mediaId = material.getMediaId();
                if (mediaId != null) {
                    msgType = MsgType.MPNEWS;
                    message = material.getMediaId();
                } else {
                    List<WxArticleMessage> articles = new ArrayList<WxArticleMessage>();
                    for (ImageTextDto imageTextDto : items) {
                        WxArticleMessage article = new WxArticleMessage();
                        article.setTitle(imageTextDto.getTitle());
                        article.setDescription(imageTextDto.getSummary());
                        article.setPicUrl(imageTextDto.getMaterialCoverUrl());
                        String wrapper = imageTextDto.getUrl();
                        try {
                            wrapper = String.format(traceUrl, wechatId, URLEncoder.encode(imageTextDto.getUrl(), StandardCharsets.UTF_8.displayName()), imageTextDto.getId(), "mass-send");
                        } catch (UnsupportedEncodingException e) {
                            log.error("url 编码错误", e);
                        }
                        article.setUrl(wrapper);
                        articles.add(article);
                    }
                    msgType = MsgType.NEWS;
                    message = articles;
                }
            } else if (material.getMaterialType() == MaterialType.IMAGE.getValue()) {
                msgType = MsgType.IMAGE;
                message = material.getMediaId();
                conversationModel.setContent(material.getPicUrl());
            } else if (material.getMaterialType() == MaterialType.TEXT.getValue()) {
                MaterialTextDetail materialTextDetail = new MaterialTextDetail();
                materialTextDetail.setMaterialId(material.getId());
                materialTextDetail.setWechatId(material.getWechatId());
                materialTextDetail = materialTextDetailMapper.selectOne(materialTextDetail);
                notBlank(materialTextDetail, Message.MATERIAL_TEXT_DETAIL_NOT_EXIST);

                conversationModel.setContent(materialTextDetail.getContent());
                msgType = MsgType.TEXT;
                message = materialTextDetail.getContent();
            } else {
                throw new WechatException(Message.CONVERSATION_CONTENT_NOT_SUPPORT);
            }
        } else {
            material = new Material();
            msgType = MsgType.TEXT;
            message = conversationModel.getContent();
        }

        log.info("msgType : {}", msgType);

        Conversation conversation = new Conversation();
        conversation.setContent(conversationModel.getContent());
        conversation.setCreatedAt(current);
        conversation.setDirection(true);
        conversation.setWechatId(wechatId);
        conversation.setUserId(user != null ? user.getId() : null);
        conversation.setMemberId(member.getId());
        conversation.setOpenId(member.getOpenId());
        conversation.setUnionId(member.getUnionId());
        conversation.setMsgType(msgType.getValue());
        conversation.setPicUrl(material.getPicUrl());
        conversation.setStatus(ConversationStatus.READ.getValue());
        conversation.setIsMass(false);
        conversation.setMemberPhoto(member.getLocalHeadImgUrl());
        conversation.setKfPhoto(user != null ? user.getLocalHeadImgUrl() : null);
        conversation.setMaterialId(material.getId());
        log.info("conversation : {}", conversation);
        conversationMapper.insert(conversation);
        log.info("conversation id : {}", conversation.getId());
        if (!conversationImageTextDetails.isEmpty()) {
            for (ConversationImageTextDetail citd : conversationImageTextDetails) {
                citd.setConversationId(conversation.getId());
            }
            conversationImageTextDetailMapper.insertList(conversationImageTextDetails);
        }
        String msgtype = msgType.name().toLowerCase();
        WechatClientDelegate.sendCustomMessage(wechatId, member.getOpenId(), msgtype, message);
        log.info("send message success.");
        log.info("wechat to member end.");
        return conversation;
    }

    @Override
    public Page<ConversationDto> search(Integer wechatId, ConversationModel conversationModel, boolean queryCount) {
        if (conversationModel == null) {
            conversationModel = new ConversationModel();
        }
        Page<ConversationDto> conversationDtos = new Page<ConversationDto>(conversationModel.getPageNum(), conversationModel.getPageSize());
        if (conversationModel.pagable()) {
            PageHelper.startPage(conversationModel.getPageNum(), conversationModel.getPageSize(), queryCount);
        }
        Date lastConversationAt = DateUtil.parseDate(conversationModel.getTime());
        conversationModel.setStartAt(DateUtil.parse(DateUtil.utc2DefaultLocal(conversationModel.getStart(), true)));
        conversationModel.setEndAt(new Date());
        conversationDtos = conversationMapper.search(wechatId, conversationModel.getMemberId(), conversationModel.getType(), conversationModel.getDir(), conversationModel.getStatus(), conversationModel.getStartAt(), conversationModel.getEndAt(), lastConversationAt, conversationModel.getMsgType(), conversationModel.getKeyWords(), "id", "desc");
        if (conversationModel.isUpdateRead()) {
            Conversation conversation = null;
            for (ConversationDto conversationDto : conversationDtos) {
                if (conversationDto.getStatus().equals(ConversationStatus.UNREAD.getValue())) {
                    conversation = get(wechatId, conversationDto.getId());
                    conversation.setStatus(ConversationStatus.READ.getValue());
                    conversationMapper.updateByPrimaryKey(conversation);
                }
                if (conversationDto.getIsMass() != null && conversationDto.getIsMass() == 1) {
                    List<MassConversation> massConversations = getMassConversations(wechatId, conversationDto.getId());
                    for (MassConversation massConversation : massConversations) {
                        if (massConversation.getStatus().equals(ConversationStatus.UNREAD.getValue())) {
                            massConversation.setStatus(ConversationStatus.READ.getValue());
                            massConversationMapper.updateByPrimaryKey(massConversation);
                        }
                    }
                }
            }
        }
        return conversationDtos;
    }

    @Override
    public Page<ConversationDto> searchUnread(Integer wechatId, ConversationModel conversationModel, boolean queryCount) {
        if (conversationModel == null) {
            conversationModel = new ConversationModel();
        }
        Page<ConversationDto> conversationDtos = new Page<ConversationDto>(conversationModel.getPageNum(), conversationModel.getPageSize());
        if (conversationModel.pagable() && conversationModel.getPageNum() != null && conversationModel.getPageSize() != null) {
            PageHelper.startPage(conversationModel.getPageNum(), conversationModel.getPageSize(), queryCount);
        }
        Date lastConversationAt = DateUtil.parseDate(conversationModel.getTime());
        conversationDtos = conversationMapper.searchUp(wechatId, conversationModel.getMemberId(), conversationModel.getType(), conversationModel.getMsgTypes(), conversationModel.getDir(), conversationModel.getStatus(), DateUtil.getDateBegin(conversationModel.getStartAt()), DateUtil.getDateEnd(conversationModel.getEndAt()), lastConversationAt, "id", "desc");
        return conversationDtos;
    }

    private List<MassConversation> getMassConversations(Integer wechatId, Integer conversationId) {
        MassConversation record = new MassConversation();
        record.setWechatId(wechatId);
        record.setConversationId(conversationId);
        return massConversationMapper.select(record);
    }

    private Conversation get(Integer wechatId, Integer id) {
        Conversation record = new Conversation();
        record.setId(id);
        record.setWechatId(wechatId);
        return conversationMapper.selectOne(record);
    }

    @Override
    public Page<ConversationDto> searchMass(Integer wechatId, ConversationModel conversationModel, boolean queryCount) {
        if (conversationModel == null) {
            conversationModel = new ConversationModel();
        }
        if (conversationModel.pagable()) {
            PageHelper.startPage(conversationModel.getPageNum(), conversationModel.getPageSize(), queryCount);
        }
        Page<ConversationDto> conversationDtos = conversationMapper.searchMass(wechatId, DateUtil.getDateBegin(conversationModel.getStartAt()), DateUtil.getDateEnd(conversationModel.getEndAt()), conversationModel.getStatus(), conversationModel.getSortName(), conversationModel.getSortDir());
        return conversationDtos;
    }

    @Override
    public Integer countMass(Integer wechatId, ConversationModel conversationModel) {
        return conversationMapper.countMass(wechatId, conversationModel.getStartAt(), conversationModel.getEndAt(), conversationModel.getStatus());
    }

    @Override
    public Integer countMassAvalible(Integer wechatId) {
        Date current = new Date();
        Date startAt = DateUtil.getDateFirstDayOfMonth(current);
        Date endAt = DateUtil.getDateFirstDayOfNextMonth(current);
        return Constants.MAX_MASS_SIZE_PER_MONTH - conversationMapper.countMass(wechatId, startAt, endAt, MassConversationResultStatus.SEND_SUCCESS.getValue());
    }

    @Override
    public void preMassConversation(Integer wechatId, User user, MassConversationModel massConversationModel) {
        if (massConversationModel == null) {
            massConversationModel = new MassConversationModel();
        }
        if (!massConversationModel.emptyQuery()) {
            Long massMemberSize = preMassGetMembers(wechatId, massConversationModel);
            if (null == massMemberSize || massMemberSize.longValue() == 0) {
                if (massConversationModel.getIsForce() != null && massConversationModel.getIsForce()) {
                    throw new WechatException(Message.MEMBER_SELECTED_ALL_OFFLINE);
                }
                throw new WechatException(Message.MEMBER_NOT_BLANK);
            }
        }

        MsgType msgType = null;
        Date current = new Date();
        Material material = null;
        List<ConversationImageTextDetail> conversationImageTextDetails = new ArrayList<ConversationImageTextDetail>();
        if (massConversationModel.getMaterialId() != null) {
            material = materialService.getMaterial(wechatId, massConversationModel.getMaterialId());
            notBlank(material, Message.MATERIAL_NOT_EXIST);
            if (material.getMaterialType() == MaterialType.IMAGE_TEXT.getValue()) {
                msgType = MsgType.MPNEWS;
                // 非强推则检查图文是否有效
                if (massConversationModel.getIsForce() != null && massConversationModel.getIsForce()) {

                } else {
                    materialService.checkMaterialInvalidInWeiXin(wechatId, material.getMediaId());
                }

            } else if (material.getMaterialType() == MaterialType.IMAGE.getValue()) {
                msgType = MsgType.IMAGE;
                // 非强推则检查图文是否有效
                if (massConversationModel.getIsForce() != null && massConversationModel.getIsForce()) {

                } else {
                    materialService.checkMaterialInvalidInWeiXin(wechatId, material.getMediaId());
                }
                massConversationModel.setContent(material.getPicUrl());
            } else if (material.getMaterialType() == MaterialType.TEXT.getValue()) {
                MaterialTextDetail materialTextDetail = new MaterialTextDetail();
                materialTextDetail.setMaterialId(material.getId());
                materialTextDetail.setWechatId(material.getWechatId());
                materialTextDetail = materialTextDetailMapper.selectOne(materialTextDetail);
                notBlank(materialTextDetail, Message.MATERIAL_TEXT_DETAIL_NOT_EXIST);

                msgType = MsgType.TEXT;
                massConversationModel.setContent(materialTextDetail.getContent());
            } else {
                throw new WechatException(Message.CONVERSATION_CONTENT_NOT_SUPPORT);
            }
        } else if (massConversationModel.getNewid() != null) {
            if ("dcrm".equals(massConversationModel.getNewtype())) {
                msgType = MsgType.DCRMNEWS;
                DcrmImageTextDetailDto detail = dcrmImageTextDetailService.queryObject(massConversationModel.getNewid());
                material = new Material();
                material.setId(detail.getMaterialId());
            } else {
                msgType = MsgType.WECHATNEWS;
                MaterialImageTextDetail detail = materialImageTextDetailService.selectByKey(massConversationModel.getNewid());
                material = new Material();
                material.setId(detail.getMaterialId());
            }
        } else {
            MaterialTextDetail materialTextDetail = new MaterialTextDetail();
            materialTextDetail.setContent(massConversationModel.getContent());
            materialTextDetail.setWechatId(wechatId);
            List<MaterialTextDetail> materialTextDetails = materialTextDetailMapper.select(materialTextDetail);
            if (!materialTextDetails.isEmpty()) {
                materialTextDetail = materialTextDetails.get(0);
            }
            if (materialTextDetail == null || materialTextDetail.getMaterialId() == null) {
                material = new Material();
                material.setCreatedAt(current);
                if (user != null) {
                    material.setCreatorId(user.getId());
                }
                material.setMaterialType(MaterialType.TEXT.getValue());
                material.setStatus(MaterialStatus.INUSED.getValue());
                material.setWechatId(wechatId);
                materialService.save(material);

                materialTextDetail = new MaterialTextDetail();
                materialTextDetail.setContent(massConversationModel.getContent());
                materialTextDetail.setMaterialId(material.getId());
                materialTextDetail.setWechatId(wechatId);
                materialTextDetailMapper.insert(materialTextDetail);
            } else {
                material = materialService.getMaterial(wechatId, materialTextDetail.getMaterialId());
            }
            msgType = MsgType.TEXT;
        }

        Conversation conversation = new Conversation();
        conversation.setContent(massConversationModel.getContent());
        conversation.setCreatedAt(current);
        conversation.setDirection(true);
        conversation.setWechatId(wechatId);
        conversation.setUserId(user.getId());
        conversation.setKfPhoto(user.getLocalHeadImgUrl());
        conversation.setMsgType(msgType.getValue());
        conversation.setStatus(ConversationStatus.UNREAD.getValue());
        conversation.setIsMass(true);
        conversationMapper.insert(conversation);

        if (!conversationImageTextDetails.isEmpty()) {
            for (ConversationImageTextDetail citd : conversationImageTextDetails) {
                citd.setConversationId(conversation.getId());
            }
            conversationImageTextDetailMapper.insertList(conversationImageTextDetails);
        }

        MassConversationResult massConversationResult = new MassConversationResult();
        massConversationResult.setConditions(JSONObject.toJSONString(massConversationModel));
        massConversationResult.setConversationId(conversation.getId());
        massConversationResult.setCreatedAt(current);
        massConversationResult.setCreatorId(user.getId());
        massConversationResult.setWechatId(wechatId);
        massConversationResult.setMaterialId(material.getId());
        massConversationResult.setStatus(MassConversationResultStatus.WAIT_AUDIT.getValue());
        massConversationResult.setMsgType(msgType.getValue());
        massConversationResultMapper.insert(massConversationResult);
    }

    @Override
    public void auditMassConversation(Integer wechatId, User user, MassConversationModel massConversationModel) {
        MassConversationResultStatus status = MassConversationResultStatus.getByName(massConversationModel.getStatus());
        IllegalArgumentUtil.notBlank(status, Message.CONVERSATION_STATUS_INVALID);
        if (status != MassConversationResultStatus.AUDIT_NOT_PASS && status != MassConversationResultStatus.AUDIT_PASS) {
            throw new WechatException(Message.CONVERSATION_STATUS_INVALID);
        }
        MassConversationResult massConversationResult = new MassConversationResult();
        massConversationResult.setWechatId(wechatId);
        massConversationResult.setId(massConversationModel.getId());
        massConversationResult = massConversationResultMapper.selectOne(massConversationResult);
        massConversationResult.setStatus(status.getValue());
        IllegalArgumentUtil.notBlank(massConversationResult, Message.CONVERSATION_MASS_NOT_EXIST);
        massConversationResult.setStatus(status.getValue());
        massConversationResult.setAuditReason(massConversationModel.getReason());
        massConversationResult.setAuditAt(new Date());
        massConversationResult.setAuditBy(user.getId());
        massConversationResultMapper.updateByPrimaryKey(massConversationResult);
    }

    /**
     * 群发消息功能,支持立即发送,以及定时发送
     *
     * @param wechatId
     * @param user
     * @param massConversationModel
     */
    @Override
    public void sendMassConversation(Integer wechatId, User user, MassConversationModel massConversationModel) {
        long start = System.currentTimeMillis();
        log.info("start sendMassConversation >>> " + massConversationModel == null ? "" : massConversationModel.toString());
        if (massConversationModel == null) {
            massConversationModel = new MassConversationModel();
        }
        Date current = new Date();
        Date runAt = null;
        try {
            runAt = DateUtil.parse(massConversationModel.getRunAt());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (runAt != null && runAt.compareTo(new Date()) <= 0) {
            throw new WechatException(Message.CONVERSATION_MASS_RUN_AT_MUST_BE_GE_NOW);
        }
        MassConversationResult massConversationResult = new MassConversationResult();
        massConversationResult.setWechatId(wechatId);
        massConversationResult.setId(massConversationModel.getId());
        massConversationResult.setStatus(MassConversationResultStatus.getByName(massConversationModel.getStatus()).getValue());
        massConversationResult = massConversationResultMapper.selectOne(massConversationResult);
        log.info("massConversationResult-wechatId-id"+wechatId+massConversationResult.getId());

        IllegalArgumentUtil.notBlank(massConversationResult, Message.CONVERSATION_MASS_NOT_EXIST);
        MassConversationModel condition = JSONObject.parseObject(massConversationResult.getConditions(), MassConversationModel.class);

        Byte materialType = null;
        Material material = null;
        if (MsgType.DCRMNEWS.getValue() == massConversationResult.getMsgType() || MsgType.WECHATNEWS.getValue() == massConversationResult.getMsgType()) {
            materialType = massConversationResult.getMsgType();
        } else {
            if (massConversationResult.getMaterialId() == null) {
                throw new WechatException(Message.CONVERSATION_CONTENT_NOT_BLANK);
            }
            material = materialService.getMaterial(wechatId, massConversationResult.getMaterialId());
            notBlank(material, Message.MATERIAL_NOT_EXIST);
            materialType = material.getMaterialType();
            if (materialType != MaterialType.IMAGE_TEXT.getValue() && materialType != MaterialType.IMAGE.getValue() && materialType != MaterialType.TEXT.getValue()) {
                throw new WechatException(Message.CONVERSATION_CONTENT_NOT_SUPPORT);
            }
            if (materialType != MaterialType.TEXT.getValue()) {
                if (StringUtils.isBlank(material.getMediaId())) {
                    throw new WechatException(Message.MATERIAL_NOT_PUSH_TO_WX);
                }
                materialService.checkMaterialInvalidInWeiXin(wechatId, material.getMediaId());
            }
        }

        if (runAt == null) {
            String message = "";
            MsgType msgType = null;
            List<ConversationImageTextDetail> conversationImageTextDetails = new ArrayList<ConversationImageTextDetail>();
            ConversationImageTextDetail conversationImageTextDetail = null;
            if (materialType == MaterialType.IMAGE_TEXT.getValue()) {
                log.info("materialType..." + MaterialType.IMAGE_TEXT.toString());
                MaterialDto materialDto = materialService.getImageText(wechatId, massConversationResult.getMaterialId(), false);
                List<ImageTextDto> items = materialDto.getItems();
                JSONArray itemArray = new JSONArray();
                JSONObject itemJson = null;
                for (ImageTextDto imageTextDto : items) {
                    conversationImageTextDetail = new ConversationImageTextDetail.Builder().build();
                    conversationImageTextDetail.setWechatId(imageTextDto.getWechatId());
                    conversationImageTextDetail.setAuthor(imageTextDto.getAuthor());
                    conversationImageTextDetail.setContent(imageTextDto.getContent());
                    conversationImageTextDetail.setContentSourceChecked(imageTextDto.getContentSourceChecked());
                    conversationImageTextDetail.setContentSourceUrl(imageTextDto.getContentSourceUrl());
                    conversationImageTextDetail.setShowCover(imageTextDto.isShowCover());
                    conversationImageTextDetail.setSummary(imageTextDto.getSummary());
                    conversationImageTextDetail.setTitle(imageTextDto.getTitle());
                    conversationImageTextDetail.setMaterialCoverUrl(imageTextDto.getMaterialCoverUrl());
                    conversationImageTextDetails.add(conversationImageTextDetail);
                    itemJson = new JSONObject();
                    itemJson.put("id", imageTextDto.getId());
                    itemJson.put("materialId", materialDto.getId());
                    itemJson.put("title", imageTextDto.getTitle());
                    itemJson.put("summary", imageTextDto.getSummary());
                    itemJson.put("materialCoverUrl", imageTextDto.getMaterialCoverUrl());
                    itemArray.add(itemJson);
                }
                massConversationModel.setContent(itemArray.toJSONString());
                msgType = MsgType.MPNEWS;
                message = material.getMediaId();
            } else if (materialType == MaterialType.IMAGE.getValue()) {
                log.info("materialType..." + MaterialType.IMAGE.toString());
                msgType = MsgType.IMAGE;
                message = material.getMediaId();
                massConversationModel.setContent(material.getPicUrl());
            } else if (materialType == MaterialType.TEXT.getValue()) {
                log.info("materialType..." + MaterialType.TEXT.toString());
                MaterialTextDetail materialTextDetail = new MaterialTextDetail();
                materialTextDetail.setMaterialId(material.getId());
                materialTextDetail.setWechatId(material.getWechatId());
                materialTextDetail = materialTextDetailMapper.selectOne(materialTextDetail);
                notBlank(materialTextDetail, Message.MATERIAL_TEXT_DETAIL_NOT_EXIST);

                msgType = MsgType.TEXT;
                message = materialTextDetail.getContent();
                massConversationModel.setContent(materialTextDetail.getContent());
            } else if (materialType == MaterialType.DCRMNEWS.getValue()) {
                log.info("materialType..." + MaterialType.DCRMNEWS.toString());
                Integer id = condition.getNewid();
                DcrmImageTextDetailDto dto = dcrmImageTextDetailService.queryObject(id);
                ConversationImageTextDetail details = new ConversationImageTextDetail.Builder()
                        .title(dto.getTitle()).content(dto.getContent()).contentSourceUrl(dto.getLink()).materialCoverUrl(dto.getCoverPicUrl())
                        .summary(dto.getSummary()).showCover(false).contentSourceChecked(false).author("").wechatId(wechatId).build();
                Material materialR = materialService.getMaterial(wechatId, dto.getMaterialCoverId());
                material = materialR;
                JSONArray itemArray = new JSONArray();
                JSONObject itemJson = new JSONObject();
                itemJson.put("id", dto.getId());
                itemJson.put("materialId", dto.getId());
                itemJson.put("title", dto.getTitle());
                itemJson.put("summary", dto.getSummary());
                itemJson.put("materialCoverUrl", dto.getCoverPicUrl());
                itemArray.add(itemJson);
                massConversationModel.setContent(itemArray.toJSONString());
                msgType = MsgType.DCRMNEWS;
                conversationImageTextDetails.add(details);
            } else if (materialType == MaterialType.WECHATNEWS.getValue()) {
                log.info("materialType..." + MaterialType.WECHATNEWS.toString());
                Integer id = condition.getNewid();
                MaterialImageTextDetail dto = materialImageTextDetailService.selectByKey(id);
                Material materialR = materialService.getMaterial(wechatId, dto.getMaterialCoverId());
                material = materialR;
                ConversationImageTextDetail details = new ConversationImageTextDetail.Builder()
                        .title(dto.getTitle()).content(dto.getContent()).contentSourceUrl(dto.getContentSourceUrl()).materialCoverUrl(materialR.getPicUrl())
                        .summary(dto.getSummary()).showCover(dto.getShowCover()).contentSourceChecked(dto.getContentSourceChecked()).author("").wechatId(wechatId).build();

                JSONArray itemArray = new JSONArray();
                JSONObject itemJson = new JSONObject();
                itemJson.put("id", dto.getId());
                itemJson.put("materialId", dto.getId());
                itemJson.put("title", dto.getTitle());
                itemJson.put("summary", dto.getSummary());
                itemJson.put("materialCoverUrl", materialR.getPicUrl());
                itemArray.add(itemJson);
                massConversationModel.setContent(itemArray.toJSONString());
                msgType = MsgType.WECHATNEWS;
                conversationImageTextDetails.add(details);
            } else {
                throw new WechatException(Message.CONVERSATION_CONTENT_NOT_SUPPORT);
            }

            Conversation conversation = get(wechatId, massConversationResult.getConversationId());
            conversation.setContent(massConversationModel.getContent());
            if (user != null) {
                conversation.setUserId(user.getId());
                conversation.setKfPhoto(user.getLocalHeadImgUrl());
            }
            conversation.setPicUrl(material.getPicUrl());
            conversationMapper.updateByPrimaryKey(conversation);

            if (!conversationImageTextDetails.isEmpty()) {
                for (ConversationImageTextDetail citd : conversationImageTextDetails) {
                    citd.setConversationId(conversation.getId());
                }
                conversationImageTextDetailMapper.insertList(conversationImageTextDetails);
            }

            // push message by KeFu interface
            if (condition.getIsForce() != null && condition.getIsForce()) {
                log.info("start mass conversation with force {}!", massConversationResult.getId());
                pushMessage(wechatId, massConversationResult, msgType, message, current, condition, material, conversationImageTextDetails);
                return;
            }

            // send to all by WX
            if (condition.emptyQuery()) {
                log.info("start mass conversation with is_to_all {}!", massConversationResult.getId());
                sendToAllByWx(wechatId, massConversationResult, msgType, message, current, condition, material);
                return;
            }
            log.info("start mass conversation with send by wx {}!", massConversationResult.getId());

            massConversationResult.setStatus(MassConversationResultStatus.GROUPING.getValue());
            massConversationResultMapper.updateByPrimaryKey(massConversationResult);
            asynSendMasMessage(wechatId, massConversationResult, msgType, message, current, condition, user);

        } else {
            massConversationResult.setStatus(MassConversationResultStatus.WAIT_SEND.getValue());
            massConversationResult.setRunAt(runAt);
            massConversationResultMapper.updateByPrimaryKey(massConversationResult);

            try {
                Map<String, Object> jobMap = new HashMap<String, Object>();
                jobMap.put("jobGroup", 1);
                jobMap.put("jobDesc", "MassConversation_" + massConversationModel.getId());
                jobMap.put("jobCron", DateUtil.cron.format(runAt));
                jobMap.put("executorHandler", "massConversationJob");
                jobMap.put("executorParam", "-d" + TenantContext.getCurrentTenant() + "," + wechatId + "," + massConversationModel.getId());

                ReturnT<String> returnT = schedulerRestService.addJob(jobMap);
                if (ReturnT.FAIL_CODE == returnT.getCode()) {
                    throw new WechatException(Message.CONVERSATION_MASS_ADD_JOB_ERROR);
                }
            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
                throw new WechatException(Message.CONVERSATION_MASS_ADD_JOB_ERROR);
            }
        }
        log.info("end sendMassConversation >>> " + (System.currentTimeMillis() - start) / 1000);
    }

    /**
     * 给微信粉丝发送消息,发给所有用户,没有条件筛选,所以强制添加is_to_all参数
     *
     * @param wechatId
     * @param massConversationResult
     * @param msgType
     * @param wxMassMessage
     * @param current
     * @param condition
     * @param material
     */
    // @Async("callerRunsExecutor")
    private void sendToAllByWx(Integer wechatId, MassConversationResult massConversationResult, MsgType msgType, String wxMassMessage, Date current, MassConversationModel condition, Material material) {
        WxMessage wxMessage = WechatClientDelegate.sendToAll(wechatId, msgType.toString().toLowerCase(), wxMassMessage);
        log.info("sendMessageResponse : {}", wxMessage);
        if (wxMessage.fail()) {
            throw new WechatException(Message.SYSTEM_ERROR);
        }
        massConversationResult.setSendAt(current);
        massConversationResult.setMsgId(wxMessage.getMsgId());
        massConversationResult.setMsgDataId(wxMessage.getDataId());
        massConversationResult.setErrcode(wxMessage.getErrcode() + "");
        massConversationResult.setErrmsg(wxMessage.getErrmsg());
        massConversationResult.setStatus(MassConversationResultStatus.SENDING.getValue());
        massConversationResult.setMsgType(msgType.getValue());
        massConversationResultMapper.updateByPrimaryKey(massConversationResult);
    }

    /**
     * 强推消息逻辑
     *
     * @param wechatId
     * @param massConversationResult
     * @param msgType
     * @param wxMassMessage
     * @param current
     * @param condition
     * @param material
     */
    // @Async("callerRunsExecutor")
    private void pushMessage(Integer wechatId, MassConversationResult massConversationResult, MsgType msgType, String wxMassMessage, Date current, MassConversationModel condition, Material material, List<ConversationImageTextDetail> conversationImageTextDetails) {
        List<MemberDto> members = null;
        if (condition.getMemberIds() == null || condition.getMemberIds().length == 0) {
            MemberModel memberModel = condition.getMemberModel();
            members = memberMapper.search(wechatId, memberModel.getOpenId(), memberModel.getNickname(), memberModel.getSex(), memberModel.getCountry(), memberModel.getProvince(), memberModel.getCity(), Objects.nonNull(memberModel.getSubscribe()) ? (memberModel.getSubscribe() ? 1 : 0) : null, memberModel.getActivityStartAt(), memberModel.getActivityEndAt(), memberModel.getBatchSendOfMonthStartAt(), memberModel.getBatchSendOfMonthEndAt(), DateUtil.getDateBegin(DateUtil.parse(memberModel.getAttentionStartAt())), DateUtil.getDateEnd(DateUtil.parse(memberModel.getAttentionEndAt())), DateUtil.getDateBegin(DateUtil.parse(memberModel.getCancelSubscribeStartAt())), DateUtil.getDateEnd(DateUtil.parse(memberModel.getCancelSubscribeEndAt())), true, null, memberModel.getMobile(), memberModel.getMemberTags(), memberModel.getEncludeMemberTags(),null, null, null, DateUtil.getDate(-2), null);
        } else {
            members = memberMapper.selectByMemberId(condition.getMemberIds(), wechatId, true);
        }

        if (members.isEmpty()) {
            throw new WechatException(Message.MEMBER_NOT_BLANK);
        }

        log.info("start mass conversation by force!");
        MemberModel memberModel = condition.getMemberModel();
        long totalCount = 0;
        long filterCount = 0;
        int sendCount = 0;
        int errorCount = 0;
        if (condition.getMemberIds() != null && condition.getMemberIds().length > 0) {
            totalCount = condition.getMemberIds().length;
            filterCount = members.size();
        } else {
            Long totalMemberCount = memberMapper.count(wechatId, memberModel.getOpenId(), memberModel.getNickname(), memberModel.getSex(), memberModel.getCountry(), memberModel.getProvince(), memberModel.getCity(), Objects.nonNull(memberModel.getSubscribe()) ? (memberModel.getSubscribe() ? 1 : 0) : null, memberModel.getActivityStartAt(), memberModel.getActivityEndAt(), memberModel.getBatchSendOfMonthStartAt(), memberModel.getBatchSendOfMonthEndAt(), DateUtil.getDateBegin(DateUtil.parse(memberModel.getAttentionStartAt())), DateUtil.getDateEnd(DateUtil.parse(memberModel.getAttentionEndAt())), DateUtil.getDateBegin(DateUtil.parse(memberModel.getCancelSubscribeStartAt())), DateUtil.getDateEnd(DateUtil.parse(memberModel.getCancelSubscribeEndAt())), true, null, memberModel.getMobile(), memberModel.getMemberTags(),memberModel.getEncludeMemberTags(), null, null, null, DateUtil.getDate(-2), null);
            if (null == totalMemberCount) {
                totalCount = 0;
            } else {
                totalCount = totalMemberCount.longValue();
            }
            filterCount = totalCount;
        }

        for (MemberDto dto : members) {
            boolean success = false;
            if (MsgType.DCRMNEWS.getValue() == msgType.getValue() || MsgType.WECHATNEWS.getValue() == msgType.getValue()) {
                try {
                    CommonUtils.send2SocialWechatCoreApi(wechatId, dto, condition.getNewid(), condition.getNewtype(), conversationService);
                    success = true;
                } catch (Exception e) {
                    success = false;
                    log.error(e.getMessage(), e);
                }
            } else {
                WxMessage wxMessage = WechatClientDelegate.sendCustomMessage(wechatId, dto.getOpenId(), msgType.toString().toLowerCase(), wxMassMessage);
                success = wxMessage.success();
            }
            if (success) {
                sendCount++;
                recordForceMsg(dto, msgType, condition, wechatId);
            } else {
                errorCount++;
            }
        }

        massConversationResult.setTotalCount((int) totalCount);
        massConversationResult.setFilterCount((int) filterCount);
        massConversationResult.setSendCount(sendCount);
        massConversationResult.setErrorCount(errorCount);
        massConversationResult.setSendAt(current);
        massConversationResult.setStatus(MassConversationResultStatus.SEND_SUCCESS.getValue());
        massConversationResult.setMsgType(msgType.getValue());
        massConversationResultMapper.updateByPrimaryKey(massConversationResult);
    }

    /**
     * 异步根据条件群发消息
     *
     * @param wechatId
     * @param massConversationResult
     * @param msgType
     * @param wxMassMessage
     * @param current
     * @param condition
     * @param user
     */
    // @Async("callerRunsExecutor")
    private void asynSendMasMessage(Integer wechatId, MassConversationResult massConversationResult, MsgType msgType, String wxMassMessage, Date current, MassConversationModel condition, User user) {
        log.info("start sendMassMessage massConversationResult : {}!", massConversationResult.getId());
        Conversation conversation = get(wechatId, massConversationResult.getConversationId());
        WxMessage wxMessage = null;
        int batchIndex = 1;
        if (null != condition.getMemberIds() && condition.getMemberIds().length > 0) {
            List<MemberDto> list = memberMapper.selectByMemberId(condition.getMemberIds(), wechatId, condition.getIsForce());
            List<MassConversation> massConversations = new ArrayList<MassConversation>();
            MassConversation massConversation = null;
            for (MemberDto memberDto : list) {
                massConversation = new MassConversation();
                massConversation.setConversationId(conversation.getId());
                massConversation.setCreatedAt(current);
                if (user != null) {
                    massConversation.setCreatorId(user.getId());
                }
                massConversation.setMemberId(memberDto.getId());
                massConversation.setWechatId(wechatId);
                massConversation.setStatus(ConversationStatus.UNREAD.getValue());
                massConversations.add(massConversation);
            }
            // batch insert into DB
            insertConversationListToDB(massConversations);

            wxMessage = WechatClientDelegate.sendMessage(wechatId, getOpenIds(list), msgType.toString().toLowerCase(), wxMassMessage);
            log.info("sendMessageResponse : {}", wxMessage);
            if (wxMessage.fail()) {
                throw new WechatException(Message.SYSTEM_ERROR);
            }
            try {
                List<Integer> ids = list.stream().map(MemberDto::getId).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(ids)) {
                    memberMapper.updateBatchSendMonth(ids);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } else {
            log.debug("start sendMassMessage by Query");
            String batchSizeStr = configService.getConfigValue(wechatId, "MASS_CONVERSATION", "BATCH_SIZE");

            int batchSize = 10000;// 默认批大小为10000
            if (StringUtils.isNotBlank(batchSizeStr)) {
                batchSize = Integer.parseInt(batchSizeStr);
            }

            for (; ; batchIndex++) {
                PageHelper.startPage(batchIndex, batchSize, false);
                MemberModel memberModel = condition.getMemberModel();
                Page<MemberDto> list = memberMapper.massMembersSearch(wechatId, memberModel.getOpenId(), memberModel.getNickname(), memberModel.getSex(), memberModel.getCountry(), memberModel.getProvince(), memberModel.getCity(), memberModel.getSubscribe(), memberModel.getActivityStartAt(), memberModel.getActivityEndAt(), memberModel.getBatchSendOfMonthStartAt(), memberModel.getBatchSendOfMonthEndAt(), DateUtil.getDateBegin(DateUtil.parse(memberModel.getAttentionStartAt())), DateUtil.getDateEnd(DateUtil.parse(memberModel.getAttentionEndAt())), DateUtil.getDateBegin(DateUtil.parse(memberModel.getCancelSubscribeStartAt())), DateUtil.getDateEnd(DateUtil.parse(memberModel.getCancelSubscribeEndAt())), memberModel.getIsOnline(), null, memberModel.getMobile(), memberModel.getMemberTags(), memberModel.getEncludeMemberTags(),condition.getSortName(), condition.getSortDir(), condition.getBindStatus());

                if (list == null || list.size() == 0) {
                    break;
                }

                List<MassConversation> massConversations = new ArrayList<MassConversation>();
                MassConversation massConversation = null;
                for (MemberDto memberDto : list) {
                    massConversation = new MassConversation();
                    massConversation.setConversationId(conversation.getId());
                    massConversation.setCreatedAt(current);
                    if (user != null) {
                        massConversation.setCreatorId(user.getId());
                    }
                    massConversation.setMemberId(memberDto.getId());
                    massConversation.setWechatId(wechatId);
                    massConversation.setStatus(ConversationStatus.UNREAD.getValue());
                    massConversation.setPiCi(batchIndex);
                    massConversations.add(massConversation);
                }
                // batch insert into DB
                insertConversationListToDB(massConversations);
                // 分批插入群发数据
                massConversationBatchResultService.batchSendMassMsg(batchIndex, wechatId, list, conversation.getId(), msgType, wxMassMessage);
            }
            try {
                Map<String, Object> jobMap = new HashMap<String, Object>();
                jobMap.put("jobGroup", 1);
                jobMap.put("jobDesc", "BatchMassConversation_" + conversation.getId());
                jobMap.put("jobCron", DateUtil.cron.format(DateUtils.addMinutes(new Date(), 1)));
                jobMap.put("executorHandler", "batchMassConversationJob");
                jobMap.put("executorParam", "-d" + TenantContext.getCurrentTenant() + "," + wechatId + "," + conversation.getId());

                ReturnT<String> returnT = schedulerRestService.addJob(jobMap);
                if (ReturnT.FAIL_CODE == returnT.getCode()) {
                    log.error("add job error:" + returnT.getCode() + "," + returnT.getMsg());
                    throw new WechatException(Message.CONVERSATION_MASS_ADD_JOB_ERROR);
                }
            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
                throw new WechatException(Message.CONVERSATION_MASS_ADD_JOB_ERROR);
            }
        }

        massConversationResult.setSendAt(current);
        massConversationResult.setMsgId(wxMessage != null ? wxMessage.getMsgId() : null);
        massConversationResult.setMsgDataId(wxMessage != null ? wxMessage.getDataId() : null);
        massConversationResult.setErrcode(wxMessage != null ? wxMessage.getErrcode() + "" : null);
        massConversationResult.setErrmsg(wxMessage != null ? wxMessage.getErrmsg() : null);
        massConversationResult.setStatus(MassConversationResultStatus.SENDING.getValue());
        massConversationResult.setMsgType(msgType.getValue());
        massConversationResult.setTotalBatch(batchIndex - 1);
        massConversationResultMapper.updateByPrimaryKey(massConversationResult);
        log.info("end sendMassMessage massConversationResult!");
    }

    /**
     * 异步插入用户任务会话
     *
     * @param massConversations
     */
    // @Async("callerRunsExecutor")
    private void insertConversationListToDB(List<MassConversation> massConversations) {
        massConversationMapper.insertList(massConversations);
    }

    private void recordForceMsg(MemberDto dto, MsgType msgType, MassConversationModel massConversationModel, Integer wechatId) {
        Conversation record = new Conversation();
        Date current = new Date();
        record.setCreatedAt(current);
        record.setMsgType(msgType.getValue());
        record.setWechatId(wechatId);
        record.setStatus(ConversationStatus.UNREAD.getValue());
        record.setDirection(true);
        record.setMemberId(dto.getId());
        record.setMemberPhoto(dto.getLocalHeadImgUrl());
        record.setIsMass(false);
        record.setContent(massConversationModel.getContent());
        conversationMapper.insert(record);

    }

    private List<String> getOpenIds(List<MemberDto> members) {
        List<String> openIdList = new LinkedList<>();
        for (MemberDto member : members) {
            openIdList.add(member.getOpenId());
        }
        return openIdList;
    }

    /**
     * 用于检查预审核时符合条件的用户数量
     *
     * @param wechatId
     * @param massConversationModel
     * @return
     */
    private Long preMassGetMembers(Integer wechatId, MassConversationModel massConversationModel) {

        Long size = 0l;
        // 先判断是否是发送给选中的用户
        if (null != massConversationModel.getMemberIds() && massConversationModel.getMemberIds().length > 0) {
            if (massConversationModel.getIsForce() != null && massConversationModel.getIsForce()) {
                size = memberMapper.countByMemberId(massConversationModel.getMemberIds(), wechatId, true);
            } else {
                size = memberMapper.countByMemberId(massConversationModel.getMemberIds(), wechatId, null);

            }

        } else {
            // 如果是强推,则强制设置在线状态为true的用户
            if (massConversationModel.getIsForce() != null && massConversationModel.getIsForce()) {
                massConversationModel.setIsOnline(true);
            }
            MemberModel memberModel = massConversationModel.getMemberModel();
            size = memberMapper.count(wechatId, memberModel.getOpenId(), memberModel.getNickname(), memberModel.getSex(), memberModel.getCountry(), memberModel.getProvince(), memberModel.getCity(), Objects.nonNull(memberModel.getSubscribe()) ? (memberModel.getSubscribe() ? 1 : 0) : null, memberModel.getActivityStartAt(), memberModel.getActivityEndAt(), memberModel.getBatchSendOfMonthStartAt(), memberModel.getBatchSendOfMonthEndAt(), DateUtil.getDateBegin(DateUtil.parse(memberModel.getAttentionStartAt())), DateUtil.getDateEnd(DateUtil.parse(memberModel.getAttentionEndAt())), DateUtil.getDateBegin(DateUtil.parse(memberModel.getCancelSubscribeStartAt())), DateUtil.getDateEnd(DateUtil.parse(memberModel.getCancelSubscribeEndAt())), memberModel.getIsOnline(), null, memberModel.getMobile(), memberModel.getMemberTags(),memberModel.getEncludeMemberTags(), massConversationModel.getSortName(), massConversationModel.getSortDir(), massConversationModel.getBindStatus(), DateUtil.getDate(-2), null);
        }

        return size;
    }

    private List<ReportMessageItemDto> findDates4Message(Date begin, Date end) {
        List<ReportMessageItemDto> list = new ArrayList<ReportMessageItemDto>();
        ReportMessageItemDto dto = new ReportMessageItemDto();
        dto.setDate(DateUtil.formatYYYYMMDD(begin));
        list.add(dto);

        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(begin);
        calBegin.set(Calendar.HOUR_OF_DAY, 0);
        calBegin.set(Calendar.MINUTE, 0);
        calBegin.set(Calendar.SECOND, 0);
        calBegin.set(Calendar.MILLISECOND, 0);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        calEnd.set(Calendar.HOUR_OF_DAY, 0);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, 0);
        calEnd.set(Calendar.MILLISECOND, 0);
        while (calEnd.after(calBegin)) {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            dto = new ReportMessageItemDto();
            dto.setDate(DateUtil.formatYYYYMMDD(calBegin.getTime()));
            list.add(dto);
        }
        return list;
    }

    @Override
    public ReportMessageDto messageReport(Integer wechatId, Date start, Date end) {
        // TODO Auto-generated method stub

        ReportMessageDto dto = conversationMapper.messageCount(wechatId, start, end);
        List<ReportMessageItemDto> allList = findDates4Message(start, end);
        List<ReportMessageItemDto> list = conversationMapper.messageReport(wechatId, start, end);
        for (ReportMessageItemDto ddto : allList) {
            for (ReportMessageItemDto temp : list) {
                if (ddto.getDate().equals(temp.getDate())) {
                    ddto.setImage(temp.getImage());
                    ddto.setImagetext(temp.getImagetext());
                    ddto.setText(temp.getText());
                    ddto.setVideo(temp.getVideo());
                    ddto.setVoice(temp.getVoice());
                    ddto.setSendTimes(temp.getSendTimes());
                    ddto.setClick(temp.getClick());
                    ddto.setScan(temp.getScan());
                    ddto.setLink(temp.getLink());
                    ddto.setLocation(temp.getLocation());
                    ddto.setShortvideo(temp.getShortvideo());
                    ddto.setMusic(temp.getMusic());
                    break;
                }
            }
        }
        if (allList != null && allList.size() > 0) {
            dto.setList(allList);
        }
        if (dto.getReceiver() > 0) {
            dto.setPerCapita((double) dto.getSendTimes() / dto.getReceiver());
        }
        return dto;
    }

    @Override
    public ReportMessageDto hourMessageReport(Integer wechatId, Date startDate, Date endDate) {
        ReportMessageDto dto = conversationMapper.messageCount(wechatId, startDate, endDate);
        if (dto.getReceiver() > 0) {
            dto.setPerCapita((double) dto.getSendTimes() / dto.getReceiver());
        }
        List<ReportMessageItemDto> listDtos = conversationMapper.messageItemReport(wechatId, startDate, endDate);
        List<ReportMessageItemDto> list = new ArrayList<ReportMessageItemDto>();
        outter:
        for (int i = 1; i < 25; i++) {
            String date = i + ":00";
            for (ReportMessageItemDto rmid : listDtos) {
                if (rmid.getDate().equals(date)) {
                    list.add(rmid);
                    continue outter;
                }
            }

            ReportMessageItemDto record = getEmptyReportMessageItemDto(date);
            list.add(record);
        }
        dto.setList(list);
        return dto;
    }

    public ReportMessageItemDto getEmptyReportMessageItemDto(String date) {
        ReportMessageItemDto record = new ReportMessageItemDto();
        record.setDate(date);
        record.setSendTimes(0);
        record.setImage(0);
        record.setImagetext(0);
        record.setScan(0);
        record.setClick(0);
        record.setLink(0);
        record.setLocation(0);
        record.setVoice(0);
        record.setVideo(0);
        record.setText(0);
        record.setShortvideo(0);
        record.setMusic(0);
        return record;
    }

    @Override
    public List<ConversationDto> searchCustomerServiceConversation(Integer wechatId, Date startDate, Date endDate) {
        return conversationMapper.searchCustomerServiceConversation(wechatId, startDate, endDate);
    }

    @Override
    public Page<UserBehavior> selectUserBehavior(Integer wechatId, ConversationModel conversationModel) {
        return conversationMapper.selectUserBehavior(wechatId, conversationModel.getMemberId());
    }

    @Override
    public List<UserLocation> selectUserLocation(Integer wechatId, ConversationModel conversationModel) {
        List<UserLocation> userLocations = conversationMapper.selectUserLocation(wechatId, conversationModel.getMemberId());
        return filtrateDate(userLocations);
    }

    @Override
    public void saveActivity(ConversationActivityModel conversationActivityModel, Integer wechatId) {
        MemberDto memberDto = memberMapper.selectByOpenId(conversationActivityModel.getOpenId(), wechatId);
        Conversation conversation=new Conversation();
        conversation.setEvent((byte)50);
        conversation.setOpenId(conversationActivityModel.getOpenId());
        conversation.setUnionId(conversationActivityModel.getUnionId());
        conversation.setEventName("ACTIVITY");
        conversation.setEventKey(conversationActivityModel.getEventKey());
        conversation.setMemberId(memberDto.getId());
        conversation.setCreatedAt(new Date());
        conversation.setTitle(conversationActivityModel.getTitle());
        conversation.setMsgType((byte)10);
        conversation.setWechatId(wechatId);
        conversation.setStatus((byte)0);
        conversation.setDirection(true);
        conversationMapper.insert(conversation);
    }

    private List<UserLocation> filtrateDate(List<UserLocation> userLocations) {
        List<UserLocation> result=new ArrayList<>();
        result.add(userLocations.get(0));
        Date fromDate=userLocations.get(0).getCreatedAt();
        for (int i=0;i<userLocations.size();i++){
            if(i+1<userLocations.size()){
                UserLocation userLocationTo = userLocations.get(i + 1);
                Date toDate=userLocationTo.getCreatedAt();
                long from = fromDate.getTime();
                long to = toDate.getTime();
                int hours = (int) ((to - from) / (1000 * 60 * 60));
                if(hours!=0){
                    fromDate=toDate;
                    result.add(userLocations.get(i + 1));
                }
            }else{
                UserLocation userLocationTo = userLocations.get(i);
                Date toDate=userLocationTo.getCreatedAt();
                long from = fromDate.getTime();
                long to = toDate.getTime();
                int hours = (int) ((to - from) / (1000 * 60 * 60));
                if(hours!=0){
                    result.add(userLocations.get(i));
                }
            }
        }
        return result;
    }

    @Autowired
    public RabbitTemplate rabbitTemplate;

    private static ObjectMapper om = new ObjectMapper();

    @Override
    public void send2SocialWechatCoreApi(RabbitmqTable table, RabbitmqMethod method, Object obj) {
        try {
            String queueName = "social.wechat.core.api.conversation";
            String content = om.writeValueAsString(obj);
            Map<String, String> json = new HashMap<String, String>();
            json.put("table", table.getValue());
            json.put("method", method.getValue());
            json.put("content", content);
            String message = om.writeValueAsString(json);

            rabbitTemplate.setRoutingKey(queueName);
            rabbitTemplate.setQueue(queueName);
            rabbitTemplate.convertAndSend(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
