package com.stylefeng.guns.rest.way.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.core.util.RegexUtil;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryLogMailCheckMapper;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryLogMobileCheckMapper;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryLogUserLoginMapper;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryModelChipMapper;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryModelUserMapper;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryRelUserChipMapper;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryLogMailCheck;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryLogMobileCheck;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryLogUserLogin;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryModelChip;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryModelUser;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryRelUserChip;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.way.enums.LoginTypeEnum;
import com.stylefeng.guns.rest.way.model.AccessoryChipModel;
import com.stylefeng.guns.rest.way.model.FingerManagerModel;
import com.stylefeng.guns.rest.way.model.ResponseLoginByMail;
import com.stylefeng.guns.rest.way.model.ResponseLoginByphone;
import com.stylefeng.guns.rest.way.model.ResponseLoginFp;
import com.stylefeng.guns.rest.way.model.ResponseModel;
import com.stylefeng.guns.rest.way.model.UserAndChip;
import com.stylefeng.guns.rest.way.model.UserModel;
import com.stylefeng.guns.rest.way.service.IpAddressService;
import com.stylefeng.guns.rest.way.service.MailService;
import com.stylefeng.guns.rest.way.util.AppFingerUtil;
import com.stylefeng.guns.rest.way.util.ErrorCodeParam;
import com.stylefeng.guns.rest.way.util.ModelReturnUtil;
import com.stylefeng.guns.rest.way.util.ModelUtil;
import com.stylefeng.guns.rest.way.util.Params;
import com.stylefeng.guns.rest.way.util.SentSmsThread;
import com.stylefeng.guns.rest.way.util.TimeUtil;
import com.stylefeng.guns.rest.way.util.ToolUtil;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;

/**
 * 登录相关接口
 */
@Controller
@RequestMapping("wayapi/v1/accessory/auth")
public class AuthLoginController {

	private static final Logger LOG = LoggerFactory.getLogger(AuthLoginController.class);
	private static final String Tag = "AuthLoginController";
	@Autowired
	private MailService mailService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtProperties jwtProperties;
	@Autowired
	private WayAccessoryModelUserMapper wayAccessoryModelUserMapper;
	@Autowired
	private WayAccessoryModelChipMapper wayAccessoryModelChipMapper;
	@Autowired
	private WayAccessoryRelUserChipMapper wayAccessoryRelUserChipMapper;
	@Autowired
	private WayAccessoryLogUserLoginMapper wayAccessoryLogUserLoginMapper;
	@Autowired
	private WayAccessoryLogMobileCheckMapper wayAccessoryLogMobileCheckMapper;
	@Autowired
	private WayAccessoryLogMailCheckMapper wayAccessoryLogMailCheckMapper;

	@Autowired
	private IpAddressService ipService;

	@Value("${send-sms.MAIL_TEXT}")
	String MAIL_TEXT;
	@Value("${send-sms.PHONE_MESSAGE_TEXT}")
	String PHONE_MESSAGE_TEXT;

	@Value("${send-sms.YP_PROMOTION_APIKEY}")
	String YP_PROMOTION_APIKEY;

	/* 短信过期时间 */
	@Value("${jwt.sms-overdue-time}")
	int smsOverdueTime;

	/* 邮件发送方地址 */
	@Value("${spring.mail.username}")
	String fromMailAddress;

	private static final String SUCCESS_CODE = "0";

