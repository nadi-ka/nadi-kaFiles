package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.constant_attribute.SessionAtribute;

public class ChangeLanguageCommand implements Command{
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String local = request.getParameter(SessionAtribute.LOCALIZATION);
		session.setAttribute(SessionAtribute.LOCALIZATION, local);
		
		String command = (String) session.getAttribute("command");

        if (command != null) {
            response.sendRedirect(request.getContextPath() + "/register?command=" + command);
        } else {
            response.sendRedirect(request.getContextPath());
        }

	}
}
