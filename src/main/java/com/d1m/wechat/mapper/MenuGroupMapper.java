package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.MenuDto;
import com.d1m.wechat.dto.MenuGroupDto;
import com.d1m.wechat.dto.ReportMenuGroupDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.MenuGroup;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface MenuGroupMapper extends MyMapper<MenuGroup> {

	Integer getPersonalizedMenugroupCount(@Param("wechatId") Integer wechatId);

	MenuGroup getDefaultMenuGroup(@Param("wechatId") Integer wechatId);

	Page<MenuGroupDto> search(@Param("wechatId") Integer wechatId,
			@Param("status") byte status);

	MenuGroupDto get(@Param("wechatId") Integer wechatId,
			@Param("menuGroupId") Integer menuGroupId,
			@Param("status") byte status);

	Page<MenuDto> getRootMenus(@Param("menuGroupId") Integer menuGroupId);

	List<ReportMenuGroupDto> reportMenuGroupList(@Param("wechatId") Integer wechatId)
			throws WechatException;
	
}