package src.main.java.by.epam.ts.command.commandImpl;

import javax.servlet.http.HttpServletRequest;

import src.main.java.by.epam.ts.command.ActionCommand;
import src.main.java.by.epam.ts.servlet.manager.ConfigurationManager;
import src.main.java.by.epam.ts.servlet.manager.MessageManager;

public class WrongRequestCommand implements ActionCommand{
	
	public String execute(HttpServletRequest request) {
		String message = MessageManager.getProperty("message.wrongCommandMessage");
		request.setAttribute("wrongCommandMessage", message);
		String page = ConfigurationManager.getProperty("path.page.error");
		return page;
	}


}
