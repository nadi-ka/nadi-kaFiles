package by.epam.ts.command.commandImpl;

import javax.servlet.http.HttpServletRequest;

import by.epam.ts.command.ActionCommand;
import by.epam.ts.service.serviceFactory.ServiceFactory;
import by.epam.ts.servlet.manager.ConfigurationManager;

public final class LogoutCommand implements ActionCommand{
	private ServiceFactory factory;
	
	public LogoutCommand(ServiceFactory factory) {
		this.factory = factory;
	}

	@Override
	public String execute(HttpServletRequest request) {
		String page = ConfigurationManager.getProperty("path.page.index");
		request.getSession().invalidate();
		
		return page;
	}

}
