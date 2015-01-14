package com.example.housing;

import com.example.housing.data.model.User;
import com.example.housing.data.provider.UserProvider;
import com.example.housing.utility.DHStudValidator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

// TODO: Auto-generated Javadoc
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
	
	/** The dh_1. */
	private Label dh_1;
	
	/** The dh_2. */
	private Label dh_2;
	
	/** The moodlename. */
	private TextField moodlename;
	
	/** The passwordmoodle. */
	private PasswordField passwordmoodle;
	
	/** The passwordLayout. */
	private HorizontalLayout passwordLayout;
	
	/** The button_1. */
	private Button button_1;//Profil bearbeiten
	
	/** The button_2. */
	private Button button_2;//abbrechen
	
	/** The button_3. */
	private Button button_3;//Änderungen speichern
	
	/** The button_4. */
	private Button button_4;//Profil löschen


	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		
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
	 * Sets the Content of the page.
	 */
	public void setContent(){
				
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
		passwordLayout = new HorizontalLayout();
		passwordLayout.setVisible(false);
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
		
		// dh
		dh_1 = new Label();
		dh_1.setWidth("-1px");
		dh_1.setHeight("-1px");
		dh_1.setValue("Sie sind als Dualer Student der DHBW Karlsruhe registriert.");
		dh_1.setVisible(false);
		content.addComponent(dh_1);
		
		// dh
		dh_2 = new Label();
		dh_2.setWidth("-1px");
		dh_2.setHeight("-1px");
		dh_2.setValue("Sie sind nicht als Dualer Student der DHBW Karlsruhe registriert.");
		dh_2.setVisible(false);
		content.addComponent(dh_2);
		
		// dhstud
		dhstud = new CheckBox();
		dhstud.setCaption("Ich bin Dualer Student an der DH Karlsruhe.");
		dhstud.setImmediate(false);
		dhstud.setDescription("Als Dualer Student können Sie mehr Funktionen nutzen. Moodle Anmeldedaten zur Validierung erforderlich");
		dhstud.setWidth("-1px");
		dhstud.setEnabled(true);
		dhstud.setHeight("-1px");
		dhstud.setVisible(false);
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
		moodlename.setEnabled(true);
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
		passwordmoodle.setEnabled(true);
		content.addComponent(passwordmoodle);
		
		// button_1
		button_1 = new Button();
		button_1.setCaption("Profildaten bearbeiten");
		button_1.setImmediate(true);
		button_1.setDescription("Bearbeiten Ihrer Profildaten.");
		button_1.setWidth("-1px");
		button_1.setHeight("-1px");
		button_1.setVisible(true);
		button_1.setIcon(FontAwesome.PENCIL);
		content.addComponent(button_1);
		//Bearbeitung aktivieren
		button_1.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				//Felder anzeigen und bearbeitbar machen
				button_1.setVisible(false);
				button_2.setVisible(true);
				button_3.setVisible(true);
				prename.setEnabled(true);
				lastname.setEnabled(true);
				email_1.setEnabled(true);
				email_2.setVisible(true);
				email_2.setEnabled(true);
				passwordLayout.setVisible(true);
				handy.setEnabled(true);
				if(VaadinSession.getCurrent().getAttribute(User.class).getAccessLevel()==0){
					dhstud.setVisible(true);
				}
			}
		});
		
		// button_2
		button_2 = new Button();
		button_2.setCaption("Abbrechen");
		button_2.setIcon(FontAwesome.MAIL_REPLY);
		button_2.setImmediate(true);
		button_2.setDescription("Abbrechen der Bearbeitung. Ihre Änderungen werden nicht gespeichert.");
		button_2.setWidth("-1px");
		button_2.setHeight("-1px");
		button_2.setVisible(false);
		content.addComponent(button_2);
		button_2.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				//ursprüngliche Daten wieder laden
				daten();
				//Felder ausbelden und nicht bearbeitbar machen
				button_1.setVisible(true);
				button_2.setVisible(false);
				button_3.setVisible(false);
				prename.setEnabled(false);
				lastname.setEnabled(false);
				email_1.setEnabled(false);
				email_2.setVisible(false);
				email_2.setEnabled(false);
				passwordLayout.setVisible(false);
				handy.setVisible(true);
				handy.setEnabled(false);
				dhstud.setVisible(false);
				moodlename.setVisible(false);
				passwordmoodle.setVisible(false);
			}
		});
		
		// button_3
		button_3 = new Button();
		button_3.setVisible(false);
		button_3.setCaption("Änderungen speichern");
		button_3.setIcon(FontAwesome.SAVE);
		button_3.setImmediate(true);
		button_3.setDescription("Speichern der Änderungen.");
		button_3.setWidth("-1px");
		button_3.setHeight("-1px");
		content.addComponent(button_3);
		button_3.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				//Validierung der Felder
				boolean validate = validate();
				if(validate){//falls alle Felder richtig ausgefüllt wurden
					
					User u = VaadinSession.getCurrent().getAttribute(User.class);
					//User u = new UserProvider().findByEmail(email_1.getValue());
					u.setIdUser(VaadinSession.getCurrent().getAttribute(User.class).getIdUser());
					u.setFirstname(prename.getValue());
					u.setLastname(lastname.getValue());
					u.setEmail(email_1.getValue());
					u.setPassword(password_1.getValue());
					u.setMobile(handy.getValue());
					if(dhstud.getValue()){
						u.setAccessLevel(1);
					}
					//Werte in der DB speichern
					new UserProvider().alterUser(u);
					
					//neues User-Objekt in der Session speichern
					VaadinSession.getCurrent().setAttribute(User.class, u);//User-Objekt in der Session speichern
					
					//Navigation zur Profilseite
					String name = "Profile";
					getUI().getNavigator().addView(name, new Profile());
					getUI().getNavigator().navigateTo(name);
					
					Notification.show("Ihre Änderungen wurden erfolgreich gespeichert.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
				}else{//Registrierung nicht erfolgreich
					Notification.show("Die Speicherung Ihrer Änderungen war nicht erfolgreich. Bitte überprüfen Sie Ihre Eingaben.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
				}
			}
		});
		
		
		// button_4
		button_4 = new Button();
		button_4.setStyleName("loeschen");
		button_4.setVisible(true);
		button_4.setCaption("Profil löschen");
		button_4.setImmediate(true);
		button_4.setDescription("Löschen des Profils.");
		button_4.setWidth("-1px");
		button_4.setHeight("-1px");
		content.addComponent(button_4);
		button_4.addClickListener(new Button.ClickListener() {
		public void buttonClick(ClickEvent event) {
				CheckWindow w  = new CheckWindow();//Wollen Sie Ihr Profil wirklich löschen?
				UI.getCurrent().addWindow(w);//neues Fenster hinzufügen
			}
		
			//Check Window
			class CheckWindow extends Window{
				
				public CheckWindow(){
					super("Wollen Sie Ihr Profil wirklich löschen?");
					this.center();		
					this.setHeight("50%");
				    this.setWidth("30%");
				    
				    //Layout
				    final VerticalLayout content = new VerticalLayout();
				    content.setMargin(true);
				    
				    //Hinweistext
					Label l = new Label("Wenn Sie Ihr Profil löschen werden all Ihre Daten gelöscht (inklusive Ihrer angebotenen Wohnungen)!");
					content.addComponent(l);
					
					//Button "Ja"
					Button yes = new Button();
					yes.setCaption("Ja, ich will mein Profil löschen.");
					yes.setDescription("Profil löschen");
					yes.setIcon(FontAwesome.CHECK);
					yes.setWidth("-1px");
					yes.setHeight("-1px");
					content.addComponent(yes);
					yes.addClickListener(new Button.ClickListener() {
						public void buttonClick(ClickEvent event) {
							User u = new UserProvider().findByEmail(VaadinSession.getCurrent().getAttribute(User.class).getEmail());//User in der DB suchen
							new UserProvider().removeUser(u);//User in der DB löschen
							
							//Logout
							VaadinSession.getCurrent().setAttribute("login", false);
							VaadinSession.getCurrent().setAttribute(User.class, null);
							
							//Navigation zur Startseite
							String name = "Startseite";
							getUI().getNavigator().addView(name, new Startseite());
							getUI().getNavigator().navigateTo(name);
							
							//dieses Fenster schließen
							CheckWindow.this.close();
							
							//Meldung an den Nutzer
							Notification notif = new Notification("Ihr Profil wurde gelöscht!", Type.HUMANIZED_MESSAGE);
							notif.setDelayMsec(300);
							notif.setIcon(FontAwesome.INFO);
							notif.show(Page.getCurrent());
						}
					});
					
					//Abbrechen-Button
					Button no = new Button();
					no.setCaption("Nein, doch nicht.");
					no.setIcon(FontAwesome.MAIL_REPLY);
					no.setDescription("Profil nicht löschen");
					no.setWidth("-1px");
					no.setHeight("-1px");
					content.addComponent(no);
					no.addClickListener(new Button.ClickListener() {
						public void buttonClick(ClickEvent event) {
							CheckWindow.this.close();//Fenster schließen
						}
					});
					
					this.setContent(content);
				}
				
			}
		});
		
		daten();//Felder mit Daten befüllen
	}
	
	/**
	 * Sets the data from the User-Session-Object.
	 * @see com.vaadin.server.VaadinSession
	 */
	private void daten() {
		prename.setValue(VaadinSession.getCurrent().getAttribute(User.class).getFirstname());
		lastname.setValue(VaadinSession.getCurrent().getAttribute(User.class).getLastname());
		email_1.setValue(VaadinSession.getCurrent().getAttribute(User.class).getEmail());
		email_2.setValue(VaadinSession.getCurrent().getAttribute(User.class).getEmail());
		password_1.setValue(VaadinSession.getCurrent().getAttribute(User.class).getPassword());
		password_2.setValue(VaadinSession.getCurrent().getAttribute(User.class).getPassword());
		handy.setValue(VaadinSession.getCurrent().getAttribute(User.class).getMobile());
		if(VaadinSession.getCurrent().getAttribute(User.class).getAccessLevel()==0){
			dh_2.setVisible(true);
			dh_1.setVisible(false);
		}else{
			dh_1.setVisible(true);
			dh_2.setVisible(false);
		}
	}

	/**
	 * Validates the user input.
	 *
	 * @return boolean
	 * @see com.vaadin.data.validator.EmailValidator
	 * @see com.vaadin.data.validator.StringLengthValidator;
	 * @see com.example.housing.utility.DHStudValidator;
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
		
		System.out.println(dhstud.getValue());
		if(dhstud.getValue()){
			if(!DHStudValidator.validate(moodlename.getValue(), passwordmoodle.getValue())){
				moodlename.setComponentError(new UserError("Ihre Moodleanmeldedaten stimmen nicht."));
				passwordmoodle.setComponentError(new UserError("Ihre Moodleanmeldedaten stimmen nicht."));
			}
		}
		return erfolgreich;
	}
}
