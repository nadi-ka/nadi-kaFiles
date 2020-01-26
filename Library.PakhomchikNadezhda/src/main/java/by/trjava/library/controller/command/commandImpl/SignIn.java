package by.trjava.library.controller.command.commandImpl;

import by.trjava.library.controller.command.Command;
import by.trjava.library.controller.parser.Parser;
import by.trjava.library.service.exceptionService.ServiceException;
import by.trjava.library.service.serviceFactory.ServiceFactory;
import by.trjava.library.service.serviceFactory.serviceFactoryImpl.ServiceFactoryImpl;
import by.trjava.library.service.userService.UserService;

public class SignIn implements Command {
    @Override
    public String execute(String request) {
        String response;
        String login = Parser.getParameterValue(request, "login");
        String password = Parser.getParameterValue(request, "password");

        ServiceFactory factory = ServiceFactoryImpl.getInstance();
        UserService userService = factory.getUserService();
        try {
            userService.SignIn(login, password);
            response = "Welcome, " + login + "!";
        }
        catch (ServiceException ex) {
            response = "Error during login procedure.";
        }
        return response;
    }
}
