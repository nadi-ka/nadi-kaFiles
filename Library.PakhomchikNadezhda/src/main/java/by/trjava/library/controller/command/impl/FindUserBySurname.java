package by.trjava.library.controller.command.impl;

import by.trjava.library.beans.user.User;
import by.trjava.library.controller.command.Command;
import by.trjava.library.controller.parser.Parser;
import by.trjava.library.service.exceptionService.ServiceException;
import by.trjava.library.service.serviceFactory.ServiceFactory;
import by.trjava.library.service.serviceFactory.ServiceFactoryImpl;
import by.trjava.library.service.userService.UserService;

import java.util.ArrayList;
import java.util.List;

public class FindUserBySurname implements Command {
    @Override
    public String execute(String request) {
        String userToFindSurname = Parser.getParameterValue(request, "surname");
        String userWhoPerformLogin = Parser.getParameterValue(request, "login");
        String userWhoPerformPassword = Parser.getParameterValue(request, "password");
        List<User> usersBySurname;
        User userWhoPerform;
        String response;

        ServiceFactory factory =  ServiceFactoryImpl.getInstance();
        UserService userService = factory.getUserService();
        try {
            userWhoPerform = userService.SignIn(userWhoPerformLogin, userWhoPerformPassword);
            usersBySurname = userService.findUserBySurname(userToFindSurname, userWhoPerform);
            List<String> logins = new ArrayList<>();
            for (User one: usersBySurname) {
                logins.add(one.getLogin());
            }
            response = "The user was found successfully." + " Login: " + logins;
        }
        catch (ServiceException ex) {
            response = "Error during the operation. The user wasn't found.";
        }
        return response;
    }
}
