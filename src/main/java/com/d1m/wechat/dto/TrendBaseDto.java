package com.d1m.wechat.dto;

public class TrendBaseDto {
	
	private String time;
	private Integer count;
	
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
		//return (new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(time);
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
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
	
	
}
