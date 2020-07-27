package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.net.URLEncoder;
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
import by.epam.ts.controller.command.access_manager.AccessManager;
import by.epam.ts.controller.command.util.mailer.Mailer;
import by.epam.ts.controller.command.util.mailer.MailerException;
import by.epam.ts.controller.constant_attribute.MailAttributes;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public class AddNewPatientCommand implements Command, AccessManager {

	private static final String mustBeAdded = "true";
	private static final String ENCODING = "UTF-8";
	
	private static final Logger log = LogManager.getLogger(AddNewPatientCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Checking of the user rights;
		boolean staffRights = checkDoctorRights(request);
		if (!staffRights) {
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.ACCESS_DENIED);
			return;
		}
		String surname = request.getParameter(RequestAtribute.SURNAME);
		String surnameUTF8 = URLEncoder.encode(surname, ENCODING);
		String name = request.getParameter(RequestAtribute.NAME);
		String nameUTF8 = URLEncoder.encode(name, ENCODING);
		String email = request.getParameter(RequestAtribute.EMAIL);
		String dateOfBirth = request.getParameter(RequestAtribute.DATE_OF_BIRTH);

		// if List of patients with the same surnames was found, but the variant "anyway
		// add new patient" was chosen;
		String addObviously = request.getParameter(RequestAtribute.ADD_OBVIOUSLY);

		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		String patientId = null;
		try {
			// checking if patient with such surname already exists;
			List<Patient> patients = userService.getPatientBySurname(surname);
			// if "anyway add new patient" was chosen or surname is unique;
			if ((addObviously != null && addObviously.equals(mustBeAdded)) || patients.isEmpty()) {
				patientId = userService.addNewPatient(surname, name, dateOfBirth, email);
				
				//Send letter to the given patient's e-mail;
				Mailer mailer = new Mailer();
				mailer.send(MailAttributes.LETTER_SUBJECT, MailAttributes.LETTER_BODY_NEW_PATIENT, MailAttributes.TEMPORARY_EMAIL_FOR_CHECK);
	
				response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT
						+ RequestAtribute.COMMAND + "=" + CommandEnum.GET_CURRENT_PATIENT_PAGE.toString().toLowerCase()
						+ "&" + RequestAtribute.MESSAGE + "=" + RequestMessage.ADDED_SUCCESSFULY + "&"
						+ RequestAtribute.PATIENT_ID + "=" + patientId);
			} else {
				// display list of existing patients with the same surnames and add to request current
				// patients data, which were entered in form;
				response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT
						+ RequestAtribute.COMMAND + "=" + CommandEnum.GET_PATIENT_DATA_PAGE.toString().toLowerCase()
						+ "&" + RequestAtribute.MESSAGE + "=" + RequestMessage.PATIENT_EXISTS + "&"
						+ RequestAtribute.SURNAME + "=" + surnameUTF8 + "&" + RequestAtribute.NAME + "=" + nameUTF8 + "&"
						+ RequestAtribute.DATE_OF_BIRTH + "=" + dateOfBirth + "&" + RequestAtribute.EMAIL + "="
						+ email);
			}
		} catch (ValidationServiceException e) {
			log.log(Level.WARN,
					"Error when calling userService.addNewPatient(patient) from  AddNewPatientCommand. Invalid parameters:",
					e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_STAFF_MAIN_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE
					+ "=" + RequestMessage.ERROR_DATA);
		} catch (ServiceException e) {
			log.log(Level.ERROR, "The patient wasn't added", e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		}catch (MailerException e) {
			log.log(Level.WARN, "Error when calling send()from AddNewPatientCommand", e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT
					+ RequestAtribute.COMMAND + "=" + CommandEnum.GET_CURRENT_PATIENT_PAGE.toString().toLowerCase()
					+ "&" + RequestAtribute.MESSAGE + "=" + RequestMessage.ADDED_SUCCESSFULY + "&"
					+ RequestAtribute.PATIENT_ID + "=" + patientId);
		}

	}

}
