package com.pippin.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.stream.JsonReader;
import com.pippin.User;

public class Search {

	public Search() {
	}

	public List<User> parseJsonPublicSearch(JsonReader jreader)
			throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		String name = "";
		LinkedList<Integer> results = null;
		List<User> uList = null;
		try {
			while (jreader.hasNext()) {
				String tempName = jreader.nextName();
				if (tempName.equals("query"))
					map.put("query", jreader.nextString());
				else if (tempName.equals("email"))
					map.put("email", jreader.nextString());
				else if (tempName.equals("gender"))
					map.put("gender", jreader.nextString());
				// else if(name.equals("phonenumber"))
				// map.put("phonenumber", jreader.nextString());
				else if (tempName.equals("company"))
					map.put("company", jreader.nextString());
				else if (tempName.equals("currentcity"))
					map.put("currentcity", jreader.nextString());
				else if (tempName.equals("firstname"))
					name = jreader.nextString();
				else if (tempName.equals("middlename"))
					name = name + " " + jreader.nextString();
				else if (tempName.equals("lastname"))
					name = name + " " + jreader.nextString();
			}
			name = name.trim();
			map.put("name", name);
			if (map.containsKey("query") || map.containsKey("email")
					|| map.containsKey("gender") || map.containsKey("company")
					|| map.containsKey("currentcity")) {
				results = advancedSearch(map);
			} else {
				results = basicSearch(name);
			}

			uList = Profile.listUsers(results, 0);
			try {
			} finally {
				jreader.close();
			}
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uList;
	}
	public List<User> parseJsonConnectionsSearch(JsonReader jreader, int userID)
			throws IOException {
		String name = "";
		LinkedList<Integer> results = null;
		List<User> uList = null;
		while (jreader.hasNext()) {
			String tempName = jreader.nextName();
			if (tempName.equals("firstname"))
				name = jreader.nextString();
			else if (tempName.equals("middlename"))
				name = name + " " + jreader.nextString();
			else if (tempName.equals("lastname"))
				name = name + " " + jreader.nextString();
		}
		name = name.trim();
		try {
			results = basicSearch(name, userID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		uList = Profile.listUsers(results, userID);
		jreader.close();
		return uList;
	}
	public LinkedList<Integer> groupSearch(String text) throws SQLException {
		DBConnection db = DBConnection.getInstance();
		Connection conn = db.getConnection();
		LinkedList<Integer> groupIDs = new LinkedList<Integer>();
		PreparedStatement preparedStatement = groupQuery(text.split(" "), conn);
		ResultSet queryResults = preparedStatement.executeQuery();
		while (queryResults.next()) {
			groupIDs.add(queryResults.getInt("groups_id"));
		}
		queryResults.close();
		preparedStatement.close();
		conn.close();
		db.closeConnection();
		return groupIDs;

	}

	/**
	 * returns all connected user ids of the given user id that might match the
	 * first, last and/or middle name
	 * 
	 * @param text
	 *            The text given by the client.
	 * @param userID
	 *            Searches for only the connections to the userID
	 * @return User IDs
	 */
	public LinkedList<Integer> basicSearch(String text, int userID)
			throws SQLException {
		if (text == "") {
			return null;
		}
		DBConnection db = DBConnection.getInstance();
		Connection conn = db.getConnection();
		LinkedList<Integer> userIDs = new LinkedList<Integer>();
		PreparedStatement preparedStatement = nameQuery(text.split(" "), conn,
				userID);
		ResultSet queryResults = preparedStatement.executeQuery();
		while (queryResults.next()) {
			int id;
			if ((id = queryResults.getInt("user_id")) != userID && id > 1)
				userIDs.add(id);
		}
		queryResults.close();
		preparedStatement.close();
		conn.close();
		db.closeConnection();
		return userIDs;

	}

	/**
	 * returns all public user ids of users that might match the first, last
	 * and/or middle name
	 * 
	 * @param text
	 *            The text given by the client.
	 * @return User IDs
	 */
	public LinkedList<Integer> basicSearch(String text) throws SQLException,
	InstantiationException, IllegalAccessException,
	ClassNotFoundException {
		if (text == "") {
			return null;
		}
		DBConnection db = DBConnection.getInstance();
		Connection conn = db.getConnection();
		LinkedList<Integer> userIDs = new LinkedList<Integer>();
		PreparedStatement preparedStatement = nameQuery(text.split(" "), conn, -1);
		ResultSet queryResults = preparedStatement.executeQuery();

		while (queryResults.next()) {
			userIDs.add(queryResults.getInt("user_id"));
		}

		queryResults.close();
		preparedStatement.close();
		conn.close();
		db.closeConnection();
		return userIDs;
	}

	// might not need depending
	private PreparedStatement groupQuery(String[] keywords, Connection conn)
			throws SQLException {
		String textStmt = "";
		for (String text : keywords) {
			textStmt += "%" + text + "%";
		}
		String query = "SELECT DISTINCT groups_id " + "FROM groups "
				+ "WHERE name COLLATE UTF8_GENERAL_CI LIKE ?";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, textStmt);
		return preparedStatement;
	}

	private PreparedStatement nameQuery(String[] keywords, Connection conn,
			int userID) throws SQLException {
		if (keywords == null)
			return null;

		String textStmt = "";
		for (String text : keywords) {
			textStmt += "%" + text + "%";
		}
		String query = "SELECT DISTINCT user_id "
				+ "FROM users, connections "
				+ "WHERE CONCAT(COALESCE(first_name, ''),' ' ,COALESCE(middle_name, ''),' ',COALESCE(last_name,'')) COLLATE UTF8_GENERAL_CI LIKE ? "
				+ "AND connections.first_name_access = 1 ";
		if (userID <= 1) {
			query += "AND connections.user1_id = users.user_id "
					+ "AND connections.user2_id = 1;";
		} else {
			query += "AND (users.user_id = connections.user1_id OR users.user_id = connections.user2_id) "
					+ "AND (connections.user1_id = ? OR connections.user2_id = ?);";

		}
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, textStmt);
		if (userID > 1) {// If userID is not valid (-1) nor the public id (1)
			preparedStatement.setInt(2, userID);
			preparedStatement.setInt(3, userID);
		}

		// System.out.println(preparedStatement.toString());
		return preparedStatement;
	}

