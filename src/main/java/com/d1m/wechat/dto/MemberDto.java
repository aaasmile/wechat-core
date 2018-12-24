package com.d1m.wechat.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;

import com.d1m.wechat.model.enums.Sex;
import com.d1m.wechat.service.AreaInfoService;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.I18nUtil;

public class MemberDto {

	private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
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
	// 绑定时间 对应member表bind_at字段
	private Date bindAt;
	// 渠道来源
	private String subscribeScene;
	// 二维码扫码场景
	private Integer qrScene;

	// 二维码扫码场景描述
	private String qrSceneStr;

	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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
		return (isSubscribe != null && isSubscribe) && (lastConversationAt != null && lastConversationAt.compareTo(
				DateUtil.changeDate(new Date(), Calendar.HOUR_OF_DAY, -Constants.CONVERSATION_HOUR_LIMIT)) >= 0);
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
		this.localHeadImgUrl = localHeadImgUrl == null ? null : localHeadImgUrl.trim();
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

	public Date getBindAt() {
		return bindAt;
	}

	public void setBindAt(Date bindAt) {
		this.bindAt = bindAt;
	}

	public String getSubscribeScene() {
		return subscribeScene;
	}

	public void setSubscribeScene(String subscribeScene) {
		this.subscribeScene = subscribeScene;
	}

	public Integer getQrScene() {
		return qrScene;
	}

	public void setQrScene(Integer qrScene) {
		this.qrScene = qrScene;
	}

	public String getQrSceneStr() {
		return qrSceneStr;
	}

	public void setQrSceneStr(String qrSceneStr) {
		this.qrSceneStr = qrSceneStr;
	}

	/**
	 * 条件查询，不建议全量导出
	 * @param dataRow
	 * @param locale
	 * @param areaInfoService
	 * @param lang
	 */
	public void fillRows(Row dataRow, Locale locale, AreaInfoService areaInfoService, String lang) {
		String attentionStatus = "subscribe";
		dataRow.createCell(1).setCellValue(nickname);
		if (sex != null) {
			dataRow.createCell(2).setCellValue(I18nUtil.getMessage(Sex.getByValue(sex).name().toLowerCase(), locale));
		}
        if (province != null) {
            province = areaInfoService.selectNameById(Integer.parseInt(province), lang);
        }
        if (city != null) {
        	city = areaInfoService.selectNameById(Integer.parseInt(city), lang);
        }
        
		dataRow.createCell(3).setCellValue(mobile);
		dataRow.createCell(4).setCellValue(province);
		dataRow.createCell(5).setCellValue(city);

		if (isSubscribe != null && !isSubscribe) {
			if (unsubscribeAt != null) {
				attentionStatus = "cancel.subscribe";
			} else {
				attentionStatus = "unsubscribe";
			}
		}
		dataRow.createCell(6).setCellValue(I18nUtil.getMessage(attentionStatus, locale));

		if (bindStatus != null && bindStatus == 1) {
			dataRow.createCell(7).setCellValue(I18nUtil.getMessage("bind", locale));
		} else {
			dataRow.createCell(7).setCellValue(I18nUtil.getMessage("unbind", locale));
		}

		if (subscribeAt != null) {
			dataRow.createCell(8).setCellValue(DF.format(subscribeAt));
		}

		dataRow.createCell(9).setCellValue(batchsendMonth == null ? 0 : batchsendMonth);
		StringBuffer tags = new StringBuffer();
		if (memberTags != null && !memberTags.isEmpty()) {
			for (MemberTagDto mt : memberTags) {
				tags.append(mt.getName()).append(" | ");
			}
		}
		dataRow.createCell(10).setCellValue(tags.toString());
		dataRow.createCell(11).setCellValue(openId);
		if(bindAt != null) {
			dataRow.createCell(12).setCellValue(DF.format(bindAt));
		}
		if (getUnsubscribeAt() != null) {
			dataRow.createCell(13).setCellValue(DF.format(unsubscribeAt));
		}
		if(unionId != null) {
			dataRow.createCell(14).setCellValue(unionId);
		}
	}
	
	public void MemberToMap(Map<String, String> wechatMessage) {
		wechatMessage.put("memberId", this.id == null ? "-1" : String.valueOf(this.id));
		wechatMessage.put("unionId", this.unionId);
		wechatMessage.put("openId", this.openId);
		wechatMessage.put("nickname", this.nickname);
		wechatMessage.put("localHeadImgUrl", this.localHeadImgUrl);
		wechatMessage.put("sex", this.sex == null ? "0" : Sex.getByValue(this.sex).getName());
	}
}
