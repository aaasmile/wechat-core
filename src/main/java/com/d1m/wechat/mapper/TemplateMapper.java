package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.model.Template;
import com.d1m.wechat.util.MyMapper;

public interface TemplateMapper extends MyMapper<Template> {

	List<Template> list(@Param("wechatId") Integer wechatId);

	String selectParamsByTemplateId(@Param("templateId") String templateId);

}
