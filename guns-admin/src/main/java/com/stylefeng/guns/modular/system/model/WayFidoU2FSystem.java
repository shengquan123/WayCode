package com.stylefeng.guns.modular.system.model;

import java.util.Date;

/**
 * @author shengquan
 */
public class WayFidoU2FSystem {

	private Integer id;
	private String systemName;
	private String systemDoMain;
	private Date addTime;
	private Date updateTime;

	@Override
	public String toString() {
		return "WayFidoU2FSystem [id=" + id + ", systemName=" + systemName + ", systemDoMain=" + systemDoMain
				+ ", addTime=" + addTime + ", updateTime=" + updateTime + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemDoMain() {
		return systemDoMain;
	}

	public void setSystemDoMain(String systemDoMain) {
		this.systemDoMain = systemDoMain;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
