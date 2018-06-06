package com.d1m.wechat.pamametermodel;

public class ExcelMember {
	
	private String nickname,gender,mobile,province,city,subscribe,subscribe_time,
	bind,message_sent,tags,openid, unbund_at;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(String subscribe_time) {
		this.subscribe_time = subscribe_time;
	}

	public String getBind() {
		return bind;
	}

	public void setBind(String bind) {
		this.bind = bind;
	}

	public String getMessage_sent() {
		return message_sent;
	}

	public void setMessage_sent(String message_sent) {
		this.message_sent = message_sent;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUnbund_at() {
		return unbund_at;
	}

	public void setUnbund_at(String unbund_at) {
		this.unbund_at = unbund_at;
	}

	@Override
	public String toString() {
		return "ExcelMember [nickname=" + nickname + ", gender=" + gender + ", mobile=" + mobile + ", province="
				+ province + ", city=" + city + ", subscribe=" + subscribe + ", subscribe_time=" + subscribe_time
				+ ", bind=" + bind + ", message_sent=" + message_sent + ", tags=" + tags + ", openid=" + openid
				+ ", unbund_at=" + unbund_at + "]";
	}
}