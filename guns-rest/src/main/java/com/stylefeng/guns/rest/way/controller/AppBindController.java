package com.stylefeng.guns.rest.way.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryLogQuickLaunchMapper;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryRelUserChipFingerprintMapper;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryRelUserChipQuickLaunchMapper;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryLogQuickLaunch;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryRelUserChipFingerprint;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryRelUserChipQuickLaunch;
import com.stylefeng.guns.rest.way.model.RequestEditBind;
import com.stylefeng.guns.rest.way.model.ResponseBilndDelete;
import com.stylefeng.guns.rest.way.model.ResponseModel;
import com.stylefeng.guns.rest.way.util.AppFingerUtil;
import com.stylefeng.guns.rest.way.util.ErrorCodeParam;
import com.stylefeng.guns.rest.way.util.ModelUtil;

/**
 * 用户直达应用管理接口
 * 
 * @author lori
 *
 */
@Controller
@RequestMapping("wayapi/v1/accessory/app")
public class AppBindController {

	private static final Logger LOG = LoggerFactory.getLogger(AppBindController.class);
	private static String Tag = "AppBindController";

	@Autowired
	private WayAccessoryRelUserChipQuickLaunchMapper wayAccessoryRelUserChipQuickLaunchMapper;
	@Autowired
	private WayAccessoryRelUserChipFingerprintMapper wayAccessoryRelUserChipFingerprintMapper;
	@Autowired
	private WayAccessoryLogQuickLaunchMapper wayAccessoryLogQuickLaunchMapper;

