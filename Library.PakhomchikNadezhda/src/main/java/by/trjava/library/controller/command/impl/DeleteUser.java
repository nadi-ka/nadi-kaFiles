package by.trjava.library.controller.command.impl;

import by.trjava.library.beans.user.User;
import by.trjava.library.controller.command.Command;
import by.trjava.library.controller.parser.Parser;
import by.trjava.library.service.exceptionService.ServiceException;
import by.trjava.library.service.serviceFactory.ServiceFactory;
import by.trjava.library.service.serviceFactory.ServiceFactoryImpl;
import by.trjava.library.service.userService.UserService;

public class DeleteUser implements Command {

    @Override
    public String execute(String request) {
        String userToDeleteId = Parser.getParameterValue(request, "id");
        String userWhoPerformLogin = Parser.getParameterValue(request, "login");
        String userWhoPerformPassword = Parser.getParameterValue(request, "password");
        User userToDelete;
        User userWhoPerform;
        String response;

        ServiceFactory factory =  ServiceFactoryImpl.getInstance();
        UserService userService = factory.getUserService();
        try {
            userWhoPerform = userService.SignIn(userWhoPerformLogin, userWhoPerformPassword);
            userToDelete = userService.findUserById(userToDeleteId, userWhoPerform);
            userService.deleteUser(userToDelete, userWhoPerform);
            response = "The user was deleted successfully.";
        }
        catch (ServiceException ex) {
            response = "Error during the operation. The user wasn't deleted.";
        }
        return response;
    }
}
