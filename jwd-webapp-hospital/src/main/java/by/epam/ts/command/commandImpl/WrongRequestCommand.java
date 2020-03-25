package by.epam.ts.command.commandImpl;

import javax.servlet.http.HttpServletRequest;

import by.epam.ts.command.ActionCommand;
import by.epam.ts.service.serviceFactory.ServiceFactory;
import by.epam.ts.servlet.manager.ConfigurationManager;
import by.epam.ts.servlet.manager.MessageManager;

public final class WrongRequestCommand implements ActionCommand{
	private ServiceFactory factory;
	
	public  WrongRequestCommand(ServiceFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public String execute(HttpServletRequest request) {
		String message = MessageManager.getProperty("message.wrongCommandMessage");
		request.setAttribute("wrongCommandMessage", message);
		String page = ConfigurationManager.getProperty("path.page.error");
		return page;
	}


}
