package com.stylefeng.guns.modular.restapi.model;

/**
 * 用户设备指纹关系模型
 * 
 * @author lori
 *
 */
public class FingerManagerModel {
	// 指纹id
	private Integer fqId;
	// 手指的位置
	private String location;
	// 设备的chipId
	private Integer chipId;
	// 设备编号
	private String chipNumber;
	// 用户的Id
	private Integer userId;
	//注册时间
	private String addTime;
	// 直达功能
	private QuickLaunchModel quickLaunchModel;

	public Integer getFqId() {
		return fqId;
	}

	public void setFqId(Integer fqId) {
		this.fqId = fqId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getChipId() {
		return chipId;
	}

	public void setChipId(Integer chipId) {
		this.chipId = chipId;
	}

	public String getChipNumber() {
		return chipNumber;
	}

	public void setChipNumber(String chipNumber) {
		this.chipNumber = chipNumber;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public QuickLaunchModel getQuickLaunchModel() {
		return quickLaunchModel;
	}

	public void setQuickLaunchModel(QuickLaunchModel quickLaunchModel) {
		this.quickLaunchModel = quickLaunchModel;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

}
