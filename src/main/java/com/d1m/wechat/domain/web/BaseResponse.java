package com.d1m.wechat.domain.web;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by jone.wang on 2018/11/6.
 * Description:
 */
public class BaseResponse {
	@ApiModelProperty(value = "状态码", required = true)
	private Integer resultCode;
	@ApiModelProperty(value = "返回信息", example = "操作成功")
	private String msg;
	@ApiModelProperty(value = "返回对象json")
	private Object data;

	public BaseResponse(Integer resultCode, String msg, Object data) {
		super();
		this.resultCode = resultCode;
		this.msg = msg;
		this.data = data;
	}

	public BaseResponse() {
		super();
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static class Builder {
		private Integer resultCode;
		private String msg;
		private Object data;

		public Builder resultCode(Integer resultCode) {
			this.resultCode = resultCode;
			return this;
		}

		public Builder msg(String msg) {
			this.msg = msg;
			return this;
		}

		public Builder data(Object data) {
			this.data = data;
			return this;
		}

		public BaseResponse build() {
			return new BaseResponse(this);
		}
	}

	private BaseResponse(Builder builder) {
		this.resultCode = builder.resultCode;
		this.msg = builder.msg;
		this.data = builder.data;
	}

	@Override
	public String toString() {
		return "BaseResponse [resultCode=" + resultCode + ", msg=" + msg + ", data=" + data + "]";
	}
}
