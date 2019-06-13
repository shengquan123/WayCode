package com.stylefeng.guns.rest.modular.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.stylefeng.guns.core.util.RenderUtil;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.way.exception.WayBizExceptionEnum;
import com.stylefeng.guns.rest.way.model.ResponseModel;

import io.jsonwebtoken.JwtException;

/**
 * 对客户端请求的jwt token验证过滤器
 *
 * @author dyson
 * @Date 2018/3/16 17:29
 */
public class AuthFilter extends OncePerRequestFilter {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtProperties jwtProperties;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request.getServletPath().equals("/" + "auth")
				|| request.getServletPath().equals("/" + "wayapi/v1/accessory/auth/getCode")
				|| request.getServletPath().equals("/" + "wayapi/v1/accessory/auth/getMailCode")
				|| request.getServletPath().equals("/" + "wayapi/v1/accessory/report/userAction")
				|| request.getServletPath().equals("/" + "wayapi/v1/accessory/report/windowsVersion")
				// 过滤测试URL：
				|| request.getServletPath().contains("testDruid")) {
			chain.doFilter(request, response);
			return;
		}
		final String requestHeader = request.getHeader(jwtProperties.getHeader());
		String authToken = null;
		if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
			authToken = requestHeader.substring(7);

			// 验证token是否过期,包含了验证jwt是否正确
			try {
				boolean flag = jwtTokenUtil.isTokenExpired(authToken);
				if (flag) {
					RenderUtil.renderJson(response,
							new ResponseModel(WayBizExceptionEnum.TOKEN_EXPIRED.getResponseCode(),
									WayBizExceptionEnum.TOKEN_EXPIRED.getResponseMessage()));
					logger.warn("token过期");
					return;
				}
			} catch (JwtException e) {
				// 有异常就是token解析失败
				RenderUtil.renderJson(response, new ResponseModel(WayBizExceptionEnum.TOKEN_ERROR.getResponseCode(),
						WayBizExceptionEnum.TOKEN_ERROR.getResponseMessage()));
				logger.warn("token解析失败");
				return;
			}
		} else {
			// header没有带Bearer字段
			RenderUtil.renderJson(response, new ResponseModel(WayBizExceptionEnum.TOKEN_ERROR.getResponseCode(),
					WayBizExceptionEnum.TOKEN_ERROR.getResponseMessage()));
			logger.warn("No Bearer token in header");
			return;
		}
		chain.doFilter(request, response);
	}
}