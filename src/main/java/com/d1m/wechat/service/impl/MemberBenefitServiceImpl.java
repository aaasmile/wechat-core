package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.d1m.wechat.dto.benefit.MemberBenefitDto;
import com.d1m.wechat.mapper.MemberBenefitMapper;
import com.d1m.wechat.pamametermodel.MemberBenefitListModel;
import com.d1m.wechat.service.MemberBenefitService;
import com.d1m.wechat.util.DateUtil;
import com.github.pagehelper.Page;

import tk.mybatis.mapper.common.Mapper;

@Service
public class MemberBenefitServiceImpl extends BaseService<MemberBenefitDto> implements MemberBenefitService {
	
	@Autowired
	private MemberBenefitMapper memberBenefitMapper;
	
	@Override
	public Page<MemberBenefitDto> search(Integer wechatId, MemberBenefitListModel memberBenefitListModel) {
		Page<MemberBenefitDto> page = null;
		
		long total = memberBenefitMapper.count(wechatId, 
				memberBenefitListModel.getNickname(), 
				memberBenefitListModel.getSex(),
				memberBenefitListModel.getCountry(), 
				memberBenefitListModel.getProvince(),
				memberBenefitListModel.getCity(), 
				memberBenefitListModel.getSubscribe(),
				memberBenefitListModel.getActivityStartAt(), 
				memberBenefitListModel.getActivityEndAt(), 
				memberBenefitListModel.getBatchSendOfMonthStartAt(), 
				memberBenefitListModel.getBatchSendOfMonthEndAt(), 
				DateUtil.getDateBegin(DateUtil.parse(memberBenefitListModel
								.getAttentionStartAt())), 
				DateUtil.getDateEnd(DateUtil.parse(memberBenefitListModel
								.getAttentionEndAt())), 
				DateUtil.getDateBegin(DateUtil.parse(memberBenefitListModel
								.getCancelSubscribeStartAt())), 
				DateUtil.getDateEnd(DateUtil.parse(memberBenefitListModel
								.getCancelSubscribeEndAt())), 
				memberBenefitListModel.getMobile(),
				memberBenefitListModel.getIsOnline(),
				DateUtil.getDate(-2),
				memberBenefitListModel.getSortName(), 
				memberBenefitListModel.getSortDir()
		);
		
		Integer pageSize = memberBenefitListModel.getPageSize();
		Integer offset = (memberBenefitListModel.getPageNum() - 1) * pageSize;
		
		page = memberBenefitMapper.search(
				wechatId, 
				memberBenefitListModel.getNickname(), 
				memberBenefitListModel.getSex(),
				memberBenefitListModel.getCountry(), 
				memberBenefitListModel.getProvince(),
				memberBenefitListModel.getCity(), 
				memberBenefitListModel.getSubscribe(),
				memberBenefitListModel.getActivityStartAt(), 
				memberBenefitListModel.getActivityEndAt(), 
				memberBenefitListModel.getBatchSendOfMonthStartAt(), 
				memberBenefitListModel.getBatchSendOfMonthEndAt(), 
				DateUtil.getDateBegin(DateUtil.parse(memberBenefitListModel
								.getAttentionStartAt())), 
				DateUtil.getDateEnd(DateUtil.parse(memberBenefitListModel
								.getAttentionEndAt())), 
				DateUtil.getDateBegin(DateUtil.parse(memberBenefitListModel
								.getCancelSubscribeStartAt())), 
				DateUtil.getDateEnd(DateUtil.parse(memberBenefitListModel
								.getCancelSubscribeEndAt())), 
				memberBenefitListModel.getMobile(),
				memberBenefitListModel.getIsOnline(),
				DateUtil.getDate(-2),
				memberBenefitListModel.getSortName(), 
				memberBenefitListModel.getSortDir(),
				offset, 
				pageSize);
		
		page.setTotal(total);
		return page;
	}

	@Override
	public Mapper<MemberBenefitDto> getGenericMapper() {
		return memberBenefitMapper;
	}

	@Override
	public MemberBenefitDto getMemberBenefitDto(Integer wechatId, Integer id) {
		
		return memberBenefitMapper.getById(wechatId, id);
	}

	
}

