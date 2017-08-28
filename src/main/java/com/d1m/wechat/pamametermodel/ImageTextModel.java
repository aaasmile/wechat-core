package com.d1m.wechat.pamametermodel;

public class ImageTextModel extends BaseModel {

	private Integer id;

	private String title;

	private String author;

	private String content;

	private Boolean contentSourceChecked;

	private String contentSourceUrl;

	private boolean showCover;

	private String summary;

	private Integer materialCoverId;

	private String materialCoverUrl;

	private String materialCoverMediaId;

	private String query;

	private Boolean pushed;

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

	public Boolean getPushed() {
		return pushed;
	}

	public String getQuery() {
		return query;
	}

	public String getSummary() {
		return summary;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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

	public void setPushed(Boolean pushed) {
		this.pushed = pushed;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setShowCover(boolean showCover) {
		this.showCover = showCover;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getComment() {
		return this.comment;
	}

	public void setComment(Integer comment) {
		this.comment = comment;
	}
}
