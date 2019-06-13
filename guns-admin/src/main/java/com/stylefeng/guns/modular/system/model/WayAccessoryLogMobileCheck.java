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
@TableName("way_accessory_log_mobile_check")
public class WayAccessoryLogMobileCheck extends Model<WayAccessoryLogMobileCheck> {

    private static final long serialVersionUID = 1L;

    /**
     * 手机验证码日志表id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 手机号码
     */
	@TableField("phone_no")
	private String phoneNo;
    /**
     * 验证码
     */
	@TableField("iden_code")
	private String idenCode;
    /**
     * 交易类型1.注册 2登录
     */
	@TableField("trans_code")
	private String transCode;
    /**
     * 添加时间
     */
	@TableField("check_time")
	private Date checkTime;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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
