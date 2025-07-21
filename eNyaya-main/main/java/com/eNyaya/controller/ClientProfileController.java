package com.eNyaya.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.eNyaya.model.ClientModel;
import com.eNyaya.service.ClientService; // This should be your actual ClientService class
import com.eNyaya.util.SessionUtil;

@WebServlet(asyncSupported = true, urlPatterns = { "/client/profile" }) // CHANGE THIS LINE
public class ClientProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ClientService clientService = new ClientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Your existing doGet logic
        Integer clientId = (Integer) SessionUtil.getAttribute(req, "clientID");
        if (clientId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        ClientModel client = clientService.getClientById(clientId);
        if (client == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Client not found");
            return;
        }

        req.setAttribute("client", client);
        req.getRequestDispatcher("/WEB-INF/pages/clientProfile.jsp")
           .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Your existing doPost logic
        Integer clientId = (Integer) SessionUtil.getAttribute(req, "clientID");
        if (clientId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        ClientModel existing = clientService.getClientById(clientId);
        if (existing == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Client not found");
            return;
        }

        ClientModel updated = new ClientModel();
        updated.setClientID(clientId);
        updated.setClientName(existing.getClientName());
        updated.setClientEmail(req.getParameter("email"));
        updated.setClientAddress(req.getParameter("address"));
        updated.setClientNumber(req.getParameter("number"));
        updated.setDateOfBirth(existing.getDateOfBirth());
        updated.setGender(existing.getGender());

        String newPassword = req.getParameter("password");
        updated.setClientPassword(
          (newPassword != null && !newPassword.isEmpty())
            ? newPassword
            : existing.getClientPassword()
        );

        if (!clientService.updateClient(updated)) {
          resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                         "Could not update client");
          return;
        }

        resp.sendRedirect(req.getContextPath() + "/client/profile");
    }
}