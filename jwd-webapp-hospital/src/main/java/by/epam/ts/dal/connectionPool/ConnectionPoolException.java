package src.main.java.by.epam.ts.dal.connectionPool;

public class ConnectionPoolException extends Exception{
	public static final long serialVersionUID = 1L;

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

}
