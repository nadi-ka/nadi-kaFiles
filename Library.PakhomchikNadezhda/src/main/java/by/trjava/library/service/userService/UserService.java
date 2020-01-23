package by.trjava.library.service.userService;

import by.trjava.library.beans.user.User;
import by.trjava.library.service.exceptionService.ServiceException;

import java.util.List;

public interface UserService {

    void register(String surname, String name, String login, String password) throws ServiceException;

    User SignIn(String login, String password) throws ServiceException;

    void setPermissionAdministrator(User userToSetPermission, User userWhoPerform) throws ServiceException;

    boolean deleteUser(User userToDelete, User userWhoPerform) throws ServiceException;

    void updateUser(User currentUser, String newSurname, String newName,
                    String newLogin, String newPassword) throws ServiceException;

    User findUserById(String id, User userAdministrator) throws ServiceException;

    List<User> findUserBySurname(String surname, User userAdministrator) throws ServiceException;
}
