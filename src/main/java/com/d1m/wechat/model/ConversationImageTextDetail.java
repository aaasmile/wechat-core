package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "conversation_image_text_detail")
public class ConversationImageTextDetail {
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
     * 封面图片素材url
     */
    @Column(name = "material_cover_url")
    private String materialCoverUrl;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 会话ID
     */
    @Column(name = "conversation_id")
    private Integer conversationId;

    /**
     * 正文
     */
    private String content;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
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
     * 获取封面图片素材url
     *
     * @return material_cover_url - 封面图片素材url
     */
    public String getMaterialCoverUrl() {
        return materialCoverUrl;
    }

    /**
     * 设置封面图片素材url
     *
     * @param materialCoverUrl 封面图片素材url
     */
    public void setMaterialCoverUrl(String materialCoverUrl) {
        this.materialCoverUrl = materialCoverUrl == null ? null : materialCoverUrl.trim();
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
     * 获取会话ID
     *
     * @return conversation_id - 会话ID
     */
    public Integer getConversationId() {
        return conversationId;
    }

    /**
     * 设置会话ID
     *
     * @param conversationId 会话ID
     */
    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
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
}