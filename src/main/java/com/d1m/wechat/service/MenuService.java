package com.d1m.wechat.service;

import java.util.Date;
import java.util.List;


import com.d1m.wechat.dto.ReportMenuChildDto;
import com.d1m.wechat.dto.ReportMenuDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.Menu;

public interface MenuService extends IService<Menu> {

	Menu get(Integer wechatId, Integer id);

	ReportMenuDto menuReport(Integer wechatId,Integer groupId, Integer status, Date start, Date end) 
			throws WechatException;

	List<ReportMenuChildDto> menuChildReport(Integer wechatId, Integer groupId,Date start,Date end)
			throws WechatException;

	Menu getByUrl(Integer wechatId, String url, Integer menuId);
	
	
}
