package com.d1m.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.model.Template;
import com.d1m.wechat.pamametermodel.TemplateEncryptModel;

import java.util.List;

public interface TemplateService extends IService<Template> {

	List<Template> list(Integer wechatId);

	JSONObject getParams(TemplateEncryptModel templateEncryptModel);

	JSONObject sendToWX(TemplateEncryptModel templateEncryptModel);

	JSONObject sendToWX(Integer wechatId, String touser, String templateId,
                        String url, String data);

	void delete(Integer wechatId, Integer id);

	void pullWxTemplate(Integer wechatId);

}
