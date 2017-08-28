package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.dto.BusinessDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.Business;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.BusinessModel;
import com.github.pagehelper.Page;

public interface BusinessService extends IService<Business> {

	Business create(Integer wechatId, User user, BusinessModel model)
			throws WechatException;

	BusinessDto get(Integer wechatId, Integer id) throws WechatException;

	Page<BusinessDto> search(Integer wechatId, BusinessModel businessModel,
			boolean queryCount) throws WechatException;

	void delete(Integer wechatId, BusinessModel model) throws WechatException;

	Business update(Integer wechatId, BusinessModel businessModel)
			throws WechatException;

	Business getBusinessByCode(Integer wechatId, String code);
	
	List<BusinessDto> searchByLngLat(Integer wechatId, Double lng, Double lat,
			Integer size) throws WechatException;

	void pushBusinessToWx(Integer wechatId, Business business, BusinessModel model);

	void initBusinessLatAndLng(Integer wechatId, User user);

}