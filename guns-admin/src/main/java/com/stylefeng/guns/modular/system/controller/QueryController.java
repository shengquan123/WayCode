package com.stylefeng.guns.modular.system.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.util.RegexUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.restapi.commans.params.ErrorCodeParam;
import com.stylefeng.guns.modular.restapi.commans.params.Params;
import com.stylefeng.guns.modular.restapi.commans.utils.ModelUtil;
import com.stylefeng.guns.modular.restapi.commans.utils.TimeUtil;
import com.stylefeng.guns.modular.restapi.controller.AppBindController;
import com.stylefeng.guns.modular.restapi.model.AccessoryChipMode;
import com.stylefeng.guns.modular.restapi.model.FingerManagerModel;
import com.stylefeng.guns.modular.restapi.model.HistoryNewUser;
import com.stylefeng.guns.modular.restapi.model.QuickLaunchModel;
import com.stylefeng.guns.modular.restapi.model.UserModelInfo;
import com.stylefeng.guns.modular.system.dao.WayAccessoryLogMailCheckMapper;
import com.stylefeng.guns.modular.system.dao.WayAccessoryLogMobileCheckMapper;
import com.stylefeng.guns.modular.system.dao.WayAccessoryLogUserActionMapper;
import com.stylefeng.guns.modular.system.dao.WayAccessoryModelUserMapper;
import com.stylefeng.guns.modular.system.dao.WayAccessoryRelUserChipFingerprintMapper;
import com.stylefeng.guns.modular.system.dao.WayAccessoryRelUserChipMapper;
import com.stylefeng.guns.modular.system.dao.WayAccessoryRelUserChipQuickLaunchMapper;
import com.stylefeng.guns.modular.system.dao.WayAccessoryStatisticalNewUserMapper;
import com.stylefeng.guns.modular.system.model.WayAccessoryModelUser;
import com.stylefeng.guns.modular.system.model.WayAccessoryRelUserChipFingerprint;
import com.stylefeng.guns.modular.system.model.WayAccessoryRelUserChipQuickLaunch;
import com.stylefeng.guns.modular.system.model.WayAccessoryStatisticalNewUser;
import com.stylefeng.guns.modular.system.warpper.MenuWarpper;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/wayapi/v1/accessory/query")
public class QueryController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(AppBindController.class);
	private static String Tag = "QueryController";

	@Autowired
	private WayAccessoryStatisticalNewUserMapper wayAccessoryStatisticalNewUserMapper;

	@Autowired
	private WayAccessoryModelUserMapper wayAccessoryModelUserMapper;

	@Autowired
	private WayAccessoryRelUserChipFingerprintMapper wayAccessoryRelUserChipFingerprintMapper;

	@Autowired
	private WayAccessoryRelUserChipQuickLaunchMapper wayAccessoryRelUserChipQuickLaunchMapper;

	@Autowired
	private WayAccessoryLogMobileCheckMapper wayAccessoryLogMobileCheckMapper;
	@Autowired
	private WayAccessoryLogMailCheckMapper wayAccessoryLogMailCheckMapper;

	@Autowired
	private WayAccessoryLogUserActionMapper wayAccessoryLogUserActionMapper;

	@Autowired
	private WayAccessoryRelUserChipMapper wayAccessoryRelUserChipMapper;
	private long[] historyNewUsers = new long[2];

	/**
	 * TODO 查询历史所有新增用户数量的集合
	 * 
	 * @return
	 */
	@ApiOperation("查询历史所有新增用户数量的集合")
	@RequestMapping(value = "newAllUserCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> newAllUserCount() {

		String methodName = "/newAllUserCount";
		LOG.info(Tag + methodName);

		Map<String, String> map = new HashMap<String, String>();
		try {
			// 每天新增人数：数据统计->新增用户->新增用户
			int dayCount = wayAccessoryModelUserMapper.selectDayCount(TimeUtil.getTodayDateString());

			List<Object> historyNewUserList = new ArrayList<Object>();
			List<WayAccessoryStatisticalNewUser> allAmountList = wayAccessoryStatisticalNewUserMapper
					.selectHAllDayCount();

			if (allAmountList != null && allAmountList.size() > 0) {
				for (WayAccessoryStatisticalNewUser object : allAmountList) {
					historyNewUsers = new long[2];
					historyNewUsers[0] = getTimeStamp(object.getCheckDate());
					historyNewUsers[1] = Integer.valueOf(object.getCountNumber().toString());
					historyNewUserList.add(historyNewUsers);
				}
			}
			historyNewUsers[0] = getTimeStamp(TimeUtil.getTodayDateString());
			historyNewUsers[1] = dayCount;
			historyNewUserList.add(historyNewUsers);

			map.put(Params.RESPONSESTATUS, Params.SUCCESS);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SUCCESS_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SUCCESS_MSG);
			map.put(Params.RESPONSEBODY, JSON.toJSONString(historyNewUserList));
			LOG.info("查询成功");
			return map;
		} catch (Exception e) {
			map.put(Params.RESPONSESTATUS, Params.FAIL);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SERVERBUSY_FAIL_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SERVERBUSY_FAIL_MSG);
			map.put(Params.RESPONSEBODY, "");
			LOG.error("系统异常" + map.get(Params.RESPONSECODE) + e);
			e.printStackTrace();
			return map;
		}
	}

	/**
	 * TODO 查询历史新增用户数量
	 * 
	 * @param stratTime 2017-01-01
	 * @param endTime 2017-01-01
	 * @return
	 */
	@ApiOperation("查询历史新增用户数量")
	@RequestMapping(value = "newUserCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> newUserCount(@RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime) {

		String methodName = "/newUserCount";
		LOG.info(Tag + methodName);
		LOG.info("stratTime = " + startTime);
		LOG.info("endTime = " + endTime);

		Map<String, String> map = new HashMap<String, String>();
		try {
			if (!ToolUtil.isNotEmpty(startTime)) {
				map.put(Params.RESPONSESTATUS, Params.FAIL);
				map.put(Params.RESPONSECODE, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE);
				map.put(Params.RESPONSEMESSAGE, "userId" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				map.put(Params.RESPONSEBODY, "");
				LOG.warn("stratTime" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG + map.get(Params.RESPONSECODE));
				return map;
			}
			if (!ToolUtil.isNotEmpty(endTime)) {
				map.put(Params.RESPONSESTATUS, Params.FAIL);
				map.put(Params.RESPONSECODE, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE);
				map.put(Params.RESPONSEMESSAGE, "userId" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				map.put(Params.RESPONSEBODY, "");
				LOG.warn("endTime" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG + map.get(Params.RESPONSECODE));
				return map;
			}
			List<HistoryNewUser> historyNewUserList = new ArrayList<HistoryNewUser>();
			List<Map<String, Object>> allAmountList = wayAccessoryStatisticalNewUserMapper.selectHDayCount(startTime,
					endTime);
			if (ToolUtil.isNotEmpty(allAmountList)) {
				for (Map<String, Object> object : allAmountList) {

					HistoryNewUser historyNewUser = new HistoryNewUser();
					historyNewUser.setAmount(Integer.valueOf(object.get("count").toString()));
					historyNewUser.setCheckDte(object.get("check_date").toString());
					historyNewUserList.add(historyNewUser);
				}
			}
			map.put(Params.RESPONSESTATUS, Params.SUCCESS);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SUCCESS_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SUCCESS_MSG);
			map.put(Params.RESPONSEBODY, JSON.toJSONString(historyNewUserList));
			LOG.info("查询成功");
			return map;
		} catch (Exception e) {
			map.put(Params.RESPONSESTATUS, Params.FAIL);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SERVERBUSY_FAIL_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SERVERBUSY_FAIL_MSG);
			map.put(Params.RESPONSEBODY, "");
			LOG.error("系统异常" + map.get(Params.RESPONSECODE) + e);
			e.printStackTrace();
			return map;
		}
	}

	/**
	 * TODO 查询单个用户明细
	 * 
	 * @param phoneOrMail
	 * @return
	 */
	@ApiOperation("查询单个用户明细")
	@RequestMapping(value = "userInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> userInfo(@RequestParam("phone"/* "phoneOrMail" */) String phoneOrMail) {

		String methodName = "/userInfo";
		LOG.info(Tag + methodName);
		LOG.info("userId = " + phoneOrMail);

		Map<String, String> map = new HashMap<String, String>();
		try {
			if (!ToolUtil.isNotEmpty(phoneOrMail)) {
				map.put(Params.RESPONSESTATUS, Params.FAIL);
				map.put(Params.RESPONSECODE, ErrorCodeParam.PARAMETERISEMPTY_FAIL_CODE);
				map.put(Params.RESPONSEMESSAGE, "userId" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				map.put(Params.RESPONSEBODY, "");
				LOG.warn("phone" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG + map.get(Params.RESPONSECODE));
				return map;
			}
			List<WayAccessoryModelUser> wayAccessoryModelUserList = null;
			if (phoneOrMail.contains("@") && RegexUtil.checkMail(phoneOrMail)) { // 邮箱
				wayAccessoryModelUserList = wayAccessoryModelUserMapper.selectWayAccessoryModelUserByMail(phoneOrMail,
						1);
			} else { // 手机
				wayAccessoryModelUserList = wayAccessoryModelUserMapper.selectWayAccessoryModelUserByPhone(phoneOrMail,
						1);
			}
			if (wayAccessoryModelUserList == null) {
				map.put(Params.RESPONSESTATUS, Params.FAIL);
				map.put(Params.RESPONSECODE, ErrorCodeParam.NODATA_FAIL_CODE);
				map.put(Params.RESPONSEMESSAGE, "用户信息" + ErrorCodeParam.NODATA_FAIL_MSG);
				map.put(Params.RESPONSEBODY, "");
				LOG.warn("用户信息" + ErrorCodeParam.NODATA_FAIL_MSG + map.get(Params.RESPONSECODE));
				return map;
			}
			WayAccessoryModelUser wayAccessoryModelUser = wayAccessoryModelUserList.get(0);

			// 用户注册的指纹数量
			List<Integer> fingerprintIdList = wayAccessoryRelUserChipFingerprintMapper
					.selectAllFingerprint(wayAccessoryModelUser.getId());
			int fingerprintAmount = fingerprintIdList.size();
			int quickLaunchAmount = 0;
			for (Integer fingerprintId : fingerprintIdList) {
				quickLaunchAmount = quickLaunchAmount + wayAccessoryRelUserChipQuickLaunchMapper
						.selectWayAccessoryRelUserChipQuickLaunchMapperByFingerprintInfoId(fingerprintId.toString())
						.size();
			}
			// 短信发送数量
			int messageSendAmount = wayAccessoryLogMobileCheckMapper.selectUserSendAmount(phoneOrMail);
			// 邮件发送数量
			int mailSendAmount = wayAccessoryLogMailCheckMapper.selectUserSendAmount(phoneOrMail);
			int encryptAmount = wayAccessoryLogUserActionMapper.selectEncryptAmount(wayAccessoryModelUser.getId());
			UserModelInfo userModelInfo = new UserModelInfo();
			userModelInfo = ModelUtil.setUserModelInfo(wayAccessoryModelUser, fingerprintAmount, encryptAmount,
					quickLaunchAmount, messageSendAmount, mailSendAmount, phoneOrMail);

			map.put(Params.RESPONSESTATUS, Params.SUCCESS);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SUCCESS_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SUCCESS_MSG);
			map.put(Params.RESPONSEBODY, JSON.toJSONString(userModelInfo));
			LOG.info("查询成功");
			return map;
		} catch (Exception e) {
			map.put(Params.RESPONSESTATUS, Params.FAIL);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SERVERBUSY_FAIL_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SERVERBUSY_FAIL_MSG);
			map.put(Params.RESPONSEBODY, "");
			LOG.error("系统异常" + map.get(Params.RESPONSECODE) + e);
			e.printStackTrace();
			return map;
		}
	}

	/**
	 * TODO 查询用户列表
	 * 
	 * @param key 关键字
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	@ApiOperation("查询用户列表")
	@RequestMapping(value = "userList", method = RequestMethod.POST)
	@ResponseBody
	public Object userList(@RequestParam("key") String key, @RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime) {

		String methodName = "/userList";
		LOG.info(Tag + methodName);
		LOG.info("key = " + key);
		LOG.info("startTime = " + startTime);
		LOG.info("endTime = " + endTime);
		if (key.equals(",")) {
			key = "";
		}
		if (startTime == null || startTime.equals("") || startTime.equals(",")) {
			startTime = "2000-01-01";
		}
		if (endTime == null || endTime.equals("") || endTime.equals(",")) {
			endTime = TimeUtil.getTodayDateString();
		}
		key = key.replaceAll(",", "");
		startTime = startTime.replaceAll(",", "");
		endTime = endTime.replaceAll(",", "");
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

			// 没有关键字
			if (!ToolUtil.isNotEmpty(key)) {
				// 起始条数
				List<WayAccessoryModelUser> wayAccessoryModelUserList = wayAccessoryModelUserMapper
						.selectNewUser(startTime, endTime);
				if (wayAccessoryModelUserList == null) {

					LOG.warn("未查询到" + ErrorCodeParam.NODATA_FAIL_MSG);
					return "未查询到用户";
				}
				for (WayAccessoryModelUser wayAccessoryModelUser : wayAccessoryModelUserList) {
					// 用户注册的指纹数量
					int fingerprintAmount = wayAccessoryRelUserChipFingerprintMapper
							.selectAllFingerprint(wayAccessoryModelUser.getId()).size();
					List<Integer> fingerprintIdList = wayAccessoryRelUserChipFingerprintMapper
							.selectAllFingerprint(wayAccessoryModelUser.getId());
					int quickLaunchAmount = 0;
					for (Integer fingerprintId : fingerprintIdList) {
						quickLaunchAmount = quickLaunchAmount + wayAccessoryRelUserChipQuickLaunchMapper
								.selectWayAccessoryRelUserChipQuickLaunchMapperByFingerprintInfoId(
										fingerprintId.toString())
								.size();
					}
					// 短信发送数量
					int messageSendAmount = wayAccessoryLogMobileCheckMapper
							.selectUserSendAmount(wayAccessoryModelUser.getPhoneNo());
					// 邮件发送数量
					int mailSendAmount = wayAccessoryLogMailCheckMapper
							.selectUserSendAmount(wayAccessoryModelUser.getEmail());
					int encryptAmount = wayAccessoryModelUser.getNumber();
					list.add(ModelUtil.setModelUser(wayAccessoryModelUser, fingerprintAmount, encryptAmount,
							quickLaunchAmount, messageSendAmount, mailSendAmount, wayAccessoryModelUser.getPhoneNo()));
				}
				LOG.info("查询成功");
				return super.warpObject(new MenuWarpper(list));
			} else {
				List<WayAccessoryModelUser> wayAccessoryModelUserList = wayAccessoryModelUserMapper
						.selectKeyNewUser(key, startTime, endTime);
				if (wayAccessoryModelUserList == null) {
					return "未查询到用户";
				}

				for (WayAccessoryModelUser wayAccessoryModelUser : wayAccessoryModelUserList) {
					// 用户注册的指纹数量
					int fingerprintAmount = wayAccessoryRelUserChipFingerprintMapper
							.selectAllFingerprint(wayAccessoryModelUser.getId()).size();
					List<Integer> fingerprintIdList = wayAccessoryRelUserChipFingerprintMapper
							.selectAllFingerprint(wayAccessoryModelUser.getId());
					int quickLaunchAmount = 0;
					for (Integer fingerprintId : fingerprintIdList) {
						quickLaunchAmount = quickLaunchAmount + wayAccessoryRelUserChipQuickLaunchMapper
								.selectWayAccessoryRelUserChipQuickLaunchMapperByFingerprintInfoId(
										fingerprintId.toString())
								.size();
					}
					// 短信发送数量
					int messageSendAmount = wayAccessoryLogMobileCheckMapper
							.selectUserSendAmount(wayAccessoryModelUser.getPhoneNo());
					// 邮件发送数量
					int mailSendAmount = wayAccessoryLogMailCheckMapper
							.selectUserSendAmount(wayAccessoryModelUser.getEmail());
					int encryptAmount = wayAccessoryLogUserActionMapper
							.selectEncryptAmount(wayAccessoryModelUser.getId());
					list.add(ModelUtil.setModelUser(wayAccessoryModelUser, fingerprintAmount, encryptAmount,
							quickLaunchAmount, messageSendAmount, mailSendAmount, wayAccessoryModelUser.getPhoneNo()));
				}
				LOG.info("查询成功");
				return super.warpObject(new MenuWarpper(list));
			}
		} catch (Exception e) {

			LOG.error("系统异常" + e);
			e.printStackTrace();
			return "系统异常";
		}
	}

	/**
	 * TODO 查询用户设备和指纹位置
	 * 
	 * @param userId
	 * @return
	 */
	@ApiOperation("查询用户设备和指纹位置")
	@RequestMapping(value = "userChipList", method = RequestMethod.POST)
	@ResponseBody
	public String userChipList(@RequestParam("userId") String userId) {

		String methodName = "/userChipList";
		LOG.info(Tag + methodName);
		LOG.info("userId = " + userId);

		try {
			if (!ToolUtil.isNotEmpty(userId)) {

				LOG.warn("userId" + ErrorCodeParam.PARAMETERISEMPTY_FAIL_MSG);
				return "userId为空";
			}
			if (!ToolUtil.isNum(userId)) {
				return "userId不是全是数字";
			}
			List<AccessoryChipMode> accessoryChipModeList = new ArrayList<AccessoryChipMode>();
			List<Map<String, Object>> chipList = wayAccessoryRelUserChipMapper.selectByUserId(Integer.valueOf(userId));

			List<FingerManagerModel> FingerManagerModelList = new ArrayList<FingerManagerModel>();

			QuickLaunchModel quickLaunchModel = new QuickLaunchModel();

			List<WayAccessoryRelUserChipQuickLaunch> wayAccessoryRelUserChipQuickLaunchList = new ArrayList<WayAccessoryRelUserChipQuickLaunch>();
			for (Map<String, Object> object : chipList) {
				AccessoryChipMode accessoryChipMode = new AccessoryChipMode();
				accessoryChipMode.setChipId(Integer.valueOf(object.get("id").toString()));
				accessoryChipMode.setChipNumber(object.get("chip_number").toString());
				accessoryChipMode.setChipBrand(object.get("chip_brand").toString());

				FingerManagerModelList = new ArrayList<FingerManagerModel>();
				// 根据用户id和设备id查询指纹信息
				List<WayAccessoryRelUserChipFingerprint> wayAccessoryRelUserChipFingerprintList = wayAccessoryRelUserChipFingerprintMapper
						.selectWayAccessoryRelUserChipFingerprintByChipIdUserId(Integer.parseInt(userId),
								Integer.parseInt(object.get("id").toString()));
				if (wayAccessoryRelUserChipFingerprintList != null
						&& wayAccessoryRelUserChipFingerprintList.size() > 0) {
					for (WayAccessoryRelUserChipFingerprint wayAccessoryRelUserChipFingerprint : wayAccessoryRelUserChipFingerprintList) {
						FingerManagerModel fingerManagerModel = new FingerManagerModel();
						fingerManagerModel.setLocation(wayAccessoryRelUserChipFingerprint.getFpLocation() + "");
						fingerManagerModel
								.setAddTime(TimeUtil.dateToString(wayAccessoryRelUserChipFingerprint.getCreateTime()));

						wayAccessoryRelUserChipQuickLaunchList = wayAccessoryRelUserChipQuickLaunchMapper
								.selectWayAccessoryRelUserChipQuickLaunchMapperByFingerprintInfoId(
										wayAccessoryRelUserChipFingerprint.getId().toString());

						if (wayAccessoryRelUserChipQuickLaunchList != null
								&& wayAccessoryRelUserChipQuickLaunchList.size() > 0
								&& wayAccessoryRelUserChipQuickLaunchList.get(0) != null) {

							quickLaunchModel = new QuickLaunchModel();
							quickLaunchModel.setUrl(wayAccessoryRelUserChipQuickLaunchList.get(0).getAppPath());

							fingerManagerModel.setQuickLaunchModel(quickLaunchModel);

						}

						FingerManagerModelList.add(fingerManagerModel);
					}
				}
				accessoryChipMode.setFingerManagerModelList(FingerManagerModelList);

				accessoryChipModeList.add(accessoryChipMode);
			}

			return JSON.toJSONString(accessoryChipModeList);

		} catch (Exception e) {

			return "系统异常";
		}
	}

	/**
	 * TODO 查询发送短信数量
	 *
	 * @return
	 */
	@ApiOperation("查询发送短信数量")
	@RequestMapping(value = "messageSendInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> messageSendInfo() {
		String methodName = "/messageSendInfo";
		LOG.info(Tag + methodName);

		Map<String, String> map = new HashMap<String, String>();
		try {
			// 短信发送数量
			int messageSendAmount = wayAccessoryLogMobileCheckMapper.selectAllUserSendAmount();

			map.put(Params.RESPONSESTATUS, Params.SUCCESS);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SUCCESS_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SUCCESS_MSG);
			map.put(Params.RESPONSEBODY, messageSendAmount + "");
			LOG.info("查询成功");
			return map;
		} catch (Exception e) {
			map.put(Params.RESPONSESTATUS, Params.FAIL);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SERVERBUSY_FAIL_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SERVERBUSY_FAIL_MSG);
			map.put(Params.RESPONSEBODY, "");
			LOG.error("系统异常" + map.get(Params.RESPONSECODE) + e);
			//e.printStackTrace();
			return map;
		}
	}

	/**
	 * TODO 查询发送邮件数量
	 *
	 * @return
	 */
	@ApiOperation("查询发送邮件数量")
	@RequestMapping(value = "mailSendInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> mailSendInfo() {
		String methodName = "/mailSendInfo";
		LOG.info(Tag + methodName);

		Map<String, String> map = new HashMap<String, String>();
		try {
			// 邮件发送数量
			int messageSendAmount = wayAccessoryLogMailCheckMapper.selectAllUserSendAmount();

			map.put(Params.RESPONSESTATUS, Params.SUCCESS);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SUCCESS_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SUCCESS_MSG);
			map.put(Params.RESPONSEBODY, messageSendAmount + "");
			LOG.info("查询成功");
			return map;
		} catch (Exception e) {
			map.put(Params.RESPONSESTATUS, Params.FAIL);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SERVERBUSY_FAIL_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SERVERBUSY_FAIL_MSG);
			map.put(Params.RESPONSEBODY, "");
			LOG.error("系统异常" + map.get(Params.RESPONSECODE) + e);
			return map;
		}
	}

	/**
	 * 日期转时间戳
	 * 
	 * @param time
	 * @return
	 * @throws java.text.ParseException
	 */
	private static long getTimeStamp(String time) throws java.text.ParseException {
		time = time + " 00:00:00";
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
		long unixTimestamp = date.getTime();
		return unixTimestamp;

	}
}
