package com.d1m.wechat.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

public class WechatMapperTest extends BaseTxTests {

	@Autowired
	WechatMapper wechatMapper;

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
	public void search() {
		Page<Wechat> page = wechatMapper.search(null, null, "created_at", null,
				null);
		JSONObject json = new JSONObject();
		json = representation(Message.WECHAT_LIST_SUCCESS, page.getResult(), 1,
				10, page.getTotal());
		System.out.println(json);

	}

}
