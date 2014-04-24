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
 * Servlet implementation class resetPW
 */
@WebServlet(description = "Edit password", urlPatterns = { "/Account/EditPW" })
public class editPWServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public editPWServlet() {
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
		JsonReader jreader = new JsonReader(request.getReader());
		Login login = new Gson().fromJson(jreader, Login.class);
		try {
	//		boolean confirm = new Profile().editPW(login);
			int userID = new Profile().getID(login.getEmail());
	//		User user = new Profile().find(userID);
	//		String json = new Gson().toJson(user);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			Writer writer = null;
			try {
				writer = response.getWriter();
	//			writer.write(json);
			} finally {
				writer.close();
			}
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//you have successfully changed your password
		//do we want to return a json object of user?

		}
	}

}
