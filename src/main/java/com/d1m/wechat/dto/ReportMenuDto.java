package com.d1m.wechat.dto;

import java.util.List;

public class ReportMenuDto {
	
  	private List<ReportMenuTrendDto> trendList;
	private List<ReportMenuBaseDto> menuList;
	
	public List<ReportMenuTrendDto> getTrendList() {
		return trendList;
	}
	public void setTrendList(List<ReportMenuTrendDto> trendList) {
		this.trendList = trendList;
	}
	public List<ReportMenuBaseDto> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<ReportMenuBaseDto> menuList) {
		this.menuList = menuList;
	}
	
}
