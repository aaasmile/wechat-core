package com.d1m.wechat.dto;

import java.text.DecimalFormat;

public class PieBaseDto {

	private String name;
	private Integer count;
	private int rate;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * @return the rate
	 */
	public int getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(Double rate) {
		DecimalFormat    df   = new DecimalFormat("######0.00");
		this.rate = (int)(Double.parseDouble(df.format(rate)) * 100);
	}
	
	
}
