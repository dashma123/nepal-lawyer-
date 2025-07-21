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
import com.eNyaya.util.SessionUtil;

/**
 * Servlet implementation class FindLawyer
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/findLawyer" })
public class FindLawyerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final LawyerService lawyerService = new LawyerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	Integer clientID = (Integer) SessionUtil.getAttribute(req, "clientID");
        if (clientID == null) {
            // not logged in (or session expired) → send back to login
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String keyword = req.getParameter("q");
        List<LawyerModel> lawyers;

        if (keyword != null && !keyword.isBlank()) {
            lawyers = lawyerService.searchByKeyword(keyword);
        } else {
            lawyers = lawyerService.getAllLawyers();
        }

        req.setAttribute("lawyers", lawyers);
        req.setAttribute("searchQuery", keyword);
        req.getRequestDispatcher("/WEB-INF/pages/findLawyer.jsp")
           .forward(req, resp);
    }
}
