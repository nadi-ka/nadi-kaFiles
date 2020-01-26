package by.trjava.library.service.userService.userServiceImpl;

import by.trjava.library.dal.daoFactory.DaoFactory;
import by.trjava.library.dal.daoFactory.daoFactoryImpl.DaoFactoryImpl;
import by.trjava.library.dal.exeptionDao.DAOException;
import by.trjava.library.dal.userDao.UserDao;
import by.trjava.library.bean.user.User;
import by.trjava.library.bean.user.UserRole;
import by.trjava.library.service.exceptionService.ServiceException;
import by.trjava.library.service.userService.UserService;
import by.trjava.library.service.validation.UserValidator;

import java.util.List;

public class UserServiceImpl implements UserService {

    private DaoFactory factory = DaoFactoryImpl.getInstance();
    private UserDao userDao = factory.getUserDao();

    public void register(String surname, String name, String login, String password)
            throws ServiceException {
        if (UserValidator.isCorrectSurname(surname) && UserValidator.isCorrectName(name) &&
                UserValidator.isCorrectLogin(login) && UserValidator.isCorrectPassword(password)) {
            List<User> users;
            User user;
            try {
                users = userDao.getAllUsers();
                if (UserValidator.isUniqueLogin(users, login)) {
                    user = new User(surname, name, login, password);
                    userDao.addUser(user);
                } else {
                    throw new ServiceException("Not unique login! Please, change the login and try " +
                            "to register again! " + login);
                }
            } catch (DAOException ex) {
                throw new ServiceException("The user wasn't registered! " + login, ex);
            }
        } else {
            throw new ServiceException("Incorrect data! Please, check the user's surname, name, login, password. " +
                    surname + " " + name + " " + login + " " + password);
        }
    }

    public User SignIn(String login, String password) throws ServiceException {
        List<User> list;
        try {
            list = userDao.getAllUsers();
            for (User one : list) {
                if (UserValidator.checkLoginAndPassword(one, login, password)) {
                    return one;
                }
            }
            throw new ServiceException("Incorrect login or password. Please, try to log in again!");
        } catch (DAOException ex) {
            throw new ServiceException("The user wasn't found! " + login, ex);
        }
    }

    public void setPermissionAdministrator(User userToSetPermission, User userWhoPerform) throws ServiceException {
        List<User> list;
        if (UserValidator.isAdministrator(userWhoPerform)) {
            try {
                list = userDao.getAllUsers();
                for (User one : list) {
                    if (one.getId().equals(userToSetPermission.getId())) {
                        one.setUserRole(UserRole.ADMINISTRATOR);
                        break;
                    }
                }
                userDao.addUsers(list);
            } catch (DAOException ex) {
                throw new ServiceException("The user's role wasn't changed! ", ex);
            }
        } else {
            throw new ServiceException("Permission of administrator wasn't confirmed!");
        }
    }

    public boolean deleteUser(User userToDelete, User userWhoPerform) throws ServiceException {
        if (UserValidator.isAdministrator(userWhoPerform) ||
                (userToDelete != null && (userToDelete.equals(userWhoPerform)))) {
            try {
                return userDao.deleteUser(userToDelete);
            } catch (DAOException ex) {
                throw new ServiceException("The user wasn't deleted! ", ex);
            }
        } else {
            throw new ServiceException("Permission for the operation wasn't confirmed!");
        }
    }

    public void updateUser(User userToUpdate, String newSurname, String newName,
                           String newLogin, String newPassword) throws ServiceException {
        if (UserValidator.isCorrectSurname(newSurname) && UserValidator.isCorrectName(newName) &&
                UserValidator.isCorrectLogin(newLogin) && UserValidator.isCorrectPassword(newPassword)) {
            List<User> users;
            try {
                users = userDao.getAllUsers();
                if (UserValidator.isUniqueLoginToUpdateUser(users, userToUpdate, newLogin)) {
                    for (User one : users) {
                        if (one.getId().equals(userToUpdate.getId())) {
                            userToUpdate.setSurname(newSurname);
                            userToUpdate.setName(newName);
                            userToUpdate.setLogin(newLogin);
                            userToUpdate.setPassword(newPassword);
                            int index = users.indexOf(one);
                            users.set(index, userToUpdate);
                            break;
                        }
                    }
                } else {
                    throw new ServiceException("This login is already exist! Please, change the login " +
                            "and try again. " + newLogin);
                }
                userDao.addUsers(users);
            } catch (DAOException ex) {
                throw new ServiceException("The user wasn't updated! ", ex);
            }
        } else {
            throw new ServiceException("Incorrect data! The user wasn't updated. " + newSurname + " " + newName +
                    " " + newLogin + " " + newPassword);
        }
    }

    public User findUserById(String id, User userAdministrator) throws ServiceException {
        User user;
        if (id != null && UserValidator.isAdministrator(userAdministrator)) {
            try {
                user = userDao.getUserById(id);
                if (user != null) {
                    return user;
                }
                throw new ServiceException("The user with such id wasn't found in base!");
            } catch (DAOException ex) {
                throw new ServiceException("The user wasn't found! " + id, ex);
            }
        } else {
            throw new ServiceException("You haven't permission of administrator or have entered " +
                    "incorrect data!");
        }
    }

    public List<User> findUserBySurname(String surname, User userAdministrator) throws ServiceException {
        List<User> list;
        if (surname != null && UserValidator.isAdministrator(userAdministrator)) {
            try {
                list = userDao.getUserBySurname(surname);
                if (!list.isEmpty()) {
                    return list;
                }
                throw new ServiceException("The user with such surname wasn't found in base!");
            } catch (DAOException ex) {
                throw new ServiceException("The user wasn't found! " + surname, ex);
            }
        } else {
            throw new ServiceException("You haven't permission of administrator or  have entered " +
                    "incorrect data!");
        }
    }
}
