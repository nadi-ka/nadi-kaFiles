package by.trjava.library.controller.command.commandImpl;

import by.trjava.library.controller.command.Command;
import by.trjava.library.controller.parser.Parser;
import by.trjava.library.service.exceptionService.ServiceException;
import by.trjava.library.service.serviceFactory.ServiceFactory;
import by.trjava.library.service.serviceFactory.serviceFactoryImpl.ServiceFactoryImpl;
import by.trjava.library.service.userService.UserService;

public class Register implements Command {
    @Override
    public String execute(String request) {
        String response;
        String surname = Parser.getParameterValue(request, "newSurname");
        String name = Parser.getParameterValue(request, "newName");
        String login = Parser.getParameterValue(request, "newLogin");
        String password = Parser.getParameterValue(request, "newPassword");

        ServiceFactory factory = ServiceFactoryImpl.getInstance();
        UserService userService = factory.getUserService();
        try {
            userService.register(surname, name, login, password);
            response = "You have bean registered successfully!";
        }
        catch (ServiceException ex) {
            response = "Error during registration procedure.";
        }
        return response;
    }
}
