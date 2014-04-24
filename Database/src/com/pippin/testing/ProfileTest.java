package com.pippin.testing;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.pippin.Login;
import com.pippin.Name;
import com.pippin.User;
import com.pippin.database.DBConnection;
import com.pippin.database.Profile;

public class ProfileTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@AfterClass
	public static void deleteTestUser(){
		try {
			DBConnection.getInstance()
				.getConnection()
				.createStatement()
				.executeUpdate("DELETE FROM users WHERE primary_email='emaildoesntmatter@gmail.com'");
		} catch (SQLException e) {
			e.printStackTrace();
			fail("SQL Exception thrown");
		}
	}

	@Test
	public void testGetID_BadEmail() {
		assertEquals(-1, Profile.getID("badEmail"));
	}
	
	@Test
	public void testGetID_NonExistEmail() {
		assertEquals(-1, Profile.getID("fakeemail@gmail.com"));
	}
	
	@Test
	public void testGetID_GoodEmail() {
		assertEquals(2, Profile.getID("DaveBurgess@gmail.com"));
	}
	
	@Test
	public void testGetID_DeactiveAccount() {
		assertEquals(2, Profile.getID("DaveBurgess@gmail.com"));
	}

	@Test
	public void testLogin_BadEmail() {
		Login incLogin = new Login("badEmail","badPassword");
		assertNull(Profile.login(incLogin));
	}

	@Test
	public void testLogin_GoodEmailWrongPass() {
		Login badPass = new Login("DaveBurgess@gmail.com","wrongpass");
		assertNull(Profile.login(badPass));
	}

	@Test
	public void testLogin_GoodEmailCorretPass() {
		Login daveLogin = new Login("DaveBurgess@gmail.com","password");
		User daveExpected= Profile.login(daveLogin);
		User daveActual = new User.UserBuilder().name(new Name("Dave", "R", "Burgess")).build();
		assertEquals(daveActual.getName().getFirstName(), daveExpected.getName().getFirstName());
	}

	@Test
	public void testCreateAccount_ExistEmail() {
		Login daveLogin = new Login("DaveBurgess@gmail.com","password");
		User daveCreate = new User.UserBuilder()
			.login(daveLogin)
			.name(new Name("Dave", "R", "Burgess"))
			.build();
		assertEquals(-1, Profile.createAccount(daveCreate));
	}

	@Test
	public void testCreateAccount_NoEmail() {
		Login daveLogin = new Login("","password");
		User daveCreate = new User.UserBuilder()
			.login(daveLogin)
			.name(new Name("Dave", "R", "Burgess"))
			.build();
		assertEquals(-1, Profile.createAccount(daveCreate));
	}

	@Test
	public void testCreateAccount_NoPassword() {
		Login daveLogin = new Login("emaildoesntmatter@gmail.com","");
		User daveCreate = new User.UserBuilder()
			.login(daveLogin)
			.name(new Name("Dave", "R", "Burgess"))
			.build();
		assertEquals(-1, Profile.createAccount(daveCreate));
	}

	@Test
	public void testCreateAccount_Sucess() {
		Login newLogin = new Login("newemail@gmail.com","password");
		User newCreate = new User.UserBuilder()
			.login(newLogin)
			.name(new Name("J", "Unit", "Test"))
			.build();
		assertTrue(Profile.createAccount(newCreate)>1);
		//creating a new account will return some new user_ID over 1
	}

	@Test
	public void testEditAccount_NewEmail() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testEditAccount_NewEmailAlreadyExists() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testEditAccount_Sucess() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddFriend() {
		fail("Not yet implemented");
	}

	@Test
	public void testAcceptFriend() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveFriend() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testListUsers() {
		fail("Not yet implemented");
	}

	@Test
	public void testResetPW() {
		fail("Not yet implemented");
	}

	@Test
	public void testEditPW() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeactivate() {
		fail("Not yet implemented");
	}

}
