package com.stylefeng.guns.modular.restapi.commans.utils;

import java.util.ArrayList;
import java.util.List;

import com.stylefeng.guns.modular.restapi.model.FingerManagerModel;
import com.stylefeng.guns.modular.restapi.model.QuickLaunchModel;
import com.stylefeng.guns.modular.restapi.model.UserModel;
import com.stylefeng.guns.modular.system.model.WayAccessoryModelUser;
import com.stylefeng.guns.modular.system.model.WayAccessoryRelUserChipFingerprint;
import com.stylefeng.guns.modular.system.model.WayAccessoryRelUserChipQuickLaunch;

public class ModelReturnUtil {
	/**
	 * 用户模型
	 * 
	 * @param wayAccessoryModelUser
	 * @return
	 */
	public static UserModel setUserModel(WayAccessoryModelUser wayAccessoryModelUser) {
		UserModel userModel = new UserModel();
		List<FingerManagerModel> fingerManagerModels = new ArrayList<FingerManagerModel>();
		userModel.setUserId(wayAccessoryModelUser.getId() + "");
		userModel.setUserPhone(wayAccessoryModelUser.getPhoneNo());
		userModel.setNikeName(wayAccessoryModelUser.getNickName());
		userModel.setEmail(wayAccessoryModelUser.getEmail());
		userModel.setSex(wayAccessoryModelUser.getSexFlag() + "");
		userModel.setFingerManagerModels(fingerManagerModels);
		return userModel;
	}

	/**
	 * 指纹的返回
	 * 
	 * @param wayAccessoryRelUserChipFingerprint
	 * @return
	 */
	public static FingerManagerModel setWayAccessoryRelUserChipFingerprint(
			WayAccessoryRelUserChipFingerprint wayAccessoryRelUserChipFingerprint,
			List<WayAccessoryRelUserChipQuickLaunch> wayAccessoryRelUserChipQuickLaunchList) {
		FingerManagerModel fingerManagerModel = new FingerManagerModel();
		QuickLaunchModel quickLaunchModel = new QuickLaunchModel();
		fingerManagerModel.setFqId(wayAccessoryRelUserChipFingerprint.getId().intValue());
		fingerManagerModel.setLocation(wayAccessoryRelUserChipFingerprint.getFpLocation() + "");
		fingerManagerModel.setChipId(wayAccessoryRelUserChipFingerprint.getChipId().intValue());
		fingerManagerModel.setChipNumber(wayAccessoryRelUserChipFingerprint.getChipNumber());
		fingerManagerModel.setUserId(wayAccessoryRelUserChipFingerprint.getUserId().intValue());
		fingerManagerModel.setQuickLaunchModel(quickLaunchModel);
		if (wayAccessoryRelUserChipQuickLaunchList.size() > 0) {
			for (WayAccessoryRelUserChipQuickLaunch wayAccessoryRelUserChipQuickLaunch : wayAccessoryRelUserChipQuickLaunchList) {
				if (wayAccessoryRelUserChipFingerprint.getId() == wayAccessoryRelUserChipQuickLaunch
						.getFingerprintInfoId()) {
					quickLaunchModel = setQuickLaunchModel(wayAccessoryRelUserChipQuickLaunch);
					fingerManagerModel.setQuickLaunchModel(quickLaunchModel);
					break;
				}
			}
		}

		return fingerManagerModel;
	}

	/**
	 * 直达模型
	 * 
	 * @param wayAccessoryRelUserChipQuickLaunch
	 * @return
	 */
	public static QuickLaunchModel setQuickLaunchModel(
			WayAccessoryRelUserChipQuickLaunch wayAccessoryRelUserChipQuickLaunch) {
		QuickLaunchModel quickLaunchModel = new QuickLaunchModel();
		quickLaunchModel.setQuickId(wayAccessoryRelUserChipQuickLaunch.getId().intValue());
		quickLaunchModel.setType(wayAccessoryRelUserChipQuickLaunch.getType());
		quickLaunchModel.setUrl(wayAccessoryRelUserChipQuickLaunch.getAppPath());
		quickLaunchModel.setImg_url(wayAccessoryRelUserChipQuickLaunch.getAppPath());
		return quickLaunchModel;

	}
}
