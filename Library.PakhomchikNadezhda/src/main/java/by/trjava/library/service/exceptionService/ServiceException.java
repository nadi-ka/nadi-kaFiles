package by.trjava.library.service.exceptionService;

import by.trjava.library.dal.exeptionDao.DAOException;

public class ServiceException extends DAOException {

    public ServiceException() {}

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

}
