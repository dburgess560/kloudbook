//rhromada

package com.pippin.testing;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import com.pippin.database.DBConnection;

public class ProfileGroupTest {

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
	public void testCreateGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindGroup() {
		fail("Not yet implemented");
	}
}
