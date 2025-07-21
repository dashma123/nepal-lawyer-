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
import com.eNyaya.service.LawyerAppointmentService;

/**
 * Servlet implementation class LawyerAppointment
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/lawyerAppointment" })
public class LawyerAppointmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	   private LawyerAppointmentService appointmentService;

	    @Override
	    public void init() {
	        appointmentService = new LawyerAppointmentService();
	    }

	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        // Get logged-in lawyer's ID from session
	        HttpSession session = request.getSession(false);
	        Integer lawyerID = (session != null) ? (Integer) session.getAttribute("lawyerID") : null;

	        if (lawyerID == null) {
	        	request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
	            return;
	        }

	        // Fetch appointments using service
	        List<AppointmentModel> appointmentList = appointmentService.getAppointmentsByLawyerId(lawyerID);

	        // Set data in request scope
	        request.setAttribute("appointmentList", appointmentList);

	        // Forward to JSP
	        request.getRequestDispatcher("/WEB-INF/pages/lawyerAppointment.jsp").forward(request, response);
	    }
}
