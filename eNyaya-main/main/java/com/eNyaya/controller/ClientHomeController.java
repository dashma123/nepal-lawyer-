package com.eNyaya.controller;

import com.eNyaya.model.ClientModel;
import com.eNyaya.model.LawyerModel;
import com.eNyaya.service.ClientService;
import com.eNyaya.service.LawyerService;
import com.eNyaya.util.SessionUtil;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Servlet implementation class ClientHome
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/clientHome" })
public class ClientHomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	private final ClientService clientService = new ClientService();
    private final LawyerService lawyerService = new LawyerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 1) Read clientID out of session instead of request parameter
        Integer clientID = (Integer) SessionUtil.getAttribute(req, "clientID");
        if (clientID == null) {
            // not logged in (or session expired) → send back to login
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 2) Load client and lawyers
        ClientModel client = clientService.getClientById(clientID);
        if (client == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Client not found");
            return;
        }

        List<LawyerModel> lawyers = lawyerService.getAllLawyers();

        // 3) Put into request scope
        req.setAttribute("client", client);
        req.setAttribute("lawyers", lawyers);

        // 4) Forward to JSP
        req.getRequestDispatcher("/WEB-INF/pages/clientHome.jsp").forward(req, resp);
    }

}
