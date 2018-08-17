package com.d1m.wechat.pamametermodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("会员标签参数")
public class MemberTagInfoModel extends MemberTagModel {

	@ApiModelProperty("公众号ID")
	private String wechat_id;

	public String getWechat_id() {
		return wechat_id;
	}

	public void setWechat_id(String wechat_id) {
		this.wechat_id = wechat_id;
	}
	
}
