package com.example.housing.utility;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import com.sun.mail.smtp.SMTPMessage;

public class SendEMail {
	public static void send(String to, String from, String subject, String text){
		Properties properties = new Properties();
		Session session = initialize(properties);
		// neue Message erzeugen
		SMTPMessage message = (SMTPMessage) new SMTPMessage(session);
		try {
			message.setEnvelopeFrom("wohnungsboerse_dh@web.de");// tatsächlicher Absender (E-Mail wird von uns an uns selbst geschickt, unter dem Namen bzw. der E-Mail-Adresse des Nutzers)
			message.setFrom(new InternetAddress(from));// E-Mail-Adresse des Benutzers
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));// E-Mail-Adresse des Empfängers
			message.setSubject(subject);// Betreff
			message.setContent(text, "text/html; charset=UTF-8");// Text der E-Mail (mit html Code für den Style)
			// E-Mail senden
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendEmailAlias(String to, String from, String alias, String subject, String text){
		Properties properties = new Properties();
		Session session = initialize(properties);
		// neue Message erzeugen
		SMTPMessage message = (SMTPMessage) new SMTPMessage(session);
		try {
			message.setEnvelopeFrom(from);// tatsächlicher Absender (E-Mail wird von uns an uns selbst geschickt, unter dem Namen bzw. der E-Mail-Adresse des Nutzers)
			message.setFrom(new InternetAddress(alias));// E-Mail-Adresse des Benutzers
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));// E-Mail-Adresse des Empfängers
			message.setSubject(subject);// Betreff
			message.setContent(text, "text/html; charset=UTF-8");// Text der E-Mail (mit html Code für den Style)
			// E-Mail senden
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	private static Session initialize(Properties properties) {
		// Properties setzen
		properties.put("mail.smtp.host", "smtp.web.de");// SMTP-Server von web.de
		properties.put("mail.smtp.auth", "true");// Authentifikation erforderlich
		properties.put("mail.smtp.port", "587");// Port des SMTP-Servers
		properties.put("mail.smtp.starttls.enable", "true");// SSLv3/TLSv1
		// Authentifizierung
		Authenticator auth = new javax.mail.Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("wohnungsboerse_dh@web.de", "wohnungsboerse");// Unsere E-Mail-Adresse und unser Passwort
			}
		};
		// Session erstellen mit den oben gesetzten Properties und der Authentifikation
		Session session = Session.getDefaultInstance(properties, auth);
		return session;
	}
}
