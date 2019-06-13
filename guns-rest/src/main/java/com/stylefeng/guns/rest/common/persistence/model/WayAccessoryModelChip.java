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
@TableName("way_accessory_model_chip")
public class WayAccessoryModelChip extends Model<WayAccessoryModelChip> {

	private static final long serialVersionUID = 1L;

	/**
	 * 设备信息表id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 设备编号
	 */
	@TableField("chip_number")
	private String chipNumber;
	/**
	 * 设备品牌
	 */
	@TableField("chip_brand")
	private String chipBrand;
	/**
	 * 设备类型: 1-NORMAL, 2-SAFE
	 */
	@TableField("chip_type")
	private Integer chipType;
	/**
	 * 状态:0-无效;1-有效
	 */
	private Integer status;
	/**
	 * 添加时间
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

	public String getChipNumber() {
		return chipNumber;
	}

	public void setChipNumber(String chipNumber) {
		this.chipNumber = chipNumber;
	}

	public String getChipBrand() {
		return chipBrand;
	}

	public void setChipBrand(String chipBrand) {
		this.chipBrand = chipBrand;
	}

	public Integer getChipType() {
		return chipType;
	}

	public void setChipType(Integer chipType) {
		this.chipType = chipType;
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

}
