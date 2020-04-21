package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class GiveConsentCommand implements Command{
	
	private final static String ID_APPOINTMENT = "id_appointment";
	private final static String CONSENT = "consent";
	
	private static final Logger log = LogManager.getLogger(GiveConsentCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Integer idAppointment = Integer.parseInt(request.getParameter(ID_APPOINTMENT));
		Boolean consent = Boolean.parseBoolean(request.getParameter(CONSENT));
		
		Map<Integer, Boolean> consentMap = new HashMap<Integer, Boolean>();
		consentMap.put(idAppointment, consent);
		
		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		try {
			userService.getPatientsConsent(consentMap);
			response.sendRedirect(request.getContextPath() + "/register?command=show_treatment");
		}catch (ServiceException ex) {
			log.log(Level.ERROR, "Error during calling method execute from GiveConsentCommand", ex);
			request.setAttribute(RequestAtribute.MESSAGE, RequestAtribute.TECHNICAL_ERROR);
			response.sendRedirect(request.getContextPath() + "/register?command=show_error_page&message=technical_error");
		}
		
	}

}
