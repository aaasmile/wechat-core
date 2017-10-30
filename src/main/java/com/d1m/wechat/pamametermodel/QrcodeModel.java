package com.d1m.wechat.pamametermodel;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("二维码 参数")
public class QrcodeModel extends BaseModel {
	@ApiModelProperty("ID")
	private Integer id;
	@ApiModelProperty("创建时间")
	private Date createdAt;
	@ApiModelProperty("名称")
	private String name;
	@ApiModelProperty("摘要")
	private String summary;
	@ApiModelProperty("二维码类型ID")
	private Integer qrcodeTypeId;
	@ApiModelProperty("二维码类型名称")
	private String qrcodeTypeName;
	@ApiModelProperty("二维码URL")
	private String qrcodeUrl;
	@ApiModelProperty("场景值")
	private String scene;
	@ApiModelProperty("二维码ID")
	private Integer qrcodeId;
	@ApiModelProperty("二维码行为规则参数")
	private ActionEngineModel rules;
	@ApiModelProperty("查询条件")
	private String query;
	@ApiModelProperty("支持订阅回复")
	private Byte sopportSubscribeReply;
	@ApiModelProperty("开始时间")
	private String start;
	@ApiModelProperty("结束时间")
	private String end;
	@ApiModelProperty("状态")
	private Integer status;

	public Date getCreatedAt() {
		return createdAt;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getQrcodeId() {
		return qrcodeId;
	}

	public Integer getQrcodeTypeId() {
		return qrcodeTypeId;
	}

	public String getQrcodeTypeName() {
		return qrcodeTypeName;
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public String getQuery() {
		return query;
	}

	public ActionEngineModel getRules() {
		return rules;
	}

	public String getScene() {
		return scene;
	}

	public Byte getSopportSubscribeReply() {
		return sopportSubscribeReply;
	}

	public String getSummary() {
		return summary;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQrcodeId(Integer qrcodeId) {
		this.qrcodeId = qrcodeId;
	}

	public void setQrcodeTypeId(Integer qrcodeTypeId) {
		this.qrcodeTypeId = qrcodeTypeId;
	}

	public void setQrcodeTypeName(String qrcodeTypeName) {
		this.qrcodeTypeName = qrcodeTypeName;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setRules(ActionEngineModel rules) {
		this.rules = rules;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public void setSopportSubscribeReply(Byte sopportSubscribeReply) {
		this.sopportSubscribeReply = sopportSubscribeReply;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
