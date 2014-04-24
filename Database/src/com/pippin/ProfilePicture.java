package com.pippin;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ProfilePicture {
	private final String filePath = "/KloudBook/Database/src/com/pippin/testing/img/";
	private String fileName;

	public ProfilePicture() {
	}
	public void uploadImage(String sourcePath) throws IOException {
		File srcFile = new File(sourcePath);
		String[] paths = sourcePath.split("/");
		fileName = paths[paths.length];
		File destFile = new File(filePath + "/" + fileName);
		FileUtils.copyFile(srcFile, destFile);
	}
}
