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
@TableName("way_accessory_model_user")
public class WayAccessoryModelUser extends Model<WayAccessoryModelUser> {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户信息表id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 手机号
	 */
	@TableField("phone_no")
	private String phoneNo;
	/**
	 * 用户名
	 */
	@TableField("account_name")
	private String accountName;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 昵称
	 */
	@TableField("nick_name")
	private String nickName;
	/**
	 * 性别：0 默认 1男 2女 3 其他
	 */
	@TableField("sex_flag")
	private Integer sexFlag;
	/**
	 * 状态:0-无效;1-有效
	 */
	private Integer status;
	/**
	 * ip地址
	 */
	@TableField("ip_address")
	private String ipAddress;
	/**
	 * 所在城市
	 */
	private String city;
	/**
	 * 所在省
	 */
	private String privince;
	/**
	 * 加密文件数量
	 */
	private Integer number;
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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getSexFlag() {
		return sexFlag;
	}

	public void setSexFlag(Integer sexFlag) {
		this.sexFlag = sexFlag;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPrivince() {
		return privince;
	}

	public void setPrivince(String privince) {
		this.privince = privince;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
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
