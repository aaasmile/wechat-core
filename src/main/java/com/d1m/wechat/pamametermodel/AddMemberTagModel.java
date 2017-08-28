package com.d1m.wechat.pamametermodel;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.ParamUtil;

public class AddMemberTagModel extends BaseModel {

	private Integer[] activity;

	private String attentionStartTime;

	private String attentionEndTime;

	private Integer[] batchSendOfMonth;

	private String cancelStartTime;

	private String cancelEndTime;

	private Integer country;

	private Integer province;

	private Integer city;

	private String nickname;

	private String sex;

	private Boolean subscribe;

	private Integer[] memberIds;

	private List<MemberTagModel> memberTags;

	private String mobile;

	private List<MemberTagModel> tags;

	private String openId;

	private Boolean sendToAll;
	
	private Integer bindStatus;

	/**
	 * 1:不活跃,2:活跃
	 */
	private Boolean isOnline;

	public boolean emptyQuery() {
		return activity == null && StringUtils.isBlank(attentionStartTime)
				&& StringUtils.isBlank(attentionEndTime)
				&& batchSendOfMonth == null
				&& StringUtils.isBlank(cancelStartTime)
				&& StringUtils.isBlank(cancelEndTime) && country == null
				&& province == null && city == null
				&& StringUtils.isBlank(nickname) && StringUtils.isBlank(sex)
				&& subscribe == null && memberIds == null
				&& (memberTags == null || memberTags.isEmpty())
				&& StringUtils.isBlank(mobile) && isOnline == null;
	}

	public Integer[] getActivity() {
		return activity;
	}

	public String getAttentionEndTime() {
		return attentionEndTime;
	}

	public String getAttentionStartTime() {
		return attentionStartTime;
	}

	public Integer[] getBatchSendOfMonth() {
		return batchSendOfMonth;
	}

	public String getCancelEndTime() {
		return cancelEndTime;
	}

	public String getCancelStartTime() {
		return cancelStartTime;
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

	public MemberModel getMemberModel() {
		AddMemberTagModel addMemberTagModel = this;
		MemberModel mm = new MemberModel();
		if (addMemberTagModel.getActivity() != null) {
			if (addMemberTagModel.getActivity().length > 0) {
				mm.setActivityStartAt(addMemberTagModel.getActivity()[0]);
			}
			if (addMemberTagModel.getActivity().length > 1) {
				mm.setActivityEndAt(addMemberTagModel.getActivity()[1]);
			}
		}
		mm.setAttentionStartAt(DateUtil.utc2DefaultLocal(addMemberTagModel
				.getAttentionStartTime()));
		mm.setAttentionEndAt(DateUtil.utc2DefaultLocal(addMemberTagModel
				.getAttentionEndTime()));
		if (addMemberTagModel.getBatchSendOfMonth() != null) {
			if (addMemberTagModel.getBatchSendOfMonth().length > 0) {
				mm.setBatchSendOfMonthStartAt(addMemberTagModel
						.getBatchSendOfMonth()[0]);
			}
			if (addMemberTagModel.getBatchSendOfMonth().length > 1) {
				mm.setBatchSendOfMonthEndAt(addMemberTagModel
						.getBatchSendOfMonth()[1]);
			}
		}
		mm.setCancelSubscribeStartAt(DateUtil
				.utc2DefaultLocal(addMemberTagModel.getCancelStartTime()));
		mm.setCancelSubscribeEndAt(DateUtil.utc2DefaultLocal(addMemberTagModel
				.getCancelEndTime()));
		if (addMemberTagModel.getCountry() != null) {
			mm.setCountry(addMemberTagModel.getCountry());
		}
		if (addMemberTagModel.getProvince() != null) {
			mm.setProvince(addMemberTagModel.getProvince());
		}
		if (addMemberTagModel.getCity() != null) {
			mm.setCity(addMemberTagModel.getCity());
		}
		mm.setNickname(addMemberTagModel.getNickname());
		if (StringUtils.isNotBlank(addMemberTagModel.getSex())) {
			mm.setSex(ParamUtil.getByte(addMemberTagModel.getSex(), null));
		}
		List<MemberTagModel> memberTags = addMemberTagModel.getMemberTags();
		if (memberTags != null && !memberTags.isEmpty()) {
			Integer[] memberTagsArray = new Integer[memberTags.size()];
			for (int i = 0; i < memberTags.size(); i++) {
				memberTagsArray[i] = memberTags.get(i).getId();
			}
			mm.setMemberTags(memberTagsArray);
		}
		mm.setMobile(addMemberTagModel.getMobile());
		mm.setIsOnline(addMemberTagModel.getIsOnline());
		mm.setSubscribe(addMemberTagModel.getSubscribe());
		return mm;
	}

	public List<MemberTagModel> getMemberTags() {
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

	public Boolean getSendToAll() {
		return sendToAll;
	}

	public String getSex() {
		return sex;
	}

	public Boolean getSubscribe() {
		return subscribe;
	}

	public List<MemberTagModel> getTags() {
		return tags;
	}

	public void setActivity(Integer[] activity) {
		this.activity = activity;
	}

	public void setAttentionEndTime(String attentionEndTime) {
		this.attentionEndTime = attentionEndTime;
	}

	public void setAttentionStartTime(String attentionStartTime) {
		this.attentionStartTime = attentionStartTime;
	}

	public void setBatchSendOfMonth(Integer[] batchSendOfMonth) {
		this.batchSendOfMonth = batchSendOfMonth;
	}

	public void setCancelEndTime(String cancelEndTime) {
		this.cancelEndTime = cancelEndTime;
	}

	public void setCancelStartTime(String cancelStartTime) {
		this.cancelStartTime = cancelStartTime;
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

	public void setMemberTags(List<MemberTagModel> memberTags) {
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

	public void setSendToAll(Boolean sendToAll) {
		this.sendToAll = sendToAll;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setSubscribe(Boolean subscribe) {
		this.subscribe = subscribe;
	}

	public void setTags(List<MemberTagModel> tags) {
		this.tags = tags;
	}

	public Integer getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}

}
