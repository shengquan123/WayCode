package com.stylefeng.guns.rest.modular.auth.converter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.auth.security.DataSecurityAction;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;

/**
 * 带签名的http信息转化器.
 * <p>
 * http传输的数据并不都是带签名的
 * <p>
 * 只有通过请求体(body)传输的数据才是，即requestBody
 * <p>
 * 普通参数参数不需要签名校验
 * <p>
 * *@RequestBody注解:
 * 用于读取Request请求的Body部分数据，使用系统默认配置的HttpMessageConverter(接口)进行解析，
 * 然后把相应的数据绑定到Controller中的方法参数上。
 * 所以才会有为什么使用了@RequestBody注解之后，测试时需要手动进行数据的签名，或者在客户端进行数据签名，
 * 本项目中通过继承FastJsonHttpMessageConverter(其顶层父接口依然是HttpMessageConverter)类，来实现
 * 自定义的http传输过程中的body中的参数要求。
 * 
 * @author fengshuonan
 * @date 2017-08-25 15:42
 */
public class WithSignMessageConverter extends FastJsonHttpMessageConverter {

	@Autowired
	JwtProperties jwtProperties;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	DataSecurityAction dataSecurityAction;

	@Override
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {

		InputStream in = inputMessage.getBody();
		Object o = JSON.parseObject(in, super.getFastJsonConfig().getCharset(), BaseTransferEntity.class,
				super.getFastJsonConfig().getFeatures());

		// 先转化成原始的对象
		BaseTransferEntity baseTransferEntity = (BaseTransferEntity) o;

		// 校验签名
		String token = HttpKit.getRequest().getHeader(jwtProperties.getHeader()).substring(7);
		String md5KeyFromToken = jwtTokenUtil.getMd5KeyFromToken(token);

		String object = baseTransferEntity.getObject();
		String json = dataSecurityAction.unlock(object);
		String encrypt = MD5Util.encrypt(object + md5KeyFromToken);

		if (encrypt.equals(baseTransferEntity.getSign())) {
			System.out.println("签名校验成功!");
		} else {
			System.out.println("签名校验失败,数据被改动过!");
			throw new GunsException(BizExceptionEnum.SIGN_ERROR);
		}

		// 校验签名后再转化成应该的对象
		return JSON.parseObject(json, type);
	}
}
