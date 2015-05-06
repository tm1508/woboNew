package com.example.housing.utility;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import com.sun.mail.smtp.SMTPMessage;

/**
 * The Class SendEMail.
 * 
 * @author MWI Wohungsbörse 2014
 * @version 1.0
 * @see javax.mail.*
 */
public class SendEMail {

	/** Properties. */
	static Properties properties = new Properties();
	
	public static void main(String args[]){
		send("julia.agricola@web.de", "test@web.de", "test", "<span>äöüß&%$§</span>");
	}
	
	/**
	 * Sends an E-Mail.
	 *
	 * @param to String, who recives the e-mail?
	 * @param from String, who sends the e-mail?
	 * @param subject String, subject of the e-mail
	 * @param text String, text of the e-mail
	 * @see javax.mail.*
	 * @see com.sun.mail.smtp.SMTPMessage
	 */
	public static void send(String to, String from, String subject, String text){
		
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
		
		// neue Message erzeugen
		SMTPMessage message = (SMTPMessage) new SMTPMessage(session);
		try {
			message.setEnvelopeFrom("wohnungsboerse_dh@web.de");// tatsächlicher Absender (E-Mail wird von uns an uns selbst geschickt, unter dem Namen bzw. der E-Mail-Adresse des Nutzers)
			message.setFrom(new InternetAddress(from));// E-Mail-Adresse des Benutzers
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));// E-Mail-Adresse des Empfängers
			message.setSubject(subject);// Betreff
			message.setContent(text, "text/html");// Text der E-Mail (mit html Code für den Style)
			//message.setAllow8bitMIME(true);
			// E-Mail senden
			Transport.send(message);

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	/**
	 * Send E-Mail Alias.
	 *
	 * @param to the to
	 * @param from the from
	 * @param cc the cc
	 * @param subject the subject
	 * @param text the text
	 */
	public static void sendEmailAlias(String to, String from, String alias, String subject, String text){
		
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
		
		// neue Message erzeugen
		SMTPMessage message = (SMTPMessage) new SMTPMessage(session);
		try {
			message.setEnvelopeFrom(from);// tatsächlicher Absender (E-Mail wird von uns an uns selbst geschickt, unter dem Namen bzw. der E-Mail-Adresse des Nutzers)
			message.setFrom(new InternetAddress(alias));// E-Mail-Adresse des Benutzers
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));// E-Mail-Adresse des Empfängers
			message.setSubject(subject);// Betreff
			message.setContent(text, "text/html");// Text der E-Mail (mit html Code für den Style)
			
			// E-Mail senden
			Transport.send(message);

		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
}
