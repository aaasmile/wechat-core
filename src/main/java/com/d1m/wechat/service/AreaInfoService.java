package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.model.AreaInfo;

public interface AreaInfoService extends IService<AreaInfo> {

	public List<AreaInfo> selectAll();

	public List<AreaInfo> selectByParentId(Integer parentId);

	AreaInfo getByCName(String cName);

    public String selectNameById(int area);

	public String selectNameById(int area, String lang);

}
