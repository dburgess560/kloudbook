//rhromada

package com.pippin.database;

/**
 * The object that holds each members data in group
 * 
 * @author Robert
 */
public class GroupMember {

	private int memberID;
	private int accessLevel;
	private int groupID;
	private int userID;

	private final int GROUPADMINACCESS = 1;
	private final int GROUPMEMBERACCESS = 0;
	private final int INACTIVEMEMBERACCESS = -1;

	/**
	 * The constructor of GroupMember objects used in Group class Takes in the
	 * user and its access and creates the object 0 is member, 1 is admin, -1 is
	 * inactive
	 */
	public GroupMember(int userID, int access, int memberID, int groupID) {
		this.accessLevel = access;
		this.memberID = memberID;
		this.groupID = groupID;
		this.userID = userID;
	}

	/**
	 * changes this groupMember's access level.
	 */
	public void setAccess(int newAccess) {
		accessLevel = newAccess;
	}

	/**
	 * 0 is member, 1 is admin, -1 is inactive
	 */
	public int getAccessLevel() {
		return accessLevel;
	}

	// Some basic getters
	public int getUserID() {
		return userID;
	}

	public int getMemberID() {
		return memberID;
	}

	public boolean isAdmin() {
		return (accessLevel == GROUPADMINACCESS);
	}

	public int getGroupID() {
		return groupID;
	}

	public boolean isActive() {
		return (accessLevel != INACTIVEMEMBERACCESS);
	}
}