package com.d1m.wechat.dto;

import java.util.List;

public class ReportQrcodeDto {

	private int total;
	private int currentTotal;
//	private int attentionScanNum;
	private int unAttentionScanNum;
	private int scanNumTotal;
	private int attentionByScanTotal;
	private int userByScanTotal;
	private List<ReportQrcodeItemDto> list;
	private List<ReportQrcodeItemDto> trendList;
	private List<ReportQrcodeItemDto> qrcodeList;
	
	public int getTotal() {
		return total;
	}
	public int getCurrentTotal() {
		return currentTotal;
	}
//	public int getAttentionScanNum() {
//		return attentionScanNum;
//	}
	public int getUnAttentionScanNum() {
		return unAttentionScanNum;
	}
	public List<ReportQrcodeItemDto> getList() {
		return list;
	}
	public List<ReportQrcodeItemDto> getTrendList() {
		return trendList;
	}
	public List<ReportQrcodeItemDto> getQrcodeList() {
		return qrcodeList;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public void setCurrentTotal(int currentTotal) {
		this.currentTotal = currentTotal;
	}
//	public void setAttentionScanNum(int attentionScanNum) {
//		this.attentionScanNum = attentionScanNum;
//	}
	public void setUnAttentionScanNum(int unAttentionScanNum) {
		this.unAttentionScanNum = unAttentionScanNum;
	}
	public void setList(List<ReportQrcodeItemDto> list) {
		this.list = list;
	}
	public void setTrendList(List<ReportQrcodeItemDto> trendList) {
		this.trendList = trendList;
	}
	public void setQrcodeList(List<ReportQrcodeItemDto> qrcodeList) {
		this.qrcodeList = qrcodeList;
	}
	public int getScanNumTotal() {
		return scanNumTotal;
	}
	public void setScanNumTotal(int scanNumTotal) {
		this.scanNumTotal = scanNumTotal;
	}
	public int getAttentionByScanTotal() {
		return attentionByScanTotal;
	}
	public void setAttentionByScanTotal(int attentionByScanTotal) {
		this.attentionByScanTotal = attentionByScanTotal;
	}
	public int getUserByScanTotal() {
		return userByScanTotal;
	}
	public void setUserByScanTotal(int userByScanTotal) {
		this.userByScanTotal = userByScanTotal;
	}
	
}
