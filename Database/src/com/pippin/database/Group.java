//rhromada
//Group 

//1 = Business
//2= personal
//3 = school

package com.pippin.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The class that contains information about and manages groups
 * 
 * @author Robert
 */

@SuppressWarnings("unused")
public class Group {

	private int groupID;
	private String groupName;
	private String description;
	private int sharedInfo;
	private ArrayList<GroupMember> groupMembers;
	private boolean isActive;

	private final int GROUPADMINPRIORITY = 1;
	private final int GROUPMEMBERPRIORITY = 0;
	private final int PENDINGMEMBERPRIORITY = -1;

	/**
	 * Creates a group with pulled data from the database using the Profile
	 * class Used for any data base manipulation of the group
	 * 
	 * @param groupName
	 * @param description
	 * @param groupID
	 * @param sharedInfo
	 * @param isActive
	 * @param members
	 */
	// subject to change
	public Group(String groupName, String description, int groupID,
			int sharedInfo, boolean isActive, ArrayList<GroupMember> members) {
		this.groupName = groupName;
		this.description = description;
		this.groupID = groupID;
		this.sharedInfo = sharedInfo;
		this.isActive = isActive;
		groupMembers = members;
	}

	/**
	 * Creates a shell group
	 * 
	 * @param ID
	 */
	public Group(int ID) {
		this.groupID = ID;
	}

