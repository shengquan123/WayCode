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
@TableName("way_accessory_log_fingerprint_capture")
public class WayAccessoryLogFingerprintCapture extends Model<WayAccessoryLogFingerprintCapture> {

    private static final long serialVersionUID = 1L;

    /**
     * 设备指纹采集日志表id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 设备表id
     */
	@TableField("chip_id")
	private Long chipId;
    /**
     * 设备编号
     */
	@TableField("chip_number")
	private String chipNumber;
    /**
     * 注册成功次数
     */
	@TableField("reg_succ_time")
	private Integer regSuccTime;
    /**
     * 注册失败次数
     */
	@TableField("reg_fail_time")
	private Integer regFailTime;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private Date updateTime;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getChipId() {
		return chipId;
	}

	public void setChipId(Long chipId) {
		this.chipId = chipId;
	}

	public String getChipNumber() {
		return chipNumber;
	}

	public void setChipNumber(String chipNumber) {
		this.chipNumber = chipNumber;
	}

	public Integer getRegSuccTime() {
		return regSuccTime;
	}

	public void setRegSuccTime(Integer regSuccTime) {
		this.regSuccTime = regSuccTime;
	}

	public Integer getRegFailTime() {
		return regFailTime;
	}

	public void setRegFailTime(Integer regFailTime) {
		this.regFailTime = regFailTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
