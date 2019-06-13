package com.stylefeng.guns.rest.way.model;

public class ResponseUserInit {

	private String chipNumber;
	private String phone;

	public String getChipNumber() {
		return chipNumber;
	}

	public void setChipNumber(String chipNumber) {
		this.chipNumber = chipNumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "ResponseLoginByphone [chipNumber=" + chipNumber + ", phone=" + phone + "]";
	}
}
