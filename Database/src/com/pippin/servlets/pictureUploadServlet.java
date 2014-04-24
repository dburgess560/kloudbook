package com.pippin.servlets;

import java.io.File;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.pippin.database.Picture;

@WebServlet("/pictureUploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
maxFileSize = 1024 * 1024 * 5, // 5 MB
maxRequestSize = 1024 * 1024 * 10)
// 10 MB
public class pictureUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private boolean isMultipart;
	private String filePath;
	// private int maxFileSize = 200 * 1024;
	// private int maxMemSize = 4 * 1024;
	private File file;
	private static final String UPLOAD_DIR = "uploads\\";

	public pictureUploadServlet() {
		super();
	}

	public void init() {
		// filePath = getServletContext().getInitParameter("file-upload");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		// Check that we have a file upload request
		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");
		if (!isMultipart) {
			// no file uploaded
			return;
		}
		// gets absolute path of the web application
		String applicationPath = request.getServletContext().getRealPath("");
		// constructs path of the directory to save uploaded file
		String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// try {
		// Parse the request to get file items.
		List<FileItem> fileItems = null;
		try {
			fileItems = upload.parseRequest(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Picture p = new Picture(uploadFilePath);
			p.parseImageUpload(fileItems);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// }
		// // Picture p = new Picture();
		// // p.parseImageUpload(fileItems, uploadFilePath);
		// // Process the uploaded file items
		// Iterator it = fileItems.iterator();
		//
		// int userID = -1;
		// String fileName = "";
		//
		// while (it.hasNext()) {
		// FileItem fi = (FileItem) it.next();
		// if (!fi.isFormField()) {
		// // Get the uploaded file parameters
		// String fieldName = fi.getFieldName();
		// // rename file in file system to its SHA1
		// fileName = fi.getName();
		// String[] parsedName = fileName.split("\\.");
		// String type = "." + parsedName[parsedName.length - 1];
		// MessageDigest md = MessageDigest.getInstance("SHA1");
		// byte[] mdbytes = md.digest(fi.get());
		// StringBuffer sb = new StringBuffer("");
		// for (int i = 0; i < mdbytes.length; i++) {
		// sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100,
		// 16).substring(1));
		// }
		// fileName = sb.toString() + type;
		// String contentType = fi.getContentType();
		// boolean isInMemory = fi.isInMemory();
		// long sizeInBytes = fi.getSize();
		// // Write the file
		// if (fileName.lastIndexOf("\\") >= 0) {
		// file = new File(uploadFilePath+
		// fileName.substring(fileName.lastIndexOf("\\")));
		// } else {
		// file = new File(uploadFilePath+
		// fileName.substring(fileName.lastIndexOf("\\") + 1));
		// }
		// System.out.println(uploadFilePath + fileName);
		// fi.write(file);
		//
		// } else {
		//
		// fi.write(file);
		//
		// JsonFactory f = new MappingJsonFactory();
		// JsonParser jp = f.createJsonParser(file);
		// JsonToken current;
		//
		// current = jp.nextToken();
		// if (current != JsonToken.START_OBJECT) {
		// System.out
		// .println("Error: root should be object: quiting.");
		// return;
		// }
		// while (jp.nextToken() != JsonToken.END_OBJECT) {
		// String fieldName = jp.getCurrentName();
		// // move from field name to field value
		// current = jp.nextToken();
		// if (fieldName.equals("ID")) {
		// if (current == JsonToken.VALUE_NUMBER_INT) {
		// JsonNode node = jp.readValueAsTree();
		// userID = node.getIntValue();
		// }
		// }
		// }
		// }
		// }
		//
		// if (!fileName.equals("") && userID != -1) {
		// Picture p = new Picture();
		// p.storeProfileImage(userID, uploadFilePath + fileName);
		// }
		// } catch (Exception ex) {
		// System.out.println(ex);
		// }

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

	}
}