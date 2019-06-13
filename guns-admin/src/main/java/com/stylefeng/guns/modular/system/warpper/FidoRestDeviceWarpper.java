package com.stylefeng.guns.modular.system.warpper;

import java.util.List;
import java.util.Map;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;

public class FidoRestDeviceWarpper extends BaseControllerWarpper {

	public FidoRestDeviceWarpper(List<Map<String, Object>> list) {
		super(list);
	}

	@Override
	protected void warpTheMap(Map<String, Object> map) {
		try {
			map.put("status", ConstantFactory.me().getStatus(Integer.parseInt(map.get("status").toString())));
			//map.put("status", ConstantFactory.me().getStatus(Integer.parseInt((String)map.get("status"))));
		} catch (NumberFormatException e) {
			map.put("status", map.get("status"));
		}
		try {
			map.put("compromised", ConstantFactory.me().getCompromised(Integer.parseInt(map.get("compromised").toString())));
			//map.put("compromised", ConstantFactory.me().getCompromised(Integer.parseInt((String) map.get("compromised"))));
		} catch (NumberFormatException e) {
			map.put("compromised", map.get("compromised"));
		}
	}
}
