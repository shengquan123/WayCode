package com.stylefeng.guns.rest.way.model;

public class ReportActionModel {
	//用户id
	private int userId;
      // type(操作类型，1.客户端启动 2.用户手机登录 3.用户退出 4.客户端切换帐号 5.指纹登录/解锁6.文件加解密  7.网页/应用快捷直达点击 8.应用/网站免密登录 9.使用小贴士10.帮助中心11.客户端退出 )
	private int type ;  
	//  number 加解密的次数
	private int number;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
}
