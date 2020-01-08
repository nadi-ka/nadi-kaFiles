package by.trjava.library.dal.userDao;

import by.trjava.library.dal.exeptionDao.DAOException;
import by.trjava.library.bean.user.User;
import java.util.List;

public interface UserDao {

    void addUsers(List<User> users) throws DAOException;

    void addUser(User user) throws DAOException;

    List<User> getAllUsers() throws DAOException;

    User getUserById(long id) throws DAOException, NullPointerException;

    List<User> getUserBySurname(String surname) throws DAOException, NullPointerException;

    boolean deleteUserById (long id) throws DAOException;

    boolean deleteUser(User user) throws DAOException;

}
