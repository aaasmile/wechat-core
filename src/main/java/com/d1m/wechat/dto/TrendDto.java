package com.d1m.wechat.dto;

import java.text.DecimalFormat;
import java.util.List;

public class TrendDto {
	
	private Integer total;
	private Integer attention;
	private int newRate;
	private Integer cancel;
	private List<TrendBaseDto> attentionList;
	private List<TrendBaseDto> cancelList;
	private Integer bind;
	private Integer unBind;
	private int cancleRate;
	private List<TrendBaseDto> attentionTimesList;
	private List<TrendBaseDto> cancelTimesList;
	private Integer attentionTimes;
	private Integer cancelTimes;
	
	public Integer getAttentionTimes() {
		return attentionTimes;
	}
	public void setAttentionTimes(Integer attentionTimes) {
		this.attentionTimes = attentionTimes;
	}
	public Integer getCancelTimes() {
		return cancelTimes;
	}
	public void setCancelTimes(Integer cancelTimes) {
		this.cancelTimes = cancelTimes;
	}
	public List<TrendBaseDto> getAttentionTimesList() {
		return attentionTimesList;
	}
	public void setAttentionTimesList(List<TrendBaseDto> attentionTimesList) {
		this.attentionTimesList = attentionTimesList;
	}
	public List<TrendBaseDto> getCancelTimesList() {
		return cancelTimesList;
	}
	public void setCancelTimesList(List<TrendBaseDto> cancelTimesList) {
		this.cancelTimesList = cancelTimesList;
	}
	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	/**
	 * @return the attention
	 */
	public Integer getAttention() {
		return attention;
	}
	/**
	 * @param attention the attention to set
	 */
	public void setAttention(Integer attention) {
		this.attention = attention;
	}
	/**
	 * @return the newRate
	 */
	public int getNewRate() {
		return newRate;
	}
	/**
	 * @param newRate the newRate to set
	 */
	public void setNewRate(Double newRate) {
		DecimalFormat df = new DecimalFormat("######0.00");   
		this.newRate = (int)(Double.parseDouble(df.format(newRate)) * 100);
	}
	public int getCancleRate() {
		return cancleRate;
	}
	public void setCancleRate(Double cancleRate) {
		DecimalFormat df = new DecimalFormat("######0.00");   
		this.cancleRate = (int)(Double.parseDouble(df.format(cancleRate)) * 100);
	}
	/**
	 * @return the cancel
	 */
	public Integer getCancel() {
		return cancel;
	}
	/**
	 * @param cancel the cancel to set
	 */
	public void setCancel(Integer cancel) {
		this.cancel = cancel;
	}
	/**
	 * @return the attentionList
	 */
	public List<TrendBaseDto> getAttentionList() {
		return attentionList;
	}
	/**
	 * @param attentionList the attentionList to set
	 */
	public void setAttentionList(List<TrendBaseDto> attentionList) {
		this.attentionList = attentionList;
	}
	/**
	 * @return the cancelList
	 */
	public List<TrendBaseDto> getCancelList() {
		return cancelList;
	}
	/**
	 * @param cancelList the cancelList to set
	 */
	public void setCancelList(List<TrendBaseDto> cancelList) {
		this.cancelList = cancelList;
	}
	public Integer getBind() {
		return bind;
	}
	public Integer getUnBind() {
		return unBind;
	}
	public void setBind(Integer bind) {
		this.bind = bind;
	}
	public void setUnBind(Integer unBind) {
		this.unBind = unBind;
	}
	
	
}
