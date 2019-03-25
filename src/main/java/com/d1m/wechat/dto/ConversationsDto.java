package com.d1m.wechat.dto;

import java.util.Date;

public class ConversationsDto {
  /**
   * 主键ID
   */
  private Integer id;

  /**
   * 会员ID
   */
  private Integer memberId;

  /**
   * openId
   */
  private String openId;

  /**
   * unionId
   */
  private String unionId;

  /**
   * 客服ID(为空代表系统)
   */
  private Integer userId;

  /**
   * 素材ID
   */
  private Integer materialId;

  /**
   * 微信消息id
   */
  private String msgId;

  /**
   * 消息类型
   */
  private Byte msgType;

  /**
   * 会话时间
   */
  private Date createdAt;

  /**
   * 公众号ID
   */
  private Integer wechatId;

  /**
   * 状态(0:未回复,1:已回复)
   */
  private Byte status;

  /**
   * 原始xml会话ID
   */
  private Integer originalConversationId;

  /**
   * 事件类型
   */
  private Byte event;

  /**
   * 文本
   */
  private String content;

  /**
   * 图片链接
   */
  private String picUrl;

  /**
   * 音乐链接
   */
  private String musicUrl;

  /**
   * 语音url
   */
  private String voiceUrl;

  /**
   * 视频url
   */
  private String videoUrl;

  /**
   * 小视频url
   */
  private String shortVideoUrl;

  /**
   * 媒体id
   */
  private String mediaId;

  /**
   * 语音格式，如amr，speex等
   */
  private String format;

  /**
   * 语音识别结果
   */
  private String recognition;

  /**
   * 视频消息缩略图的媒体id
   */
  private Integer thumbMediaId;

  /**
   * 地理位置纬度
   */
  private Double locationX;

  /**
   * 地理位置经度
   */
  private Double locationY;

  /**
   * 地理位置精度
   */
  private Double locationPrecision;

  /**
   * 地图缩放大小
   */
  private Double scale;

  /**
   * 	地理位置信息
   */
  private String label;

  /**
   * 消息标题
   */
  private String title;

  /**
   * 消息描述
   */
  private String description;

  /**
   * 消息链接
   */
  private String url;

  /**
   * 事件KEY值
   */
  private String eventKey;

  /**
   * 点击菜单id
   */
  private Integer menuId;

  /**
   * 点击父级菜单id
   */
  private Integer menuParentId;

  /**
   * 点击菜单组id
   */
  private Integer menuGroupId;

  /**
   * 二维码的ticket
   */
  private String ticket;

  /**
   * 商户自己内部ID，即字段中的sid
   */
  private String uniqId;

  /**
   * 微信的门店ID，微信内门店唯一标示ID
   */
  private String poiId;

  /**
   * 审核结果，成功succ 或失败fail
   */
  private String result;

  /**
   * 会话方向(0:进,1:出)
   */
  private Boolean direction;

  /**
   * 会员头像
   */
  private String memberPhoto;

  /**
   * 客服头像
   */
  private String kfPhoto;

  /**
   * 是否是群发会话
   */
  private Boolean isMass;
  /**
   * 事件名称
   */
  private String event_name;
  /**
   * 事件来源
   */
  private String source;

  public Integer getId() {
    return id;
  }

  public Integer getMemberId() {
    return memberId;
  }

  public String getOpenId() {
    return openId;
  }

  public String getUnionId() {
    return unionId;
  }

  public Integer getUserId() {
    return userId;
  }

  public Integer getMaterialId() {
    return materialId;
  }

  public String getMsgId() {
    return msgId;
  }

