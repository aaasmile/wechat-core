package com.d1m.wechat.service;

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

	MemberBenefitDto getMemberBenefitDto(Integer wechatId, Integer id);
	
	
}
