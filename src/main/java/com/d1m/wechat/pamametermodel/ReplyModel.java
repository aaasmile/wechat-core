package com.d1m.wechat.pamametermodel;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("回复参数")
public class ReplyModel extends BaseModel {
	
	@ApiModelProperty("回复ID")
	private Integer id;
	@ApiModelProperty("名称")
	private String name;
	@ApiModelProperty("行为规则")
	private ActionEngineModel rules;
	@ApiModelProperty("匹配模式")
	private Byte matchMode;
	@ApiModelProperty("权重")
	private Integer weight;
	@ApiModelProperty("查询条件")
	private String query;
	@ApiModelProperty("关键字")
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