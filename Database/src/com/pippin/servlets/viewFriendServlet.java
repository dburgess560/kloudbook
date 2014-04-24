package com.pippin.servlets;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.pippin.User;
import com.pippin.database.Profile;

/**
 * Servlet implementation class friendsServlet
 */
@WebServlet("/View/Friend")
public class viewFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public viewFriendServlet() {
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
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			//log them out and sendredirect to login page
			session.invalidate();
			response.sendRedirect("login.jsp");
		}
		else{
			JsonReader jreader = new JsonReader(request.getReader());
			User user = new Gson().fromJson(jreader, User.class); //a user object of the friend?
			int friendID = user.getUserid(); //grab friend ID from json
			User friend = null;
			friend = Profile.findUser(friendID, Integer.parseInt((String) session.getAttribute("userID")));
			//now send back json object
			String json = new Gson().toJson(friend);
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
