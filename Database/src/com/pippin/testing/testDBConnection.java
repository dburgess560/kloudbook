package com.pippin.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pippin.database.DBConnection;

public class testDBConnection {

	private DBConnection db;
	
	@Before
	public void setUp() throws Exception {
		db = DBConnection.getInstance();

	}

	@After
	public void tearDown() throws Exception {
		db.closeConnection();
	}

	@Test
	public void testDBConnection() {
		fail("Can we even test this?");
	}
	
	

//	@Test(expected=SQLException.class)
//	public void testExecuteMisspelledQuery() throws SQLException {
//		ResultSet fakeQuery = null;
//		fakeQuery = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery("ASELECT * FROM user_data;");
//		assertNull("Incorrect SQL query returned something", fakeQuery);
//	}
//	@Test
//	public void testExecuteSelect() throws SQLException {
//		ResultSet select = null;
//		select = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM user_data LIMIT 1;");
//		assertNotNull("Correct SQL query failed", select);
//	}
//	@Test(expected=SQLException.class)
//	public void testExecuteWrongQuery() throws SQLException {
//		ResultSet insert = null;
//		insert = db.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery("INSERT INTO user_high_school VALUES (1,8,'ARHS'),(2,10,'BSCU');");
//		assertNull("Somehow ran an INSERT query in a method that can only run SELECT", insert);
//	}
//
//	@Test(expected=SQLException.class)
//	public void testUpdateMisspelledQuery() throws SQLException {
//		int fakeQuery = -5;
//		fakeQuery = db.getConnection().createStatement().executeUpdate("AINSERT INTO user_high_school VALUES (1,8,'ARHS'),(2,10,'BSCU');");
//		assertEquals("Incorrect SQL query returned something", -1, fakeQuery);
//	}
//	@Test(expected=SQLException.class)
//	public void testUpdatePKErrorQuery() throws SQLException {
//		int pkError = -5;
//		pkError = db.getConnection().createStatement().executeUpdate("INSERT INTO user_high_school VALUES (1,8,'ARHS'),(2,10,'BSCU');");
//		assertEquals("The table might be empty if this occurs", -1, pkError);
//	}
//	@Test
//	public void testUpdateDelete1() throws SQLException {
//		int deleteOld = -5;
//		deleteOld = db.getConnection().createStatement().executeUpdate("DELETE FROM user_high_school WHERE user_id=8;");
//		assertEquals("Delete failed", 1, deleteOld);
//	}
//	@Test
//	public void testUpdateInsert1() throws SQLException {
//		int insertNew = -5;
//		insertNew = db.getConnection().createStatement().executeUpdate("INSERT INTO user_high_school (user_id,high_school) VALUES (8,'ARHS');");
//		assertEquals("Insert failed", 1, insertNew);
//	}
//	@Test
//	public void testUpdateDelete2() throws SQLException {
//		int deleteBothOld = -5;
//		deleteBothOld = db.getConnection().createStatement().executeUpdate("DELETE FROM user_high_school WHERE user_id=8 OR user_id=10;");
//		assertEquals("Double delete failed", 2, deleteBothOld);
//	}
//	@Test
//	public void testUpdateInsert2() throws SQLException {
//		int insertBothNew = -5;
//		insertBothNew = db.getConnection().createStatement().executeUpdate("INSERT INTO user_high_school (user_id,high_school) VALUES (8,'ARHS2'),(10,'BSCU');");
//		assertEquals("Double insert failed", 2, insertBothNew);
//	}
//	@Test
//	public void testUpdateQuery() throws SQLException {
//		int updateEntry = -5;
//		updateEntry = db.getConnection().createStatement().executeUpdate("UPDATE user_high_school SET high_school='ARHS' WHERE user_id=8;");
//		assertEquals("Update failed", 1, updateEntry);
//	}
//
//	@Test
//	public void testPrepareQuery() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testClose() {
//		fail("Not yet implemented");
//	}

}
