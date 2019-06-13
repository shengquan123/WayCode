package com.stylefeng.guns.rest.common.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryRelUserChip;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-06-22
 */
public interface WayAccessoryRelUserChipMapper extends BaseMapper<WayAccessoryRelUserChip> {
	/**
	 * 根据用户id和芯片id查询关系
	 *
	 * @return
	 * @date 2017年6月23日 下午17:56:56
	 */
	public List<WayAccessoryRelUserChip> selectByChipIdAndUserId(@Param("userId") int userId,
			@Param("chipId") int chipId);

	/**
	 * 通过userId查询左连接查询设备模型表相关信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> selectByUserId(@Param("userId") int userId);

}