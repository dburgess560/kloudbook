package com.pippin.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pippin.database.DBConnection;
import com.pippin.database.Picture;
import com.pippin.servlets.pictureUploadServlet;

public class ProfilePictureTest {
	pictureUploadServlet pUP;
	String url = this.getClass().getResource("testimg").getPath();
	public Picture p = new Picture(url);

	@BeforeClass
	public static void startDatabaseTest() throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {

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

	private File createDummyPicture(String fileName) throws IOException {
		File f = new File(fileName);
		f.createNewFile();
		return f;
	}

	@Test
	public void addPictureTest() throws SQLException, IOException {
		String fileName = p.storeProfileImage(2, "test.jpg");
		System.out.println(fileName);
		File f = createDummyPicture(fileName);
		assertTrue(f.exists());
	}

	@Test
	public void deletePictureTest() throws IOException, SQLException {
		String fileName = p.storeProfileImage(3, "test1.jpg");
		File f = createDummyPicture(fileName);
		fileName = p.retrieveProfileImage(3);
		assertEquals("test1.jpg", fileName);
		assertTrue(f.exists());
		p.removeProfileImage(3);
		assertFalse(f.exists());
		assertEquals("", p.retrieveProfileImage(3));
	}
}
