package com.stylefeng.guns.modular.restapi.model;

public class UserModelInfo {
	// 用户ID
	private String userId;
	// 注册时间
	private String registerTime;
	// 注册至今时间
	private String registerToPresentTime;
	// 指纹总数量
	private int fingerprintAmount;
	// 加密文件数量
	private int encryptAmount;
	// 直达应用文件数量
	private int quickLaunchAmount;
	// 短信发送数
	private int messageSendAmount;
	// 邮件发送数
	private int mailSendAmount;
	// 所有用户发送短信总条数
	private int allMessageSendAmount;
	// 返回用户的电话
	private String userPhone;
	// 昵称
	private String nikeName;
	// 性别
	private String sex;
	// 邮箱
	private String email;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getRegisterToPresentTime() {
		return registerToPresentTime;
	}

	public void setRegisterToPresentTime(String registerToPresentTime) {
		this.registerToPresentTime = registerToPresentTime;
	}

	public int getFingerprintAmount() {
		return fingerprintAmount;
	}

	public void setFingerprintAmount(int fingerprintAmount) {
		this.fingerprintAmount = fingerprintAmount;
	}

	public int getEncryptAmount() {
		return encryptAmount;
	}

	public void setEncryptAmount(int encryptAmount) {
		this.encryptAmount = encryptAmount;
	}

	public int getQuickLaunchAmount() {
		return quickLaunchAmount;
	}

	public void setQuickLaunchAmount(int quickLaunchAmount) {
		this.quickLaunchAmount = quickLaunchAmount;
	}

	public int getMessageSendAmount() {
		return messageSendAmount;
	}

	public int getMailSendAmount() {
		return mailSendAmount;
	}

	public void setMailSendAmount(int mailSendAmount) {
		this.mailSendAmount = mailSendAmount;
	}

	public void setMessageSendAmount(int messageSendAmount) {
		this.messageSendAmount = messageSendAmount;
	}

	public int getAllMessageSendAmount() {
		return allMessageSendAmount;
	}

	public void setAllMessageSendAmount(int allMessageSendAmount) {
		this.allMessageSendAmount = allMessageSendAmount;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
