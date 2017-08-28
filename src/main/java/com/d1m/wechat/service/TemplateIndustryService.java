package com.d1m.wechat.service;

import com.d1m.wechat.model.TemplateIndustry;

public interface TemplateIndustryService extends IService<TemplateIndustry> {

	TemplateIndustry get(Integer wechatId);

	void pullWxTemplateIndustry(Integer wechatId);
}
