package com.example.housing;

import com.example.housing.data.model.User;
import com.example.housing.utility.DHStudValidator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class Profile.
 * 
 * @author MWI Wohungsbörse 2014
 * @version 1.0
 * @see com.example.housing.HousingUI
 */
@SuppressWarnings("serial")
public class Profile extends VerticalLayout implements View{
	
	/** The content. */
	private VerticalLayout content;//Layout fuer den Inhalt
	
	//Felder des Registrierungsformulars
	/** The title. */
	private Label title;
	
	/** The lastname. */
	private TextField lastname;
	
	/** The prename. */
	private TextField prename;
	
	/** The email_1. */
	private TextField email_1;
	
	/** The email_2. */
	private TextField email_2;
	
	/** The password_1. */
	private PasswordField password_1;
	
	/** The password_2. */
	private PasswordField password_2;
	
	/** The handy. */
	private TextField handy;
	
	/** The dhstud. */
	private CheckBox dhstud;
	
	/** The moodlename. */
	private TextField moodlename;
	
	/** The passwordmoodle. */
	private PasswordField passwordmoodle;
	
	
	
	/** The button_1. */
	private Button button_1;
	
	/** The button_2. */
	private Button button_2;
	
	/** The button_3. */
	private Button button_3;


	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Instantiates a new Registrierung.
	 */
	public Profile(){
		setMargin(true);
		
		//Navigation hinzufuegen
		Navigation nav = new Navigation();
		nav.setWidth("100%");
		nav.addStyleName("navigation");
		addComponent(nav);
		
		NavigationPublic navPublic = new NavigationPublic();
		addComponent(navPublic);
		
		//falls der Benutzer eingelogt ist verändert sich die Navigation
		if(VaadinSession.getCurrent().getAttribute("login").equals(true)){
			nav.setVisible(true);
			navPublic.setVisible(false);
		}else{
			nav.setVisible(false);
			navPublic.setVisible(true);
		}
		
		//Inhalt hinzufuegen
		content = new VerticalLayout();
		content.setMargin(true);
		content.setWidth("100%");
		setContent();//Methode zum befuellen des Inhalts aufrufen
		addComponent(content);
		
		//Footer hinzufuegen
		Footer f = new Footer();
		addComponent(f);
		
		
	}
	
