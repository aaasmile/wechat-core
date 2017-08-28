package com.d1m.wechat.dto;

import java.util.List;

/**
 * 用户来源分析
 * @author d1m
 */
public class ReportUserSourceDto {

	private int wechatSearch;
	private int businessCard;
	private int qrCode;
	private int topRightMenu;
	private int wechatInImageText;
	private int wechatInArticleAd;
	private int momentsAd;
	private int payAttentionAfter;
	private int other;
	private String date;
	private List<ReportUserSourceDto> list;
	
	
	public int getWechatSearch() {
		return wechatSearch;
	}
	public void setWechatSearch(int wechatSearch) {
		this.wechatSearch = wechatSearch;
	}
	public int getBusinessCard() {
		return businessCard;
	}
	public void setBusinessCard(int businessCard) {
		this.businessCard = businessCard;
	}
	public int getQrCode() {
		return qrCode;
	}
	public void setQrCode(int qrCode) {
		this.qrCode = qrCode;
	}
	public int getTopRightMenu() {
		return topRightMenu;
	}
	public void setTopRightMenu(int topRightMenu) {
		this.topRightMenu = topRightMenu;
	}
	public int getWechatInImageText() {
		return wechatInImageText;
	}
	public void setWechatInImageText(int wechatInImageText) {
		this.wechatInImageText = wechatInImageText;
	}
	public int getWechatInArticleAd() {
		return wechatInArticleAd;
	}
	public void setWechatInArticleAd(int wechatInArticleAd) {
		this.wechatInArticleAd = wechatInArticleAd;
	}
	public int getMomentsAd() {
		return momentsAd;
	}
	public void setMomentsAd(int momentsAd) {
		this.momentsAd = momentsAd;
	}
	public int getPayAttentionAfter() {
		return payAttentionAfter;
	}
	public void setPayAttentionAfter(int payAttentionAfter) {
		this.payAttentionAfter = payAttentionAfter;
	}
	public int getOther() {
		return other;
	}
	public void setOther(int other) {
		this.other = other;
	}
	public List<ReportUserSourceDto> getList() {
		return list;
	}
	public void setList(List<ReportUserSourceDto> list) {
		this.list = list;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}
