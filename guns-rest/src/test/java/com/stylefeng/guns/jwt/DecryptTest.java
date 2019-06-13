package com.stylefeng.guns.jwt;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryRelUserChip;
import com.stylefeng.guns.rest.modular.auth.converter.BaseTransferEntity;
import com.stylefeng.guns.rest.modular.auth.security.impl.Base64SecurityAction;
import com.stylefeng.guns.rest.way.model.RequestEditBind;
import com.stylefeng.guns.rest.way.model.ResponseLoginByMail;
import com.stylefeng.guns.rest.way.model.ResponseLoginByphone;
import com.stylefeng.guns.rest.way.model.ResponseLoginFp;

/**
 * jwt测试
 *
 * @author fengshuonan
 * @date 2017-08-21 16:34
 */
public class DecryptTest {

	public static void main(String[] args) {

		//String key = "mySecret";
		//String compactJws = "eyJhbGciOiJIUzUxMiJ9.eyJyYW5kb21LZXkiOiI3Z2lzaGsiLCJzdWIiOiIxMjczOTU5ODQ3QHFxLmNvbSIsImV4cCI6MTUzNjkxMDUzNiwiaWF0IjoxNTM0MzE4NTM2fQ.Q_WlBJ0j-A1yCiSHXdXUjl-AR4addrDK8xOdvaF4KKSFTN5qZblsFmeUxHQfB7GcG9OM-IRnbladHPCXlXlN3g";
		String salt = "xq0bcm";

		// SimpleObject simpleObject = new SimpleObject();
		// simpleObject.setUser("stylefeng");
		// simpleObject.setAge(12);
		// simpleObject.setName("ffff");
		// simpleObject.setTips("code");

		// ResponseUserInit responseUserInit = new ResponseUserInit();
		// responseUserInit.setChipNumber("0000000000000000D9661D43137B4739");
		// responseUserInit.setPhone("1860215265");

		ResponseLoginByphone responseLoginByphone = new ResponseLoginByphone();
		responseLoginByphone.setChipNumber("DYSON001");
		responseLoginByphone.setCode("604201");
		responseLoginByphone.setIp("192.168.1.1");
		responseLoginByphone.setPhone("17717290328");
		responseLoginByphone.setLogAppType(1);
		/*ResponseLoginByMail responseLoginByMail = new ResponseLoginByMail();
		responseLoginByMail.setMail("1273959847@qq.com");
		responseLoginByMail.setCode("643901");
		responseLoginByMail.setIp("192.168.1.1");
		responseLoginByMail.setChipNumber("110D003011FFFFFF82001A000607E107");
		responseLoginByMail.setLogAppType(1);*/

		// RequestEditBind requestEditBind = new RequestEditBind();
		// requestEditBind.setFpId(95);
		// requestEditBind.setType(2);
		// requestEditBind.setUrl("yiqine中文");

		// 客户端指纹验证登录
		// ResponseGetFp responseGetFp = new ResponseGetFp();
		// responseGetFp.setChipId(7);
		// responseGetFp.setUserId(19);

		// 指纹注册
		// ResponseRegister responseRegister = new ResponseRegister();
		// responseRegister.setChipId(7);
		// responseRegister.setLocation(1);
		// responseRegister.setUserId(19);
		// responseRegister.setChipFpId(1);

		// 指纹直达绑定
		// RequestEditBind requestEditBind = new RequestEditBind();
		// requestEditBind.setFpId(382);
		// requestEditBind.setType(1);
		// requestEditBind.setUrl("D:\\360安全浏览器下载\\WPS_10.1.0.7106.exe");

		// ResponceDelete responceDelete = new ResponceDelete();
		// responceDelete.setFpId(72);

		// ResponseGetFp responseGetFp = new ResponseGetFp();
		// responseGetFp.setChipId(13);
		// responseGetFp.setUserId(16);

		String jsonString = JSON.toJSONString(responseLoginByphone);
		System.out.println("jsonString = " + jsonString);
		String encode = new Base64SecurityAction().doAction(jsonString);
		System.out.println("encode = " + encode);
		String md5 = MD5Util.encrypt(encode + salt);
		System.out.println("md5 = " + md5);

		BaseTransferEntity baseTransferEntity = new BaseTransferEntity();
		baseTransferEntity.setObject(encode);
		baseTransferEntity.setSign(md5);

		System.out.println(JSON.toJSONString(baseTransferEntity));

		// System.out.println("body = " +
		// Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getBody());
		// System.out.println("header = " +
		// Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getHeader());
		// System.out.println("signature = " +
		// Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getSignature());
	}
}
