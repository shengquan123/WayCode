package com.stylefeng.guns.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.modular.system.model.WayAccessoryLogUserAction;

/**
 * <p>
 * Mapper 接口
 * </p>
 * ,
 *
 * @author stylefeng
 * @since 2017-08-17
 */
public interface WayAccessoryLogUserActionMapper extends BaseMapper<WayAccessoryLogUserAction> {
	public WayAccessoryLogUserAction selectWayAccessoryLogUserActionByUserBy(@Param("type") int type,
			@Param("checkDate") String checkDate, @Param("userId") int userId);

	/**
	 * 获取昨日用户行为数据
	 * @param yesterday
	 * @return
	 */
	public List<Map<String, Object>> selectYesterdayAmount(@Param("yesterday") String yesterday);

	/**
	 *  获取上周用户行为数据
	 * @param lastWeek
	 * @return
	 */
	public List<Map<String, Object>> selectLastWeekAmount(@Param("lastWeek") String lastWeek);

	/**
	 *  获取上个月用户行为数据
	 * @param LastMonth
	 * @return
	 */
	public List<Map<String, Object>> selectLastMonthAmount(@Param("LastMonth") String LastMonth);
/**
 * 获得加解密次数
 * @param userId
 * @return
 */
	public int selectEncryptAmount(@Param("userId") Long userId);
	/**
	 * 获取昨日人数
	 * @return
	 */
	public List<Integer> activeYesterdayAmount(@Param("dayTime") String dayTime );
	/**
	 * 获取前周活跃用户
	 * @return
	 */
	public List<Integer> activeLastWeekAmount( );
	/**
	 * 获取月周期用户
	 * @return
	 */
	public List<Integer> activeLastMonthAmount( );
	
	/**
	 * 获取今日用户行为数据
	 * @return
	 */
	public List<Map<String,Object>> selectDayAmount(@Param("dayTime") String dayTime);
	/**
	 * 获取这周用户行为数据
	 * @return
	 */
	public List<Map<String,Object>> selectWeekAmount();
	/**
	 * 获取这月用户行为数据
	 * @return
	 */
	public List<Map<String,Object>> selectMonthAmount();
	
	/**
	 * 获取今日用户行为数据
	 * @return
	 */
	public List<Map<String,Object>> selectDayAvgAmount(@Param("startTime") String startTime,@Param("endTime") String endTime);


}