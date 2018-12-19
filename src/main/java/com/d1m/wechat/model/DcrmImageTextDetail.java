package com.d1m.wechat.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "dcrm_image_text_detail")
public class DcrmImageTextDetail {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "link")
    private String link;

    /**
     * 封面图片素材ID
     */
    @Column(name = "material_cover_id")
    private Integer materialCoverId;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 素材ID
     */
    @Column(name = "material_id")
    private Integer materialId;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
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
    @Column(name = "material_category_id")
   private String materialCategoryId;

    /**
     * 关联微信图文id
     */
    @Column(name = "wx_image_text_id")
    private Integer wxImageTextId;

    /**
     * 阅读标签id
     */
    @Column(name = "tag_id")
    private String tagId;

    //创建时间
    private Date createdAt;
    //创建人
    private Integer createdBy;
    //最后更新时间
    private Date lasteUpdatedAt;
    //最后更新人
    private Integer lasteUpdatedBy;
    /**
     * 发送次数
     */
    @Column(name="send_times")
    private Integer sendTimes;
    /**
     * 阅读次数
     */
    @Column(name="read_times")
    private Integer readTimes;

    /**
     * 二维码id
     */
    private Integer qrcodeId;

    public Integer getQrcodeId() {
        return qrcodeId;
    }

    public void setQrcodeId(Integer qrcodeId) {
        this.qrcodeId = qrcodeId;
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

    public String getMaterialCategoryId() {
        return materialCategoryId;
    }

    public void setMaterialCategoryId(String materialCategoryId) {
        this.materialCategoryId = materialCategoryId;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLasteUpdatedAt() {
        return lasteUpdatedAt;
    }

    public void setLasteUpdatedAt(Date lasteUpdatedAt) {
        this.lasteUpdatedAt = lasteUpdatedAt;
    }

    public Integer getLasteUpdatedBy() {
        return lasteUpdatedBy;
    }

    public void setLasteUpdatedBy(Integer lasteUpdatedBy) {
        this.lasteUpdatedBy = lasteUpdatedBy;
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
}