package com.stylefeng.guns.core.util;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类.
 * 
 * @author shengquan
 */
public class RegexUtil {

	/**
	 * 正则表达式：验证邮箱
	 */
	public static final String MAIL_REG = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

	/**
	 * 邮箱的正则验证
	 */
	public static boolean checkMail(String mail) {
		return Pattern.matches(MAIL_REG, mail);
	}
}
