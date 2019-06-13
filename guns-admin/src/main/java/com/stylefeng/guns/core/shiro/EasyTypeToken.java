package com.stylefeng.guns.core.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class EasyTypeToken extends UsernamePasswordToken {
	private static final long serialVersionUID = -2564928913725078138L;

	private ShiroLoginType type;

	public EasyTypeToken() {
		super();
	}

	public EasyTypeToken(String username, String password, ShiroLoginType type, boolean rememberMe, String host) {
		super(username, password, rememberMe, host);
		this.type = type;
	}

	/** 免密登录 */
	public EasyTypeToken(String username) {
		super(username, "", false, null);
		this.type = ShiroLoginType.NOPASSWD;
	}

	/** 账号密码登录 */
	public EasyTypeToken(String username, String password) {
		super(username, password, false, null);
		this.type = ShiroLoginType.PASSWORD;
	}

	public ShiroLoginType getType() {
		return type;
	}

	public void setType(ShiroLoginType type) {
		this.type = type;
	}
}
