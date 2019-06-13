package com.stylefeng.guns.rest.way.model;

/**
 * 设备和用户模型
 * 
 * @author lori
 *
 */
public class UserAndChip {
	// 设备模型
	private AccessoryChipModel accessoryChipModel;
	// 用户模型
	private UserModel userModel;

	public AccessoryChipModel getAccessoryChipModel() {
		return accessoryChipModel;
	}

	public void setAccessoryChipModel(AccessoryChipModel accessoryChipModel) {
		this.accessoryChipModel = accessoryChipModel;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

}
