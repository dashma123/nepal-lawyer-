

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.eNyaya.model.AppointmentModel;
import com.eNyaya.model.ClientModel;
import com.eNyaya.model.LawyerModel;
import com.eNyaya.service.AppointmentService;
import com.eNyaya.service.ClientService;
import com.eNyaya.service.LawyerService;
import com.eNyaya.util.SessionUtil;

/**
 * Servlet implementation class BookAppointment
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/bookAppointment" })
public class BookAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final ClientService clientService       = new ClientService();
    private final LawyerService lawyerService = new LawyerService();
    private final AppointmentService apptService     = new AppointmentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 1) Get clientId from session
        Integer clientId = (Integer) SessionUtil.getAttribute(req, "clientID");
        if (clientId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String lawyerIdParam = req.getParameter("lawyerId");
        if (lawyerIdParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing lawyer ID");
            return;
        }

        int lawyerId;
        try {
            lawyerId = Integer.parseInt(lawyerIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid lawyer ID");
            return;
        }

        ClientModel client = clientService.getClientById(clientId);
        LawyerModel lawyer = lawyerService.getLawyerById(lawyerId);
        if (client == null || lawyer == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Client or Lawyer not found");
            return;
        }

        req.setAttribute("client", client);
        req.setAttribute("lawyer", lawyer);
        req.getRequestDispatcher("/WEB-INF/pages/bookingAppointment.jsp")
           .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer clientId = (Integer) SessionUtil.getAttribute(req, "clientID");
        if (clientId == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int lawyerId = Integer.parseInt(req.getParameter("lawyerID"));
        LocalDate date    = LocalDate.parse(req.getParameter("date"));
        LocalTime start   = LocalTime.parse(req.getParameter("startTime"));
        LocalTime end     = LocalTime.parse(req.getParameter("endTime"));
        String mode       = req.getParameter("mode");
        String desc       = req.getParameter("description");
        
        System.out.println("Booking for clientID=" + clientId + ", lawyerID=" + lawyerId);

        AppointmentModel appt = new AppointmentModel();
        appt.setClientID(clientId);
        appt.setLawyerID(lawyerId);
        appt.setAppointmentDate(date);
        appt.setStartTime(start);
        appt.setEndTime(end);
        appt.setMode(mode);
        appt.setStatus("Pending");
        appt.setDescription(desc);

        boolean success = apptService.bookAppointment(appt);

        if (success) {
            // Load client and lawyer for confirmation page
            ClientModel client = clientService.getClientById(clientId);
            LawyerModel lawyer = lawyerService.getLawyerById(lawyerId);
            req.setAttribute("client", client);
            req.setAttribute("lawyer", lawyer);
            req.setAttribute("appointment", appt);
            // Forward to confirmation JSP
            req.getRequestDispatcher("/WEB-INF/pages/bookingConfirmation.jsp")
               .forward(req, resp);
        } else {
            // On failure, re-display booking page with error
            req.setAttribute("error", "Could not book appointment. Please try again.");
            List<AppointmentModel> booked = apptService.getBookedSlots(lawyerId, date);
            req.setAttribute("bookedSlots", booked);
            req.setAttribute("selectedDate", date);
            ClientModel client = clientService.getClientById(clientId);
            LawyerModel lawyer = lawyerService.getLawyerById(lawyerId);
            req.setAttribute("client", client);
            req.setAttribute("lawyer", lawyer);
            req.getRequestDispatcher("/WEB-INF/pages/bookingAppointment.jsp")
               .forward(req, resp);
        }
    }

}
