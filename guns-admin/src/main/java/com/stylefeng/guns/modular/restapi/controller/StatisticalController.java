package com.stylefeng.guns.modular.restapi.controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.restapi.commans.params.ErrorCodeParam;
import com.stylefeng.guns.modular.restapi.commans.params.Params;
import com.stylefeng.guns.modular.restapi.commans.utils.TimeUtil;
import com.stylefeng.guns.modular.restapi.model.ActiveUser;
import com.stylefeng.guns.modular.restapi.model.AvgFingerprint;
import com.stylefeng.guns.modular.restapi.model.LaunchAppInfo;
import com.stylefeng.guns.modular.restapi.model.NewUserCount;
import com.stylefeng.guns.modular.restapi.model.TypeUserArea;
import com.stylefeng.guns.modular.restapi.model.TypeUserFingerprintInfo;
import com.stylefeng.guns.modular.restapi.model.TypeVersion;
import com.stylefeng.guns.modular.restapi.model.UserAction;
import com.stylefeng.guns.modular.system.dao.WayAccessoryLogQuickLaunchMapper;
import com.stylefeng.guns.modular.system.dao.WayAccessoryLogUserActionMapper;
import com.stylefeng.guns.modular.system.dao.WayAccessoryLogWindowsVersionMapper;
import com.stylefeng.guns.modular.system.dao.WayAccessoryModelUserMapper;
import com.stylefeng.guns.modular.system.dao.WayAccessoryRelUserChipFingerprintMapper;
import com.stylefeng.guns.modular.system.dao.WayAccessoryStatisticalNewUserMapper;
import com.stylefeng.guns.modular.system.model.WayAccessoryLogQuickLaunch;
import com.stylefeng.guns.modular.system.model.WayAccessoryStatisticalNewUser;

/**
 * 统计接口
 * 
 * @author lori
 *
 */
@Controller
@RequestMapping("wayapi/v1/accessory/statistical")
public class StatisticalController {
	private static final Logger LOG = LoggerFactory.getLogger(AppBindController.class);
	private static String Tag = "StatisticalController";
	private DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
	@Autowired
	private WayAccessoryModelUserMapper wayAccessoryModelUserMapper;

	@Autowired
	private WayAccessoryStatisticalNewUserMapper wayAccessoryStatisticalNewUserMapper;

	@Autowired
	private WayAccessoryRelUserChipFingerprintMapper wayAccessoryRelUserChipFingerprintMapper;

	@Autowired
	private WayAccessoryLogWindowsVersionMapper wayAccessoryLogWindowsVersionMapper;

	@Autowired
	private WayAccessoryLogQuickLaunchMapper wayAccessoryLogQuickLaunchMapper;

	@Autowired
	private WayAccessoryLogUserActionMapper wayAccessoryLogUserActionMapper;

	/**
	 * TODO 实时新增用户统计数量查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "newUserCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> newUserCount() {

		String methodName = "/newUserCount";
		LOG.info(Tag + methodName);

		Map<String, String> map = new HashMap<String, String>();
		try {

			int userCount = wayAccessoryModelUserMapper.selectAllAmount();
			// 每天新增人数
			int dayCount = wayAccessoryModelUserMapper.selectDayCount(TimeUtil.getTodayDateString());
			// 每周新增
			int weekCount = wayAccessoryModelUserMapper.selectWeekCount();
			// 每月新增
			int monthCount = wayAccessoryModelUserMapper.selectMonthCount();

			// 前一日新增人数
			//int dayHCount = wayAccessoryModelUserMapper.selectDayCount(TimeUtil.getYesterdayDateString());
			// 前一周新增人数
			/*WayAccessoryStatisticalNewUser wayAccessoryStatisticalNewUserW = wayAccessoryStatisticalNewUserMapper
					.selectWeekCount(TimeUtil.getLastWeekMonday(TimeUtil.getTimestamp()));*/
			// 前一月新增人数
			/*WayAccessoryStatisticalNewUser wayAccessoryStatisticalNewUserM = wayAccessoryStatisticalNewUserMapper
					.selectMonthCount(TimeUtil.getLastMonthMonday(TimeUtil.getTimestamp()));*/
			NewUserCount newUserCount = new NewUserCount();
			newUserCount.setDayCount(dayCount);
			String dayPercent = "100%";
			if (userCount != 0) {
				dayPercent = df.format((float) dayCount * 100 / userCount) + "%";// 返回的是String类型
			}

