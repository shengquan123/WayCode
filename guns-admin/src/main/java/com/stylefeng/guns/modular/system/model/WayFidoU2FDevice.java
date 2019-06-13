package com.stylefeng.guns.modular.system.model;

import java.util.Date;

public class WayFidoU2FDevice {

	private Integer id;
	private String num;
	private String systemName;
	private String userUUId;
	private String userName;
	private String password;
	private Integer status;
	private String name;
	private String version;
	private String keyHandle;
	private String publicKey;
	private String appId;
	private String attestationCert;
	private Integer compromised;
	private Integer countNum;
	private Date addTime;
	private Date lastAuthTime;
	private Date updateTime;

	@Override
	public String toString() {
		return "WayFidoU2FDevice [id=" + id + ", num=" + num + ", systemName=" + systemName + ", userUUId=" + userUUId
				+ ", userName=" + userName + ", password=" + password + ", status=" + status + ", name=" + name
				+ ", version=" + version + ", keyHandle=" + keyHandle + ", publicKey=" + publicKey + ", appId=" + appId
				+ ", attestationCert=" + attestationCert + ", compromised=" + compromised + ", countNum=" + countNum
				+ ", addTime=" + addTime + ", lastAuthTime=" + lastAuthTime + ", updateTime=" + updateTime + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getUserUUId() {
		return userUUId;
	}

	public void setUserUUId(String userUUId) {
		this.userUUId = userUUId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getKeyHandle() {
		return keyHandle;
	}

	public void setKeyHandle(String keyHandle) {
		this.keyHandle = keyHandle;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAttestationCert() {
		return attestationCert;
	}

	public void setAttestationCert(String attestationCert) {
		this.attestationCert = attestationCert;
	}

	public Integer getCompromised() {
		return compromised;
	}

	public void setCompromised(Integer compromised) {
		this.compromised = compromised;
	}

	public Integer getCountNum() {
		return countNum;
	}

	public void setCountNum(Integer countNum) {
		this.countNum = countNum;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getLastAuthTime() {
		return lastAuthTime;
	}

	public void setLastAuthTime(Date lastAuthTime) {
		this.lastAuthTime = lastAuthTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
