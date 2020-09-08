package by.epam.ts.controller.command.util.mailer;

public class MailerException extends Exception {

	private static final long serialVersionUID = 1L;

	public MailerException() {
	}

	public MailerException(String message) {
		super(message);
	}

	public MailerException(Throwable cause) {
		super(cause);
	}

	public MailerException(String message, Throwable cause) {
		super(message, cause);
	}

}
