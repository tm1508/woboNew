package com.example.housing;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Request;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.RequestProvider;
import com.example.housing.utility.SendEMail;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;

@SuppressWarnings("serial")
public class Anfrageformular extends VerticalLayout implements View{

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
		
		setMargin(true);
		
		//Navigation hinzufuegen
		Navigation nav = new Navigation();
		nav.setWidth("100%");
		nav.addStyleName("navigation");
		addComponent(nav);
		
		/*//falls der Benutzer eingelogt ist verändert sich die Navigation
		if(VaadinSession.getCurrent().getAttribute("login").equals(true)){
			nav.setVisible(true);
			navPublic.setVisible(false);
		}else{
			nav.setVisible(false);
			navPublic.setVisible(true);
		}*/
		
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
		sendButton.setCaption("Anfrage abschicken");
		//button.setImmediate(true);
		sendButton.setWidth("-1px");
		sendButton.setHeight("-1px");
		content.addComponent(sendButton);
		//Abschließen der Registrierung
		sendButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				if(new RequestProvider().requestExists(VaadinSession.getCurrent().getAttribute(User.class), requestedOffer) == false){//der Nutzer existiert noch nicht
					//Werte in der DB speichern
					safeToDB();
					
					//E-Mail an den Nutzer senden
					sendEMail();
					
					//TODO: Navigation
					//Navigation zur Startseite
					String name = "AngebotAnzeigen";
					getUI().getNavigator().addView(name, new Einzelansicht(requestedOffer));
					getUI().getNavigator().navigateTo(name);
					
					Notification.show("Die Anfrage war erfolgreich. Der Anbieter kann Sie nun kontaktieren.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
				}else{//eine Anfrage von diesem User für dieses Angebot existiert bereits
					Notification.show("Sie hatten bereits eine Anfrage für dieses Angebot abgegeben.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
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
				+"<br/><br/>Sie haben eine Anfrage zu einem Ihrer Angebote in der Wohnungsbörse der DHBW erhalten:"
				+"<br/><br/>" + text.getValue() + "</span>"
				+"<br/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 20pt' >"
				+ "</span><br/><br/>Mit freundlichen Grüßen<br/>Ihr DHBW Wohungsbörsen-Team<p/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 8pt' >Anschrift:<br/>DHBW Karlsruhe<br/>Baden-Wuerttemberg Cooperative State University Karlsruhe<br />Erzbergerstraße 121 . 76133 Karlsruhe <br />Postfach 10 01 36 . 76231 Karlsruhe   <br />Telefon +49.721.9735-5 <br />Telefax +49.721.9735-600 <br />E-Mail: dreischer@dhbw-karlsruhe.de<br /><br/><br/>Ansprechpartner:<br/> <br />Dr. Anita Dreischer<br /><br/><b>Copyright DHBW Karlsruhe. Alle Rechte vorbehalten.</b></span>";
		
		SendEMail.send(requestedOffer.getOffer_idUser().getEmail(), "wohnungsboerse_dh@web.de", "Neue Anfrage in der DHBW-Wohnungsbörse", bodyAnbieter);
	}
	
}
