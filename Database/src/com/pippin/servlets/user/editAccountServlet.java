package com.pippin.servlets.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pippin.Login;
import com.pippin.User;
import com.pippin.database.Profile;

/**
 * Servlet implementation class editProfileServlet
 */
@WebServlet(description = "Edit Profile Information", urlPatterns = { "/Account/Edit" })
public class editAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public editAccountServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			//log them out and sendredirect to login page
	        session.invalidate();
	        response.sendRedirect("kloudbook.com/Account/Logout");
		}
		else{
			String userid = session.getAttribute("userID").toString();
			int userID = Integer.parseInt(userid);
			JsonReader jreader = new JsonReader(request.getReader());
			User user = new Gson().fromJson(jreader, User.class);
	//		boolean confirm = new Profile().editAccount(user);

			//now send back json object
				String json = new Gson().toJson(user);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				Writer writer = null;
				try {
					writer = response.getWriter();
					writer.write(json);
				} finally {
					writer.close();
				}
		}
	}

}
