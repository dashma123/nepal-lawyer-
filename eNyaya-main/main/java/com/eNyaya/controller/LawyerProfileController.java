package com.eNyaya.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import com.eNyaya.model.LawyerModel;
import com.eNyaya.service.LawyerService;

/**
 * Servlet implementation class LawyerProfile
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/lawyerProfile" })
public class LawyerProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 private final LawyerService lawyerService = new LawyerService();

	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
	        String idParam = req.getParameter("id");
	        int id;
	        try {
	            id = Integer.parseInt(idParam);
	        } catch (NumberFormatException e) {
	            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid lawyer ID");
	            return;
	        }

	        LawyerModel lawyer = lawyerService.getLawyerById(id);
	        if (lawyer == null) {
	            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Lawyer not found");
	            return;
	        }
	        
	        req.setAttribute("lawyer", lawyer);
	        req.getRequestDispatcher("/WEB-INF/pages/lawyerProfile.jsp")
	        .forward(req, resp);
	    }
	    
	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {

	        int id = Integer.parseInt(req.getParameter("id"));
	        LawyerModel updated = new LawyerModel();
	        updated.setLawyerID(id);
	        updated.setLawyerName(req.getParameter("name"));
	        updated.setLicenseNumber(req.getParameter("license"));
	        updated.setDateJoined(LocalDate.parse(req.getParameter("dob")));
	        updated.setLawyerEmail(req.getParameter("email"));
	        updated.setGender(req.getParameter("gender"));
	        updated.setLawyerNumber(req.getParameter("phone"));
	        updated.setDistrict(req.getParameter("district"));
	        updated.setProvince(req.getParameter("province"));

	        boolean ok = lawyerService.updateLawyer(updated);
	        if (!ok) {
	            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
	                           "Could not update lawyer");
	            return;
	        }
	        
	        resp.sendRedirect(req.getContextPath()
	            + "/lawyer/profile?id=" + id);
	    }

}
