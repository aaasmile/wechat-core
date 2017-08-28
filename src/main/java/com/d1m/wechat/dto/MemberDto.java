package com.d1m.wechat.dto;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.DateUtil;

public class MemberDto {

	private Integer id;

	private String unionId;

	private String openId;

	private String nickname;

	private Byte sex;

	private String language;

	private String city;

	private String province;

	private String country;

	private String localHeadImgUrl;

	private String headImgUrl;

	private String createdAt;

	private Integer wechatId;

	private Integer memberGroupId;

	private Byte activity;

	private Integer batchsendMonth;

	private Date subscribeAt;

	private Date unsubscribeAt;

	private String fromwhere;

	private String mobile;

	private String firstSubscribeIp;

	private List<MemberTagDto> memberTags;

	private Boolean isSubscribe;

	private Integer conversationCount;

	private Date lastConversationAt;
	
	private String level;
	
	private Integer credits;
	
	private Integer bindStatus;

	/**
	 * 是否在线(48小时内发过消息并且是已关注状态)
	 */
	private boolean online;

	public Byte getActivity() {
		return activity;
	}

	public Integer getBatchsendMonth() {
		return batchsendMonth;
	}

	public String getCity() {
		return city;
	}

	public Integer getConversationCount() {
		return conversationCount;
	}

	public String getCountry() {
		return country;
	}

	public String getCreatedAt() {
		return DateUtil.formatYYYYMMDDHHMMSS(DateUtil.parse(createdAt));
	}

	public String getFirstSubscribeIp() {
		return firstSubscribeIp;
	}

	public String getFromwhere() {
		return fromwhere;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public Integer getId() {
		return id;
	}

	public Boolean getIsSubscribe() {
		return isSubscribe;
	}

	public String getLanguage() {
		return language;
	}

	public Date getLastConversationAt() {
		return lastConversationAt;
	}

	public String getLocalHeadImgUrl() {
		return localHeadImgUrl;
	}

	public Integer getMemberGroupId() {
		return memberGroupId;
	}

	public List<MemberTagDto> getMemberTags() {
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

	public String getProvince() {
		return province;
	}

	public Byte getSex() {
		return sex;
	}

	public Date getSubscribeAt() {
		return subscribeAt;
	}

	public String getUnionId() {
		return unionId;
	}

	public Date getUnsubscribeAt() {
		return unsubscribeAt;
	}

	public Integer getWechatId() {
		return wechatId;
	}

	public boolean isOnline() {
		return (isSubscribe != null && isSubscribe)
				&& (lastConversationAt != null && lastConversationAt
						.compareTo(DateUtil.changeDate(new Date(),
								Calendar.HOUR_OF_DAY,
								-Constants.CONVERSATION_HOUR_LIMIT)) >= 0);
	}

	public void setActivity(Byte activity) {
		this.activity = activity;
	}

	public void setBatchsendMonth(Integer batchsendMonth) {
		this.batchsendMonth = batchsendMonth;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public void setConversationCount(Integer conversationCount) {
		this.conversationCount = conversationCount;
	}

	public void setCountry(String country) {
		this.country = country == null ? null : country.trim();
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setFirstSubscribeIp(String firstSubscribeIp) {
		this.firstSubscribeIp = firstSubscribeIp;
	}

	public void setFromwhere(String fromwhere) {
		this.fromwhere = fromwhere == null ? null : fromwhere.trim();
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl == null ? null : headImgUrl.trim();
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIsSubscribe(Boolean isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public void setLanguage(String language) {
		this.language = language == null ? null : language.trim();
	}

	public void setLastConversationAt(Date lastConversationAt) {
		this.lastConversationAt = lastConversationAt;
	}

	public void setLocalHeadImgUrl(String localHeadImgUrl) {
		this.localHeadImgUrl = localHeadImgUrl == null ? null : localHeadImgUrl
				.trim();
	}

	public void setMemberGroupId(Integer memberGroupId) {
		this.memberGroupId = memberGroupId;
	}

	public void setMemberTags(List<MemberTagDto> memberTags) {
		this.memberTags = memberTags;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public void setOpenId(String openId) {
		this.openId = openId == null ? null : openId.trim();
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public void setSubscribeAt(Date subscribeAt) {
		this.subscribeAt = subscribeAt;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId == null ? null : unionId.trim();
	}

	public void setUnsubscribeAt(Date unsubscribeAt) {
		this.unsubscribeAt = unsubscribeAt;
	}

	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

	public String getLevel() {
		return level;
	}

	public Integer getCredits() {
		return credits;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	public Integer getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}
}
