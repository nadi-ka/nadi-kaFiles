package by.epam.ts.command.impl;

import javax.servlet.http.HttpServletRequest;

import by.epam.ts.command.Command;
import by.epam.ts.servlet.manager.PageManager;

public final class LogoutCommand implements Command{
	
	@Override
	public String execute(HttpServletRequest request) {
		String page = PageManager.getProperty("path.page.index");
		request.getSession().invalidate();
		
		return page;
	}

}
