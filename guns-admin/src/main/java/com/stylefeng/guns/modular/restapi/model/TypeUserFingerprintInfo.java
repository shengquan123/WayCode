package com.stylefeng.guns.modular.restapi.model;

import java.util.List;

public class TypeUserFingerprintInfo {
	// 用户总数量
	private int userAmout;
	// 用户指纹总数量
	private int fingerprintAmout;
	// 用户平均的注册指纹数量
	private float avgFingerprintAmout;
	// 指纹1注册数量
	private List<AvgFingerprint> avgFingerprintList;

	public int getUserAmout() {
		return userAmout;
	}

	public void setUserAmout(int userAmout) {
		this.userAmout = userAmout;
	}

	public int getFingerprintAmout() {
		return fingerprintAmout;
	}

	public void setFingerprintAmout(int fingerprintAmout) {
		this.fingerprintAmout = fingerprintAmout;
	}

	public float getAvgFingerprintAmout() {
		return avgFingerprintAmout;
	}

	public void setAvgFingerprintAmout(float avgFingerprintAmout) {
		this.avgFingerprintAmout = avgFingerprintAmout;
	}

	public List<AvgFingerprint> getAvgFingerprintList() {
		return avgFingerprintList;
	}

	public void setAvgFingerprintList(List<AvgFingerprint> avgFingerprintList) {
		this.avgFingerprintList = avgFingerprintList;
	}

}
