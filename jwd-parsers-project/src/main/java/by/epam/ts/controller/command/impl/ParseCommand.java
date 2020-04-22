package by.epam.ts.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.bean.Patient;
import by.epam.ts.controller.command.ActionCommand;
import by.epam.ts.controller.constant_attribute.RequestAttribute;
import by.epam.ts.controller.constant_attribute.RequestParameter;
import by.epam.ts.controller.manager.NavigationManager;
import by.epam.ts.service.XmlService;
import by.epam.ts.service.exception.ServiceException;
import by.epam.ts.service.exception.ServiceValidationException;
import by.epam.ts.service.factory.XmlServiceFactory;

public final class ParseCommand implements ActionCommand {

	private static final Logger log = LogManager.getLogger(ParseCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		XmlServiceFactory factory = XmlServiceFactory.getInstance();
		XmlService xmlService = factory.getXmlService();
		List<Patient> patients = new ArrayList<Patient>();
		String fileName = request.getParameter(RequestAttribute.FILE_NAME);
		String parser = request.getParameter(RequestAttribute.PARSER);
		if (fileName == null) {
			response.sendRedirect(
					request.getContextPath() + "/controller?command=get_index_page&message=need_upload_file");
		} else if (parser == null) {
			response.sendRedirect(
					request.getContextPath() + "/controller?command=get_index_page&message=need_choose_parser");
		} else {
			try {
				switch (parser) {
				case RequestParameter.SAX_PARSER:
					patients = xmlService.getPatientsListSAX(fileName);
					break;
				case RequestParameter.STAX_PARSER:
					patients = xmlService.getPatientsListStaX(fileName);
					break;
				case RequestParameter.DOM_PARSER:
					patients = xmlService.getPatientsListDOM(fileName);
					break;
				}
				request.setAttribute(RequestAttribute.PATIENTS, patients);
				request.setAttribute(RequestAttribute.FILE_NAME, fileName);
				request.setAttribute(RequestAttribute.PARSER, parser);
				String page = NavigationManager.getProperty("path.page.result");
				goForward(request, response, page);
			} catch (ServiceValidationException e) {
				log.log(Level.WARN, "Document is not valid: " + fileName, e);
				response.sendRedirect(
						request.getContextPath() + "/controller?command=get_index_page&message=invalid_document");
			} catch (ServiceException e) {
				log.log(Level.ERROR, "Parsing of the document was failed: " + fileName, e);
				response.sendRedirect(
						request.getContextPath() + "/controller?command=get_error_page&message=technical_error");
			}

		}

	}

}
