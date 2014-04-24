//dburgess

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
 * Servlet implementation class login
 */
@WebServlet(description = "Login to Kloudbook", urlPatterns = { "/Account/Login" })
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JsonReader jreader = new JsonReader(request.getReader());
		Login loginUser = new Gson().fromJson(jreader, Login.class);

		User user = null;
		
		user = Profile.login(loginUser);
		
		String userJson = new Gson().toJson(user);
		
		Writer writer = null;
		
		try {
			writer = response.getWriter();
			writer.write(userJson);
		} finally {
			writer.close();
		}
		
		HttpSession session = request.getSession(true); // creates session for the user
		
		if(session.isNew())	{
			
			session.setAttribute("userID", user.getUserid()); // associates session with the user's user id
		}
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
