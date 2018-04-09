package com.d1m.wechat.dto.benefit;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;


public class MemberBenefitDto {
	
	private Integer id;

	private String unionId;

	private String openId;

	private String nickname;

	private Byte sex;

	private Integer city = 0;

	private Integer province = 0;

	private Integer country = 0;

	private String headImgUrl;

	private String createdAt;

	private Integer wechatId;

	private String mobile;

	private Boolean isSubscribe;

	private Boolean bindingOver;
	
	private Boolean registerOver;
	
	private String birthday;
	
	private Boolean surveyOver;
	
	private String badges;
	
	private Integer manualCountry;
	
	private Integer manualProvince;
	
	private Integer manualCity;
	
	private Integer pointsBalance;
	
	private Byte activity;
	
	@Column(name = "last_conversation_at")
	private Date lastConversationAt;

	@Column(name = "subscribe_at")
	private Date subscribeAt;
	
	private Boolean isOnline = false;
	
	private Boolean invited = false;
	
	private Integer invitedById;
	
    public Boolean getOnline() {
      return (this.getIsSubscribe() != null && this.getIsSubscribe())
    		  && (this.getLastConversationAt() != null && 
    		  this.getLastConversationAt().compareTo(changeDate(new Date(),
										              Calendar.HOUR_OF_DAY,
										              -48)) >= 0);
	}

	public Date changeDate(Date date, int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);
        return cal.getTime();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

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

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCountry() {
		return country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getWechatId() {
		return wechatId;
	}

	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(Boolean isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public Boolean getBindingOver() {
		return bindingOver;
	}

	public void setBindingOver(Boolean bindingOver) {
		this.bindingOver = bindingOver;
	}

	public Boolean getRegisterOver() {
		return registerOver;
	}

	public void setRegisterOver(Boolean registerOver) {
		this.registerOver = registerOver;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Boolean getSurveyOver() {
		return surveyOver;
	}

	public void setSurveyOver(Boolean surveyOver) {
		this.surveyOver = surveyOver;
	}

	public String getBadges() {
		return badges;
	}

	public void setBadges(String badges) {
		this.badges = badges;
	}

	public Integer getManualCountry() {
		return manualCountry;
	}

	public void setManualCountry(Integer manualCountry) {
		this.manualCountry = manualCountry;
	}

	public Integer getManualProvince() {
		return manualProvince;
	}

	public void setManualProvince(Integer manualProvince) {
		this.manualProvince = manualProvince;
	}

	public Integer getManualCity() {
		return manualCity;
	}

	public void setManualCity(Integer manualCity) {
		this.manualCity = manualCity;
	}

	public Integer getPointsBalance() {
		return pointsBalance;
	}

	public void setPointsBalance(Integer pointsBalance) {
		this.pointsBalance = pointsBalance;
	}

	public Byte getActivity() {
		return activity;
	}

	public void setActivity(Byte activity) {
		this.activity = activity;
	}

	public Date getLastConversationAt() {
		return lastConversationAt;
	}

	public void setLastConversationAt(Date lastConversationAt) {
		this.lastConversationAt = lastConversationAt;
	}

	public Date getSubscribeAt() {
		return subscribeAt;
	}

	public void setSubscribeAt(Date subscribeAt) {
		this.subscribeAt = subscribeAt;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	public Boolean getInvited() {
		return invited;
	}

	public void setInvited(Boolean invited) {
		this.invited = invited;
	}

	public Integer getInvitedById() {
		return invitedById;
	}

	public void setInvitedById(Integer invitedById) {
		this.invitedById = invitedById;
	}
}
