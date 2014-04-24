package com.pippin;

public class BusinessInfo extends UserInfo {

	private String company;

	public BusinessInfo(){
	}
	
	public BusinessInfo(Address address, PhoneNumber phoneNumber) {
		setAddress(address);
		phoneNumber(phoneNumber);
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
