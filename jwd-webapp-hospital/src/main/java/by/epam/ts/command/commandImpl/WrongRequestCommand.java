package by.epam.ts.command.commandImpl;

import javax.servlet.http.HttpServletRequest;

import by.epam.ts.command.ActionCommand;
import by.epam.ts.servlet.manager.ConfigurationManager;
import by.epam.ts.servlet.manager.MessageManager;

public class WrongRequestCommand implements ActionCommand{
	
	@Override
	public String execute(HttpServletRequest request) {
		request.setAttribute("wrondCommandMessage", 
				MessageManager.getProperty("message.wrongCommandMessage"));
		String page = ConfigurationManager.getProperty("path.page.error");
		return page;
	}


}
