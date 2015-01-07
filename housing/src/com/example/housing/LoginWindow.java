package com.example.housing;

import com.example.housing.data.model.User;
import com.example.housing.data.provider.UserProvider;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;

/**
 * The Class LoginWindow.
 * @author MWI Wohungsbörse 2014
 * @version 1.0
 * @see com.example.housing.Registrierung
 */
@SuppressWarnings("serial")
public class LoginWindow extends Window{
	
	/** The title. */
	public static Label title;
	
	/** The email_1. */
	public static TextField email_1;
	
	/** The password_1. */
	public static PasswordField password_1;
	
	/** The login button. */
	public static Button loginButton;
	
	public static Button link;
	
	/**
	 * Instantiates a new login window.
	 */
	public LoginWindow() {
		super("Bitte loggen Sie sich ein...");
		initialisieren();
	}
	
	/**
	 * Initialisieren.
	 * @see com.vaadin.ui.Window
	 */
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
			Button loginButton = new Button();
			loginButton.setCaption("Login");
			loginButton.setImmediate(true);
			loginButton.setDescription("Login abschließen");
			loginButton.setWidth("-1px");
			loginButton.setHeight("-1px");
			content.addComponent(loginButton);
			loginButton.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					try{
						// Find out the base bath for the servlet
						String servletPath = VaadinServlet.getCurrent()
						    .getServletContext().getContextPath() + VaadinServletService 
						    .getCurrentServletRequest().getServletPath();
						
						System.out.println(servletPath);
						//1. User aus der Datenbank auslesen
						User u = new UserProvider().findByEmail(email_1.getValue());

						//2. Prüfen ob das Konto aktiviert ist
						if(!u.isActivated()){
							
							String[] msgs = Page.getCurrent().getUriFragment().split("/");//Request Parameter auslesen (wurde bei der Registrierung verschickt)
							
							if(msgs.length == 1){//es gibt keinen Parameter -> kein Login möglich
								Notification.show("Login fehlgeschlagen!","Ihr Konto ist nicht freigeschalten. Bitte folgen Sie dem Link in der E-Mail, die Sie erhalten haben.", Type.HUMANIZED_MESSAGE);
							}else{
								if(email_1.getValue().equals(u.getEmail()) && email_1.getValue().equals(msgs[1])){//richtiger Parameter wurde übergeben
									//Aktivierung in DB speichern
									u.setActivated(true);
									new UserProvider().alterUser(u);
								}else{
									Notification.show("Login fehlgeschlagen!","Ihr Konto ist nicht freigeschalten. Bitte folgen Sie dem Link in der E-Mail, die Sie erhalten haben.", Type.HUMANIZED_MESSAGE);
								}
							}
						}
						
						//3. Prüfen ob Benutzer und Passwort stimmen (nur wenn das Konto aktiviert ist)
						if(u.isActivated()){
							if(email_1.getValue().equals(u.getEmail()) && password_1.getValue().equals(u.getPassword())){//prüfen ob Passwort und E-Mail stimmen
								Notification.show("Login erfolgreich.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
								VaadinSession.getCurrent().setAttribute(User.class, u);//User-Objekt in der Session speichern
								VaadinSession.getCurrent().setAttribute("login", true);//Login-Attribut auf true setzen (wird auf jeder Seite abgefragt, um zu prüfen welche Navigationsleiste angezeigt werden soll)
							
								Page.getCurrent().reload();//Seite erneut Laden (damit die Navigationsleiste verändert wird)
							}else{
								//Fehlermeldung bei falschem Benutzername oder Passwort
								Notification.show("Login fehlgeschlagen!","Bitte überprüfen Sie Benutzername und/oder Passwort.", Type.HUMANIZED_MESSAGE);
							}
						}
	
					}catch(Exception e){
						//Fehlermeldung bei Datenbankproblemen
						Notification.show("Login fehlgeschlagen!","Bitte registrieren Sie sich zuerst.", Type.HUMANIZED_MESSAGE);
					}
				}
			});
			
			link = new Button();
			link.setStyleName("link");
			link.setCaption("Passwort vergessen?");
			link.setImmediate(false);
			link.setWidth("-1px");
			link.setHeight("-1px");
			link.setIcon(FontAwesome.EXTERNAL_LINK);
			
			
			
			link.addClickListener(new Button.ClickListener(){
			public void buttonClick(ClickEvent event) {
				ForgotPasswordWindow w = new ForgotPasswordWindow();
				UI.getCurrent().removeWindow(LoginWindow.this);
				UI.getCurrent().addWindow(w);
				
			}
			});
			
	
			content.addComponent(link);
			
			this.setContent(content);    
			
			
	}
	
	//diese Methode ist nur zum Testen !!!
	public User test(){
		User u = new User();
		u.setFirstname("Max");
		u.setLastname("Mustermann");
		u.setEmail("max.mustermann@test.de");
		u.setPassword("12345");
		u.setMobile("12345678");
		u.setDhMail(null);
		u.setActivated(true);
		u.setAccessLevel(0);
		return u;
	}

}
