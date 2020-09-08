package by.epam.ts.dal.pool;

public class ConnectionPoolException extends Exception {

	public static final long serialVersionUID = 1L;

	public ConnectionPoolException() {
	}

	public ConnectionPoolException(String message) {
		super(message);
	}

	public ConnectionPoolException(Throwable cause) {
		super(cause);
	}

	public ConnectionPoolException(String message, Throwable cause) {
		super(message, cause);
	}

}
