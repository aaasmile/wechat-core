package com.d1m.wechat.dto;

import java.util.Date;

public class ReplyActionEngineDto {

	private Integer id;

	private String name;

	private Date start_at;

	private Date end_at;

	private String conditions;

	private String effect;

	private Integer actionEngineId;

	public Integer getActionEngineId() {
		return actionEngineId;
	}

	public String getConditions() {
		return conditions;
	}

	public String getEffect() {
		return effect;
	}

	public Date getEnd_at() {
		return end_at;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getStart_at() {
		return start_at;
	}

	public void setActionEngineId(Integer actionEngineId) {
		this.actionEngineId = actionEngineId;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public void setEnd_at(Date end_at) {
		this.end_at = end_at;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStart_at(Date start_at) {
		this.start_at = start_at;
	}

}
