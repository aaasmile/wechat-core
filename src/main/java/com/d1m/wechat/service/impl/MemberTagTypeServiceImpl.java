package com.d1m.wechat.service.impl;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.MemberTagTypeDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.MemberTagMapper;
import com.d1m.wechat.mapper.MemberTagTypeMapper;
import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.model.MemberTagType;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.enums.MemberTagStatus;
import com.d1m.wechat.service.MemberTagTypeService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class MemberTagTypeServiceImpl extends BaseService<MemberTagType>
		implements MemberTagTypeService {

	@Autowired
	private MemberTagTypeMapper memberTagTypeMapper;
	
	@Autowired
	private MemberTagMapper memberTagMapper;

	@Override
	public Mapper<MemberTagType> getGenericMapper() {
		return memberTagTypeMapper;
	}

	public void setMemberTagTypeMapper(MemberTagTypeMapper memberTagTypeMapper) {
		this.memberTagTypeMapper = memberTagTypeMapper;
	}

	@Override
	public MemberTagType create(User user, Integer wechatId, String name, Integer parentId)
			throws WechatException {
		notBlank(name, Message.MEMBER_TAG_TYPE_NAME_NOT_EMPTY);
		MemberTagType record = new MemberTagType();
		record.setWechatId(wechatId);
		record.setName(name);
		record.setParentId(parentId);
		if (memberTagTypeMapper.selectCount(record) > 0) {
			throw new WechatException(Message.MEMBER_TAG_TYPE_NAME_EXIST);
		}
		record.setCreatedAt(new Date());
		record.setCreatorId(user.getId());
		record.setStatus(MemberTagStatus.INUSED.getValue());
		memberTagTypeMapper.insert(record);
		return record;
	}

	@Override
	public MemberTagType update(User user, Integer wechatId, Integer id,
			String name) throws WechatException {
		notBlank(id, Message.MEMBER_TAG_TYPE_ID_NOT_EMPTY);
		notBlank(name, Message.MEMBER_TAG_TYPE_NAME_NOT_EMPTY);
		MemberTagType record = new MemberTagType();
		record.setId(id);
		record.setWechatId(wechatId);
		record = memberTagTypeMapper.selectOne(record);
		notBlank(record, Message.MEMBER_TAG_TYPE_NOT_EXIST);
		//remove duplicated name checking
		checkTagTypeDuplicate(wechatId, id, name);
		record.setName(name);
		memberTagTypeMapper.updateByPrimaryKey(record);
		return record;
	}

	private void checkTagTypeDuplicate(Integer wechatId, Integer id, String name) {
		MemberTagType record = new MemberTagType();
		record.setName(name);
		record.setWechatId(wechatId);
		if(memberTagTypeMapper.selectCount(record)>1){
			throw new WechatException(Message.MEMBER_TAG_TYPE_NAME_EXIST);
		}
		record = memberTagTypeMapper.selectOne(record);
		if(record!=null && record.getId()!=id){
			throw new WechatException(Message.MEMBER_TAG_TYPE_NAME_EXIST);
		}
	}

	@Override
	public void delete(User user, Integer wechatId, Integer id)
			throws WechatException {
		notBlank(id, Message.MEMBER_TAG_TYPE_ID_NOT_EMPTY);
		MemberTagType record = new MemberTagType();
		record.setId(id);
		record.setWechatId(wechatId);
		record = memberTagTypeMapper.selectOne(record);
		notBlank(record, Message.MEMBER_TAG_TYPE_NOT_EXIST);
		
		MemberTag memberTag = new MemberTag();
		memberTag.setWechatId(wechatId);
		memberTag.setStatus(MemberTagStatus.INUSED.getValue());
		memberTag.setMemberTagTypeId(id);
		int tagCount = memberTagMapper.selectCount(memberTag);
		if(tagCount > 0){
			throw new WechatException(Message.MEMBER_TAG_TYPE_OWN_TAG);
		}
		
		/*record.setStatus(MemberTagTypeStatus.DELETED.getValue());
		memberTagTypeMapper.updateByPrimaryKey(record);*/
		memberTagTypeMapper.delete(record);
	}

	@Override
	public Page<MemberTagType> search(Integer wechatId, String name,
			String sortName, String sortDir, Integer pageNum, Integer pageSize,
			boolean queryCount) {
		PageHelper.startPage(pageNum, pageSize, queryCount);
		return memberTagTypeMapper.search(wechatId, name, sortName, sortDir);
	}

	@Override
	public MemberTagType selectOne(MemberTagType memberTagType) {
		return memberTagTypeMapper.selectOne(memberTagType);
	}

	@Override
	public Integer getDefaultId() {
		return memberTagTypeMapper.getFirst();
	}

	@Override
	public List<MemberTagTypeDto> selectAllTagTypes(Integer wechatId) {
		return memberTagTypeMapper.selectAllTagTypes(wechatId);
	}
}
