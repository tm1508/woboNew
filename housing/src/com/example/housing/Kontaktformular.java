package com.example.housing;

import com.example.housing.data.model.Offer;
import com.example.housing.utility.SendEMail;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;

public class Kontaktformular extends CustomHorizontalLayout implements View{
	private static final long serialVersionUID = 1L;

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
		
		content = super.initCustomHorizontalLayout();
		setContent();
	}

	public void setContent() {
		
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
		//sendButton.setIcon(FontAwesome.ENVELOPE_SQUARE);
		sendButton.setIcon(FontAwesome.SEND);
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
			
		String bodyAnfrager = "<meta charset='utf-8'/><img src='http://193.196.7.216:8080/housing/APP/connector/0/12/source/dh.PNG'/><br/><br/><span style='color: #000000' 'font-family: Arial, sans-serif''font-size: 16pt' >Sehr geehrter Administrator,"
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