	/**
	 * Methode zum Befuellen des Inhalts der Seite.
	 */
	public void setContent(){
		
		//TODO Inhalt einfügen
		
		// title
		title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Meine Profildaten");
		title.addStyleName("title");
		content.addComponent(title);
				
		// prename
		prename = new TextField();
		prename.setCaption("Vorname");
		prename.setDescription("Bitte Vorname angeben");
		prename.setWidth("220px");
		prename.setHeight("-1px");
		prename.setIcon(FontAwesome.USER);
		prename.addStyleName("textfield");
		prename.setRequired(true);
		prename.setRequiredError("Das Feld darf nicht leer sein.");
		prename.setImmediate(false);
		prename.setEnabled(false);

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
		lastname.setEnabled(false);
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
			email_1.addValidator(new EmailValidator("Das iste keine gültige E-Mail Adresse."));
			email_1.setEnabled(false);
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
			email_2.addValidator(new EmailValidator("Das iste keine gültige E-Mail Adresse."));
		
			email_2.setVisible(false);
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
			password_1.addValidator(new StringLengthValidator("Das Passwort ist zu kurz. Es muss mindestens 5 Zeichen lang sein.", 5, null, false));
			password_1.setIcon(FontAwesome.KEY);
			password_1.setVisible(false);
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
			password_2.addValidator(new StringLengthValidator("Das Passwort ist zu kurz. Es muss mindestens 5 Zeichen lang sein.", 5, null, false));
			password_2.setIcon(FontAwesome.KEY);
			password_2.setVisible(false);
			passwordLayout.addComponent(password_2);
		
		content.addComponent(passwordLayout);
		
		// handy
		handy = new TextField();
		handy.setCaption("Handynummer");
		handy.setImmediate(false);
		handy.setDescription("Bitte Handynummer angeben (optional)");
		handy.setWidth("220px");
		handy.setHeight("-1px");
		handy.setIcon(FontAwesome.PHONE);
		handy.setEnabled(false);
		content.addComponent(handy);
		
		// dhstud
		dhstud = new CheckBox();
		dhstud.setCaption("Ich bin Dualer Student an der DH Karlsruhe.");
		dhstud.setImmediate(false);
		dhstud.setDescription("Als Dualer Student können Sie mehr Funktionen nutzen. Moodle Anmeldedaten zur Validierung erforderlich");
		dhstud.setWidth("-1px");
		dhstud.setEnabled(false);
		dhstud.setHeight("-1px");
		content.addComponent(dhstud);
		//wenn dhstud angekreuzt wird, werden die Felder fuer die Moodle-Anmeldedaten sichtbar
		dhstud.addValueChangeListener(new ValueChangeListener() {
	            @Override
	            public void valueChange(final ValueChangeEvent event) {
	                final boolean value = (boolean) event.getProperty().getValue();
	                
	                if(value==true){
	                	moodlename.setVisible(true);
	                	passwordmoodle.setVisible(true);
	                	moodlename.setRequired(true);
	                	moodlename.setRequiredError("Das Feld darf nicht leer sein.");
	                	passwordmoodle.setRequired(true);
	                	passwordmoodle.setRequiredError("Das Feld darf nicht leer sein.");
	                	
	                }else{
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
		moodlename.setEnabled(false);
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
		passwordmoodle.setEnabled(false);
		content.addComponent(passwordmoodle);
		
	
		
		// button_1
		button_1 = new Button();
		button_1.setCaption("Änderungen speichern");
		button_1.setImmediate(true);
		button_1.setDescription("Abschließen der Registrierung, danach können Sie sich anmelden");
		button_1.setWidth("-1px");
		button_1.setHeight("-1px");
		content.addComponent(button_1);
		//Abschließen der Registrierung
		button_1.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				//Validierung der Felder
				boolean validate = validate();
				if(validate){//falls alle Felder richtig ausgefüllt wurden
					
					//Werte in der DB speichern
					safeToDB();
					
					//Navigation zur Startseite
					String name = "Startseite";
					getUI().getNavigator().addView(name, new Startseite());
					getUI().getNavigator().navigateTo(name);
					
					Notification.show("Die Registrierung war erfolgreich. Sie können sich jetzt anmelden.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
				}else{//Registrierung nicht erfolgreich
					Notification.show("Die Registrierung war nicht erfolgreich. Bitte überprüfen Sie Ihre Eingaben.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
				}
	
			}
		});
		
		
		// button_2
		button_2 = new Button();
		button_2.setCaption("Abbrechen");
		button_2.setImmediate(true);
		button_2.setDescription("Abschließen der Registrierung, danach können Sie sich anmelden");
		button_2.setWidth("-1px");
		button_2.setHeight("-1px");
		content.addComponent(button_2);
		//Abschließen der Registrierung
		button_2.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
	
			}
		});
		
		
		// button_3
		button_3 = new Button();
		button_3.setCaption("Profildaten bearbeiten");
		button_3.setImmediate(true);
		button_3.setDescription("Abschließen der Registrierung, danach können Sie sich anmelden");
		button_3.setWidth("-1px");
		button_3.setHeight("-1px");
		content.addComponent(button_3);
		//Abschließen der Registrierung
		button_2.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
	
			}
		});
		
		test();
	}
	
	

	
	
	
	private void test() {
		// TODO Auto-generated method stub
		prename.setValue("Max");
		lastname.setValue("Mustermann");
		
	}

	/**
	 * Stores a new User to Database.
	 */
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
		//TODO entsprechende Methode in UserProvider aufrufen
		
	}

	/**
	 * Validates the user input
	 * @return boolean
	 */
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
			
			
		if(!password_1.getValue().equals(password_2.getValue())){
			System.out.println(password_1.getValue());
			System.out.println(password_2.getValue());
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
		
	

		return erfolgreich;
		
	}

}
