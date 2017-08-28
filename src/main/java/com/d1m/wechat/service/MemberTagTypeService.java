package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.dto.MemberTagTypeDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.MemberTagType;
import com.d1m.wechat.model.User;
import com.github.pagehelper.Page;

public interface MemberTagTypeService extends IService<MemberTagType> {

	MemberTagType create(User user, Integer wechatId, String name, Integer parentId)
			throws WechatException;

	MemberTagType update(User user, Integer wechatId, Integer id, String name)
			throws WechatException;

	void delete(User user, Integer wechatId, Integer id) throws WechatException;

	Page<MemberTagType> search(Integer wechatId, String name, String sortName,
			String sortDir, Integer pageNum, Integer pageSize,
			boolean queryCount);

	MemberTagType selectOne(MemberTagType memberTagType);

	Integer getDefaultId();
	
	List<MemberTagTypeDto> selectAllTagTypes(Integer wechatId);
}
