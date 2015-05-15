package com.example.housing;

import com.example.housing.data.model.Offer;

import com.example.housing.utility.SendEMail;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;

public class AdminanfrageWohnung extends CustomHorizontalLayout implements View {
	
	VerticalLayout content;
	Offer angebot;
	private RichTextArea text;
	
	public AdminanfrageWohnung(Offer o){
		this.angebot = o;
		
		content = super.initCustomHorizontalLayout();
		setContent();
	}
	
	public void setContent() {
		
		//title
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Anbieter kontaktieren");
		title.addStyleName("title");
		content.addComponent(title);
		
		//Infolabel
		Label infoText = new Label("Hier k�nnen Sie eine Nachricht an den Anbieter des Wohnungsangebotes verfassen. Bitten Sie ihn beispielsweise um die Entfernung illegaler, nicht gew�nschter oder nicht mehr aktueller Inhalte.");
		content.addComponent(infoText);
		
		//text
		Label anfrage = new Label("Nachricht");
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
		sendButton.setCaption("Nachricht abschicken");
		//button.setImmediate(true);
		sendButton.setWidth("-1px");
		sendButton.setHeight("-1px");
		content.addComponent(sendButton);
		sendButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
					
					//E-Mail an den Nutzer senden
					sendEMail();
					
					//Navigation zur Startseite
					String name = "AngebotAnzeigen";
					getUI().getNavigator().addView(name, new Einzelansicht(angebot));
					getUI().getNavigator().navigateTo(name);
					
					Notification not = new Notification("Die Nachricht wurde erfolgreich �bermittelt!",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
					not.setStyleName("success");
					not.setDelayMsec(300);
					not.show(Page.getCurrent());
				
			}

		});
	}
	
	protected void sendEMail() {
		
		String message = "<meta charset='utf-8'/><img src='http://193.196.7.216:8080/housing/APP/connector/0/12/source/dh.PNG'/><br/><br/><span style='color: #000000' 'font-family: Arial, sans-serif''font-size: 16pt' >Sehr geehrte Nutzerin, sehr geehrter Nutzer,"
				+"<br/><br/>Sie haben eine Nachricht eines Portal-Administrators zu Ihrem Angebot \"" + angebot.getTitle() + "\" in der Wohnungsb�rse der DHBW erhalten:"
				+"<br/><br/>" + text.getValue() 
				+"</span>"
				+"<br/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 20pt' >"
				+ "</span><br/><br/>Mit freundlichen Gr��en<br/>Ihr DHBW Wohungsb�rsen-Team<p/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 8pt' >Anschrift:<br/>DHBW Karlsruhe<br/>Baden-Wuerttemberg Cooperative State University Karlsruhe<br />Erzbergerstra�e 121 . 76133 Karlsruhe <br />Postfach 10 01 36 . 76231 Karlsruhe   <br />Telefon +49.721.9735-5 <br />Telefax +49.721.9735-600 <br />E-Mail: dreischer@dhbw-karlsruhe.de<br /><br/><br/>Ansprechpartner:<br/> <br />Dr. Anita Dreischer<br /><br/><b>Copyright DHBW Karlsruhe. Alle Rechte vorbehalten.</b></span>";
		
		//Email an Anbieter senden
		SendEMail.send(angebot.getOffer_idUser().getEmail(), "wohnungsboerse_dh@web.de", "Benachrichtigung zu Ihrem Angebot in der DHBW-Wohnungsb�rse", message);
	
		//TODO irgendwo hinterlegen, abspeichern,... damit Admin die Nachricht auch sp�ter noch sehen kann
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
