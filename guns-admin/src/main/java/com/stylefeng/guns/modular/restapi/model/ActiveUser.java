package com.stylefeng.guns.modular.restapi.model;

/**
 * 活跃用户
 * 
 * @author Administrator
 *
 */
public class ActiveUser {
	/**
	 * 人数
	 */
	private int amount;
	/**
	 * 比例
	 */
	private String persent;
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getPersent() {
		return persent;
	}
	public void setPersent(String persent) {
		this.persent = persent;
	}

}
