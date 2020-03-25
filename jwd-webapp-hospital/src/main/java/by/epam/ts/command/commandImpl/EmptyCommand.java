package by.epam.ts.command.commandImpl;

import javax.servlet.http.HttpServletRequest;

import by.epam.ts.command.ActionCommand;
import by.epam.ts.service.serviceFactory.ServiceFactory;
import by.epam.ts.servlet.manager.ConfigurationManager;

public final class EmptyCommand implements ActionCommand{
	private ServiceFactory factory;
	
	public EmptyCommand(ServiceFactory factory) {
		this.factory = factory;
	}

	@Override
	public String execute(HttpServletRequest request) {
		String page = ConfigurationManager.getProperty("path.page.login");
		return page;
	}

}
