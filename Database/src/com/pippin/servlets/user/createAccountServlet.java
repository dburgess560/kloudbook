package com.pippin.servlets.user;

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
import com.pippin.Login;
import com.pippin.User;
import com.pippin.database.Profile;

/**
 * Servlet implementation class createAccountServlet
 */
@WebServlet("/Account/Create")
public class createAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public createAccountServlet() {
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

		JsonReader jreader = new JsonReader(request.getReader());
		User newUser = new Gson().fromJson(jreader, User.class);
		
		int confirm = Profile.createAccount(newUser);
		newUser = Profile.findUser(confirm, confirm);
		
		//Now a json object is sent back
		
		String json = new Gson().toJson(newUser);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		Writer writer = null;
		
		try {
			writer = response.getWriter();
			writer.write(json);
		} finally {
			writer.close();
		}
		
		HttpSession session = request.getSession(true);	
	}
}
