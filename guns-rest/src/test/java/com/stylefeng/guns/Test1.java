package com.stylefeng.guns;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stylefeng.guns.rest.way.util.IpAddress;

public class Test1 {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String json_result = null;
		json_result = IpAddress.getAddresses("ip=" + "116.226.182.20", "utf-8");
	
		 JSONObject json = JSON.parseObject(json_result);
		String region = JSON.parseObject(json.get("data").toString()).get("region").toString();
		String city = JSON.parseObject(json.get("data").toString()).get("city").toString();
		System.out.println(region+city);
	}
	
	@Test
	public void test() {
		
	}
}
