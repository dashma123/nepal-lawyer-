package com.eNyaya.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.eNyaya.service.AdminDashboardService;
import com.eNyaya.service.AdminDashboardService.RecentAppointment;
import com.eNyaya.service.AdminDashboardService.Stats;

/**
 * Servlet implementation class AdminDashboard
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/adminDashboard" })
public class AdminDashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private AdminDashboardService dashboardService;
	
	@Override
    public void init() throws ServletException {
        dashboardService = new AdminDashboardService();
    }

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get dashboard stats
        Stats stats = dashboardService.getStats();

        // Get recent appointments (logs)
        List<RecentAppointment> recentLogs = dashboardService.getRecentAppointments(10);

        // Add data to request scope
        req.setAttribute("stats", stats);
        req.setAttribute("logs", recentLogs);

        // Forward to JSP
        req.getRequestDispatcher("/WEB-INF/pages/adminDashboard.jsp").forward(req, resp);
    }

}