	private ArrayList<SearchStatement> advancedQuery(
			HashMap<String, String> fieldHashMap) {
		ArrayList<SearchStatement> searchStmts = new ArrayList<SearchStatement>();
		for (Entry entry : fieldHashMap.entrySet()) {
			SearchStatement searchStmt = null;
			String fieldTable = "";
			String connectionTable = "";
			String stmt = "";
			String[] keywords = entry.getValue().toString().split(" ");
			switch (entry.getKey().toString().toLowerCase()) {
			case "name": // looking for each case string
				fieldTable = "users";
				connectionTable = "connections";
				stmt = "CONCAT(COALESCE(first_name, ''),' ' ,COALESCE(middle_name, ''),' ',COALESCE(last_name,'')) COLLATE UTF8_GENERAL_CI LIKE ? AND connections.user1_id = users.user_id AND connections.user2_id = 1 AND connections.first_name_access = 1";
				// System.out.println(query.toString());
				break;
			case "high school":
				fieldTable = "user_high_school";
				connectionTable = "user_high_school_connections";
				stmt = "high_school LIKE ? AND user_high_school_connections.connections_id = connections.connections_id AND user_high_school_connections.high_school_access = 1";
				// System.out.println(query.toString());
				break;
			case "college":
				fieldTable = "user_college";
				connectionTable = "user_college_connections";
				stmt = "college COLLATE UTF8_GENERAL_CI LIKE ? AND user_college_connections.connections_id = connections.connections_id AND user_college_connections.college_access = 1";
				// System.out.println(query.toString());
				break;
			case "company":
				fieldTable = "user_businessinfo";
				connectionTable = "user_businessinfo_connections";
				stmt = "company COLLATE UTF8_GENERAL_CI LIKE ? AND user_businessinfo_connections.connections_id = connections.connections_id AND user_businessinfo_connections.company_access = 1";
				// System.out.println(query.toString());
				break;
			case "current city":
				fieldTable = "user_homeinfo";
				connectionTable = "user_homeinfo_connections";
				stmt = "current_city COLLATE UTF8_GENERAL_CI LIKE ? AND user_homeinfo_connections.connections_id = connections.connections_id AND user_homeinfo_connections.current_city_access = 1";
				// System.out.println(query.toString());
				break;
			case "hometown":
				fieldTable = "users";
				connectionTable = "connections";
				stmt = "hometown COLLATE UTF8_GENERAL_CI LIKE ? AND connections.user1_id = users.user_id AND connections.user2_id = 1 AND connections.hometown_access = 1";
				// System.out.println(query.toString());
				break;
			case "email address":
				fieldTable = "users";
				connectionTable = "connections";
				stmt = "primary_email COLLATE UTF8_GENERAL_CI LIKE ? AND connections.user1_id = users.user_id AND connections.user2_id = 1 AND connections.primary_email_access = 1";
				// System.out.println(query.toString());
				break;
			}
			searchStmt = new SearchStatement(fieldTable, connectionTable, stmt,
					keywords);
			searchStmts.add(searchStmt);
		}
		return searchStmts;
	}

