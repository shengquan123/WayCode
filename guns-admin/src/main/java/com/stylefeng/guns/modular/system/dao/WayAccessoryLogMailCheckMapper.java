package com.stylefeng.guns.modular.system.dao;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.modular.system.model.WayAccessoryLogMobileCheck;

public interface WayAccessoryLogMailCheckMapper extends BaseMapper<WayAccessoryLogMobileCheck> {

	/**
	 * 查询发送邮箱验证码条数
	 * 
	 * @param mail
	 * @return
	 */
	public int selectUserSendAmount(@Param("mail") String mail);
	/**
	 * 查询发送邮箱验证码总条数
	 * 
	 * @return
	 */
	public int selectAllUserSendAmount();
}
