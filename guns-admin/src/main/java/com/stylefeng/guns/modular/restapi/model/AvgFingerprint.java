package com.stylefeng.guns.modular.restapi.model;

public class AvgFingerprint {
	// 手指类型
	private int type;
	// 占比
	private String percent;
	//数量
	private String amount;
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}
