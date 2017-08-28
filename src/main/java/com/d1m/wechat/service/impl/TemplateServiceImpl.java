package com.d1m.wechat.service.impl;

import cn.d1m.wechat.client.model.WxMessage;
import cn.d1m.wechat.client.model.WxTemplate;
import cn.d1m.wechat.client.model.common.WxList;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.MemberMapper;
import com.d1m.wechat.mapper.TemplateIndustryMapper;
import com.d1m.wechat.mapper.TemplateMapper;
import com.d1m.wechat.mapper.TemplateResultMapper;
import com.d1m.wechat.model.Template;
import com.d1m.wechat.model.TemplateResult;
import com.d1m.wechat.pamametermodel.TemplateEncryptModel;
import com.d1m.wechat.service.TemplateResultService;
import com.d1m.wechat.service.TemplateService;
import com.d1m.wechat.service.WechatService;
import com.d1m.wechat.util.IllegalArgumentUtil;
import com.d1m.wechat.util.Message;
import com.d1m.wechat.util.Security;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

@Service
public class TemplateServiceImpl extends BaseService<Template> implements
        TemplateService {

	private Logger log = LoggerFactory.getLogger(TemplateServiceImpl.class);
	private static String SALT = "national";

	@Autowired
	private TemplateMapper templateMapper;

	@Autowired
	private TemplateResultService templateResultService;

	@Autowired
	private TemplateResultMapper templateResultMapper;

	@Autowired
	private WechatService wechatService;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private TemplateIndustryMapper templateIndustryMapper;

	private static final String CHINESE_CDATA = "[\u4e00-\u9fa5|：|{{|.DATA]+";

	@Override
	public Mapper<Template> getGenericMapper() {
		// TODO Auto-generated method stub
		return templateMapper;
	}

	@Override
	public List<Template> list(Integer wechatId) {
		return templateMapper.list(wechatId);
	}

	@Override
	public JSONObject getParams(TemplateEncryptModel templateEncryptModel) {
		String decryptData = Security.decrypt(templateEncryptModel.getData(),
				SALT);
		log.debug("decrypt dataMap: " + decryptData);
		JSONObject data = JSON.parseObject(decryptData);
		String templateId = data.getString("template_id");

		notBlank(templateId, Message.TEMPLATE_ID_NOT_BLANK);

		JSONObject ret = new JSONObject();
		String code = "";
		String msg = "";
		String encyptStr = "";

		Template record = new Template();
		record.setTemplateId(templateId);
		record = templateMapper.selectOne(record);
		if (record.getStatus() == null || record.getStatus() == 0) {
			code = Message.TEMPLATE_WEXIN_NOT_EXIST.getCode().toString();
			msg = Message.TEMPLATE_WEXIN_NOT_EXIST.getName();
		} else {
			String params = templateMapper.selectParamsByTemplateId(templateId);
			JSONObject paramters = new JSONObject();
			paramters.put("paramters", params);
			String paramterStr = JSON.toJSONString(paramters);
			encyptStr = Security.encrypt(paramterStr, SALT);
			code = "0";
			msg = "success";
		}

		ret.put("code", code);
		ret.put("msg", msg);
		ret.put("data", encyptStr);
		return ret;
	}

	@Override
	public JSONObject sendToWX(TemplateEncryptModel templateEncryptModel) {
		String decryptData = Security.decrypt(templateEncryptModel.getData(),
				SALT);
		log.debug("decrypt dataMap: " + decryptData);
		JSONObject jsonData = JSON.parseObject(decryptData);
		String touser = jsonData.getString("touser");
		String templateId = jsonData.getString("template_id");
		String url = jsonData.getString("url");
		String data = jsonData.getString("data");
		Integer wechatId = memberMapper.selectWechatIdByOpenId(touser);
		return sendToWX(wechatId, touser, templateId, url, data);
	}

	@Override
	public synchronized JSONObject sendToWX(Integer wechatId, String touser,
			String templateId, String url, String data) {
		notBlank(touser, Message.TEMPLATE_OPENID_NOT_BLANK);
		notBlank(templateId, Message.TEMPLATE_ID_NOT_BLANK);
		notBlank(data, Message.TEMPLATE_DATA_NOT_BLANK);

		JSONObject ret = new JSONObject();
		String code = "";
		String msg = "";
		String encyptStr = "";

		if (wechatId == null) {
			code = Message.TEMPLATE_WEXIN_NOT_EXIST.getCode().toString();
			msg = Message.TEMPLATE_WEXIN_NOT_EXIST.getName();
		} else {
			WxMessage result = WechatClientDelegate.sendTemplate(wechatId,
					touser, templateId, data, url);
			if (result.fail()) {
				msg = result.getErrmsg();
			} else {
				String dataStr = JSON.toJSONString(result);
				encyptStr = Security.encrypt(dataStr, SALT);
				msg = "success";

				TemplateResult record = new TemplateResult();
				record.setTemplateId(templateId);
				record.setOpenId(touser);
				record.setPushAt(new Date());
				record.setWechatId(wechatId);
				record.setData(data);
				if (url != null) {
					record.setUrl(url);
				}
				record.setMsgId(result.getMsgId());
				templateResultService.save(record);
			}

		}

		ret.put("code", code);
		ret.put("msg", msg);
		ret.put("data", encyptStr);
		return ret;
	}

	@Override
	public void delete(Integer wechatId, Integer id) {
		IllegalArgumentUtil.notBlank(id, Message.TEMPLATE_ID_NOT_BLANK);
		Template record = new Template();
		record.setWechatId(wechatId);
		record.setId(id);
		List<Template> templates = templateMapper.select(record);
		if (templates.isEmpty()) {
			throw new WechatException(Message.TEMPLATE_NOT_BLANK);
		}
		// TODO
		templateMapper.delete(templates.get(0));
		WechatClientDelegate.deleteTemplate(wechatId, templates.get(0)
				.getTemplateId());
	}

	@Override
	public synchronized void pullWxTemplate(Integer wechatId) {
		log.info("start to pull wx template.");
		WxList<WxTemplate> templateList = WechatClientDelegate
				.getTemplateList(wechatId);
		if (templateList == null) {
			log.info("wx template size is empty.");
			return;
		}
		List<WxTemplate> list = templateList.get();
		log.info("wx template size : {}", list.size());
		Template template = null;
		for (WxTemplate wxTemplate : list) {
			Template record = new Template();
			record.setWechatId(wechatId);
			record.setTemplateId(wxTemplate.getTemplateId());
			template = templateMapper.selectOne(record);
			if (template == null) {
				log.error("TEMPLATE ID [{}] NOT FOUND.",
						wxTemplate.getTemplateId());
				template = new Template();
				template.setWechatId(wechatId);
				template.setTemplateId(wxTemplate.getTemplateId());
			}
			template.setContent(wxTemplate.getContent());
			template.setDeputyIndustry(wxTemplate.getDeputyIndustry());
			template.setPrimaryIndustry(wxTemplate.getPrimaryIndustry());
			template.setTitle(wxTemplate.getTitle());
			template.setExample(wxTemplate.getExample());
			template.setStatus((byte) 1);
			String parameter = wxTemplate.getContent().replaceAll(
					CHINESE_CDATA, "");
			parameter = parameter.replace("}}", ",");
			parameter = "[" + parameter.substring(0, parameter.length() - 1)
					+ "]";
			template.setParameters(parameter);
			if (template.getId() == null) {
				templateMapper.insert(template);
			} else {
				templateMapper.updateByPrimaryKeySelective(template);
			}
		}
		log.info("finish to pull wx template.");
	}

}
