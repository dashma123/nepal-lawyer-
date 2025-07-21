package com.eNyaya.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.eNyaya.service.ContactUsService;
import com.eNyaya.util.SessionUtil;

/**
 * Servlet implementation class ContactUs
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/contactUs" })
public class ContactUsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ContactUsService contactService = new ContactUsService();

	
	/**
	 * Handles GET requests to the login page.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Integer clientID = (Integer) SessionUtil.getAttribute(request, "clientID");
        if (clientID == null) {
            // not logged in (or session expired) → send back to login
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
		request.getRequestDispatcher("/WEB-INF/pages/contactUs.jsp").forward(request, response);
	}
	
	  @Override
	  protected void doPost(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, IOException {

	    String firstName = request.getParameter("firstName");
	    String lastName = request.getParameter("lastName");
	    String email = request.getParameter("email");
	    String phone = request.getParameter("phone");
	    String subject = request.getParameter("subject");
	    String message = request.getParameter("message");

	    contactService.saveContactMessage(firstName, lastName, email, phone, subject, message);

	    // Redirecting to the same contact page with a success parameter
	    request.getRequestDispatcher("/WEB-INF/pages/contactUs.jsp").forward(request, response);
	  }

}
