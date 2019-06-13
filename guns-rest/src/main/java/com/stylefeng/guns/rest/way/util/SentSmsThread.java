package com.stylefeng.guns.rest.way.util;

import java.util.Map;

import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;

public class SentSmsThread extends Thread {
	
	/**
	 * 发送短信
	 * @param apikey
	 * @param phone
	 * @param text
	 * @return
	 */
	public Result<SmsSingleSend> single_send(String apikey,String phone, String text) {
		// 初始化clnt,使用单例方式
		YunpianClient clnt = new YunpianClient("apikey").init();
		// 发送短信API
		Map<String, String> param = clnt.newParam(3);
		param.put(YunpianClient.APIKEY, apikey);
		param.put(YunpianClient.MOBILE, phone);
		param.put(YunpianClient.TEXT, text);
		Result<SmsSingleSend> r = clnt.sms().single_send(param);
		return r;
	}
}
