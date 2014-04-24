package com.pippin.servlets;


import java.io.IOException;

import java.util.List;

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
 * Servlet implementation class viewAddressBookServlet
 */
@WebServlet("/View/AddressBook")
public class viewAddressBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public viewAddressBookServlet() {
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
			session.invalidate();
			response.sendRedirect("login");
		}
		else{
			JsonReader jreader = new JsonReader(request.getReader());
	//		User user = new Gson().fromJson(jreader, User.class);
	//		List<UserMk2> addressbook = new Profile().buildUsers(user.connections);
		}
	}
}
