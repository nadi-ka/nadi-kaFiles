package by.epam.ts.dal;

import java.rmi.server.ExportException;


public class DaoException extends Exception{
	
	public DaoException() {}
	
	public DaoException(String message) {
		super(message);
	}
	
	public DaoException(Throwable cause) {
		super(cause);
	}
	
	public DaoException(String message, Throwable cause) {
		super(message,cause);
	}

}