	/**
	 * TODO 获取手机验证码
	 * 方法名修改为getPhoneCode
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "getCode", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> getCode(@RequestParam("phone") String phone) {
		String methodName = "/getPhoneCode";
		LOG.info(Tag + methodName);
		LOG.info("参数phone:" + phone);
		ResponseModel responseModel = new ResponseModel();
		try {
			// 参数判断
			if (!ToolUtil.isNotEmpty(phone)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE,
						"PHONE" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG, "", "");
				LOG.warn("phone：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			LOG.info("======[发送手机验证码]======");

			int codes = (int) ((Math.random() * 9 + 1) * 100000);
			String idencode = String.valueOf(codes);

			Result<SmsSingleSend> result = new SentSmsThread().single_send(YP_PROMOTION_APIKEY, phone,
					PHONE_MESSAGE_TEXT.replace("xxxxxx", idencode));

			if (SUCCESS_CODE.equals(result.getCode().toString())) {
				LOG.info("===保存手机验证码===");
				WayAccessoryLogMobileCheck wayAccessoryLogMobileCheck = new WayAccessoryLogMobileCheck();
				wayAccessoryLogMobileCheck.setIdenCode(idencode);
				wayAccessoryLogMobileCheck.setPhoneNo(phone);
				wayAccessoryLogMobileCheck.setTransCode("L");
				wayAccessoryLogMobileCheck.setCheckTime(TimeUtil.getTimestamp());
				try {
					wayAccessoryLogMobileCheckMapper.insert(wayAccessoryLogMobileCheck);
					LOG.info("验证码保存成功");
				} catch (Exception e) {
					ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.ADDFAILED_FAIL_CODE,
							ErrorCodeParam.ADDFAILED_FAIL_MSG, "", "");
					LOG.error("系统异常 : " + e);
					return ResponseEntity.ok(responseModel);
				}
			} else if (result.getCode() > 0) {
				// 验证码类短信1小时内同一手机号发送次数不能超过3次
				if (result.getCode() == 22) {
					ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.VERIFYCODEUNLIMITED_FAIL_CODE,
							ErrorCodeParam.VERIFYCODEUNLIMITED_FAIL_MSG, "", "");
					LOG.warn(Params.RESPONSECODE + "1小时内同一手机号发送次数超过限制");
					return ResponseEntity.ok(responseModel);
				}
				// 调用API时发生错误，需要开发者进行相应的处理。 提示客户端获取验证码失败
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.SERVERBUSY_FAIL_CODE,
						ErrorCodeParam.SERVERBUSY_FAIL_MSG, "", "");
				LOG.warn(Params.RESPONSECODE + "短信发送API调用异常，需要开发者进行相应的处理。");
				return ResponseEntity.ok(responseModel);
			} else if (result.getCode() < 0 && result.getCode() > -50) {
				// 权限验证失败，需要开发者进行相应的处理。 提示客户端获取验证码失败
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.SERVERBUSY_FAIL_CODE,
						ErrorCodeParam.SERVERBUSY_FAIL_MSG, "", "");
				LOG.warn(Params.RESPONSECODE + "权限验证失败，需要开发者进行相应的处理。");
				return ResponseEntity.ok(responseModel);
			} else {
				// 系统内部错误，请联系技术支持，调查问题原因并获得解决方案。 提示客户端获取验证码失败
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.SERVERBUSY_FAIL_CODE,
						ErrorCodeParam.SERVERBUSY_FAIL_MSG, "", "");
				LOG.warn(Params.RESPONSECODE + "系统内部错误，请联系技术支持，调查问题原因并获得解决方案。");
				return ResponseEntity.ok(responseModel);
			}
			ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.SUCCESS_CODE, ErrorCodeParam.SUCCESS_MSG, "",
					"");
			LOG.info("【发送成功】：" + ErrorCodeParam.SUCCESS_CODE + "，【手机号码】：" + phone);
			return ResponseEntity.ok(responseModel);
		} catch (Exception e) {
			ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.SERVERBUSY_FAIL_CODE,
					ErrorCodeParam.SERVERBUSY_FAIL_MSG, "", "");
			LOG.error("Exception : ", e);
			return ResponseEntity.ok(responseModel);
		}
	}

	/**
	 * TODO 获取邮箱验证码
	 * 
	 * @param mail
	 * @return
	 */
	@RequestMapping(value = "getMailCode", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> getMailCode(@RequestParam("mail") String mail) {
		ResponseModel responseModel = new ResponseModel();
		String methodName = "/getMailCode";
		LOG.info(Tag + methodName);
		LOG.info("参数mail:" + mail);
		try {
			if (!ToolUtil.isNotEmpty(mail)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE,
						"MAIL" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG, "", "");
				LOG.warn("mail：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			// 邮箱正则判断
			if (!RegexUtil.checkMail(mail)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.MAILDATEERR_CODE,
						"MAIL" + ErrorCodeParam.MAILDATEERR_MSG, "", "");
				LOG.warn("mail：" + ErrorCodeParam.MAILDATEERR_MSG);
				return ResponseEntity.ok(responseModel);
			}
			LOG.info("======[生成邮箱验证码]======");
			int codes = (int) ((Math.random() * 9 + 1) * 100000);
			String idencode = String.valueOf(codes);
			// 向邮箱发送此验证码
			try {
				mailService.sendHtmlMail(fromMailAddress, mail, MAIL_TEXT.replace("xxxxxx", idencode));
			} catch (Exception e) { // 邮件发送失败
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE,
						"MAIL" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG, "", "");
				LOG.warn("mail：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}

			LOG.info("===保存邮箱验证码===");
			WayAccessoryLogMailCheck wayAccessoryLogMailCheck = new WayAccessoryLogMailCheck();
			Integer selectByMail = wayAccessoryLogMailCheckMapper.selectByMail(mail);
			if (selectByMail > 0) {
				wayAccessoryLogMailCheck.setTransCode("2"); // 登录
			} else {
				wayAccessoryLogMailCheck.setTransCode("1"); // 注册
			}
			wayAccessoryLogMailCheck.setMailAddress(mail);
			wayAccessoryLogMailCheck.setIdenCode(idencode);
			wayAccessoryLogMailCheck.setCheckTime(TimeUtil.getTimestamp());
			try {
				wayAccessoryLogMailCheckMapper.insert(wayAccessoryLogMailCheck);
			} catch (Exception e) { // 邮箱验证码保存失败
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.ADDFAILED_FAIL_CODE,
						ErrorCodeParam.ADDFAILED_FAIL_MSG, "", "");
				LOG.error("系统异常 : " + e);
				return ResponseEntity.ok(responseModel);
			}
			ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.SUCCESS_CODE, ErrorCodeParam.SUCCESS_MSG, "",
					"");
			LOG.info("【邮箱验证码获取成功】：" + ErrorCodeParam.SUCCESS_CODE + "，【邮箱地址】：" + mail);
			return ResponseEntity.ok(responseModel);
		} catch (Exception e) {
			ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.SERVERBUSY_FAIL_CODE,
					ErrorCodeParam.SERVERBUSY_FAIL_MSG, "", "");
			LOG.error("Exception : ", e);
			return ResponseEntity.ok(responseModel);
		}
	}

	/**
	 * TODO 手机注册或登录
	 * 
	 * @param phone
	 * @param code
	 * @param chipId
	 * @param ip
	 * @param logAppType
	 * @return
	 */
	@RequestMapping(value = "loginByPhone", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> loginByPhone(@RequestBody ResponseLoginByphone responseLoginByphone) {
		String methodName = "/loginByPhone";
		LOG.info(Tag + methodName);
		String chipNumber = responseLoginByphone.getChipNumber();
		String code = responseLoginByphone.getCode();
		String ip = responseLoginByphone.getIp();
		String phone = responseLoginByphone.getPhone();
		int logAppType = responseLoginByphone.getLogAppType();
		LOG.info("参数phone:" + phone);
		LOG.info("参数code:" + code);
		LOG.info("参数chipNumber:" + chipNumber);
		LOG.info("参数ip:" + ip);
		LOG.info("参数logAppType:" + logAppType);

		/** 返回的标准模型 */
		ResponseModel responseModel = new ResponseModel();

		/** 返回的设备和用户模型 */
		UserAndChip userAndChip = new UserAndChip();

		/** 返回的设备模型 */
		AccessoryChipModel accessoryChipModel = new AccessoryChipModel();

		/** 返回用户模型 */
		UserModel userModel = new UserModel();

		try {

			// 参数判断
			if (!ToolUtil.isNotEmpty(chipNumber)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE,
						"CHIPNUMBER" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG, "", "");
				LOG.warn("chipNumber：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNotEmpty(code)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE,
						"CODE" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG, "", "");
				LOG.warn("code：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNotEmpty(ip)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE,
						"IP" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG, "", "");
				LOG.warn("ip：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNotEmpty(phone)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE,
						"PHONE" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG, "", "");
				LOG.warn("phone：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNotEmpty(logAppType)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE,
						"LOGAPPTYPE" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG, "", "");
				LOG.warn("logAppType：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}

			/** 业务逻辑 */
			// 验证码是否存在
			List<WayAccessoryLogMobileCheck> wayAccessoryLogMobileCheckList = wayAccessoryLogMobileCheckMapper
					.selectWayAccessoryLogMobileCheckByCodeAndPhone(code, phone);
			if (wayAccessoryLogMobileCheckList == null || wayAccessoryLogMobileCheckList.size() <= 0) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.VERIFYCODEERROR_FAIL_CODE,
						ErrorCodeParam.VERIFYCODEERROR_FAIL_MSG, "", "");
				LOG.info("验证码错误phone=" + phone + ",code=" + code);
				return ResponseEntity.ok(responseModel);
			}
			// 验证码 是否超时
			if (TimeUtil.getTimestamp().getTime()
					- wayAccessoryLogMobileCheckList.get(0).getCheckTime().getTime() > smsOverdueTime) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.DATAFAILURE_FAIL_CODE,
						"验证码:" + ErrorCodeParam.DATAFAILURE_FAIL_MSG, "", "");
				LOG.info("验证码超时phone=" + phone + ",code=" + code);
				return ResponseEntity.ok(responseModel);
			}
			// 查询用户信息
			WayAccessoryModelUser wayAccessoryModelUser = new WayAccessoryModelUser();
			// 状态，1为有效，0为无效
			int status = 1;
			List<WayAccessoryModelUser> wayAccessoryModelUserLsit = wayAccessoryModelUserMapper
					.selectWayAccessoryModelUserByPhone(phone, status);
			if (wayAccessoryModelUserLsit == null || wayAccessoryModelUserLsit.size() <= 0) {
				LOG.info("未查到用户:" + phone);
				// 新建用户
				wayAccessoryModelUser = ModelUtil.setWayAccessoryModelUser(phone, ip);
				wayAccessoryModelUserMapper.insert(wayAccessoryModelUser);

				// 查询或添加设备
				accessoryChipModel = AppFingerUtil.findChipOrAddChip(chipNumber, logAppType,
						wayAccessoryModelChipMapper);

				// 添加用户设备关系
				WayAccessoryRelUserChip wayAccessoryRelUserChip = ModelUtil
						.setWayAccessoryRelUserChip(wayAccessoryModelUser.getId(), accessoryChipModel.getChipId());

				wayAccessoryRelUserChipMapper.insert(wayAccessoryRelUserChip);

				// 记录登录日志 (设置登录类型：1手机登录)
				WayAccessoryLogUserLogin wayAccessoryLogUserLogin = ModelUtil.setWayAccessoryLogUserLogin(
						wayAccessoryModelUser, Long.parseLong(accessoryChipModel.getChipId().toString()),
						LoginTypeEnum.LOGINBYPHONE.getLoginTypeCode(), logAppType);

				wayAccessoryLogUserLoginMapper.insert(wayAccessoryLogUserLogin);

				// 更新用户ip地址ipService
				wayAccessoryModelUser.setIpAddress(ip);
				String city = "默认上海";
				String subdivision = "默认上海";
				String subdivisionAndCity = ipService.getSubdivisionAndCity(ip);
				if (subdivisionAndCity != null) {
					if (subdivisionAndCity.contains("^")) {
						String[] scArr = subdivisionAndCity.split("^");
						if (scArr.length == 2) {
							subdivision = scArr[0];
							city = scArr[1];
						}
					}
				}
				wayAccessoryModelUser.setCity(city);
				wayAccessoryModelUser.setPrivince(subdivision);
				wayAccessoryModelUser.updateById();

				// 新用户转成前端模型, 设置登录类型：1手机登录
				userModel = ModelReturnUtil.setUserModel(wayAccessoryModelUser,
						LoginTypeEnum.LOGINBYPHONE.getLoginTypeCode());
				userAndChip.setAccessoryChipModel(accessoryChipModel);
				userAndChip.setUserModel(userModel);
				String json = JSON.toJSONString(userAndChip);

				String token = HttpKit.getRequest().getHeader(jwtProperties.getHeader()).substring(7);
				String md5KeyFromToken = jwtTokenUtil.getMd5KeyFromToken(token);
				// md5签名
				String encrypt = MD5Util.encrypt(json + md5KeyFromToken);
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.SUCCESS_CODE, ErrorCodeParam.SUCCESS_MSG,
						encrypt, userAndChip);
				return ResponseEntity.ok(responseModel);
			} else { // 用户存在
				wayAccessoryModelUser = wayAccessoryModelUserLsit.get(0);
				// 更新用户ip地址ipService
				wayAccessoryModelUser.setIpAddress(ip);
				String city = "默认上海";
				String subdivision = "默认上海";
				String subdivisionAndCity = ipService.getSubdivisionAndCity(ip);
				if (subdivisionAndCity != null) {
					if (subdivisionAndCity.contains("^")) {
						String[] scArr = subdivisionAndCity.split("\\^");
						if (scArr.length == 2) {
							subdivision = scArr[0];
							city = scArr[1];
						}
					}
				}
				wayAccessoryModelUser.setCity(city);
				wayAccessoryModelUser.setPrivince(subdivision);
				wayAccessoryModelUser.updateById();
			}

			// 查询或添加设备
			accessoryChipModel = AppFingerUtil.findChipOrAddChip(chipNumber, logAppType, wayAccessoryModelChipMapper);

			// 设备id
			int chipId = accessoryChipModel.getChipId();

			// 查询用户设备关系，没有绑定用户设备关系
			userModel = ModelReturnUtil.setUserModel(wayAccessoryModelUser,
					LoginTypeEnum.LOGINBYPHONE.getLoginTypeCode());
			userAndChip.setAccessoryChipModel(accessoryChipModel);
			userAndChip.setUserModel(userModel);

			List<WayAccessoryRelUserChip> wayAccessoryRelUserChipList = wayAccessoryRelUserChipMapper
					.selectByChipIdAndUserId(wayAccessoryModelUser.getId().intValue(), chipId);

			if (wayAccessoryRelUserChipList == null || wayAccessoryRelUserChipList.size() <= 0) {
				// 不存在关系，添加用户和设备关系
				WayAccessoryRelUserChip wayAccessoryRelUserChip = ModelUtil
						.setWayAccessoryRelUserChip(wayAccessoryModelUser.getId(), chipId);
				wayAccessoryRelUserChipMapper.insert(wayAccessoryRelUserChip);

				// 记录登录日志
				WayAccessoryLogUserLogin wayAccessoryLogUserLogin = ModelUtil.setWayAccessoryLogUserLogin(
						wayAccessoryModelUser, Long.valueOf(chipId), LoginTypeEnum.LOGINBYPHONE.getLoginTypeCode(),
						logAppType);
				wayAccessoryLogUserLoginMapper.insert(wayAccessoryLogUserLogin);
				LOG.info("未查询到该设备和用户关系userId=" + wayAccessoryModelUser.getId() + "设备ID=" + chipId);

				String json = JSON.toJSONString(userAndChip);

				String token = HttpKit.getRequest().getHeader(jwtProperties.getHeader()).substring(7);
				String md5KeyFromToken = jwtTokenUtil.getMd5KeyFromToken(token);
				// md5签名
				String encrypt = MD5Util.encrypt(json + md5KeyFromToken);
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.SUCCESS_CODE, ErrorCodeParam.SUCCESS_MSG,
						encrypt, userAndChip);
				return ResponseEntity.ok(responseModel);
			} else {
				WayAccessoryRelUserChip wayAccessoryRelUserChip = wayAccessoryRelUserChipList.get(0);
				wayAccessoryRelUserChip.setUpdateTime(TimeUtil.getTimestamp());
				wayAccessoryRelUserChipMapper.updateAllColumnById(wayAccessoryRelUserChip);
			}

			userModel.setFingerManagerModels(new ArrayList<FingerManagerModel>());
			userAndChip.setAccessoryChipModel(accessoryChipModel);
			userAndChip.setUserModel(userModel);

			LOG.info("查询到用户设备下注册的指纹下绑定直达应用userId=" + wayAccessoryModelUser.getId() + "设备ID=" + chipId);
			String json = JSON.toJSONString(userAndChip);

			String token = HttpKit.getRequest().getHeader(jwtProperties.getHeader()).substring(7);
			String md5KeyFromToken = jwtTokenUtil.getMd5KeyFromToken(token);
			// md5签名
			String encrypt = MD5Util.encrypt(json + md5KeyFromToken);
			ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.SUCCESS_CODE, ErrorCodeParam.SUCCESS_MSG,
					encrypt, userAndChip);
			return ResponseEntity.ok(responseModel);
		} catch (Exception e) {
			ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.SERVERBUSY_FAIL_CODE,
					ErrorCodeParam.SERVERBUSY_FAIL_MSG, "", "");
			LOG.error("系统异常:" + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.ok(responseModel);
		}
	}

	/**
	 * TODO 邮箱验证注册或登录
	 * 
	 * @RequestBody-->先签名，再发请求
	 * @param mail
	 * @param code
	 * @param chipId
	 * @param ip
	 * @param logAppType
	 * @return
	 */
	@RequestMapping(value = "loginByMail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> loginByMail(@RequestBody ResponseLoginByMail responseLoginByMail) {
		/* 返回数据 */
		ResponseModel responseModel = new ResponseModel();
		String methodName = "/loginByMail";
		LOG.info(Tag + methodName);

		String mail = responseLoginByMail.getMail();
		String code = responseLoginByMail.getCode();
		String chipNumber = responseLoginByMail.getChipNumber();
		String ip = responseLoginByMail.getIp();
		int logAppType = responseLoginByMail.getLogAppType();
		LOG.info("参数mail:" + mail);
		LOG.info("参数code:" + code);
		LOG.info("参数chipNumber" + chipNumber);
		LOG.info("参数ip:" + ip);
		LOG.info("参数logType:" + logAppType);

		/** 返回用户模型 */
		UserModel userModel = new UserModel();
		/** 返回的设备和用户模型 */
		UserAndChip userAndChip = new UserAndChip();
		/** 返回的设备模型 */
		AccessoryChipModel accessoryChipModel = new AccessoryChipModel();

		try {
			// 参数判断
			if (!ToolUtil.isNotEmpty(mail)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE,
						"MAIL" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG, "", "");
				LOG.warn("mail：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!RegexUtil.checkMail(mail)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE,
						"MAIL" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG, "", "");
				LOG.warn("mail：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNotEmpty(ip)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE,
						"IP" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG, "", "");
				LOG.warn("ip：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNotEmpty(code)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE,
						"CODE" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG, "", "");
				LOG.warn("code：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNotEmpty(chipNumber)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE,
						"CHIPNUMBER" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG, "", "");
				LOG.warn("chipNumber：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNotEmpty(logAppType)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE,
						"LOGTYPE" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG, "", "");
				LOG.warn("logType：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}

			/** 业务逻辑 */
			// 验证码是否存在
			List<WayAccessoryLogMailCheck> wayAccessoryLogMailCheckList = wayAccessoryLogMailCheckMapper
					.selectWayAccessoryLogMailCheckByCodeAndMail(code, mail);
			if (wayAccessoryLogMailCheckList == null || wayAccessoryLogMailCheckList.size() <= 0) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.VERIFYCODEERROR_FAIL_CODE,
						ErrorCodeParam.VERIFYCODEERROR_FAIL_MSG, "", "");
				LOG.info("验证码错误mail=" + mail + ",code=" + code);
				return ResponseEntity.ok(responseModel);
			}
			// 验证码 是否超时
			if (TimeUtil.getTimestamp().getTime()
					- wayAccessoryLogMailCheckList.get(0).getCheckTime().getTime() > smsOverdueTime) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.DATAFAILURE_FAIL_CODE,
						"邮箱验证码" + ErrorCodeParam.DATAFAILURE_FAIL_MSG, "", "");
				LOG.info("邮箱验证码超时mail=" + mail + ",code=" + code);
				return ResponseEntity.ok(responseModel);
			}
			// 查询用户信息
			WayAccessoryModelUser wayAccessoryModelUser = new WayAccessoryModelUser();
			// 状态，1为有效，0为无效
			int status = 1;
			List<WayAccessoryModelUser> wayAccessoryModelUserLsit = wayAccessoryModelUserMapper
					.selectWayAccessoryModelUserByEmail(mail, status);
			if (wayAccessoryModelUserLsit == null || wayAccessoryModelUserLsit.size() <= 0) {
				LOG.info("未查到用户:" + mail);
				// 新建用户
				wayAccessoryModelUser = ModelUtil.setWayAccessoryModelUserByMail(mail, ip);
				wayAccessoryModelUserMapper.insert(wayAccessoryModelUser);

				// 查询或添加设备
				accessoryChipModel = AppFingerUtil.findChipOrAddChip(chipNumber, logAppType,
						wayAccessoryModelChipMapper);

				// 添加用户设备关系
				WayAccessoryRelUserChip wayAccessoryRelUserChip = ModelUtil
						.setWayAccessoryRelUserChip(wayAccessoryModelUser.getId(), accessoryChipModel.getChipId());
				wayAccessoryRelUserChipMapper.insert(wayAccessoryRelUserChip);

				// 记录登录日志，设置登录类型(2：邮箱登录)
				WayAccessoryLogUserLogin wayAccessoryLogUserLogin = ModelUtil.setWayAccessoryLogUserLogin(
						wayAccessoryModelUser, Long.parseLong(accessoryChipModel.getChipId().toString()),
						LoginTypeEnum.LOGINBYMAIL.getLoginTypeCode(), logAppType);

				wayAccessoryLogUserLoginMapper.insert(wayAccessoryLogUserLogin);

				// 更新用户ip地址ipService
				wayAccessoryModelUser.setIpAddress(ip);
				String city = "默认上海";
				String subdivision = "默认上海";
				String subdivisionAndCity = ipService.getSubdivisionAndCity(ip);
				if (subdivisionAndCity != null) {
					if (subdivisionAndCity.contains("^")) {
						String[] scArr = subdivisionAndCity.split("^");
						if (scArr.length == 2) {
							subdivision = scArr[0];
							city = scArr[1];
						}
					}
				}
				wayAccessoryModelUser.setCity(city);
				wayAccessoryModelUser.setPrivince(subdivision);
				wayAccessoryModelUser.updateById();

				// 新用户转成前端模型
				userModel = ModelReturnUtil.setUserModel(wayAccessoryModelUser,
						LoginTypeEnum.LOGINBYMAIL.getLoginTypeCode());
				userAndChip.setAccessoryChipModel(accessoryChipModel);
				userAndChip.setUserModel(userModel);
				String json = JSON.toJSONString(userAndChip);

				String token = HttpKit.getRequest().getHeader(jwtProperties.getHeader()).substring(7);
				String md5KeyFromToken = jwtTokenUtil.getMd5KeyFromToken(token);
				// md5签名
				String encrypt = MD5Util.encrypt(json + md5KeyFromToken);
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.SUCCESS_CODE, ErrorCodeParam.SUCCESS_MSG,
						encrypt, userAndChip);
				return ResponseEntity.ok(responseModel);
			} else { // 用户存在
				wayAccessoryModelUser = wayAccessoryModelUserLsit.get(0);
				// 更新用户ip地址ipService
				wayAccessoryModelUser.setIpAddress(ip);
				String city = "默认上海";
				String subdivision = "默认上海";
				String subdivisionAndCity = ipService.getSubdivisionAndCity(ip);
				if (subdivisionAndCity != null) {
					if (subdivisionAndCity.contains("^")) {
						String[] scArr = subdivisionAndCity.split("\\^");
						if (scArr.length == 2) {
							subdivision = scArr[0];
							city = scArr[1];
						}
					}
				}
				wayAccessoryModelUser.setCity(city);
				wayAccessoryModelUser.setPrivince(subdivision);
				wayAccessoryModelUser.updateById(); // 更新记录
			}
			// 查询或添加设备
			accessoryChipModel = AppFingerUtil.findChipOrAddChip(chipNumber, logAppType, wayAccessoryModelChipMapper);

			// 设备id
			int chipId = accessoryChipModel.getChipId();

			// 查询用户设备关系，没有绑定用户设备关系(设置登录类型：2邮箱登录)
			userModel = ModelReturnUtil.setUserModel(wayAccessoryModelUser,
					LoginTypeEnum.LOGINBYMAIL.getLoginTypeCode());
			userAndChip.setAccessoryChipModel(accessoryChipModel);
			userAndChip.setUserModel(userModel);

			List<WayAccessoryRelUserChip> wayAccessoryRelUserChipList = wayAccessoryRelUserChipMapper
					.selectByChipIdAndUserId(wayAccessoryModelUser.getId().intValue(), chipId);

			if (wayAccessoryRelUserChipList == null || wayAccessoryRelUserChipList.size() <= 0) {
				// 不存在关系，添加用户和设备关系
				WayAccessoryRelUserChip wayAccessoryRelUserChip = ModelUtil
						.setWayAccessoryRelUserChip(wayAccessoryModelUser.getId(), chipId);
				wayAccessoryRelUserChipMapper.insert(wayAccessoryRelUserChip);

				// 记录登录日志(设置登录类型：2邮箱登录)
				WayAccessoryLogUserLogin wayAccessoryLogUserLogin = ModelUtil.setWayAccessoryLogUserLogin(
						wayAccessoryModelUser, Long.valueOf(chipId), LoginTypeEnum.LOGINBYMAIL.getLoginTypeCode(),
						logAppType);
				wayAccessoryLogUserLoginMapper.insert(wayAccessoryLogUserLogin);
				LOG.info("未查询到该设备和用户关系userId=" + wayAccessoryModelUser.getId() + "设备ID=" + chipId);

				String json = JSON.toJSONString(userAndChip);

				String token = HttpKit.getRequest().getHeader(jwtProperties.getHeader()).substring(7);
				String md5KeyFromToken = jwtTokenUtil.getMd5KeyFromToken(token);
				// md5签名
				String encrypt = MD5Util.encrypt(json + md5KeyFromToken);
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.SUCCESS_CODE, ErrorCodeParam.SUCCESS_MSG,
						encrypt, userAndChip);
				return ResponseEntity.ok(responseModel);
			} else {
				WayAccessoryRelUserChip wayAccessoryRelUserChip = wayAccessoryRelUserChipList.get(0);
				wayAccessoryRelUserChip.setUpdateTime(TimeUtil.getTimestamp());
				wayAccessoryRelUserChipMapper.updateAllColumnById(wayAccessoryRelUserChip);
			}

			userModel.setFingerManagerModels(new ArrayList<FingerManagerModel>());
			userAndChip.setAccessoryChipModel(accessoryChipModel);
			userAndChip.setUserModel(userModel);

			LOG.info("查询到用户设备下注册的指纹下绑定直达应用userId=" + wayAccessoryModelUser.getId() + "设备ID=" + chipId);
			String json = JSON.toJSONString(userAndChip);

			String token = HttpKit.getRequest().getHeader(jwtProperties.getHeader()).substring(7);
			String md5KeyFromToken = jwtTokenUtil.getMd5KeyFromToken(token);
			// md5签名
			String encrypt = MD5Util.encrypt(json + md5KeyFromToken);
			ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.SUCCESS_CODE, ErrorCodeParam.SUCCESS_MSG,
					encrypt, userAndChip);
			return ResponseEntity.ok(responseModel);
		} catch (Exception e) {
			ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.SERVERBUSY_FAIL_CODE,
					ErrorCodeParam.SERVERBUSY_FAIL_MSG, "", "");
			LOG.error("系统异常:" + e);
			return ResponseEntity.ok(responseModel);
		}
	}

	/**
	 * TODO 指纹验证登录
	 * 
	 * @param userId
	 * @param chipNumber
	 * @param logAppType
	 * @return
	 */
	@RequestMapping(value = "loginByFp", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> loginByFp(@RequestBody ResponseLoginFp responseLoginFp) {

		UserAndChip userAndChip = new UserAndChip();
		String methodName = "/loginByFp";
		LOG.info(Tag + methodName);
		String chipNumber = responseLoginFp.getChipNumber();
		int userId = responseLoginFp.getUserId();
		int logAppType = responseLoginFp.getLogAppType();
		LOG.info("参数userId:" + userId);
		LOG.info("参数chipNumber:" + chipNumber);
		LOG.info("参数logAppType:" + logAppType);
		ResponseModel responseModel = new ResponseModel();
		try {
			// 参数判断
			if (!ToolUtil.isNotEmpty(userId)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE,
						"USERID" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG, "", "");
				LOG.warn("userId：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNum(userId)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE,
						"USERID" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG, "", "");
				LOG.warn("userId：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNotEmpty(chipNumber)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE,
						"CHIPNUMBER" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG, "", "");
				LOG.warn("chipNumber：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNotEmpty(logAppType)) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE,
						"CHIPNUMBER" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG, "", "");
				LOG.warn("logAppType：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}

			// 业务逻辑
			// 查询用户信息
			WayAccessoryModelUser wayAccessoryModelUser = wayAccessoryModelUserMapper.selectById(userId);
			if (wayAccessoryModelUser == null) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.NODATA_FAIL_CODE,
						"用户信息:" + ErrorCodeParam.NODATA_FAIL_MSG, "", "");
				LOG.info("未查到用户userId=" + userId);
				return ResponseEntity.ok(responseModel);
			}

			// 查询设备信息
			WayAccessoryModelChip wayAccessoryModelChip = wayAccessoryModelChipMapper
					.selectWayAccessoryModelChipByChipNumber(chipNumber, logAppType, 1);
			AccessoryChipModel accessoryChipModel = new AccessoryChipModel();
			int chipId = 0;
			if (wayAccessoryModelChip == null) {
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.NODATA_FAIL_CODE,
						"设备" + ErrorCodeParam.NODATA_FAIL_MSG, "", "");
				LOG.info("未查到设备Id：" + chipNumber);
				return ResponseEntity.ok(responseModel);
			} else {
				chipId = wayAccessoryModelChip.getId().intValue();
				accessoryChipModel.setChipBrand(wayAccessoryModelChip.getChipBrand());
				accessoryChipModel.setChipId(chipId);
				accessoryChipModel.setChipNumber(wayAccessoryModelChip.getChipNumber());
				LOG.info("登录记录");
			}

			// 查询用户设备关系，没有只返回用户信息
			UserModel userModel = ModelReturnUtil.setUserModel(wayAccessoryModelUser,
					LoginTypeEnum.LOGINBYFINGER.getLoginTypeCode());
			userAndChip.setAccessoryChipModel(accessoryChipModel);
			userAndChip.setUserModel(userModel);
			List<WayAccessoryRelUserChip> wayAccessoryRelUserChipList = wayAccessoryRelUserChipMapper
					.selectByChipIdAndUserId(userId, chipId);
			if (wayAccessoryRelUserChipList == null || wayAccessoryRelUserChipList.size() <= 0) {
				// 记录登录日志(设置登录类型：3指纹登录)
				WayAccessoryLogUserLogin wayAccessoryLogUserLogin = ModelUtil.setWayAccessoryLogUserLogin(
						wayAccessoryModelUser, 0L, LoginTypeEnum.LOGINBYFINGER.getLoginTypeCode(), logAppType);
				wayAccessoryLogUserLoginMapper.insert(wayAccessoryLogUserLogin);
				LOG.info("未查询到该设备和用户关系userId=" + userId + "设备ID=" + chipId);

				String json = JSON.toJSONString(userAndChip);

				String token = HttpKit.getRequest().getHeader(jwtProperties.getHeader()).substring(7);
				String md5KeyFromToken = jwtTokenUtil.getMd5KeyFromToken(token);
				// md5签名
				String encrypt = MD5Util.encrypt(json + md5KeyFromToken);
				ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.NODATA_FAIL_CODE,
						ErrorCodeParam.NODATA_FAIL_MSG, encrypt, userAndChip);
				return ResponseEntity.ok(responseModel);
			}

			// 设置用户设备下指纹
			userModel.setFingerManagerModels(new ArrayList<FingerManagerModel>());
			userAndChip.setAccessoryChipModel(accessoryChipModel);
			userAndChip.setUserModel(userModel);
			LOG.info("登录记录");
			// 记录登录日志
			WayAccessoryLogUserLogin wayAccessoryLogUserLogin = ModelUtil.setWayAccessoryLogUserLogin(
					wayAccessoryModelUser, Long.valueOf(chipId), LoginTypeEnum.LOGINBYFINGER.getLoginTypeCode(),
					logAppType);
			wayAccessoryLogUserLoginMapper.insert(wayAccessoryLogUserLogin);

			LOG.info("结束userId=" + userId + "设备ID=" + chipId);
			String json = JSON.toJSONString(userAndChip);

			String token = HttpKit.getRequest().getHeader(jwtProperties.getHeader()).substring(7);
			String md5KeyFromToken = jwtTokenUtil.getMd5KeyFromToken(token);
			// md5签名
			String encrypt = MD5Util.encrypt(json + md5KeyFromToken);
			ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.SUCCESS_CODE, ErrorCodeParam.SUCCESS_MSG,
					encrypt, userAndChip);
			return ResponseEntity.ok(responseModel);
		} catch (Exception e) {
			ModelReturnUtil.setResponseModel(responseModel, ErrorCodeParam.SERVERBUSY_FAIL_CODE,
					ErrorCodeParam.SERVERBUSY_FAIL_MSG, "", "");
			LOG.error("系统异常" + e);
			return ResponseEntity.ok(responseModel);
		}
	}
}