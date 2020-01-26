package by.trjava.library.controller.command.commandImpl;

import by.trjava.library.bean.user.User;
import by.trjava.library.controller.command.Command;
import by.trjava.library.controller.parser.Parser;
import by.trjava.library.service.exceptionService.ServiceException;
import by.trjava.library.service.serviceFactory.ServiceFactory;
import by.trjava.library.service.serviceFactory.serviceFactoryImpl.ServiceFactoryImpl;
import by.trjava.library.service.userService.UserService;

public class SetPermissionAdministrator implements Command {

    @Override
    public String execute(String request) {
        String response;
        String userId = Parser.getParameterValue(request, "id");
        String userWhoPerformLogin = Parser.getParameterValue(request, "login");
        String userWhoPerformPassword = Parser.getParameterValue(request, "password");
        User userToSetPermission;
        User userWhoPerform;

        ServiceFactory factory = ServiceFactoryImpl.getInstance();
        UserService userService = factory.getUserService();
        try {
            userWhoPerform = userService.SignIn(userWhoPerformLogin, userWhoPerformPassword);
            userToSetPermission = userService.findUserById(userId, userWhoPerform);
            userService.setPermissionAdministrator(userToSetPermission, userWhoPerform);
            response = "The permission of administrator has been set successfully.";
        }
        catch (ServiceException ex) {
            response = "Error during the operation of setting administrator's permission.";
        }
        return response;
    }
}
