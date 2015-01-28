package com.example.housing;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Request;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.RequestProvider;
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

public class AdminanfrageWohnung extends HorizontalLayout implements View {
	
	VerticalLayout content;
	Offer angebot;
	private RichTextArea text;
	
	public AdminanfrageWohnung(Offer o){
		this.angebot = o;
		
		content = new VerticalLayout();
		
		content.setMargin(true);
		content.setSizeFull();
		content.setSpacing(true);
		
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
		title.setValue("Anfrageformular");
		title.addStyleName("title");
		content.addComponent(title);
		
		//Infolabel
		Label infoText = new Label("Hier können Sie eine Nachricht an den Anbieter verfassen.");
		content.addComponent(infoText);
		
		//text
		Label anfrage = new Label("Anfrage");
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
		sendButton.setCaption("Anfrage abschicken");
		//button.setImmediate(true);
		sendButton.setWidth("-1px");
		sendButton.setHeight("-1px");
		content.addComponent(sendButton);
		sendButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
					
					//E-Mail an den Nutzer senden
					sendEMail();
					
					//TODO: Navigation
					//Navigation zur Startseite
					String name = "AngebotAnzeigen";
					getUI().getNavigator().addView(name, new Einzelansicht(angebot));
					getUI().getNavigator().navigateTo(name);
					
					Notification not = new Notification("Die Anfrage war erfolgreich!",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
					not.setStyleName("success");
					not.setDelayMsec(300);
					not.setIcon(FontAwesome.CHECK_SQUARE_O);
					not.show(Page.getCurrent());
				
			}

		});
	}
	
	
	
	protected void sendEMail() {
		
		
		//Email an Anfrager senden
		
		SendEMail.send(angebot.getOffer_idUser().getEmail(), "wohnungsboerse_dh@web.de", "Ihre Anfrage in der DHBW-Wohnungsbörse",text.getValue());
		
		
	
	}
	
	

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
