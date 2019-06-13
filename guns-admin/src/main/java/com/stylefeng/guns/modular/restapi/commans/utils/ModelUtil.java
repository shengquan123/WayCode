package com.stylefeng.guns.modular.restapi.commans.utils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.stylefeng.guns.modular.restapi.model.UserModelInfo;
import com.stylefeng.guns.modular.system.model.WayAccessoryModelUser;
import com.stylefeng.guns.modular.system.model.WayAccessoryStatisticalNewUser;
import com.stylefeng.guns.modular.system.model.WayAccessoryStatisticalUserAction;

public class ModelUtil {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
	 * TODO 返回后台用户数据
	 * 
	 * @param wayAccessoryModelUser
	 * @param fingerprintAmount
	 * @param encryptAmount
	 * @param quickLaunchAmount
	 * @param messageSendAmount
	 * @param mailSendAmount
	 * @param phone
	 */
	public static Map<String, Object> setModelUser(WayAccessoryModelUser wayAccessoryModelUser, int fingerprintAmount,
			int encryptAmount, int quickLaunchAmount, int messageSendAmount, int mailSendAmount, String phone) {
		Map<String, Object> maps = new HashMap<String, Object>();

		maps.put("userId", wayAccessoryModelUser.getId().toString());
		maps.put("registerTime", sdf.format(wayAccessoryModelUser.getCreateTime()));
		maps.put("registerToPresentTime",
				TimeUtil.getIntervalDays(wayAccessoryModelUser.getCreateTime(), TimeUtil.getTimestamp()));

		// 指纹数量
		maps.put("fingerprintAmount", "<a style='color:#4F9D9D;' href='#' onclick='MgrUser.openFp("
				+ wayAccessoryModelUser.getId().toString() + ",1)'>" + fingerprintAmount + "</span>");
		maps.put("fingerprintAmountNo", fingerprintAmount);

		maps.put("encryptAmount", encryptAmount);
		maps.put("quickLaunchAmount", "<a style='color:#4F9D9D;' href='#' onclick='MgrUser.openFp("
				+ wayAccessoryModelUser.getId().toString() + ",0)'>" + quickLaunchAmount + "</span>");
		maps.put("quickLaunchAmountNo", quickLaunchAmount);

		maps.put("messageSendAmount", messageSendAmount);
		// 添加邮件发送总数量
		maps.put("mailSendAmount", mailSendAmount);
		maps.put("accountName", wayAccessoryModelUser.getAccountName());
		maps.put("nikeName", wayAccessoryModelUser.getNickName());
		if (wayAccessoryModelUser.getSexFlag().toString().equals("1"))
			maps.put("sex", "男");
		else
			maps.put("sex", "女");
		maps.put("email", wayAccessoryModelUser.getEmail());

		return maps;

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
	 * @param mailSendAmount
	 * @param phone
	 */
	public static UserModelInfo setUserModelInfo(WayAccessoryModelUser wayAccessoryModelUser, int fingerprintAmount,
			int encryptAmount, int quickLaunchAmount, int messageSendAmount, int mailSendAmount, String phone) {
		UserModelInfo userModelInfo = new UserModelInfo();
		userModelInfo.setUserId(wayAccessoryModelUser.getId().toString());
		userModelInfo.setRegisterTime(wayAccessoryModelUser.getCreateTime().toString());
		// 注册多少天
		int day = TimeUtil.getIntervalDays(wayAccessoryModelUser.getCreateTime(), TimeUtil.getTimestamp());
		userModelInfo.setRegisterToPresentTime(day + "");
		userModelInfo.setFingerprintAmount(fingerprintAmount);
		userModelInfo.setEncryptAmount(encryptAmount);
		userModelInfo.setQuickLaunchAmount(quickLaunchAmount);
		userModelInfo.setUserPhone(phone);
		userModelInfo.setSex(wayAccessoryModelUser.getSexFlag().toString());
		userModelInfo.setNikeName(wayAccessoryModelUser.getNickName());
		userModelInfo.setEmail(wayAccessoryModelUser.getEmail());
		return userModelInfo;
	}

}
