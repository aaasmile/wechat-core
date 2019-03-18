package com.d1m.wechat.wx;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Generated;

@lombok.Data
public class Miniprogram{

	@JsonProperty("pagepath")
	@NotEmpty
	private String pagepath;

	@JsonProperty("appid")
	@NotEmpty
	private String appid;

}