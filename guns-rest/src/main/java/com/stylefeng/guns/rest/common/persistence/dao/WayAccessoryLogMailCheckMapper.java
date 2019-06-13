package com.stylefeng.guns.rest.common.persistence.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryLogMailCheck;

public interface WayAccessoryLogMailCheckMapper extends BaseMapper<WayAccessoryLogMailCheck> {

	/**
	 * @param code
	 * @param mail
	 * @return
	 */
	List<WayAccessoryLogMailCheck> selectWayAccessoryLogMailCheckByCodeAndMail(@Param("code") String code,
			@Param("mail") String mail);

	/**
	 * @param mail
	 * @return
	 */
	Integer selectByMail(@Param("mail") String mail);

}