  public Byte getMsgType() {
    return msgType;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Integer getWechatId() {
    return wechatId;
  }

  public Byte getStatus() {
    return status;
  }

  public Integer getOriginalConversationId() {
    return originalConversationId;
  }

  public Byte getEvent() {
    return event;
  }

  public String getContent() {
    return content;
  }

  public String getPicUrl() {
    return picUrl;
  }

  public String getMusicUrl() {
    return musicUrl;
  }

  public String getVoiceUrl() {
    return voiceUrl;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public String getShortVideoUrl() {
    return shortVideoUrl;
  }

  public String getMediaId() {
    return mediaId;
  }

  public String getFormat() {
    return format;
  }

  public String getRecognition() {
    return recognition;
  }

  public Integer getThumbMediaId() {
    return thumbMediaId;
  }

  public Double getLocationX() {
    return locationX;
  }

  public Double getLocationY() {
    return locationY;
  }

  public Double getLocationPrecision() {
    return locationPrecision;
  }

  public Double getScale() {
    return scale;
  }

  public String getLabel() {
    return label;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getUrl() {
    return url;
  }

  public String getEventKey() {
    return eventKey;
  }

  public Integer getMenuId() {
    return menuId;
  }

  public Integer getMenuParentId() {
    return menuParentId;
  }

  public Integer getMenuGroupId() {
    return menuGroupId;
  }

  public String getTicket() {
    return ticket;
  }

  public String getUniqId() {
    return uniqId;
  }

  public String getPoiId() {
    return poiId;
  }

  public String getResult() {
    return result;
  }

  public Boolean getDirection() {
    return direction;
  }

  public String getMemberPhoto() {
    return memberPhoto;
  }

  public String getKfPhoto() {
    return kfPhoto;
  }

  public Boolean getMass() {
    return isMass;
  }

  public String getEvent_name() {
    return event_name;
  }

  public String getSource() {
    return source;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setMemberId(Integer memberId) {
    this.memberId = memberId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public void setUnionId(String unionId) {
    this.unionId = unionId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public void setMaterialId(Integer materialId) {
    this.materialId = materialId;
  }

  public void setMsgId(String msgId) {
    this.msgId = msgId;
  }

  public void setMsgType(Byte msgType) {
    this.msgType = msgType;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setWechatId(Integer wechatId) {
    this.wechatId = wechatId;
  }

  public void setStatus(Byte status) {
    this.status = status;
  }

  public void setOriginalConversationId(Integer originalConversationId) {
    this.originalConversationId = originalConversationId;
  }

  public void setEvent(Byte event) {
    this.event = event;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setPicUrl(String picUrl) {
    this.picUrl = picUrl;
  }

  public void setMusicUrl(String musicUrl) {
    this.musicUrl = musicUrl;
  }

  public void setVoiceUrl(String voiceUrl) {
    this.voiceUrl = voiceUrl;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  public void setShortVideoUrl(String shortVideoUrl) {
    this.shortVideoUrl = shortVideoUrl;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public void setRecognition(String recognition) {
    this.recognition = recognition;
  }

  public void setThumbMediaId(Integer thumbMediaId) {
    this.thumbMediaId = thumbMediaId;
  }

  public void setLocationX(Double locationX) {
    this.locationX = locationX;
  }

  public void setLocationY(Double locationY) {
    this.locationY = locationY;
  }

  public void setLocationPrecision(Double locationPrecision) {
    this.locationPrecision = locationPrecision;
  }

  public void setScale(Double scale) {
    this.scale = scale;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setEventKey(String eventKey) {
    this.eventKey = eventKey;
  }

  public void setMenuId(Integer menuId) {
    this.menuId = menuId;
  }

  public void setMenuParentId(Integer menuParentId) {
    this.menuParentId = menuParentId;
  }

  public void setMenuGroupId(Integer menuGroupId) {
    this.menuGroupId = menuGroupId;
  }

  public void setTicket(String ticket) {
    this.ticket = ticket;
  }

  public void setUniqId(String uniqId) {
    this.uniqId = uniqId;
  }

  public void setPoiId(String poiId) {
    this.poiId = poiId;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public void setDirection(Boolean direction) {
    this.direction = direction;
  }

  public void setMemberPhoto(String memberPhoto) {
    this.memberPhoto = memberPhoto;
  }

  public void setKfPhoto(String kfPhoto) {
    this.kfPhoto = kfPhoto;
  }

  public void setMass(Boolean mass) {
    isMass = mass;
  }

  public void setEvent_name(String event_name) {
    this.event_name = event_name;
  }

  public void setSource(String source) {
    this.source = source;
  }
}
