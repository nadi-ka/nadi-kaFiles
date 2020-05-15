package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.Patient;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.controller.manager.NavigationManager;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public class AddNewPatientCommand implements Command {
	
	private static final String mustBeAdded = "true";

	private static final Logger log = LogManager.getLogger(AddNewPatientCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Checking of the user rights;
		boolean staffRights = checkStaffRights(request, response);
		if (!staffRights) {
			response.sendRedirect(request.getContextPath() + "/font?" + RequestAtribute.COMMAND + "="
					+ CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.ACCESS_DENIED);
			return;
		}
		String surname = request.getParameter(RequestAtribute.SURNAME);
		String name = request.getParameter(RequestAtribute.NAME);
		String email = request.getParameter(RequestAtribute.EMAIL);
		String dateOfBirth = request.getParameter(RequestAtribute.DATE_OF_BIRTH);
		//if List of patients with the same surnames was found, but the variant "anyway add new patient" was chosen;
		String addObviously = request.getParameter(RequestAtribute.ADD_OBVIOUSLY);

		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		try {
			// checking if patient with such surname already exists;
			List<Patient> patients = userService.getPatientBySurname(surname);
			//if "anyway add new patient" was chosen or surname is unique;
			if ((addObviously != null && addObviously.equals(mustBeAdded)) || patients.isEmpty()) {
				String patientId = userService.addNewPatient(surname, name, dateOfBirth, email);

				response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND + "="
						+ CommandEnum.GET_CURRENT_PATIENT_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE
						+ "=" + RequestMessage.ADDED_SUCCESSFULY + "&" + RequestAtribute.PATIENT_ID + "=" + patientId);
			} else {
				// show existing patients with the same surnames;
				request.setAttribute(RequestAtribute.LIST_PATIENTS, patients);
				request.setAttribute(RequestAtribute.SURNAME, surname);
				request.setAttribute(RequestAtribute.NAME, name);
				request.setAttribute(RequestAtribute.EMAIL, email);
				request.setAttribute(RequestAtribute.DATE_OF_BIRTH, dateOfBirth);
				request.setAttribute(RequestAtribute.MESSAGE, RequestMessage.PATIENT_EXISTS);
				String page = NavigationManager.getProperty("path.page.staff.patient_data");
				goForward(request, response, page);
			}
		} catch (ValidationServiceException e) {
			log.log(Level.WARN,
					"Error when calling userService.addNewPatient(patient) from  AddNewPatientCommand. Invalid parameters:",
					e);
			request.setAttribute(RequestAtribute.MESSAGE, RequestMessage.ERROR_DATA);
			response.sendRedirect(request.getContextPath() + "/font?" + RequestAtribute.COMMAND + "="
					+ CommandEnum.GET_STAFF_MAIN_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.ERROR_DATA);
		} catch (ServiceException e) {
			log.log(Level.ERROR, "The patient wasn't added", e);
			request.setAttribute(RequestAtribute.MESSAGE, RequestMessage.TECHNICAL_ERROR);
			response.sendRedirect(request.getContextPath() + "/font?" + RequestAtribute.COMMAND + "="
					+ CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		}

	}

}
