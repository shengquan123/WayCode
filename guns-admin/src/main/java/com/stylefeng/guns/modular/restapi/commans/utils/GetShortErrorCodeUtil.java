package com.stylefeng.guns.modular.restapi.commans.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

public class GetShortErrorCodeUtil {
	public static String getErrorCode(String code){
		try {
			ResourceBundle rb = ResourceBundle.getBundle("errorCode", Locale.getDefault());
			return StringUtils.isBlank(rb.getString(code))?code:rb.getString(code);
		} catch (Exception e) {
			return code;
		}
	}
}
