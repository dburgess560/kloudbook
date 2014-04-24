package com.pippin;

public class Address {
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	
	public Address(){
		
	}
	
	public Address(String address1, String address2, String city, String state, String zip){
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public String toString(){
		String address = address1 + "\n" + address2 + "\n" + city + ", " + state + " " + zip;
		return address;
	}
}
