package com.d1m.wechat.dto;

import java.util.List;

public class ReplyDto {

	private Integer id;

	private String name;

	private Byte replyType;

	private Byte matchMode;

	private String createdAt;

	private Integer weight;

	private List<ReplyActionEngineDto> rules;
	
	private List<String> keyWords;

	public String getCreatedAt() {
		return createdAt;
	}

	public Integer getId() {
		return id;
	}

	public Byte getMatchMode() {
		return matchMode;
	}

	public String getName() {
		return name;
	}

	public Byte getReplyType() {
		return replyType;
	}

	public List<ReplyActionEngineDto> getRules() {
		return rules;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMatchMode(Byte matchMode) {
		this.matchMode = matchMode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReplyType(Byte replyType) {
		this.replyType = replyType;
	}

	public void setRules(List<ReplyActionEngineDto> rules) {
		this.rules = rules;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public List<String> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(List<String> keyWords) {
		this.keyWords = keyWords;
	}

}
