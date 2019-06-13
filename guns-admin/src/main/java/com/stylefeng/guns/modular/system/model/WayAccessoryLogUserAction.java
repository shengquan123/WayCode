package com.stylefeng.guns.modular.system.model;

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
 * @since 2017-08-17
 */
@TableName("way_accessory_log_user_action")
public class WayAccessoryLogUserAction extends Model<WayAccessoryLogUserAction> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户行为记录表 
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 用户id
     */
	@TableField("user_id")
	private Long userId;
    /**
     * check_date
     */
	@TableField("check_date")
	private String checkDate;
    /**
     * 行为记录类型：1.客户端启动2.用户注册 3.用户手机登录 4.用户指纹登录/解锁 5.用户退出6.客户端切换帐号 7.文件加解密 8.网页/应用快捷直达点击  9.应用/网站免密登录 10.使用小贴士11.帮助中心12.客户端退出
     */
	private Integer type;
    /**
     * 次数
     */
	private Long count;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
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
