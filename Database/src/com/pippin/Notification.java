package com.pippin;

public class Notification {
	
	int notification_id;
	int connection_id;
	boolean isGroup; //If isGroup=TRUE then connection_id==group_members_id
	
	public Notification(int notification_id, int connection_id, boolean isGroup){
		this.notification_id=notification_id;
		this.connection_id=connection_id;
		this.isGroup=isGroup;
	}

	public int getNotificationID(){
		return notification_id;
	}
	
	public int getConnectionID(){
		return connection_id;
	}
	
	public boolean isGroup(){
		return isGroup;
	}
}
//	
//	/*
//	 * This is when someone requests you to be your friend
//	 */
//	public Notifications(int user2_id){
//		pendingFriend = user2_id;
//	}
//	
//	/*
//	 * This is when an administrator requests you to join their group
//	 */
//	public Notifications(int user2_id, int groupID){
//		pendingFriend = user2_id;
//		pendingGroup = groupID;
//	}
//	
//	public int getPending(){
//		return pendingFriend;
//	}
//	
//	public int pendingGroup(){
//		return pendingGroup;
//	}
//	
//}
