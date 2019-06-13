package com.stylefeng.guns.modular.restapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.restapi.commans.params.ErrorCodeParam;
import com.stylefeng.guns.modular.restapi.commans.params.Params;
import com.stylefeng.guns.modular.restapi.commans.utils.ModelReturnUtil;
import com.stylefeng.guns.modular.restapi.model.FingerManagerModel;
import com.stylefeng.guns.modular.system.dao.WayAccessoryRelUserChipFingerprintMapper;
import com.stylefeng.guns.modular.system.dao.WayAccessoryRelUserChipQuickLaunchMapper;
import com.stylefeng.guns.modular.system.model.WayAccessoryRelUserChipFingerprint;
import com.stylefeng.guns.modular.system.model.WayAccessoryRelUserChipQuickLaunch;

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


	/**
	 * TODO 应用直达的获取
	 * 
	 * @param userId
	 * @param chipId
	 * @return
	 */
	@RequestMapping(value = "getBindInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> getBindInfo(@RequestParam("userId") Integer userId,
			@RequestParam("chipId") Integer chipId) {

		String methodName = "/getBindInfo";
		LOG.info(Tag + methodName);
		LOG.info("userId = " + userId);
		LOG.info("chipId = " + chipId);

		Map<String, String> map = new HashMap<String, String>();

		try {
			/** 参数判断 start **/

			if (!ToolUtil.isNotEmpty(userId)) {
				
				map.put(Params.RESPONSECODE, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE);
				map.put(Params.RESPONSEMESSAGE, "userId" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				map.put(Params.RESPONSEBODY, "");
				LOG.warn("userId" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG + map.get(Params.RESPONSECODE));
				return map;
			}
			if (!ToolUtil.isNum(userId)) {
				
				map.put(Params.RESPONSECODE, ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
				map.put(Params.RESPONSEMESSAGE, "userId" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
				map.put(Params.RESPONSEBODY, "");
				LOG.warn("userId" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG + map.get(Params.RESPONSECODE));
				return map;
			}

			if (!ToolUtil.isNotEmpty(chipId)) {
				
				map.put(Params.RESPONSECODE, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE);
				map.put(Params.RESPONSEMESSAGE, "chipId" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				map.put(Params.RESPONSEBODY, "");
				LOG.warn("chipId：" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG + map.get(Params.RESPONSECODE));
				return map;
			}
			if (!ToolUtil.isNum(chipId)) {
				
				map.put(Params.RESPONSECODE, ErrorCodeParam.PARAMETERTYPEERROR_FAIL_CODE);
				map.put(Params.RESPONSEMESSAGE, "chipId" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG);
				map.put(Params.RESPONSEBODY, "");
				LOG.warn("chipId：" + ErrorCodeParam.PARAMETERTYPEERROR_FAIL_MSG + map.get(Params.RESPONSECODE));
				return map;
			}
			/** 参数判断 end **/

			/** 业务逻辑 start **/

			// 根据用户id和设备id查询指纹信息
			List<WayAccessoryRelUserChipFingerprint> wayAccessoryRelUserChipFingerprintList = wayAccessoryRelUserChipFingerprintMapper
					.selectWayAccessoryRelUserChipFingerprintByChipIdUserId(userId, chipId);

			if (wayAccessoryRelUserChipFingerprintList == null || wayAccessoryRelUserChipFingerprintList.size() <= 0) {
				
				map.put(Params.RESPONSECODE, ErrorCodeParam.NODATA_FAIL_CODE);
				map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.NODATA_FAIL_MSG);
				map.put(Params.RESPONSEBODY, "");
				LOG.warn(ErrorCodeParam.NODATA_FAIL_MSG + map.get(Params.RESPONSECODE));
				return map;
			}

			/* 用户设备关系表集合 */
			List<FingerManagerModel> fingerManagerModelList = new ArrayList<FingerManagerModel>();

			/* 用户设备指纹关系模型 */
			FingerManagerModel fingerManagerModel = new FingerManagerModel();

			/* 用户设备直达应用关系模型 */
			List<WayAccessoryRelUserChipQuickLaunch> wayAccessoryRelUserChipQuickLaunchList = new ArrayList<WayAccessoryRelUserChipQuickLaunch>();
			wayAccessoryRelUserChipQuickLaunchList = wayAccessoryRelUserChipQuickLaunchMapper
					.selectWayAccessorByChipIdAnduserId(userId, chipId);

			for (WayAccessoryRelUserChipFingerprint wayAccessoryRelUserChipFingerprint : wayAccessoryRelUserChipFingerprintList) {
				fingerManagerModel = new FingerManagerModel();
				fingerManagerModel = ModelReturnUtil.setWayAccessoryRelUserChipFingerprint(
						wayAccessoryRelUserChipFingerprint, wayAccessoryRelUserChipQuickLaunchList);
				fingerManagerModelList.add(fingerManagerModel);
			}
			/** 业务逻辑 end **/

			map.put(Params.RESPONSESTATUS, Params.SUCCESS);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SUCCESS_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SUCCESS_MSG);
			
			map.put(Params.RESPONSEBODY, JSON.toJSONString(fingerManagerModelList));
			LOG.info("查询成功");
			return map;
		} catch (Exception e) {
			
			map.put(Params.RESPONSECODE, ErrorCodeParam.SERVERBUSY_FAIL_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SERVERBUSY_FAIL_MSG);
			map.put(Params.RESPONSEBODY, "");
			LOG.error("系统异常" + map.get(Params.RESPONSECODE) + e);
			e.printStackTrace();
			return map;
		}
	}
}