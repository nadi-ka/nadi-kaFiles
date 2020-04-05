package by.epam.ts.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.ts.controller.command.Command;
import by.epam.ts.controller.constant_attribute.RequestAtribute;
import by.epam.ts.controller.manager.MessageManager;

public final class LogoutCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mesaage = MessageManager.getProperty("local.login.successfully.logout");
		request.setAttribute(RequestAtribute.SUCCESSFUL_LOGOUT, mesaage);
		request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/register?command=show_index_page");
		
	}

}
