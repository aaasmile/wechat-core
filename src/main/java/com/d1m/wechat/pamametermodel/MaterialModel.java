package com.d1m.wechat.pamametermodel;

import java.util.List;

import com.d1m.wechat.util.DateUtil;

public class MaterialModel {

	private Integer id;

	private List<ImageTextModel> items;

	private String url;

	private MaterialTextDetailModel text;

	private String title;

	private String createdAt;

	private Byte materialType;

	private List<ImageTextModel> imagetexts;// for create imagetext

	private Boolean push;

	private Integer memberId;

	/**
	 * 评论(0:不能评论,1:所有人可以评论，2：仅有粉丝可以评论)
	 */
//	private Integer comment;

	public String getCreatedAt() {
		return DateUtil.formatYYYYMMDDHHMMSS(DateUtil.parse(createdAt));
	}

	public Integer getId() {
		return id;
	}

	public List<ImageTextModel> getImagetexts() {
		return imagetexts;
	}

	public List<ImageTextModel> getItems() {
		return items;
	}

	public Byte getMaterialType() {
		return materialType;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public Boolean getPush() {
		return push;
	}

	public MaterialTextDetailModel getText() {
		return text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setImagetexts(List<ImageTextModel> imagetexts) {
		this.imagetexts = imagetexts;
	}

	public void setItems(List<ImageTextModel> items) {
		this.items = items;
	}

	public void setMaterialType(Byte materialType) {
		this.materialType = materialType;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public void setPush(Boolean push) {
		this.push = push;
	}

	public void setText(MaterialTextDetailModel text) {
		this.text = text;
	}


	public void setUrl(String url) {
		this.url = url;
	}
//
//	public Integer getComment() {
//		return comment;
//	}
//	public void setComment(Integer comment) {
//		this.comment = comment;
//	}
}
