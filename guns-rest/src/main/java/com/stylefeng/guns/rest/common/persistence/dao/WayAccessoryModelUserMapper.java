package com.stylefeng.guns.rest.common.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryModelUser;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-06-22
 */
public interface WayAccessoryModelUserMapper extends BaseMapper<WayAccessoryModelUser> {
	// 获取用户信息
	public List<WayAccessoryModelUser> selectWayAccessoryModelUserByPhone(@Param("phone") String phone,
			@Param("status") int status);

	public List<WayAccessoryModelUser> selectWayAccessoryModelUserByEmail(@Param("mail") String mail,
			@Param("status") int status);

	/**
	 * 获得某天新增人数
	 * 
	 * @param dayTime
	 * @return
	 */
	public int selectDayCount(@Param("dayTime") String dayTime);

	/**
	 * 获得本周新增人数
	 * 
	 * @return
	 */
	public int selectWeekCount();

	/**
	 * 本月新增人数
	 * 
	 * @return
	 */
	public int selectMonthCount();

	/**
	 * 获得用户地区分布
	 * 
	 * @return
	 */
	public List<Map<String, Object>> selectArea();

	/**
	 * 获得所有用户数量
	 * 
	 * @return
	 */
	public Integer selectAllAmount();

	/**
	 * 获取昨日新增用户数量
	 */
	public Integer selectYesterdayAmount(@Param("yesterday") String yesterday);

	/**
	 * 获取上周所有新增用户数量
	 * 
	 * @param LastWeek
	 * @return
	 */
	public int selectLastWeekAmount(@Param("LastWeek") String LastWeek);

	/**
	 * 获取上个月所有新增用户数量
	 * 
	 * @param LastMonth
	 * @return
	 */
	public int selectLastMonthAmount(@Param("LastMonth") String LastMonth);

	// /**
	// * 分页获取用户
	// *
	// * @param num
	// * @param pageNum
	// * @return
	// */
	// public List<WayAccessoryModelUser> selectNewUser(@Param("num") int num,
	// @Param("pageNum") int pageNum);

	/**
	 * 获取用户
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<WayAccessoryModelUser> selectNewUser(@Param("startTime") String startTime,
			@Param("endTime") String endTime);

	// /**
	// * 模糊查询分页获取用户
	// *
	// * @param key
	// * @param num
	// * @param pageNum
	// * @return
	// */
	// public List<WayAccessoryModelUser> selectKeyNewUser(@Param("key") String
	// key, @Param("nu") Integer nu,
	// @Param("pageNum") Integer pageNum);
	/**
	 * 时间段内模糊查询
	 * 
	 * @param key
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<WayAccessoryModelUser> selectKeyNewUser(@Param("key") String key, @Param("startTime") String startTime,
			@Param("endTime") String endTime);

	/**
	 * 获得某天新增人数
	 * 
	 * @param dayTime
	 * @return
	 */
	public int selectDayActiveCount(@Param("dayTime") String dayTime);

	/**
	 * 获得本周新增人数
	 * 
	 * @return
	 */
	public int selectWeekActiveCount();

	/**
	 * 本月新增人数
	 * 
	 * @return
	 */
	public int selectMonthActiveCount();

}