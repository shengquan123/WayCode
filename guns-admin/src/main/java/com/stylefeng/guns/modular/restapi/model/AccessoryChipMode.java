package com.stylefeng.guns.modular.restapi.model;

import java.util.List;

/**
 * 设备信息
 * 
 * @author lori
 *
 */
public class AccessoryChipMode {
	// 设备Id
	private Integer chipId;
	// 设备编号
	private String chipNumber;
	// 设备品牌
	private String chipBrand;
	//指纹关系模型
	private List<FingerManagerModel> FingerManagerModelList;
	
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

	public String getChipBrand() {
		return chipBrand;
	}

	public void setChipBrand(String chipBrand) {
		this.chipBrand = chipBrand;
	}

	public List<FingerManagerModel> getFingerManagerModelList() {
		return FingerManagerModelList;
	}

	public void setFingerManagerModelList(List<FingerManagerModel> fingerManagerModelList) {
		FingerManagerModelList = fingerManagerModelList;
	}
}
