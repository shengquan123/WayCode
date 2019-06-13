package com.stylefeng.guns.rest.modular.auth.controller.dto;

import com.stylefeng.guns.rest.modular.auth.validator.dto.Credence;

/**
 * 认证的请求dto
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:00
 */
public class AuthRequest implements Credence {

	private String phoneOrMail;
	private String code;

	public String getPhoneOrMail() {
		return phoneOrMail;
	}

	public void setPhoneOrMail(String phoneOrMail) {
		this.phoneOrMail = phoneOrMail;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getCredenceName() {
		return this.phoneOrMail;
	}

	@Override
	public String getCredenceCode() {
		return this.code;
	}
}