	/*
	 * /** an internal method for confirming the calling admin is a valid admin
	 * within this group
	 */
	private boolean isAdmin(int userID) throws SQLException {
		GroupMember gm = internalFind(userID);
		if (gm != null) {
			if (gm.isAdmin() && gm.isActive()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * an internal method for finding a group member within the groupMember
	 * ArrayList
	 * 
	 * @throws SQLException
	 */
	private GroupMember internalFind(int userID) throws SQLException {
		String stmt = "SELECT * FROM group_members WHERE user_id = ? AND group_id = ?;";
		DBConnection db = DBConnection.getInstance();
		PreparedStatement sm = db.getConnection().prepareStatement(stmt);
		sm.setInt(1, userID);
		sm.setInt(2, groupID);
		ResultSet result = sm.executeQuery();
		GroupMember groupMember = null;
		while (result.next()) {
			groupMember = new GroupMember(result.getInt("user_id"),
					result.getInt("access"), result.getInt("group_members_id"),
					result.getInt("group_id"));
		}
		sm.closeOnCompletion();
		db.closeConnection();
		return groupMember;
	}

	/**
	 * This adds a member to the database.
	 * 
	 * @param admin
	 *            this is the calling admin for verification
	 * @param userID
	 *            the user being added
	 * @return returns true if admin was valid and user was not in group already
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int addMember(int admin, int userID) throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		int groupMemberID = -1;
		if (isAdmin(admin) && internalFind(userID) == null) {
			String stmt = "INSERT INTO group_members(group_id,user_id,access) VALUES ( ? , ?, ?);";
			DBConnection db = DBConnection.getInstance();
			Connection conn = db.getConnection();
			PreparedStatement sm = conn.prepareStatement(stmt,
					Statement.RETURN_GENERATED_KEYS);
			sm.setInt(1, this.groupID);
			sm.setInt(2, userID);
			sm.setInt(3, PENDINGMEMBERPRIORITY);
			sm.executeUpdate();
			ResultSet result = sm.getGeneratedKeys();
			while (result.next()) {
				groupMemberID = result.getInt(1);
			}
			sm.closeOnCompletion();
			conn.close();
			
			stmt = "INSERT INTO notification(user_id, connection_id, isGroup) VALUES (?, ?, ?);";
			db = DBConnection.getInstance();
			conn = db.getConnection();
			sm = conn.prepareStatement(stmt,
					Statement.RETURN_GENERATED_KEYS);
			sm.setInt(1, userID);
			sm.setInt(2, groupMemberID);
			sm.setBoolean(3, true);
			sm.executeUpdate();
			
			sm.closeOnCompletion();
			conn.close();
			return userID;
		}
		return -1;
	}

	public GroupMember getGroupMember(int userID) throws SQLException {
		String stmt = "SELECT * FROM group_members WHERE user_id = ? AND group_id = ?;";
		DBConnection db = DBConnection.getInstance();
		PreparedStatement sm = db.getConnection().prepareStatement(stmt);
		sm.setInt(1, userID);
		sm.setInt(2, groupID);
		ResultSet result = sm.executeQuery();
		GroupMember groupMember = null;
		while (result.next()) {
			groupMember = new GroupMember(result.getInt("user_id"),
					result.getInt("access"), result.getInt("group_members_id"),
					result.getInt("group_id"));
		}
		return groupMember;

	}

	/**
	 * takes a user id and makes this member an active user of this group
	 * 
	 * @param userID
	 *            the user being confirmed
	 * @return returns true if user is part of this group and
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean confirmMember(int groupMemberID, boolean accept){
		if(accept){
			try{
				String stmt = "UPDATE group_members SET access= ? WHERE group_members_id= ?;";
				DBConnection db = DBConnection.getInstance();
				Connection conn = db.getConnection();
				PreparedStatement sm = conn.prepareStatement(stmt);
				sm.setInt(1, GROUPMEMBERPRIORITY);
				sm.setInt(2, groupMemberID);
				sm.executeUpdate();
				sm.closeOnCompletion();
				conn.close();
				return true;
			} catch(SQLException e){
				e.printStackTrace();
				return false;
			}
		}
		else {
			try{
				String stmt = "DELETE FROM group_members WHERE group_members_id= ?;";
				DBConnection db = DBConnection.getInstance();
				Connection conn = db.getConnection();
				PreparedStatement sm = conn.prepareStatement(stmt);
				sm.setInt(1, groupMemberID);
				sm.executeUpdate();
				sm.closeOnCompletion();
				conn.close();
				return true;
			} catch(SQLException e){
				e.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * removes a member from the database with the given userID
	 * 
	 * @param admin
	 *            this is the calling admin for verification
	 * @param userID
	 *            the user being removed
	 * @param groupMemberID
	 *            the users memberID
	 * @return returns groupMember that was removed else returns -1;
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int removeMember(int admin, int userID)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {

		// if (isAdmin(admin)) {
		// int adminNumber = 0;
		// for (GroupMember member : groupMembers) {
		// if (member.isAdmin() && member.isActive()) {
		// adminNumber++;
		// }
		// }
		// GroupMember gm = internalFind(userID);
		// if (gm != null) {
		// if (gm.getMemberID() == groupMemberID) {
		// if (gm.isAdmin() && gm.isActive() && adminNumber <= 1) {
		// String stmt =
		// "DELETE FROM group_members WHERE group_members_id = ? AND user_id = ? AND group_id = ?;";
		// DBConnection db = DBConnection.getInstance();
		// PreparedStatement sm = db.getConnection()
		// .prepareStatement(stmt);
		// sm.setInt(1, groupMemberID);
		// sm.setInt(2, userID);
		// sm.setInt(3, this.groupID);
		// sm.executeUpdate();
		// sm.closeOnCompletion();
		// db.closeConnection();
		// groupMembers.remove(gm);
		// return true;
		// }
		// }
		// }
		// }
		// return false;

		if (isAdmin(admin) || admin == userID) { // if admin equals userID,
													// person wishes to remove
													// self
			GroupMember gm = internalFind(userID);
			if (gm != null) {
				String stmt = "DELETE FROM group_members WHERE user_id = ? AND group_id = ?;";
				DBConnection db = DBConnection.getInstance();
				PreparedStatement sm = db.getConnection()
						.prepareStatement(stmt);
				sm.setInt(1, userID);
				sm.setInt(2, this.groupID);

				sm.executeUpdate();
				sm.closeOnCompletion();
				db.closeConnection();
				return userID;
			}
		}
		return -1;
	}

	/**
	 * Changes the description of this group
	 * 
	 * @param admin
	 *            this is the calling admin for verification
	 * @param newDescription
	 * @return returns true if calling admin was valid
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean setDescription(int admin, String newDescription)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		if (isAdmin(admin) && newDescription!="") {
			String stmt = "UPDATE groups SET description= ? WHERE groups_id= ? ;";
			DBConnection db = DBConnection.getInstance();
			PreparedStatement sm = db.getConnection().prepareStatement(stmt);
			sm.setString(1, newDescription);
			sm.setInt(2, this.groupID);
			sm.executeUpdate();
			sm.closeOnCompletion();
			db.closeConnection();
			return true;
		} else
			return false;
	}

	/**
	 * Changes the group name of this group
	 * 
	 * @param admin
	 *            this is the calling admin for verification
	 * @param newName
	 * @return returns true if calling admin was valid
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean setGroupName(int admin, String newName)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		if (isAdmin(admin) && newName!="") {
			String stmt = "UPDATE groups SET name= ? WHERE groups_id= ?;";
			DBConnection db = DBConnection.getInstance();
			PreparedStatement sm = db.getConnection().prepareStatement(stmt);
			sm.setString(1, newName);
			sm.setInt(2, this.groupID);
			sm.executeUpdate();
			sm.closeOnCompletion();
			db.closeConnection();
			return true;
		} else
			return false;
	}

	/**
	 * Changes the shared info of this group
	 * 
	 * @param admin
	 *            this is the calling admin for verification
	 * @param newSharedInfo
	 * @return returns true if calling admin was valid
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean setSharedInfo(int admin, int newSharedInfo)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		if (isAdmin(admin)) {
			String stmt = "UPDATE groups SET shared_info= ? WHERE groups_id= ?;";
			DBConnection db = DBConnection.getInstance();
			PreparedStatement sm = db.getConnection().prepareStatement(stmt);
			sm.setInt(1, newSharedInfo);
			sm.setInt(2, this.groupID);
			sm.executeUpdate();
			sm.closeOnCompletion();
			db.closeConnection();
			return true;
		} else
			return false;
	}

	/**
	 * Changes the active status of the group
	 * 
	 * @param admin
	 *            this is the calling admin for verification
	 * @param groupID
	 * @param isActive
	 * @return returns true if calling admin was valid
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean setActiveStatus(int admin, int groupID, boolean isActive)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		if (isAdmin(admin)) {
			String stmt = "UPDATE groups SET isActive= ? WHERE groups_id= ?;";
			DBConnection db = DBConnection.getInstance();
			PreparedStatement sm = db.getConnection().prepareStatement(stmt);
			sm.setBoolean(1, isActive);
			sm.setInt(2, this.groupID);
			sm.executeUpdate();
			sm.closeOnCompletion();
			db.closeConnection();
			return true;
		} else
			return false;
	}

	/**
	 * Changes a groupMember of this groups's priority level to the newPriority
	 * level
	 * 
	 * @param admin
	 *            this is the calling admin for verification
	 * @param userID
	 *            the user being changed
	 * @param memberID
	 *            the user's member id
	 * @param newPrioityLevel
	 * @return returns true if calling admin was valid and user is part of this
	 *         group
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean changeMemberPriority(int admin, int userID, int memberID,
			int newPrioityLevel) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		if (isAdmin(admin)) {
			GroupMember gm = internalFind(userID);
			if (gm != null) {
				if (gm.getMemberID() == memberID) {
					try {
						String stmt = "UPDATE group_members SET access = ? WHERE group_members_id= ?;";
						DBConnection db = DBConnection.getInstance();
						PreparedStatement sm;
						sm = db.getConnection().prepareStatement(stmt);
						sm.setInt(1, newPrioityLevel);
						sm.setInt(2, memberID);
						sm.executeUpdate();
						db.closeConnection();
						sm.closeOnCompletion();
						return true;
					} catch (SQLException e) {
						e.printStackTrace();
						return false;
					}
				}
			}
		}
		return false;
	}

	/**
	 * This will delete the group from the database NOTE this is not a
	 * deactivate, this will permanently remove this group
	 * 
	 * @param admin
	 *            this is the calling admin for verification
	 * @return returns true if calling admin was valid
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean deleteGroup(int admin) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		if (isAdmin(admin)) {
			String stmt2 = "DELETE FROM group_members WHERE group_id = ?";
			DBConnection db2 = DBConnection.getInstance();
			PreparedStatement sm2;
			sm2 = db2.getConnection().prepareStatement(stmt2);
			sm2.setInt(1, this.groupID);
			sm2.executeUpdate();
			sm2.closeOnCompletion();
			String stmt = "DELETE FROM groups WHERE groups_id = ?;";
			DBConnection db = DBConnection.getInstance();
			PreparedStatement sm;
			sm = db.getConnection().prepareStatement(stmt);
			sm.setInt(1, this.groupID);
			sm.executeUpdate();
			db.closeConnection();
			sm.closeOnCompletion();
			return true;
		}
		return false;
	}

	/**
	 * Gets a list of all active members of this group
	 * 
	 * @return this list of active groupMembers in this group
	 * @throws SQLException
	 */
	public ArrayList<GroupMember> getActiveMembers() throws SQLException {
		// ArrayList<GroupMember> members = new ArrayList<GroupMember>();
		// for (GroupMember gm : groupMembers){
		// if(gm.getGroupID()==this.groupID &&
		// gm.getAccessLevel()>PENDINGMEMBERPRIORITY){
		// members.add(gm);
		// }
		// }
		// return members;

		String stmt = "SELECT * FROM group_members WHERE access > -1 AND group_id = ?;";
		DBConnection db = DBConnection.getInstance();
		PreparedStatement sm = db.getConnection().prepareStatement(stmt);
		sm.setInt(1, groupID);
		ResultSet result = sm.executeQuery();
		ArrayList<GroupMember> groupMembers = new ArrayList<GroupMember>();
		while (result.next()) {
			GroupMember groupMember = new GroupMember(result.getInt("user_id"),
					result.getInt("access"), result.getInt("group_members_id"),
					result.getInt("group_id"));
			groupMembers.add(groupMember);
		}
		return groupMembers;
	}

	public String getDescription() throws SQLException {
		String stmt = "SELECT * FROM group_members group_id = ?;";
		DBConnection db = DBConnection.getInstance();
		PreparedStatement sm = db.getConnection().prepareStatement(stmt);
		sm.setInt(1, groupID);
		ResultSet result = sm.executeQuery();
		String des = null;
		while (result.next()) {
			des = result.getString("description");
		}
		sm.closeOnCompletion();
		db.closeConnection();
		return des;
	}

	public String getName() throws SQLException {
		String stmt = "SELECT * FROM groups group_id = ?;";
		DBConnection db = DBConnection.getInstance();
		PreparedStatement sm = db.getConnection().prepareStatement(stmt);
		sm.setInt(1, groupID);
		ResultSet result = sm.executeQuery();
		String name = null;
		while (result.next()) {
			name = result.getString("name");
		}
		sm.closeOnCompletion();
		db.closeConnection();
		return name;
	}

	public int getGroupID() {
		return groupID;
	}

	public int getSharedInfo() throws SQLException {
		String stmt = "SELECT * FROM groups group_id = ?;";
		DBConnection db = DBConnection.getInstance();
		PreparedStatement sm = db.getConnection().prepareStatement(stmt);
		sm.setInt(1, groupID);
		ResultSet result = sm.executeQuery();
		int sInfo = -1;
		while (result.next()) {
			sInfo = result.getInt("shared_info");
		}
		sm.closeOnCompletion();
		db.closeConnection();
		return sInfo;
	}

	public boolean isActive() throws SQLException {
		String stmt = "SELECT * FROM groups group_id = ?;";
		DBConnection db = DBConnection.getInstance();
		PreparedStatement sm = db.getConnection().prepareStatement(stmt);
		sm.setInt(1, groupID);
		ResultSet result = sm.executeQuery();
		boolean active = false;
		while (result.next()) {
			active = result.getBoolean("isActive");
		}
		sm.closeOnCompletion();
		db.closeConnection();
		return active;
	}
}

// TODO
// test for non-valid admin on near all methods
// test for removing users not in the group
// test for members part of more than one group not being removed from all the
// 		groups on remove member
// test for removing the last admin from a group
// test for test for adding a member to group that is in group already
// test for access levels viewable to pending members
// test for confirm notifications to join group sent then group deleted then the
// 		notification accepted
// test for group being viewable when group is not active
// Confirm with android team that we will need to add a picture to the group
// 		data
//