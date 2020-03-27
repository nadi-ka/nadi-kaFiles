package by.epam.ts.command.impl;

import javax.servlet.http.HttpServletRequest;

import by.epam.ts.command.Command;
import by.epam.ts.servlet.manager.PageManager;
import by.epam.ts.servlet.manager.MessageManager;

public final class WrongRequestCommand implements Command{
	
	@Override
	public String execute(HttpServletRequest request) {
		String message = MessageManager.getProperty("message.wrongCommandMessage");
		request.setAttribute("wrongCommandMessage", message);
		String page = PageManager.getProperty("path.page.error");
		return page;
	}


}
