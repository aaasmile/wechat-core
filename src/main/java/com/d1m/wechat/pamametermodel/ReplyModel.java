package com.d1m.wechat.pamametermodel;

import java.util.List;


public class ReplyModel extends BaseModel {

	private Integer id;

	private String name;

	private ActionEngineModel rules;

	private Byte matchMode;

	private Integer weight;
	
	private String query;
	
	private List<String> keyWords;

	public Integer getId() {
		return id;
	}

	public Byte getMatchMode() {
		return matchMode;
	}

	public String getName() {
		return name;
	}

	public ActionEngineModel getRules() {
		return rules;
	}

	public Integer getWeight() {
		return weight;
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

	public void setRules(ActionEngineModel rules) {
		this.rules = rules;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<String> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(List<String> keyWords) {
		this.keyWords = keyWords;
	}

}