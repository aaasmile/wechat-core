package com.d1m.wechat.dto;

import java.util.List;

public class ReportArticleDto {

	private int pageTotal;
	private int oriPageTotal;
	private int addFavTotal;
	private int shareTotal;
	private List<ReportArticleItemDto> list;
	private List<ReportArticleItemDto> articleList;
	
	public int getPageTotal() {
		return pageTotal;
	}
	public int getOriPageTotal() {
		return oriPageTotal;
	}
	public int getAddFavTotal() {
		return addFavTotal;
	}
	public int getShareTotal() {
		return shareTotal;
	}
	public List<ReportArticleItemDto> getList() {
		return list;
	}
	public List<ReportArticleItemDto> getArticleList() {
		return articleList;
	}
	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}
	public void setOriPageTotal(int oriPageTotal) {
		this.oriPageTotal = oriPageTotal;
	}
	public void setAddFavTotal(int addFavTotal) {
		this.addFavTotal = addFavTotal;
	}
	public void setShareTotal(int shareTotal) {
		this.shareTotal = shareTotal;
	}
	public void setList(List<ReportArticleItemDto> list) {
		this.list = list;
	}
	public void setArticleList(List<ReportArticleItemDto> articleList) {
		this.articleList = articleList;
	}
	
}
