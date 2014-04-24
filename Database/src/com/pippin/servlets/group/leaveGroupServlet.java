package com.pippin.servlets.group;

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
import com.pippin.database.Group;
import com.pippin.database.GroupMember;
import com.pippin.database.Profile;

/**
 * Servlet implementation class leaveGroupServlet
 */
@WebServlet("/Group/Leave")
public class leaveGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public leaveGroupServlet() {
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
		
			int userID = (int) request.getAttribute("userID");
			JsonReader jreader = new JsonReader(request.getReader());
			Group groupToLeave = new Gson().fromJson(jreader, Group.class); 
			Boolean confirm = false;
		
		try {
			groupToLeave.removeMember(userID, userID); // user can remove him/herself 
			confirm = true;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
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
