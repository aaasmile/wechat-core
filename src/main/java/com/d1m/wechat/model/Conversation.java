package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

public class Conversation {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会员ID
     */
    @Column(name = "member_id")
    private Integer memberId;

    /**
     * openId
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * unionId
     */
    @Column(name = "union_id")
    private String unionId;

    /**
     * 客服ID(为空代表系统)
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 素材ID
     */
    @Column(name = "material_id")
    private Integer materialId;

    /**
     * 微信消息id
     */
    @Column(name = "msg_id")
    private String msgId;

    /**
     * 消息类型
     */
    @Column(name = "msg_type")
    private Byte msgType;

    /**
     * 会话时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 状态(0:未回复,1:已回复)
     */
    private Byte status;

    /**
     * 原始xml会话ID
     */
    @Column(name = "original_conversation_id")
    private Integer originalConversationId;

    /**
     * 事件类型
     */
    private Byte event;

    /**
     * 图片链接
     */
    @Column(name = "pic_url")
    private String picUrl;

    /**
     * 音乐链接
     */
    @Column(name = "music_url")
    private String musicUrl;

    /**
     * 语音url
     */
    @Column(name = "voice_url")
    private String voiceUrl;

    /**
     * 视频url
     */
    @Column(name = "video_url")
    private String videoUrl;

    /**
     * 小视频url
     */
    @Column(name = "short_video_url")
    private String shortVideoUrl;

    /**
     * 媒体id
     */
    @Column(name = "media_id")
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
    @Column(name = "thumb_media_id")
    private Integer thumbMediaId;

    /**
     * 地理位置纬度
     */
    @Column(name = "location_x")
    private Double locationX;

    /**
     * 地理位置经度
     */
    @Column(name = "location_y")
    private Double locationY;

    /**
     * 地理位置精度
     */
    @Column(name = "location_precision")
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
    @Column(name = "event_key")
    private String eventKey;

    /**
     * 点击菜单id
     */
    @Column(name = "menu_id")
    private Integer menuId;

    /**
     * 点击父级菜单id
     */
    @Column(name = "menu_parent_id")
    private Integer menuParentId;

    /**
     * 点击菜单组id
     */
    @Column(name = "menu_group_id")
    private Integer menuGroupId;

    /**
     * 二维码的ticket
     */
    private String ticket;

    /**
     * 商户自己内部ID，即字段中的sid
     */
    @Column(name = "uniq_id")
    private String uniqId;

    /**
     * 微信的门店ID，微信内门店唯一标示ID
     */
    @Column(name = "poi_id")
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
    @Column(name = "member_photo")
    private String memberPhoto;

    /**
     * 客服头像
     */
    @Column(name = "kf_photo")
    private String kfPhoto;

    /**
     * 是否是群发会话
     */
    @Column(name = "is_mass")
    private Boolean isMass;

    /**
     * 文本
     */
    private String content;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取会员ID
     *
     * @return member_id - 会员ID
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * 设置会员ID
     *
     * @param memberId 会员ID
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取openId
     *
     * @return open_id - openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置openId
     *
     * @param openId openId
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * 获取unionId
     *
     * @return union_id - unionId
     */
    public String getUnionId() {
        return unionId;
    }

