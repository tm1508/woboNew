package com.example.housing;

import com.example.housing.data.model.User;
import com.example.housing.data.provider.UserProvider;
import com.example.housing.utility.SendEMail;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;

/**
 * The Class ForgotPasswordWindow.
 * @author MWI Wohungsbörse 2014
 * @version 1.0
 * @see com.example.housing.Registrierung
 */
@SuppressWarnings("serial")
public class ForgotPasswordWindow extends Window{
	
	/** The title. */
	public static Label title;
	
	/** The email_1. */
	public static TextField email_1;
	
	/** The password_1. */
	public static PasswordField password_1;
	
	/** The password_1. */
	public static PasswordField password_2;
	
	/** The save button. */
	public static Button save;
	
	/** The cancel button. */
	public static Button cancel;
	
	/** The text. */
	public static Label text;
	
	/**
	 * Instantiates a new ForgotPasswordWindow.
	 */
	public ForgotPasswordWindow() {
		super("Passwort vergessen.");
		initialisieren();
	}
	
	/**
	 * Initialisieren.
	 * @see com.vaadin.ui.Window
	 */
	public void initialisieren(){
		this.center();
		this.setHeight("60%");
	    this.setWidth("40%");
	    
	    final VerticalLayout content = new VerticalLayout();
	    content.setMargin(true);
			// title
			title = new Label();
			title.setImmediate(false);
			title.setWidth("-1px");
			title.setHeight("-1px");
			title.setValue("Passwort vergessen");
			content.addComponent(title);
				
			// email_1
			email_1 = new TextField();
			email_1.setCaption("E-Mail");
			email_1.setDescription("Bitte E-Mail-Adresse angeben");
			email_1.setWidth("221px");
			email_1.setHeight("-1px");
			email_1.setRequired(true);
			email_1.setIcon(FontAwesome.ENVELOPE);
			email_1.setInputPrompt("max.mustermann@test.de");
			content.addComponent(email_1);
				
			// password_1
			password_1 = new PasswordField();
			password_1.setCaption("Neues Passwort");
			password_1.setImmediate(false);
			password_1.setDescription("Bitte Passwort eingeben");
			password_1.setWidth("220px");
			password_1.setHeight("-1px");
			password_1.setRequired(true);
			password_1.setIcon(FontAwesome.KEY);
			content.addComponent(password_1);
			
			// password_2
			password_2 = new PasswordField();
			password_2.setCaption("Neues Passwort (Kontrolle)");
			password_2.setImmediate(false);
			password_2.setDescription("Bitte Passwort eingeben");
			password_2.setWidth("220px");
			password_2.setHeight("-1px");
			password_2.setRequired(true);
			password_2.setIcon(FontAwesome.KEY);
			content.addComponent(password_2);
			
			HorizontalLayout hl = new HorizontalLayout();
			// speichern
			save = new Button();
			save.setCaption("speichern");
			save.setIcon(FontAwesome.CHECK);
			save.setImmediate(true);
			save.setDescription("Neues Passwort speichern");
			save.setWidth("-1px");
			save.setHeight("-1px");
			
			save.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					try{
						
						//1. User aus der Datenbank auslesen
						User u = new UserProvider().findByEmail(email_1.getValue());
						
						//2. Validate: Passwörter sind gleich?
						if(validate()){
							u.setPassword(password_1.getValue());//neues Passwort setzen
							u.setActivated(false);//Nutzer deaktivieren
							new UserProvider().alterUser(u);//User in der DB updaten
			
							//E-Mail an den Nutzer senden
							sendEMail();
							
							ForgotPasswordWindow.this.close();//Fenster schließen
							Notification notif = new Notification("Ihr Passwort wurde geändert","Bitte folgen Sie dem Link in der E-Mail, die Sie erhalten haben.", Type.HUMANIZED_MESSAGE);
							notif.setDelayMsec(300);
							notif.setIcon(FontAwesome.CHECK_SQUARE_O);
							notif.show(Page.getCurrent());
						}else{
							Notification notif = new Notification("Änderung des Passworts fehlgeschlagen!","Bitte überprüfen Sie Ihre Eingaben.", Type.HUMANIZED_MESSAGE);
							notif.setDelayMsec(300);
							notif.setIcon(FontAwesome.EXCLAMATION_TRIANGLE);
							notif.show(Page.getCurrent());
						}
											
					}catch(Exception e){
						//Fehlermeldung bei Datenbankproblemen
						Notification notif = new Notification("Änderung des Passworts fehlgeschlagen!","Es gibt keinen Nutzer mit dieser E-Mail-Adresse.", Type.HUMANIZED_MESSAGE);
						notif.setDelayMsec(300);
						notif.setIcon(FontAwesome.EXCLAMATION_TRIANGLE);
						notif.show(Page.getCurrent());
					}
				}
			});
			
			// abbrechen
			cancel = new Button();
			cancel.setCaption("abbrechen");
			cancel.setIcon(FontAwesome.MAIL_REPLY);
			cancel.setImmediate(true);
			cancel.setDescription("Diese Aktion abbrechen");
			cancel.setWidth("-1px");
			cancel.setHeight("-1px");
			
			cancel.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					ForgotPasswordWindow.this.close();//Fenster schließen
				}
			});
			
			hl.addComponent(cancel);
			hl.addComponent(save);
			content.addComponent(hl);
			
			text = new Label("Wenn Sie Ihr neues Passwort speichern erhalten Sie eine E-Mail an die angegebene E-Mail-Adresse. Um Sich erneut einloggen zu können folgen Sie bitte dem Link in der E-Mail.");
			content.addComponent(text);
			
			this.setContent(content);    
	}
	
	
	/**
	 * Validates the user input
	 * Validates if the text fields "email_1", "password_1" and "password_2" are filled. It also validates weather the two password are equal.
	 * @return boolean
	 * @see com.vaadin.data.validator.EmailValidator
	 * @see com.vaadin.data.validator.StringLengthValidator;
	 * @see com.example.housing.utility.DHStudValidator;
	 */
	public boolean validate(){
		boolean erfolgreich=true;//wird auf false gesetzt, falls ein Wert nicht richtig ist
		
		//sind alle Pflichtfelder gefüllt?
		try {
			email_1.validate();
		} catch (InvalidValueException e) {
			erfolgreich=false;
		}
		
		try {
			password_1.validate();
		} catch (InvalidValueException e) {
			erfolgreich=false;
		}
		
		try {
			password_2.validate();
		} catch (InvalidValueException e) {
			erfolgreich=false;
		}
		
		//sind die beiden Passwörter gleich?
		if(!password_1.getValue().equals(password_2.getValue())){
			System.out.println(password_1.getValue());
			System.out.println(password_2.getValue());
			password_1.setComponentError(new UserError("Die beiden Passwörter stimmen nicht überein."));
			password_2.setComponentError(new UserError("Die beiden Passwörter stimmen nicht überein."));
			erfolgreich=false;
		}
			
		return erfolgreich;
	}
	
	
	/**
	 * Sends an EMail to the User.
	 * @see com.example.housing.utility.SendEMail
	 */
	public void sendEMail(){
		// Text der E-Mail mit Style-Informationen
		String body = "<span style='color: #000000' 'font-family: Arial, sans-serif''font-size: 16pt' >Sehr geehrte Nutzerin, sehr geehrter Nutzer,"
					 +"<br/><br/>Sie haben Ihr Passwort vergessen und ein neues Passwort angegeben. Ihr Konto muss erneut freigeschalten werden. Bitte folgen sie dem Link unten, dann können Sie sich wieder wie gewohnt einloggen.Dadurch wird sichergestellt, dass keine Unbefungten Ihre E-Mail-Adresse und Ihr Benutzerkonto verwenden können.</span>"
					 +"<br/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 20pt' >"
					 + "<a href='http://localhost:8080/housing/servlet/com.example.housing.HousingUI$Servlet#!Startseite/"+email_1.getValue()+"'>weiter zum Login</a>"
						+ "</span><br/><br/>Mit freundlichen Grüßen<br/>Ihr DHBW Wohungsbörsen-Team<p/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 8pt' >Anschrift:<br/>DHBW Karlsruhe<br/>Baden-Wuerttemberg Cooperative State University Karlsruhe<br />Erzbergerstraße 121 . 76133 Karlsruhe <br />Postfach 10 01 36 . 76231 Karlsruhe   <br />Telefon +49.721.9735-5 <br />Telefax +49.721.9735-600 <br />E-Mail: dreischer@dhbw-karlsruhe.de<br /><br/><br/>Ansprechpartner:<br/> <br />Dr. Anita Dreischer<br /><br/><b>Copyright DHBW Karlsruhe. Alle Rechte vorbehalten.</b></span>";
		//E-Mail senden
		SendEMail.send(email_1.getValue(), "wohnungsboerse_dh@web.de", "Passwort vergessen", body);
	}


}
