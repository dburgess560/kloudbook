package com.pippin.database;

public class FBOAuth {
	private String ID;
	private String secret;
	
	public FBOAuth(String appid, String appsecret){
		ID = appid;
		secret = appsecret;
	}
	
	public String getID(){
		return ID;
	}
	
	public String getSecret(){
		return secret;
	}
}
