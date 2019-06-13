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
@TableName("way_accessory_log_quick_launch")
public class WayAccessoryLogQuickLaunch extends Model<WayAccessoryLogQuickLaunch> {

    private static final long serialVersionUID = 1L;

    /**
     * 直达表直达记录表id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 1直达网站的域名、2直达应用
     */
	private Integer type;
    /**
     * 直达网站的域名（qq.com）
     */
	@TableField("site_url")
	private String siteUrl;
    /**
     * app_url
     */
	@TableField("app_url")
	private String appUrl;
    /**
     * 绑定个数
     */
	private Integer number;
    /**
     * 占所有绑定网址百分比
     */
	private Float percent;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Float getPercent() {
		return percent;
	}

	public void setPercent(Float percent) {
		this.percent = percent;
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
