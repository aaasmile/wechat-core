package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.MassConversationMapper;
import com.d1m.wechat.model.MassConversation;
import com.d1m.wechat.pamametermodel.MassConversationModel;
import com.d1m.wechat.service.MassConversationService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class MassConversationServiceImpl extends BaseService<MassConversation>
		implements MassConversationService {

	@Autowired
	private MassConversationMapper massConversationMapper;

	@Override
	public Mapper<MassConversation> getGenericMapper() {
		return massConversationMapper;
	}

	@Override
	public Page<MassConversation> search(Integer wechatId,
			MassConversationModel massConversationModel, boolean queryCount) {
		if (massConversationModel == null) {
			massConversationModel = new MassConversationModel();
		}
		if (massConversationModel.pagable()) {
			PageHelper.startPage(massConversationModel.getPageNum(),
					massConversationModel.getPageSize(), queryCount);
		}
		return massConversationMapper.search(wechatId,
				massConversationModel.getConversationId(),
				massConversationModel.getSortName(),
				massConversationModel.getSortDir());
	}

}
