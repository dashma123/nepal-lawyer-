package com.eNyaya.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import com.eNyaya.model.ClientModel;
import com.eNyaya.model.LawyerModel;
import com.eNyaya.service.RegisterService;
import com.eNyaya.util.PasswordUtil;
//import com.eNyaya.util.PasswordUtil;
import com.eNyaya.util.ValidationUtil;

/**
 * Servlet implementation class Register
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
				maxFileSize = 1024 * 1024 * 10, // 10MB
				maxRequestSize = 1024 * 1024 * 50) // 50MB
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RegisterService registerService;

    @Override
    public void init() throws ServletException {
        super.init();
        registerService = new RegisterService();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
	}

	
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
		String role = req.getParameter("role");   // expects "client" or "lawyer"

        try {
        	if ("Client".equalsIgnoreCase(role)) {
        	    handleClientRegistration(req, resp);
        	} else if ("Lawyer".equalsIgnoreCase(role)) {
        	    handleLawyerRegistration(req, resp);
        	} else {
        	    req.setAttribute("error", "Invalid role.");
        	    req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
        	}
        } catch (SQLException e) {
            req.setAttribute("error", "Database error: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
        }
    }
	
	
	private void handleClientRegistration(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {

		// 1. field‑level validation
		String validationError = validateClientForm(req);
		
		if (validationError != null) {
			forwardWithError(req, resp, validationError);
			return;
		}
		
		// 2. email‑uniqueness check
		String email = req.getParameter("clientEmail");
		if (registerService.isEmailExists(email)) {
			forwardWithError(req, resp, "Email already registered.");
			return;
		}
		
		// 3. build model & persist
		ClientModel client = buildClientModel(req);
		boolean success = registerService.addClient(client);
		
		if (success) {
			resp.sendRedirect("login.jsp");
		} else {
			forwardWithError(req, resp, "Client registration failed.");
		}
	}
		
	private void handleLawyerRegistration(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
	
		// 1. run all field‑level validation
		String validationError = validateLawyerForm(req);
		
		if (validationError != null) {          // something failed
			forwardWithError(req, resp, validationError);
			return;
		}
		
		// 2. email‑uniqueness check
		String email = req.getParameter("lawyerEmail");
		if (registerService.isEmailExists(email)) {
			forwardWithError(req, resp, "Email already registered.");
			return;
		}
		
		// 3. build the model & persist
		LawyerModel lawyer = buildLawyerModel(req);
		boolean success = registerService.addLawyer(lawyer);
		
		if (success) {
			resp.sendRedirect("login.jsp");
		} else {
			forwardWithError(req, resp, "Lawyer registration failed.");
		}
	}
	
	/** Validate all client‑side fields. 
	 *  @return null if everything is OK, otherwise the first error message.
	 */
	private String validateClientForm(HttpServletRequest req) {
		String clientName = req.getParameter("clientName");
		String clientEmail = req.getParameter("clientEmail");
		String clientNumber = req.getParameter("clientPhone");
		String clientPassword = req.getParameter("password");
		String rePassword = req.getParameter("confirmPassword");
	    LocalDate dob = LocalDate.parse(req.getParameter("clientDOB"));
	    String gender = req.getParameter("clientGender");

	    if (ValidationUtil.isNullOrEmpty(clientName))       
	    	return "Full name is required.";
	    
	    if (ValidationUtil.isNullOrEmpty(clientEmail))          
	    	return "Email is required.";
	    
	    if (!ValidationUtil.isValidEmail(clientEmail))          
	    	return "Invalid email format.";
	    
	    if (ValidationUtil.isNullOrEmpty(clientNumber))          
	    	return "Phone number is required.";
	    
	    if (!ValidationUtil.isValidPhoneNumber(clientNumber))          
	    	return "Invalid phone number.";
	    
	    if (ValidationUtil.isNullOrEmpty(clientPassword))       
	    	return "Password is required.";
	    
	    if (!clientPassword.equals(rePassword))   
	    	return "Passwords do not match.";
	    
	    if (!ValidationUtil.isAgeAtLeast16(dob))          
	    	return "Invalid date format (YYYY‑MM‑DD).";
	    
	    if (ValidationUtil.isNullOrEmpty(gender))         
	    	return "Gender is required.";

	    return null;  // everything passed
	}

	
	/** Validate all client‑side fields. 
	 *  @return null if everything is OK, otherwise the first error message.
	 */
    private String validateLawyerForm(HttpServletRequest req) {
    	String fullName = req.getParameter("lawyerName");
    	String email = req.getParameter("lawyerEmail");
    	String phone = req.getParameter("lawyerPhone");
    	String password = req.getParameter("password");
    	String rePassword = req.getParameter("confirmPassword");
    	String licenseNumber = req.getParameter("licenseNumber");
    	String status = req.getParameter("status");
    	String district = req.getParameter("district");
    	String province = req.getParameter("province");

        if (ValidationUtil.isNullOrEmpty(fullName)) 
        	return "Full name is required.";
        
        if (ValidationUtil.isNullOrEmpty(email)) 
        	return "Email is required.";
        
        if (!ValidationUtil.isValidEmail(email)) 
        	return "Invalid email format.";
        
        if (ValidationUtil.isNullOrEmpty(phone)) 
        	return "Phone number is required.";
        
        if (ValidationUtil.isNullOrEmpty(password)) 
        	return "Password is required.";
        
        if (!password.equals(rePassword)) 
        	return "Passwords do not match.";
        
        if (ValidationUtil.isNullOrEmpty(licenseNumber)) 
        	return "License number is required.";
        
        if (ValidationUtil.isNullOrEmpty(status)) 
        	return "Status is required.";
        
        if (ValidationUtil.isNullOrEmpty(district)) 
        	return "District is required.";
        
        if (ValidationUtil.isNullOrEmpty(province)) 
        	return "Province is required.";

        // You can add more detailed validation if needed

        return null;
    }
    
    private ClientModel buildClientModel(HttpServletRequest req) {
    	String clientName = req.getParameter("clientName");
        String clientEmail = req.getParameter("clientEmail");
        String clientNumber = req.getParameter("clientPhone");
        String clientPassword = req.getParameter("password");
        LocalDate dob = LocalDate.parse(req.getParameter("clientDOB"));
        String gender = req.getParameter("clientGender");
        String address = req.getParameter("clientAddress");

        // Encrypt password
        String encryptedPassword = PasswordUtil.encrypt(clientEmail, clientPassword);

        return new ClientModel(clientName, clientEmail, address, clientNumber, encryptedPassword,  dob, gender);
    }

    private LawyerModel buildLawyerModel(HttpServletRequest req) {
        String lawyerName = req.getParameter("lawyerName");
        String lawyerEmail = req.getParameter("lawyerEmail");
        String lawyerNumber = req.getParameter("lawyerPhone");
        String Gender = req.getParameter("lawyerGender");
        String licenseNumber = req.getParameter("licenseNumber");
        LocalDate dateJoined = LocalDate.parse(req.getParameter("dateJoined"));
        String district = req.getParameter("district");
        String province = req.getParameter("province");
        String lawyerPassword = req.getParameter("password");

        //Encrypt password before storing
        String encryptedPassword = PasswordUtil.encrypt(lawyerEmail, lawyerPassword);

        return new LawyerModel(lawyerName, lawyerEmail, lawyerNumber, Gender, encryptedPassword,
    			licenseNumber, dateJoined, district, province);
    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, String error)
            throws ServletException, IOException {
        String role = req.getParameter("role");
        if ("Client".equalsIgnoreCase(role)) {
            req.setAttribute("clientName", req.getParameter("clientName"));
            req.setAttribute("clientEmail", req.getParameter("clientEmail"));
            req.setAttribute("clientPhone", req.getParameter("clientPhone"));
            req.setAttribute("clientDOB", req.getParameter("clientDOB"));
            req.setAttribute("clientGender", req.getParameter("clientGender"));
            req.setAttribute("clientAddress", req.getParameter("clientAddress"));
        } else if ("Lawyer".equalsIgnoreCase(role)) {
            req.setAttribute("lawyerName", req.getParameter("lawyerName"));
            req.setAttribute("lawyerEmail", req.getParameter("lawyerEmail"));
            req.setAttribute("lawyerPhone", req.getParameter("lawyerPhone"));
            req.setAttribute("lawyerGender", req.getParameter("lawyerGender"));
            req.setAttribute("licenseNumber", req.getParameter("licenseNumber"));
            req.setAttribute("dateJoined", req.getParameter("dateJoined"));
            req.setAttribute("district", req.getParameter("district"));
            req.setAttribute("province", req.getParameter("province"));
        }

        req.setAttribute("role", role);
        req.setAttribute("error", error);
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp); // ← Add this line
    }


}
