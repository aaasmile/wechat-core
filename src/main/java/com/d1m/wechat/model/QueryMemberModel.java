package com.d1m.wechat.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

public class QueryMemberModel {

	/** 会员卡号 */
	@JSONField(ordinal = 1)
	private String pmcode;
	//
	/** 手机号码 */
	@JSONField(ordinal = 2)
	private String phone;

	/** 绑定会员查询会员卡 */
	@JSONField(ordinal = 3)
	private String fromBind;

	/** 微信uniodid */
	@JSONField(ordinal = 4)
	private String unionid;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public String getPmcode() {
		return pmcode;
	}

	public void setPmcode(String pmcode) {
		this.pmcode = pmcode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFromBind() {
		return fromBind;
	}

	public void setFromBind(String fromBind) {
		this.fromBind = fromBind;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
}
