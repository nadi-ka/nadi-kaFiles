package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.command.access_manager.AccessManager;
import by.epam.ts.controller.command.util.builder.RedirectBuilder;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.service.HospitalizationService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ValidationServiceException;
import by.epam.ts.service.factory.ServiceFactory;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class AddHospitalizationCommand implements Command, AccessManager {

	private static final Logger log = LogManager.getLogger(AddHospitalizationCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Checking of the user rights;
		boolean staffRights = checkDoctorRights(request);

		if (!staffRights) {
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.ACCESS_DENIED).getResultString());

			return;
		}

		String patientId = request.getParameter(RequestAtribute.PATIENT_ID);
		String entryDate = request.getParameter(RequestAtribute.DATE_BEGINNING);
		String alreadyHospitalized = request.getParameter(RequestAtribute.ALREADY_HOSPITALIZED);
		String alreadyDischarged = request.getParameter(RequestAtribute.ALREADY_DISCHARGED);

		// If current hospitalization is open, the patient can't be hospitalized again;
		if ((alreadyHospitalized != null && !alreadyHospitalized.isEmpty())
				&& (alreadyDischarged == null || alreadyDischarged.isEmpty())) {
			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_HOSPITALIZATION_PAGE.toString().toLowerCase());
			response.sendRedirect(
					builder.setMessage(RequestMessage.HOSPITALIZED_ELIER).setPatientId(patientId).getResultString());
			
			return;
		}

		ServiceFactory factory = ServiceFactoryImpl.getInstance();
		HospitalizationService service = factory.getHospitalizationService();

		try {
			service.addNewHospitalisation(patientId, entryDate);

			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_CURRENT_PATIENT_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.HOSPITALIZATION_ADDED_SUCCESSFULY)
					.setPatientId(patientId).getResultString());

		} catch (ValidationServiceException e) {
			log.log(Level.WARN,
					"Error when calling userService.addNewHospitalisation() from  AddHospitalizationCommand. Invalid parameters:",
					e);

			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.GET_HOSPITALIZATION_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.ERROR_DATA).setPatientId(patientId)
					.setInvalidParameters(e.getMessage()).getResultString());

		} catch (ServiceException e) {
			log.log(Level.ERROR,
					"Error when calling userService.addNewHospitalisation() from  AddHospitalizationCommand.", e);

			RedirectBuilder builder = new RedirectBuilder(request.getContextPath(), RequestAtribute.CONTROLLER_FONT,
					CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase());
			response.sendRedirect(builder.setMessage(RequestMessage.TECHNICAL_ERROR).getResultString());

		}

	}

}
