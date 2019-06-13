package com.stylefeng.guns.rest.way.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryLogUserActionMapper;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryModelUserMapper;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryLogUserAction;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryLogWindowsVersion;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryModelUser;
import com.stylefeng.guns.rest.way.model.ReportUserAction;
import com.stylefeng.guns.rest.way.model.ResponseModel;
import com.stylefeng.guns.rest.way.model.WindowsType;
import com.stylefeng.guns.rest.way.util.ErrorCodeParam;
import com.stylefeng.guns.rest.way.util.ModelUtil;
import com.stylefeng.guns.rest.way.util.TimeUtil;

/**
 * 上报接口
 * 
 * @author lori
 *
 */
@RestController
@RequestMapping("wayapi/v1/accessory/report")
public class ReportController {

	private static final Logger LOG = LoggerFactory.getLogger(ReportController.class);
	private static String Tag = "ReportController";

	@Autowired
	private WayAccessoryModelUserMapper wayAccessoryModelUserMapper;
	@Autowired
	private WayAccessoryLogUserActionMapper wayAccessoryLogUserActionMapper;

	/**
	 * TODO 用户行为上报接口
	 * 
	 * @param userId
	 * @param Int
	 *            type(操作类型： 1.客户端启动 2.用户手机号登录 3.用户退出 4.客户端切换帐号 5.指纹登录/解锁点击次数
	 *            6.文件加解密操作 7.网页/应用快捷直达点击次数 8.应用/网站免密登录点击次数 9.使用小贴士点击次数 10.帮助中心点击次数
	 *            11.客户端退出点击次数 12.隐私空间点击次数 13.文件加解密点击次数)
	 * @param number
	 *            加解密的次数
	 * @return
	 */
	@RequestMapping(value = "userAction")
	public ResponseEntity<?> userAction(ReportUserAction reportUserAction) {

		String methodName = "/userAction";
		LOG.info(Tag + methodName);
		int userId = reportUserAction.getUserId();
		int type = reportUserAction.getType();
		int number = reportUserAction.getNumber();
		LOG.info("userId = " + userId);
		LOG.info("type = " + type);
		LOG.info("number = " + number);

		ResponseModel responseModel = new ResponseModel();
		try {
			/** 参数判断 start **/
			if (!ToolUtil.isNotEmpty(userId)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG + "USERID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("userId：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNum(userId)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG + "USERID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("userId：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNotEmpty(type)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG + "TYPE");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("type：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNum(type)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG + "TYPE");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("type：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNotEmpty(number)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("number：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNum(number)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("number：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			WayAccessoryLogUserAction wayAccessoryLogUserAction = new WayAccessoryLogUserAction();
			// userId 为空，客户端启动或者退出
			if (userId == 0) {
				if (type == 1) {
					ModelUtil.setWayAccessoryLogUserAction(1, 0);
				} else if (type == 11) {
					ModelUtil.setWayAccessoryLogUserAction(12, 0);
				} else {
					responseModel.setResponseCode(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
					responseModel.setResponseMessage(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG + "TYPE");
					responseModel.setObject("");
					responseModel.setSign("");
					LOG.warn("type：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
					return ResponseEntity.ok(responseModel);
				}
			} else {
				WayAccessoryModelUser wayAccessoryModelUser = wayAccessoryModelUserMapper.selectById(userId);
				if (wayAccessoryModelUser == null) {
					responseModel.setResponseCode(ErrorCodeParam.NODATA_FAIL_CODE);
					responseModel.setResponseMessage("用户信息" + ErrorCodeParam.NODATA_FAIL_MSG);
					responseModel.setObject("");
					responseModel.setSign("");
					LOG.info("没有用户信息userId=" + userId);
					return ResponseEntity.ok(responseModel);

				}
				// 判断上报类型
				String checkDate = TimeUtil.getTodayDateString();
				if (type == 2) {

					wayAccessoryLogUserAction = wayAccessoryLogUserActionMapper
							.selectWayAccessoryLogUserActionByUserBy(3, checkDate, userId);
					if (ToolUtil.isNotEmpty(wayAccessoryLogUserAction)) {
						wayAccessoryLogUserAction.setCount(wayAccessoryLogUserAction.getCount() + 1);
						wayAccessoryLogUserActionMapper.updateById(wayAccessoryLogUserAction);
					} else {
						ModelUtil.setWayAccessoryLogUserAction(3, userId);
					}
				} else if (type == 3) {
					wayAccessoryLogUserAction = wayAccessoryLogUserActionMapper
							.selectWayAccessoryLogUserActionByUserBy(4, checkDate, userId);
					if (ToolUtil.isNotEmpty(wayAccessoryLogUserAction)) {

						wayAccessoryLogUserAction.setCount(wayAccessoryLogUserAction.getCount() + 1);
						wayAccessoryLogUserActionMapper.updateById(wayAccessoryLogUserAction);
					} else {
						ModelUtil.setWayAccessoryLogUserAction(4, userId);
					}

				} else if (type == 4) {
					wayAccessoryLogUserAction = wayAccessoryLogUserActionMapper
							.selectWayAccessoryLogUserActionByUserBy(5, checkDate, userId);
					if (ToolUtil.isNotEmpty(wayAccessoryLogUserAction)) {
						wayAccessoryLogUserAction.setCount(wayAccessoryLogUserAction.getCount() + 1);
						wayAccessoryLogUserActionMapper.updateById(wayAccessoryLogUserAction);
					} else {
						ModelUtil.setWayAccessoryLogUserAction(5, userId);
					}

				} else if (type == 5) {

					wayAccessoryLogUserAction = wayAccessoryLogUserActionMapper
							.selectWayAccessoryLogUserActionByUserBy(6, checkDate, userId);
					if (ToolUtil.isNotEmpty(wayAccessoryLogUserAction)) {
						wayAccessoryLogUserAction.setCount(wayAccessoryLogUserAction.getCount() + 1);
						wayAccessoryLogUserActionMapper.updateById(wayAccessoryLogUserAction);
					} else {
						ModelUtil.setWayAccessoryLogUserAction(6, userId);
					}

				} else if (type == 6) {
					number = wayAccessoryModelUser.getNumber() + number;
					wayAccessoryModelUser.setNumber(number);
					wayAccessoryModelUser.updateById();
					wayAccessoryLogUserAction = wayAccessoryLogUserActionMapper
							.selectWayAccessoryLogUserActionByUserBy(7, checkDate, userId);
					if (ToolUtil.isNotEmpty(wayAccessoryLogUserAction)) {
						wayAccessoryLogUserAction.setCount(wayAccessoryLogUserAction.getCount() + 1);
						wayAccessoryLogUserActionMapper.updateById(wayAccessoryLogUserAction);
					} else {
						ModelUtil.setWayAccessoryLogUserAction(7, userId);
					}

				} else if (type == 7) {
					wayAccessoryLogUserAction = wayAccessoryLogUserActionMapper
							.selectWayAccessoryLogUserActionByUserBy(8, checkDate, userId);
					if (ToolUtil.isNotEmpty(wayAccessoryLogUserAction)) {
						wayAccessoryLogUserAction.setCount(wayAccessoryLogUserAction.getCount() + 1);
						wayAccessoryLogUserActionMapper.updateById(wayAccessoryLogUserAction);
					} else {
						ModelUtil.setWayAccessoryLogUserAction(8, userId);
					}

				} else if (type == 8) {
					wayAccessoryLogUserAction = wayAccessoryLogUserActionMapper
							.selectWayAccessoryLogUserActionByUserBy(9, checkDate, userId);
					if (ToolUtil.isNotEmpty(wayAccessoryLogUserAction)) {
						wayAccessoryLogUserAction.setCount(wayAccessoryLogUserAction.getCount() + 1);
						wayAccessoryLogUserActionMapper.updateById(wayAccessoryLogUserAction);
					} else {
						ModelUtil.setWayAccessoryLogUserAction(9, userId);
					}

				} else if (type == 9) {
					wayAccessoryLogUserAction = wayAccessoryLogUserActionMapper
							.selectWayAccessoryLogUserActionByUserBy(10, checkDate, userId);
					if (ToolUtil.isNotEmpty(wayAccessoryLogUserAction)) {
						wayAccessoryLogUserAction.setCount(wayAccessoryLogUserAction.getCount() + 1);
						wayAccessoryLogUserActionMapper.updateById(wayAccessoryLogUserAction);
					} else {
						ModelUtil.setWayAccessoryLogUserAction(10, userId);
					}

				} else if (type == 10) {
					wayAccessoryLogUserAction = wayAccessoryLogUserActionMapper
							.selectWayAccessoryLogUserActionByUserBy(11, checkDate, userId);
					if (ToolUtil.isNotEmpty(wayAccessoryLogUserAction)) {
						wayAccessoryLogUserAction.setCount(wayAccessoryLogUserAction.getCount() + 1);
						wayAccessoryLogUserActionMapper.updateById(wayAccessoryLogUserAction);
					} else {
						ModelUtil.setWayAccessoryLogUserAction(11, userId);
					}

				} else if (type == 12) {
					wayAccessoryLogUserAction = wayAccessoryLogUserActionMapper
							.selectWayAccessoryLogUserActionByUserBy(13, checkDate, userId);
					if (ToolUtil.isNotEmpty(wayAccessoryLogUserAction)) {
						wayAccessoryLogUserAction.setCount(wayAccessoryLogUserAction.getCount() + 1);
						wayAccessoryLogUserActionMapper.updateById(wayAccessoryLogUserAction);
					} else {
						ModelUtil.setWayAccessoryLogUserAction(13, userId);
					}
				} else if (type == 13) {
					wayAccessoryLogUserAction = wayAccessoryLogUserActionMapper
							.selectWayAccessoryLogUserActionByUserBy(14, checkDate, userId);
					if (ToolUtil.isNotEmpty(wayAccessoryLogUserAction)) {
						wayAccessoryLogUserAction.setCount(wayAccessoryLogUserAction.getCount() + 1);
						wayAccessoryLogUserActionMapper.updateById(wayAccessoryLogUserAction);
					} else {
						ModelUtil.setWayAccessoryLogUserAction(14, userId);
					}
				} else {
					responseModel.setResponseCode(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
					responseModel.setResponseMessage(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG + "TYPE");
					responseModel.setObject("");
					responseModel.setSign("");
					LOG.warn("type：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
					return ResponseEntity.ok(responseModel);
				}
			}

			responseModel.setResponseCode(ErrorCodeParam.SUCCESS_CODE);
			responseModel.setResponseMessage(ErrorCodeParam.SUCCESS_MSG);
			responseModel.setObject("");
			responseModel.setSign("");

			return ResponseEntity.ok(responseModel);
		} catch (Exception e) {
			responseModel.setResponseCode(ErrorCodeParam.SERVERBUSY_FAIL_CODE);
			responseModel.setResponseMessage(ErrorCodeParam.SERVERBUSY_FAIL_MSG);
			responseModel.setObject("");
			responseModel.setSign("");
			LOG.error("系统异常" + e);
			return ResponseEntity.ok(responseModel);
		}
	}

	/**
	 * TODO 操作系统上报接口
	 * 
	 * @param windowsType（window版本
	 *            1.win7，2.win8 3.win8.1,4.win10）
	 * @return
	 */
	@RequestMapping(value = "windowsVersion")
	public ResponseEntity<?> windowsVersion(WindowsType windowsTypes) {

		String methodName = "/windowsVersion";
		LOG.info(Tag + methodName);
		int windowsType = windowsTypes.getWindowsType();
		LOG.info("windowsType = " + windowsType);
		ResponseModel responseModel = new ResponseModel();
		if (!ToolUtil.isNotEmpty(windowsType)) {
			responseModel.setResponseCode(ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE);
			responseModel.setResponseMessage(ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG + "WINDOWSTYPE");
			responseModel.setObject("");
			responseModel.setSign("");
			LOG.warn("windowsType：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
			return ResponseEntity.ok(responseModel);
		}
		if (!ToolUtil.isNotEmpty(String.valueOf(windowsType))) {
			responseModel.setResponseCode(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
			responseModel.setResponseMessage(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG + "WINDOWSTYPE");
			responseModel.setObject("");
			responseModel.setSign("");
			LOG.warn("windowsType：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
			return ResponseEntity.ok(responseModel);
		}

		if (windowsType != 1 && windowsType != 2 && windowsType != 3 && windowsType != 4) {
			responseModel.setResponseCode(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
			responseModel.setResponseMessage(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG + "WINDOWSTYPE");
			responseModel.setObject("");
			responseModel.setSign("");
			LOG.warn("windowsType：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
			return ResponseEntity.ok(responseModel);
		}

		try {
			/** 参数判断 start **/

			WayAccessoryLogWindowsVersion wayAccessoryLogWindowsVersion = new WayAccessoryLogWindowsVersion();

			wayAccessoryLogWindowsVersion.setVersion(windowsType);
			wayAccessoryLogWindowsVersion.setCreateTime(TimeUtil.getTimestamp());
			wayAccessoryLogWindowsVersion.insert();

			responseModel.setResponseCode(ErrorCodeParam.SUCCESS_CODE);
			responseModel.setResponseMessage(ErrorCodeParam.SUCCESS_MSG);
			responseModel.setObject("");
			responseModel.setSign("");

			return ResponseEntity.ok(responseModel);
		} catch (Exception e) {

			responseModel.setResponseCode(ErrorCodeParam.SERVERBUSY_FAIL_CODE);
			responseModel.setResponseMessage(ErrorCodeParam.SERVERBUSY_FAIL_MSG);
			responseModel.setObject("");
			responseModel.setSign("");
			LOG.error("系统异常" + e);
			return ResponseEntity.ok(responseModel);
		}
	}
}
