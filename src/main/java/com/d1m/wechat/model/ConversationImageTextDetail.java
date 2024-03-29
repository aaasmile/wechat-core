package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "conversation_image_text_detail")
public class ConversationImageTextDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "wechat_id")
	private Integer wechatId;

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

	public Integer getWechatId() {
		return wechatId;
	}

	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
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

	public static class Builder {
		private Integer id;
		private Integer wechatId;
		private String title;
		private String author;
		private Boolean contentSourceChecked;
		private String contentSourceUrl;
		private Boolean showCover;
		private String materialCoverUrl;
		private String summary;
		private Integer conversationId;
		private String content;

		public Builder id(Integer id) {
			this.id = id;
			return this;
		}

		public Builder wechatId(Integer wechatId) {
			this.wechatId = wechatId;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder author(String author) {
			this.author = author;
			return this;
		}

		public Builder contentSourceChecked(Boolean contentSourceChecked) {
			this.contentSourceChecked = contentSourceChecked;
			return this;
		}

		public Builder contentSourceUrl(String contentSourceUrl) {
			this.contentSourceUrl = contentSourceUrl;
			return this;
		}

		public Builder showCover(Boolean showCover) {
			this.showCover = showCover;
			return this;
		}

		public Builder materialCoverUrl(String materialCoverUrl) {
			this.materialCoverUrl = materialCoverUrl;
			return this;
		}

		public Builder summary(String summary) {
			this.summary = summary;
			return this;
		}

		public Builder conversationId(Integer conversationId) {
			this.conversationId = conversationId;
			return this;
		}

		public Builder content(String content) {
			this.content = content;
			return this;
		}

		public ConversationImageTextDetail build() {
			return new ConversationImageTextDetail(this);
		}
	}

	private ConversationImageTextDetail(Builder builder) {
		this.id = builder.id;
		this.wechatId = builder.wechatId;
		this.title = builder.title;
		this.author = builder.author;
		this.contentSourceChecked = builder.contentSourceChecked;
		this.contentSourceUrl = builder.contentSourceUrl;
		this.showCover = builder.showCover;
		this.materialCoverUrl = builder.materialCoverUrl;
		this.summary = builder.summary;
		this.conversationId = builder.conversationId;
		this.content = builder.content;
	}

	public ConversationImageTextDetail(Integer id, Integer wechatId, String title, String author, Boolean contentSourceChecked, String contentSourceUrl, Boolean showCover, String materialCoverUrl, String summary, Integer conversationId, String content) {
		super();
		this.id = id;
		this.wechatId = wechatId;
		this.title = title;
		this.author = author;
		this.contentSourceChecked = contentSourceChecked;
		this.contentSourceUrl = contentSourceUrl;
		this.showCover = showCover;
		this.materialCoverUrl = materialCoverUrl;
		this.summary = summary;
		this.conversationId = conversationId;
		this.content = content;
	}

	public ConversationImageTextDetail() {
		super();
	}
	
}