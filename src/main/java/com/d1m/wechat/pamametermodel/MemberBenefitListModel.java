package com.d1m.wechat.pamametermodel;

public class MemberBenefitListModel extends BaseModel{
	
	private String nickname;
	private Byte sex;
	private Integer country;
	private Integer province;
	private Integer city;
	private Boolean subscribe;
	private Integer activityStartAt;
	private Integer activityEndAt;
	private Integer batchSendOfMonthStartAt;
	private Integer batchSendOfMonthEndAt;
	private String attentionStartAt;
	private String attentionEndAt;
	private String cancelSubscribeStartAt;
	private String cancelSubscribeEndAt;
	private String mobile;
	private Boolean isOnline;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Byte getSex() {
		return sex;
	}
	public void setSex(Byte sex) {
		this.sex = sex;
	}
	public Integer getCountry() {
		return country;
	}
	public void setCountry(Integer country) {
		this.country = country;
	}
	public Integer getProvince() {
		return province;
	}
	public void setProvince(Integer province) {
		this.province = province;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public Boolean getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(Boolean subscribe) {
		this.subscribe = subscribe;
	}
	public Integer getActivityStartAt() {
		return activityStartAt;
	}
	public void setActivityStartAt(Integer activityStartAt) {
		this.activityStartAt = activityStartAt;
	}
	public Integer getActivityEndAt() {
		return activityEndAt;
	}
	public void setActivityEndAt(Integer activityEndAt) {
		this.activityEndAt = activityEndAt;
	}
	public Integer getBatchSendOfMonthStartAt() {
		return batchSendOfMonthStartAt;
	}
	public void setBatchSendOfMonthStartAt(Integer batchSendOfMonthStartAt) {
		this.batchSendOfMonthStartAt = batchSendOfMonthStartAt;
	}
	public Integer getBatchSendOfMonthEndAt() {
		return batchSendOfMonthEndAt;
	}
	public void setBatchSendOfMonthEndAt(Integer batchSendOfMonthEndAt) {
		this.batchSendOfMonthEndAt = batchSendOfMonthEndAt;
	}
	public String getAttentionStartAt() {
		return attentionStartAt;
	}
	public void setAttentionStartAt(String attentionStartAt) {
		this.attentionStartAt = attentionStartAt;
	}
	public String getAttentionEndAt() {
		return attentionEndAt;
	}
	public void setAttentionEndAt(String attentionEndAt) {
		this.attentionEndAt = attentionEndAt;
	}
	public String getCancelSubscribeStartAt() {
		return cancelSubscribeStartAt;
	}
	public void setCancelSubscribeStartAt(String cancelSubscribeStartAt) {
		this.cancelSubscribeStartAt = cancelSubscribeStartAt;
	}
	public String getCancelSubscribeEndAt() {
		return cancelSubscribeEndAt;
	}
	public void setCancelSubscribeEndAt(String cancelSubscribeEndAt) {
		this.cancelSubscribeEndAt = cancelSubscribeEndAt;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Boolean getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}
	
}
