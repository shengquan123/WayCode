package com.stylefeng.guns.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.modular.system.model.WayAccessoryStatisticalNewUser;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-08-17
 */
public interface WayAccessoryStatisticalNewUserMapper extends BaseMapper<WayAccessoryStatisticalNewUser> {
	/**
	 * 查询前一日新增用户
	 * 
	 * @param dayTime
	 * @return
	 */
	public int selectDayCount(@Param("dayTime") String dayTime);

	/**
	 * 查询前一周新增用户
	 * 
	 * @param dayTime
	 * @return
	 */
	public WayAccessoryStatisticalNewUser selectWeekCount(@Param("weekTime") String weekTime);

	/**
	 * 查询前一月新增用户
	 * 
	 * @param dayTime
	 * @return
	 */
	public WayAccessoryStatisticalNewUser selectMonthCount(@Param("monthTime") String monthTime);
	
	/**
	 * 历史天查询
	 * @return
	 */
	public List<WayAccessoryStatisticalNewUser> selectHAllDayCount();

	
	/**
	 * 历史天查询
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> selectHDayCount(@Param("startTime") String startTime,
			@Param("endTime") String endTime);

	/**
	 * 历史周查询
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> selectHWeekCount(@Param("startTime") String startTime,
			@Param("endTime") String endTime);

	/**
	 * 历史月查询
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> selectHMonthCount(@Param("startTime") String startTime,
			@Param("endTime") String endTime);

}