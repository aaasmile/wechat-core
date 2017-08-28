package com.d1m.wechat.dto;

import java.util.List;

public class ReportAreaBaseDto {

	private int count;
	private int id;
	private List<ReportAreaBaseDto> list ;
	private List<ReportAreaBaseDto> province ;
	private List<ReportAreaBaseDto> city ;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public List<ReportAreaBaseDto> getList() {
		return list;
	}
	public void setList(List<ReportAreaBaseDto> list) {
		this.list = list;
	}
	public List<ReportAreaBaseDto> getProvince() {
		return province;
	}
	public void setProvince(List<ReportAreaBaseDto> province) {
		this.province = province;
	}
	public List<ReportAreaBaseDto> getCity() {
		return city;
	}
	public void setCity(List<ReportAreaBaseDto> city) {
		this.city = city;
	}
	
	
}
