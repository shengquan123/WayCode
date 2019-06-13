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
@TableName("way_accessory_log_windows_version")
public class WayAccessoryLogWindowsVersion extends Model<WayAccessoryLogWindowsVersion> {

    private static final long serialVersionUID = 1L;

    /**
     * windows版本表id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * Windows版本1.win7，2.win8 ,3.win8.1,4.win10
     */
	private Integer version;
    /**
     * datetime
     */
	@TableField("create_time")
	private Date createTime;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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
