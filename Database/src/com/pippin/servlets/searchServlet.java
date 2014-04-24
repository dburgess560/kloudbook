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
@WebServlet("/Search")
public class searchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public searchServlet() {
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
		doPost(request, response);
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
		List<User> users = s.parseJsonPublicSearch(jreader);
		/*
		 * HashMap<String,String> map = new HashMap<String,String>(); String
		 * name=""; try{ while(jreader.hasNext()){ String tempName =
		 * jreader.nextName(); if(tempName.equals("query")) map.put("query",
		 * jreader.nextString()); else if(tempName.equals("email"))
		 * map.put("email", jreader.nextString()); else
		 * if(tempName.equals("gender")) map.put("gender",
		 * jreader.nextString()); //else if(name.equals("phonenumber")) //
		 * map.put("phonenumber", jreader.nextString()); else
		 * if(tempName.equals("company")) map.put("company",
		 * jreader.nextString()); else if(tempName.equals("currentcity"))
		 * map.put("currentcity", jreader.nextString()); else
		 * if(tempName.equals("firstname")) name = jreader.nextString(); else
		 * if(tempName.equals("middlename")) name = name + " " +
		 * jreader.nextString(); else if(tempName.equals("lastname")) name =
		 * name + " " + jreader.nextString(); } map.put("name", name);
		 * List<Integer> results;
		 * if(map.containsKey("query")||map.containsKey("email"
		 * )||map.containsKey
		 * ("gender")||map.containsKey("company")||map.containsKey
		 * ("currentcity")){ results = new Search().advancedSearch(map); }else{
		 * results = new Search().basicSearch(name); }
		 */

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
