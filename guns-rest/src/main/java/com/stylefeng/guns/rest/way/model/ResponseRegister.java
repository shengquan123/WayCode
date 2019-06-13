package com.stylefeng.guns.rest.way.model;

public class ResponseRegister {

	//芯片Id
	private int chipId;
	//指纹的位置
	private int location;
	//用户Id
	private  int userId;
	//芯片中指纹id
	private int chipFpId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getChipId() {
		return chipId;
	}
	public void setChipId(int chipId) {
		this.chipId = chipId;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public int getChipFpId() {
		return chipFpId;
	}
	public void setChipFpId(int chipFpId) {
		this.chipFpId = chipFpId;
	}	
}
