package com.pippin.servlets.notification;

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
import com.pippin.Notification;
import com.pippin.database.Profile;

/**
 * Servlet implementation class NotificationAcceptServlet
 */
@WebServlet("/Notification/Accept")
public class NotificationAcceptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotificationAcceptServlet() {
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
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session == null){
			//log them out and sendredirect to login page
			session.invalidate();
			response.sendRedirect("index.html");
		}
		else{
			JsonReader jreader = new JsonReader(request.getReader());
			Notification notifAccept = new Gson().fromJson(jreader, Notification.class);
			boolean confirm = false;
			confirm = new Profile().acceptNotification(notifAccept); //parameter should be AcceptDeny
			
			String message = new Gson().toJson(confirm);
			
			Writer writer = null;
			
			try {
				writer = response.getWriter();
				writer.write(message);
			} finally {
				writer.close();
				jreader.close();
			}
			
		}
	}

}
