package com.d1m.wechat.dto;

import java.util.Date;

public class ImageTextDto {

	private Integer id;

	private String title;

	private String author;

	private Date createdAt;

	private String content;

	private Boolean contentSourceChecked;

	private String contentSourceUrl;

	private boolean showCover;

	private String summary;

	private Integer materialCoverId;

	private String materialCoverUrl;

	private String materialCoverMediaId;

	private Integer comment;


	public String getAuthor() {
		return author;
	}

	public String getContent() {
		return content;
	}

	public Boolean getContentSourceChecked() {
		return contentSourceChecked;
	}

	public String getContentSourceUrl() {
		return contentSourceUrl;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Integer getId() {
		return id;
	}

	public Integer getMaterialCoverId() {
		return materialCoverId;
	}

	public String getMaterialCoverMediaId() {
		return materialCoverMediaId;
	}

	public String getMaterialCoverUrl() {
		return materialCoverUrl;
	}

	public String getSummary() {
		return summary;
	}

	public String getTitle() {
		return title;
	}

	public boolean isShowCover() {
		return showCover;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setContentSourceChecked(Boolean contentSourceChecked) {
		this.contentSourceChecked = contentSourceChecked;
	}

	public void setContentSourceUrl(String contentSourceUrl) {
		this.contentSourceUrl = contentSourceUrl;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMaterialCoverId(Integer materialCoverId) {
		this.materialCoverId = materialCoverId;
	}

	public void setMaterialCoverMediaId(String materialCoverMediaId) {
		this.materialCoverMediaId = materialCoverMediaId;
	}

	public void setMaterialCoverUrl(String materialCoverUrl) {
		this.materialCoverUrl = materialCoverUrl;
	}

	public void setShowCover(boolean showCover) {
		this.showCover = showCover;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public Integer getComment() {
		return comment;
	}
	public void setComment(Integer comment) {
		this.comment = comment;
	}
}
