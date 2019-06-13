package com.stylefeng.guns.modular.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.modular.system.model.WayAccessoryRelUserChipQuickLaunch;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-06-22
 */
public interface WayAccessoryRelUserChipQuickLaunchMapper extends BaseMapper<WayAccessoryRelUserChipQuickLaunch> {
	/**
	 * 根据设备指纹信息表id查询
	 *
	 * @return
	 * @date 2017年6月23日 下午17:56:56
	 */
	public List<WayAccessoryRelUserChipQuickLaunch> selectWayAccessoryRelUserChipQuickLaunchMapperByFingerprintInfoId(
			@Param("fingerprintInfoId") String fingerprintInfoId);

	/**
	 * 通过chipID和UserId查询快捷信息
	 * 
	 * @param userId
	 * @param chipId
	 * @return
	 */
	public List<WayAccessoryRelUserChipQuickLaunch> selectWayAccessorByChipIdAnduserId(@Param("userId") int userId,
			@Param("chipId") int chipId);

}