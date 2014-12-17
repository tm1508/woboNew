package com.example.housing;
/**
 * Registrierung der Nutzer, enthaelt ein Registrierungsformular
 * 
 * @author MWI Wohungsbörse 2014
 * @version 1.0
 * @see com.example.housing.HousingUI
 */
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
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


// TODO: Auto-generated Javadoc
/**
 * The Class Registrierung.
 */
@SuppressWarnings("serial")
public class Registrierung extends VerticalLayout implements View{
	
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
	
	/** The agbs. */
	private CheckBox agbs;
	
	/** The link_1. */
	private Link link_1;
	
	/** The button. */
	private Button button;


	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Konstruktor der Klasse Registrierung
	 * fuegt die Navigation, den Inhalt und die Fusszeile hinzu.
	 */
	public Registrierung(){
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
		title.setValue("Registrierung");
		title.addStyleName("title");
		content.addComponent(title);
				
		// prename
		prename = new TextField();
		prename.setCaption("Vorname");
		prename.setImmediate(false);
		prename.setDescription("Bitte Vorname angeben");
		prename.setWidth("220px");
		prename.setHeight("-1px");
		prename.setRequired(true);
		prename.setIcon(FontAwesome.USER);
		prename.setInputPrompt("Max");
		prename.addStyleName("textfield");
		content.addComponent(prename);
		
		// lastname
		lastname = new TextField();
		lastname.setCaption("Nachname");
		lastname.setImmediate(false);
		lastname.setDescription("Bitte Nachname angeben");
		lastname.setWidth("221px");
		lastname.setHeight("-1px");
		lastname.setRequired(true);
		lastname.setIcon(FontAwesome.USER);
		lastname.setInputPrompt("Mustermann");
		content.addComponent(lastname);
		
		//E-Mail mit eigenenm Layout
		HorizontalLayout emailLayout = new HorizontalLayout();
			// email_1
			email_1 = new TextField();
			email_1.setCaption("E-Mail");
			email_1.setImmediate(false);
			email_1.setDescription("Bitte E-Mail-Adresse angeben");
			email_1.setWidth("221px");
			email_1.setHeight("-1px");
			email_1.setRequired(true);
			email_1.setIcon(FontAwesome.ENVELOPE);
			email_1.setInputPrompt("max.mustermann@test.de");
			emailLayout.addComponent(email_1);
					
			// email_2
			email_2 = new TextField();
			email_2.setCaption("E-Mail (Kontrolle)");
			email_2.setImmediate(false);
			email_2.setDescription("Bitte E-Mail zur Kontrolle erneut angeben");
			email_2.setWidth("221px");
			email_2.setHeight("-1px");
			email_2.setRequired(true);
			email_2.setIcon(FontAwesome.ENVELOPE);
			email_2.setInputPrompt("max.mustermann@test.de");
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
		handy.setInputPrompt("01234/567890123");
		content.addComponent(handy);
		
		// dhstud
		dhstud = new CheckBox();
		dhstud.setCaption("Ich bin Dualer Student an der DH Karlsruhe.");
		dhstud.setImmediate(false);
		dhstud.setDescription("Als Dualer Student können Sie mehr Funktionen nutzen. Moodle Anmeldedaten zur Validierung erforderlich");
		dhstud.setWidth("-1px");
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
	                }else{
	                	moodlename.setVisible(false);
	                	passwordmoodle.setVisible(false);
	                }
	                
	            }
	        });
		
		// moodlename
		moodlename = new TextField();
		moodlename.setCaption("Moodle Anmeldenamen");
		moodlename.setEnabled(false);
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
		passwordmoodle.setEnabled(false);
		passwordmoodle.setImmediate(false);
		passwordmoodle.setVisible(false);
		passwordmoodle.setDescription("Bitte Ihr Moodle Kennwort angeben");
		passwordmoodle.setWidth("211px");
		passwordmoodle.setHeight("-1px");
		passwordmoodle.setIcon(FontAwesome.KEY);
		content.addComponent(passwordmoodle);
		
		// agbs
		agbs = new CheckBox();
		agbs.setCaption("Ich akzeptiere die ");
		agbs.setImmediate(false);
		agbs.setDescription("Sie müssen die AGBs akzeptieren damit Sie sich registrieren können");
		agbs.setWidth("-1px");
		agbs.setHeight("-1px");
		agbs.setRequired(true);
		content.addComponent(agbs);
		
		// link_1
		link_1 = new Link();
		link_1.setStyleName("text");
		link_1.setCaption("AGBs");
		link_1.setImmediate(false);
		link_1.setWidth("-1px");
		link_1.setHeight("-1px");
		link_1.setIcon(FontAwesome.EXTERNAL_LINK);
		content.addComponent(link_1);
		
		// button
		button = new Button();
		button.setCaption("Registrierung abschließen");
		button.setImmediate(true);
		button.setDescription("Abschließen der Registrierung, danach können Sie sich anmelden");
		button.setWidth("-1px");
		button.setHeight("-1px");
		content.addComponent(button);
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				//TODO Registrierung
				Notification.show("Die Registrierung war erfolgreich. Sie können sich jetzt anmelden.",Type.HUMANIZED_MESSAGE);

			}
		});
		

	}
	
	
	


}
