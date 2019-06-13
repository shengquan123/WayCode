package com.stylefeng.guns.modular.system.warpper;

import java.util.List;
import java.util.Map;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;

public class FidoRestAppWarpper extends BaseControllerWarpper {

	public FidoRestAppWarpper(List<Map<String, Object>> list) {
		super(list);
	}

	@Override
	protected void warpTheMap(Map<String, Object> map) {
		try {
			map.put("status", ConstantFactory.me().getStatus(Integer.parseInt((String) map.get("status"))));
		} catch (Exception e) {
			map.put("status", map.get("status"));
		}
	}
}
