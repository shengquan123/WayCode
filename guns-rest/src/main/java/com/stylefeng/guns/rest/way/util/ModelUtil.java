package com.stylefeng.guns.rest.way.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryRelUserChipQuickLaunchMapper;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryLogQuickLaunch;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryLogUserAction;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryLogUserLogin;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryModelChip;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryModelUser;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryRelUserChip;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryRelUserChipFingerprint;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryRelUserChipQuickLaunch;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryStatisticalNewUser;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryStatisticalUserAction;

public class ModelUtil {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * TODO 手机号创建用户
	 * 
	 * @param phone
	 * @return
	 */
	public static WayAccessoryModelUser setWayAccessoryModelUser(String phone, String ip)
			throws UnsupportedEncodingException {
		WayAccessoryModelUser wayAccessoryModelUser = new WayAccessoryModelUser();

		// json_result用于接收返回的json数据
		// String json_result = null;

		// json_result = IpAddress.getAddresses("ip=" + ip, "utf-8");
		// JSONObject json = JSON.parseObject(json_result);
		// String region =
		// JSON.parseObject(json.get("data").toString()).get("region").toString();
		// String city =
		// JSON.parseObject(json.get("data").toString()).get("city").toString();

		wayAccessoryModelUser.setPhoneNo(phone);
		wayAccessoryModelUser.setAccountName(phone);
		wayAccessoryModelUser.setEmail("");
		wayAccessoryModelUser.setNickName(phone);
		wayAccessoryModelUser.setSexFlag(0);
		wayAccessoryModelUser.setStatus(1);
		wayAccessoryModelUser.setIpAddress(ip);
		wayAccessoryModelUser.setPrivince("上海");
		wayAccessoryModelUser.setCity("上海");
		wayAccessoryModelUser.setNumber(0);
		wayAccessoryModelUser.setCreateTime(TimeUtil.getTimestamp());
		wayAccessoryModelUser.setUpdateTime(TimeUtil.getTimestamp());
		return wayAccessoryModelUser;
	}

	/**
	 * TODO 邮箱创建用户
	 * 
	 * @param phone
	 * @param ip
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static WayAccessoryModelUser setWayAccessoryModelUserByMail(String mail, String ip)
			throws UnsupportedEncodingException {
		WayAccessoryModelUser wayAccessoryModelUser = new WayAccessoryModelUser();

		//wayAccessoryModelUser.setPhoneNo("");
		wayAccessoryModelUser.setAccountName(mail);
		wayAccessoryModelUser.setEmail(mail);
		wayAccessoryModelUser.setNickName(mail);
		wayAccessoryModelUser.setSexFlag(0);
		wayAccessoryModelUser.setStatus(1);
		wayAccessoryModelUser.setIpAddress(ip);
		wayAccessoryModelUser.setPrivince("上海");
		wayAccessoryModelUser.setCity("上海");
		wayAccessoryModelUser.setNumber(0);
		wayAccessoryModelUser.setCreateTime(TimeUtil.getTimestamp());
		wayAccessoryModelUser.setUpdateTime(TimeUtil.getTimestamp());
		return wayAccessoryModelUser;
	}

	/**
	 * TODO 添加设备
	 * 
	 * @param chipNumber
	 * @return
	 */
	public static WayAccessoryModelChip setWayAccessoryModelChip(String chipNumber, Integer chipType) {
		WayAccessoryModelChip wayAccessoryModelChip = new WayAccessoryModelChip();
		wayAccessoryModelChip.setChipNumber(chipNumber);
		wayAccessoryModelChip.setChipBrand("HX");
		wayAccessoryModelChip.setChipType(chipType);
		wayAccessoryModelChip.setStatus(1);
		wayAccessoryModelChip.setCreateTime(TimeUtil.getTimestamp());
		wayAccessoryModelChip.setUpdateTime(TimeUtil.getTimestamp());
		return wayAccessoryModelChip;
	}

	/**
	 * TODO 添加设备和用户关系
	 * 
	 * @param userId
	 * @param chipId
	 * @return
	 */
	public static WayAccessoryRelUserChip setWayAccessoryRelUserChip(long userId, long chipId) {
		WayAccessoryRelUserChip wayAccessoryRelUserChip = new WayAccessoryRelUserChip();
		wayAccessoryRelUserChip.setUserId(userId);
		wayAccessoryRelUserChip.setChipId(chipId);
		wayAccessoryRelUserChip.setStatus(1);
		wayAccessoryRelUserChip.setCreateTime(TimeUtil.getTimestamp());
		wayAccessoryRelUserChip.setUpdateTime(TimeUtil.getTimestamp());
		return wayAccessoryRelUserChip;
	}

