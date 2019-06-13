package com.stylefeng.guns.rest.way.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryLogFingerprintCaptureMapper;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryModelChipMapper;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryModelUserMapper;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryRelUserChipFingerprintMapper;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryRelUserChipMapper;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryRelUserChipQuickLaunchMapper;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryLogFingerprintCapture;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryModelChip;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryModelUser;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryRelUserChip;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryRelUserChipFingerprint;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryRelUserChipQuickLaunch;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.way.model.FingerManagerModel;
import com.stylefeng.guns.rest.way.model.ResponceDelete;
import com.stylefeng.guns.rest.way.model.ResponseFP;
import com.stylefeng.guns.rest.way.model.ResponseGetFp;
import com.stylefeng.guns.rest.way.model.ResponseModel;
import com.stylefeng.guns.rest.way.model.ResponseRegister;
import com.stylefeng.guns.rest.way.util.ErrorCodeParam;
import com.stylefeng.guns.rest.way.util.ModelReturnUtil;
import com.stylefeng.guns.rest.way.util.ModelUtil;
import com.stylefeng.guns.rest.way.util.TimeUtil;

/**
 * 用户指纹管理接口
 * 
 * @author lori
 *
 */
@Controller
@RequestMapping("wayapi/v1/accessory/fp")
public class AppFingerController {

	private static final Logger LOG = LoggerFactory.getLogger(AppFingerController.class);
	private static String Tag = "AppFingerController";

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtProperties jwtProperties;
	@Autowired
	private WayAccessoryRelUserChipFingerprintMapper wayAccessoryRelUserChipFingerprintMapper;
	@Autowired
	private WayAccessoryModelUserMapper wayAccessoryModelUserMapper;
	@Autowired
	private WayAccessoryModelChipMapper wayAccessoryModelChipMapper;
	@Autowired
	private WayAccessoryRelUserChipMapper wayAccessoryRelUserChipMapper;
	@Autowired
	private WayAccessoryLogFingerprintCaptureMapper wayAccessoryLogFingerprintCaptureMapper;
	@Autowired
	private WayAccessoryRelUserChipQuickLaunchMapper wayAccessoryRelUserChipQuickLaunchMapper;

	/**
	 * TODO 指纹注册接口
	 * 
	 * @param userId
	 * @param chipId
	 * @param location
	 * @return
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> register(@RequestBody ResponseRegister responseRegister) {

		String methodName = "/register";
		LOG.info(Tag + methodName);
		int userId = responseRegister.getUserId();
		int chipId = responseRegister.getChipId();
		int location = responseRegister.getLocation();
		int chipFpId = responseRegister.getChipFpId();
		LOG.info("userId = " + userId);
		LOG.info("chipId = " + chipId);
		LOG.info("location = " + location);
		LOG.info("chipFpId = " + chipFpId);

		ResponseModel responseModel = new ResponseModel();

		// 是否成功
		int IsSuccess = 0;
		try {
			/** 参数判断 start **/

