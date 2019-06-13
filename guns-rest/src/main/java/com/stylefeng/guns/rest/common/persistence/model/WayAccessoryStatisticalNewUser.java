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
@TableName("way_accessory_statistical_new_user")
public class WayAccessoryStatisticalNewUser extends Model<WayAccessoryStatisticalNewUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 新增用户统表id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 统计日期
     */
	@TableField("check_date")
	private String checkDate;
    /**
     * 周期（1日,2周，3月）
     */
	@TableField("check_type")
	private Integer checkType;
    /**
     * 新增数量
     */
	@TableField("count_number")
	private Integer countNumber;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public Integer getCheckType() {
		return checkType;
	}

	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}

	public Integer getCountNumber() {
		return countNumber;
	}

	public void setCountNumber(Integer countNumber) {
		this.countNumber = countNumber;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
