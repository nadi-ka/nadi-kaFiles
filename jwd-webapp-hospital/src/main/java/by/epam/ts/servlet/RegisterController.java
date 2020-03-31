package by.epam.ts.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.command.Command;
import by.epam.ts.command.CommandProvider;
import by.epam.ts.servlet.manager.MessageManager;
import by.epam.ts.servlet.manager.NavigationManager;


public class RegisterController extends HttpServlet {
private static final long serialVersionUID = 1L;
	
	private static final Logger log = LogManager.getLogger(RegisterController.class);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		log.info("Logger from RegisterController.");
		String page = null;
		request.getSession(true).setAttribute("local", request.getParameter("local"));
		CommandProvider provider = new CommandProvider();
		Command command = provider.defineCommand(request);
		page = command.execute(request);
		
		if (page == null) {
			page = NavigationManager.getProperty("path.page.index");
			String message = MessageManager.getProperty("local.nullpage");
			request.getSession().setAttribute("nullPage", message);
			response.sendRedirect(request.getContextPath() + page);
		}
		
		else {
			RequestDispatcher dispatcher = request.getRequestDispatcher(page);
			dispatcher.forward(request, response);	
		}
	}
}
	
