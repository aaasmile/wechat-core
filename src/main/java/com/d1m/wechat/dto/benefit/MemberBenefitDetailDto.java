package com.d1m.wechat.dto.benefit;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

public class MemberBenefitDetailDto extends MemberBenefitDto {
	
	private MemberBenefitDto invitedBy;
	
	private Boolean badge1;
	private Boolean badge2;
	private Boolean badge3;
	private Boolean badge4;
	
	private MemberBenefitDetailDto() {
		
	}
	
	public MemberBenefitDetailDto(MemberBenefitDto primaryDto, MemberBenefitDto invitedMember) {
		this();
		try {
			BeanUtils.copyProperties(this, primaryDto);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		this.setInvitedBy(invitedMember);
	}
	
	public void setInvitedBy(MemberBenefitDto invitedBy) {
		this.invitedBy = invitedBy;
	}

	public MemberBenefitDto getInvitedBy() {
		return invitedBy;
	}
	
	public Boolean getBadge1() {
		return getBadgeByIndex(1, getBadges());
	}

	public Boolean getBadge2() {
		return getBadgeByIndex(2, getBadges());
	}

	public Boolean getBadge3() {
		return getBadgeByIndex(3, getBadges());
	}

	public Boolean getBadge4() {
		return getBadgeByIndex(4, getBadges());
	}
	
	public void setBadge1(Boolean badge1) {
		this.badge1 = badge1;
	}
	
	public void setBadge2(Boolean badge2) {
		this.badge2 = badge2;
	}
	
	public void setBadge3(Boolean badge3) {
		this.badge3 = badge3;
	}

	public void setBadge4(Boolean badge4) {
		this.badge4 = badge4;
	}
	
	
	private static Boolean getBadgeByIndex(int index, String badges) {
		if(StringUtils.isEmpty(badges)) return false;
		
		String bs[] = badges.split("");
		
		if(index > bs.length-1) return false;
		
		for (int i = 0; i < bs.length; i++) {
			if(index == i+1) {
				String b = bs[i];
				if("1".equals(b))
					return true;
				else
					return false;
			}
		}
		
		return false;
			
	}
	
}