	public LinkedList<Integer> advancedSearch(
			HashMap<String, String> fieldHashMap) throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		if (fieldHashMap == null)
			return null;
		DBConnection db = DBConnection.getInstance();
		Connection conn = db.getConnection();
		// LinkedList<String> query = new LinkedList<String>();
		// String[] params = new String[fieldHashMap.size()];
		ArrayList<SearchStatement> searchStatements = advancedQuery(fieldHashMap);
		String resultSelect = "";
		String resultWhere = "";
		String resultJoin = "";
		String fieldTableA = "";
		String fieldTableB = "";
		String connectionTableA = "";
		String connectionTableB = "";
		String stmtA;
		String stmtB;
		int count = 0;

		for (SearchStatement searchStatement : searchStatements) {
			if (count == 0) {
				fieldTableA = searchStatement.getFieldTable();
				connectionTableA = searchStatement.getConnectionTable();
				stmtA = searchStatement.getStmt();
				resultSelect = "SELECT DISTINCT " + fieldTableA
						+ ".user_id FROM " + fieldTableA;
				resultJoin = " INNER JOIN connections ON connections.user1_id = "
						+ fieldTableA + ".user_id";
				if (!connectionTableA.equals("connections"))
					resultJoin += " INNER JOIN " + connectionTableA + " ON "
							+ connectionTableA + ".connections_id"
							+ " = connections.connections_id";
				resultWhere = " WHERE " + stmtA;
			} else {
				fieldTableB = searchStatement.getFieldTable();
				connectionTableB = searchStatement.getConnectionTable();
				stmtB = searchStatement.getStmt();
				if (!resultSelect.contains(fieldTableB))//
					resultJoin += " INNER JOIN " + fieldTableB + " ON "
							+ fieldTableA + ".user_id = " + fieldTableB
							+ ".user_id";
				if (!connectionTableB.equals("connections"))
					resultJoin += " INNER JOIN " + connectionTableB + " ON "
							+ connectionTableB + ".connections_id"
							+ " = connections.connections_id";
				resultWhere += " AND " + stmtB;
			}
			count++;
		}

		String result = resultSelect + resultJoin + resultWhere + ";";
		PreparedStatement preparedStatement = conn.prepareStatement(result);
		int paramIndex = 1;
		for (SearchStatement searchStatement : searchStatements) {
			String keywordStmt = "";
			for (String keyword : searchStatement.getKeywords()) {
				keywordStmt += "%" + keyword + "%";
			}
			preparedStatement.setString(paramIndex, keywordStmt);
			paramIndex++;
		}
		// System.out.println(preparedStatement.toString());
		LinkedList<Integer> userIDs = new LinkedList<Integer>();
		ResultSet queryResults = preparedStatement.executeQuery();
		while (queryResults.next()) {
			userIDs.add(queryResults.getInt("user_id"));
		}

		queryResults.close();
		preparedStatement.close();
		conn.close();
		db.closeConnection();
		return userIDs;
	}

	private class SearchStatement {
		public String[] keywords;
		public String connectionTable;
		public String fieldTable;
		public String stmt;

		public SearchStatement(String fieldTable, String connectionTable,
				String stmt, String[] keywords) {
			this.fieldTable = fieldTable;
			this.connectionTable = connectionTable;
			this.stmt = stmt;
			this.keywords = keywords;
		}

		public String[] getKeywords() {
			return keywords;
		}

		public void setKeywords(String[] keywords) {
			this.keywords = keywords;
		}

		public String getStmt() {
			return stmt;
		}

		public void setStmt(String stmt) {
			this.stmt = stmt;
		}

		public String getConnectionTable() {
			return connectionTable;
		}

		public void setConnectionTable(String connectionTable) {
			this.connectionTable = connectionTable;
		}

		public String getFieldTable() {
			return fieldTable;
		}

		public void setFieldTable(String fieldTable) {
			this.fieldTable = fieldTable;
		}

	}
}
