package com.stylefeng.guns.rest.way.model;

public class ResponseLoginByphone {
	/**
	 * 
	 */

	private String chipNumber; // 芯片Id
	private String code;
	private String phone;
	private String ip;
	private int logAppType; // 登录APP类型

	public String getChipNumber() {
		return chipNumber;
	}

	public void setChipNumber(String chipNumber) {
		this.chipNumber = chipNumber;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getLogAppType() {
		return logAppType;
	}

	public void setLogAppType(int logAppType) {
		this.logAppType = logAppType;
	}

	@Override
	public String toString() {
		return "ResponseLoginByphone [chipNumber=" + chipNumber + ", code=" + code + ", phone=" + phone + ", ip=" + ip
				+ ", logAppType=" + logAppType + "]";
	}
}
