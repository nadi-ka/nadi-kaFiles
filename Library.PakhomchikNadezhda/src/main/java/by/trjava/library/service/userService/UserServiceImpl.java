package by.trjava.library.service.userService;

import by.trjava.library.dal.daoFactory.DaoFactory;
import by.trjava.library.dal.daoFactory.JsonDaoFactory;
import by.trjava.library.dal.exeptionDao.DAOException;
import by.trjava.library.dal.userDao.UserDao;
import by.trjava.library.bean.user.User;
import by.trjava.library.bean.user.UserRole;
import by.trjava.library.service.exceptionService.ServiceException;
import by.trjava.library.service.validation.ValidationUser;
import java.util.List;

public class UserServiceImpl implements UserService {

    DaoFactory factory = JsonDaoFactory.getInstance();
    UserDao userDao = factory.getUserDao();

    public void register(String surname, String name, String login, String password)
            throws ServiceException {
        if (ValidationUser.isCorrectSurname(surname) && ValidationUser.isCorrectName(name) &&
                ValidationUser.isCorrectLogin(login) && ValidationUser.isCorrectPassword(password)) {
            User user;
            List<User> list;
            try {
                list = userDao.getAllUsers();
                if (ValidationUser.isUniqueLogin(list, login)) {
                    user = new User(surname, name, login, password);
                    userDao.addUser(user);
                }
            } catch (DAOException ex) {
                throw new ServiceException("The user cannot be registered! " + login);
            }
        } else {
            throw new ServiceException("Incorrect data! " + surname + " " + name + " " + login + " " + password);
        }
    }

    public User logIn(String login, String password) throws ServiceException {
        List<User> list;
        try {
            list = userDao.getAllUsers();
            for (User one : list) {
                if (ValidationUser.checkLoginAndPassword(one, login, password)) {
                    return one;
                }
            }
            return null;
        } catch (DAOException ex) {
            throw new ServiceException("The user with such login and password is not in exist! " + login +
                    " " + password);
        }
    }

    public void setPermissionAdministrator(User userToSetPermission, User userWhoPerform) throws ServiceException {
        List<User> list;
        try {
            list = userDao.getAllUsers();
            for (User one : list) {
                if (one.getId() == userToSetPermission.getId()) {
                    one.setUserRole(UserRole.ADMINISTRATOR);
                    break;
                }
            }
            userDao.addUsers(list);
        } catch (DAOException ex) {
            throw new ServiceException("The user's role wasn't changed!");
        }
    }

    public boolean deleteUser(User userToDelete, User userWhoPerform) throws ServiceException {
        if (ValidationUser.isAdministrator(userWhoPerform) ||
                (!ValidationUser.isAdministrator(userWhoPerform) && userToDelete.equals(userWhoPerform))) {
            try {
                return userDao.deleteUser(userToDelete);
            } catch (DAOException ex) {
                throw new ServiceException("The user wasn't deleted! " + userToDelete.toString());
            }
        } else {
            throw new ServiceException("This operation cannot be supported by the user! " + userWhoPerform.toString());
        }
    }

    public void updateUser(long id, String currentLogin, String currentPassword, String surname, String name,
                           String newLogin, String newPassword)
            throws ServiceException {
        if (ValidationUser.isCorrectSurname(surname) && ValidationUser.isCorrectName(name) &&
                ValidationUser.isCorrectLogin(newLogin) && ValidationUser.isCorrectPassword(newPassword)) {
            List<User> users;
            try {
                users = userDao.getAllUsers();
                for (User one: users) {
                    if (ValidationUser.checkLoginAndPassword(one, currentLogin, currentPassword)) {
                        one.setSurname(surname);
                        one.setName(name);
                        one.setLogin(newLogin);
                        one.setPassword(newPassword);
                        userDao.addUsers(users);
                    }
                }
            } catch (DAOException ex) {
                throw new ServiceException("The user wasn't updated! " + id);
            }
        } else {
            throw new ServiceException("Incorrect data! " + surname + " " + name + " " + newLogin + " " + newPassword);
        }
    }

    public User findUserById(long id, User userAdministrator) throws ServiceException {
        User user;
        if (ValidationUser.isAdministrator(userAdministrator)) {
            try {
                user = userDao.getUserById(id);
                return user;
            } catch (DAOException ex) {
                throw new ServiceException("The user wasn't found! " + id);
            }
        } else {
            throw new ServiceException("Permission of administrator wasn't confirmed!");
        }
    }

    public List<User> findUserBySurname(String surname, User userAdministrator) throws ServiceException {
        List<User> list;
        if (ValidationUser.isAdministrator(userAdministrator)) {
            try {
                list = userDao.getUserBySurname(surname);
                return list;
            } catch (DAOException ex) {
                throw new ServiceException("The user wasn't found! " + surname);
            }
        } else {
            throw new ServiceException("Permission Administrator wasn't confirmed! ");
        }
    }

}