			if (!ToolUtil.isNotEmpty(userId)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE);
				responseModel.setResponseMessage( ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG+"USERID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("userId：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNum(userId)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG+"USERID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("userId：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNotEmpty(chipId)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE);
				responseModel.setResponseMessage( ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG+"CHIPID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("chipId：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNum(chipId)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
				responseModel.setResponseMessage(  ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG+"CHIPID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("chipId：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}

			if (!ToolUtil.isNotEmpty(location)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE);
				responseModel.setResponseMessage(  ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG+"LOCALTION");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("location：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}

			if (!ToolUtil.isNum(location)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
				responseModel.setResponseMessage(  ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG+"LOCALTION");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("location：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			
			if (!ToolUtil.isNotEmpty(chipFpId)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE);
				responseModel.setResponseMessage(  ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG+"CHIPFPID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("chipFpId：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}

			if (!ToolUtil.isNum(chipFpId)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
				responseModel.setResponseMessage( ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG+"CHIPFPID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("chipFpId：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}

			/** 参数判断 end **/

			/** 业务逻辑 start **/
			// 判断用户
			WayAccessoryModelUser wayAccessoryModelUser = wayAccessoryModelUserMapper
					.selectById(Long.parseLong(userId + ""));
			if (wayAccessoryModelUser == null) {
				responseModel.setResponseCode(ErrorCodeParam.NODATA_FAIL_CODE);
				responseModel.setResponseMessage("用户信息" + ErrorCodeParam.NODATA_FAIL_MSG);
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("用户信息" + ErrorCodeParam.NODATA_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (wayAccessoryModelUser.getStatus() == 0) {

				responseModel.setResponseCode(ErrorCodeParam.DATAFAILURE_FAIL_CODE);
				responseModel.setResponseMessage("用户" + ErrorCodeParam.DATAFAILURE_FAIL_MSG);
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("用户" + ErrorCodeParam.DATAFAILURE_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			// 判断设备
			WayAccessoryModelChip wayAccessoryModelChip = wayAccessoryModelChipMapper.selectById(chipId);
			if (wayAccessoryModelChip == null) {

				responseModel.setResponseCode(ErrorCodeParam.NODATA_FAIL_CODE);
				responseModel.setResponseMessage("设备" + ErrorCodeParam.NODATA_FAIL_MSG);
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("设备" + ErrorCodeParam.NODATA_FAIL_MSG);
				return ResponseEntity.ok(responseModel);

			} else {
				if (wayAccessoryModelChip.getStatus() != 1) {
					responseModel.setResponseCode(ErrorCodeParam.DATAFAILURE_FAIL_CODE);
					responseModel.setResponseMessage("设备" + ErrorCodeParam.DATAFAILURE_FAIL_MSG);
					responseModel.setObject("");
					responseModel.setSign("");
					LOG.warn("设备" + ErrorCodeParam.DATAFAILURE_FAIL_MSG);
					return ResponseEntity.ok(responseModel);
				}
			}

			// 判断用户设备指纹关系表
			WayAccessoryRelUserChip wayAccessoryRelUserChip = new WayAccessoryRelUserChip();
			List<WayAccessoryRelUserChip> wayAccessoryRelUserChipList = wayAccessoryRelUserChipMapper
					.selectByChipIdAndUserId(Integer.parseInt(wayAccessoryModelUser.getId().toString()),
							Integer.parseInt(wayAccessoryModelChip.getId().toString()));
			if (wayAccessoryRelUserChipList == null || wayAccessoryRelUserChipList.size() <= 0) {
				responseModel.setResponseCode(ErrorCodeParam.NODATA_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.NODATA_FAIL_MSG);
				responseModel.setObject("");
				responseModel.setSign("");
				return ResponseEntity.ok(responseModel);

			} else {
				// 存在关系，判断关系是否失效
				wayAccessoryRelUserChip = wayAccessoryRelUserChipList.get(0);
				if (wayAccessoryRelUserChip.getStatus() != 1) {
					responseModel.setResponseCode(ErrorCodeParam.DATAFAILURE_FAIL_CODE);
					responseModel.setResponseMessage("设备关系" + ErrorCodeParam.DATAFAILURE_FAIL_MSG);
					responseModel.setObject("");
					responseModel.setSign("");
					LOG.warn("设备关系" + ErrorCodeParam.DATAFAILURE_FAIL_MSG);
					return ResponseEntity.ok(responseModel);
				}
			}
			// 判断用设备下是否有指纹
			WayAccessoryRelUserChipFingerprint wayAccessoryRelUserChipFingerprint = new WayAccessoryRelUserChipFingerprint();
			List<WayAccessoryRelUserChipFingerprint> wayAccessoryRelUserChipFingerprintList = wayAccessoryRelUserChipFingerprintMapper
					.selectByUserIdAndChipIdAndLocation(wayAccessoryModelUser.getId(), wayAccessoryModelChip.getId(),
							location);
			if (wayAccessoryRelUserChipFingerprintList != null && wayAccessoryRelUserChipFingerprintList.size() > 0) {
				wayAccessoryRelUserChipFingerprint = wayAccessoryRelUserChipFingerprintList.get(0);
				if (wayAccessoryRelUserChipFingerprint.getStatus() != 1) {

					responseModel.setResponseCode(ErrorCodeParam.DATAFAILURE_FAIL_CODE);
					responseModel.setResponseMessage("该指纹" + ErrorCodeParam.DATAFAILURE_FAIL_MSG);
					responseModel.setObject("");
					responseModel.setSign("");
					LOG.warn("该指纹" + ErrorCodeParam.DATAFAILURE_FAIL_MSG);
					return ResponseEntity.ok(responseModel);
				} else {
					responseModel.setResponseCode(ErrorCodeParam.ADDFAILED_FAIL_CODE);
					responseModel.setResponseMessage("该指纹" + ErrorCodeParam.ADDFAILED_FAIL_MSG);
					responseModel.setObject("");
					responseModel.setSign("");
					LOG.warn("该指纹" + ErrorCodeParam.ADDFAILED_FAIL_MSG);
					return ResponseEntity.ok(responseModel);
				}
			}

			// 添加用户设备指纹关系表
			IsSuccess = wayAccessoryRelUserChipFingerprintMapper.insert(ModelUtil
					.addWayAccessoryRelUserChipFingerprint(wayAccessoryModelChip, wayAccessoryModelUser, location,chipFpId));

			// 添加日志
			WayAccessoryLogFingerprintCapture wayAccessoryLogFingerprintCapture = new WayAccessoryLogFingerprintCapture();
			List<WayAccessoryLogFingerprintCapture> wayAccessoryLogFingerprintCaptureList = wayAccessoryLogFingerprintCaptureMapper
					.selectByChipId(wayAccessoryModelChip.getId());

			boolean isAdd = false;
			if (wayAccessoryLogFingerprintCaptureList == null || wayAccessoryLogFingerprintCaptureList.size() <= 0) {
				wayAccessoryLogFingerprintCapture = new WayAccessoryLogFingerprintCapture();
				wayAccessoryLogFingerprintCapture.setCreateTime(TimeUtil.getTimestamp());
				wayAccessoryLogFingerprintCapture.setChipId(wayAccessoryModelChip.getId());
				wayAccessoryLogFingerprintCapture.setChipNumber(wayAccessoryModelChip.getChipNumber());
				wayAccessoryLogFingerprintCapture.setRegFailTime(0);
				wayAccessoryLogFingerprintCapture.setRegSuccTime(0);
				isAdd = true;
			} else {
				wayAccessoryLogFingerprintCapture = wayAccessoryLogFingerprintCaptureList.get(0);
			}
			wayAccessoryLogFingerprintCapture.setUpdateTime(TimeUtil.getTimestamp());
			if (IsSuccess == 1) {
				wayAccessoryLogFingerprintCapture
						.setRegSuccTime(wayAccessoryLogFingerprintCapture.getRegSuccTime() + 1);
				if (isAdd) {
					// 添加
					wayAccessoryLogFingerprintCaptureMapper.insert(wayAccessoryLogFingerprintCapture);
				} else {
					// 修改
					wayAccessoryLogFingerprintCaptureMapper.updateAllColumnById(wayAccessoryLogFingerprintCapture);
				}
				responseModel.setResponseCode(ErrorCodeParam.SUCCESS_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.SUCCESS_MSG);
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.info("添加成功");
				return ResponseEntity.ok(responseModel);
			} else {
				// 注册失败
				wayAccessoryLogFingerprintCapture
						.setRegSuccTime(wayAccessoryLogFingerprintCapture.getRegFailTime() + 1);

				if (isAdd) {
					wayAccessoryLogFingerprintCaptureMapper.insert(wayAccessoryLogFingerprintCapture);
				} else {
					wayAccessoryLogFingerprintCaptureMapper.updateAllColumnById(wayAccessoryLogFingerprintCapture);
				}
				responseModel.setResponseCode(ErrorCodeParam.ADDFAILED_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.ADDFAILED_FAIL_MSG);
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn(ErrorCodeParam.ADDFAILED_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			/** 业务逻辑 end **/
		} catch (Exception e) {
			responseModel.setResponseCode(ErrorCodeParam.NODATA_FAIL_CODE);
			responseModel.setResponseMessage(ErrorCodeParam.SERVERBUSY_FAIL_MSG);
			responseModel.setObject("");
			responseModel.setSign("");
			LOG.error("系统异常");
			return ResponseEntity.ok(responseModel);
		}
	}

	/**
	 * TODO 指纹删除接口
	 * 
	 * @param fpId
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> delete(@RequestBody ResponceDelete responceDelete) {

		String methodName = "/delete";
		LOG.info(Tag + methodName);
		int fpId = responceDelete.getFpId();
		LOG.info("fpId = " + fpId);

		ResponseModel responseModel = new ResponseModel();

		try {
			/** 参数判断 start **/

			if (!ToolUtil.isNotEmpty(fpId)) {

				responseModel.setResponseCode(ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				responseModel.setResponseMessage( ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG+"USERID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("userId：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNum(fpId)) {

				responseModel.setResponseCode(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
				responseModel.setResponseMessage( ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG+"TYPE");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("userId：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}

			/** 参数判断 end **/

			/** 业务逻辑 start **/
			WayAccessoryRelUserChipFingerprint wayAccessoryRelUserChipFingerprint = wayAccessoryRelUserChipFingerprintMapper
					.selectById(fpId);
			if (wayAccessoryRelUserChipFingerprint == null) {
				responseModel.setResponseCode(ErrorCodeParam.NODATA_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.NODATA_FAIL_MSG);
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn(ErrorCodeParam.NODATA_FAIL_MSG);
				return ResponseEntity.ok(responseModel);

			}

			//删除指纹
			int isDelete = wayAccessoryRelUserChipFingerprintMapper.deleteById(Long.parseLong(fpId + ""));
			if (isDelete != 1) {
				responseModel.setResponseCode(ErrorCodeParam.MODIFYFAILED_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.MODIFYFAILED_FAIL_MSG);
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn(ErrorCodeParam.MODIFYFAILED_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			
			//根据指纹查询直达
			List<WayAccessoryRelUserChipQuickLaunch> wayList = 
					wayAccessoryRelUserChipQuickLaunchMapper.selectWayAccessoryRelUserChipQuickLaunchMapperByFingerprintInfoId(fpId+"");
			if(wayList!=null && wayList.size()>0){
				for(WayAccessoryRelUserChipQuickLaunch way : wayList){
					way.deleteById();
				}
			}

			/** 业务逻辑 end **/

			responseModel.setResponseCode(ErrorCodeParam.SUCCESS_CODE);
			responseModel.setResponseMessage(ErrorCodeParam.SUCCESS_MSG);
			responseModel.setObject("");
			responseModel.setSign("");
			LOG.info("删除成功");
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
	 * TODO 获取用户指纹及绑定的直达数据
	 * 
	 * @param userId
	 * @param chipId
	 * @return
	 */
	@RequestMapping(value = "getFpAndBindInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> getFpAndBindInfo(@RequestBody ResponseGetFp responseGetFp) {

		String methodName = "/getFpAndBindInfo";
		LOG.info(Tag + methodName);
		int userId = responseGetFp.getUserId();
		int chipId = responseGetFp.getChipId();
		LOG.info("userId = " + userId);
		LOG.info("chipId = " + chipId);

		ResponseModel responseModel = new ResponseModel();

		try {
			/** 参数判断 start **/

			if (!ToolUtil.isNotEmpty(userId)) {

				responseModel.setResponseCode(ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				responseModel.setResponseMessage(ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG+"USERID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("userId：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNum(userId)) {

				responseModel.setResponseCode(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
				responseModel.setResponseMessage(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG+"USERID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("userId：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}

			if (!ToolUtil.isNotEmpty(chipId)) {

				responseModel.setResponseCode(ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				responseModel.setResponseMessage(ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG+"CHIPID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("chipId：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			if (!ToolUtil.isNum(chipId)) {
				responseModel.setResponseCode(ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
				responseModel.setResponseMessage(  ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG+"CHIPID");
				responseModel.setObject("");
				responseModel.setSign("");
				LOG.warn("chipId：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return ResponseEntity.ok(responseModel);
			}
			/** 参数判断 end **/

			/** 业务逻辑 start **/

			ResponseFP responseFP = new ResponseFP();
			responseFP.setFingerManagerModelList(new ArrayList<FingerManagerModel>());
			
			// 根据用户id和设备id查询所有指纹信息
			List<WayAccessoryRelUserChipFingerprint> wayAccessoryRelUserChipFingerprintList = wayAccessoryRelUserChipFingerprintMapper
					.selectWayAccessoryRelUserChipFingerprintByChipIdUserId(Integer.parseInt(userId + ""),
							Integer.parseInt(chipId + ""));
			if (wayAccessoryRelUserChipFingerprintList != null && wayAccessoryRelUserChipFingerprintList.size() > 0) {

				/* 用户设备关系表集合 */
				List<FingerManagerModel> fingerManagerModelList = new ArrayList<FingerManagerModel>();

				/* 用户设备指纹关系模型 */
				FingerManagerModel fingerManagerModel = new FingerManagerModel();

				for (WayAccessoryRelUserChipFingerprint wayAccessoryRelUserChipFingerprint : wayAccessoryRelUserChipFingerprintList) {
					fingerManagerModel = new FingerManagerModel();
					List<WayAccessoryRelUserChipQuickLaunch> list = wayAccessoryRelUserChipQuickLaunchMapper
							.selectWayAccessorByChipIdAnduserId(userId, chipId);
					fingerManagerModel = ModelReturnUtil
							.setWayAccessoryRelUserChipFingerprint(wayAccessoryRelUserChipFingerprint, list);
					fingerManagerModelList.add(fingerManagerModel);
				}
				/** 业务逻辑 end **/
				
				responseFP.setFingerManagerModelList(fingerManagerModelList);
			}
			
			responseModel.setObject(responseFP);

			String json = JSON.toJSONString(responseFP);

			String token = HttpKit.getRequest().getHeader(jwtProperties.getHeader()).substring(7);
			String md5KeyFromToken = jwtTokenUtil.getMd5KeyFromToken(token);
			// md5签名
			String encrypt = MD5Util.encrypt(json + md5KeyFromToken);
			responseModel.setSign(encrypt);
			responseModel.setResponseCode(ErrorCodeParam.SUCCESS_CODE);
			responseModel.setResponseMessage(ErrorCodeParam.SUCCESS_MSG);
			responseModel.setObject(responseFP);
			responseModel.setSign(encrypt);
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
