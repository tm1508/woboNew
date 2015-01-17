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

@SuppressWarnings("serial")
public class Anfrageformular extends HorizontalLayout implements View{

	private Offer requestedOffer;
	
	private RichTextArea text;
	
	VerticalLayout content;
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public Anfrageformular(Offer currentOffer){
		
		//Bezug zu Angebot
		requestedOffer = currentOffer;
		
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
		Label infoText = new Label("Hier können Sie eine Anfrage an den Anbieter verfassen. Dieser Text wird zusammen mit Ihren Kontaktdaten aus Ihrem Profil an den Anbieter weitergeleitet. Er kann Sie dann gezielt kontaktieren, um mit Ihnen alles Weitere zu besprechen.");
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
				if(new RequestProvider().requestExists(VaadinSession.getCurrent().getAttribute(User.class), requestedOffer) == false){//Anfrage existiert noch nicht
					//Werte in der DB speichern
					safeToDB();
					
					//E-Mail an den Nutzer senden
					sendEMail();
					
					//TODO: Navigation
					//Navigation zur Startseite
					String name = "AngebotAnzeigen";
					getUI().getNavigator().addView(name, new Einzelansicht(requestedOffer));
					getUI().getNavigator().navigateTo(name);
					
					Notification not = new Notification("Die Anfrage war erfolgreich. Der Anbieter kann Sie nun kontaktieren.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
					not.setStyleName("success");
					not.setDelayMsec(300);
					not.setIcon(FontAwesome.CHECK_SQUARE_O);
					not.show(Page.getCurrent());
				}else{//eine Anfrage von diesem User für dieses Angebot existiert bereits
					Notification not = new Notification("Sie hatten bereits eine Anfrage für dieses Angebot abgegeben.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
					not.setDelayMsec(300);
					not.setStyleName("failure");
					not.show(Page.getCurrent());
				}
			}

		});
	}
	
	protected void safeToDB() {
		
		Request request = new Request();
		request.setRequest_idUser(VaadinSession.getCurrent().getAttribute(User.class));
		request.setRequest_idOffer(requestedOffer);
		
		new RequestProvider().addRequest(request);
		
	}
	
	protected void sendEMail() {
		String bodyAnbieter = "<span style='color: #000000' 'font-family: Arial, sans-serif''font-size: 16pt' >Sehr geehrte Nutzerin, sehr geehrter Nutzer,"
				+"<br/><br/>Sie haben eine Anfrage zu Ihrem Angebot: \"" + requestedOffer.getTitle() + "\" in der Wohnungsbörse der DHBW erhalten:"
				+"<br/><br/>" + text.getValue() 
				+"<br/>" + "Kontaktdaten des Anfragers: "
				+"<br/>" + VaadinSession.getCurrent().getAttribute(User.class).getFirstname()+ " " + VaadinSession.getCurrent().getAttribute(User.class).getLastname() 
				+"<br/>" + "Email: " + VaadinSession.getCurrent().getAttribute(User.class).getEmail() 
				+"<br/>" + "Handy: " + VaadinSession.getCurrent().getAttribute(User.class).getMobile() + "</span>"
				+"<br/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 20pt' >"
				+ "</span><br/><br/>Mit freundlichen Grüßen<br/>Ihr DHBW Wohungsbörsen-Team<p/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 8pt' >Anschrift:<br/>DHBW Karlsruhe<br/>Baden-Wuerttemberg Cooperative State University Karlsruhe<br />Erzbergerstraße 121 . 76133 Karlsruhe <br />Postfach 10 01 36 . 76231 Karlsruhe   <br />Telefon +49.721.9735-5 <br />Telefax +49.721.9735-600 <br />E-Mail: dreischer@dhbw-karlsruhe.de<br /><br/><br/>Ansprechpartner:<br/> <br />Dr. Anita Dreischer<br /><br/><b>Copyright DHBW Karlsruhe. Alle Rechte vorbehalten.</b></span>";
		
		String bodyAnfrager = "<span style='color: #000000' 'font-family: Arial, sans-serif''font-size: 16pt' >Sehr geehrte Nutzerin, sehr geehrter Nutzer,"
				+"<br/><br/>Sie haben eine Anfrage zum Angebot: \"" + requestedOffer.getTitle() + "\" in der DHBW-Wohnungsbörse versendet:"
				+"<br/><br/>" + text.getValue() 
				+"<br/>" + "Ihre Kontaktdaten: "
				+"<br/>" + VaadinSession.getCurrent().getAttribute(User.class).getFirstname()+ " " + VaadinSession.getCurrent().getAttribute(User.class).getLastname() 
				+"<br/>" + "Email: " + VaadinSession.getCurrent().getAttribute(User.class).getEmail() 
				+"<br/>" + "Handy: " + VaadinSession.getCurrent().getAttribute(User.class).getMobile() 
				+"<br/><br/>" + "Der Anbieter der Wohnung kann Sie nun kontaktieren." + "</span>"
				+"<br/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 20pt' >"
				+ "</span><br/><br/>Mit freundlichen Grüßen<br/>Ihr DHBW Wohungsbörsen-Team<p/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 8pt' >Anschrift:<br/>DHBW Karlsruhe<br/>Baden-Wuerttemberg Cooperative State University Karlsruhe<br />Erzbergerstraße 121 . 76133 Karlsruhe <br />Postfach 10 01 36 . 76231 Karlsruhe   <br />Telefon +49.721.9735-5 <br />Telefax +49.721.9735-600 <br />E-Mail: dreischer@dhbw-karlsruhe.de<br /><br/><br/>Ansprechpartner:<br/> <br />Dr. Anita Dreischer<br /><br/><b>Copyright DHBW Karlsruhe. Alle Rechte vorbehalten.</b></span>";
		
		//Email an Anfrager senden
		SendEMail.send(VaadinSession.getCurrent().getAttribute(User.class).getEmail(), "wohnungsboerse_dh@web.de", "Ihre Anfrage in der DHBW-Wohnungsbörse", bodyAnfrager);
		//Email an Anbieter senden
		SendEMail.send(requestedOffer.getOffer_idUser().getEmail(), "wohnungsboerse_dh@web.de", "Neue Anfrage in der DHBW-Wohnungsbörse", bodyAnbieter);
	
	}
	
}
