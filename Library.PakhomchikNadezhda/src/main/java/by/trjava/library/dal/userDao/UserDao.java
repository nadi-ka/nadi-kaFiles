package by.trjava.library.dal.userDao;

import by.trjava.library.dal.exeptionDao.DAOException;
import by.trjava.library.beans.user.User;
import java.util.List;

public interface UserDao {

    void addUsers(List<User> users) throws DAOException;

    void addUser(User user) throws DAOException;

    List<User> getAllUsers() throws DAOException;

    User getUserById(String id) throws DAOException;

    List<User> getUserBySurname(String surname) throws DAOException;

    boolean deleteUser(User user) throws DAOException;

}
