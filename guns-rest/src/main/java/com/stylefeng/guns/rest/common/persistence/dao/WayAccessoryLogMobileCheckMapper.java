package com.stylefeng.guns.rest.common.persistence.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryLogMobileCheck;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-06-22
 */
public interface WayAccessoryLogMobileCheckMapper extends BaseMapper<WayAccessoryLogMobileCheck> {
	/**
	 * 发送验证码
	 * 
	 * @param code
	 * @param phone
	 * @return
	 */
	public List<WayAccessoryLogMobileCheck> selectWayAccessoryLogMobileCheckByCodeAndPhone(@Param("code") String code,
			@Param("phone") String phone);

	/**
	 * 查询发送验证码条数
	 * 
	 * @param phone
	 * @return
	 */
	public int selectUserSendAmount(@Param("phone") String phone);

	/**
	 * 查询发送验证码总条数
	 * 
	 * @param phone
	 * @return
	 */
	public int selectAllUserSendAmount();

}