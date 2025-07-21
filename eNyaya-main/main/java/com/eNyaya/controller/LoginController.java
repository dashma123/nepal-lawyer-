package com.eNyaya.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.eNyaya.service.LoginService;
import com.eNyaya.util.CookieUtil;

/**
 * Servlet implementation class Login
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login"})
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	private final LoginService loginService;

	/**
	 * Constructor initializes the LoginService.
	 */
	public LoginController() {
		this.loginService = new LoginService();
	}

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
		request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for user login.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    String email = req.getParameter("email");
	    String password = req.getParameter("password");

	    String loginResult = loginService.login(email, password, req);

	    switch (loginResult) {
	        case "admin":
	            CookieUtil.addCookie(resp, "role", "admin", 5 * 30);
	            resp.sendRedirect(req.getContextPath() + "/adminDashboard");
	            break;

	        case "client":
	            CookieUtil.addCookie(resp, "role", "client", 5 * 30);
	            resp.sendRedirect(req.getContextPath() + "/clientHome");
	            break;

	        case "lawyer":
	            CookieUtil.addCookie(resp, "role", "lawyer", 5 * 30);
	            resp.sendRedirect(req.getContextPath() + "/lawyerDashboard");
	            break;

	        default:
	        	System.out.println("Checking login for: " + email);
	            handleLoginFailure(req, resp, loginResult);
	            break;
	    }
	}


	/**
	 * Handles login failures by setting attributes and forwarding to the login
	 * page.
	 *
	 * @param req         HttpServletRequest object
	 * @param resp        HttpServletResponse object
	 * @param loginResult Boolean indicating the login status
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, String loginResult)
			throws ServletException, IOException {
		String errorMessage;
		if (loginResult == null) {
			errorMessage = "Our server is under maintenance. Please try again later!";
		} else {
			errorMessage = "User credential mismatch. Please try again!";
		}

		req.setAttribute("error", errorMessage);
		req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
	}
}
