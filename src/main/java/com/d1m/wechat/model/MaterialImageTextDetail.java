package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "material_image_text_detail")
public class MaterialImageTextDetail {
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
     * 作者
     */
    private String author;

    /**
     * 原文链接是否存在
     */
    @Column(name = "content_source_checked")
    private Boolean contentSourceChecked;

    /**
     * 原文链接
     */
    @Column(name = "content_source_url")
    private String contentSourceUrl;

    /**
     * 封面图片显示在正文
     */
    @Column(name = "show_cover")
    private Boolean showCover;

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
     * 正文
     */
    private String content;

    /**
     * 评论(0:不能评论,1:所有人可以评论，2：仅有粉丝可以评论)
     */
    private Integer comment;


    @Column(name = "remarks")
    private String remarks;

    /**
     * 图文页的URL
     */
    private String url;

    /**
     * 微信图文URL链接sn参数
     */
    private String sn;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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
     * 获取作者
     *
     * @return author - 作者
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置作者
     *
     * @param author 作者
     */
    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    /**
     * 获取原文链接是否存在
     *
     * @return content_source_checked - 原文链接是否存在
     */
    public Boolean getContentSourceChecked() {
        return contentSourceChecked;
    }

    /**
     * 设置原文链接是否存在
     *
     * @param contentSourceChecked 原文链接是否存在
     */
    public void setContentSourceChecked(Boolean contentSourceChecked) {
        this.contentSourceChecked = contentSourceChecked;
    }

    /**
     * 获取原文链接
     *
     * @return content_source_url - 原文链接
     */
    public String getContentSourceUrl() {
        return contentSourceUrl;
    }

    /**
     * 设置原文链接
     *
     * @param contentSourceUrl 原文链接
     */
    public void setContentSourceUrl(String contentSourceUrl) {
        this.contentSourceUrl = contentSourceUrl == null ? null : contentSourceUrl.trim();
    }

    /**
     * 获取封面图片显示在正文
     *
     * @return show_cover - 封面图片显示在正文
     */
    public Boolean getShowCover() {
        return showCover;
    }

    /**
     * 设置封面图片显示在正文
     *
     * @param showCover 封面图片显示在正文
     */
    public void setShowCover(Boolean showCover) {
        this.showCover = showCover;
    }

    /**
     * 获取封面图片素材ID
     *
     * @return material_cover_id - 封面图片素材ID
     */
    public Integer getMaterialCoverId() {
        return materialCoverId;
    }

    /**
     * 设置封面图片素材ID
     *
     * @param materialCoverId 封面图片素材ID
     */
    public void setMaterialCoverId(Integer materialCoverId) {
        this.materialCoverId = materialCoverId;
    }

    /**
     * 获取摘要
     *
     * @return summary - 摘要
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 设置摘要
     *
     * @param summary 摘要
     */
    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
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

    /**
     * 获取顺序
     *
     * @return sequence - 顺序
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * 设置顺序
     *
     * @param sequence 顺序
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * 获取正文
     *
     * @return content - 正文
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置正文
     *
     * @param content 正文
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }


    public Integer getComment() {
        return comment;
    }
    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}