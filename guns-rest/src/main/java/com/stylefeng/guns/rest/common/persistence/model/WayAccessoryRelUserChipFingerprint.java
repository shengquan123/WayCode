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
@TableName("way_accessory_rel_user_chip_fingerprint")
public class WayAccessoryRelUserChipFingerprint extends Model<WayAccessoryRelUserChipFingerprint> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户设备指纹关系表id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 设备表ID
     */
	@TableField("chip_id")
	private Long chipId;
    /**
     * 用户表ID
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 设备编号
     */
	@TableField("chip_number")
	private String chipNumber;
	
    /**
     * 芯片中指纹id
     */
	@TableField("chip_fp_id")
	private Integer chipFpId;
	
    /**
     * 指纹位置(1-28)
     */
	@TableField("fp_location")
	private Integer fpLocation;
    /**
     * 状态:0-无效;1-有效
     */
	private Integer status;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getChipNumber() {
		return chipNumber;
	}

	public void setChipNumber(String chipNumber) {
		this.chipNumber = chipNumber;
	}

	public Integer getFpLocation() {
		return fpLocation;
	}

	public void setFpLocation(Integer fpLocation) {
		this.fpLocation = fpLocation;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getChipFpId() {
		return chipFpId;
	}

	public void setChipFpId(Integer chipFpId) {
		this.chipFpId = chipFpId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
