package com.pippin;

public class UserInfo {

	private Address address;
	private PhoneNumber phoneNumber;

	public UserInfo() {

	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public void phoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
