package by.epam.ts.service.exception;

public class ServiceValidationException extends ServiceException{
private static final long serialVersionUID = 1L;
	
	public ServiceValidationException() {}
	
	public ServiceValidationException(String message) {
		super(message);
	}
	
	public ServiceValidationException(Throwable cause) {
		super(cause);
	}
	
	public ServiceValidationException(String message, Throwable cause) {
		super(message, cause);
	}

}
