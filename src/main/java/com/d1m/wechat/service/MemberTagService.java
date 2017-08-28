package com.d1m.wechat.service;

import java.util.Date;
import java.util.List;

import com.d1m.wechat.dto.MemberTagDto;
import com.d1m.wechat.dto.ReportMemberTagDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.AddMemberTagModel;
import com.d1m.wechat.pamametermodel.MemberTagModel;
import com.github.pagehelper.Page;

public interface MemberTagService extends IService<MemberTag> {

	MemberTag get(Integer wechatId, Integer id);

	List<MemberTagDto> create(User user, Integer wechatId,
			AddMemberTagModel tags) throws WechatException;

	MemberTag update(User user, Integer wechatId, Integer id,
			Integer memberTagTypeId, String name) throws WechatException;

	void delete(User user, Integer wechatId, Integer id) throws WechatException;

	Page<MemberTag> search(Integer wechatId, Integer memberTagTypeId,
			String name, String sortName, String sortDir, Integer pageNum,
			Integer pageSize, boolean queryCount);

	Page<ReportMemberTagDto> tagUser(Integer wechatId, Date start, Date end,
			Integer top);

	void csvAddMemberTag(Integer wechatId, String uploadPath, Integer userId, String oriFileName, String csv, String csvName);

	MemberTag selectOne(MemberTag memberTag);
	
	List<MemberTagDto> getAllMemberTags(Integer wechatId);

	void moveTag(Integer wechatId, MemberTagModel model);

	List<MemberTagDto> searchName(Integer wechatId, MemberTagModel model);

	List<MemberTag> exportList(Integer wechatId, MemberTagModel model);
	
}
