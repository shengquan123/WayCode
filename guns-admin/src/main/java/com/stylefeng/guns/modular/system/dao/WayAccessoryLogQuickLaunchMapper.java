package com.stylefeng.guns.modular.system.dao;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.modular.system.model.WayAccessoryLogQuickLaunch;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-08-17
 */
public interface WayAccessoryLogQuickLaunchMapper extends BaseMapper<WayAccessoryLogQuickLaunch> {
	/**
	 * 获得网页直达排行榜
	 * 
	 * @return
	 */
	public List<WayAccessoryLogQuickLaunch> selectWebSiteType();

	/**
	 * 获得网页app排行榜
	 * 
	 * @return
	 */
	public List<WayAccessoryLogQuickLaunch> selectAppType();

	/**
	 * 查询app/网页是否在
	 * 
	 * @param appUrl
	 * @return
	 */
	public WayAccessoryLogQuickLaunch selectAppWeb(@RequestParam("appUrl") String appUrl);

	/**
	 *  所有app的个数
	 * @return
	 */
	public int selectAmountAppAll();

	/**
	 *  所有web的个数
	 * @return
	 */
	public int selectAmountWebAll();

}