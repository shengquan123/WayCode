package com.stylefeng.guns.rest.common.persistence.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryModelChip;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-06-22
 */
public interface WayAccessoryModelChipMapper extends BaseMapper<WayAccessoryModelChip> {

	/**
	 * 根据设备编号查询设备
	 *
	 * @return
	 * @date 2017年6月23日 下午17:56:56
	 */
	public List<WayAccessoryModelChip> selectWayAccessoryModelChipByChipNumber(@Param("chipNumber") String chipNumber);

	/**
	 * 通过chipid获得设备模型
	 * 
	 * @param chipNumber
	 * @param chipType
	 * @param status
	 * @return
	 */
	public WayAccessoryModelChip selectWayAccessoryModelChipByChipNumber(@Param("chipNumber") String chipNumber,
			@Param("chipType") int chipType, @Param("status") int status);

}