package com.pippin;

public class HomeInfo extends UserInfo {
	private String currentCity;

	public HomeInfo() {
	}
	
	public HomeInfo(Address address, PhoneNumber phoneNumber) {
		setAddress(address);
		phoneNumber(phoneNumber);
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

}
