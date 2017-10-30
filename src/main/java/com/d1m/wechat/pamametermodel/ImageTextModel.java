package com.d1m.wechat.pamametermodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("图文参数")
public class ImageTextModel extends BaseModel {
	
	@ApiModelProperty("主键ID")
	private Integer id;
	
	@ApiModelProperty("标题")
	private String title;

	@ApiModelProperty("作者")
	private String author;
	
	@ApiModelProperty("正文")
	private String content;

	@ApiModelProperty("原文链接是否存在")
	private Boolean contentSourceChecked;

	@ApiModelProperty("原文链接")
	private String contentSourceUrl;

	@ApiModelProperty("是否展示封面")
	private boolean showCover;

	@ApiModelProperty("摘要")
	private String summary;

	@ApiModelProperty("素材封面ID")
	private Integer materialCoverId;

	@ApiModelProperty("素材封面URL")
	private String materialCoverUrl;

	@ApiModelProperty("素材封面媒体ID")
	private String materialCoverMediaId;
	
	@ApiModelProperty("查询条件")
	private String query;

	@ApiModelProperty("是否推送")
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
