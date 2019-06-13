package com.stylefeng.guns.rest.way.model;

/**
 * 用户设备指纹关系模型
 * 
 * @author lori
 *
 */
public class FingerManagerModel {

	// 注册时间
	private String addTime;

	// 设备的chipId
	private Integer chipId;

	// 设备编号
	private String chipNumber;

	// 指纹id
	private Integer fqId;
	
	// 芯片中指纹id
	private Integer chipFpId;
	
	// 手指的位置
	private String location;

	// 直达功能
	private QuickLaunchModel quickLaunchModel;

	// 用户的Id
	private Integer userId;

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

	public Integer getChipFpId() {
		return chipFpId;
	}

	public void setChipFpId(Integer chipFpId) {
		this.chipFpId = chipFpId;
	}
}
