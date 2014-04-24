//dburgess

package com.pippin;

public class Login {
	private String primary_email="";
	private String password="";
	
	//For testing
	public Login(String email, String pass){
		primary_email=email;
		password=pass;
	}
	
	public String getEmail(){
		return primary_email;
	}
	
	public String getPassword(){
		String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
		return sha256hex;
	}
}