			newUserCount.setUserCount(userCount);
			newUserCount.setDayPercent(dayPercent);

			newUserCount.setMonthCount(monthCount);
			String weekPercent = "100%";
			/*int weekHCount = 0;
			if (ToolUtil.isNotEmpty(wayAccessoryStatisticalNewUserW)) {
				weekHCount = wayAccessoryStatisticalNewUserW.getCountNumber();
			}*/
			if (userCount != 0) {

				weekPercent = df.format((float) weekCount * 100 / userCount) + "%";// 返回的是String类型
			}
			newUserCount.setWeekPercent(weekPercent);

			newUserCount.setWeekCount(weekCount);
			String monthPercent = "100%";
			/*int monthHCount = 0;
			if (ToolUtil.isNotEmpty(wayAccessoryStatisticalNewUserM)) {
				monthHCount = wayAccessoryStatisticalNewUserM.getCountNumber();
			}*/
			if (userCount != 0) {
				monthPercent = df.format((float) monthCount * 100 / userCount) + "%";// 返回的是String类型

			}
			newUserCount.setMonthPercent(monthPercent);
			map.put(Params.RESPONSESTATUS, Params.SUCCESS);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SUCCESS_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SUCCESS_MSG);
			map.put(Params.RESPONSEBODY, JSON.toJSONString(newUserCount));

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

	/**
	 * TODO 用户周期活跃统计数据查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "userActive", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> userActive() {

		String methodName = "/userActive";
		LOG.info(Tag + methodName);

		Map<String, String> map = new HashMap<String, String>();
		try {

			List<ActiveUser> activeUserList = new ArrayList<ActiveUser>();
			// 日活跃
			int dayActive = wayAccessoryModelUserMapper.selectDayActiveCount(TimeUtil.getTodayDateString());
			int yestedayActive = wayAccessoryLogUserActionMapper
					.activeYesterdayAmount(TimeUtil.getYesterdayDateString()).size();

			ActiveUser activeDay = new ActiveUser();
			activeDay.setAmount(dayActive);
			String persent = "0%";
			if (yestedayActive != 0) {
				persent = df.format((float) dayActive * 100 / yestedayActive) + "%";// 返回的是String类型
			}
			activeDay.setPersent(persent);
			activeUserList.add(activeDay);

			// 周活跃
			int weekActive = wayAccessoryModelUserMapper.selectWeekActiveCount();
			int lastWeekActive = wayAccessoryLogUserActionMapper.activeLastWeekAmount().size();

			ActiveUser activeWeek = new ActiveUser();
			activeWeek.setAmount(weekActive);
			String persentW = "0%";
			if (lastWeekActive != 0) {
				persentW = df.format((float) weekActive * 100 / lastWeekActive) + "%";// 返回的是String类型
			}
			activeWeek.setPersent(persentW);
			activeUserList.add(activeWeek);

			// 月活跃
			int monthActive = wayAccessoryModelUserMapper.selectMonthActiveCount();
			int lastMonthActive = wayAccessoryLogUserActionMapper.activeLastMonthAmount().size();

			ActiveUser activeMonth = new ActiveUser();
			activeMonth.setAmount(monthActive);
			String persentM = "0%";
			if (lastMonthActive != 0) {
				persentM = df.format((float) monthActive * 100 / lastMonthActive) + "%";// 返回的是String类型
			}
			activeMonth.setPersent(persentM);
			activeUserList.add(activeMonth);

			map.put(Params.RESPONSESTATUS, Params.SUCCESS);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SUCCESS_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SUCCESS_MSG);
			map.put(Params.RESPONSEBODY, JSON.toJSONString(activeUserList));
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

	/**
	 * TODO 地区统计数据查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "userArea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> userArea() {
		String methodName = "/userArea";
		LOG.info(Tag + methodName);

		Map<String, String> map = new HashMap<String, String>();
		try {
			List<Map<String, Object>> countList = wayAccessoryModelUserMapper.selectArea();
			List<TypeUserArea> typeUserAreaList = new ArrayList<TypeUserArea>();
			int amount = wayAccessoryModelUserMapper.selectAllAmount();
			for (Map<String, Object> mapUser : countList) {
				TypeUserArea typeUserArea = new TypeUserArea();
				typeUserArea.setCity(String.valueOf(mapUser.get("city")));
				int count = Integer.valueOf(mapUser.get("count").toString());
				typeUserArea.setCount(count);
				String countPercent = df.format((float) count * 100 / amount) + "%";// 返回的是String类型

				typeUserArea.setCountPercent(countPercent);
				typeUserAreaList.add(typeUserArea);
			}

			map.put(Params.RESPONSESTATUS, Params.SUCCESS);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SUCCESS_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SUCCESS_MSG);
			map.put(Params.RESPONSEBODY, JSON.toJSONString(typeUserAreaList));
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

	/**
	 * TODO 用户指纹统计数据查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "userFinger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> userFinger() {

		String methodName = "/userFinger";
		LOG.info(Tag + methodName);

		Map<String, String> map = new HashMap<String, String>();
		try {
			TypeUserFingerprintInfo typeUserFingerprintInfo = new TypeUserFingerprintInfo();
			// 注册有效指纹总数量
			int amountFingerprint = wayAccessoryRelUserChipFingerprintMapper.selectAllAmount();
			typeUserFingerprintInfo.setFingerprintAmout(amountFingerprint);
			// 注册有效用户总数量
			int userAmount = wayAccessoryModelUserMapper.selectAllAmount();

			float avgFingerprintAmout = 0;
			if (userAmount != 0) {
				avgFingerprintAmout = amountFingerprint / userAmount;
			}

			typeUserFingerprintInfo.setAvgFingerprintAmout(avgFingerprintAmout);
			typeUserFingerprintInfo.setUserAmout(userAmount);
			List<AvgFingerprint> avgFingerprintList = new ArrayList<AvgFingerprint>();
			List<Map<String, Object>> selectTpyeAllFingerprintList = wayAccessoryRelUserChipFingerprintMapper
					.selectTpyeAllFingerprint();
			for (Map<String, Object> typeUserFingerprint : selectTpyeAllFingerprintList) {
				AvgFingerprint avgFingerprint = new AvgFingerprint();
				avgFingerprint.setType(Integer.valueOf(typeUserFingerprint.get("fp_location").toString()));
				String percent = "0.00";
				if (amountFingerprint != 0) {
					percent = df.format(
							(float) Integer.valueOf(typeUserFingerprint.get("count").toString()) * 100 / amountFingerprint)
							+ "";// 返回的是String类型
				}

				avgFingerprint.setPercent(percent);
				avgFingerprint.setAmount(typeUserFingerprint.get("count").toString());
				avgFingerprintList.add(avgFingerprint);
			}
			typeUserFingerprintInfo.setAvgFingerprintList(avgFingerprintList);

			map.put(Params.RESPONSESTATUS, Params.SUCCESS);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SUCCESS_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SUCCESS_MSG);
			map.put(Params.RESPONSEBODY, JSON.toJSONString(typeUserFingerprintInfo));
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

	/**
	 * TODO 操作系统统计数据查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "windowsVersion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> windowsVersion() {

		String methodName = "/windowsVersion";
		LOG.info(Tag + methodName);

		Map<String, String> map = new HashMap<String, String>();
		try {

			List<Map<String, Object>> amountVersionList = wayAccessoryLogWindowsVersionMapper.selectTpyeAllVersion();
			int amount = wayAccessoryLogWindowsVersionMapper.selectAllAmount();
			List<TypeVersion> typeVersionList = new ArrayList<TypeVersion>();
			for (Map<String, Object> object : amountVersionList) {
				String version = "";
				TypeVersion typeVersion = new TypeVersion();
				if (object.get("version").toString().equals("1")) {
					version = "WIN7";
				} else if (object.get("version").toString().equals("2")) {
					version = "WIN8";
				} else if (object.get("version").toString().equals("3")) {
					version = "WIN8.1";
				} else if (object.get("version").toString().equals("4")) {
					version = "WIN10";
				}
				typeVersion.setType(version);
				typeVersion.setAmount(Integer.valueOf(object.get("count").toString()));
				String percent = df.format((float) Integer.valueOf(object.get("count").toString()) * 100 / amount)
						+ "%";// 返回的是String类型
				typeVersion.setPercent(percent);
				typeVersionList.add(typeVersion);
			}
			map.put(Params.RESPONSESTATUS, Params.SUCCESS);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SUCCESS_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SUCCESS_MSG);
			map.put(Params.RESPONSEBODY, JSON.toJSONString(typeVersionList));
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

	/**
	 * TODO 直达统计数据查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "quickLaunch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> quickLaunch() {

		String methodName = "/quickLaunch";
		LOG.info(Tag + methodName);

		Map<String, String> map = new HashMap<String, String>();
		try {
			// 网页直达统计

			List<LaunchAppInfo> launchSiteList = new ArrayList<LaunchAppInfo>();
			List<WayAccessoryLogQuickLaunch> listSiteList = wayAccessoryLogQuickLaunchMapper.selectWebSiteType();
			for (WayAccessoryLogQuickLaunch wayAccessoryLogQuickLaunch : listSiteList) {
				LaunchAppInfo launchAppInfo = new LaunchAppInfo();
				launchAppInfo.setUrl(wayAccessoryLogQuickLaunch.getSiteUrl());
				launchAppInfo.setPercent(wayAccessoryLogQuickLaunch.getPercent() * 100 + "%");
				launchAppInfo.setType(1);
				launchSiteList.add(launchAppInfo);
			}
			List<WayAccessoryLogQuickLaunch> listAppList = wayAccessoryLogQuickLaunchMapper.selectAppType();
			for (WayAccessoryLogQuickLaunch wayAccessoryLogQuickLaunch : listAppList) {

				LaunchAppInfo launchAppInfo = new LaunchAppInfo();
				launchAppInfo.setUrl(wayAccessoryLogQuickLaunch.getAppUrl());
				launchAppInfo.setPercent(wayAccessoryLogQuickLaunch.getPercent() * 100 + "%");
				launchAppInfo.setType(2);
				launchSiteList.add(launchAppInfo);
			}
			map.put(Params.RESPONSESTATUS, Params.SUCCESS);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SUCCESS_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SUCCESS_MSG);

			map.put(Params.RESPONSEBODY, JSON.toJSONString(launchSiteList));

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

	/**
	 * TODO 用户行为分析接口 获取当日,当周,当月用户行为数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "userAction", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> userAction() {

		String methodName = "/userAction";
		LOG.info(Tag + methodName);
		Map<String, String> map = new HashMap<String, String>();
		try {
			List<UserAction> userActionList = new ArrayList<UserAction>();
			// 获得当日次数
			List<Map<String, Object>> dayList = wayAccessoryLogUserActionMapper
					.selectDayAmount(TimeUtil.getTodayDateString());
			for (Map<String, Object> dayMap : dayList) {
				int type = Integer.valueOf(dayMap.get("type").toString());
				int count = Integer.valueOf(dayMap.get("count").toString());
				UserAction userAction = new UserAction();
				String typeA = "";
				userAction.setTimeType("日");
				if (type == 1) {
					typeA = "客户端启动的次数";
				} else if (type == 2) {
					typeA = "用户注册";
				} else if (type == 3) {
					typeA = "用户手机登录";
				} else if (type == 4) {
					typeA = "指纹登录/解锁点击次数";
				} else if (type == 5) {
					typeA = "用户退出";
				} else if (type == 6) {
					typeA = "客户端切换帐号登录的次数";
				} else if (type == 7) {
					typeA = "文件加解密点击次数 ";
				} else if (type == 8) {
					typeA = "网页/应用快捷直达点击次数";
				} else if (type == 9) {
					typeA = "应用/网站免密登录点击次数";
				} else if (type == 10) {
					typeA = "使用小贴士点击次数";
				} else if (type == 11) {
					typeA = "帮助中心点击次数";
				} else if (type == 12) {
					typeA = "客户端退出的次数";
				}
				userAction.setType(typeA);
				userAction.setCount(String.valueOf(count));
				userActionList.add(userAction);
			}
			// 获得当周次数
			List<Map<String, Object>> weekList = wayAccessoryLogUserActionMapper.selectWeekAmount();
			for (Map<String, Object> weekMap : weekList) {
				int type = Integer.valueOf(weekMap.get("type").toString());
				int count = Integer.valueOf(weekMap.get("count").toString());
				UserAction userAction = new UserAction();
				String typeA = "";
				userAction.setTimeType("周");
				if (type == 1) {
					typeA = "客户端启动的次数";
				} else if (type == 2) {
					typeA = "用户注册";
				} else if (type == 3) {
					typeA = "用户手机登录";
				} else if (type == 4) {
					typeA = "指纹登录/解锁点击次数";
				} else if (type == 5) {
					typeA = "用户退出";
				} else if (type == 6) {
					typeA = "客户端切换帐号登录的次数";
				} else if (type == 7) {
					typeA = "文件加解密点击次数 ";
				} else if (type == 8) {
					typeA = "网页/应用快捷直达点击次数";
				} else if (type == 9) {
					typeA = "应用/网站免密登录点击次数";
				} else if (type == 10) {
					typeA = "使用小贴士点击次数";
				} else if (type == 11) {
					typeA = "帮助中心点击次数";
				} else if (type == 12) {
					typeA = "客户端退出的次数";
				}
				userAction.setType(typeA);
				userAction.setCount(String.valueOf(count));
				userActionList.add(userAction);
			}
			// 获得当月次数
			List<Map<String, Object>> monthList = wayAccessoryLogUserActionMapper.selectMonthAmount();
			for (Map<String, Object> monthMap : monthList) {
				int type = Integer.valueOf(monthMap.get("type").toString());
				int count = Integer.valueOf(monthMap.get("count").toString());
				UserAction userAction = new UserAction();
				String typeA = "";
				userAction.setTimeType("月");
				if (type == 1) {
					typeA = "客户端启动的次数";
				} else if (type == 2) {
					typeA = "用户注册";
				} else if (type == 3) {
					typeA = "用户手机登录";
				} else if (type == 4) {
					typeA = "指纹登录/解锁点击次数";
				} else if (type == 5) {
					typeA = "用户退出";
				} else if (type == 6) {
					typeA = "客户端切换帐号登录的次数";
				} else if (type == 7) {
					typeA = "文件加解密点击次数 ";
				} else if (type == 8) {
					typeA = "网页/应用快捷直达点击次数";
				} else if (type == 9) {
					typeA = "应用/网站免密登录点击次数";
				} else if (type == 10) {
					typeA = "使用小贴士点击次数";
				} else if (type == 11) {
					typeA = "帮助中心点击次数";
				} else if (type == 12) {
					typeA = "客户端退出的次数";
				}
				userAction.setType(typeA);
				userAction.setCount(String.valueOf(count));
				userActionList.add(userAction);
			}

			map.put(Params.RESPONSESTATUS, Params.SUCCESS);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SUCCESS_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SUCCESS_MSG);
			map.put(Params.RESPONSEBODY, JSON.toJSONString(userActionList));
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

	/**
	 * TODO 用户行为获取接口 根据开始&结束日期获取用户此时间段内全部行为数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "getUserAction", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> getUserAction(@RequestParam("beginTime") String beginTime,
			@RequestParam("endTime") String endTime) {

		String methodName = "/getUserAction";
		LOG.info(Tag + methodName);

		Map<String, String> map = new HashMap<String, String>();
		try {
			List<UserAction> userActionList = new ArrayList<UserAction>();
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			Date dateS = format1.parse(beginTime);
			Date dateE = format1.parse(endTime);
			int i = TimeUtil.daysBetween(dateS, dateE) + 1; // 天数加1
			// 获得当日次数
			List<Map<String, Object>> dayList = wayAccessoryLogUserActionMapper.selectDayAvgAmount(beginTime, endTime);
			for (Map<String, Object> dayMap : dayList) {
				int type = Integer.valueOf(dayMap.get("type").toString());
				int count = Integer.valueOf(dayMap.get("count").toString());
				UserAction userActionAll = new UserAction();
				userActionAll.setTimeType("日");
				UserAction userActionAvg = new UserAction();
				userActionAvg.setTimeType("日平均数");
				String typeA = "";
				if (type == 1) {
					typeA = "客户端启动的次数";
				} else if (type == 2) {
					typeA = "用户注册";
				} else if (type == 3) {
					typeA = "用户手机登录";
				} else if (type == 4) {
					typeA = "指纹登录/解锁点击次数";
				} else if (type == 5) {
					typeA = "用户退出";
				} else if (type == 6) {
					typeA = "客户端切换帐号登录的次数";
				} else if (type == 7) {
					typeA = "文件加解密点击次数 ";
				} else if (type == 8) {
					typeA = "网页/应用快捷直达点击次数";
				} else if (type == 9) {
					typeA = "应用/网站免密登录点击次数";
				} else if (type == 10) {
					typeA = "使用小贴士点击次数";
				} else if (type == 11) {
					typeA = "帮助中心点击次数";
				} else if (type == 12) {
					typeA = "客户端退出的次数";
				}
				userActionAll.setType(typeA);
				userActionAvg.setType(typeA);

				userActionAll.setCount(String.valueOf(count));
				userActionAvg.setCount(String.valueOf((float) count / (float) i));
				userActionList.add(userActionAll);
				userActionList.add(userActionAvg);
			}
			userActionList = addWeekAndMonth(userActionList);

			map.put(Params.RESPONSESTATUS, Params.SUCCESS);
			map.put(Params.RESPONSECODE, ErrorCodeParam.SUCCESS_CODE);
			map.put(Params.RESPONSEMESSAGE, ErrorCodeParam.SUCCESS_MSG);
			map.put(Params.RESPONSEBODY, JSON.toJSONString(userActionList));
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

	public List<UserAction> addWeekAndMonth(List<UserAction> userActionList) {
		userActionList.add(setUserActionNull("周", "客户端启动的次数"));
		userActionList.add(setUserActionNull("周", "客户端退出的次数"));
		userActionList.add(setUserActionNull("周", "客户端切换帐号登录的次数"));
		userActionList.add(setUserActionNull("周", "指纹登录/解锁点击次数"));
		userActionList.add(setUserActionNull("周", "文件加解密点击次数"));
		userActionList.add(setUserActionNull("周", "网页/应用快捷直达点击次数"));
		userActionList.add(setUserActionNull("周", "应用/网站免密登录点击次数"));
		userActionList.add(setUserActionNull("周", "使用小贴士点击次数"));
		userActionList.add(setUserActionNull("周", "帮助中心点击次数"));

		userActionList.add(setUserActionNull("月", "客户端启动的次数"));
		userActionList.add(setUserActionNull("月", "客户端退出的次数"));
		userActionList.add(setUserActionNull("月", "客户端切换帐号登录的次数"));
		userActionList.add(setUserActionNull("月", "指纹登录/解锁点击次数"));
		userActionList.add(setUserActionNull("月", "文件加解密点击次数"));
		userActionList.add(setUserActionNull("月", "网页/应用快捷直达点击次数"));
		userActionList.add(setUserActionNull("月", "应用/网站免密登录点击次数"));
		userActionList.add(setUserActionNull("月", "使用小贴士点击次数"));
		userActionList.add(setUserActionNull("月", "帮助中心点击次数"));
		return userActionList;
	}

	/**
	 * 搜索时,用户行为:周&月数据置空
	 * 
	 * @param timeType
	 * @param type
	 * @return
	 */
	public UserAction setUserActionNull(String timeType, String type) {
		UserAction userAction = new UserAction();
		userAction.setTimeType(timeType);
		userAction.setType(type);
		userAction.setCount("-");
		return userAction;
	}
}
