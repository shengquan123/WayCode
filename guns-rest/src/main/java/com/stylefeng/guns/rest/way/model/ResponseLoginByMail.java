package com.stylefeng.guns.rest.way.model;

public class ResponseLoginByMail {

	private String mail;
	private String code;
	private String chipNumber;
	private int logAppType;
	private String ip;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "ResponseLoginByMail [mail=" + mail + ", code=" + code + ", chipNumber=" + chipNumber + ", logAppType="
				+ logAppType + ", ip=" + ip + "]";
	}

}
