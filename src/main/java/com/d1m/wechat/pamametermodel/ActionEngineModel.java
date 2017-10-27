package com.d1m.wechat.pamametermodel;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("行为规则参数")
public class ActionEngineModel extends BaseModel {
	
	@ApiModelProperty("行为规则ID")
	private Integer id;
	@ApiModelProperty("名称")
	private String name;
	@ApiModelProperty("开始时间")
	private String start_at;
	@ApiModelProperty("结束时间")
	private String end_at;
	@ApiModelProperty("规则")
	private ActionEngineCondition condition;
	@ApiModelProperty("产生的效果")
	private List<ActionEngineEffect> effect;
	@ApiModelProperty("二维码ID")
	private Integer qrcodeId;
	@ApiModelProperty("回复ID")
	private Integer replyId;

	public ActionEngineCondition getCondition() {
		return condition;
	}

	public List<ActionEngineEffect> getEffect() {
		return effect;
	}

	public String getEnd_at() {
		return end_at;
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

	public Integer getReplyId() {
		return replyId;
	}

	public String getStart_at() {
		return start_at;
	}

	public void setCondition(ActionEngineCondition condition) {
		this.condition = condition;
	}

	public void setEffect(List<ActionEngineEffect> effect) {
		this.effect = effect;
	}

	public void setEnd_at(String end_at) {
		this.end_at = end_at;
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

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	public void setStart_at(String start_at) {
		this.start_at = start_at;
	}

}
