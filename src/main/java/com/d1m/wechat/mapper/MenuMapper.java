package com.d1m.wechat.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.ReportMenuBaseDto;
import com.d1m.wechat.dto.ReportMenuChildDto;
import com.d1m.wechat.dto.ReportMenuDto;
import com.d1m.wechat.model.Menu;
import com.d1m.wechat.util.MyMapper;

public interface MenuMapper extends MyMapper<Menu> {

	List<Menu> list(@Param("wechatId") Integer wechatId,
			@Param("menuGroupId") Integer menuGroupId,
			@Param("status") byte status, @Param("parentId") Integer parentId);

	List<ReportMenuBaseDto> menuReport(
			@Param("wechatId") Integer wechatId, 
			@Param("groupId") Integer groupId, 
			@Param("status") Integer status, 
			@Param("start") Date start,
			@Param("end") Date end);
	
	List<ReportMenuBaseDto> getSubMenuList(
			@Param("wechatId") Integer wechatId, 
			@Param("groupId") Integer groupId, 
			@Param("status") Integer status);
	
	ReportMenuDto menuGroupReport(
			@Param("wechatId") Integer wechatId, @Param("start") Date start,
			@Param("end") Date end);
	
	
	List<ReportMenuChildDto> menuChildReport(
			@Param("wechatId") Integer wechatId, 
			@Param("groupId") Integer groupId, 
			@Param("start") Date start,
			@Param("end") Date end);

	List<ReportMenuBaseDto> getMainMenuList(@Param("wechatId") Integer wechatId, 
			@Param("groupId") Integer groupId);

	Menu getMatchUrl(@Param("wechatId") Integer wechatId, @Param("url") String url, 
			@Param("status") byte status, @Param("groupId") Integer groupId);

	List<ReportMenuBaseDto> menuUserReport(
			@Param("wechatId") Integer wechatId, 
			@Param("groupId") Integer groupId, 
			@Param("status") Integer status, 
			@Param("start") Date start,
			@Param("end") Date end);
	
	
}