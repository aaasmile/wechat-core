package com.d1m.wechat.pamametermodel;

import java.util.List;

import com.d1m.wechat.dto.MemberTagDto;

public class ActionEngineCondition {

	private Integer[] gender;

	private Byte[] language;

	private Integer[] mobileStatus;

	private String dateOfBirthStart;

	private String dateOfBirthEnd;

	private Integer hasMobile;

	private Boolean isFollowed;

	private Integer[] memberTagIds;

	private List<MemberTagDto> memberTags;

	public String getDateOfBirthEnd() {
		return dateOfBirthEnd;
	}

	public String getDateOfBirthStart() {
		return dateOfBirthStart;
	}

	public Integer[] getGender() {
		return gender;
	}

	public Integer getHasMobile() {
		return hasMobile;
	}

	public Byte[] getLanguage() {
		return language;
	}

	public Integer[] getMemberTagIds() {
		return memberTagIds;
	}

	public List<MemberTagDto> getMemberTags() {
		return memberTags;
	}

	public Integer[] getMobileStatus() {
		return mobileStatus;
	}

	public void setDateOfBirthEnd(String dateOfBirthEnd) {
		this.dateOfBirthEnd = dateOfBirthEnd;
	}

	public void setDateOfBirthStart(String dateOfBirthStart) {
		this.dateOfBirthStart = dateOfBirthStart;
	}

	public void setGender(Integer[] gender) {
		this.gender = gender;
	}

	public void setHasMobile(Integer hasMobile) {
		this.hasMobile = hasMobile;
	}

	public void setLanguage(Byte[] language) {
		this.language = language;
	}

	public void setMemberTagIds(Integer[] memberTagIds) {
		this.memberTagIds = memberTagIds;
	}

	public void setMemberTags(List<MemberTagDto> memberTags) {
		this.memberTags = memberTags;
	}

	public void setMobileStatus(Integer[] mobileStatus) {
		this.mobileStatus = mobileStatus;
	}

	public void setStringOfBirthStart(String dateOfBirthStart) {
		this.dateOfBirthStart = dateOfBirthStart;
	}

	public Boolean getIsFollowed() {
		return isFollowed;
	}

	public void setIsFollowed(Boolean isFollowed) {
		this.isFollowed = isFollowed;
	}
}
