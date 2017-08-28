package com.d1m.wechat.service;

import com.d1m.wechat.model.User;
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.pamametermodel.WechatModel;
import com.d1m.wechat.pamametermodel.WechatTagModel;
import com.github.pagehelper.Page;

import java.util.List;

public interface WechatService extends IService<Wechat> {

	Wechat getByOpenId(String openId);

	Page<Wechat> search(WechatModel wechatModel, boolean queryCount);
	
	int insert(WechatTagModel wechatTagModel, User user);
	
	Wechat getById(Integer id);
	
	int update(Integer id, WechatTagModel wechatTagModel, User user);

	int updateStatus(Integer id, Byte statusId);

	Integer getIsSystemRole(User user);

	void updateIsUse(User user, Integer wechatId);

	List<Wechat> getWechatList();

}
