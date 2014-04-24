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
import com.pippin.database.Profile;

/**
 * Servlet implementation class deactivateServlet
 */
@WebServlet("/Account/Deactivate")
public class deactivateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public deactivateServlet() {
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

		// Check to make sure there is a valid session

		if(session == null){
			//log them out and sendRedirect to login page
			session.invalidate();
			response.sendRedirect("kloudbook.com/Account/Logout");
		}
		else{

			JsonReader jreader = new JsonReader(request.getReader());
			Login deactivateUser = new Gson().fromJson(jreader, Login.class);
			boolean confirm = false; // confirm will hold whether or not deactivation was successful

			try {

				confirm = new Profile().deactivate(deactivateUser);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String confirmedDeactivation = new Gson().toJson(confirm); // confirm is returned as a json
			Writer writer = null;

			try {
				writer = response.getWriter();
				writer.write(confirmedDeactivation);
			} finally {
				writer.close();
			}

			session = request.getSession(false); 
			session.invalidate();
			response.sendRedirect("kloudbook.com/Account/Logout"); // User is sent to login page after deactivating
		}
	}
}
