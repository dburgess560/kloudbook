package com.pippin.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		java.io.PrintWriter out = response.getWriter();
		out.println("<html> "
				+ "<head> "
				+ "<title>File Uploading Form</title> "
				+ "</head> "
				+ "<body> "
				+ "<h3>File Upload:</h3> "
				+ "Select a file to upload: <br /> "
				+ "<table>"
				+ "<form action= pictureUploadServlet  method= post enctype= multipart/form-data > "
				+ "<input type= file  name= file  size= 50 /> <br /> "
				+ "<input type= submit value= Upload File /> " 
				+ "</form> " + "</body> " + "</html>");
		// throw new ServletException("GET method used with "
		// + getClass().getName() + ": POST method required.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
