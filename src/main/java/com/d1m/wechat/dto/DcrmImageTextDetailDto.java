package com.d1m.wechat.dto;

import java.io.Serializable;
public class DcrmImageTextDetailDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 正文
     */
    private String content;


    /**
     * 地址
     */
    private String link;

    /**
     * 封面图片素材ID
     */
    private Integer materialCoverId;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 素材ID
     */
    private Integer materialId;

    /**
     * 公众号ID
     */
    private Integer wechatId;

    /**
     * 状态(0:删除,1:使用)
     */
    private Byte status;

    /**
     * 顺序
     */
    private Integer sequence;


    /**
     * 备注
     */
    private String remark;

    /**
     * 图文分类id
     */
    private String imageTypeId;

    /**
     * 关联微信图文id
     */
    private Integer wxImageTextId;

    /**
     * 阅读标签id
     */
    private String tagId;

    /**
     * 创建时间
     */
    private Integer createdAt;

    /**
     * 最后更新时间
     */
    private Integer lasteUpdatedAt;

    private Integer createdBy;

    /**
     * 最后更新人
     */
    private Integer lasteUpdatedBy;
    /**
     * 素材分类ID
     */
    private String materialCategoryId;
    /**
     * 会员ID
     */
    private Integer memberId;
    /**
     * 发送次数
     */
    private Integer sendTimes;
    /**
     * 阅读次数
     */
    private Integer readTimes;

    /**
     * 封面图url
     */
    private String coverPicUrl;
    /**
     * 素材分类名称
     */
    private String categoryName;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 二维码id
     */
    private Integer qrcodeId;
    
	private Integer newid;

	private String newtype;

    /**
     * 图文页的URL
     */
    private String url;
    /**
     * 是否不完整图文
     */
    private Boolean notComplete;

    /**
     * 微信图文是否存在
     */
    private Boolean wxImageTextExist;

    public Boolean getWxImageTextExist() {
        return wxImageTextExist;
    }

    public void setWxImageTextExist(Boolean wxImageTextExist) {
        this.wxImageTextExist = wxImageTextExist;
    }

    public Boolean getNotComplete() {
        return notComplete;
    }

    public void setNotComplete(Boolean notComplete) {
        this.notComplete = notComplete;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getQrcodeId() {
        return qrcodeId;
    }

    public void setQrcodeId(Integer qrcodeId) {
        this.qrcodeId = qrcodeId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMaterialCategoryId() {
        return materialCategoryId;
    }

    public void setMaterialCategoryId(String materialCategoryId) {
        this.materialCategoryId = materialCategoryId;
    }

    public Integer getLasteUpdatedBy() {
        return lasteUpdatedBy;
    }

    public void setLasteUpdatedBy(Integer lasteUpdatedBy) {
        this.lasteUpdatedBy = lasteUpdatedBy;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getMaterialCoverId() {
        return materialCoverId;
    }

    public void setMaterialCoverId(Integer materialCoverId) {
        this.materialCoverId = materialCoverId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getWechatId() {
        return wechatId;
    }

    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImageTypeId() {
        return imageTypeId;
    }

    public void setImageTypeId(String imageTypeId) {
        this.imageTypeId = imageTypeId;
    }

    public Integer getWxImageTextId() {
        return wxImageTextId;
    }

    public void setWxImageTextId(Integer wxImageTextId) {
        this.wxImageTextId = wxImageTextId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getLasteUpdatedAt() {
        return lasteUpdatedAt;
    }

    public void setLasteUpdatedAt(Integer lasteUpdatedAt) {
        this.lasteUpdatedAt = lasteUpdatedAt;
    }

    public Integer getSendTimes() {
        return sendTimes;
    }

    public void setSendTimes(Integer sendTimes) {
        this.sendTimes = sendTimes;
    }

    public Integer getReadTimes() {
        return readTimes;
    }

    public void setReadTimes(Integer readTimes) {
        this.readTimes = readTimes;
    }

    public String getCoverPicUrl() {
        return coverPicUrl;
    }

    public void setCoverPicUrl(String coverPicUrl) {
        this.coverPicUrl = coverPicUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

	public Integer getNewid() {
		return newid;
	}

	public void setNewid(Integer newid) {
		this.newid = newid;
	}

	public String getNewtype() {
		return newtype;
	}

	public void setNewtype(String newtype) {
		this.newtype = newtype;
	}
}
