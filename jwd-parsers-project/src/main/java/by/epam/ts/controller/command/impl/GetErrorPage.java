package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.ts.controller.command.ActionCommand;
import by.epam.ts.controller.manager.NavigationManager;

public final class GetErrorPage implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = NavigationManager.getProperty("path.page.error");
		goForward(request, response, page);

	}

}
