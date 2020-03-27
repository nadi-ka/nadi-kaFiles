package by.epam.ts.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.tools.picocli.CommandLine.Command;

import by.epam.ts.command.CommandProvider;
import by.epam.ts.servlet.manager.MessageManager;
import by.epam.ts.servlet.manager.PageManager;

public class RegisterController extends HttpServlet {
private static final long serialVersionUID = 1L;
	
	static final Logger log = LogManager.getLogger(RegisterController.class);
	
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
		log.info("Logger!!!");
		String page = null;
		CommandProvider provider = new CommandProvider();
		Command command = provider.defineCommand(request);
		page = command.execute(request);
		
		if (page == null) {
			page = PageManager.getProperty("path.page.index");
			request.getSession().setAttribute("nullPage", MessageManager.getProperty("message.nullpage"));
			response.sendRedirect(request.getContextPath() + page);
		}
		
		else {
			RequestDispatcher dispatcher = request.getRequestDispatcher(page);
			dispatcher.forward(request, response);	
		}
	}
}
	
