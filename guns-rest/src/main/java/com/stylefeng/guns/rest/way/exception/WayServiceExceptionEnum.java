package com.stylefeng.guns.rest.way.exception;

/**
 * 抽象接口
 *
 * @author fengshuonan
 * @date 2017-12-28-下午10:27
 */
public interface WayServiceExceptionEnum {
	/**
	 * 返回状态码
	 */
	String getResponseCode();

	/**
	 * 返回状态信息
	 */
	String getResponseMessage();

}
