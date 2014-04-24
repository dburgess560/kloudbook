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
import com.pippin.database.Group;
import com.pippin.database.Profile;

/**
 * Servlet implementation class createGroupServlet
 */
@WebServlet(description = "Create A Group", urlPatterns = { "/Group/Create" })
public class createGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public createGroupServlet() {
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
			Group newGroup = new Gson().fromJson(jreader, Group.class);
		try {
			
			int userID = (int) request.getAttribute("userID");
			
			session.setAttribute("adminID", userID);
			int confirm = Profile.createGroup(userID, newGroup.getName(), newGroup.getDescription(), newGroup.getSharedInfo());
		    newGroup = Profile.findGroup(confirm);
		    
			} catch (SQLException e) {
			
			e.printStackTrace();
			}
		
		String json = new Gson().toJson(newGroup);
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
