package com.stylefeng.guns.rest.way.model;

public class ResponseModel {
	// 返回状态码
	private String responseCode;
	// 返回状态信息
	private String responseMessage;
	// 返回的object
	private Object object;
	// 返回的签名
	private String sign;

	public ResponseModel() {
	}

	public ResponseModel(String responseCode, String responseMessage) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.object = new String("");
		this.sign = "";
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
