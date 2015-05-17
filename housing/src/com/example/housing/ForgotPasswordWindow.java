package com.example.housing;

import com.example.housing.data.model.User;
import com.example.housing.data.provider.UserProvider;
import com.example.housing.utility.GenerateCode;
import com.example.housing.utility.SendEMail;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;

public class ForgotPasswordWindow extends Window {
	private static final long serialVersionUID = 1L;

	public Label title;
	public TextField email_1;
	public PasswordField password_1;
	public PasswordField password_2;
	public Button save;
	public Button cancel;
	public Label text;

	public ForgotPasswordWindow() {
		super("Passwort vergessen.");
		initialisieren();
	}

	public void initialisieren() {
		this.center();
		this.setHeight("60%");
		this.setWidth("40%");

		final VerticalLayout content = new VerticalLayout();
		content.setMargin(true);

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

		HorizontalLayout layoutButtons = new HorizontalLayout();
		// speichern
		save = new Button();
		save.setStyleName("speichern");
		save.setCaption("neues Passwort anfordern");
		save.setIcon(FontAwesome.CHECK);
		save.setImmediate(true);
		save.setDescription("Neues Passwort speichern");
		save.setWidth("-1px");
		save.setHeight("-1px");

		save.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				try {
					// 1. User aus der Datenbank auslesen
					User u = new UserProvider().findByEmail(email_1.getValue());

					// 2. Validate: Passwörter sind gleich?
					if (validate()) {
						String password = generatePassword();
						u.setPassword(password);// neues Passwort setzen
						new UserProvider().alterUser(u);// User in der DB updaten

						sendEMail(password);// E-Mail an den Nutzer senden

						ForgotPasswordWindow.this.close();// Fenster schließen
						Notification notif = new Notification("Ihr Passwort wurde zurückgesetzt", "Bitte folgen Sie dem Link in der E-Mail, die Sie erhalten haben.",Type.HUMANIZED_MESSAGE);
						notif.setDelayMsec(300);
						notif.setStyleName("success");
						notif.show(Page.getCurrent());
					} else {
						Notification notif = new Notification("Zurücksetzen des Passworts fehlgeschlagen!","Bitte überprüfen Sie Ihre Eingaben.",Type.HUMANIZED_MESSAGE);
						notif.setDelayMsec(300);
						notif.setStyleName("failure");
						notif.show(Page.getCurrent());
					}
				} catch (Exception e) {
					// Fehlermeldung bei Datenbankproblemen
					Notification notif = new Notification("Zurücksetzen des Passworts fehlgeschlagen!","Es gibt keinen Nutzer mit dieser E-Mail-Adresse.",Type.HUMANIZED_MESSAGE);
					notif.setDelayMsec(300);
					notif.setStyleName("failure");
					notif.show(Page.getCurrent());
				}
			}
		});

		// abbrechen
		cancel = new Button();
		cancel.setStyleName("speichern");
		cancel.setCaption("abbrechen");
		cancel.setIcon(FontAwesome.MAIL_REPLY);
		cancel.setImmediate(true);
		cancel.setDescription("Diese Aktion abbrechen");
		cancel.setWidth("-1px");
		cancel.setHeight("-1px");

		cancel.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				ForgotPasswordWindow.this.close();// Fenster schließen
			}
		});

		layoutButtons.addComponent(cancel);
		layoutButtons.addComponent(save);
		content.addComponent(layoutButtons);

		text = new Label("Wenn Sie Ihr Passwort vergessen haben, können Sie hier Ihr Passwort zurücksetzten und ein neues anfordern. Um Sich erneut einloggen zu können folgen Sie bitte dem Link in der E-Mail.");
		content.addComponent(text);

		this.setContent(content);
	}

	public boolean validate() {
		boolean erfolgreich = true;// wird auf false gesetzt, falls ein Wert nicht richtig ist

		// sind alle Pflichtfelder gefüllt?
		try {
			email_1.validate();
		} catch (InvalidValueException e) {
			erfolgreich = false;
		}

		return erfolgreich;
	}

	// passwort generieren:
	public static String generatePassword() {
		StringBuilder password = new StringBuilder();

		for (int i = 0; i < 5; i++) {
			switch (rand(0, 2)) {
			case 0: // Zahlen
				password.append(rand(0, 9));
				break;
			case 1: // Großbuchstaben
				password.append((char) rand(65, 90));
				break;
			case 2: // Kleinbuchstaben
				password.append((char) rand(97, 122));
				break;
			case 3: // Kleinbuchstaben
				password.append((char) rand(97, 122));
				break;
			case 4: // Großbuchstaben
				password.append((char) rand(65, 90));
				break;
			}
		}

		return password.toString();
	}

	private static int rand(int min, int max) {
		return (int) (Math.random() * (max - min + 1)) + min;
	}

	public void sendEMail(String password) {
		String path = UI.getCurrent().getPage().getLocation().getHost() + ":"
				+ UI.getCurrent().getPage().getLocation().getPort()
				+ UI.getCurrent().getPage().getLocation().getPath()
				+ "#!Startseite/";
		String code = GenerateCode.generateCode(email_1.getValue());
		// Text der E-Mail mit Style-Informationen
		String body = "<meta charset='utf-8'/><img src='http://193.196.7.216:8080/housing/APP/connector/0/12/source/dh.PNG'/><br/><br/><span style='color: #000000' 'font-family: Arial, sans-serif''font-size: 16pt' >Sehr geehrte Nutzerin, sehr geehrter Nutzer,"
				+ "<br/><br/>Ihr Passwort wurde zurückgesetzt. Ihr neues Passwort lautet:"
				+ password
				+ ". Bitte folgen sie dem Link unten, dann können Sie sich wieder wie gewohnt einloggen. Dadurch wird sichergestellt, dass keine Unbefungten Ihre E-Mail-Adresse und Ihr Benutzerkonto verwenden können. <br/><br/> Unter \"Persönlichen Einstellungen\" können Sie ihr Passwort ändern.</span>"
				+ "<br/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 20pt' >"
				+ "<a href='http://"
				+ path
				+ code
				+ "'>weiter zum Login</a>"
				+ "</span><br/><br/>Mit freundlichen Grüßen<br/>Ihr DHBW Wohungsbörsen-Team<p/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 8pt' >Anschrift:<br/>DHBW Karlsruhe<br/>Baden-Wuerttemberg Cooperative State University Karlsruhe<br />Erzbergerstraße 121 . 76133 Karlsruhe <br />Postfach 10 01 36 . 76231 Karlsruhe   <br />Telefon +49.721.9735-5 <br />Telefax +49.721.9735-600 <br />E-Mail: dreischer@dhbw-karlsruhe.de<br /><br/><br/>Ansprechpartner:<br/> <br />Dr. Anita Dreischer<br /><br/><b>Copyright DHBW Karlsruhe. Alle Rechte vorbehalten.</b></span>";
		// E-Mail senden
		SendEMail.send(email_1.getValue(), "wohnungsboerse_dh@web.de",
				"Passwort vergessen", body);
	}

}
