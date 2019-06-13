package com.stylefeng.guns.modular.system.transfer;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 版本传输bean
 * 
 * @author shengquan
 * @Date 2018/03/05 22:40
 */
public class VersionDto {

	private Integer id;
	private Integer softType;
	private String versionName;
	private Integer versionNumber;
	private String fileCheckCode;
	private String fileName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date publishTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSoftType() {
		return softType;
	}

	public void setSoftType(Integer softType) {
		this.softType = softType;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	@Override
	public String toString() {
		return "VersionDto [id=" + id + ", softType=" + softType + ", versionName=" + versionName + ", versionNumber="
				+ versionNumber + ", fileCheckCode=" + fileCheckCode + ", fileName=" + fileName + ", publishTime="
				+ publishTime + "]";
	}
}
