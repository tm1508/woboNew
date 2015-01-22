package com.example.housing;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Request;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.RequestProvider;
import com.example.housing.utility.DHStudValidator;
import com.example.housing.utility.SendEMail;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;

@SuppressWarnings("serial")
public class Kontaktformular extends HorizontalLayout implements View{

	private Offer requestedOffer;
	
	private RichTextArea text;
	
	VerticalLayout content;
	TextField prename;
	TextField email_1;
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public Kontaktformular(){
		
	
		this.setWidth("100%");
		
		//linkes rotes Panel
		Panel p = new Panel();
		p.setWidth("100%");
		p.setHeight("100%");
		p.addStyleName("red");
		addComponent(p);
		this.setExpandRatio(p, 1);
		
		//mittlerer Teil der Seite
		VerticalLayout v = new VerticalLayout();
				
		//Navigation hinzufuegen
		Navigation nav = new Navigation();
		nav.setWidth("100%");
		nav.addStyleName("navigation");
		v.addComponent(nav);
		
		NavigationPublic navPublic = new NavigationPublic();
		v.addComponent(navPublic);
		
		NavigationAdmin navAdmin = new NavigationAdmin();
		v.addComponent(navAdmin);
		
		//falls der Benutzer eingelogt ist verändert sich die Navigation
		if(VaadinSession.getCurrent().getAttribute("login").equals(true)){
			if(VaadinSession.getCurrent().getAttribute(User.class).getAccessLevel()==2){//falls der User ein Admin ist
				nav.setVisible(false);
				navPublic.setVisible(false);
				navAdmin.setVisible(true);//Admin-Navigation
			}else{//ansonsten: Naviagtion für eingeloggte Nutzer
				nav.setVisible(true);
				navPublic.setVisible(false);
				navAdmin.setVisible(false);
			}
		}else{//ansonsten Public Navigation (für alle)
			nav.setVisible(false);
			navPublic.setVisible(true);
			navAdmin.setVisible(false);
		}
			
			//Inhalt hinzufuegen
			content = new VerticalLayout();
			content.setMargin(true);
			content.setWidth("100%");
			setContent();//Methode zum befuellen des Inhalts aufrufen
			v.addComponent(content);
			
			//Footer hinzufuegen
			Footer f = new Footer();
			v.addComponent(f);
			
			//rotes Panel unter dem Footer
			Panel p2 = new Panel();
			p2.setWidth("100%");
			p2.addStyleName("red");
			p2.setHeight("30px");
			v.addComponent(p2);
	
		addComponent(v);
		this.setExpandRatio(v, 12);
		
		//rotes rechtes Panel
		Panel p1 = new Panel();
		p1.setWidth("100%");
		p1.addStyleName("red");
		p1.setHeight("100%");
		addComponent(p1);
		this.setExpandRatio(p1, 1);
	}

	private void setContent() {
		
		//title
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Kontaktformular");
		title.addStyleName("title");
		content.addComponent(title);
		
		//Infolabel
		Label infoText = new Label("Hier können Sie den Administrator kontaktieren. Bitte geben Sie hierzu Ihren Namen und Ihre E-Mail-Adresse an. Er kann Sie dann gezielt kontaktieren, um mit Ihnen alles Weitere zu besprechen.");
		content.addComponent(infoText);
		
		// prename
		prename = new TextField();
		prename.setCaption("Name");
		prename.setRequiredError("Das Feld darf nicht leer sein.");
		prename.setDescription("Bitte Name angeben");
		prename.setWidth("220px");
		prename.setHeight("-1px");
		prename.setIcon(FontAwesome.USER);
		prename.setInputPrompt("Max");
		prename.addStyleName("textfield");
		prename.setRequired(true);
		prename.setRequiredError("Das Feld darf nicht leer sein.");
		prename.setImmediate(false);
		content.addComponent(prename);
		
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
		content.addComponent(email_1);
		
		//text
		Label anfrage = new Label("Kontaktformular");
		anfrage.addStyleName("AbschnittLabel");
		text = new RichTextArea();
		text.setRequired(true);
		text.setRequiredError("Das Textfeld darf nicht leer sein.");
		text.setWidth("100%");
		content.addComponent(anfrage);
		content.addComponent(text);
		
		// button
		Button sendButton = new Button();
		sendButton.setIcon(FontAwesome.ENVELOPE_SQUARE);
		sendButton.setCaption("Formular abschicken");
		//button.setImmediate(true);
		sendButton.setWidth("-1px");
		sendButton.setHeight("-1px");
		content.addComponent(sendButton);
		sendButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				if(validate()){//Anfrage existiert noch nicht
					
					//E-Mail an den Nutzer senden
					sendEMail();
					
					//Navigation zur Startseite
					String name = "Startseite";
					getUI().getNavigator().addView(name, new Startseite());
					getUI().getNavigator().navigateTo(name);
					
					Notification not = new Notification("Die Anfrage war erfolgreich. Der Administrator kann Sie nun kontaktieren.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
					not.setStyleName("success");
					not.setDelayMsec(300);
					not.setIcon(FontAwesome.CHECK_SQUARE_O);
					not.show(Page.getCurrent());
				}else{//eine Anfrage von diesem User für dieses Angebot existiert bereits
					Notification not = new Notification("Bitte füllen Sie alle Mussfelder.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
					not.setDelayMsec(300);
					not.setStyleName("failure");
					not.show(Page.getCurrent());
				}
			}

		});
	}
	

	
	protected void sendEMail() {
		
		String bodyAnfrager = "<span style='color: #000000' 'font-family: Arial, sans-serif''font-size: 16pt' >Sehr geehrter Administrator,"
				+"<br/><br/>Sie haben eine Frage zu der DHBW-Wohungsbörse erhalten:"
				+"<br/><br/>" + text.getValue() 
				+"<br/>" + "Kontaktdaten des Absenders: "
				+"<br/> Name:" + prename.getValue()
				+"<br/>" + "Email: " + email_1.getValue() ;
			
		//Email an Anfrager senden
		SendEMail.sendEmailAlias("wohnungsboerse_dh@web.de", "wohnungsboerse_dh@web.de", email_1.getValue(), "Kontaktfomular", bodyAnfrager);
		
	}
	
	public boolean validate(){
		boolean erfolgreich=true;//wird auf false gesetzt, falls ein Wert nicht richtig ist
		try {
			prename.validate();
		} catch (Exception e) {
			erfolgreich=false;
		}
		
		
		
		try {
			email_1.validate();
		} catch (Exception e) {
			erfolgreich=false;
		}
		
		
		return erfolgreich;
		
	}
	
}
