package com.d1m.wechat.dto.benefit;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;

import lombok.Data;

@Data
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
	
}
