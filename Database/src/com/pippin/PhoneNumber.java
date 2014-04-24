package com.pippin;

public class PhoneNumber {

	private String number;
	private String extension;
	private String country_code;
	
	public PhoneNumber() {

	}

	public PhoneNumber(String num, String ext, String cc){
		number = num;
		extension = ext;
		country_code = cc;
	}
	
	public String toString(){
		String phone = number;
		if(extension != null) phone += " x" + extension;
		if(country_code != null) phone = "+" + country_code + phone;
		return phone;
	}
	
	public String getNumber(){
		return number;
	}
	
	public String getExt(){
		return extension;
	}
	
	public String getCC(){
		return country_code;
	}
}
