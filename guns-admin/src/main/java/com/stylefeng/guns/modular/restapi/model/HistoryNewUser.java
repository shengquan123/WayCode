package com.stylefeng.guns.modular.restapi.model;

public class HistoryNewUser {
	// 结算期
	private String checkDte;
	// 新增人数
	private int amount;
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCheckDte() {
		return checkDte;
	}

	public void setCheckDte(String checkDte) {
		this.checkDte = checkDte;
	}

}
