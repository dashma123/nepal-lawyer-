package com.eNyaya.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.eNyaya.model.LawyerModel;
import com.eNyaya.service.LawyerService;

/**
 * Servlet implementation class Home
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/home","/" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final LawyerService lawyerService = new LawyerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 1) load all lawyers
        List<LawyerModel> lawyers = lawyerService.getAllLawyers();
        req.setAttribute("lawyers", lawyers);

        // 2) forward to index.jsp
        req.getRequestDispatcher("/WEB-INF/pages/home.jsp")
           .forward(req, resp);
    }
}
