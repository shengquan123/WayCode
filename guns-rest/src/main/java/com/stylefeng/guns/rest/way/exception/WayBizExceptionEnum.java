package com.stylefeng.guns.rest.way.exception;

/**
 * 所有业务异常的枚举
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午5:04:51
 */
public enum WayBizExceptionEnum implements WayServiceExceptionEnum {

	/**
	 * token过期
	 */
	TOKEN_EXPIRED("WRY700", "抱歉！您的登录信息已经过期，请重新登录。"),
	/**
	 * token验证失败
	 */
	TOKEN_ERROR("WRY700", "抱歉！您的登录信息验证失败，请重新登录。"),
	/**
	 * 签名验证失败
	 */
	SIGN_ERROR("WRY500", "抱歉！请求数据签名验证失败，请重新登录再试。"),
	/**
	 * 其他
	 */
	AUTH_REQUEST_ERROR("WRY400", "抱歉！您的帐号或验证码验证失败，请重试。");

	WayBizExceptionEnum(String responseCode, String responseMessage) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}

	// 返回状态码
	private String responseCode;
	// 返回状态信息
	private String responseMessage;

	@Override
	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	@Override
	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
}
