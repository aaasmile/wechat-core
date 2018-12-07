package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

public class Material {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 创建人
     */
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 素材类型(1:图文,2:图片,3:语音,4:视频,5:小视频,6:文字)
     */
    @Column(name = "material_type")
    private Byte materialType;

    /**
     * 名称
     */
    private String name;

    /**
     * 图片路径
     */
    @Column(name = "pic_url")
    private String picUrl;

    /**
     * 微信图片路径
     */
    @Column(name = "wx_pic_url")
    private String wxPicUrl;

    /**
     * 图片分类
     */
    @Column(name = "material_image_type_id")
    private Integer materialImageTypeId;

    /**
     * 标题
     */
    private String title;

    /**
     * 语音类型
     */
    @Column(name = "material_voice_type_id")
    private Integer materialVoiceTypeId;

    /**
     * 语音路径
     */
    @Column(name = "voice_url")
    private String voiceUrl;

    /**
     * 视频分类
     */
    @Column(name = "material_video_type_id")
    private Integer materialVideoTypeId;

    /**
     * 视频路径
     */
    @Column(name = "video_url")
    private String videoUrl;

    /**
     * 视频标签
     */
    @Column(name = "video_tag")
    private String videoTag;

    /**
     * 简介
     */
    private String description;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 修改人
     */
    @Column(name = "modify_by_id")
    private Integer modifyById;

    /**
     * 修改时间
     */
    @Column(name = "modify_at")
    private Date modifyAt;

    /**
     * 最近同步微信时间
     */
    @Column(name = "last_push_at")
    private Date lastPushAt;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 微信素材的media_id
     */
    @Column(name = "media_id")
    private String mediaId;

    /**
     * 状态(0:删除,1:使用)
     */
    private Byte status;

    @Column(name="is_scrm")
    private int isScrm;

    public int getIsScrm() {
        return isScrm;
    }

    public void setIsScrm(int isScrm) {
        this.isScrm = isScrm;
    }

    /**
     * 评论(0:不能评论,1:所有人可以评论，2：仅有粉丝可以评论)
     */
//    private Integer comment;


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
     * 获取创建人
     *
     * @return creator_id - 创建人
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人
     *
     * @param creatorId 创建人
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取素材类型(1:图文,2:图片,3:语音,4:视频,5:小视频,6:文字)
     *
     * @return material_type - 素材类型(1:图文,2:图片,3:语音,4:视频,5:小视频,6:文字)
     */
    public Byte getMaterialType() {
        return materialType;
    }

    /**
     * 设置素材类型(1:图文,2:图片,3:语音,4:视频,5:小视频,6:文字)
     *
     * @param materialType 素材类型(1:图文,2:图片,3:语音,4:视频,5:小视频,6:文字)
     */
    public void setMaterialType(Byte materialType) {
        this.materialType = materialType;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取图片路径
     *
     * @return pic_url - 图片路径
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * 设置图片路径
     *
     * @param picUrl 图片路径
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    /**
     * 获取微信图片路径
     *
     * @return wx_pic_url - 微信图片路径
     */
    public String getWxPicUrl() {
        return wxPicUrl;
    }

    /**
     * 设置微信图片路径
     *
     * @param wxPicUrl 微信图片路径
     */
    public void setWxPicUrl(String wxPicUrl) {
        this.wxPicUrl = wxPicUrl == null ? null : wxPicUrl.trim();
    }

    /**
     * 获取图片分类
     *
     * @return material_image_type_id - 图片分类
     */
    public Integer getMaterialImageTypeId() {
        return materialImageTypeId;
    }

    /**
     * 设置图片分类
     *
     * @param materialImageTypeId 图片分类
     */
    public void setMaterialImageTypeId(Integer materialImageTypeId) {
        this.materialImageTypeId = materialImageTypeId;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取语音类型
     *
     * @return material_voice_type_id - 语音类型
     */
    public Integer getMaterialVoiceTypeId() {
        return materialVoiceTypeId;
    }

    /**
     * 设置语音类型
     *
     * @param materialVoiceTypeId 语音类型
     */
    public void setMaterialVoiceTypeId(Integer materialVoiceTypeId) {
        this.materialVoiceTypeId = materialVoiceTypeId;
    }

    /**
     * 获取语音路径
     *
     * @return voice_url - 语音路径
     */
    public String getVoiceUrl() {
        return voiceUrl;
    }

    /**
     * 设置语音路径
     *
     * @param voiceUrl 语音路径
     */
    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl == null ? null : voiceUrl.trim();
    }

    /**
     * 获取视频分类
     *
     * @return material_video_type_id - 视频分类
     */
    public Integer getMaterialVideoTypeId() {
        return materialVideoTypeId;
    }

    /**
     * 设置视频分类
     *
     * @param materialVideoTypeId 视频分类
     */
    public void setMaterialVideoTypeId(Integer materialVideoTypeId) {
        this.materialVideoTypeId = materialVideoTypeId;
    }

    /**
     * 获取视频路径
     *
     * @return video_url - 视频路径
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * 设置视频路径
     *
     * @param videoUrl 视频路径
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    /**
     * 获取视频标签
     *
     * @return video_tag - 视频标签
     */
    public String getVideoTag() {
        return videoTag;
    }

    /**
     * 设置视频标签
     *
     * @param videoTag 视频标签
     */
    public void setVideoTag(String videoTag) {
        this.videoTag = videoTag == null ? null : videoTag.trim();
    }

    /**
     * 获取简介
     *
     * @return description - 简介
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置简介
     *
     * @param description 简介
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取修改人
     *
     * @return modify_by_id - 修改人
     */
    public Integer getModifyById() {
        return modifyById;
    }

    /**
     * 设置修改人
     *
     * @param modifyById 修改人
     */
    public void setModifyById(Integer modifyById) {
        this.modifyById = modifyById;
    }

    /**
     * 获取修改时间
     *
     * @return modify_at - 修改时间
     */
    public Date getModifyAt() {
        return modifyAt;
    }

    /**
     * 设置修改时间
     *
     * @param modifyAt 修改时间
     */
    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    /**
     * 获取最近同步微信时间
     *
     * @return last_push_at - 最近同步微信时间
     */
    public Date getLastPushAt() {
        return lastPushAt;
    }

    /**
     * 设置最近同步微信时间
     *
     * @param lastPushAt 最近同步微信时间
     */
    public void setLastPushAt(Date lastPushAt) {
        this.lastPushAt = lastPushAt;
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
     * 获取微信素材的media_id
     *
     * @return media_id - 微信素材的media_id
     */
    public String getMediaId() {
        return mediaId;
    }

    /**
     * 设置微信素材的media_id
     *
     * @param mediaId 微信素材的media_id
     */
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId == null ? null : mediaId.trim();
    }

    /**
     * 获取状态(0:删除,1:使用)
     *
     * @return status - 状态(0:删除,1:使用)
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态(0:删除,1:使用)
     *
     * @param status 状态(0:删除,1:使用)
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

//    public Integer getComment() {
//        return comment;
//    }
//    public void setComment(Integer comment) {
//        this.comment = comment;
//    }
}