package com.pippin;

import org.apache.commons.validator.routines.UrlValidator;

public class SocialMedia {
	private String socialMediaURL;
	
	public SocialMedia(){
	}

	public SocialMedia(String url){
		setSocialMediaURL(url);
	}

	public String getSocialMediaURL() {
		return socialMediaURL;
	}

	public void setSocialMediaURL(String url) {
		if (validUrl(url)) {
			this.socialMediaURL = url;
		} else {
			//Invalid url
		}
	}
	
	private boolean validUrl(String url){
		String[] schemes = {"http","https"}; // DEFAULT schemes = "http", "https", "ftp"
		UrlValidator urlValidator = new UrlValidator(schemes);
		return urlValidator.isValid(url);
	}
}
