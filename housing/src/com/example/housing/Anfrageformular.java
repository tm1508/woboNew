package com.example.housing;

import org.jsoup.Jsoup;

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
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;

public class Anfrageformular extends CustomHorizontalLayout implements View{
	private static final long serialVersionUID = 1L;

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
		
		content = super.initCustomHorizontalLayout();
		setContent();
	}

	public void setContent() {
		
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
		//sendButton.setIcon(FontAwesome.ENVELOPE_SQUARE);
		sendButton.setIcon(FontAwesome.SEND);
		sendButton.setCaption("Anfrage abschicken");
		//button.setImmediate(true);
		sendButton.setWidth("-1px");
		sendButton.setHeight("-1px");
		content.addComponent(sendButton);
		sendButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				
				if(new RequestProvider().requestExists((User) (VaadinSession.getCurrent().getSession().getAttribute("user")), requestedOffer) == false){//Anfrage existiert noch nicht
					//Werte in der DB speichern
					safeToDB();
					
					//E-Mail an den Nutzer senden
					sendEMail();
					
					//Navigation zurück zum Angebot
					String name = "AngebotAnzeigen";
					getUI().getNavigator().addView(name, new Einzelansicht(requestedOffer));
					getUI().getNavigator().navigateTo(name);
					
					Notification not = new Notification("Die Anfrage war erfolgreich. Der Anbieter kann Sie nun kontaktieren.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
					not.setStyleName("success");
					not.setDelayMsec(300);
					not.show(Page.getCurrent());
					
				} else { //eine Anfrage von diesem User für dieses Angebot existiert bereits
					
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
        String t = text.getValue().replace("<br>", "\n");
        String tx = Jsoup.parse(t).text();
		request.setMessage(tx);
		request.setRequest_idUser((User) VaadinSession.getCurrent().getSession().getAttribute("user"));
		request.setRequest_idOffer(requestedOffer);
		
		new RequestProvider().addRequest(request);
		
	}
	
	protected void sendEMail() {
		String bodyAnbieter = "<meta charset='utf-8'/><img src='http://193.196.7.216:8080/housing/APP/connector/0/12/source/dh.PNG'/><br/><br/><span style='color: #000000' 'font-family: Arial, sans-serif''font-size: 16pt' >Sehr geehrte Nutzerin, sehr geehrter Nutzer,"
				+"<br/><br/>Sie haben eine Anfrage zu Ihrem Angebot \"" + requestedOffer.getTitle() + "\" in der Wohnungsbörse der DHBW erhalten:"
				+"<br/><br/>" + text.getValue() 
				+"<br/>" + "Kontaktdaten des Anfragenden: "
				+"<br/>" + ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getFirstname()+ " " + ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getLastname() 
				+"<br/>" + "Email: " + ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getEmail() 
				+"<br/>" + "Handy: " + ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getMobile() + "</span>"
				+"<br/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 20pt' >"
				+ "</span><br/><br/>Mit freundlichen Grüßen<br/>Ihr DHBW Wohungsbörsen-Team<p/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 8pt' >Anschrift:<br/>DHBW Karlsruhe<br/>Baden-Wuerttemberg Cooperative State University Karlsruhe<br />Erzbergerstraße 121 . 76133 Karlsruhe <br />Postfach 10 01 36 . 76231 Karlsruhe   <br />Telefon +49.721.9735-5 <br />Telefax +49.721.9735-600 <br />E-Mail: dreischer@dhbw-karlsruhe.de<br /><br/><br/>Ansprechpartner:<br/> <br />Dr. Anita Dreischer<br /><br/><b>Copyright DHBW Karlsruhe. Alle Rechte vorbehalten.</b></span>";
		
		String bodyAnfrager = "<meta charset='utf-8'/><img src='http://193.196.7.216:8080/housing/APP/connector/0/12/source/dh.PNG'/><br/><br/><span style='color: #000000' 'font-family: Arial, sans-serif''font-size: 16pt' >Sehr geehrte Nutzerin, sehr geehrter Nutzer,"
				+"<br/><br/>Sie haben eine Anfrage zum Angebot \"" + requestedOffer.getTitle() + "\" in der DHBW-Wohnungsbörse versendet:"
				+"<br/><br/>" + text.getValue() 
				+"<br/>" + "Ihre Kontaktdaten: "
				+"<br/>" + ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getFirstname()+ " " + ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getLastname() 
				+"<br/>" + "Email: " + ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getEmail() 
				+"<br/>" + "Handy: " + ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getMobile() 
				+"<br/><br/>" + "Der Anbieter der Wohnung kann Sie nun kontaktieren." + "</span>"
				+"<br/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 20pt' >"
				+ "</span><br/><br/>Mit freundlichen Grüßen<br/>Ihr DHBW Wohungsbörsen-Team<p/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 8pt' >Anschrift:<br/>DHBW Karlsruhe<br/>Baden-Wuerttemberg Cooperative State University Karlsruhe<br />Erzbergerstraße 121 . 76133 Karlsruhe <br />Postfach 10 01 36 . 76231 Karlsruhe   <br />Telefon +49.721.9735-5 <br />Telefax +49.721.9735-600 <br />E-Mail: dreischer@dhbw-karlsruhe.de<br /><br/><br/>Ansprechpartner:<br/> <br />Dr. Anita Dreischer<br /><br/><b>Copyright DHBW Karlsruhe. Alle Rechte vorbehalten.</b></span>";
		
		//Email an Anfrager senden
		SendEMail.send(((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getEmail(), "wohnungsboerse_dh@web.de", "Ihre Anfrage in der DHBW-Wohnungsbörse", bodyAnfrager);
		//Email an Anbieter senden
		SendEMail.send(requestedOffer.getOffer_idUser().getEmail(), "wohnungsboerse_dh@web.de", "Neue Anfrage zu Ihrem Angebot in der DHBW-Wohnungsbörse", bodyAnbieter);
	
	}
	
}
