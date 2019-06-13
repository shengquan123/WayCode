package com.stylefeng.guns.rest.way.model;

public class ResponseLoginFp {
	// 芯片Id
	private String chipNumber;
	// 用户Id
	private int userId;
	// 登录APP类型
	private int logAppType;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getChipNumber() {
		return chipNumber;
	}

	public void setChipNumber(String chipNumber) {
		this.chipNumber = chipNumber;
	}

	public int getLogAppType() {
		return logAppType;
	}

	public void setLogAppType(int logAppType) {
		this.logAppType = logAppType;
	}

}
