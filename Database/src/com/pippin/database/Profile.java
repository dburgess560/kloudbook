//dburgess
//Profile 

package com.pippin.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.pippin.Access;
import com.pippin.Address;
import com.pippin.BusinessInfo;
import com.pippin.Friends;
import com.pippin.HomeInfo;
import com.pippin.Login;
import com.pippin.Name;
import com.pippin.Notification;
import com.pippin.PhoneNumber;
import com.pippin.SocialMedia;
import com.pippin.User;
import com.pippin.User.UserBuilder;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.auth.OAuthAuthorization;
import facebook4j.auth.OAuthSupport;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;

public class Profile {

	public Profile() {
	}

	/*
	 * Gets the ID of the user with given primary_email Returns -1 if user does
	 * not exist, returns user_id if they do
	 */
	static public int getID(String email) {
		int id = -1;
		try {
			DBConnection db = DBConnection.getInstance();
			String query = "SELECT user_id, primary_email FROM users WHERE primary_email='"
					+ email + "' LIMIT 1;";
			Connection conn = db.getConnection();
			ResultSet result = conn
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			while (result.next()) {
				if (result.getString("primary_email").equals(email)) {
					id = result.getInt("user_id");
					break;
				}
			}
			result.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	/*
	 * Checks primary_email and password combo Returns -1 if no email found, 0
	 * if email found but bad password, ID# if email and password match same
	 * user
	 */
	static private int checkPass(Login user) {
		int id = -1;
		try {
			DBConnection db = DBConnection.getInstance();
			String query = "SELECT user_id, primary_email, password, isActive FROM users WHERE primary_email='"
					+ user.getEmail() + "' LIMIT 1;";
			Connection conn = db.getConnection();
			ResultSet result = conn
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery(query);

			while (result.next()) {
				if (result.getString("primary_email").equals(user.getEmail())) {
					id = 0;
				}
				if (result.getString("password").equals(user.getPassword())) {
					id = result.getInt("user_id");
					if (!result.getBoolean("isActive"))
						reactivate(id);
					break;
				}
			}
			result.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	/*
	 * Returns the user object for loging in a user
	 */
	static public User login(Login user) {
		int id = checkPass(user);
		if (id > 0)
			return findCurrentUser(id);
		else
			return null;
	}

	/*
	 * Creates a new user in the database Uses procedures in the database to
	 * quickly edit multiple tables at once Returns user_id or -1 if user
	 * already exists or an error occurs
	 */
	static public int createAccount(User newUser) {
		if (newUser.getPrimaryEmail() == null)
			return -1;
		if (newUser.getPassword() == null)
			return -1;
		int returnID = getID(newUser.getPrimaryEmail());
		if (returnID == -1) { // If primary_email does not already exist, start
								// creating new user
			String sqlCreate = "CALL new_user(" + "?, " // primary_email//String
														// 1
					+ "?, " // first_name//String 2
					+ "?, " // middle_name//String 3
					+ "?, " // last_name//String 4
					+ "?, " // gender//Char 5
					+ "?, " // hometown//String 6
					+ "?, " // mobile_number//String 7
					+ "?, " // mobile_number_ext//String 8
					+ "?, " // mobile_country_code//String 9
					+ "?, " // picture//Blob-Image 10
					+ "?, " // registered//Date 11
					+ "?, " // birthday//Date 12
					+ "?, " // out user_id//int 13 OUT
					+ "?, " // pass//String 14
					+ "?, " // home_address1//String 15
					+ "?, " // home_address2//String 16
					+ "?, " // home_city//String 17
					+ "?, " // home_state//String 18
					+ "?, " // home_zip//String 19
					+ "?, " // home_number//String 20
					+ "?, " // home_number_ext//String 21
					+ "?, " // home_country_code//String 22
					+ "?, " // current_city//String 23
					+ "?, " // business_address1//String 24
					+ "?, " // business_address2//String 25
					+ "?, " // business_city//String 26
					+ "?, " // business_state//String 27
					+ "?, " // business_zip//String 28
					+ "?, " // business_number//String 29
					+ "?, " // business_number_ext//String 30
					+ "?, " // business_country_code//String 31
					+ "?);";// company//String 32
			DBConnection db = DBConnection.getInstance();
			Connection  conn;
			CallableStatement cstmt;
			try {
				conn = db.getConnection();
				cstmt = conn.prepareCall(sqlCreate);
				cstmt.setString(1, newUser.getPrimaryEmail().toString());
				cstmt.setString(2, newUser.getName().getFirstName());
				cstmt.setString(3, newUser.getName().getMiddleName());
				cstmt.setString(4, newUser.getName().getLastName());
				cstmt.setString(5, (newUser.getGender() + ""));
				cstmt.setString(6, newUser.getHometown());
				cstmt.setString(7, newUser.getMobileNumber().getNumber());
				cstmt.setString(8, newUser.getMobileNumber().getExt());
				cstmt.setString(9, newUser.getMobileNumber().getCC());
				cstmt.setString(10, newUser.getPicture());
				cstmt.setDate(11, newUser.getRegistered());
				cstmt.setDate(12, newUser.getBirthday());
				cstmt.setInt(13, returnID);
				cstmt.setString(14, newUser.getPassword());
				cstmt.setString(15, newUser.getHomeInfo().getAddress().getAddress1());
				cstmt.setString(16, newUser.getHomeInfo().getAddress().getAddress2());
				cstmt.setString(17, newUser.getHomeInfo().getAddress().getCity());
				cstmt.setString(18, newUser.getHomeInfo().getAddress().getState());
				cstmt.setString(19, newUser.getHomeInfo().getAddress().getZip());
				cstmt.setString(20, newUser.getHomeInfo().getPhoneNumber().getNumber());
				cstmt.setString(21, newUser.getHomeInfo().getPhoneNumber().getExt());
				cstmt.setString(22, newUser.getHomeInfo().getPhoneNumber().getCC());
				cstmt.setString(23, newUser.getHomeInfo().getCurrentCity());
				cstmt.setString(24, newUser.getBusinessInfo().getAddress().getAddress1());
				cstmt.setString(25, newUser.getBusinessInfo().getAddress().getAddress2());
				cstmt.setString(26, newUser.getBusinessInfo().getAddress().getCity());
				cstmt.setString(27, newUser.getBusinessInfo().getAddress().getState());
				cstmt.setString(28, newUser.getBusinessInfo().getAddress().getZip());
				cstmt.setString(29, newUser.getBusinessInfo().getPhoneNumber().getNumber());
				cstmt.setString(30, newUser.getBusinessInfo().getPhoneNumber().getExt());
				cstmt.setString(31, newUser.getBusinessInfo().getPhoneNumber().getCC());
				cstmt.setString(32, newUser.getBusinessInfo().getCompany());
				cstmt.execute();
				cstmt.closeOnCompletion();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			returnID = getID(newUser.getPrimaryEmail().toString());
			if (!(newUser.getHighSchool()).isEmpty()) {
				sqlCreate = "CALL add_hs(" + "?, " // user_id//int
						+ "?);"; // high_school//String
				for (String hs : newUser.getHighSchool()) {
					try {
						conn = db.getConnection();
						cstmt = conn.prepareCall(sqlCreate);
						cstmt.setInt(1, returnID);
						cstmt.setString(2, hs);
						cstmt.execute();
						cstmt.closeOnCompletion();
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			if (!(newUser.getCollege()).isEmpty()) {
				sqlCreate = "CALL add_college(" + "?, " // user_id//int
						+ "?);"; // college//String
				for (String college : newUser.getCollege()) {
					try {
						conn = db.getConnection();
						cstmt = conn.prepareCall(sqlCreate);
						cstmt.setInt(1, returnID);
						cstmt.setString(2, college);
						cstmt.execute();
						cstmt.closeOnCompletion();
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			if (!(newUser.getSocialMedia()).isEmpty()) {
				sqlCreate = "CALL add_social(" + "?, " // user_id//int
						+ "?);"; // social_media//String
				for (SocialMedia social : newUser.getSocialMedia()) {
					try {
						conn = db.getConnection();
						cstmt = conn.prepareCall(sqlCreate);
						cstmt.setInt(1, returnID);
						cstmt.setString(2, social.getSocialMediaURL());
						cstmt.execute();
						cstmt.closeOnCompletion();
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			if (!(newUser.getAdditionalEmails()).isEmpty()) {
				sqlCreate = "CALL add_email(" + "?, " // user_id//int
						+ "?);"; // email//String
				for (String email : newUser.getAdditionalEmails()) {
					try {
						conn = db.getConnection();
						cstmt = conn.prepareCall(sqlCreate);
						cstmt.setInt(1, returnID);
						cstmt.setString(2, email);
						cstmt.execute();
						cstmt.closeOnCompletion();
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			if (!(newUser.getAdditionalNumbers()).isEmpty()) {
				sqlCreate = "CALL add_number(" + "?, " // user_id//int
						+ "?, " // num//String
						+ "?, " // ext//String
						+ "?);"; // cc//String

				for (PhoneNumber num : newUser.getAdditionalNumbers()) {
					try {
						conn = db.getConnection();
						cstmt = conn.prepareCall(sqlCreate);
						cstmt.setInt(1, returnID);
						cstmt.setString(2, num.getNumber());
						cstmt.setString(3, num.getExt());
						cstmt.setString(4, num.getCC());
						cstmt.execute();
						cstmt.closeOnCompletion();
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return returnID;
	}

	public boolean editAccount(User userToUpdate) {
		int id = userToUpdate.getUserid();
		if (userToUpdate.getPrimaryEmail() != null
				&& id != getID(userToUpdate.getPrimaryEmail())) {
			// Returns false if trying to update the primary email and it
			// matches to the id of an existing user
			return false;
		}
		String sqlEdit = "CALL edit_user(" + "?, " // user_id//Int 1
				+ "?, " // primary_email//String 2
				+ "?, " // first_name//String 3
				+ "?, " // middle_name//String 4
				+ "?, " // last_name//String 5
				+ "?, " // gender//Char 6
				+ "?, " // hometown//String 7
				+ "?, " // mobile_number//String 8
				+ "?, " // mobile_number_ext//String 9
				+ "?, " // mobile_country_code//String 10
				+ "?, " // picture//Blob-Image 11
				+ "?, " // birthday//Date 12
				+ "?, " // home_address1//String 13
				+ "?, " // home_address2//String 14
				+ "?, " // home_city//String 15
				+ "?, " // home_state//String 16
				+ "?, " // home_zip//String 17
				+ "?, " // home_number//String 18
				+ "?, " // home_number_ext//String 19
				+ "?, " // home_country_code//String 20
				+ "?, " // current_city//String 21
				+ "?, " // business_address1//String 22
				+ "?, " // business_address2//String 23
				+ "?, " // business_city//String 24
				+ "?, " // business_state//String 25
				+ "?, " // business_zip//String 26
				+ "?, " // business_number//String 27
				+ "?, " // business_number_ext//String 28
				+ "?, " // business_country_code//String 29
				+ "?);";// company//String 30

		DBConnection db = DBConnection.getInstance();
		try {
			Connection conn = db.getConnection();
			CallableStatement cstmt = conn.prepareCall(sqlEdit);
			cstmt.setInt(1, id);
			cstmt.setString(2, userToUpdate.getPrimaryEmail());
			cstmt.setString(3, userToUpdate.getName().getFirstName());
			cstmt.setString(4, userToUpdate.getName().getMiddleName());
			cstmt.setString(5, userToUpdate.getName().getLastName());
			cstmt.setString(6, (userToUpdate.getGender() + ""));
			cstmt.setString(7, userToUpdate.getHometown());
			cstmt.setString(8, userToUpdate.getMobileNumber().getNumber());
			cstmt.setString(9, userToUpdate.getMobileNumber().getExt());
			cstmt.setString(10, userToUpdate.getMobileNumber().getCC());
			cstmt.setString(11, userToUpdate.getPicture());
			cstmt.setDate(12, userToUpdate.getBirthday());
			cstmt.setString(13, userToUpdate.getHomeInfo()
					.getAddress()
					.getAddress1());
			cstmt.setString(14, userToUpdate.getHomeInfo()
					.getAddress()
					.getAddress2());
			cstmt.setString(15, userToUpdate.getHomeInfo()
					.getAddress()
					.getCity());
			cstmt.setString(16, userToUpdate.getHomeInfo()
					.getAddress()
					.getState());
			cstmt.setString(17, userToUpdate.getHomeInfo()
					.getAddress()
					.getZip());
			cstmt.setString(18, userToUpdate.getHomeInfo()
					.getPhoneNumber()
					.getNumber());
			cstmt.setString(19, userToUpdate.getHomeInfo()
					.getPhoneNumber()
					.getExt());
			cstmt.setString(20, userToUpdate.getHomeInfo()
					.getPhoneNumber()
					.getCC());
			cstmt.setString(21, userToUpdate.getHomeInfo()
					.getCurrentCity());
			cstmt.setString(22, userToUpdate.getBusinessInfo()
					.getAddress()
					.getAddress1());
			cstmt.setString(23, userToUpdate.getBusinessInfo()
					.getAddress()
					.getAddress2());
			cstmt.setString(24, userToUpdate.getBusinessInfo()
					.getAddress()
					.getCity());
			cstmt.setString(25, userToUpdate.getBusinessInfo()
					.getAddress()
					.getState());
			cstmt.setString(26, userToUpdate.getBusinessInfo()
					.getAddress()
					.getZip());
			cstmt.setString(27, userToUpdate.getBusinessInfo()
					.getPhoneNumber()
					.getNumber());
			cstmt.setString(28, userToUpdate.getBusinessInfo()
					.getPhoneNumber()
					.getExt());
			cstmt.setString(29, userToUpdate.getBusinessInfo()
					.getPhoneNumber()
					.getCC());
			cstmt.setString(30, userToUpdate.getBusinessInfo()
					.getCompany());
			cstmt.execute();
			cstmt.closeOnCompletion();
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addFriend(Access newFriend) {
		String sqlAddFriend = "CALL friend_new(" 
				+ "?, " // user1_id
				+ "?, " // user2_id
				+ "?, " // primary_email_access
				+ "?, " // name_access
				+ "?, " // gender_access
				// + "?, " //hometown_access//PUBLIC
				+ "?, " // mobile_number_access
				+ "?, " // picture_access
				+ "?, " // registered_access
				+ "?, " // birthday_access
				+ "?, " // businessAddress_access
				+ "?, " // businessNumber_access
				// + "?, " //company_access//PUBLIC
				+ "?, " // homeAddress_access
				+ "?, " // homeNumber_access
				+ "?, " // add_emails_access
				+ "?, " // add_nums_access
				+ "?)";// social_media_access

		DBConnection db = DBConnection.getInstance();
		try {
			Connection conn = db.getConnection();
			CallableStatement cstmt = conn.prepareCall(sqlAddFriend);
			cstmt.setInt(1, newFriend.user1_id);
			cstmt.setInt(2, newFriend.user1_id);
			cstmt.setBoolean(3, newFriend.primaryEmail);
			cstmt.setBoolean(4, true);//name
			cstmt.setBoolean(5, newFriend.gender);
			cstmt.setBoolean(6, newFriend.mobileNumber);
			cstmt.setBoolean(7, newFriend.picture);
			cstmt.setBoolean(8, false);//registered
			cstmt.setBoolean(9, newFriend.birthday);
			cstmt.setBoolean(10, newFriend.businessAddress);
			cstmt.setBoolean(11, newFriend.businessNumber);
			cstmt.setBoolean(12, newFriend.homeAddress);
			cstmt.setBoolean(13, newFriend.homeNumber);
			cstmt.setBoolean(14, newFriend.additionalEmails);
			cstmt.setBoolean(15, newFriend.additionalNumbers);
			cstmt.setBoolean(16, newFriend.socialMedia);
			cstmt.execute();
			cstmt.closeOnCompletion();
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean acceptFriend(int connectionID){
		DBConnection db = DBConnection.getInstance();
		String sqlAcceptFriend = "UPDATE connections SET isActive=? WHERE connections_id=?;";
		try {
			Connection conn = db.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement(sqlAcceptFriend);
			pstmt.setBoolean(1, true);
			pstmt.setInt(2, connectionID);
			pstmt.executeUpdate();
			pstmt.closeOnCompletion();
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeFriend(int connectionID){
		String sqlRemoveFriend = "CALL friend_del(?);";
		DBConnection db = DBConnection.getInstance();
		try{
			Connection conn = db.getConnection();
			CallableStatement cstmt = conn.prepareCall(sqlRemoveFriend);
			cstmt.setInt(1, connectionID);
			cstmt.execute();
			cstmt.closeOnCompletion();
			conn.close();
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Friends getFriends(int user_id){
		DBConnection db = DBConnection.getInstance();
		List<Integer> friendIDs = new LinkedList<Integer>();
		List<Integer> connectionsIDs = new LinkedList<Integer>();
		List<String> friendNames = new LinkedList<String>();
		try {
			String query = "SELECT user1_id, user2_id, connections_id FROM connections WHERE (user1_id=? OR user2_id=?);";
			Connection conn = db.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, user_id);
			ResultSet result = pstmt.executeQuery();
			
			while (result.next()) {
				if(result.getInt("user1_id")!=user_id){
					friendIDs.add(result.getInt("user1_id"));
				}
				else{
					friendIDs.add(result.getInt("user2_id"));
				}
				connectionsIDs.add(result.getInt("connections_id"));
			}
			result.close();
			pstmt.closeOnCompletion();
			conn.close();
			for(int friendID : friendIDs){
				query = "SELECT first_name, middle_name, last_name WHERE user_id=? LIMIT 1;";
				conn = db.getConnection();
				pstmt = conn
						.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, friendID);
				result = pstmt.executeQuery();
				while (result.next()) {
					String name = result.getString("first_name")
							+ " " + result.getString("middle_name")
							+ " " + result.getString("last_name");
					friendNames.add(name);
				}
				result.close();
				pstmt.closeOnCompletion();
				conn.close();
			}
			return (new Friends(friendIDs, connectionsIDs, friendNames));
		} catch(SQLException e){
			return null;
		}
	}
	
	public List<Notification> getNotifications(int user_id){
		String query;
		DBConnection db = DBConnection.getInstance();
		Connection conn;
		ResultSet result;
		List<Notification> notifications = new LinkedList<Notification>();
		
		try {
			query = "SELECT notification_id, connection_id, isGroup FROM notification WHERE user_id=?;";
			conn = db.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, user_id);
			result = pstmt.executeQuery();
			
			while (result.next()) {
				notifications.add(
						new Notification(
								result.getInt(1),
								result.getInt(2),
								result.getBoolean(3)
								)
						);
			}
			result.close();
			pstmt.closeOnCompletion();
			conn.close();
			return notifications;
		} catch(SQLException e){
			return null;
		}
	}
	
	public boolean acceptNotification(Notification notif){
		if(!notif.isGroup()){
			if(acceptFriend(notif.getConnectionID()))
				return removeNotification(notif.getNotificationID());
		}
		else{
			if(new Group(-1).confirmMember(notif.getConnectionID(), true))
				return removeNotification(notif.getNotificationID());
		}
		return false;
	}
	
	public boolean denyNotification(Notification notif){
		if(!notif.isGroup()){
			if(removeFriend(notif.getConnectionID()))
				return removeNotification(notif.getNotificationID());
		}
		else{
			if(new Group(-1).confirmMember(notif.getConnectionID(), false))
				return removeNotification(notif.getNotificationID());
		}
		return false;
	}
	
	private boolean removeNotification(int notifID){
		DBConnection db = DBConnection.getInstance();
		String sqlRemoveNotification = "DELETE FROM notification WHERE notification_id=?;";
		try {
			Connection conn = db.getConnection();
			PreparedStatement preparedStatement = conn
					.prepareStatement(sqlRemoveNotification);
			preparedStatement.setInt(1, notifID);
			preparedStatement.execute();
			preparedStatement.closeOnCompletion();
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static int insertGroup(String groupName, String groupDescription, int sharedInfo)
			throws SQLException {
		DBConnection db = DBConnection.getInstance();
		// insert a new group
		String groupInsert = "INSERT INTO groups(name,description,isActive,shared_info)  VALUES(?,?,?,?);";
		PreparedStatement preparedStatement = db.getConnection()
				.prepareStatement(groupInsert, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, groupName);
		preparedStatement.setString(2, groupDescription);
		preparedStatement.setInt(3, 1);
		preparedStatement.setInt(4, sharedInfo);

		preparedStatement.executeUpdate();

		int groupID = -1;
		ResultSet result = preparedStatement.getGeneratedKeys();
		while (result.next()) {
			groupID = result.getInt(1);
		}
		preparedStatement.closeOnCompletion();
		db.closeConnection();

		return groupID;
	}

	private static void insertGroupMember(int groupID, int userID)
			throws SQLException {
		DBConnection db = DBConnection.getInstance();
		String groupMemberInsert = "INSERT INTO group_members(group_id,user_id,access) VALUES (?,?,1);";
		PreparedStatement preparedStatement = db.getConnection()
				.prepareStatement(groupMemberInsert);
		preparedStatement.setInt(1, groupID);
		preparedStatement.setInt(2, userID);
		preparedStatement.executeUpdate();
		preparedStatement.closeOnCompletion();
		db.closeConnection();

	}

	// takes in the user who is going to make the group
	// returns the group id
	public static int createGroup(int userID, String groupName,
			String groupDescription, int SharedInfo) throws SQLException {
		DBConnection db = DBConnection.getInstance();
		int groupID = insertGroup(groupName, groupDescription, SharedInfo);
		// get the last inserted id, which would be the group_id
		// Insert that user id into the group
		if (groupID != -1) {
			insertGroupMember(groupID, userID);
		}
		db.closeConnection();

		return groupID;
	}

	private static Group selectGroup(int id) throws SQLException {
		DBConnection db = DBConnection.getInstance();

		String name = "";
		String description = "";
		int groups_id = -1;
		int shared_info = -1;
		boolean active = false;
		String groupSelect = "SELECT * FROM groups WHERE groups_id = ?;";
		PreparedStatement preparedStatement = db.getConnection()
				.prepareStatement(groupSelect);
		preparedStatement.setInt(1, id);
		ResultSet result = preparedStatement.executeQuery();
		while (result.next()) {
			name = result.getString("name");
			description = result.getString("description");
			groups_id = result.getInt("groups_id");
			shared_info = result.getInt("shared_info");
			active = result.getBoolean("isActive");
		}
		if (groups_id == -1)
			return null;
		Group group = new Group(name, description, groups_id, shared_info,
				active, selectGroupMembers(id));
		// preparedStatement.closeOnCompletion();
		db.closeConnection();

		return group;

	}

	private static ArrayList<GroupMember> selectGroupMembers(int id)
			throws SQLException {
		DBConnection db = DBConnection.getInstance();
		String groupMemberSelect = "SELECT * FROM group_members WHERE group_id = ?;";
		PreparedStatement preparedStatement = db.getConnection()
				.prepareStatement(groupMemberSelect);
		preparedStatement.setInt(1, id);
		ResultSet result = preparedStatement.executeQuery();
		ArrayList<GroupMember> groupMembers = new ArrayList<GroupMember>();
		while (result.next()) {
			groupMembers.add(new GroupMember(result.getInt("user_id"), result
					.getInt("access"), result.getInt("group_members_id"),
					result.getInt("group_id")));
		}
		preparedStatement.closeOnCompletion();
		db.closeConnection();
		return groupMembers;
	}

	public static Group findGroup(int id) throws SQLException {
		Group group = selectGroup(id);
		DBConnection.getInstance().closeConnection();

		return group;
	}

	static public List<User> listUsers(List<Integer> findIDs, int currentUserID) {
		List<User> usersList = new LinkedList<User>();
		for (int userID : findIDs) {
			usersList.add(findUser(userID, currentUserID));
		}
		return usersList;
	}
	
	//Sending currentUserID=1 is public
	//Returns a User object based on the current users access level/shared info
	static public User findUser(int findID, int currentUserID) {
		if(findID == currentUserID) return findCurrentUser(findID);
		
		UserBuilder userbuild = new User.UserBuilder();
		List<String> high_school = new LinkedList<String>();
		List<String> college = new LinkedList<String>();
		List<SocialMedia> socialMedia = new LinkedList<SocialMedia>();
		List<String> additionalEmails = new LinkedList<String>();
		List<PhoneNumber> additionalNumbers = new LinkedList<PhoneNumber>();
		
		String query;
		DBConnection db = DBConnection.getInstance();
		Connection conn;
		ResultSet result;
		Access connection = new Access();
		
		try {
			//This query either returns the friend's access or their public access
//			query = "SELECT * FROM view_access WHERE (user_id1=" + findID
//					+ " AND user2_id=" + currentUserID + ") OR (user_id1=" + currentUserID
//					+ " AND user2_id=" + findID + ") OR (user_id1=" + currentUserID 
//					+ " AND user2_id=1) ORDER BY user2_id DESC LIMIT 1;";
			//This query does not have the public access backup
			query = "SELECT * FROM view_access WHERE (user_id1=" + findID
					+ " AND user2_id=" + currentUserID + ") OR (user_id1=" + currentUserID
					+ " AND user2_id=" + findID + ")  LIMIT 1;";
			conn = db.getConnection();
			result = conn
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			while (result.next()) {
				connection.primaryEmail = (result.getBoolean("primary_email"));
				connection.gender = (result.getBoolean("gender"));
				connection.mobileNumber = (result.getBoolean("mobile_number"));
				connection.picture = (result.getBoolean("picture"));
				connection.birthday = (result.getBoolean("birthday"));
				connection.businessAddress = (result.getBoolean("business_address"));
				connection.businessNumber = (result.getBoolean("business_number"));
				connection.homeAddress = (result.getBoolean("home_address"));
				connection.homeNumber = (result.getBoolean("home_number"));
				connection.additionalEmails = (result.getBoolean("additional_emails"));
				connection.additionalNumbers = (result.getBoolean("additional_numbers"));
				connection.socialMedia = (result.getBoolean("social_media"));
			}
			result.close();
			conn.close();
		
			query = "SELECT * FROM user_data WHERE user_id=" + findID
					+ " LIMIT 1;";
			conn = db.getConnection();
			result = conn
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			while (result.next()) {
				if(connection.primaryEmail)
					userbuild = userbuild.primaryEmail(result.getString("primary_email"));
				if(connection.gender)
					userbuild = userbuild.gender(result.getString("gender"));
					
				userbuild = userbuild.name(new Name(result
					.getString("first_name"), result
					.getString("last_name"), result
					.getString("middle_name")));
				if(connection.mobileNumber)
					userbuild = userbuild.mobileNumber(new PhoneNumber(result
										.getString("mobile_number"), result
										.getString("mobile_number_ext"), result
										.getString("mobile_country_code")));
				if(connection.picture)
					userbuild = userbuild.picture(null);
				if(connection.birthday)
					userbuild = userbuild.birthday(result.getDate("birthday"));

				BusinessInfo busInfo = new BusinessInfo();
				busInfo.setCompany(result.getString("company"));
				if(connection.businessAddress)
					busInfo.setAddress(new Address(result
						.getString("business_address1"), result
						.getString("business_address2"), result
						.getString("business_city"), result
						.getString("business_state"), result
						.getString("business_zip")));
				if(connection.businessNumber)
					busInfo.phoneNumber(new PhoneNumber(result
						.getString("business_number"), result
						.getString("business_number_ext"), result
						.getString("business_country_code")));

				HomeInfo homInfo = new HomeInfo();
				homInfo.setCurrentCity(result.getString("current_city"));
				if(connection.homeAddress)
					homInfo.setAddress(new Address(result
						.getString("home_address1"), result
						.getString("home_address2"), result
						.getString("home_city"),
						result.getString("home_state"), result
								.getString("home_zip")));
				if(connection.homeNumber)
					homInfo.phoneNumber(new PhoneNumber(result
						.getString("home_number"), result
						.getString("home_number_ext"), result
						.getString("home_country_code")));
				
				userbuild = userbuild
						.userid(findID)
						.businessInfo(busInfo)
						.homeInfo(homInfo)
						.hometown(result.getString("hometown"))
						.registered(result.getDate("registered"));
			}
			result.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			query = "SELECT high_school FROM user_high_school WHERE user_id="
					+ findID + ";";
			conn = db.getConnection();
			result = conn
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery(query);

			while (result.next()) {
				high_school.add(result.getString("high_school"));
			}
			userbuild = userbuild.highSchool(high_school);
			result.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			query = "SELECT college FROM user_college WHERE user_id=" + findID
					+ ";";
			conn = db.getConnection();
			result = conn
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery(query);

			while (result.next()) {
				college.add(result.getString("college"));
			}
			userbuild = userbuild.college(college);
			result.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(connection.socialMedia){
			try {
				query = "SELECT media_link FROM social_media WHERE user_id="
						+ findID + ";";
				conn = db.getConnection();
				result = conn
						.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY).executeQuery(query);
	
				while (result.next()) {
					socialMedia
							.add(new SocialMedia(result.getString("media_link")));
				}
				userbuild = userbuild.socialMedia(socialMedia);
				result.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(connection.additionalEmails){
			try {
				query = "SELECT email FROM additional_emails WHERE user_id="
						+ findID + ";";
				conn = db.getConnection();
				result = conn
						.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY).executeQuery(query);
	
				while (result.next()) {
					additionalEmails.add(result.getString("email"));
				}
				userbuild = userbuild.additionalEmails(additionalEmails);
				result.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(connection.additionalNumbers){
			try {
				query = "SELECT number, number_extension, number_country_code "
						+ "FROM additional_numbers WHERE user_id=" + findID + ";";
				conn = db.getConnection();
				result = conn
						.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY).executeQuery(query);
	
				while (result.next()) {
					additionalNumbers.add(new PhoneNumber(result
							.getString("number"), result
							.getString("number_extension"), result
							.getString("number_country_code")));
				}
				userbuild = userbuild.additionalNumbers(additionalNumbers);
				result.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userbuild.build();
	}

	// Returns the current user object
	static private User findCurrentUser(int findID) {
		UserBuilder userbuild = new User.UserBuilder();
		List<String> high_school = new LinkedList<String>();
		List<String> college = new LinkedList<String>();
		List<SocialMedia> socialMedia = new LinkedList<SocialMedia>();
		List<String> additionalEmails = new LinkedList<String>();
		List<PhoneNumber> additionalNumbers = new LinkedList<PhoneNumber>();
		String query;
		DBConnection db = DBConnection.getInstance();
		Connection conn;
		ResultSet result;

		try {
			query = "SELECT * FROM user_data WHERE user_id=" + findID
					+ " LIMIT 1;";
			conn = db.getConnection();
			result = conn
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			while (result.next()) {

				BusinessInfo busInfo = new BusinessInfo();
				busInfo.setAddress(new Address(result
						.getString("business_address1"), result
						.getString("business_address2"), result
						.getString("business_city"), result
						.getString("business_state"), result
						.getString("business_zip")));

				busInfo.setCompany(result.getString("company"));
				busInfo.phoneNumber(new PhoneNumber(result
						.getString("business_number"), result
						.getString("business_number_ext"), result
						.getString("business_country_code")));

				HomeInfo homInfo = new HomeInfo();
				homInfo.setAddress(new Address(result
						.getString("home_address1"), result
						.getString("home_address2"), result
						.getString("home_city"),
						result.getString("home_state"), result
								.getString("home_zip")));

				homInfo.setCurrentCity(result.getString("current_city"));
				homInfo.phoneNumber(new PhoneNumber(result
						.getString("home_number"), result
						.getString("home_number_ext"), result
						.getString("home_country_code")));

				userbuild = userbuild
						.userid(findID)
						.primaryEmail(result.getString("primary_email"))
						.gender(result.getString("gender"))
						.businessInfo(busInfo)
						.homeInfo(homInfo)
						.name(new Name(result.getString("first_name"), result
								.getString("last_name"), result
								.getString("middle_name")))
						.hometown(result.getString("hometown"))
						.mobileNumber(
								new PhoneNumber(result
										.getString("mobile_number"), result
										.getString("mobile_number_ext"), result
										.getString("mobile_country_code")))
						.picture(null).registered(result.getDate("registered"))
						.birthday(result.getDate("birthday"));
			}
			result.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			query = "SELECT high_school FROM user_high_school WHERE user_id="
					+ findID + ";";
			conn = db.getConnection();
			result = conn
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery(query);

			while (result.next()) {
				high_school.add(result.getString("high_school"));
			}
			userbuild = userbuild.highSchool(high_school);
			result.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			query = "SELECT college FROM user_college WHERE user_id=" + findID
					+ ";";
			conn = db.getConnection();
			result = conn
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery(query);

			while (result.next()) {
				college.add(result.getString("college"));
			}
			userbuild = userbuild.college(college);
			result.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			query = "SELECT media_link FROM social_media WHERE user_id="
					+ findID + ";";
			conn = db.getConnection();
			result = conn
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery(query);

			while (result.next()) {
				socialMedia
						.add(new SocialMedia(result.getString("media_link")));
			}
			userbuild = userbuild.socialMedia(socialMedia);
			result.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			query = "SELECT email FROM additional_emails WHERE user_id="
					+ findID + ";";
			conn = db.getConnection();
			result = conn
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery(query);

			while (result.next()) {
				additionalEmails.add(result.getString("email"));
			}
			userbuild = userbuild.additionalEmails(additionalEmails);
			result.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			query = "SELECT number, number_extension, number_country_code "
					+ "FROM additional_numbers WHERE user_id=" + findID + ";";
			conn = db.getConnection();
			result = conn
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery(query);

			while (result.next()) {
				additionalNumbers.add(new PhoneNumber(result
						.getString("number"), result
						.getString("number_extension"), result
						.getString("number_country_code")));
			}
			userbuild = userbuild.additionalNumbers(additionalNumbers);
			result.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userbuild.build();
	}

	// This should send an email with a random password
	static public boolean resetPW(Login resetUser) throws SQLException {
		String query = "UPDATE gateway SET password='"
				+ resetUser.getPassword() + "' WHERE email='"
				+ resetUser.getEmail() + "';";
		DBConnection db = DBConnection.getInstance();
		Connection conn = db.getConnection();
		int code = -1;
		code = conn.createStatement().executeUpdate(query);
		conn.close();
		if (code == 0 || code == -1) {
			return false;
		} else
			return true;
	}

	// Edits password -- This ill be used after resetPW changes to send
	// confirmation email
	// For now exactly the same
	public boolean editPW(Login oldPass, Login newPass) throws SQLException {
		int id = checkPass(oldPass);
		int code = -1;
		if (id > 0 && oldPass.getEmail().equals(newPass.getEmail())) {
			String query = "UPDATE gateway SET password='"
					+ newPass.getPassword() + "' WHERE email='"
					+ newPass.getEmail() + "';";
			DBConnection db = DBConnection.getInstance();
			Connection conn = db.getConnection();
			code = conn.createStatement().executeUpdate(query);
			conn.close();
		}
		if (code == 0 || code == -1) {
			return false;
		} else
			return true;
	}

	public boolean deactivate(Login checkPassword) throws SQLException {
		int id = checkPass(checkPassword);
		String query = "UPDATE users SET isActive=0 WHERE user_id='" + id
				+ "';";
		DBConnection db = DBConnection.getInstance();
		Connection conn = db.getConnection();
		conn.createStatement().executeUpdate(query);
		conn.close();
		return true;
	}

	static private void reactivate(int id) throws SQLException {
		String query = "UPDATE users SET isActive=1 WHERE user_id='" + id
				+ "';";
		DBConnection db = DBConnection.getInstance();
		Connection conn = db.getConnection();
		conn.createStatement().executeUpdate(query);
		conn.close();
	}
	
	public String FBAccessToken(String id, String secret){
		//not complete yet, but compiles
		// Make the configuration builder
				ConfigurationBuilder confBuilder = new ConfigurationBuilder();
				confBuilder.setDebugEnabled(true);

				// Set application id, secret key and access token
				// THIS INFORMATION MUST BE SUPPLIED FROM THE FRONT-END.
				// Can be either iOS or Android.
				// Don't know anything about web front end though
		        confBuilder.setOAuthAppId(id);
		        confBuilder.setOAuthAppSecret(secret);

		        // Set permission
		        // Extracts a user's basic information plus friends list
		        confBuilder.setOAuthPermissions("basic_info");
		        
		        // Additional stuff
		        confBuilder.setUseSSL(true);
		        confBuilder.setJSONStoreEnabled(true);
		        
		        // Create configuration object
		        Configuration configuration = confBuilder.build();

		        // Create facebook instance
		        FacebookFactory ff = new FacebookFactory(configuration);
		        Facebook facebook = ff.getInstance();
		        AccessToken accessToken = null;
		        try{
		            OAuthSupport oAuthSupport = new OAuthAuthorization(configuration );
		            accessToken = oAuthSupport.getOAuthAppAccessToken();
		        }catch (FacebookException e) {
		            System.err.println("Error while creating access token " + e.getLocalizedMessage());
		        }
		        facebook.setOAuthAccessToken(accessToken);
		        return accessToken.getToken();
	}
}
