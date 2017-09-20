package com.d1m.wechat.dto.benefit;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import lombok.Data;

@Data
public class MemberBenefitDetailDto extends MemberBenefitDto {
	
	private MemberBenefitDto invitedBy;
	
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
	
}
