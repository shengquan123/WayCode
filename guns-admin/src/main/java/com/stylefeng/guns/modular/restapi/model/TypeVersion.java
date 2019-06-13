package com.stylefeng.guns.modular.restapi.model;

public class TypeVersion {
	// 版本类型
	private String type;
	// 数量
	private int amount;
	// 占比
	private String percent;


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

}