	/**
	 * TODO 创建登录日志
	 * 
	 * @param wayAccessoryModelUser
	 * @param chipId
	 * @param tpye
	 * @param logAppType
	 * @return
	 */
	public static WayAccessoryLogUserLogin setWayAccessoryLogUserLogin(WayAccessoryModelUser wayAccessoryModelUser,
			Long chipId, int type, int logAppType) {
		WayAccessoryLogUserLogin wayAccessoryLogUserLogin = new WayAccessoryLogUserLogin();
		wayAccessoryLogUserLogin.setUserId(wayAccessoryModelUser.getId());
		wayAccessoryLogUserLogin.setChipId(chipId);
		wayAccessoryLogUserLogin.setLogAppType(logAppType);
		wayAccessoryLogUserLogin.setLogType(type);
		wayAccessoryLogUserLogin.setStatus(1);
		wayAccessoryLogUserLogin.setLogTime(TimeUtil.getTimestamp());
		return wayAccessoryLogUserLogin;
	}

	/**
	 * TODO 添加直达
	 * 
	 * @param fpId
	 * @param type
	 * @param appPath
	 * @param wayAccessoryRelUserChipQuickLaunchMapper
	 * @return
	 */
	public static Integer saveWayAccessoryRelUserChipQuickLaunch(Long fpId, Integer type, String appPath,
			WayAccessoryRelUserChipQuickLaunchMapper wayAccessoryRelUserChipQuickLaunchMapper) {

		WayAccessoryRelUserChipQuickLaunch wayAccessoryRelUserChipQuickLaunch = new WayAccessoryRelUserChipQuickLaunch();
		wayAccessoryRelUserChipQuickLaunch.setFingerprintInfoId(fpId);
		wayAccessoryRelUserChipQuickLaunch.setType(type);
		wayAccessoryRelUserChipQuickLaunch.setAppPath(appPath);

		if (type == 1)
			wayAccessoryRelUserChipQuickLaunch.setIconDefult(Params.ICON_DEFULT_APP);
		if (type == 2)
			wayAccessoryRelUserChipQuickLaunch.setIconDefult(Params.ICON_DEFULT_WEB);
		if (type == 3)
			wayAccessoryRelUserChipQuickLaunch.setIconDefult(Params.ICON_DEFULT_FILE);

		wayAccessoryRelUserChipQuickLaunch.setCreateTime(TimeUtil.getTimestamp());
		wayAccessoryRelUserChipQuickLaunch.setUpdateTime(TimeUtil.getTimestamp());
		return wayAccessoryRelUserChipQuickLaunchMapper.insert(wayAccessoryRelUserChipQuickLaunch);
	}

	/**
	 * TODO 修改直达
	 * 
	 * @param wayAccessoryRelUserChipQuickLaunch
	 * @param type
	 * @param appPath
	 * @param wayAccessoryRelUserChipQuickLaunchMapper
	 * @return
	 */
	public static boolean updateWayAccessoryRelUserChipQuickLaunch(
			WayAccessoryRelUserChipQuickLaunch wayAccessoryRelUserChipQuickLaunch, Integer type, String appPath,
			WayAccessoryRelUserChipQuickLaunchMapper wayAccessoryRelUserChipQuickLaunchMapper) {

		wayAccessoryRelUserChipQuickLaunch.setType(type);
		wayAccessoryRelUserChipQuickLaunch.setAppPath(appPath);

		if (type == 1)
			wayAccessoryRelUserChipQuickLaunch.setIconDefult(Params.ICON_DEFULT_APP);
		if (type == 2)
			wayAccessoryRelUserChipQuickLaunch.setIconDefult(Params.ICON_DEFULT_WEB);
		if (type == 3)
			wayAccessoryRelUserChipQuickLaunch.setIconDefult(Params.ICON_DEFULT_FILE);

		wayAccessoryRelUserChipQuickLaunch.setUpdateTime(TimeUtil.getTimestamp());
		return wayAccessoryRelUserChipQuickLaunch.updateById();
	}

	/**
	 * TODO 建用户行为数据
	 * 
	 * @param type
	 * @param userId
	 */
	public static void setWayAccessoryLogUserAction(int type, long userId) {
		WayAccessoryLogUserAction wayAccessoryLogUserAction = new WayAccessoryLogUserAction();
		String checkDate = TimeUtil.getTodayDateString();
		wayAccessoryLogUserAction.setCheckDate(checkDate);
		wayAccessoryLogUserAction.setType(type);
		wayAccessoryLogUserAction.setCount(1);
		wayAccessoryLogUserAction.setUserId(userId);
		wayAccessoryLogUserAction.setCreateTime(TimeUtil.getTimestamp());
		wayAccessoryLogUserAction.insert();
	}

	/**
	 * TODO 保存统计新增用户
	 * 
	 * @param checkDate
	 * @param checkType
	 * @param count
	 */
	public static void setWayAccessoryStatisticalNewUser(String checkDate, int checkType, int count) {
		WayAccessoryStatisticalNewUser wayAccessoryStatisticalNewUser = new WayAccessoryStatisticalNewUser();
		wayAccessoryStatisticalNewUser.setCheckDate(checkDate);
		wayAccessoryStatisticalNewUser.setCheckType(checkType);
		wayAccessoryStatisticalNewUser.setCountNumber(count);
		;
		wayAccessoryStatisticalNewUser.setCreateTime(TimeUtil.getTimestamp());
		wayAccessoryStatisticalNewUser.insert();
	}

