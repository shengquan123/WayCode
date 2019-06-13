package com.stylefeng.guns.rest.way.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryModelUser;
import com.stylefeng.guns.rest.way.controller.AppBindController;

public class UserIpAddressThread extends Thread{

	private static final Logger LOG = LoggerFactory.getLogger(AppBindController.class);
	private static String Tag = "TestRunnable";
	
	//用户
	private WayAccessoryModelUser wayAccessoryModelUser;
	private String ip;

	public UserIpAddressThread(WayAccessoryModelUser wayAccessoryModelUser, String ip) {
		this.wayAccessoryModelUser = wayAccessoryModelUser;
		this.ip = ip;
	}

	public void start() {
		try {
			// 查询到用户更新用户信息
			String json_result = null;
			json_result = IpAddress.getXLAddress(ip);
			JSONObject json = JSON.parseObject(json_result);
			String region = JSON.parseObject(json.get("data").toString()).get("region").toString();
			String city = JSON.parseObject(json.get("data").toString()).get("city").toString();
			wayAccessoryModelUser.setIpAddress(ip);
			wayAccessoryModelUser.setCity(city);
			wayAccessoryModelUser.setPrivince(region);
			wayAccessoryModelUser.updateById();
		} catch (Exception e) {
			LOG.error(Tag + "用户Id为："+wayAccessoryModelUser.getId()+" 的用户更新ip地址异常" + e);
		}
	}

}
