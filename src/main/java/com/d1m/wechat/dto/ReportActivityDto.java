package com.d1m.wechat.dto;

import java.util.List;

public class ReportActivityDto {
	
	private int activityTotal;
	private int couponTotal;
	private int memberTotal;
	private int couponReceiveTotal;
	private int couponValidityTotal;
	private List<ReportActivityItemDto> list;
	private List<PieBaseDto> pieList;
	
	public int getActivityTotal() {
		return activityTotal;
	}
	public int getCouponTotal() {
		return couponTotal;
	}
	public int getMemberTotal() {
		return memberTotal;
	}
	public int getCouponReceiveTotal() {
		return couponReceiveTotal;
	}
	public int getCouponValidityTotal() {
		return couponValidityTotal;
	}
	public List<ReportActivityItemDto> getList() {
		return list;
	}
	public List<PieBaseDto> getPieList() {
		return pieList;
	}
	public void setActivityTotal(int activityTotal) {
		this.activityTotal = activityTotal;
	}
	public void setCouponTotal(int couponTotal) {
		this.couponTotal = couponTotal;
	}
	public void setMemberTotal(int memberTotal) {
		this.memberTotal = memberTotal;
	}
	public void setCouponReceiveTotal(int couponReceiveTotal) {
		this.couponReceiveTotal = couponReceiveTotal;
	}
	public void setCouponValidityTotal(int couponValidityTotal) {
		this.couponValidityTotal = couponValidityTotal;
	}
	public void setList(List<ReportActivityItemDto> list) {
		this.list = list;
	}
	public void setPieList(List<PieBaseDto> pieList) {
		this.pieList = pieList;
	}
	
}
