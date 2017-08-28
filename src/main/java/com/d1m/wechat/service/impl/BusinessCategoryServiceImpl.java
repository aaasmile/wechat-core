package com.d1m.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.BusinessCategoryDto;
import com.d1m.wechat.mapper.BusinessCategoryMapper;
import com.d1m.wechat.model.BusinessCategory;
import com.d1m.wechat.service.BusinessCategoryService;

@Service
public class BusinessCategoryServiceImpl extends BaseService<BusinessCategory>
		implements BusinessCategoryService {

	@Autowired
	private BusinessCategoryMapper businessCategoryMapper;

	public void setBusinessCategoryMapper(
			BusinessCategoryMapper businessCategoryMapper) {
		this.businessCategoryMapper = businessCategoryMapper;
	}

	@Override
	public Mapper<BusinessCategory> getGenericMapper() {
		// TODO Auto-generated method stub
		return businessCategoryMapper;
	}

	@Override
	public List<BusinessCategoryDto> list() {
		
		return businessCategoryMapper.list();
	}

	/*@Override
	public BusinessCategory create(String name, Integer parentId)
			throws WechatException {
		notBlank(name, Message.BUSINESS_CATEGORY_NAME_NOT_BLANK);
		Integer count = businessCategoryMapper.countDuplicateName(null, name,
				parentId, QrcodeTypeStatus.INUSED.getValue());
		if (count > 0) {
			throw new WechatException(Message.BUSINESS_CATEGORY_NAME_EXIST);
		}
		checkParentExist(parentId);
		BusinessCategory businessCategory = new BusinessCategory();
		businessCategory.setName(name);
		businessCategory.setParentId(parentId);
		businessCategory.setStatus(BusinessCategoryStatus.INUSED.getValue());
		businessCategory.setCreatedAt(new Date());

		businessCategoryMapper.insert(businessCategory);
		return businessCategory;

	}

	@Override
	public Page<BusinessCategoryDto> list(String name, Integer parentId,
			String sortName, String sortDir, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize, true);
		return businessCategoryMapper.list(name, parentId, sortName, sortDir);
	}

	private void checkParentExist(Integer parentId) throws WechatException {
		if (parentId == null) {
			return;
		}
		BusinessCategory record = new BusinessCategory();
		record.setId(parentId);
		BusinessCategory parent = businessCategoryMapper.selectOne(record);
		notBlank(parent, Message.BUSINESS_CATEGORY_NAME_NOT_BLANK);
	}*/

}
