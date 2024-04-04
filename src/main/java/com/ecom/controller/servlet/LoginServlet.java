package com.ecom.controller.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.ecom.controller.DatabaseController;
import com.ecom.utils.StringUtils;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	
	DatabaseController db = new DatabaseController();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		String userName = request.getParameter(StringUtils.userName);
	    String password = request.getParameter(StringUtils.password);
	    String hashedPasswordDB = db.getHashedPassword(userName);
	    System.out.println("Hashed Password: " + hashedPasswordDB);
	    if (hashedPasswordDB != null && hashedPasswordDB.contains("$")) {
	        String[] parts = hashedPasswordDB.split("\\$");
	        String extractedSalt = parts[2];
	        String extractedHash = parts[3];

	        String hashedPasswordSalt = "$2a$" + extractedSalt + "$" + extractedHash;

	        if (BCrypt.checkpw(password, hashedPasswordSalt)) {
	            int loginResult = db.getUserLoginInfo(userName, hashedPasswordSalt); // Assuming separate method for user login
	            if (loginResult == 1) {
	                // Successful user login
	                HttpSession session = request.getSession();
	                session.setAttribute("userName", userName);
	                session.setAttribute("role", "user"); // Set user role in session
	                session.setMaxInactiveInterval(30 * 60);
	                response.sendRedirect(request.getContextPath() + StringUtils.WELCOME_PAGE); // Redirect to user welcome page
	            } else {
	            	
	                // Check admin login
	                loginResult = db.getAdminLoginInfo(userName, hashedPasswordSalt); // Assuming separate method for admin login
	                if (loginResult == 1) {
	                    // Successful admin login
	                    HttpSession session = request.getSession();
	                    session.setAttribute("userName", userName);
	                    session.setAttribute("role", "admin"); // Set admin role in session
	                    session.setMaxInactiveInterval(30 * 60);
	                    response.sendRedirect(request.getContextPath() + StringUtils.ADMIN_PAGE); // Redirect to admin welcome page
	                } else {
	                    // Incorrect username or password for both user and admin
	                    request.setAttribute("errorMessage", "Incorrect username or password");
	                    RequestDispatcher dispatcher = request.getRequestDispatcher(StringUtils.LOGIN_PAGE);
	                    dispatcher.forward(request, response);
	                }
	            }
	        } else {
	            // Incorrect username or password
	            request.setAttribute("errorMessage", "Incorrect username or password");
	            RequestDispatcher dispatcher = request.getRequestDispatcher(StringUtils.LOGIN_PAGE);
	            dispatcher.forward(request, response);
	        }
}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
	}

}
