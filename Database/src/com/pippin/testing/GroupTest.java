//rhromada

package com.pippin.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pippin.database.DBConnection;
import com.pippin.database.Group;
import com.pippin.database.GroupMember;
import com.pippin.database.Profile;
import com.pippin.database.Search;

public class GroupTest {
	
	private final int ADMIN = 2;

	@BeforeClass
	public static void startGroupTest() throws SQLException,
	InstantiationException, IllegalAccessException,
	ClassNotFoundException {
		DBConnection db = DBConnection.getInstance();
		db.prepareScript("dummydelete.sql");
		db.prepareScript("dummyinsert.sql");
		db.closeConnection();

	}

	@AfterClass
	public static void endGroupTest() throws InstantiationException,
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

	private ArrayList<String> nameSelect(int userID) throws SQLException,
	InstantiationException, IllegalAccessException,
	ClassNotFoundException {
		ArrayList<String> names = new ArrayList<String>();
		DBConnection db = DBConnection.getInstance();
		ResultSet select = null;
		select = db
				.getConnection()
				.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)
						.executeQuery(
								"SELECT * FROM users where user_id = " + userID + ";");
		while (select.next()) {
			names.add(select.getString("first_name"));
		}
		db.closeConnection();
		return names;
	}

	@Test
	public void testAddMember() throws SQLException, InstantiationException,
	IllegalAccessException, ClassNotFoundException {
		Profile p = new Profile();
		int groupID = p.createGroup(2, "AddGroupMemberTestGroup",
				"tests add members", 1);
		Group g = p.findGroup(groupID);
		int userID = g.addMember(2, 6); // add Tommy as an unconfirmed
		// member
		GroupMember member = g.getGroupMember(userID);
		String name = nameSelect(member.getUserID()).get(0);
		assertEquals(name, "Tommy");
	}

	@Test
	public void testConfirmMember() throws SQLException,
	InstantiationException, IllegalAccessException,
	ClassNotFoundException {
		Profile p = new Profile();
		int groupID = p.createGroup(2, "ConfirmMemberTestGroup",
				"tests confirm members", 1);
		Group g = p.findGroup(groupID);
		int groupMemberID = g.addMember(2, 6); // add Tommy
		ArrayList<GroupMember> members = g.getActiveMembers();
		String name1 = nameSelect(members.get(0).getUserID()).get(0);
		assertEquals("Franz", name1); // admin
		assertEquals(members.size(), 1); // should only have one member active
		// (Franz)
		g.confirmMember(6, true);
		members = g.getActiveMembers();
		assertEquals(members.size(), 2); // should now have two members active
		name1 = nameSelect(members.get(0).getUserID()).get(0);
		assertEquals("Franz", name1); // admin
		String name2 = nameSelect(members.get(1).getUserID()).get(0);
		assertEquals("Tommy", name2); // regular member

	}

	@Test
	public void testRemoveMember() throws SQLException, InstantiationException,
	IllegalAccessException, ClassNotFoundException {
		Profile p = new Profile();
		Group g = p.findGroup(2);// 'kbGroup2'
		GroupMember m = g.getGroupMember(5);// Last group member with group
		// user id 5 Margaret
		g.removeMember(2, m.getUserID());// user id with 2 is
		// an admin
		ArrayList<GroupMember> members = g.getActiveMembers();
		for (GroupMember member : members) { // check through members to see if
			// member there anymore
			System.out.println(member.getMemberID());
			String name = nameSelect(member.getUserID()).get(0);
			assertNotSame(name, "Margaret");
		}
	}

	@Test
	public void testSetDescription() throws SQLException, InstantiationException,
	IllegalAccessException, ClassNotFoundException {
		Profile p = new Profile();
		int groupID = p.createGroup(ADMIN, "AddSetDecriptionTestGroup",
				"tests set description", 1);
		Group g = p.findGroup(groupID);
		g.setDescription(ADMIN, "newDescription");
		String description = g.getDescription();
		assertEquals(description, "newDescription");
	}

	@Test
	public void testSetGroupName()throws SQLException, InstantiationException,
	IllegalAccessException, ClassNotFoundException { 
		Profile p = new Profile();
		int groupID = p.createGroup(ADMIN, "AddSetGroupNameTestGroup",
				"tests set groupName", 1);
		Group g = p.findGroup(groupID);
		g.setGroupName(ADMIN, "newName");
		String name = g.getDescription();
		assertEquals(name, "newName");
	}

	@Test
	public void testSetSharedInfo() throws SQLException, InstantiationException,
	IllegalAccessException, ClassNotFoundException {
		Profile p = new Profile();
		int groupID = p.createGroup(ADMIN, "AddSetSharedInfoTestGroup",
				"tests set SharedInfo", 1);
		Group g = p.findGroup(groupID);
		g.setSharedInfo(ADMIN, 2);
		int info = g.getSharedInfo();
		assertEquals(info, 2);
	}

	@Test
	public void testSetActiveStatus() throws SQLException, InstantiationException,
	IllegalAccessException, ClassNotFoundException {
		Profile p = new Profile();
		int groupID = p.createGroup(ADMIN, "AddSetGroupNameTestGroup",
				"tests set groupName", 1);
		Group g = p.findGroup(groupID);
		g.setActiveStatus(ADMIN, groupID, false);
		boolean active = g.isActive();
		assertEquals(active, false);
	}

	@Test
	public void testChangeMemberPriority() throws SQLException, InstantiationException,
	IllegalAccessException, ClassNotFoundException {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteGroup() throws SQLException, InstantiationException,
	IllegalAccessException, ClassNotFoundException {
		Profile p = new Profile();
		int groupID = p.createGroup(2, "DeleteTestGroup",
				"tests deleting group", 1);
		Group g = new Group(groupID);
		Search s = new Search();
		assertTrue("Group not found",
				groupID == s.groupSearch("DeleteTestGroup").get(0));
		g.deleteGroup(2);
		// will not be able to find result after deleted
		assertEquals(0, s.groupSearch("DeleteTestGroup").size());
	}

	@Test
	public void testGetActiveMembers() throws SQLException, InstantiationException,
	IllegalAccessException, ClassNotFoundException {
		Group g = new Group(3); // get kbGroup3
		ArrayList<GroupMember> users = g.getActiveMembers();
		assertEquals(3, users.size());// should only return 3 active members
	}

	@Test
	public void testGetGroupName() throws SQLException, InstantiationException,
	IllegalAccessException, ClassNotFoundException {
		fail("Not yet implemented");
	}

	@Test
	public void testGetGroupDescription() throws SQLException, InstantiationException,
	IllegalAccessException, ClassNotFoundException {
		fail("Not yet implemented");
	}

	@Test
	public void testGetGroupID() throws SQLException, InstantiationException,
	IllegalAccessException, ClassNotFoundException {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSharedInfo() throws SQLException, InstantiationException,
	IllegalAccessException, ClassNotFoundException {
		fail("Not yet implemented");
	}

	@Test
	public void testisActive() throws SQLException, InstantiationException,
	IllegalAccessException, ClassNotFoundException {
		fail("Not yet implemented");
	}

	@Test
	public void createGroupTest() throws InstantiationException,
	IllegalAccessException, ClassNotFoundException, SQLException,
	InterruptedException {
		DBConnection db = DBConnection.getInstance();
		Profile p = new Profile();
		p.createGroup(2, "BOOOOOK", "testestestsetestestestestse",1);
		ResultSet result = db
				.getConnection()
				.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)
						.executeQuery("SELECT * FROM groups WHERE name = 'BOOOOOK';");
		String name = null;
		while (result.next()) {
			name = result.getString("name");
		}
		db.closeConnection();

		assertEquals("BOOOOOK", name);
	}

	@Test
	public void findGroupTest() throws SQLException, InstantiationException,
	IllegalAccessException, ClassNotFoundException {
		DBConnection db = DBConnection.getInstance();
		Profile p = new Profile();
		int id = p.createGroup(1, "Group1", "testtestesttest",1);
		Group g = p.findGroup(id);
		db.closeConnection();

		assertEquals("Group1", g.getName());
	}
}
