package com.stylefeng.guns.rest.way.model;

import java.util.List;

/**
 * 设备信息
 * 
 * @author lori
 *
 */
public class AccessoryChipModel {
	// 设备Id
	private Integer chipId;
	// 设备编号
	private String chipNumber;
	// 设备品牌
	private String chipBrand;
	
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
}
