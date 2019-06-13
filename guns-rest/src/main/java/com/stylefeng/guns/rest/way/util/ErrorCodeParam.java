package com.stylefeng.guns.rest.way.util;

/**
 * 错误码常量类
 */
public class ErrorCodeParam {
	/**
	 * 请求成功.
	 */
	public static final String SUCCESS_CODE = "WRY001";
	public static final String SUCCESS_MSG = "SUCCESS";
	/**
	 * 服务器忙
	 */
	public static final String SERVERBUSY_FAIL_CODE = "WRY002";
	public static final String SERVERBUSY_FAIL_MSG = "服务器繁忙中，请稍后再试。";
	/**
	 * 参数为空
	 */
	public static final String PARAMETERISEMPTY_FAIL_CODE = "WRY003";
	public static final String PARAMETERISEMPTY_FAIL_MSG = "请求参数中有空值，请重试。";
	/**
	 * 参数类型错误
	 */
	public static final String PARAMETERTYPEERROR_FAIL_CODE = "WRY004";
	public static final String PARAMETERTYPEERROR_FAIL_MSG = "请求参数类型不匹配，请重试。";
	/**
	 * 未查询到相关数据
	 */
	public static final String NODATA_FAIL_CODE = "WRY005";
	public static final String NODATA_FAIL_MSG = "数据未查询到";
	/**
	 * 添加失败
	 */
	public static final String ADDFAILED_FAIL_CODE = "WRY006";
	public static final String ADDFAILED_FAIL_MSG = "数据添加失败，请重试。";
	/**
	 * 修改失败
	 */
	public static final String MODIFYFAILED_FAIL_CODE = "WRY007";
	public static final String MODIFYFAILED_FAIL_MSG = "数据修改失败，请重试。";
	/**
	 * 删除失败
	 */
	public static final String DELETEFAILED_FAIL_CODE = "WRY008";
	public static final String DELETEFAILED_FAIL_MSG = "数据删除失败，请重试。";
	/**
	 * 数据失效
	 */
	public static final String DATAFAILURE_FAIL_CODE = "WRY009";
	public static final String DATAFAILURE_FAIL_MSG = "数据已经失效，请重试。";
	/**
	 * 验证码错误
	 */
	public static final String VERIFYCODEERROR_FAIL_CODE = "WRY010";
	public static final String VERIFYCODEERROR_FAIL_MSG = "抱歉！您的验证码有误，请确认后重新再试。";
	/**
	 * 验证码发送上限
	 */
	public static final String VERIFYCODEUNLIMITED_FAIL_CODE = "WRY011";
	public static final String VERIFYCODEUNLIMITED_FAIL_MSG = "抱歉！您的验证码获取次数过多，请稍后再试。";
	/**
	 * 邮箱格式错误
	 */
	public static final String MAILDATEERR_CODE = "WRY012";
	public static final String MAILDATEERR_MSG = "邮箱格式错误，请重试。";

}
