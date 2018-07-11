package com.d1m.wechat.model;

import java.util.List;

public class ListResult {

	public static final Integer SUCCESS_CODE = 0;

	private Integer Code;

	private List<com.d1m.wechat.model.List> List;

	private Integer Result;

	public boolean success() {
		return Code != null && Code.equals(SUCCESS_CODE);
	}

	public Integer getCode() {
		return Code;
	}

	public void setCode(Integer code) {
		Code = code;
	}

	public List<com.d1m.wechat.model.List> getList() {
		return List;
	}

	public void setList(List<com.d1m.wechat.model.List> list) {
		List = list;
	}

	public Integer getResult() {
		return Result;
	}

	public void setResult(Integer result) {
		Result = result;
	}

	public static Integer getSuccessCode() {
		return SUCCESS_CODE;
	}
}
