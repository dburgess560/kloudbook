package com.pippin.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.stream.JsonReader;
import com.pippin.database.DBConnection;
import com.pippin.database.Search;

public class SearchTest {
	public static Search s;
	HashMap<String, String> hm = new HashMap<String, String>();

	@BeforeClass
	public static void startDatabaseTest() throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		s = new Search();
		DBConnection db = DBConnection.getInstance();
		db.prepareScript("dummydelete.sql");
		db.prepareScript("dummyinsert.sql");
		db.closeConnection();
	}

	@AfterClass
	public static void endDatabaseTest() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		DBConnection db = DBConnection.getInstance();
		db.prepareScript("dummydelete.sql");
		db.closeConnection();
	}

	private ArrayList<String> nameSelect(LinkedList<Integer> userIDs)
			throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		ArrayList<String> names = new ArrayList<String>();
		for (Integer userID : userIDs) {
			DBConnection db = DBConnection.getInstance();
			ResultSet select = null;
			select = db
					.getConnection()
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY)
					.executeQuery(
							"SELECT * FROM users where user_id = " + userID
									+ ";");
			while (select.next()) {
				names.add(select.getString("first_name"));
			}
			db.closeConnection();
		}
		return names;
	}

	public ArrayList<String> groupSelect(LinkedList<Integer> groupIDs)
			throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		ArrayList<String> names = new ArrayList<String>();
		for (Integer groupID : groupIDs) {
			DBConnection db = DBConnection.getInstance();
			ResultSet select = null;
			select = db
					.getConnection()
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY)
					.executeQuery(
							"SELECT * FROM groups where groups_id = " + groupID
									+ ";");
			while (select.next()) {
				names.add(select.getString("name"));
			}
			db.closeConnection();
		}
		return names;
	}

	@Test
	public void JsonTest() throws IOException {
		InputStream in = getClass().getResourceAsStream("test.json");
		Reader fr = new InputStreamReader(in, "utf-8");
		JsonReader jr = new JsonReader(fr);
		jr.beginObject();
		String emailTest = "";
		while (jr.hasNext()) {
			String name = jr.nextName();
			if (name.equals("email")) {
				emailTest = jr.nextString();
			}
		}
		jr.close();
		assertEquals("test3@gmail.com", emailTest);
	}

	@Test
	public void GroupBasicSearchTest() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		ArrayList<String> groupList = groupSelect(s.groupSearch("kbGroup"));
		assertEquals("kbGroup1", groupList.get(0));
		assertEquals("kbGroup2", groupList.get(1));
	}

	@Test
	public void ConnectionBasicSearchTest() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		ArrayList<String> nameList = nameSelect(s.basicSearch("Javier Bardem",
				2));
		assertEquals("Javier", nameList.get(0));
		nameList = nameSelect(s.basicSearch("Tom Cruise", 2));
		assertEquals("Tom", nameList.get(0));
		nameList = nameSelect(s.basicSearch("Tom", 2));
		assertEquals("Tom", nameList.get(0));
		assertEquals(1, nameList.size());

	}

	@Test
	public void BasicSearchTest() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		ArrayList<String> nameList = nameSelect(s.basicSearch("Javier Bardem",
				-1));
		assertEquals("Javier", nameList.get(0));
		nameList = nameSelect(s.basicSearch("Franz Ferdinand Jorge Paco", -1));
		assertEquals("Franz", nameList.get(0));
		nameList = nameSelect(s.basicSearch("Tom"));
		assertEquals("Tom", nameList.get(0));
		assertEquals("Tommy", nameList.get(1));

	}

	@Test
	public void BasicSearchTestNull() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		LinkedList<Integer> idList = s.basicSearch("", -1);
		assertNull(idList);
	}

	@Test
	public void AdvancedSearchName() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		hm.put("Name", "Franz Ferdinand Jorge Paco");
		ArrayList<String> nameList = nameSelect(s.advancedSearch(hm));
		assertEquals("Franz", nameList.get(0));
		hm.clear();
	}

	@Test
	public void AdvancedSearchTestNull() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		LinkedList<Integer> idList = s.advancedSearch(null);
		assertNull(idList);
	}

	@Test
	public void AdvancedSearchHighSchool() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		hm.put("High School", "Holyoke High");
		ArrayList<String> nameList = nameSelect(s.advancedSearch(hm));
		assertEquals("Franz", nameList.get(0));
		assertEquals("Tom", nameList.get(1));
		assertEquals(2, nameList.size());
		hm.clear();
	}

	@Test
	public void AdvancedSearchEmail() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("Email Address", "test@gmail.com");
		ArrayList<String> nameList = nameSelect(s.advancedSearch(hm));
		assertEquals("Franz", nameList.get(0));
		hm.clear();

	}

	@Test
	public void AdvancedSearchCollege() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {

		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("College", "Hampshire College");
		ArrayList<String> nameList = nameSelect(s.advancedSearch(hm));
		assertEquals("Franz", nameList.get(0));
		hm.clear();

	}

	@Test
	public void AdvancedSearchCompany() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {

		hm.put("Company", "Amazon");
		ArrayList<String> nameList = nameSelect(s.advancedSearch(hm));
		assertEquals("Franz", nameList.get(0));
		hm.clear();

	}

	@Test
	public void AdvancedSearchCurrentCity() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {

		hm.put("Current City", "Amherst");
		ArrayList<String> nameList = nameSelect(s.advancedSearch(hm));
		assertEquals("Franz", nameList.get(0));
		assertEquals("Javier", nameList.get(1));
		assertEquals(2, nameList.size());
		hm.clear();

	}

	@Test
	public void AdvancedSearchHometown() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {

		hm.put("Hometown", "New York");
		ArrayList<String> nameList = nameSelect(s.advancedSearch(hm));
		assertEquals("Javier", nameList.get(0));
		hm.clear();
	}

	@Test
	public void AdvancedSearchJoin() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		hm.put("High School", "Holyoke High");
		hm.put("Name", "Franz Ferdinand Jorge Paco");
		hm.put("Current City", "Amherst");
		hm.put("Hometown", "Amherst");
		hm.put("Company", "Amazon");
		hm.put("College", "Hampshire College");
		hm.put("Email Address", "test@gmail.com");
		ArrayList<String> nameList = nameSelect(s.advancedSearch(hm));
		assertEquals("Franz", nameList.get(0));
		assertEquals(1, nameList.size());

		hm.clear();
	}
}
