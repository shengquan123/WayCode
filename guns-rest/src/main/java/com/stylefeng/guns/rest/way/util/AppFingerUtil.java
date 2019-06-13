package com.stylefeng.guns.rest.way.util;

import java.util.regex.Pattern;

import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryModelChipMapper;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryModelChip;
import com.stylefeng.guns.rest.way.model.AccessoryChipModel;

/**
 * 用户指纹工具类
 * 
 * @author Administrator
 *
 */
public class AppFingerUtil {

	/**
	 * 根据设备编号判断是否存在设备，存则返回，不存在则新建
	 * 
	 * @param chipNumber
	 * @param wayAccessoryModelChipMapper
	 * @return
	 */
	public static AccessoryChipModel findChipOrAddChip(String chipNumber, int chipType,
			WayAccessoryModelChipMapper wayAccessoryModelChipMapper) {

		WayAccessoryModelChip wayAccessoryModelChip = wayAccessoryModelChipMapper
				.selectWayAccessoryModelChipByChipNumber(chipNumber,chipType , 1);
		// 查询设备，没有设备添加设备
		if (wayAccessoryModelChip == null) {
			// 添加设备
			wayAccessoryModelChip = ModelUtil.setWayAccessoryModelChip(chipNumber, chipType);
			wayAccessoryModelChipMapper.insert(wayAccessoryModelChip);

		} else {
			wayAccessoryModelChip.setUpdateTime(TimeUtil.getTimestamp());
			wayAccessoryModelChipMapper.updateAllColumnById(wayAccessoryModelChip);
		}

		AccessoryChipModel accessoryChipModel = new AccessoryChipModel();
		accessoryChipModel.setChipBrand(wayAccessoryModelChip.getChipBrand());
		accessoryChipModel.setChipId(Integer.parseInt(wayAccessoryModelChip.getId().toString()));
		accessoryChipModel.setChipNumber(wayAccessoryModelChip.getChipNumber());

		return accessoryChipModel;
	}

	/**
	 * 判断是否为浏览器地址
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isURL(String str) {
		Pattern pattern = Pattern
				.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
		return pattern.matcher(str).matches();
	}
}
