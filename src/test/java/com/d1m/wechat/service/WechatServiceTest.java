package com.d1m.wechat.service;

import java.sql.Timestamp;
import java.util.Date;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.mapper.BaseTxTests;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import com.d1m.wechat.pamametermodel.WechatModel;
import com.d1m.wechat.pamametermodel.WechatTagModel;

public class WechatServiceTest extends BaseTxTests{
	Mockery context = new JUnit4Mockery();
	
	@Autowired
	WechatService wechatService;
	
	protected JSONObject representation(Message msg) {
		JSONObject json = new JSONObject();
		json.put("resultCode", msg.getCode());
		json.put("msg", msg.name());
		return json;
	}

	protected JSONObject representation(Message msg, Object data) {
		JSONObject json = new JSONObject();
		json.put("resultCode", msg.getCode());
		json.put("msg", msg.name());
		json.put("data", data);
		return json;
	}

	protected JSONObject representation(Message msg, Object data,
			Integer pageSize, Integer pageNum, long count) {
		JSONObject json = new JSONObject();
		json.put("resultCode", msg.getCode());
		json.put("msg", msg.name());
		JSONObject subJson = new JSONObject();
		subJson.put("result", data);
		subJson.put("pageSize", pageSize);
		subJson.put("pageNum", pageNum);
		subJson.put("count", count);
		json.put("data", subJson);
		return json;
	}
	
	@Test
	public void listWechatByCreatedTime(){
		WechatModel wechatModel = new WechatModel();
		wechatModel.setSortName("created_at");
		
		Page<Wechat> page = wechatService.search(wechatModel,true);
		JSONObject json = representation(Message.WECHAT_LIST_SUCCESS, page.getResult(),
				wechatModel.getPageNum(), wechatModel.getPageSize(), 
				page.getTotal());
		System.out.println(json);
		
	}
	
	@Test
	public void listWechatByName(){
		WechatModel wechatModel = new WechatModel();
		wechatModel.setSortName("name");
		
		Page<Wechat> page = wechatService.search(wechatModel,true);
		JSONObject json = representation(Message.WECHAT_LIST_SUCCESS, page.getResult(),
				wechatModel.getPageNum(), wechatModel.getPageSize(), 
				page.getTotal());
		System.out.println(json);
	}
	
	@Test
	public void insertWechat(){
		WechatTagModel wechatTagModel = new WechatTagModel();
		wechatTagModel.setName("demo");
		wechatTagModel.setAppid("dsfsd");
		wechatTagModel.setAppscret("sdsad");
		wechatTagModel.setEncodingAesKey("sfsdf");
		wechatTagModel.setOpenId("sadasd");
		wechatTagModel.setToken("sdasfdsvffsdf");
		wechatTagModel.setUrl("sddfdsf");
		User user = new User();
		user.setId(2);
		int code = wechatService.insert(wechatTagModel, user);
		JSONObject json = representation(Message.WECHAT_INSERT_SUCCESS, code);
		System.out.println(json);
	}
	
	@Test
	
	public void updateWechat(){
		WechatTagModel wechatTagModel = new WechatTagModel();
		wechatTagModel.setName("demo");
		wechatTagModel.setAppid("dsfsd");
		wechatTagModel.setAppscret("sdsad");
		wechatTagModel.setEncodingAesKey("sfsdf");
		wechatTagModel.setOpenId("sadasd");
		wechatTagModel.setToken("sdasfdsvffsdf");
		wechatTagModel.setUrl("sddfdsf");
		User user = new User();
		user.setId(2);
		int wechat = wechatService.update(4, wechatTagModel, user);
		JSONObject json = representation(Message.WECHAT_UPDATE_SUCCESS, wechat);
		System.out.println(json);
	}
	
	@Test
	public void test(){
		System.out.println(DateUtil.formatYYYYMMDDHHMMSS(new Date()));
		System.out.println(Timestamp.valueOf(DateUtil.formatYYYYMMDDHHMMSS(new Date())));
		System.out.println(DateUtil.parse(DateUtil.formatYYYYMMDDHHMMSS(new Date())));
	}

}
