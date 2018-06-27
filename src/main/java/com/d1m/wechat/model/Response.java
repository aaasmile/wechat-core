package com.d1m.wechat.model;

public class Response {

	private String error_info,status;
	private Object data;
	
	public String getError_info() {
		return error_info;
	}
	public void setError_info(String error_info) {
		this.error_info = error_info;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public static Response successful(Object data, String successInfo) {
		Response response = new Response();
		response.setStatus("successful");
		response.setData(data);
		response.setError_info(successInfo);
		return response;
	}
	
	public static Response fail(Object data, String errorInfo) {
		Response response = new Response();
		response.setStatus("fail");
		response.setData(data);
		response.setError_info(errorInfo);
		return response;
	}
}
