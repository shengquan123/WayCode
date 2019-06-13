package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 版本表
 * 
 * @author shengquan
 * @since 2018-02-27
 */
@TableName("way_accessory_software_version")
public class WayAccessorySoftwareVersion extends Model<WayAccessorySoftwareVersion> {

	private static final long serialVersionUID = 1L;

	/**
	 * 1.主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 2.软件类型（1:客户端App-SAFE,2:客户端App-NORMAL,3:设备固件-SAFE,4:设备固件-NORMAL1,5:设备固件-
	 * NORMAL3）
	 */
	private Integer type;
	/**
	 * 3.版本名称（1.0）
	 */
	private String versionName;
	/**
	 * 4.版本号（12）
	 */
	private Integer versionNumber;
	/**
	 * 5.文件校验码（最大256）
	 */
	private String fileCheckCode;
	/**
	 * 6.下载地址
	 */
	private String downloadPath;
	/**
	 * 7.上传发布时间
	 */
	private Date publishTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public Integer getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getFileCheckCode() {
		return fileCheckCode;
	}

	public void setFileCheckCode(String fileCheckCode) {
		this.fileCheckCode = fileCheckCode;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	@Override
	public String toString() {
		return "WayAccessorySoftwareVersion [id=" + id + ", type=" + type + ", versionName=" + versionName
				+ ", versionNumber=" + versionNumber + ", fileCheckCode=" + fileCheckCode + ", downloadPath="
				+ downloadPath + ", publishTime=" + publishTime + "]";
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
