package com.eNyaya.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.eNyaya.model.AppointmentModel;
import com.eNyaya.service.LawyerDashboardService;

/**
 * Servlet implementation class LawyerDashboard
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/lawyerDashboard" })
public class LawyerDashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Step 1: Get lawyer ID from session
    	HttpSession session = request.getSession(false);
    	Integer lawyerID = null;

    	if (session != null) {
    	    lawyerID = (Integer) session.getAttribute("lawyerID");
    	}

        if (lawyerID == null) {
            // Not logged in
        	request.setAttribute("loginMessage", "Please log in to access the dashboard.");
        	request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        	
            return;
        }

        // Step 2: Use LawyerDashboardService
        LawyerDashboardService service = new LawyerDashboardService();

        int totalAppointments = service.getTotalAppointment(lawyerID);
        int ongoingAppointments = service.getOngoingAppointment(lawyerID);
        int todayAppointments = service.getTodayAppointments(lawyerID);
        double rating = service.getRating(lawyerID);

        List<AppointmentModel> todayList = service.getTodayAppointmentsList(lawyerID);
        List<AppointmentModel> ongoingList = service.getOngoingAppointmentList(lawyerID);
        List<AppointmentModel> upcomingList = service.getUpcomingAppointmentList(lawyerID);

        // Step 3: Set data as request attributes
        request.setAttribute("totalAppointments", totalAppointments);
        request.setAttribute("ongoingAppointments", ongoingAppointments);
        request.setAttribute("todayAppointments", todayAppointments);
        request.setAttribute("rating", rating);
        request.setAttribute("todayList", todayList);
        request.setAttribute("ongoingList", ongoingList);
        request.setAttribute("upcomingList", upcomingList);

        // Step 4: Forward to JSP
        request.getRequestDispatcher("/WEB-INF/pages/lawyerDashboard.jsp").forward(request, response);
    }

}
