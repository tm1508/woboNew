package com.example.housing;

import com.example.housing.data.model.User;
import com.example.housing.data.provider.UserProvider;
import com.example.housing.utility.GenerateCode;
import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;

@Theme("housing")
public class LoginWindow extends Window{
	private static final long serialVersionUID = 1L;

	public Label title;
	public TextField email_1;
	public PasswordField password_1;
	public Button loginButton;
	public Button link;

	public LoginWindow() {
		super("Bitte loggen Sie sich ein...");
		initialisieren();
	}
	
	public void initialisieren(){
		this.center();
		this.setHeight("50%");
	    this.setWidth("30%");
	    final VerticalLayout content = new VerticalLayout();
	    content.setMargin(true);
	    
			// title
			title = new Label();
			title.setImmediate(false);
			title.setWidth("-1px");
			title.setHeight("-1px");
			title.setValue("Login");
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
			password_1.setCaption("Passwort");
			password_1.setImmediate(false);
			password_1.setDescription("Bitte Passwort eingeben");
			password_1.setWidth("220px");
			password_1.setHeight("-1px");
			password_1.setRequired(true);
			password_1.setIcon(FontAwesome.KEY);
			content.addComponent(password_1);
				
			// loginButton
			loginButton = new Button();
			loginButton.setIcon(FontAwesome.UNLOCK_ALT);
			loginButton.setStyleName("log");
			loginButton.setCaption("Login");
			loginButton.setImmediate(true);
			loginButton.setDescription("Login abschlie�en");
			loginButton.setWidth("-1px");
			loginButton.setHeight("-1px");
			content.addComponent(loginButton);
			loginButton.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				public void buttonClick(ClickEvent event) {
					try{
						//1. User aus der Datenbank auslesen
						User u = new UserProvider().findByEmail(email_1.getValue());
						String code = GenerateCode.generateCode(email_1.getValue());
						
						//2. Pr�fen ob das Konto aktiviert ist
						if(!u.isActivated()){
							String param = (String) VaadinSession.getCurrent().getAttribute("activated");//Parameter aus der Session auslesen
							if(code.equals(param)){//richtiger Parameter wurde �bergeben
									//Aktivierung in DB speichern
									u.setActivated(true);
									new UserProvider().alterUser(u);
								}else{
									Notification notif = new Notification("Login fehlgeschlagen!","Ihr Konto ist nicht freigeschalten. Bitte folgen Sie dem Link in der E-Mail, die Sie erhalten haben.", Type.HUMANIZED_MESSAGE);
									notif.setStyleName("failure");
									notif.setDelayMsec(300);
									notif.show(Page.getCurrent());
								}
							}
						
						//3. Pr�fen ob Benutzer und Passwort stimmen (nur wenn das Konto aktiviert ist)
						if(u.isActivated()){
							if(email_1.getValue().equals(u.getEmail()) && password_1.getValue().equals(u.getPassword())){//pr�fen ob Passwort und E-Mail stimmen
							
								Notification notif = new Notification("Login erfolgreich.","Herzlich Willkommen!", Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
								notif.setDelayMsec(300);
								notif.setStyleName("success");
								notif.show(Page.getCurrent());
								
								try {
									VaadinSession.getCurrent().getLockInstance().lock();
									VaadinSession.getCurrent().getSession().setAttribute("user", u);//User-Objekt in der Session speichern
									VaadinSession.getCurrent().getSession().setAttribute("login", true);//Login-Attribut auf true setzen (wird auf jeder Seite abgefragt, um zu pr�fen welche Navigationsleiste angezeigt werden soll)
								} finally {
									VaadinSession.getCurrent().getLockInstance().unlock();
								}
								
								String name = "Startseite";
								getUI().getNavigator().addView(name, new Startseite());
								getUI().getNavigator().navigateTo(name);
								LoginWindow.this.close();
								//Page.getCurrent().reload();//Seite erneut Laden (damit die Navigationsleiste ver�ndert wird)
							}else{
								//Fehlermeldung bei falschem Benutzername oder Passwort
								Notification notif = new Notification("Login fehlgeschlagen!","Bitte �berpr�fen Sie Benutzername und/oder Passwort.", Type.HUMANIZED_MESSAGE);
								notif.setDelayMsec(300);
								notif.setStyleName("failure");
								notif.show(Page.getCurrent());
							}
						}
	
					}catch(Exception e){
						
						try {
							VaadinSession.getCurrent().getLockInstance().lock();
							VaadinSession.getCurrent().getSession().setAttribute("login", false);
							VaadinSession.getCurrent().getSession().setAttribute("user", null);
						} finally {
							VaadinSession.getCurrent().getLockInstance().unlock();
						}
						
						//Fehlermeldung bei Datenbankproblemen
						Notification notif = new Notification("Login fehlgeschlagen!","Bitte registrieren Sie sich zuerst.", Type.HUMANIZED_MESSAGE);
						notif.setDelayMsec(300);
						notif.setStyleName("failure");
						notif.show(Page.getCurrent());
					}
				}
			});
			
			//Link zum Fenster "Passwort vergessen"
			link = new Button();
			link.setStyleName("link");
			link.setCaption("Passwort vergessen?");
			link.setImmediate(false);
			link.setWidth("-1px");
			link.setHeight("-1px");
			link.setIcon(FontAwesome.EXTERNAL_LINK);
			link.addClickListener(new Button.ClickListener(){
				private static final long serialVersionUID = 1L;
				public void buttonClick(ClickEvent event) {
					ForgotPasswordWindow w = new ForgotPasswordWindow();//Intantiiert ein neues Fenster
					UI.getCurrent().removeWindow(LoginWindow.this);//schlie�t das Loginfenster
					UI.getCurrent().addWindow(w);//�ffnet das neue Fenster "Passwort vergessen"
				}
			});
			
			content.addComponent(link);
			this.setContent(content);    
	}	
}
