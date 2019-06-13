package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 邮箱验证码日志表对应实体类.
 * 
 * @since 2018-08-13
 */
@TableName("way_accessory_log_mail_check")
public class WayAccessoryLogMailCheck extends Model<WayAccessoryLogMailCheck> {

	private static final long serialVersionUID = 1L;

	/**
	 * 邮箱验证码日志表id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 邮箱地址
	 */
	@TableField("mail_address")
	private String mailAddress;
	/**
	 * 邮箱验证码
	 */
	@TableField("iden_code")
	private String idenCode;
	/**
	 * 业务类型1.注册 2登录
	 */
	@TableField("trans_code")
	private String transCode;
	/**
	 * 添加时间
	 */
	@TableField("check_time")
	private Date checkTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getIdenCode() {
		return idenCode;
	}

	public void setIdenCode(String idenCode) {
		this.idenCode = idenCode;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
