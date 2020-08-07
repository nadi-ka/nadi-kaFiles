package by.epam.ts.controller.command.util.mailer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {

	private static final String MAIL_PROPERTIES = "mailer.properties";
	private static final String USERNAME = "npakhomchik@list.ru";
	private static final String PASSWORD = "tanya1mur";

	public void send(String subject, String text, String toEmail) throws MailerException {

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream input = classloader.getResourceAsStream(MAIL_PROPERTIES);
		Properties properties = new Properties();
		try {
			if (input != null) {
				properties.load(input);
			}

			Session session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(USERNAME, PASSWORD);
				}
			});
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(USERNAME));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(subject);
			message.setText(text);

			Transport.send(message);

		} catch (IOException e) {
			throw new MailerException("Message wasn't sent", e);
		} catch (MessagingException e) {
			throw new MailerException("Message wasn't sent", e);
		}
	}

}
