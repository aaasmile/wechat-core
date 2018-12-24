package com.d1m.wechat.pamametermodel;

import java.util.List;

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

    @ApiModelProperty("小程序")
    private MiniProgramModel miniProgram;// for create mini program

    @ApiModelProperty("图文列表")
    private Boolean push;

    private Integer memberId;
    
	private Integer newid;
	private String newtype;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<ImageTextModel> getItems() {
		return items;
	}

	public void setItems(List<ImageTextModel> items) {
		this.items = items;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MaterialTextDetailModel getText() {
		return text;
	}

	public void setText(MaterialTextDetailModel text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Byte getMaterialType() {
		return materialType;
	}

	public void setMaterialType(Byte materialType) {
		this.materialType = materialType;
	}

	public List<ImageTextModel> getImagetexts() {
		return imagetexts;
	}

	public void setImagetexts(List<ImageTextModel> imagetexts) {
		this.imagetexts = imagetexts;
	}

	public MiniProgramModel getMiniProgram() {
		return miniProgram;
	}

	public void setMiniProgram(MiniProgramModel miniProgram) {
		this.miniProgram = miniProgram;
	}

	public Boolean getPush() {
		return push;
	}

	public void setPush(Boolean push) {
		this.push = push;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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
