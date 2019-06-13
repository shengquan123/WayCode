package com.stylefeng.guns.modular.restapi.model;
/**
 * 用户行为
 * @author Administrator
 *
 */
public class UserAction {
	//类型
	private String type;
	//次数
	private String count;
	//时间类型
	private String timeType;
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

}
