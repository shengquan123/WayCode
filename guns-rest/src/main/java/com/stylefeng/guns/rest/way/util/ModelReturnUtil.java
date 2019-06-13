package com.stylefeng.guns.rest.way.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryModelUser;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryRelUserChipFingerprint;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryRelUserChipQuickLaunch;
import com.stylefeng.guns.rest.way.model.FingerManagerModel;
import com.stylefeng.guns.rest.way.model.QuickLaunchModel;
import com.stylefeng.guns.rest.way.model.ResponseModel;
import com.stylefeng.guns.rest.way.model.UserModel;

public class ModelReturnUtil {

	/** date转String */
	final static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 用户模型
	 * 
	 * @param wayAccessoryModelUser
	 * @return
	 */
	public static UserModel setUserModel(WayAccessoryModelUser wayAccessoryModelUser, Integer loginType) {
		UserModel userModel = new UserModel();
		List<FingerManagerModel> fingerManagerModels = new ArrayList<FingerManagerModel>();
		userModel.setUserId(wayAccessoryModelUser.getId() + "");
		userModel.setUserPhone(wayAccessoryModelUser.getPhoneNo());
		userModel.setLoginType(loginType);
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
			List<WayAccessoryRelUserChipQuickLaunch> list) {
		FingerManagerModel fingerManagerModel = new FingerManagerModel();
		QuickLaunchModel quickLaunchModel = new QuickLaunchModel();
		fingerManagerModel.setFqId(Integer.parseInt(wayAccessoryRelUserChipFingerprint.getId().toString()));
		fingerManagerModel.setLocation(wayAccessoryRelUserChipFingerprint.getFpLocation() + "");
		fingerManagerModel.setChipId(Integer.parseInt(wayAccessoryRelUserChipFingerprint.getChipId().toString()));
		fingerManagerModel.setChipNumber(wayAccessoryRelUserChipFingerprint.getChipNumber().toString());
		fingerManagerModel.setUserId(Integer.parseInt(wayAccessoryRelUserChipFingerprint.getUserId().toString()));
		fingerManagerModel.setQuickLaunchModel(quickLaunchModel);
		fingerManagerModel.setChipFpId(wayAccessoryRelUserChipFingerprint.getChipFpId());
		fingerManagerModel.setAddTime(format.format(wayAccessoryRelUserChipFingerprint.getCreateTime()));

		if (list.size() > 0) {
			for (WayAccessoryRelUserChipQuickLaunch obj : list) {
				if (wayAccessoryRelUserChipFingerprint.getId().equals(obj.getFingerprintInfoId())) {
					quickLaunchModel = setQuickLaunchModel(obj);
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
	public static QuickLaunchModel setQuickLaunchModel(WayAccessoryRelUserChipQuickLaunch obj) {
		QuickLaunchModel quickLaunchModel = new QuickLaunchModel();
		quickLaunchModel.setQuickId(Integer.valueOf(obj.getId().toString()));
		quickLaunchModel.setType(Integer.valueOf(obj.getType().toString()));
		quickLaunchModel.setUrl(obj.getAppPath().toString());
		quickLaunchModel.setImg_url(obj.getIconDefult());
		return quickLaunchModel;
	}

	/**
	 * 
	 * @param responseModel
	 * @return
	 */
	public static ResponseModel setResponseModel(ResponseModel responseModel, String responseCode,
			String responseMessage, String sign, Object object) {
		responseModel.setResponseCode(responseCode);
		responseModel.setResponseMessage(responseMessage);
		responseModel.setSign(sign);
		responseModel.setObject(object);
		return responseModel;
	}
}
