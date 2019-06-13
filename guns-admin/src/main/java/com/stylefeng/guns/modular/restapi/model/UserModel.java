package com.stylefeng.guns.modular.restapi.model;

import java.util.List;

/**
 * 互啊佑用户信息
 * 
 * @author lori
 *
 */
public class UserModel {
	// 用户ID
	private String userId;
	// 返回用户的电话
	private String userPhone;
	// 昵称
	private String nikeName;
	// 性别
	private String sex;
	// 邮箱
	private String email;

	// 指纹信息表
	private List<FingerManagerModel> fingerManagerModels;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public List<FingerManagerModel> getFingerManagerModels() {
		return fingerManagerModels;
	}

	public void setFingerManagerModels(List<FingerManagerModel> fingerManagerModels) {
		this.fingerManagerModels = fingerManagerModels;
	}

}
