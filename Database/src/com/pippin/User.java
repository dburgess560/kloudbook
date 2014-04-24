//dburgess
//Trying to get a more functional User class to use with GSON
package com.pippin;

import java.sql.Date;
import java.util.List;

public class User {
	// Use of builder pattern
	private int userid = -1;	//Because ints default to 0 instead of null, this forces -1 to return when a json is created
	private Login login;
	private String primaryEmail;
	private char gender;
	private BusinessInfo businessInfo;
	private HomeInfo homeInfo;
	private Name name;
	private String hometown;
	private List<String> high_school;
	private List<String> college;
	private PhoneNumber mobileNumber;
	private String picture;
	private Date registered;
	private Date birthday;
	private List<SocialMedia> socialMedia;
	private List<String> additionalEmails;
	private List<PhoneNumber> additionalNumbers;
//	private List<Notifications> notifications;

	//
	private User(UserBuilder b) {
		this.userid = b.userid;
		this.login = b.login;
		this.primaryEmail = b.primaryEmail;
		this.gender = b.gender;
		this.businessInfo = b.businessInfo;
		this.homeInfo = b.homeInfo;
		this.name = b.name;
		this.hometown = b.hometown;
		this.high_school = b.high_school;
		this.college = b.college;
		this.mobileNumber = b.mobileNumber;
		this.picture = b.picture;
		this.registered = b.registered;
		this.birthday = b.birthday;
		this.socialMedia = b.socialMedia;
		this.additionalEmails = b.additionalEmails;
		this.additionalNumbers = b.additionalNumbers;
//		this.notifications = b.notifications;
	}

	public int getUserid() {
		return userid;
	}
	
	public String getPrimaryEmail() {
		if(primaryEmail==null) return login.getEmail();
		else return primaryEmail;
	}
	
	public String getPassword(){
		if(login!=null) return login.getPassword();
		else return null;
	}
	
	public char getGender() {
		return gender;
	}

	public BusinessInfo getBusinessInfo() {
		return businessInfo;
	}

	public HomeInfo getHomeInfo() {
		return homeInfo;
	}

	public Name getName() {
		return name;
	}
	public String getHometown() {
		return hometown;
	}

	public List<String> getHighSchool() {
		return high_school;
	}
	
	public List<String> getCollege() {
		return college;
	}
	
	public PhoneNumber getMobileNumber() {
		return mobileNumber;
	}
	
	public String getPicture() {
		return picture;
	}

	public Date getRegistered() {
		return registered;
	}
	public Date getBirthday() {
		return birthday;
	}

	public List<SocialMedia> getSocialMedia() {
		return socialMedia;
	}

	public List<String> getAdditionalEmails() {
		return additionalEmails;
	}

	public List<PhoneNumber> getAdditionalNumbers() {
		return additionalNumbers;
	}
	
//	public List<Notifications> getNotifications() {
//		return notifications;
//	}

	public static class UserBuilder {
		private int userid = -1;
		private Login login;
		private String primaryEmail;
		private char gender;
		private BusinessInfo businessInfo = new BusinessInfo(new Address(), new PhoneNumber());
		private HomeInfo homeInfo = new HomeInfo(new Address(), new PhoneNumber());
		private Name name = new Name();
		private String hometown;
		private List<String> high_school;
		private List<String> college;
		private PhoneNumber mobileNumber = new PhoneNumber();
		private String picture;
		private Date registered;
		private Date birthday;
		private List<SocialMedia> socialMedia;
		private List<String> additionalEmails;
		private List<PhoneNumber> additionalNumbers;
//		private List<Notifications> notifications = new LinkedList<Notifications>();
		
		public UserBuilder userid(int userid) {
			this.userid = userid;
			return this;
		}
		
		public UserBuilder primaryEmail(String primaryEmail) {
			this.primaryEmail = primaryEmail;
			return this;
		}
		
		public UserBuilder login(Login login){
			this.login = login;
			return this;
		}
		
		public UserBuilder gender(String gender) {
			this.gender = gender.charAt(0);
			return this;
		}
		
		public UserBuilder gender(char gender) {
			this.gender = gender;
			return this;
		}

		public UserBuilder businessInfo(BusinessInfo businessInfo) {
			this.businessInfo = businessInfo;
			return this;
		}

		public UserBuilder homeInfo(HomeInfo homeInfo) {
			this.homeInfo = homeInfo;
			return this;
		}
		
		public UserBuilder name(Name name) {
			this.name = name;
			return this;
		}
		
		public UserBuilder hometown(String hometown) {
			this.hometown = hometown;
			return this;
		}

		public UserBuilder highSchool(List<String> high_school) {
			this.high_school = high_school;
			return this;
		}
		
		public UserBuilder college(List<String> college) {
			this.college = college;
			return this;
		}

		public UserBuilder mobileNumber(PhoneNumber mobileNumber) {
			this.mobileNumber = mobileNumber;
			return this;
		}

		public UserBuilder picture(String picture) {
			this.picture = picture;
			return this;
		}

		public UserBuilder registered(Date registered) {
			this.registered = registered;
			return this;
		}

		public UserBuilder birthday(Date birthday) {
			this.birthday = birthday;
			return this;
		}

		public UserBuilder socialMedia(List<SocialMedia> socialMedia) {
			this.socialMedia = socialMedia;
			return this;
		}

		public UserBuilder additionalEmails(List<String> additionalEmails) {
			this.additionalEmails = additionalEmails;
			return this;
		}

		public UserBuilder additionalNumbers(List<PhoneNumber> additionalNumbers) {
			this.additionalNumbers = additionalNumbers;
			return this;
		}
		
//		public UserBuilder notifications(List<Notifications> notifications) {
//			this.notifications = notifications;
//			return this;
//		}

		public User build() {
			return new User(this);
		}

}
}
