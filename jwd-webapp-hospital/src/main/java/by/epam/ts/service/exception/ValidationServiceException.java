package by.epam.ts.service.exception;

public class ValidationServiceException extends ServiceException{
private static final long serialVersionUID = 1L;
	
	public ValidationServiceException() {
	}
	
	public ValidationServiceException(String message) {
		super(message);
	}
	
	public ValidationServiceException(Throwable cause) {
		super(cause);
	}
	
	public ValidationServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
