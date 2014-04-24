package com.pippin.servlets;

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
 * Servlet implementation class viewGroupServlet
 */
@WebServlet("/View/Group")
public class viewGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewGroupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false); // if the current session is invalid, session is set to null
		
		if (session == null){
			
			session.invalidate(); // invalidates session
			response.sendRedirect("login page URL"); // user is sent back to login page
		}
		
		else {
			
			JsonReader jreader = new JsonReader(request.getReader());
			Group group = new Gson().fromJson(jreader, Group.class);
			int groupId = group.getGroupID();
			
		    try {
				group = Profile.findGroup(groupId);
			} catch (SQLException e) {
				e.printStackTrace();
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
		
			writer.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
