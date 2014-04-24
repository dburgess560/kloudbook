package com.pippin;

import java.sql.SQLException;

import com.pippin.database.Group;

public class Connections {
	
	String name;
	int id;
	boolean isGroup=false;
	
	public Connections(){
		
	}
	
	public Connections(String name, int id){
		this.name=name;
		this.id=id;
		this.isGroup=false;
	}
	
	public Connections(String name, int id, boolean isGroup){
		this.name=name;
		this.id=id;
		this.isGroup=isGroup;
	}
	
	public Connections(User user){
		name = user.getName().toString();
		id = user.getUserid();
	}
	
	public Connections(Group group){
		try {
			name = group.getName();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		id = group.getGroupID();
		isGroup=true;
	}
	
	public String getName(){
			return name;
	}
	
	public int getID(){
		return id;
	}
	
	public boolean isGroup(){
		return isGroup;
	}
	
}
