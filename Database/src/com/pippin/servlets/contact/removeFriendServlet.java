package com.pippin.servlets.contact;

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
import com.pippin.Connections;
import com.pippin.User;
import com.pippin.database.Profile;

/**
 * Servlet implementation class removeFriendServlet
 */
@WebServlet("/Friend/Remove2")
public class removeFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public removeFriendServlet() {
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

		HttpSession session = request.getSession(false); // sets session to null if there is no valid session
		
		if (session == null){ 
			
			session.invalidate();      // invalidates session
			response.sendRedirect("kloudbook.com/Account/Logout"); // sends user to login page
		}
		
		else {
		
			JsonReader jreader = new JsonReader(request.getReader());
			Connections friend = new Gson().fromJson(jreader, Connections.class);
			boolean confirm = false;
			
		    confirm = new Profile().removeFriend(123); // 123 is temporary
    	    
    	    String message = new Gson().toJson(confirm);
			
			Writer writer = null;
			
			try {
				writer = response.getWriter();
				writer.write(message);
			} finally {
				writer.close();
			}
    	    
		}
	}

}
