package com.stylefeng.guns.modular.restapi.model;

public class NewUserCount {
	//用户数量
	private int userCount;
	// 每日新增人数
	private int dayCount;
	// 每日新增比例
	private String dayPercent;
	// 每周新增人数
	private int weekCount;
	// 每周新增比例
	private String weekPercent;
	// 每月新增人数
	private int monthCount;
	// 每月新增比例
	private String monthPercent;
	
	

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getDayCount() {
		return dayCount;
	}

	public void setDayCount(int dayCount) {
		this.dayCount = dayCount;
	}

	public String getDayPercent() {
		return dayPercent;
	}

	public void setDayPercent(String dayPercent) {
		this.dayPercent = dayPercent;
	}

	public int getWeekCount() {
		return weekCount;
	}

	public void setWeekCount(int weekCount) {
		this.weekCount = weekCount;
	}

	public String getWeekPercent() {
		return weekPercent;
	}

	public void setWeekPercent(String weekPercent) {
		this.weekPercent = weekPercent;
	}

	public int getMonthCount() {
		return monthCount;
	}

	public void setMonthCount(int monthCount) {
		this.monthCount = monthCount;
	}

	public String getMonthPercent() {
		return monthPercent;
	}

	public void setMonthPercent(String monthPercent) {
		this.monthPercent = monthPercent;
	}

}
