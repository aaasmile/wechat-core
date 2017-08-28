package com.d1m.wechat.pamametermodel;

import java.util.List;

public class ActionEngineModel extends BaseModel {

	private Integer id;

	private String name;

	private String start_at;

	private String end_at;

	private ActionEngineCondition condition;

	private List<ActionEngineEffect> effect;

	private Integer qrcodeId;

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