    /**
     * 设置unionId
     *
     * @param unionId unionId
     */
    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }

    /**
     * 获取客服ID(为空代表系统)
     *
     * @return user_id - 客服ID(为空代表系统)
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置客服ID(为空代表系统)
     *
     * @param userId 客服ID(为空代表系统)
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取素材ID
     *
     * @return material_id - 素材ID
     */
    public Integer getMaterialId() {
        return materialId;
    }

    /**
     * 设置素材ID
     *
     * @param materialId 素材ID
     */
    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    /**
     * 获取微信消息id
     *
     * @return msg_id - 微信消息id
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * 设置微信消息id
     *
     * @param msgId 微信消息id
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    /**
     * 获取消息类型
     *
     * @return msg_type - 消息类型
     */
    public Byte getMsgType() {
        return msgType;
    }

    /**
     * 设置消息类型
     *
     * @param msgType 消息类型
     */
    public void setMsgType(Byte msgType) {
        this.msgType = msgType;
    }

    /**
     * 获取会话时间
     *
     * @return created_at - 会话时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置会话时间
     *
     * @param createdAt 会话时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取公众号ID
     *
     * @return wechat_id - 公众号ID
     */
    public Integer getWechatId() {
        return wechatId;
    }

    /**
     * 设置公众号ID
     *
     * @param wechatId 公众号ID
     */
    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    /**
     * 获取状态(0:未回复,1:已回复)
     *
     * @return status - 状态(0:未回复,1:已回复)
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态(0:未回复,1:已回复)
     *
     * @param status 状态(0:未回复,1:已回复)
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取原始xml会话ID
     *
     * @return original_conversation_id - 原始xml会话ID
     */
    public Integer getOriginalConversationId() {
        return originalConversationId;
    }

    /**
     * 设置原始xml会话ID
     *
     * @param originalConversationId 原始xml会话ID
     */
    public void setOriginalConversationId(Integer originalConversationId) {
        this.originalConversationId = originalConversationId;
    }

    /**
     * 获取事件类型
     *
     * @return event - 事件类型
     */
    public Byte getEvent() {
        return event;
    }

    /**
     * 设置事件类型
     *
     * @param event 事件类型
     */
    public void setEvent(Byte event) {
        this.event = event;
    }

    /**
     * 获取图片链接
     *
     * @return pic_url - 图片链接
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * 设置图片链接
     *
     * @param picUrl 图片链接
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    /**
     * 获取音乐链接
     *
     * @return music_url - 音乐链接
     */
    public String getMusicUrl() {
        return musicUrl;
    }

    /**
     * 设置音乐链接
     *
     * @param musicUrl 音乐链接
     */
    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl == null ? null : musicUrl.trim();
    }

    /**
     * 获取语音url
     *
     * @return voice_url - 语音url
     */
    public String getVoiceUrl() {
        return voiceUrl;
    }

    /**
     * 设置语音url
     *
     * @param voiceUrl 语音url
     */
    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl == null ? null : voiceUrl.trim();
    }

    /**
     * 获取视频url
     *
     * @return video_url - 视频url
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * 设置视频url
     *
     * @param videoUrl 视频url
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    /**
     * 获取小视频url
     *
     * @return short_video_url - 小视频url
     */
    public String getShortVideoUrl() {
        return shortVideoUrl;
    }

    /**
     * 设置小视频url
     *
     * @param shortVideoUrl 小视频url
     */
    public void setShortVideoUrl(String shortVideoUrl) {
        this.shortVideoUrl = shortVideoUrl == null ? null : shortVideoUrl.trim();
    }

    /**
     * 获取媒体id
     *
     * @return media_id - 媒体id
     */
    public String getMediaId() {
        return mediaId;
    }

    /**
     * 设置媒体id
     *
     * @param mediaId 媒体id
     */
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId == null ? null : mediaId.trim();
    }

    /**
     * 获取语音格式，如amr，speex等
     *
     * @return format - 语音格式，如amr，speex等
     */
    public String getFormat() {
        return format;
    }

    /**
     * 设置语音格式，如amr，speex等
     *
     * @param format 语音格式，如amr，speex等
     */
    public void setFormat(String format) {
        this.format = format == null ? null : format.trim();
    }

    /**
     * 获取语音识别结果
     *
     * @return recognition - 语音识别结果
     */
    public String getRecognition() {
        return recognition;
    }

    /**
     * 设置语音识别结果
     *
     * @param recognition 语音识别结果
     */
    public void setRecognition(String recognition) {
        this.recognition = recognition == null ? null : recognition.trim();
    }

    /**
     * 获取视频消息缩略图的媒体id
     *
     * @return thumb_media_id - 视频消息缩略图的媒体id
     */
    public Integer getThumbMediaId() {
        return thumbMediaId;
    }

    /**
     * 设置视频消息缩略图的媒体id
     *
     * @param thumbMediaId 视频消息缩略图的媒体id
     */
    public void setThumbMediaId(Integer thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    /**
     * 获取地理位置纬度
     *
     * @return location_x - 地理位置纬度
     */
    public Double getLocationX() {
        return locationX;
    }

    /**
     * 设置地理位置纬度
     *
     * @param locationX 地理位置纬度
     */
    public void setLocationX(Double locationX) {
        this.locationX = locationX;
    }

    /**
     * 获取地理位置经度
     *
     * @return location_y - 地理位置经度
     */
    public Double getLocationY() {
        return locationY;
    }

    /**
     * 设置地理位置经度
     *
     * @param locationY 地理位置经度
     */
    public void setLocationY(Double locationY) {
        this.locationY = locationY;
    }

    /**
     * 获取地理位置精度
     *
     * @return location_precision - 地理位置精度
     */
    public Double getLocationPrecision() {
        return locationPrecision;
    }

    /**
     * 设置地理位置精度
     *
     * @param locationPrecision 地理位置精度
     */
    public void setLocationPrecision(Double locationPrecision) {
        this.locationPrecision = locationPrecision;
    }

    /**
     * 获取地图缩放大小
     *
     * @return scale - 地图缩放大小
     */
    public Double getScale() {
        return scale;
    }

    /**
     * 设置地图缩放大小
     *
     * @param scale 地图缩放大小
     */
    public void setScale(Double scale) {
        this.scale = scale;
    }

    /**
     * 获取	地理位置信息
     *
     * @return label - 	地理位置信息
     */
    public String getLabel() {
        return label;
    }

    /**
     * 设置	地理位置信息
     *
     * @param label 	地理位置信息
     */
    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    /**
     * 获取消息标题
     *
     * @return title - 消息标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置消息标题
     *
     * @param title 消息标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取消息描述
     *
     * @return description - 消息描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置消息描述
     *
     * @param description 消息描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取消息链接
     *
     * @return url - 消息链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置消息链接
     *
     * @param url 消息链接
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取事件KEY值
     *
     * @return event_key - 事件KEY值
     */
    public String getEventKey() {
        return eventKey;
    }

    /**
     * 设置事件KEY值
     *
     * @param eventKey 事件KEY值
     */
    public void setEventKey(String eventKey) {
        this.eventKey = eventKey == null ? null : eventKey.trim();
    }

    /**
     * 获取点击菜单id
     *
     * @return menu_id - 点击菜单id
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * 设置点击菜单id
     *
     * @param menuId 点击菜单id
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    /**
     * 获取点击父级菜单id
     *
     * @return menu_parent_id - 点击父级菜单id
     */
    public Integer getMenuParentId() {
        return menuParentId;
    }

    /**
     * 设置点击父级菜单id
     *
     * @param menuParentId 点击父级菜单id
     */
    public void setMenuParentId(Integer menuParentId) {
        this.menuParentId = menuParentId;
    }

    /**
     * 获取点击菜单组id
     *
     * @return menu_group_id - 点击菜单组id
     */
    public Integer getMenuGroupId() {
        return menuGroupId;
    }

    /**
     * 设置点击菜单组id
     *
     * @param menuGroupId 点击菜单组id
     */
    public void setMenuGroupId(Integer menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    /**
     * 获取二维码的ticket
     *
     * @return ticket - 二维码的ticket
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * 设置二维码的ticket
     *
     * @param ticket 二维码的ticket
     */
    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
    }

    /**
     * 获取商户自己内部ID，即字段中的sid
     *
     * @return uniq_id - 商户自己内部ID，即字段中的sid
     */
    public String getUniqId() {
        return uniqId;
    }

    /**
     * 设置商户自己内部ID，即字段中的sid
     *
     * @param uniqId 商户自己内部ID，即字段中的sid
     */
    public void setUniqId(String uniqId) {
        this.uniqId = uniqId == null ? null : uniqId.trim();
    }

    /**
     * 获取微信的门店ID，微信内门店唯一标示ID
     *
     * @return poi_id - 微信的门店ID，微信内门店唯一标示ID
     */
    public String getPoiId() {
        return poiId;
    }

    /**
     * 设置微信的门店ID，微信内门店唯一标示ID
     *
     * @param poiId 微信的门店ID，微信内门店唯一标示ID
     */
    public void setPoiId(String poiId) {
        this.poiId = poiId == null ? null : poiId.trim();
    }

    /**
     * 获取审核结果，成功succ 或失败fail
     *
     * @return result - 审核结果，成功succ 或失败fail
     */
    public String getResult() {
        return result;
    }

    /**
     * 设置审核结果，成功succ 或失败fail
     *
     * @param result 审核结果，成功succ 或失败fail
     */
    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    /**
     * 获取会话方向(0:进,1:出)
     *
     * @return direction - 会话方向(0:进,1:出)
     */
    public Boolean getDirection() {
        return direction;
    }

    /**
     * 设置会话方向(0:进,1:出)
     *
     * @param direction 会话方向(0:进,1:出)
     */
    public void setDirection(Boolean direction) {
        this.direction = direction;
    }

    /**
     * 获取会员头像
     *
     * @return member_photo - 会员头像
     */
    public String getMemberPhoto() {
        return memberPhoto;
    }

    /**
     * 设置会员头像
     *
     * @param memberPhoto 会员头像
     */
    public void setMemberPhoto(String memberPhoto) {
        this.memberPhoto = memberPhoto == null ? null : memberPhoto.trim();
    }

    /**
     * 获取客服头像
     *
     * @return kf_photo - 客服头像
     */
    public String getKfPhoto() {
        return kfPhoto;
    }

    /**
     * 设置客服头像
     *
     * @param kfPhoto 客服头像
     */
    public void setKfPhoto(String kfPhoto) {
        this.kfPhoto = kfPhoto == null ? null : kfPhoto.trim();
    }

    /**
     * 获取是否是群发会话
     *
     * @return is_mass - 是否是群发会话
     */
    public Boolean getIsMass() {
        return isMass;
    }

    /**
     * 设置是否是群发会话
     *
     * @param isMass 是否是群发会话
     */
    public void setIsMass(Boolean isMass) {
        this.isMass = isMass;
    }

    /**
     * 获取文本
     *
     * @return content - 文本
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置文本
     *
     * @param content 文本
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}