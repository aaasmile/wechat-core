package com.d1m.wechat.dto;

import java.util.List;

public class ReportKeyMatchDto {
	private int totalCount;
	private int matchCount;
	private double matchRate;
	private List<ReportKeyMatchTrendDto> trendList;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getMatchCount() {
		return matchCount;
	}
	public void setMatchCount(int matchCount) {
		this.matchCount = matchCount;
	}
	public double getMatchRate() {
		return matchRate;
	}
	public void setMatchRate(double matchRate) {
		this.matchRate = matchRate;
	}
	public List<ReportKeyMatchTrendDto> getTrendList() {
		return trendList;
	}
	public void setTrendList(List<ReportKeyMatchTrendDto> trendList) {
		this.trendList = trendList;
	}
}
