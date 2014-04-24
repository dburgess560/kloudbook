package com.pippin.servlets.notification;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.pippin.Notification;
import com.pippin.Login;
import com.pippin.User;
import com.pippin.database.Profile;

/**
 * Servlet implementation class Notifications
 */
@WebServlet(description = "Lists Notifications", urlPatterns = { "/Notifications/View" })
public class NotificationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotificationsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int user = (int) request.getAttribute("userID");
		
		String list = new Gson().toJson((new Profile().getNotifications(user)).toString());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Writer writer = null;
		try {
			writer = response.getWriter();
			writer.write(list);
		} finally {
			writer.close();
		}
	}

}
