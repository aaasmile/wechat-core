package com.d1m.wechat.pamametermodel;

public class MemberModel {

	private String nickname;

	private Integer activityStartAt;

	private Integer activityEndAt;

	private String attentionStartAt;

	private String attentionEndAt;

	private Integer batchSendOfMonthStartAt;

	private Integer batchSendOfMonthEndAt;

	private String cancelSubscribeStartAt;

	private String cancelSubscribeEndAt;

	private Integer country;

	private Integer province;

	private Integer city;

	private Byte sex;

	private Boolean subscribe;

	private String mobile;

	private Integer[] memberTags;

	private String openId;

	private Integer[] memberIds;

	/**
	 * 1:不活跃,2:活跃
	 */
	private Boolean isOnline;

	public Integer getActivityEndAt() {
		return activityEndAt;
	}

	public Integer getActivityStartAt() {
		return activityStartAt;
	}

	public String getAttentionEndAt() {
		return attentionEndAt;
	}

	public String getAttentionStartAt() {
		return attentionStartAt;
	}

	public Integer getBatchSendOfMonthEndAt() {
		return batchSendOfMonthEndAt;
	}

	public Integer getBatchSendOfMonthStartAt() {
		return batchSendOfMonthStartAt;
	}

	public String getCancelSubscribeEndAt() {
		return cancelSubscribeEndAt;
	}

	public String getCancelSubscribeStartAt() {
		return cancelSubscribeStartAt;
	}

	public Integer getCity() {
		return city;
	}

	public Integer getCountry() {
		return country;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public Integer[] getMemberIds() {
		return memberIds;
	}

	public Integer[] getMemberTags() {
		return memberTags;
	}

	public String getMobile() {
		return mobile;
	}

	public String getNickname() {
		return nickname;
	}

	public String getOpenId() {
		return openId;
	}

	public Integer getProvince() {
		return province;
	}

	public Byte getSex() {
		return sex;
	}

	public Boolean getSubscribe() {
		return subscribe;
	}

	public void setActivityEndAt(Integer activityEndAt) {
		this.activityEndAt = activityEndAt;
	}

	public void setActivityStartAt(Integer activityStartAt) {
		this.activityStartAt = activityStartAt;
	}

	public void setAttentionEndAt(String attentionEndAt) {
		this.attentionEndAt = attentionEndAt;
	}

	public void setAttentionStartAt(String attentionStartAt) {
		this.attentionStartAt = attentionStartAt;
	}

	public void setBatchSendOfMonthEndAt(Integer batchSendOfMonthEndAt) {
		this.batchSendOfMonthEndAt = batchSendOfMonthEndAt;
	}

	public void setBatchSendOfMonthStartAt(Integer batchSendOfMonthStartAt) {
		this.batchSendOfMonthStartAt = batchSendOfMonthStartAt;
	}

	public void setCancelSubscribeEndAt(String cancelSubscribeEndAt) {
		this.cancelSubscribeEndAt = cancelSubscribeEndAt;
	}

	public void setCancelSubscribeStartAt(String cancelSubscribeStartAt) {
		this.cancelSubscribeStartAt = cancelSubscribeStartAt;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	public void setMemberIds(Integer[] memberIds) {
		this.memberIds = memberIds;
	}

	public void setMemberTags(Integer[] memberTags) {
		this.memberTags = memberTags;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public void setSubscribe(Boolean subscribe) {
		this.subscribe = subscribe;
	}

}
