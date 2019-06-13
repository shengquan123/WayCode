package com.stylefeng.guns.rest.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import com.stylefeng.guns.core.config.DefaultFastjsonConfig;
import com.stylefeng.guns.rest.config.properties.RestProperties;
import com.stylefeng.guns.rest.modular.auth.converter.WithSignMessageConverter;

/**
 * 签名校验messageConverter
 *
 * @author fengshuonan
 * @date 2017-08-25 16:04
 */
@Configuration
public class MessageConverConfig {

	@Bean
	@ConditionalOnProperty(prefix = RestProperties.REST_PREFIX, name = "sign-open", havingValue = "true", matchIfMissing = true)
	public WithSignMessageConverter withSignMessageConverter() {
		WithSignMessageConverter withSignMessageConverter = new WithSignMessageConverter();
		DefaultFastjsonConfig defaultFastjsonConfig = new DefaultFastjsonConfig();
		// 处理中文乱码问题
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		withSignMessageConverter.setFastJsonConfig(defaultFastjsonConfig.fastjsonConfig());
		withSignMessageConverter.setSupportedMediaTypes(fastMediaTypes);
		return withSignMessageConverter;

	}
}
