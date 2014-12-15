package com.example.housing;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;


public class LoginWindow extends Window{
	
	public static Label title;
	public static TextField email_1;
	public static PasswordField password_1;
	public static Button loginButton;
	
	
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
			Button loginButton = new Button();
			loginButton.setCaption("Login");
			loginButton.setImmediate(true);
			loginButton.setDescription("Login abschließen");
			loginButton.setWidth("-1px");
			loginButton.setHeight("-1px");
			content.addComponent(loginButton);
			loginButton.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					if(email_1.getValue().equals("max.mustermann@test.de")){
						Notification.show("Login erfolgreich.",Type.HUMANIZED_MESSAGE);
						VaadinSession.getCurrent().setAttribute("login", true);
						//System.out.println(VaadinSession.getCurrent().getAttribute("login").toString());
						Page.getCurrent().reload();
					}else{
						Notification.show("Login fehlgeschlagen!","Bitte überprüfen Sie Benutzername und Passwort.", Type.HUMANIZED_MESSAGE);
					}

				}
			});
				
			this.setContent(content);
	        
	       
	}
	
	
	
}
