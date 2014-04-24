//dburgess
//Test class for the DB connection object

package com.pippin.database;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.pippin.Address;
import com.pippin.BusinessInfo;
import com.pippin.HomeInfo;
import com.pippin.Login;
import com.pippin.Name;
import com.pippin.PhoneNumber;
import com.pippin.SocialMedia;
import com.pippin.User;

public class DemoOrDie {
	private static Profile profile = new Profile();
	
	public static void main(String[] args) throws SQLException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		//JimBob Being deleted
		DBConnection db= DBConnection.getInstance();
		db.getConnection().createStatement().executeUpdate("DELETE FROM users WHERE primary_email='jimbob@ymail.com'");
		
		//JimBob getting a login and a user
		Login jimsLogin = new Login("jimbob@ymail.com", "test");
		User jim = createJim(jimsLogin);
		
		//Jim being added to the DB and his new user_id
		int jims_ID = Profile.createAccount(jim);
		
		//Run a search for anyone with the name Dave
		Search searcher = new Search();
		LinkedList<Integer> ids = searcher.basicSearch("Dave", -1);
		
		//Printout emails of those with the name Dave
		for(int id: ids){
			User user = Profile.findUser(id, jims_ID);
			System.out.println("Results email is: " + user.getPrimaryEmail());
		}
		
		//Deactivate jmarsh
		Login testDeactivate = new Login("jmarsh@gmail.com", "1234");
		profile.deactivate(testDeactivate);
		
		//Reactivate jsmarsh
		Profile.login(testDeactivate);
		db.closeConnection();
		System.exit(1);
		
		/*
		String sqlCreate = "CALL DROP_THE_BASE()";
		DBConnection db = DBConnection.getInstance();
		CallableStatement cstmt = db.prepareQuery(sqlCreate);
		cstmt.execute();
		cstmt.close();
		*/
		
		
		

		
		/*
		Gson gson = new Gson();
		
		Writer writer = new FileWriter("C:\\Users\\David\\Dropbox\\School\\"
				+ "Programs\\KloudBook\\Documents\\Pippin\\API\\FakeJsons\\jim_out.txt");
		gson.toJson(jim, writer);
		writer.close();
		
		File bob2 = new File("C:\\Users\\David\\Dropbox\\School\\"
				+ "Programs\\KloudBook\\Documents\\Pippin\\API\\FakeJsons\\jim_out.txt");
		FileInputStream fis = new FileInputStream(bob2);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader bufferedReader = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
		}
		String json = sb.toString();
		
		User bob = gson.fromJson(json, User.class);
		System.out.println(bob);
		
		Gson gson = new Gson();
		
		Writer writer = new FileWriter("C:\\Users\\David\\Dropbox\\School\\"
				+ "Programs\\KloudBook\\Documents\\Pippin\\API\\FakeJsons\\newJim.txt");
		gson.toJson(dave, writer);
		
		writer.close();
		test.deactivate(testDeactivate);
		*/
	}
	
	public static User createJim(Login login){
		Address address =  new Address("72 Bart Street", "Apt 2", "City", "State", "12345");
		PhoneNumber num = new PhoneNumber("4137745081", "17", "");
		BusinessInfo businessInfo = new BusinessInfo();
		businessInfo.setAddress(address);
		businessInfo.setCompany("Bobs Discount Furniture");
		businessInfo.phoneNumber(num);
		HomeInfo homeInfo = new HomeInfo();
		homeInfo.setAddress(address);
		homeInfo.setCurrentCity("Amherst");
		homeInfo.phoneNumber(num);
		Name name = new Name();
		name.setFirstName("Jim");
		name.setMiddleName("Bo");
		name.setLastName("Marx");
		List<String> high_school = new LinkedList<String>();
		high_school.add("ARHS");
		high_school.add("Boston South");
		List<String> college = new LinkedList<String>();
		college.add("MIT");
		college.add("Babson");
		Date date = new Date(System.currentTimeMillis());
		
		List<SocialMedia> socialMedia = new LinkedList<SocialMedia>();
		SocialMedia sm = new SocialMedia();
		sm.setSocialMediaURL("http://www.google.com/");
		socialMedia.add(sm);
		socialMedia.add(sm);
		List<String> additionalEmails = new LinkedList<String>();
		additionalEmails.add("jimbob@gmail.com");
		additionalEmails.add("JMarx@bobsfurniture.com");
		List<PhoneNumber> additionalNumbers = new LinkedList<PhoneNumber>();
		additionalNumbers.add(num);
		additionalNumbers.add(num);
		
		User jim = new User.UserBuilder()
				.login(login)
				.gender("M")
				.businessInfo(businessInfo)
				.homeInfo(homeInfo)
				.name(name)
				.hometown("Quincy")
				.highSchool(high_school)
				.college(college)
				.mobileNumber(num)
				.registered((java.sql.Date) date)
				.birthday((java.sql.Date) date)
				.socialMedia(socialMedia)
				.additionalEmails(additionalEmails)
				.additionalNumbers(additionalNumbers)
				.build();
		
		return jim;
	}
	
	public static User createJulie(Login login){
		Address address =  new Address("72 Bart Street", "Apt 2", "City", "State", "12345");
		PhoneNumber num = new PhoneNumber("4137745081", "17", "");
		BusinessInfo businessInfo = new BusinessInfo();
		businessInfo.setAddress(address);
		businessInfo.setCompany("Bobs Discount Furniture");
		businessInfo.phoneNumber(num);
		HomeInfo homeInfo = new HomeInfo();
		homeInfo.setAddress(address);
		homeInfo.setCurrentCity("Amherst");
		homeInfo.phoneNumber(num);
		Name name = new Name();
		name.setFirstName("Jim");
		name.setMiddleName("Bo");
		name.setLastName("Marx");
		List<String> high_school = new LinkedList<String>();
		high_school.add("ARHS");
		high_school.add("Boston South");
		List<String> college = new LinkedList<String>();
		college.add("MIT");
		college.add("Babson");
		Date date = new Date(System.currentTimeMillis());
		
		List<SocialMedia> socialMedia = new LinkedList<SocialMedia>();
		SocialMedia sm = new SocialMedia();
		sm.setSocialMediaURL("http://www.google.com/");
		socialMedia.add(sm);
		socialMedia.add(sm);
		List<String> additionalEmails = new LinkedList<String>();
		additionalEmails.add("jimbob@gmail.com");
		additionalEmails.add("JMarx@bobsfurniture.com");
		List<PhoneNumber> additionalNumbers = new LinkedList<PhoneNumber>();
		additionalNumbers.add(num);
		additionalNumbers.add(num);
		
		User jim = new User.UserBuilder()
				.login(login)
				.gender("M")
				.businessInfo(businessInfo)
				.homeInfo(homeInfo)
				.name(name)
				.hometown("Quincy")
				.highSchool(high_school)
				.college(college)
				.mobileNumber(num)
				.registered((java.sql.Date) date)
				.birthday((java.sql.Date) date)
				.socialMedia(socialMedia)
				.additionalEmails(additionalEmails)
				.additionalNumbers(additionalNumbers)
				.build();
		
		return jim;
	}
}
