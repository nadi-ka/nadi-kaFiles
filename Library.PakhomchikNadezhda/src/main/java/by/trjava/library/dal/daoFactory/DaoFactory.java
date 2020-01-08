package by.trjava.library.dal.daoFactory;

import by.trjava.library.dal.bookDao.BookDao;
import by.trjava.library.dal.userDao.UserDao;

public interface DaoFactory {

    UserDao getUserDao();

    BookDao getBookDao();
}
