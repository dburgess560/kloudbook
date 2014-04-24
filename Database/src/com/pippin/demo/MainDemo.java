package com.pippin.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pippin.Access;
import com.pippin.Friends;
import com.pippin.Name;
import com.pippin.Notification;
import com.pippin.User;
import com.pippin.database.Profile;

public class MainDemo {

	public static void main(String[] args) {

		Profile prof = new Profile();
		ItemCreator builder = new ItemCreator();
		User jim = builder.createJim().build();
		User julie = builder.createJulie().build();
		builder.deleteUser(jim);
		builder.deleteUser(julie);
		
		int jims_id = Profile.createAccount(jim);
		
		int julies_id = Profile.createAccount(julie);
		
		//edit Julie's name
		
		Name julieMarried = new Name("Julie","Anne","Marshal");
		
		User newJulie = new User.UserBuilder().userid(julies_id).name(julieMarried).build();
		
		prof.editAccount(newJulie);
		
		Access julieToJim = builder.newAccess(julies_id, jims_id);
		prof.addFriend(julieToJim);
		
		List<Notification> jimsNotifs = prof.getNotifications(jims_id);
		for(Notification notif : jimsNotifs){
			prof.acceptNotification(notif);
		}
		
		
		Gson gson = new Gson();
		
		Writer writer;
		try {
			writer = new FileWriter("C:\\Users\\David\\Dropbox\\School\\"
					+ "Programs\\KloudBook\\Documents\\Pippin\\API\\FakeJsons\\notif_out.txt");

			gson.toJson(jimsNotifs, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
