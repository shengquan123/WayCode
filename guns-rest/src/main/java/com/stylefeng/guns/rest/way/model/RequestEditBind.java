package com.stylefeng.guns.rest.way.model;

public class RequestEditBind {
		//指纹位置
		private int fpId;
		//修改对的类型
		private int type;
		//修改的地址
		private String url;
		
		public int getFpId() {
			return fpId;
		}
		public void setFpId(int fpId) {
			this.fpId = fpId;
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
		
}
