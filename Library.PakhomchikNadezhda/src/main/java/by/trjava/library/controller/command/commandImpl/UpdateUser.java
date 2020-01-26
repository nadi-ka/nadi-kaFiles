package by.trjava.library.controller.command.commandImpl;

import by.trjava.library.bean.user.User;
import by.trjava.library.controller.command.Command;
import by.trjava.library.controller.parser.Parser;
import by.trjava.library.service.exceptionService.ServiceException;
import by.trjava.library.service.serviceFactory.ServiceFactory;
import by.trjava.library.service.serviceFactory.serviceFactoryImpl.ServiceFactoryImpl;
import by.trjava.library.service.userService.UserService;

public class UpdateUser implements Command {
    @Override
    public String execute(String request) {
        String response;
        User userToUpdate;
        String currentLogin = Parser.getParameterValue(request, "login");
        String currentPassword = Parser.getParameterValue(request, "password");
        String newSurname = Parser.getParameterValue(request, "newSurname");
        String newName = Parser.getParameterValue(request, "newName");
        String newLogin = Parser.getParameterValue(request, "newLogin");
        String newPassword = Parser.getParameterValue(request, "newPassword");

        ServiceFactory factory = ServiceFactoryImpl.getInstance();
        UserService userService = factory.getUserService();
        try {
            userToUpdate = userService.SignIn(currentLogin, currentPassword);
            userService.updateUser(userToUpdate, newSurname, newName, newLogin, newPassword);
            response = "Your data has been updated successfully.";
        }
        catch (ServiceException ex) {
            response = "Error during updating procedure.";
        }
        return response;
    }
}
