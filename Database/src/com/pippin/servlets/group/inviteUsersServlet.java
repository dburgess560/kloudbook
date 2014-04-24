package com.pippin.servlets.group;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.pippin.database.Group;
import com.pippin.database.Profile;

/**
 * Servlet implementation class inviteUsersServlet
 */
@WebServlet(description = "Invite Users to a Group", urlPatterns = { "/Group/Invite" })
public class inviteUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public inviteUsersServlet() {
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
			Type linkedListIntType = new TypeToken<LinkedList<Integer>>(){}.getType();
			List<Integer> newInts = new Gson().fromJson(jreader, linkedListIntType);
			
			int userID = (int) request.getAttribute("userID");
			
			Group group = new Gson().fromJson(jreader, Group.class);
			
			for (Integer i: newInts){
				
				try {
					
					group.addMember(userID, i);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
			}
		
		String json = new Gson().toJson(group);
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
