package com.d1m.wechat.dto;

import java.text.DecimalFormat;
import java.util.List;

public class ReportMessageDto {

	private int receiver;
	private int sendTimes;
	private double perCapita;
	private List<ReportMessageItemDto> list;
	public int getReceiver() {
		return receiver;
	}
	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}
	public int getSendTimes() {
		return sendTimes;
	}
	public void setSendTimes(int sendTimes) {
		this.sendTimes = sendTimes;
	}
	public double getPerCapita() {
		return perCapita;
	}
	public void setPerCapita(double perCapita) {
		DecimalFormat    df   = new DecimalFormat("######0.00");
		this.perCapita = Double.parseDouble(df.format(perCapita));
	}
	public List<ReportMessageItemDto> getList() {
		return list;
	}
	public void setList(List<ReportMessageItemDto> list) {
		this.list = list;
	}
	
	
}
