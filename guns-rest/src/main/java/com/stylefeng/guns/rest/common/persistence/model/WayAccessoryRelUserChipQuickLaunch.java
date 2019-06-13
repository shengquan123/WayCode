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
@TableName("way_accessory_rel_user_chip_quick_launch")
public class WayAccessoryRelUserChipQuickLaunch extends Model<WayAccessoryRelUserChipQuickLaunch> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 设备指纹信息表id
     */
	@TableField("fingerprint_info_id")
	private Long fingerprintInfoId;
    /**
     * 类型：1应用程序，2网页，3文件
     */
	private Integer type;
    /**
     * 直达应用地址
     */
	@TableField("app_path")
	private String appPath;
    /**
     * 直达应用类型默认图标
     */
	@TableField("icon_defult")
	private String iconDefult;
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

	public Long getFingerprintInfoId() {
		return fingerprintInfoId;
	}

	public void setFingerprintInfoId(Long fingerprintInfoId) {
		this.fingerprintInfoId = fingerprintInfoId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAppPath() {
		return appPath;
	}

	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}

	public String getIconDefult() {
		return iconDefult;
	}

	public void setIconDefult(String iconDefult) {
		this.iconDefult = iconDefult;
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
