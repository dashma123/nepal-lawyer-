package com.eNyaya.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.eNyaya.model.AppointmentModel;
import com.eNyaya.service.AppointmentService;
import com.eNyaya.util.SessionUtil;

/**
 * Servlet implementation class ClientAppointmentController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/client/appointments" })
public class ClientAppointmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private final AppointmentService apptService = new AppointmentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer clientId = (Integer) SessionUtil.getAttribute(req, "clientID");
        if (clientId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Fetch all appointments for this client (you'll implement this)
        List<AppointmentModel> appointments =
            apptService.getAppointmentsByClient(clientId);

        req.setAttribute("appointments", appointments);
        req.getRequestDispatcher("/WEB-INF/pages/clientAppointments.jsp")
           .forward(req, resp);
    }
}

