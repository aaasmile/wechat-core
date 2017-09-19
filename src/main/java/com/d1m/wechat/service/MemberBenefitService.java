package com.d1m.wechat.service;

import com.d1m.wechat.dto.benefit.MemberBenefitDetailDto;
import com.d1m.wechat.dto.benefit.MemberBenefitDto;
import com.d1m.wechat.pamametermodel.MemberBenefitListModel;
import com.github.pagehelper.Page;

/**
 * 
 * @author D1M
 *
 */
public interface MemberBenefitService extends IService<MemberBenefitDto> {

	Page<MemberBenefitDto> search(Integer wechatId, MemberBenefitListModel memberBenefitListModel);

	MemberBenefitDetailDto getMemberBenefitDetailDto(Integer wechatId, Integer id);
	
	
}
