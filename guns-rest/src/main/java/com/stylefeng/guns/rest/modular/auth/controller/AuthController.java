package com.stylefeng.guns.rest.modular.auth.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stylefeng.guns.core.util.RegexUtil;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import com.stylefeng.guns.rest.way.exception.WayBizExceptionEnum;
import com.stylefeng.guns.rest.way.model.ResponseModel;
import com.stylefeng.guns.rest.way.util.ErrorCodeParam;
import com.stylefeng.guns.rest.way.util.ModelReturnUtil;
import com.stylefeng.guns.rest.way.util.ToolUtil;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

	private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
	private static String Tag = "AuthController";

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Resource(name = "dbValidator")
	private IReqValidator reqValidator;

	@RequestMapping(value = "${jwt.auth-path}")
	public ResponseEntity<?> createAuthenticationToken(AuthRequest authRequest) {
		ResponseModel responseModel = new ResponseModel();
		LOG.info(Tag + "createAuthenticationToken");

		String phoneOrMail = authRequest.getPhoneOrMail();
		if (!ToolUtil.isNotEmpty(phoneOrMail)) {
			ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE,
					"PHONEORMAIL" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG, "", "");
			LOG.warn("PHONEORMAIL：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
			return ResponseEntity.ok(responseModel);
		}
		if (phoneOrMail.contains("@") && !RegexUtil.checkMail(phoneOrMail)) {
			ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.MAILDATEERR_CODE,
					"PHONEORMAIL" + ErrorCodeParam.MAILDATEERR_MSG, "", "");
			LOG.warn("PHONEORMAIL：" + ErrorCodeParam.MAILDATEERR_MSG);
			return ResponseEntity.ok(responseModel);
		}

		boolean validate = reqValidator.validate(authRequest);

		if (validate) {
			final String randomKey = jwtTokenUtil.getRandomKey();
			final String token = jwtTokenUtil.generateToken(authRequest.getPhoneOrMail(), randomKey);
			return ResponseEntity.ok(new AuthResponse(token, randomKey));
		} else {
			ModelReturnUtil.setResponseModel(responseModel, WayBizExceptionEnum.AUTH_REQUEST_ERROR.getResponseCode(),
					"PHONEORMAIL" + WayBizExceptionEnum.AUTH_REQUEST_ERROR.getResponseMessage(), "", "");
			LOG.warn(WayBizExceptionEnum.AUTH_REQUEST_ERROR.getResponseMessage());
			return ResponseEntity.ok(responseModel);
		}
	}
}
