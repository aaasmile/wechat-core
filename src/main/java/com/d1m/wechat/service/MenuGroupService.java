package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.dto.MenuGroupDto;
import com.d1m.wechat.dto.ReportMenuGroupDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.MenuGroup;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.MenuGroupModel;
import com.github.pagehelper.Page;

public interface MenuGroupService extends IService<MenuGroup> {

	MenuGroup getDefaultMenuGroup(User user, Integer wechatId);

	MenuGroup create(User user, Integer wechatId, MenuGroupModel menuGroupModel)
			throws WechatException;

	MenuGroup update(User user, Integer wechatId, Integer menuGroupId,
			MenuGroupModel menuGroupModel) throws WechatException;

	MenuGroup delete(User user, Integer wechatId, Integer menuGroupId)
			throws WechatException;

	Page<MenuGroupDto> find(Integer wechatId, MenuGroupModel menuGroupModel,
			boolean queryCount);

	MenuGroupDto get(Integer wechatId, Integer menuGroupId);

	void pushMenuGroupToWx(Integer wechatId, Integer menuGroupId)
			throws WechatException;

	List<ReportMenuGroupDto> menuGroupList(Integer wechatId)
			throws WechatException;
}
