package com.stylefeng.guns.modular.system.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.modular.system.model.WayAccessoryLogWindowsVersion;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-08-17
 */
public interface WayAccessoryLogWindowsVersionMapper extends BaseMapper<WayAccessoryLogWindowsVersion> {

	/**
	 * 查询版本各版本数量
	 * 
	 * @return
	 */
	public List<Map<String, Object>> selectTpyeAllVersion();

	/**
	 * 查询所有数量
	 * 
	 * @return
	 */
	public int selectAllAmount();

}