package com.stylefeng.guns.modular.restapi.model;

/**
 * 直达信息
 * 
 * @author lori
 *
 */
public class QuickLaunchModel {
	// 直达id
	private Integer quickId;
	// 直达类型
	private int type;
	// 直达的地址
	private String url;
	// 直达应用类型默认图标
	private String img_url;

	public Integer getQuickId() {
		return quickId;
	}

	public void setQuickId(Integer quickId) {
		this.quickId = quickId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

}
