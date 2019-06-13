package com.stylefeng.guns.modular.restapi.model;

public class LaunchAppInfo {
	// url名称
	private String url;
	// 比例
	private String percent;
	//1网页，2app
	private int type;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}



}
