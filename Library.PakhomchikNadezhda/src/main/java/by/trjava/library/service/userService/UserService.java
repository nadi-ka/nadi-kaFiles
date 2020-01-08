package by.trjava.library.service.userService;

import by.trjava.library.bean.user.User;
import by.trjava.library.service.exceptionService.ServiceException;

import java.util.List;

public interface UserService {

    void register(String surname, String name, String login, String password) throws ServiceException;

    User logIn(String login, String password) throws ServiceException;

    void setPermissionAdministrator(User userToSetPermission, User userWhoPerform) throws ServiceException;

    boolean deleteUser(User userToDelete, User userWhoPerform) throws ServiceException;

    void updateUser(long id, String login, String password, String surname, String name,
                    String newLogin, String newPassword) throws ServiceException;

    User findUserById(long id, User userAdministrator) throws ServiceException;

    List<User> findUserBySurname(String surname, User userAdministrator) throws ServiceException;
}
