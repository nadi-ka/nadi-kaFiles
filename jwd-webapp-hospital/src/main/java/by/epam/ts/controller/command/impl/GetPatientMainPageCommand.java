package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.manager.NavigationManager;

public final class GetPatientMainPageCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String page = NavigationManager.getProperty("path.page.main");
        goForward(request, response, page);

	}

}
