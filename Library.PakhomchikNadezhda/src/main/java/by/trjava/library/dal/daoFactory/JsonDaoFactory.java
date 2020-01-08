package by.trjava.library.dal.daoFactory;

import by.trjava.library.dal.bookDao.BookDao;
import by.trjava.library.dal.bookDao.BookDaoJson;
import by.trjava.library.dal.userDao.UserDao;
import by.trjava.library.dal.userDao.UserDaoJson;

public final class JsonDaoFactory implements DaoFactory {

    private final static JsonDaoFactory instance = new JsonDaoFactory();
    private UserDaoJson userDaoJson;
    private BookDaoJson bookDaoJson;

    private JsonDaoFactory() {
    }

    public static DaoFactory getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return new UserDaoJson();
    }

    public BookDao getBookDao() {
        return new BookDaoJson();
    }
}
