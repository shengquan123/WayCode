package com.stylefeng.guns.rest.common.persistence.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryLogFingerprintCapture;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-06-22
 */
public interface WayAccessoryLogFingerprintCaptureMapper extends BaseMapper<WayAccessoryLogFingerprintCapture> {
	/**
	 * 根据设备id查询记录信息
	 *
	 * @return
	 * @date 2017年6月23日 下午17:56:56
	 */

	List<WayAccessoryLogFingerprintCapture> selectByChipId(@Param("chipId") Long chipId);
}