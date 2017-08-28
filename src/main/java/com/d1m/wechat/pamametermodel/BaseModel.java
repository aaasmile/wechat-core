package com.d1m.wechat.pamametermodel;

import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;

public class BaseModel {

	private JSONObject sort;

	private String sortName;

	private String sortDir;

	private Integer pageSize = 10;

	private Integer pageNum = 1;

	public boolean pagable() {
		return getPageNum() != null && getPageSize() != null
				&& getPageNum() > 0 && getPageSize() > 0;
	}

	public void disablePage() {
		setPageNum(0);
		setPageSize(0);
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public JSONObject getSort() {
		return sort;
	}

	public String getSortDir() {
		return sortDir;
	}

	public String getSortName() {
		return sortName;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setSort(JSONObject sort) {
		this.sort = sort;
		if (sort != null) {
			for (Entry<String, Object> entry : sort.entrySet()) {
				sortName = entry.getKey();
				sortDir = entry.getValue().toString();
			}
		}
	}

	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

}
