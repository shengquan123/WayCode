package com.stylefeng.guns.rest.way.enums;

/**
 * 登录类型: 0默认 1手机登录 2邮箱登录 3指纹
 * 
 * @author shengquan
 */
public enum LoginTypeEnum {

	DEFAULT(0, "默认"),
	LOGINBYPHONE(1, "手机登录"),
	LOGINBYMAIL(2, "邮箱登录"),
	LOGINBYFINGER(3, "指纹登录");

	private int loginTypeCode;
	private String loginTypeMsg;

	private LoginTypeEnum(Integer loginTypeCode, String loginTypeMsg) {
		this.loginTypeCode = loginTypeCode;
		this.loginTypeMsg = loginTypeMsg;
	}

	public int getLoginTypeCode() {
		return loginTypeCode;
	}

	public void setLoginTypeCode(int loginTypeCode) {
		this.loginTypeCode = loginTypeCode;
	}

	public String getLoginTypeMsg() {
		return loginTypeMsg;
	}

	public void setLoginTypeMsg(String loginTypeMsg) {
		this.loginTypeMsg = loginTypeMsg;
	};
}
