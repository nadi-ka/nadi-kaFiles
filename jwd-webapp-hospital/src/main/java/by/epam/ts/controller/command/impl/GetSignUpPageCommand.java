package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.manager.NavigationManager;

public final class GetSignUpPageCommand implements Command{
	
	private static final Logger log = LogManager.getLogger(GetSignUpPageCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String page = NavigationManager.getProperty("path.page.signUp");
		goForward(request, response, page);
		
	}

}
