package com.pippin.demo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.pippin.Access;
import com.pippin.Address;
import com.pippin.BusinessInfo;
import com.pippin.HomeInfo;
import com.pippin.Login;
import com.pippin.Name;
import com.pippin.PhoneNumber;
import com.pippin.SocialMedia;
import com.pippin.User;
import com.pippin.User.UserBuilder;
import com.pippin.database.DBConnection;

public class ItemCreator {
	public ItemCreator(){}
	
	public UserBuilder createJim(){
		Login login = new Login("jim@gmail.com", "password");
		Address busAddress =  new Address("72 Bart Street", "Building 2", "Worcester", "Massachusetts", "12345");
		Address homeAddress =  new Address("18 Chestnut Ave", "Apt 7", "Amherst", "Massachusetts", "01002");
		PhoneNumber busNum = new PhoneNumber("5087745081", "1", "2");
		PhoneNumber homeNum = new PhoneNumber("4137745081", "1", "700");
		PhoneNumber mobileNum = new PhoneNumber("5081231247", "", "");
		PhoneNumber addNum = new PhoneNumber("5553331234", "", "");
		BusinessInfo businessInfo = new BusinessInfo();
		businessInfo.setAddress(busAddress);
		businessInfo.setCompany("Bobs Discount Furniture");
		businessInfo.phoneNumber(busNum);
		HomeInfo homeInfo = new HomeInfo();
		homeInfo.setAddress(homeAddress);
		homeInfo.setCurrentCity("Amherst");
		homeInfo.phoneNumber(homeNum);
		Name name = new Name();
		name.setFirstName("Jim");
		name.setMiddleName("Bob");
		name.setLastName("Marshal");
		List<String> high_school = new LinkedList<String>();
		high_school.add("ARHS");
		high_school.add("Amherst High");
		List<String> college = new LinkedList<String>();
		college.add("MIT");
		college.add("UMass Amherst");
		Date date = new Date(System.currentTimeMillis());
		
		List<SocialMedia> socialMedia = new LinkedList<SocialMedia>();
		SocialMedia sm = new SocialMedia();
		sm.setSocialMediaURL("http://www.google.com/");
		socialMedia.add(sm);
		socialMedia.add(sm);
		List<String> additionalEmails = new LinkedList<String>();
		additionalEmails.add("jimbob@gmail.com");
		additionalEmails.add("JMarsh@bobsfurniture.com");
		List<PhoneNumber> additionalNumbers = new LinkedList<PhoneNumber>();
		additionalNumbers.add(addNum);
		additionalNumbers.add(addNum);
		
		return new User.UserBuilder()
				.login(login)
				.gender("M")
				.businessInfo(businessInfo)
				.homeInfo(homeInfo)
				.name(name)
				.hometown("Quincy")
				.highSchool(high_school)
				.college(college)
				.mobileNumber(mobileNum)
				.registered((java.sql.Date) date)
				.birthday((java.sql.Date) date)
				.socialMedia(socialMedia)
				.additionalEmails(additionalEmails)
				.additionalNumbers(additionalNumbers);
	}
	
	public UserBuilder createJulie(){
		Login login = new Login("julie@gmail.com", "password");
		Address busAddress =  new Address("", "", "Hartford", "Connecticut", "01669");
		Address homeAddress =  new Address("12 Bartlett Street", "", "Framingham", "Massachusetts", "01532");
		PhoneNumber busNum = new PhoneNumber("", "", "");
		PhoneNumber homeNum = new PhoneNumber("5087761247", "", "");
		PhoneNumber mobileNum = new PhoneNumber("5089799508", "", "");
		PhoneNumber addNum = new PhoneNumber("8006769100", "", "756");
		BusinessInfo businessInfo = new BusinessInfo();
		businessInfo.setAddress(busAddress);
		businessInfo.setCompany("");
		businessInfo.phoneNumber(busNum);
		HomeInfo homeInfo = new HomeInfo();
		homeInfo.setAddress(homeAddress);
		homeInfo.setCurrentCity("Framingham");
		homeInfo.phoneNumber(homeNum);
		Name name = new Name();
		name.setFirstName("Julie");
		name.setMiddleName("");
		name.setLastName("Bushels");
		List<String> high_school = new LinkedList<String>();
		high_school.add("Westborough High");
		List<String> college = new LinkedList<String>();
		college.add("Framingham State");
		Date date = new Date(System.currentTimeMillis());
		
		List<SocialMedia> socialMedia = new LinkedList<SocialMedia>();
		SocialMedia sm = new SocialMedia();
		sm.setSocialMediaURL("http://www.facebook.com/");
		socialMedia.add(sm);
		socialMedia.add(sm);
		List<String> additionalEmails = new LinkedList<String>();
		additionalEmails.add("jbushels@gmail.com");
		List<PhoneNumber> additionalNumbers = new LinkedList<PhoneNumber>();
		additionalNumbers.add(addNum);
		
		return new User.UserBuilder()
				.login(login)
				.gender("F")
				.businessInfo(businessInfo)
				.homeInfo(homeInfo)
				.name(name)
				.hometown("Westborough")
				.highSchool(high_school)
				.college(college)
				.mobileNumber(mobileNum)
				.registered((java.sql.Date) date)
				.birthday((java.sql.Date) date)
				.socialMedia(socialMedia)
				.additionalEmails(additionalEmails)
				.additionalNumbers(additionalNumbers);
	}
	
	public void deleteUser(User user){
		DBConnection db= DBConnection.getInstance();
		try{
			Connection conn = db.getConnection();
			conn.createStatement()
			.executeUpdate("DELETE FROM users WHERE primary_email='"
					+ user.getPrimaryEmail() +"';");
			conn.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public Access newAccess(int user1, int user2){
		Access newAccess = new Access();
		newAccess.user1_id = user1;
		newAccess.user2_id = user2;
		newAccess.primaryEmail = false;
		newAccess.gender = true;
		newAccess.mobileNumber = false;
		newAccess.picture = true;
		newAccess.birthday = false;
		newAccess.businessAddress = true;
		newAccess.businessNumber = false;
		newAccess.homeAddress = true;
		newAccess.homeNumber = false;
		newAccess.socialMedia = true;
		newAccess.additionalEmails = false;
		newAccess.additionalNumbers = true;
		return newAccess;
	}
}
