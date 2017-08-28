package com.d1m.wechat.service.impl;

import cn.d1m.wechat.client.model.WxTemplateIndustry;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.d1m.wechat.mapper.TemplateIndustryMapper;
import com.d1m.wechat.model.TemplateIndustry;
import com.d1m.wechat.service.TemplateIndustryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class TemplateIndustryServiceImpl extends BaseService<TemplateIndustry>
		implements TemplateIndustryService {

	private Logger log = LoggerFactory
			.getLogger(TemplateIndustryServiceImpl.class);

	@Autowired
	private TemplateIndustryMapper templateIndustryMapper;

	@Override
	public Mapper<TemplateIndustry> getGenericMapper() {
		return templateIndustryMapper;
	}

	@Override
	public TemplateIndustry get(Integer wechatId) {
		TemplateIndustry record = new TemplateIndustry();
		record.setWechatId(wechatId);
		List<TemplateIndustry> list = templateIndustryMapper.select(record);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public void pullWxTemplateIndustry(Integer wechatId) {
		log.info("start to pull wx template industry.");
		TemplateIndustry templateIndustry = new TemplateIndustry();
		templateIndustry.setWechatId(wechatId);
		templateIndustry = templateIndustryMapper.selectOne(templateIndustry);
		if (templateIndustry == null) {
			log.info("template inudstry not found.s");
			templateIndustry = new TemplateIndustry();
			templateIndustry.setWechatId(wechatId);
		}
		WxTemplateIndustry ti = WechatClientDelegate
				.getTemplateIndustry(wechatId);
		if (ti.getPrimaryIndustry() != null) {
			templateIndustry.setPrimaryFirst(ti.getPrimaryIndustry()
					.getFirstClass());
			templateIndustry.setPrimarySecond(ti.getPrimaryIndustry()
					.getSecondClass());
		}
		if (ti.getSecondaryIndustry() != null) {
			templateIndustry.setSecondaryFirst(ti.getSecondaryIndustry()
					.getFirstClass());
			templateIndustry.setSecondarySecond(ti.getSecondaryIndustry()
					.getSecondClass());
		}
		if (templateIndustry.getId() == null) {
			templateIndustryMapper.insert(templateIndustry);
		} else {
			templateIndustryMapper.updateByPrimaryKey(templateIndustry);
		}
		log.info("finish to pull wx template industry.");
	}

}
