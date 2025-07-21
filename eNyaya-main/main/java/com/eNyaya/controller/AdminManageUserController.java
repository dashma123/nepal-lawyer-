package com.eNyaya.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.eNyaya.service.AdminManageUserService;
import com.eNyaya.util.SessionUtil;

/**
 * Servlet implementation class AdminManageUser
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/adminManageUser" })
public class AdminManageUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		processRequest(request, response); //handles get
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response); //handles post exactly the same way
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

		// Check if admin is logged in
	    Object roleObj = SessionUtil.getAttribute(request, "role");
	    if (roleObj == null || !"admin".equals(roleObj.toString())) {
	    	request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
	        return;
	    }

	    // Get parameters from request
	    String role = request.getParameter("role");
	    if (role == null || (!role.equalsIgnoreCase("client") && !role.equalsIgnoreCase("lawyer"))) {
	        role = "client"; // default to client if role @param missing or invalid
	    }

	    String action = request.getParameter("action");
	    String id = request.getParameter("id");

	    AdminManageUserService service = new AdminManageUserService();

	    if ("delete".equalsIgnoreCase(action) && id != null && role != null) {
	        boolean success = service.deleteUserById(role, id);
	        if (success) {
	            request.setAttribute("message", "User deleted successfully.");
	        } else {
	            request.setAttribute("error", "Failed to delete user.");
	        }
	    }
	    
	    
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        
        AdminManageUserService searchService = new AdminManageUserService();
        List<?> userList = searchService.searchUsers(role, id, name, email, phone);

        // Set result list attribute depending on role for JSP
        if ("client".equalsIgnoreCase(role)) {
            request.setAttribute("clientList", userList);
        } else {
            request.setAttribute("lawyerList", userList);
        }

        // Keep role so JSP can render appropriate content
        request.setAttribute("selectedRole", role);

        // Forward to JSP page for display
        request.getRequestDispatcher("/WEB-INF/pages/adminManageUser.jsp").forward(request, response);
    }

}