	/**
	 * 保存用户行为数据
	 * 
	 * @param checkType
	 * @param checkDate
	 * @param type
	 * @param count
	 */
	public static void setWayAccessoryStatisticalUserAction(int checkType, String checkDate, int type, int count) {
		WayAccessoryStatisticalUserAction wayAccessoryStatisticalUserAction = new WayAccessoryStatisticalUserAction();
		wayAccessoryStatisticalUserAction.setCheckType(checkType);
		wayAccessoryStatisticalUserAction.setCheckDate(checkDate);
		wayAccessoryStatisticalUserAction.setType(type);
		wayAccessoryStatisticalUserAction.setCount(count);
		wayAccessoryStatisticalUserAction.setCreateTime(TimeUtil.getTimestamp());
		wayAccessoryStatisticalUserAction.insert();
	}

	/**
	 * TODO 返回后台用户数据
	 * 
	 * @param wayAccessoryModelUser
	 * @param fingerprintAmount
	 * @param encryptAmount
	 * @param quickLaunchAmount
	 * @param messageSendAmount
	 * @param phone
	 */
	public static Map<String, Object> setModelUser(WayAccessoryModelUser wayAccessoryModelUser, int fingerprintAmount,
			int encryptAmount, int quickLaunchAmount, int messageSendAmount, String phone) {
		Map<String, Object> maps = new HashMap<String, Object>();

		maps.put("userId", wayAccessoryModelUser.getId().toString());
		maps.put("registerTime", sdf.format(wayAccessoryModelUser.getCreateTime()));
		maps.put("registerToPresentTime",
				TimeUtil.getIntervalDays(wayAccessoryModelUser.getCreateTime(), TimeUtil.getTimestamp()));
		maps.put("fingerprintAmount", fingerprintAmount);
		maps.put("encryptAmount", encryptAmount);
		maps.put("quickLaunchAmount", quickLaunchAmount);
		maps.put("messageSendAmount", messageSendAmount);
		maps.put("userPhone", wayAccessoryModelUser.getPhoneNo());
		maps.put("nikeName", wayAccessoryModelUser.getNickName());
		if (wayAccessoryModelUser.getSexFlag().toString().equals("1"))
			maps.put("sex", "男");
		else
			maps.put("sex", "女");
		maps.put("email", wayAccessoryModelUser.getEmail());

		return maps;

	}

	/**
	 * 保存数据
	 * 
	 * @param wayAccessoryLogQuickLaunch
	 * @param siteUrl
	 * @param appPath
	 * @param amount
	 */
	public static void setWayAccessoryLogQuickLaunch(int type, WayAccessoryLogQuickLaunch wayAccessoryLogQuickLaunch,
			String siteUrl, String appPath, int amount) {

		wayAccessoryLogQuickLaunch = new WayAccessoryLogQuickLaunch();
		wayAccessoryLogQuickLaunch.setType(type);
		wayAccessoryLogQuickLaunch.setSiteUrl(siteUrl);
		wayAccessoryLogQuickLaunch.setAppUrl(appPath);
		wayAccessoryLogQuickLaunch.setNumber(1);
		wayAccessoryLogQuickLaunch.setPercent(1 / Float.parseFloat(amount + ""));
		wayAccessoryLogQuickLaunch.setCreateTime(TimeUtil.getTimestamp());
		wayAccessoryLogQuickLaunch.insert();
	}

	/**
	 * 添加用户设备指纹关系表
	 * 
	 * @param wayAccessoryModelChip
	 * @param wayAccessoryModelUser
	 * @param location
	 * @return
	 */
	public static WayAccessoryRelUserChipFingerprint addWayAccessoryRelUserChipFingerprint(
			WayAccessoryModelChip wayAccessoryModelChip, WayAccessoryModelUser wayAccessoryModelUser, Integer location,
			Integer chipFpId) {
		WayAccessoryRelUserChipFingerprint wayAccessoryRelUserChipFingerprint = new WayAccessoryRelUserChipFingerprint();
		wayAccessoryRelUserChipFingerprint.setChipId(wayAccessoryModelChip.getId());
		wayAccessoryRelUserChipFingerprint.setChipNumber(wayAccessoryModelChip.getChipNumber());
		wayAccessoryRelUserChipFingerprint.setUserId(wayAccessoryModelUser.getId());
		wayAccessoryRelUserChipFingerprint.setChipFpId(chipFpId);
		wayAccessoryRelUserChipFingerprint.setFpLocation(location);
		wayAccessoryRelUserChipFingerprint.setStatus(1);
		wayAccessoryRelUserChipFingerprint.setCreateTime(TimeUtil.getTimestamp());
		wayAccessoryRelUserChipFingerprint.setUpdateTime(TimeUtil.getTimestamp());
		return wayAccessoryRelUserChipFingerprint;
	}
}
