package com.pippin.servlets;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.pippin.User;
import com.pippin.database.Search;

/**
 * Servlet implementation class basicSearchServlet
 */
@WebServlet("/SearchFriends")
public class searchFriendsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public searchFriendsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JsonObject jobj = new JsonObject();
		Search s = new Search();
		JsonReader jreader = new JsonReader(request.getReader());
		List<User> users = s.parseJsonConnectionsSearch(jreader, (int)request.getAttribute("userID"));
		String list = new Gson().toJson(users);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Writer writer = null;
		try {
			writer = response.getWriter();
			writer.write(list);
		} finally {
			writer.close();
			jreader.close();
		}
	}

}
