package com.pippin.database;

import java.io.File;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.MappingJsonFactory;

public class Picture {

	public String filePath;

	public Picture(String filePath) {
		this.filePath = filePath;
		if (filePath.startsWith("/"))
			this.filePath = filePath.substring(1);
		DBConnection db = DBConnection.getInstance();
	}

	private String getCurrentProfileImage(int userID) throws SQLException {
		DBConnection db = DBConnection.getInstance();
		Connection conn = db.getConnection();
		String query = "SELECT picture FROM users WHERE user_id = ? LIMIT 1";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setInt(1, userID);
		ResultSet queryResult = preparedStatement.executeQuery();
		String currentPicture = null;
		while (queryResult.next()) {
			currentPicture = queryResult.getString("picture");
		}
		queryResult.close();
		preparedStatement.close();
		conn.close();
		db.closeConnection();

		return currentPicture;
	}

	private String deleteOldProfileImage(String oldFile)
			throws SQLException {
		File file = new File(this.filePath + "/" + oldFile);
		if (file.exists())
			file.delete();
		return oldFile;
	}

	public void parseImageUpload(List<FileItem> fileItems) throws Exception {
		Iterator it = fileItems.iterator();
		File imageFile = null;
		File jsonFile = null;

		int userID = -1;
		String fileName = "";
		String type = "";
		while (it.hasNext()) {
			FileItem fi = (FileItem) it.next();
			if (!fi.isFormField()) {
				// Get the uploaded file parameters
				String fieldName = fi.getFieldName();
				// rename file in file system to its SHA1
				fileName = fi.getName();
				String[] parsedName = fileName.split("\\.");
				type = "." + parsedName[parsedName.length - 1];
				MessageDigest md = MessageDigest.getInstance("SHA1");
				byte[] mdbytes = md.digest(fi.get());
				StringBuffer sb = new StringBuffer("");
				for (int i = 0; i < 5; i++) {
					sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
							.substring(1));
				}
				fileName = sb.toString();
				// Write the file
				//	if (fileName.lastIndexOf("\\") >= 0) {
				imageFile = new File(filePath + fileName);
				//	} else {
				//		imageFile = new File(filePath + fileName);
				//	}
				fi.write(imageFile);
			} else {
				jsonFile = new File(fi.getFieldName());
				fi.write(jsonFile);
				JsonFactory f = new MappingJsonFactory();
				JsonParser jp = f.createJsonParser(jsonFile);
				JsonToken current;

				current = jp.nextToken();
				if (current != JsonToken.START_OBJECT) {
					System.out
							.println("Error: root should be object: quiting.");
					return;
				}
				while (jp.nextToken() != JsonToken.END_OBJECT) {
					String fieldName = jp.getCurrentName();
					// move from field name to field value
					current = jp.nextToken();
					if (fieldName.equals("ID")) {
						if (current == JsonToken.VALUE_NUMBER_INT) {
							JsonNode node = jp.readValueAsTree();
							userID = node.getIntValue();
						}
					}
				}
				jp.close();
				jsonFile.delete();
			}
		}
		fileName += "_" + userID + type;
		File file = new File(imageFile.getAbsoluteFile() + "_" + userID + type);
		if (file.exists())
			file.delete();
		imageFile.renameTo(new File(imageFile.getAbsoluteFile() + "_" + userID
				+ type));
		File tempFile = new File(imageFile.getAbsolutePath());
		if (tempFile.exists())
			tempFile.delete();
		this.storeProfileImage(userID, fileName);
	}

	public String storeProfileImage(int userID, String fileName)
			throws SQLException {

		String oldImage = getCurrentProfileImage(userID);
		DBConnection db = DBConnection.getInstance();
		Connection conn = db.getConnection();
		String update = "UPDATE users SET picture = ? WHERE user_id = ? LIMIT 1;";
		PreparedStatement preparedStatement = conn.prepareStatement(update);
		preparedStatement.setString(1, fileName);
		preparedStatement.setInt(2, userID);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		conn.close();
		db.closeConnection();

		String currFile = filePath + "/" + fileName;
		String oldFile = filePath + "/" + oldImage;
		if (!currFile.equals(oldFile)) {
			this.deleteOldProfileImage(oldFile);
		}
		return currFile;
	}


	public String retrieveProfileImage(int userID) throws SQLException {
		DBConnection db = DBConnection.getInstance();
		Connection conn = db.getConnection();
		String fileName = "";
		String query = "SELECT picture FROM users WHERE user_id = ? LIMIT 1;";
		PreparedStatement preparedStatement = db.getConnection()
				.prepareStatement(query);
		preparedStatement.setInt(1, userID);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			fileName = resultSet.getString("picture");
		}
		resultSet.close();
		preparedStatement.close();
		conn.close();
		db.closeConnection();
		return fileName;
	}

	public String removeProfileImage(int userID) throws SQLException {
		String fileName = retrieveProfileImage(userID);
		DBConnection db = DBConnection.getInstance();
		Connection conn = db.getConnection();
		String update = "UPDATE users SET picture = ? WHERE user_id = ? LIMIT 1;";
		PreparedStatement preparedStatement = conn.prepareStatement(update);
		preparedStatement.setString(1, "");
		preparedStatement.setInt(2, userID);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		conn.close();
		db.closeConnection();
		this.deleteOldProfileImage(fileName);
		return fileName;
	}

}
