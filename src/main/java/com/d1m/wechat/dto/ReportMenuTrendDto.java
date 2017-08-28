package com.d1m.wechat.dto;

import java.util.List;

public class ReportMenuTrendDto {

    private List<ReportMenuBaseDto> menuList;
    private String date;
    
	public List<ReportMenuBaseDto> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<ReportMenuBaseDto> menuList) {
		this.menuList = menuList;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
    
}
