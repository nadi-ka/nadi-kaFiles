package by.epam.ts.command.impl;

import javax.servlet.http.HttpServletRequest;

import by.epam.ts.command.Command;
import by.epam.ts.servlet.manager.NavigationManager;
import by.epam.ts.servlet.manager.MessageManager;

public final class WrongRequestCommand implements Command{
	
	@Override
	public String execute(HttpServletRequest request) {
		String message = MessageManager.getProperty("local.wrongcommand");
		request.setAttribute("wrongCommandMessage", message);
		String page = NavigationManager.getProperty("path.page.error");
		return page;
	}


}
