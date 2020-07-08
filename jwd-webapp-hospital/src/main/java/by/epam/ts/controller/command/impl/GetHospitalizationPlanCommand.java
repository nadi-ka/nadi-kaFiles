package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.Hospitalization;
import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.command.CommandEnum;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.constant_attribute.RequestMessage;
import by.epam.ts.controller.manager.NavigationManager;
import by.epam.ts.service.UserService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.factory.impl.ServiceFactoryImpl;

public final class GetHospitalizationPlanCommand implements Command {

	private static final String PATH = "path.page.hospitalization_plan";
	private static final Logger log = LogManager.getLogger(GetHospitalizationPlanCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String patientId = getUserIdFromSession(request);
		if ((patientId == null) || patientId.isEmpty()) {
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND
					+ "=" + CommandEnum.GET_INDEX_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.ACCESS_DENIED);
			return;
		}

		ServiceFactoryImpl factory = ServiceFactoryImpl.getInstance();
		UserService userService = factory.getUserService();
		Hospitalization lastHospitalization;
		int hospitalizationLength;

		try {
			// attempt to get the last hospitalization;
			lastHospitalization = userService.getLastHospitalizationById(patientId);
			if (lastHospitalization != null) {
				// the patient has already been hospitalized;
				// get the last entry date and attempt to get the last discharge date;
				LocalDate entryDate = lastHospitalization.getEntryDate();
				LocalDate dischargeDate = lastHospitalization.getDischargeDate();
				String page = NavigationManager.getProperty(PATH);
				if (dischargeDate != null) {
					// the patient has been already discharged, hospitalization was closed;
					request.setAttribute(RequestAtribute.HOSPITALIZATION, lastHospitalization);
					request.setAttribute(RequestAtribute.MESSAGE, RequestMessage.ALREADY_DISCHARGED);
					request.setAttribute(RequestAtribute.DATE_FINISHING, dischargeDate.toString());
					goForward(request, response, page);

				} else {
					// attempt to get average amount of bed days by primary diagnosis;
					hospitalizationLength = userService.getAverageHospitalizationLength(patientId, entryDate);
					if (hospitalizationLength == 0) {
						//the primary diagnosis is absent in current moment;
						request.setAttribute(RequestAtribute.HOSPITALIZATION, lastHospitalization);
						request.setAttribute(RequestAtribute.MESSAGE, RequestMessage.DIAGNOSIS_ABSENT);
						goForward(request, response, page);
						return;
					}
					LocalDate expectedDischargeDate = entryDate.plusDays(hospitalizationLength);
					request.setAttribute(RequestAtribute.HOSPITALIZATION, lastHospitalization);
					request.setAttribute(RequestAtribute.BED_DAYS, hospitalizationLength);
					request.setAttribute(RequestAtribute.DATE_FINISHING, expectedDischargeDate);
					goForward(request, response, page);
				}
			} else {
				// the patient hasn't been hospitalized yet;
				response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND + "="
						+ CommandEnum.GET_PATIENT_MAIN_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE
						+ "=" + RequestMessage.NO_CURRENT_HOSPITALIZATION);
			}

		} catch (ServiceException e) {
			log.log(Level.ERROR, "Error when calling execute() from GetHospitalizationPlanCommand", e);
			response.sendRedirect(request.getContextPath() + RequestAtribute.CONTROLLER_FONT + RequestAtribute.COMMAND + "="
					+ CommandEnum.SHOW_ERROR_PAGE.toString().toLowerCase() + "&" + RequestAtribute.MESSAGE + "="
					+ RequestMessage.TECHNICAL_ERROR);
		}
	}
}
