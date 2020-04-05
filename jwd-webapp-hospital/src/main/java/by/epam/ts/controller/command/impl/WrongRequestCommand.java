package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.manager.MessageManager;
import by.epam.ts.controller.manager.NavigationManager;

public final class WrongRequestCommand implements Command{
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = MessageManager.getProperty("local.wrongcommand");
		request.setAttribute(RequestAtribute.WRONG_ACTION, message);
		String page = NavigationManager.getProperty("path.page.error");
		response.sendRedirect(page);
		
	}

}
