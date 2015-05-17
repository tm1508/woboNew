package com.example.housing;

import com.example.housing.data.model.User;
import com.example.housing.data.provider.UserProvider;
import com.example.housing.utility.DHStudValidator;
import com.example.housing.utility.GenerateCode;
import com.example.housing.utility.SendEMail;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class Registrierung extends CustomHorizontalLayout implements View{
	private static final long serialVersionUID = 1L;

	private VerticalLayout content;//Layout fuer den Inhalt
	
	//Felder des Registrierungsformulars
	private Label title;
	private TextField lastname;
	private TextField prename;
	private TextField email_1;
	private TextField email_2;
	private PasswordField password_1;
	private PasswordField password_2;
	private TextField handy;
	private CheckBox dhstud;
	private TextField moodlename;
	private PasswordField passwordmoodle;
	private CheckBox agbs;
	private Button haftungsausschlussLink;
	private Button registrierungAbschliessen;

	@Override
	public void enter(ViewChangeEvent event) {
	}
	
	public Registrierung(){
		content = super.initCustomHorizontalLayout();
		setContent();
	}
	
	public void setContent(){
		// title
		title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Registrierung");
		title.addStyleName("title");
		content.addComponent(title);
				
		// prename
		prename = new TextField();
		prename.setCaption("Vorname");
		prename.setRequiredError("Das Feld darf nicht leer sein.");
		prename.setDescription("Bitte Vorname angeben");
		prename.setWidth("220px");
		prename.setHeight("-1px");
		prename.setIcon(FontAwesome.USER);
		prename.setInputPrompt("Max");
		prename.addStyleName("textfield");
		prename.setRequired(true);
		prename.setRequiredError("Das Feld darf nicht leer sein.");
		prename.setImmediate(false);
		content.addComponent(prename);
		
		// lastname
		lastname = new TextField();
		lastname.setCaption("Nachname");
		lastname.setImmediate(false);
		lastname.setDescription("Bitte Nachname angeben");
		lastname.setWidth("221px");
		lastname.setHeight("-1px");
		lastname.setRequired(true);
		lastname.setRequiredError("Das Feld darf nicht leer sein.");
		lastname.setIcon(FontAwesome.USER);
		lastname.setInputPrompt("Mustermann");
		content.addComponent(lastname);
		
		//E-Mail mit eigenem Layout
		HorizontalLayout emailLayout = new HorizontalLayout();
			// email_1
			email_1 = new TextField();
			email_1.setCaption("E-Mail");
			email_1.setImmediate(false);
			email_1.setDescription("Bitte E-Mail-Adresse angeben");
			email_1.setWidth("221px");
			email_1.setHeight("-1px");
			email_1.setRequired(true);
			email_1.setRequiredError("Das Feld darf nicht leer sein.");
			email_1.setIcon(FontAwesome.ENVELOPE);
			email_1.setInputPrompt("max.mustermann@test.de");
			email_1.addValidator(new EmailValidator("Das ist keine gültige E-Mail Adresse."));
			emailLayout.addComponent(email_1);
					
			// email_2
			email_2 = new TextField();
			email_2.setCaption("E-Mail (Kontrolle)");
			email_2.setImmediate(false);
			email_2.setDescription("Bitte E-Mail zur Kontrolle erneut angeben");
			email_2.setWidth("221px");
			email_2.setHeight("-1px");
			email_2.setRequired(true);
			email_2.setRequiredError("Das Feld darf nicht leer sein.");
			email_2.setIcon(FontAwesome.ENVELOPE);
			email_2.setInputPrompt("max.mustermann@test.de");
			email_2.addValidator(new EmailValidator("Das ist keine gültige E-Mail Adresse."));
			emailLayout.addComponent(email_2);
		
		content.addComponent(emailLayout);
		
		//Passwort mit eigenem Layout
		HorizontalLayout passwordLayout = new HorizontalLayout();
			// password_1
			password_1 = new PasswordField();
			password_1.setCaption("Passwort");
			password_1.setImmediate(false);
			password_1.setDescription("Bitte Passwort eingeben");
			password_1.setWidth("220px");
			password_1.setHeight("-1px");
			password_1.setRequired(true);
			password_1.setRequiredError("Das Feld darf nicht leer sein.");
			password_1.setIcon(FontAwesome.KEY);
			passwordLayout.addComponent(password_1);
					
			// password_2
			password_2 = new PasswordField();
			password_2.setCaption("Passwort (Kontrolle)");
			password_2.setImmediate(false);
			password_2.setDescription("Bitte Passwort zur Kontrolle erneut eingeben");
			password_2.setWidth("221px");
			password_2.setHeight("-1px");
			password_2.setRequired(true);
			password_2.setRequiredError("Das Feld darf nicht leer sein.");
			password_2.setIcon(FontAwesome.KEY);
			passwordLayout.addComponent(password_2);
		
		content.addComponent(passwordLayout);
		
		// handy
		handy = new TextField();
		handy.setCaption("Mobiltelefonnummer");
		handy.setImmediate(false);
		handy.setDescription("Bitte Mobiltelefonnummer angeben (optional)");
		handy.setWidth("220px");
		handy.setHeight("-1px");
		handy.setIcon(FontAwesome.PHONE);
		handy.setInputPrompt("01234/567890123");
		content.addComponent(handy);
		
		content.addComponent(new Label());
		
		// dhstud
		dhstud = new CheckBox();
		dhstud.setCaption("Ich bin Duale Studentin / Dualer Student an der DHBW Karlsruhe.");
		dhstud.setImmediate(false);
		dhstud.setDescription("Als Duale Studentin / Dualer Student können Sie mehr Funktionen nutzen. Moodle Anmeldedaten zur Validierung erforderlich");
		dhstud.setWidth("-1px");
		dhstud.setHeight("-1px");
		content.addComponent(dhstud);
		//wenn dhstud angekreuzt wird, werden die Felder fuer die Moodle-Anmeldedaten sichtbar
		dhstud.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

				@Override
	            public void valueChange(final ValueChangeEvent event) {
	                final boolean value = (boolean) event.getProperty().getValue();
	                
	                if(value==true){//Anzeigen der Moodle Felder sobald das Kontrollkästchen angekreuzt wird
	                	moodlename.setVisible(true);
	                	passwordmoodle.setVisible(true);
	                	moodlename.setRequired(true);
	                	moodlename.setRequiredError("Das Feld darf nicht leer sein.");
	                	passwordmoodle.setRequired(true);
	                	passwordmoodle.setRequiredError("Das Feld darf nicht leer sein.");
	                	
	                }else{//ausblednen der Felder wenn das Kästchen nicht angekreuzt ist
	                	moodlename.setVisible(false);
	                	passwordmoodle.setVisible(false);
	                	moodlename.setRequired(false);
	                	passwordmoodle.setRequired(false);
	                }
	            }
	        });
		
		// moodlename
		moodlename = new TextField();
		moodlename.setCaption("Moodle Anmeldenamen");
		moodlename.setImmediate(false);
		moodlename.setVisible(false);
		moodlename.setDescription("Bitte Ihren Moodle Anmeldenamen (nachname.vorname) angeben");
		moodlename.setWidth("211px");
		moodlename.setHeight("-1px");
		moodlename.setIcon(FontAwesome.GRADUATION_CAP);
		moodlename.setInputPrompt("nachname.vorname");
		content.addComponent(moodlename);
		
		// passwordmoodle
		passwordmoodle = new PasswordField();
		passwordmoodle.setCaption("Moodle Passwort");
		passwordmoodle.setImmediate(false);
		passwordmoodle.setVisible(false);
		passwordmoodle.setDescription("Bitte Ihr Moodle Kennwort angeben");
		passwordmoodle.setWidth("211px");
		passwordmoodle.setHeight("-1px");
		passwordmoodle.setIcon(FontAwesome.KEY);
		content.addComponent(passwordmoodle);
		
		content.addComponent(new Label());
		
		HorizontalLayout agbLayout = new HorizontalLayout();
		// agbs
		agbs = new CheckBox();
		agbs.setStyleName("link");
		agbs.setCaption("Ich akzeptiere den ");
		agbs.setImmediate(false);
		agbs.setDescription("Sie müssen den Haftungsausschluss akzeptieren damit Sie sich registrieren können");
		agbs.setWidth("-1px");
		agbs.setHeight("-1px");
		agbLayout.addComponent(agbs);
		
		// link_1
		haftungsausschlussLink = new Button();
		haftungsausschlussLink.setStyleName("link");
		haftungsausschlussLink.setCaption("Haftungsausschluss");
		haftungsausschlussLink.setImmediate(false);
		haftungsausschlussLink.setWidth("-1px");
		haftungsausschlussLink.setHeight("-1px");
		haftungsausschlussLink.setIcon(FontAwesome.EXTERNAL_LINK);
		haftungsausschlussLink.addClickListener(new Button.ClickListener(){
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				String name = "Impressum";
				getUI().getNavigator().addView(name, new Impressum());
				getUI().getNavigator().navigateTo(name);
			}
		});
		agbLayout.addComponent(haftungsausschlussLink);		
		content.addComponent(agbLayout);
		content.addComponent(new Label());
		
		// button
		registrierungAbschliessen = new Button();
		registrierungAbschliessen.setStyleName("speichern");
		registrierungAbschliessen.setIcon(FontAwesome.SAVE);
		registrierungAbschliessen.setCaption("Registrierung abschließen");
		registrierungAbschliessen.setImmediate(true);
		registrierungAbschliessen.setDescription("Abschließen der Registrierung, danach können Sie sich anmelden");
		registrierungAbschliessen.setWidth("-1px");
		registrierungAbschliessen.setHeight("-1px");
		content.addComponent(registrierungAbschliessen);
		
		//Abschließen der Registrierung
		registrierungAbschliessen.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				//Validierung der Felder
				boolean validate = validate();
				if(validate){//falls alle Felder richtig ausgefüllt wurden
					if(new UserProvider().userExists(email_1.getValue().toString()) == false){//der Nutzer existiert noch nicht
						//Werte in der DB speichern
						safeToDB();
						
						//E-Mail an den Nutzer senden
						sendEMail();
						
						//Navigation zur Startseite
						String name = "Startseite";
						getUI().getNavigator().addView(name, new Startseite());
						getUI().getNavigator().navigateTo(name);
						
						Notification not = new Notification("Die Registrierung war erfolgreich. Sie haben eine Email zur Aktivierung erhalten.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
						not.setStyleName("success");
						not.setDelayMsec(300);
						not.show(Page.getCurrent());
					}else{//ein Nutzer mit dieser E-Mail-Adresse existiert bereits
						Notification not = new Notification("Die Registrierung war nicht erfolgreich. Ein Nutzer mit dieser E-Mail-Adresse existiert bereits.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
						not.setStyleName("failure");
						not.setDelayMsec(300);
						not.show(Page.getCurrent());
					}
				}else{//Registrierung nicht erfolgreich
					Notification not = new Notification("Die Registrierung war nicht erfolgreich. Bitte überprüfen Sie Ihre Eingaben.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
					not.setStyleName("failure");
					not.setDelayMsec(300);
					not.show(Page.getCurrent());
				}
			}
		});
	}
	
	protected void safeToDB() {
		User u = new User();
		u.setFirstname(prename.getValue());
		u.setLastname(lastname.getValue());
		u.setEmail(email_1.getValue());
		u.setPassword(password_1.getValue());
		u.setMobile(handy.getValue());
		u.setActivated(false);
		if(dhstud.getValue()){
			u.setAccessLevel(1);
		}else{
			u.setAccessLevel(0);
		}
		//neuen User in die DB speichern mit:   new UserProvider().addUser(newUser);   (-> also User-Objekt "newUser" hier schon komplett instantiieren!)
		new UserProvider().addUser(u);
	}

	public boolean validate(){
		boolean erfolgreich=true;//wird auf false gesetzt, falls ein Wert nicht richtig ist
		try {
			prename.validate();
		} catch (InvalidValueException e) {
			erfolgreich=false;
		}
		
		try {
			lastname.validate();
		} catch (InvalidValueException e) {
			erfolgreich=false;
		}
		
		try {
			email_1.validate();
		} catch (InvalidValueException e) {
			erfolgreich=false;
		}
		
		try {
			email_2.validate();
		} catch (InvalidValueException e) {
			erfolgreich=false;
		}
		
		if(!email_1.getValue().equals(email_2.getValue())){
			email_1.setComponentError(new UserError("Die beiden E-Mail Adressen stimmen nicht überein."));
			email_2.setComponentError(new UserError("Die beiden E-Mail Adressen stimmen nicht überein."));
			erfolgreich=false;
		}
		
		if(password_1.getValue().length()< 5){
			password_1.setComponentError(new UserError("Das Passwort ist zu kurz. Es muss mindestens 5 Zeichen beinhalten."));
			erfolgreich=false;
		}
			
		if(!password_1.getValue().equals(password_2.getValue())){
			password_1.setComponentError(new UserError("Die beiden Passwörter stimmen nicht überein."));
			password_2.setComponentError(new UserError("Die beiden Passwörter stimmen nicht überein."));
			erfolgreich=false;
		}
			
		if(dhstud.getValue()){
			if(!DHStudValidator.validate(moodlename.getValue(), passwordmoodle.getValue())){
				moodlename.setComponentError(new UserError("Ihre Moodleanmeldedaten stimmen nicht."));
				passwordmoodle.setComponentError(new UserError("Ihre Moodleanmeldedaten stimmen nicht."));
			}
		}
		
		if(!agbs.getValue()){
			agbs.setComponentError(new UserError("Sie müssen die AGBs akzeptieren um sich anmelden zu können."));
			erfolgreich=false;
		}else{
			agbs.setComponentError(null);
		}

		return erfolgreich;
	}
	
	public void sendEMail(){
		
		String path = UI.getCurrent().getPage().getLocation().getHost() +":"+UI.getCurrent().getPage().getLocation().getPort()+UI.getCurrent().getPage().getLocation().getPath()+"#!Startseite/";
		String code = GenerateCode.generateCode(email_1.getValue());
		// Text der E-Mail mit Style-Informationen
		String body = "<meta charset='utf-8'/><img src='http://193.196.7.216:8080/housing/APP/connector/0/12/source/dh.PNG'/><br/><br/><span style='color: #000000' 'font-family: Arial, sans-serif''font-size: 16pt' >Sehr geehrte Nutzerin, sehr geehrter Nutzer,"
					 +"<br/><br/>vielen Dank, dass Sie sich für uns entschieden haben. Damit Sie sich erstmalig anmelden können, folgen Sie bitte dem folgenden Link. Dadurch wird sichergestellt, dass keine Unbefungten Ihre E-Mail-Adresse dazu verwenden können, um sich bei uns zu registrieren.</span>"
					 +"<br/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 20pt' >"
					 +"<a href='http://"+path+code+"'>weiter zum Login</a>"
					 +"</span><br/><br/>Mit freundlichen Grüßen<br/>Ihr DHBW Wohungsbörsen-Team<p/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 8pt' >Anschrift:<br/>DHBW Karlsruhe<br/>Baden-Wuerttemberg Cooperative State University Karlsruhe<br />Erzbergerstraße 121 . 76133 Karlsruhe <br />Postfach 10 01 36 . 76231 Karlsruhe   <br />Telefon +49.721.9735-5 <br />Telefax +49.721.9735-600 <br />E-Mail: dreischer@dhbw-karlsruhe.de<br /><br/><br/>Ansprechpartner:<br/> <br />Dr. Anita Dreischer<br /><br/><b>Copyright DHBW Karlsruhe. Alle Rechte vorbehalten.</b></span>";
		System.out.println(body);
		//E-Mail senden
		SendEMail.send(email_1.getValue(), "Wohnungsboerse_DHBW", "Danke für Ihre Registrierung", body);
	}
}
