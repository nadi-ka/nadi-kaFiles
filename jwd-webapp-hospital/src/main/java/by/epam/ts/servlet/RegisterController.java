package by.epam.ts.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		String page;
		HttpSession session = request.getSession(true);
		String local = request.getParameter("local");
		session.setAttribute("local", local);
		log.info("We are after session.local local == null: " + session.getAttribute("local")==null);
		CommandProvider provider = new CommandProvider();
		Command command = provider.defineCommand(request);
		page = command.execute(request);

		if (page != null) {
			log.info("We are in if. Page = " + page);
			RequestDispatcher dispatcher = request.getRequestDispatcher(page);
			dispatcher.forward(request, response);
			log.info("Logger from RegisterController -ArfetForward (if).");
		} else {
			page = NavigationManager.getProperty("path.page.index");
			log.info("Logger from RegisterController. (else, nullPage)");
			String message = MessageManager.getProperty("local.nullpage");
			request.getSession().setAttribute("nullPage", message);
			response.sendRedirect(request.getContextPath() + page);
		}
	}
}
