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
@TableName("way_accessory_statistical_user_action")
public class WayAccessoryStatisticalUserAction extends Model<WayAccessoryStatisticalUserAction> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户行为记录id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 统计类型：（1日、2周、3月）
     */
	@TableField("check_type")
	private Integer checkType;
    /**
     * 统计日期：(月：2017-01、周和日：2017-01-01)
     */
	@TableField("check_date")
	private String checkDate;
    /**
     * 行为记录类型：1.客户端启动 2.用户手机登录 3.用户退出 4.客户端切换帐号 5.指纹登录/解锁6.文件加解密 7.网页/应用快捷直达点击  8.应用/网站免密登录 9.使用小贴士10.帮助中心11.客户端退出。12.用户注册
     */
	private Integer type;
    /**
     * 次数
     */
	private Integer count;
    /**
     * create_time
     */
	@TableField("create_time")
	private Date createTime;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCheckType() {
		return checkType;
	}

	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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
