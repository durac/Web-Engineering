package at.ac.tuwien.big.we16.ue3.controller;

import at.ac.tuwien.big.we16.ue3.model.User;
import at.ac.tuwien.big.we16.ue3.model.RegistrationForm;
import at.ac.tuwien.big.we16.ue3.service.AuthService;
import at.ac.tuwien.big.we16.ue3.service.UserService;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UserController {
    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    public void getRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (this.authService.isLoggedIn(request.getSession())) {
            response.sendRedirect("/");
            return;
        }
        request.setAttribute("form", new RegistrationForm());
        request.getRequestDispatcher("/views/registration.jsp").forward(request, response);
    }

    // TODO validation of user data
    public void postRegister(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
    	
    	RegistrationForm form = new RegistrationForm();
    	boolean isvalid = form.setAndValidate(
    			request.getParameter("salutation"),
    			request.getParameter("firstname"),
    			request.getParameter("lastname"),
    			request.getParameter("dateofbirth"),
    			request.getParameter("email"),
    			request.getParameter("password"),
    			request.getParameter("streetAndNumber"),
    			request.getParameter("postcodeAndCity"),
    			request.getParameter("country"));

    	if(!isvalid) {
	    	request.setAttribute("form", form);
	    	request.getRequestDispatcher("./views/registration.jsp").forward(request, response);
    	}
    	else {
			Date datum = null;
    		SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
    		try {
    			datum = dateformat.parse(request.getParameter("dateofbirth"));
    		}
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    		
	    	User user = new User(
					UUID.randomUUID().toString(),
	    			request.getParameter("salutation"),
	    			request.getParameter("firstname"),
	    			request.getParameter("lastname"),
	    			request.getParameter("email"),
	    			request.getParameter("password"),
	    			datum,
	    			150000,
	    			0,
	    			0,
	    			0);
	        this.userService.createUser(user);
	        this.authService.login(request.getSession(), user);
	        response.sendRedirect("/");
    	}
    }

}
