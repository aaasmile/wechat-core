package com.d1m.wechat.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.controller.menugroup.MenuGroupController;
import com.d1m.wechat.dto.ReportMenuBaseDto;
import com.d1m.wechat.dto.ReportMenuChildDto;
import com.d1m.wechat.dto.ReportMenuDto;
import com.d1m.wechat.dto.ReportMenuTrendDto;
import com.d1m.wechat.mapper.MenuGroupMapper;
import com.d1m.wechat.mapper.MenuMapper;
import com.d1m.wechat.model.Menu;
import com.d1m.wechat.model.MenuGroup;
import com.d1m.wechat.model.enums.MenuGroupStatus;
import com.d1m.wechat.model.enums.MenuStatus;
import com.d1m.wechat.service.MenuService;
import com.d1m.wechat.util.DateUtil;

@Service
public class MenuServiceImpl extends BaseService<Menu> implements MenuService {

	private Logger log = LoggerFactory.getLogger(MenuGroupController.class);
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private MenuGroupMapper menuGroupMapper;

	@Override
	public Mapper<Menu> getGenericMapper() {
		return menuMapper;
	}

	@Override
	public Menu get(Integer wechatId, Integer id) {
		if (id == null) {
			return null;
		}
		Menu record = new Menu();
		record.setWechatId(wechatId);
		record.setId(id);
		record.setStatus(MenuStatus.INUSED.getValue());
		return menuMapper.selectOne(record);
	}

	private List<ReportMenuTrendDto> findDates(List<ReportMenuBaseDto> menus,
			Date begin, Date end) {
		List<ReportMenuTrendDto> list = new ArrayList<ReportMenuTrendDto>();

		ReportMenuTrendDto dto = new ReportMenuTrendDto();
		dto.setDate(DateUtil.formatYYYYMMDD(begin));
		List<ReportMenuBaseDto> menuList = new ArrayList<ReportMenuBaseDto>();
		for (ReportMenuBaseDto menu : menus) {
			ReportMenuBaseDto menuDto = new ReportMenuBaseDto();
			menuDto.setCount(0);
			menuDto.setGroupId(menu.getGroupId());
			menuDto.setId(menu.getId());
			menuDto.setParentId(menu.getParentId());
			menuDto.setName(menu.getName());
			menuList.add(menuDto);
		}
		dto.setMenuList(menuList);
		list.add(dto);

		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(begin);
		calBegin.set(Calendar.HOUR_OF_DAY, 0);
		calBegin.set(Calendar.MINUTE, 0);
		calBegin.set(Calendar.SECOND, 0);
		calBegin.set(Calendar.MILLISECOND, 0);
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(end);
		calEnd.set(Calendar.HOUR_OF_DAY, 0);
		calEnd.set(Calendar.MINUTE, 0);
		calEnd.set(Calendar.SECOND, 0);
		calEnd.set(Calendar.MILLISECOND, 0);
		while (calEnd.after(calBegin)) {
			calBegin.add(Calendar.DAY_OF_MONTH, 1);

			ReportMenuTrendDto dto1 = new ReportMenuTrendDto();
			dto1.setDate(DateUtil.formatYYYYMMDD(calBegin.getTime()));
			List<ReportMenuBaseDto> menuList1 = new ArrayList<ReportMenuBaseDto>();

			for (ReportMenuBaseDto menu : menus) {
				ReportMenuBaseDto menuDto = new ReportMenuBaseDto();
				menuDto.setCount(0);
				menuDto.setGroupId(menu.getGroupId());
				menuDto.setId(menu.getId());
				menuDto.setParentId(menu.getParentId());
				menuDto.setName(menu.getName());
				menuList1.add(menuDto);
			}
			dto1.setMenuList(menuList1);
			list.add(dto1);
		}
		return list;
	}

	@Override
	public ReportMenuDto menuReport(Integer wechatId, Integer groupId,
			Integer status, Date start, Date end) {
		// TODO Auto-generated method stub
		ReportMenuDto dto = new ReportMenuDto();
		List<ReportMenuBaseDto> menuList = menuMapper.getSubMenuList(wechatId,
				groupId, status);
		List<ReportMenuTrendDto> trendList = findDates(menuList, start, end);
		List<ReportMenuBaseDto> valueList = menuMapper.menuReport(wechatId,
				groupId, status, start, end);
		List<ReportMenuBaseDto> userValueList = menuMapper.menuUserReport(wechatId,
				groupId, status, start, end);

		// 按日期累加趋势图
		for (ReportMenuTrendDto trend : trendList) {
			for (ReportMenuBaseDto menu : trend.getMenuList()) {
				for (ReportMenuBaseDto value : valueList) {
					if (trend.getDate().equals(value.getDate())) {
						if (value.getId() == menu.getId()
								|| value.getParentId() == menu.getId()) {
							menu.setCount(menu.getCount() + value.getCount());
						}
					}
				}
			}
		}
		
		if(status.intValue() == 0){
			List<ReportMenuBaseDto> mainMenu = menuMapper.getMainMenuList(wechatId,
				groupId);
			for (ReportMenuBaseDto menu : mainMenu){
				menuList.add(menu);
			}
		}

		// 累加数据
		for (ReportMenuBaseDto menu : menuList) {
			for (ReportMenuBaseDto value : userValueList) {
				if (value.getId() == menu.getId()
						|| value.getParentId() == menu.getId()) {
					menu.setUserCount(menu.getUserCount() + value.getUserCount());
				}
			}
		}
		
		for (ReportMenuBaseDto menu : menuList) {
			for (ReportMenuBaseDto value : valueList) {
				if (value.getId() == menu.getId()
						|| value.getParentId() == menu.getId()) {
					menu.setCount(menu.getCount() + value.getCount());
				}
			}
			if(menu.getUserCount()!=0){
				menu.setPeruser(menu.getCount()/(menu.getUserCount()*1.0));
			}else {
				menu.setPeruser(0);
			}
		}
		
		dto.setMenuList(menuList);
		dto.setTrendList(trendList);
		return dto;
	}

	@Override
	public List<ReportMenuChildDto> menuChildReport(Integer wechatId,
			Integer groupId, Date start, Date end) {
		// TODO Auto-generated method stub
		return menuMapper.menuChildReport(wechatId, groupId, start, end);
	}

	@Override
	public Menu getByUrl(Integer wechatId, String url, Integer menuId) {
		if (url == null) {
			return null;
		}
		MenuGroup menuGroup = new MenuGroup();
		menuGroup.setWxMenuId(menuId.toString());
		menuGroup.setStatus(MenuGroupStatus.INUSED.getValue());
		menuGroup = menuGroupMapper.selectOne(menuGroup);
		if(menuGroup == null){
			menuGroup = menuGroupMapper.getDefaultMenuGroup(wechatId);
		}
		log.info("get menu group id: {}", menuGroup.getId());
		Menu record = new Menu();
		record.setWechatId(wechatId);
		if(url.indexOf("http://mp.weixin.qq.com")>0){
			if(url.indexOf("&scene")>0){
				url = url.substring(0, url.lastIndexOf("&scene"));
			}
			return menuMapper.getMatchUrl(wechatId, url, MenuStatus.INUSED.getValue(), 
					menuGroup.getId());
		}else{
			record.setUrl(url);
			record.setStatus(MenuStatus.INUSED.getValue());
			record.setType((byte)2);
			record.setMenuGroupId(menuGroup.getId());
			return menuMapper.selectOne(record);
		}
	}

}
