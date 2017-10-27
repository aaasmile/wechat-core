package com.d1m.wechat.pamametermodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("JS配置参数")
public class JsConfigModel {
	
	@ApiModelProperty("URL")
	private String url;
	
	@ApiModelProperty("短地址")
	private String shortUrl;

	public String getShortUrl() {
		return shortUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
