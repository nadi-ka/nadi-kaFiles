package by.trjava.library.dal.daoFactory.daoFactoryImpl;

import by.trjava.library.dal.bookDao.BookDao;
import by.trjava.library.dal.bookDao.bookDaoJson.BookDaoJson;
import by.trjava.library.dal.daoFactory.DaoFactory;
import by.trjava.library.dal.userDao.UserDao;
import by.trjava.library.dal.userDao.userDaoJson.UserDaoJson;
import by.trjava.library.settings.LibrarySettings;
import by.trjava.library.settings.settingsImpl.LibrarySettingsImpl;

public final class DaoFactoryImpl implements DaoFactory {

    private final static DaoFactoryImpl instance = new DaoFactoryImpl();
    private LibrarySettings settings = new LibrarySettingsImpl();
    private String userSettings = settings.getUserSettings();
    private String bookSettings = settings.getBookSettings();

    private final UserDao userDao = new UserDaoJson(userSettings);
    private final BookDao bookDao = new BookDaoJson(bookSettings);

    private DaoFactoryImpl() {
    }

    public static DaoFactory getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public BookDao getBookDao() {
        return bookDao;
    }
}
