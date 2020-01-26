package by.trjava.library.service.validation;

import by.trjava.library.bean.user.User;
import by.trjava.library.bean.user.UserRole;

import java.util.List;

public class UserValidator {

    public static boolean isCorrectName(String name) {
        if (name != null && name.matches("[a-zA-z]+")) {
            return true;
        }
        return false;
    }

    public static boolean isCorrectSurname(String surname) {
        if (surname != null && surname.matches("[a-zA-z]+")) {
            return true;
        }
        return false;
    }

    public static boolean isCorrectLogin(String login) {
        if (login != null && login.matches("[a-zA-z0-9]{4,}")) {
            return true;
        }
        return false;
    }

    public static boolean isCorrectPassword(String password) {
        if (password != null && password.matches("[a-zA-z0-9]{4,}")) {
            return true;
        }
        return false;
    }

    public static boolean isUniqueLogin(List<User> users, String login) {
        if (users == null) {
            return true;
        }
        for (User one : users) {
            if (one.getLogin().equals(login)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isUniqueLoginToUpdateUser(List<User> users, User user, String newLogin) {
        for (User one : users) {
            if (one.getLogin().equals(newLogin) && !one.getId().equals(user.getId())) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkLoginAndPassword(User user, String login, String password) {
        if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public static boolean isAdministrator(User user) {
        if (user != null && user.getUserRole() == UserRole.ADMINISTRATOR) {
            return true;
        }
        return false;
    }
}