	/**
	 * TODO 应用直达绑定修改
	 * 
	 * @param fpId
	 * @param type
	 * @param appPath
	 * @return
	 */
	@RequestMapping(value = "editBindInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> getBindInfo(@RequestBody RequestEditBind requestEditBind) {

		String methodName = "/editBindInfo";
		LOG.info(Tag + methodName);
		int fpId = requestEditBind.getFpId();
		int type = requestEditBind.getType();
		String appPath = requestEditBind.getUrl();
		LOG.info("fpId = " + fpId);
		LOG.info("type = " + type);
		LOG.info("url = " + appPath);

		ResponseModel responseModel = new ResponseModel();
		try {
			/** 参数判断 start **/
			if (!ToolUtil.isNotEmpty(fpId)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG + "FPID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("fpId：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNum(fpId)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG + "FPID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("fpId：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
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
			if (type != 1 && type != 2 && type != 3) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG + "TYPE");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("type：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}

			if (!ToolUtil.isNotEmpty(appPath)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG + "APPPATH");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("appPath：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			/** 参数判断 end **/

			/** 业务逻辑 start **/
			// 根据指纹id查询指纹信息
			WayAccessoryRelUserChipFingerprint wayAccessoryRelUserChipFingerprint = wayAccessoryRelUserChipFingerprintMapper
					.selectById(fpId);
			if (wayAccessoryRelUserChipFingerprint == null) {

				responseModel.setResponseCode(ErrorCodeParam.NODATA_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.NODATA_FAIL_MSG+"FPID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("fpId：" + ErrorCodeParam.NODATA_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}

			if (wayAccessoryRelUserChipFingerprint.getStatus() == 0) {
				responseModel.setResponseCode(ErrorCodeParam.DATAFAILURE_FAIL_CODE);
				responseModel.setResponseMessage("该指纹" + ErrorCodeParam.DATAFAILURE_FAIL_MSG);
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("指纹" + ErrorCodeParam.DATAFAILURE_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}

			List<WayAccessoryRelUserChipQuickLaunch> wayAccessoryRelUserChipQuickLaunchList = new ArrayList<WayAccessoryRelUserChipQuickLaunch>();
			WayAccessoryRelUserChipQuickLaunch wayAccessoryRelUserChipQuickLaunch = new WayAccessoryRelUserChipQuickLaunch();
			wayAccessoryRelUserChipQuickLaunchList = wayAccessoryRelUserChipQuickLaunchMapper
					.selectWayAccessoryRelUserChipQuickLaunchMapperByFingerprintInfoId(fpId + "");
			if (wayAccessoryRelUserChipQuickLaunchList == null || wayAccessoryRelUserChipQuickLaunchList.size() <= 0) {

				// 不存在指纹直达，直接添加指纹直达信息
				int isAdd = ModelUtil.saveWayAccessoryRelUserChipQuickLaunch(Long.parseLong(fpId + ""), type, appPath,
						wayAccessoryRelUserChipQuickLaunchMapper);
				if (isAdd == 0) {
					responseModel.setResponseCode(ErrorCodeParam.ADDFAILED_FAIL_CODE);
					responseModel.setResponseMessage(ErrorCodeParam.ADDFAILED_FAIL_MSG);
					responseModel.setObject("");
					responseModel.setSign("");
					LOG.warn(ErrorCodeParam.ADDFAILED_FAIL_MSG);
					return ResponseEntity.ok(responseModel);
				}

				// 统计直达
				countQuick(type, appPath);

				responseModel.setResponseCode(ErrorCodeParam.SUCCESS_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.SUCCESS_MSG);
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.info("添加成功");
				return ResponseEntity.ok(responseModel);

			} else {

				// 存在指纹直达，直接修改指纹直达信息
				wayAccessoryRelUserChipQuickLaunch = wayAccessoryRelUserChipQuickLaunchList.get(0);
				boolean isUp = ModelUtil.updateWayAccessoryRelUserChipQuickLaunch(wayAccessoryRelUserChipQuickLaunch,
						type, appPath, wayAccessoryRelUserChipQuickLaunchMapper);
				if (!isUp) {
					responseModel.setResponseCode(ErrorCodeParam.MODIFYFAILED_FAIL_CODE);
					responseModel.setResponseMessage(ErrorCodeParam.MODIFYFAILED_FAIL_MSG);
					responseModel.setObject("");
					responseModel.setSign("");
					LOG.warn(ErrorCodeParam.MODIFYFAILED_FAIL_MSG);
					return ResponseEntity.ok(responseModel);
				}

				// 统计直达
				countQuick(type, appPath);

				responseModel.setResponseCode(ErrorCodeParam.SUCCESS_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.SUCCESS_MSG);
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.info("添加成功");
				return ResponseEntity.ok(responseModel);

			}
			/** 业务逻辑 end **/
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
	 * TODO 应用直达删除
	 * 
	 * @param quickId
	 * @return
	 */
	@RequestMapping(value = "delBindInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> delBindInfo(@RequestBody ResponseBilndDelete responseBilndDelete) {

		String methodName = "/delBindInfo";
		LOG.info(Tag + methodName);
		int quickId = responseBilndDelete.getQuickId();
		LOG.info("quickId = " + quickId);

		ResponseModel responseModel = new ResponseModel();
		try {
			/** 参数判断 start **/
			if (!ToolUtil.isNotEmpty(quickId)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG+"QUICKID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("quickId：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNum(quickId)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG+"QUICKID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("quickId：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			/** 参数判断 end **/

			/** 业务逻辑 start **/
			// 查询id是否存在，如果存在，就删除
			WayAccessoryRelUserChipQuickLaunch wayAccessoryRelUserChipQuickLaunchList = (WayAccessoryRelUserChipQuickLaunch) wayAccessoryRelUserChipQuickLaunchMapper
					.selectById(quickId);
			if (wayAccessoryRelUserChipQuickLaunchList == null) {

				responseModel.setResponseCode(ErrorCodeParam.NODATA_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.NODATA_FAIL_MSG+"QUICKID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("quickId：" + ErrorCodeParam.NODATA_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}

			int isDelete = wayAccessoryRelUserChipQuickLaunchMapper.deleteById(Long.parseLong(quickId + ""));
			if (isDelete != 1) {
				responseModel.setResponseCode(ErrorCodeParam.DELETEFAILED_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.DELETEFAILED_FAIL_MSG);
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn(ErrorCodeParam.DELETEFAILED_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}

			responseModel.setResponseCode(ErrorCodeParam.SUCCESS_CODE);
			responseModel.setResponseMessage(ErrorCodeParam.SUCCESS_MSG);
			responseModel.setObject("");
			responseModel.setSign("");
			LOG.info("删除指纹直达结束");
			return ResponseEntity.ok(responseModel);

			/** 业务逻辑 end **/

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
	 * TODO 统计直达统计
	 * 
	 * @param type
	 * @param appPath
	 */
	private void countQuick(int type, String path) {
		try {
			// 存取网站
			if (type == 1) {
				// 判断是否为浏览器地址
				if (AppFingerUtil.isURL(path)) {
					URL url = new URL(path);
					path = url.getHost();
				}
				if (!ToolUtil.isNotEmpty(path)) {
					WayAccessoryLogQuickLaunch wayAccessoryLogQuickLaunch = wayAccessoryLogQuickLaunchMapper
							.selectWeb(path);
					int amount = wayAccessoryLogQuickLaunchMapper.selectAmountWebAll();
					if (ToolUtil.isNotEmpty(wayAccessoryLogQuickLaunch)) {
						wayAccessoryLogQuickLaunch.setNumber(wayAccessoryLogQuickLaunch.getNumber() + 1);
						wayAccessoryLogQuickLaunch
								.setPercent(wayAccessoryLogQuickLaunch.getNumber() / Float.parseFloat(amount + ""));
						wayAccessoryLogQuickLaunch.updateById();
					} else {
						ModelUtil.setWayAccessoryLogQuickLaunch(type, wayAccessoryLogQuickLaunch, path, "", amount);
					}
				}
			} else if (type == 2) {
				// 将应用直达收录
				if (path.indexOf("/") > -1) {
					path = path.substring(path.lastIndexOf("/") + 1, path.length());
				}
				WayAccessoryLogQuickLaunch wayAccessoryLogQuickLaunch = wayAccessoryLogQuickLaunchMapper
						.selectApp(path);
				int amount = wayAccessoryLogQuickLaunchMapper.selectAmountAppAll();
				if (ToolUtil.isNotEmpty(wayAccessoryLogQuickLaunch)) {
					wayAccessoryLogQuickLaunch.setNumber(wayAccessoryLogQuickLaunch.getNumber() + 1);
					wayAccessoryLogQuickLaunch
							.setPercent(wayAccessoryLogQuickLaunch.getNumber() / Float.parseFloat(amount + ""));
					wayAccessoryLogQuickLaunch.updateById();
				} else {
					ModelUtil.setWayAccessoryLogQuickLaunch(type, wayAccessoryLogQuickLaunch, "", path, amount);
				}
			}
		} catch (Exception e) {
			LOG.error(Tag + "系统异常" + e);
		}
	}

}