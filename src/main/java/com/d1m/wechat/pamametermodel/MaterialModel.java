package com.d1m.wechat.pamametermodel;

import java.util.List;

import com.d1m.wechat.util.DateUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("素材")
public class MaterialModel {
	
	@ApiModelProperty("主键ID")
	private Integer id;
	
	@ApiModelProperty("图文")
	private List<ImageTextModel> items;
	
	@ApiModelProperty("主键ID")
	private String url;
	
	@ApiModelProperty("图文详情")
	private MaterialTextDetailModel text;
	
	@ApiModelProperty("标题")
	private String title;
	
	@ApiModelProperty("创建时间")
	private String createdAt;
	
	@ApiModelProperty("图片分类")
	private Byte materialType;
	
	@ApiModelProperty("图文列表")
	private List<ImageTextModel> imagetexts;// for create imagetext
	
	@ApiModelProperty("图文列表")
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
