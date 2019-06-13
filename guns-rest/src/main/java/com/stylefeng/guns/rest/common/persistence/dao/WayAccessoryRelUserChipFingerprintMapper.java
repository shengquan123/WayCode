package com.stylefeng.guns.rest.common.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryRelUserChipFingerprint;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-06-22
 */
public interface WayAccessoryRelUserChipFingerprintMapper extends BaseMapper<WayAccessoryRelUserChipFingerprint> {

	/**
	 * 根据用户id和芯片id查询所有指纹信息
	 *
	 * @return
	 * @date 2017年6月23日 下午17:56:56
	 */

	public List<WayAccessoryRelUserChipFingerprint> selectWayAccessoryRelUserChipFingerprintByChipIdUserId(
			@Param("userId") int userId, @Param("chipId") int chipId);

	/**
	 * 根据用户id芯片id和指纹位置查询信息
	 * 
	 * @param userId
	 * @param chipId
	 * @param location
	 * @return
	 */
	List<WayAccessoryRelUserChipFingerprint> selectByUserIdAndChipIdAndLocation(@Param("userId") Long userId,
			@Param("chipId") Long chipId, @Param("location") Integer location);

	/**
	 * 返回所有有效的指纹数量
	 * 
	 * @return
	 */
	int selectAllAmount();

	/**
	 * 返回每个类型的指纹数量
	 * 
	 * @return
	 */
	public List<Map<String, Object>> selectTpyeAllFingerprint();

	/**
	 * 某一用户所有注册指纹数量
	 * 
	 * @param phone
	 * @return
	 */
	public List<Integer> selectAllFingerprint(@Param("userId") Long userId);
}