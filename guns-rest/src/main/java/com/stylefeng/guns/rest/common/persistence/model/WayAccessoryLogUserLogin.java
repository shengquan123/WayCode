package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2017-09-13
 */
@TableName("way_accessory_log_user_login")
public class WayAccessoryLogUserLogin extends Model<WayAccessoryLogUserLogin> {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户登录日志表id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 用户表id
	 */
	@TableField("user_id")
	private Long userId;
	/**
	 * 设备表id 默认0
	 */
	@TableField("chip_id")
	private Long chipId;
	/**
	 * 登录APP类型：1SAFE，2NORMAL
	 */
	@TableField("log_app_type")
	private Integer logAppType;
	/**
	 * 登录类型：1手机验证登录，2本地指纹登录，3邮箱登录
	 */
	@TableField("log_type")
	private Integer logType;
	/**
	 * 是否成功：1成功，0失败
	 */
	private Integer status;
	/**
	 * 登录时间
	 */
	@TableField("log_time")
	private Date logTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getChipId() {
		return chipId;
	}

	public void setChipId(Long chipId) {
		this.chipId = chipId;
	}

	public Integer getLogAppType() {
		return logAppType;
	}

	public void setLogAppType(Integer logAppType) {
		this.logAppType = logAppType;
	}

	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
