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
import com.pippin.Connections;
import com.pippin.database.Group;
import com.pippin.database.GroupMember;
import com.pippin.database.Profile;
import com.pippin.User;

/**
 * Servlet implementation class editGroupServlet
 */
@WebServlet("/Group/Edit")
public class editGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public editGroupServlet() {
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
			boolean confirm = false;
			
		try {
			
			int user = (int) request.getAttribute("userID"); // user ID
				
            Group group = Profile.findGroup(newGroup.getGroupID());
            group.setDescription(user, newGroup.getDescription());
            group.setGroupName(user, newGroup.getName());
            group.setSharedInfo(user, group.getSharedInfo());
            confirm = true;
             
			} catch (SQLException e) {
			
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
		
		String json = new Gson().toJson(confirm);
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
