package com.stylefeng.guns.modular.system.model;

import java.util.Date;

/**
 * @author shengquan
 */
public class WayFidoU2FApplication {

	private Integer id;
	private Integer systemId;
	private String systemName;
	private String appName; // 应用名称，唯一
	private String appRestId;
	private String appRestSecret;
	private String token;
	private Integer status;
	private String appDescription;
	private Date addTime;
	private Date expireTime;
	private Date updateTime;

	@Override
	public String toString() {
		return "WayFidoU2FApplication [id=" + id + ", systemId=" + systemId + ", systemName=" + systemName
				+ ", appName=" + appName + ", appRestId=" + appRestId + ", appRestSecret=" + appRestSecret + ", token="
				+ token + ", status=" + status + ", appDescription=" + appDescription + ", addTime=" + addTime
				+ ", expireTime=" + expireTime + ", updateTime=" + updateTime + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSystemId() {
		return systemId;
	}

	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppRestId() {
		return appRestId;
	}

	public void setAppRestId(String appRestId) {
		this.appRestId = appRestId;
	}

	public String getAppRestSecret() {
		return appRestSecret;
	}

	public void setAppRestSecret(String appRestSecret) {
		this.appRestSecret = appRestSecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAppDescription() {
		return appDescription;
	}

	public void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
