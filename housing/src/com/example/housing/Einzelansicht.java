package com.example.housing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import com.example.housing.data.model.Favorit;
import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.example.housing.data.model.Request;
import com.example.housing.data.provider.FavoritProvider;
import com.example.housing.data.provider.OfferProvider;
import com.example.housing.data.provider.RequestProvider;
import com.example.housing.data.provider.UserProvider;
import com.example.housing.utility.Format;
import com.example.housing.utility.SendEMail;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.*;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class Einzelansicht extends CustomHorizontalLayout implements View {
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout content;
	
	Offer angebot;

	public Einzelansicht(Offer einzelAngebot){
		this.angebot = einzelAngebot;
		content = super.initCustomHorizontalLayout();
		setContent();
	}

	public void setContent(){
		if(angebot.isInactive()){
			Label warn = new Label(FontAwesome.WARNING.getHtml() + "&nbsp;", ContentMode.HTML);
			warn.setStyleName("redText");
			Label inactive = new Label(" Bitte beachten Sie: Dieses Angebot ist gerade deaktiviert und kann von anderen Nutzeren nicht angesehen werden!");
			inactive.setImmediate(false);
			inactive.setWidth("1000px");
			inactive.setHeight("-1px");
			inactive.setStyleName("redText");
			
			HorizontalLayout warnInactive = new HorizontalLayout();
			warnInactive.addComponent(warn);
			warnInactive.addComponent(inactive);
			content.addComponent(warnInactive);
		}
		
		Label id = new Label("ID: "+ angebot.getIdOffer());
		id.setImmediate(false);
		id.setWidth("-1px");
		id.setHeight("-1px");
		content.addComponent(id);
		
		//titel	
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue(angebot.getTitle());
		title.addStyleName("title");
		content.addComponent(title);
		
		//Map-Button
        Button map = new Button("Karte anzeigen");
        map.setIcon(FontAwesome.MAP_MARKER);
        map.addStyleName("AnfrageButton");
        map.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				MapWindow w = new MapWindow(angebot);//neues Fenster mit Karte wird geöffnet
				UI.getCurrent().addWindow(w);
			}
        });
		
		//Adresse und Karte werden nur verifizierten Studenten bzw. Admin angezeigt
		if((boolean) VaadinSession.getCurrent().getSession().getAttribute("login") && ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getAccessLevel() != 0) { 
			
			//Vollständige Adresse
			Label lAdresse = new Label("Adresse");
			lAdresse.addStyleName("AbschnittLabel");
			content.addComponent(lAdresse);
			
			String street = angebot.getStreet();
			String zip = angebot.getZip();
			String city = angebot.getCity();
			
			GridLayout gridAdress = new GridLayout(2, 2);
			gridAdress.setWidth("33%");
			gridAdress.addComponent(new Label("Straße: "), 0, 0);
			gridAdress.addComponent(new Label(street), 1, 0);
			gridAdress.addComponent(new Label("Ort: "), 0, 1);
			gridAdress.addComponent(new Label(zip + " " + city), 1, 1);
			
			content.addComponent(gridAdress);
			content.addComponent(map);
			
		} else {
			//Kurze Adresse
			Label lTitel= new Label("in " + angebot.getCity());
			lTitel.addStyleName("AbschnittLabel");
			map.setEnabled(false);
			content.addComponent(lTitel);
			content.addComponent(map);
			content.addComponent(new Label());
			
			//Info
			Label warn = new Label(FontAwesome.WARNING.getHtml() + "&nbsp;", ContentMode.HTML);
			
			Label detailsAdresse = new Label(" Bitte beachten Sie: Die vollständige Adresse und die Kartenansicht sind nur für verifizierte Studierende der DH sichtbar!");
			detailsAdresse.setImmediate(false);
			detailsAdresse.setWidth("1000px");
			detailsAdresse.setHeight("-1px");
			
			HorizontalLayout warnAdresse = new HorizontalLayout();
			warnAdresse.addComponent(warn);
			warnAdresse.addComponent(detailsAdresse);
			content.addComponent(warnAdresse);
		 }
		
		//Button wird deaktiviert, wenn keine Standortangaben in der DB sind
		if(angebot.getLatitude()!=null && angebot.getLatitude()!=BigDecimal.valueOf(0.0)){
			//tue nichts
		}else{
			map.setEnabled(false);
		}
		
		//pictures
		GridLayout gridPictures = new GridLayout(8, 8);
		gridPictures.setMargin(false);
		content.addComponent(gridPictures);
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		
		Resource resource = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		Resource resource2 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		Resource resource3 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		Resource resource4 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		Resource resource5 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		
		switch(angebot.getPhotos().size()) {
			case 5:
				resource5 = new StreamResource(new StreamResource.StreamSource() {
					private static final long serialVersionUID = 1L;

					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(4).getPicture());
					}
				}, "Bild_5");
				
			case 4:
				resource4 = new StreamResource(new StreamResource.StreamSource() {
					private static final long serialVersionUID = 1L;

					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(3).getPicture());
					}
				}, "Bild_4");
				
			case 3:
				resource3 = new StreamResource(new StreamResource.StreamSource() {
					private static final long serialVersionUID = 1L;

					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(2).getPicture());
					}
				}, "Bild_3");
				
			case 2:
				resource2 = new StreamResource(new StreamResource.StreamSource() {
					private static final long serialVersionUID = 1L;

					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(1).getPicture());
					}
				}, "Bild_2");
				
			case 1:
				resource = new StreamResource(new StreamResource.StreamSource() {
					private static final long serialVersionUID = 1L;

					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(0).getPicture());
					}
				}, "Bild_1");
		}
		
		Image image = new Image("",resource);
		image.setWidth("388px");
		image.setHeight("314px");
		Image image2 = new Image("",resource2);
		image2.setWidth("169px");
		image2.setHeight("144px");
		Image image3 = new Image("",resource3);
		image3.setWidth("169px");
		image3.setHeight("144px");
		Image image4 = new Image("",resource4);
		image4.setWidth("169px");
		image4.setHeight("144px");
		Image image5 = new Image("",resource5);
		image5.setWidth("169px");
		image5.setHeight("144px");
		
		gridPictures.addComponent(image, 0, 0, 3, 3);
		gridPictures.addComponent(image2, 4, 0 ,5,1);
		gridPictures.addComponent(image3, 6, 0, 7,1);
		gridPictures.addComponent(image4, 4, 2, 5,3);
		gridPictures.addComponent(image5, 6, 2, 7,3);
		gridPictures.setWidth("40%");
		
		
	    //Plus-Button
        Button plus = new Button("Bilder vergrößern");
        plus.setIcon(FontAwesome.SEARCH_PLUS);
        plus.addStyleName("AnfrageButton");
        plus.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				ImageWindow w = new ImageWindow(angebot);
				UI.getCurrent().addWindow(w);
			}
        });
        if(!angebot.getPhotos().isEmpty()) {
        	content.addComponent(plus);
        }
        content.addComponent(new Label());
        
		final GridLayout gridInfos = new GridLayout(2,18); 
		content.addComponent(gridInfos);
		
		//Date	
	    DateField startDate = new DateField();
	    startDate.setValue(angebot.getStartDate());
	    startDate.setEnabled(false);
	    Label start = new Label("Verfügbar von:");
	    start.setWidth("388px");
	    gridInfos.addComponent(start, 0, 0);
	    gridInfos.addComponent(startDate, 1,0);

	    DateField endDate = new DateField();
	    endDate.setValue(angebot.getEndDate());
	    endDate.setEnabled(false);
	    gridInfos.addComponent(new Label("bis:"), 0,1);
	    gridInfos.addComponent(endDate, 1,1);
	    
	    //Size
		float sm = angebot.getSquareMetre();
		String sSm = Format.stringFormat(sm);
        gridInfos.addComponent(new Label("Größe:"),0,2);
        gridInfos.addComponent(new Label(sSm + " m²"),1 , 2);
        
        //Price
    	float price =  angebot.getPrice();
		String sPrice = Format.euroFormat(price);
		
        gridInfos.addComponent(new Label("Warmmiete:"),0,3);
        gridInfos.addComponent(new Label(sPrice + " €"),1 , 3);//bond
        float bond =  angebot.getBond();
		String sBond = Format.euroFormat(bond) + " €";
		
		//Kaution
        Label lBond = new Label("Kaution:");
        gridInfos.addComponent(lBond,0,4);
        gridInfos.addComponent(new Label(sBond),1 , 4);
        
        //IsShared       
    	int a = angebot.getType();
		String s = "";
		if(a ==1){
			s = "Wohnung";
		}else if(a ==2){
			s = "Zimmer";
		}else if(a == 3){
			s="WG-Zimmer";
		}
        gridInfos.addComponent(new Label("Art der Unterkunft:"), 0, 5);
        gridInfos.addComponent(new Label(s), 1, 5);
     
        //Number of Roomates 
        int number = angebot.getNumberOfRoommate();
        gridInfos.addComponent(new Label("Anzahl Mitbewohner:"), 0, 6);
        gridInfos.addComponent(new Label(Integer.toString(number)), 1, 6);
        
        //male / female   
        Label maleFemale = new Label(""); 
        int g = angebot.getGender();
        if(g == 1){
    	   maleFemale.setValue("egal");
        }
        else if (g == 2){
    	   maleFemale.setValue("männlich");
        }
        else if (g == 3)
        	maleFemale.setValue("weiblich");
        gridInfos.addComponent(new Label("Bevorzugtes Geschlecht:"), 0, 7);
        gridInfos.addComponent(maleFemale, 1,7);
        
        //Internet 
        CheckBox hasInternet = new CheckBox("");  
        hasInternet.setCaption("Internetanschluss vorhanden");
        gridInfos.addComponent(new Label("Ausstattung/Sonstiges:"), 0, 8);
        gridInfos.addComponent(hasInternet, 1,8);
        hasInternet.setEnabled(false);
        hasInternet.setValue( angebot.isInternet());
        
        //furnished       
        CheckBox isFurnished = new CheckBox(""); 
        isFurnished.setCaption("Möblierte Wohnung");
        gridInfos.addComponent(new Label(""), 0, 9);
        gridInfos.addComponent(isFurnished, 1,9);
        isFurnished.setEnabled(false);
        isFurnished.setValue( angebot.isFurnished());
        
        //kitchen       
        CheckBox useKitchen = new CheckBox(""); 
        useKitchen.setCaption("Küche vorhanden");
        gridInfos.addComponent(new Label(""), 0, 10);
        gridInfos.addComponent(useKitchen, 1, 10);
        useKitchen.setEnabled(false);
        useKitchen.setValue( angebot.isKitchen());
       
        //smoker
        CheckBox smokingAllowed = new CheckBox("");   
        smokingAllowed.setCaption("Raucher-Wohnung");
        gridInfos.addComponent(new Label(""), 0, 11);
        gridInfos.addComponent(smokingAllowed, 1,11);
        smokingAllowed.setEnabled(false);
        smokingAllowed.setValue(angebot.isSmoker());
        
        //pets       
        CheckBox petsAllowed = new CheckBox(""); 
        petsAllowed.setCaption("Haustiere erlaubt");
        gridInfos.addComponent(new Label(""), 0, 12);
        gridInfos.addComponent(petsAllowed, 1,12);
        petsAllowed.setEnabled(false);
        petsAllowed.setValue(angebot.isPets());
        
        //text
//        RichTextArea t = new RichTextArea();
        Label t = new Label();
        String text = angebot.getText();
        t.setEnabled(false);
        t.setContentMode(ContentMode.HTML);
        t.setValue(text);
        t.setEnabled(false);
        t.setWidth("338px");
        gridInfos.addComponent(new Label("Beschreibung:"), 0, 13);
        gridInfos.addComponent(t, 1,13);
    
        
        //Bearbeiten- und Löschen-Button für User
        if((boolean) VaadinSession.getCurrent().getSession().getAttribute("login") && ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getEmail().equals(angebot.getOffer_idUser().getEmail())){
        	HorizontalLayout userButtons = new HorizontalLayout();
        		
			Button change = new Button("Bearbeiten");
			change.setIcon(FontAwesome.PENCIL);
			change.addStyleName("BearbeitenButton");
			change.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
					String name = "AngebotErstellen";
					getUI().getNavigator().addView(name, new AngebotErstellen(angebot)); // momentan angezeigtes Angebot soll übergeben werden...
					getUI().getNavigator().navigateTo(name);
				}
			});
			
			Button delete = new Button("Angebot löschen");
			delete.setStyleName("loeschen");
			delete.setIcon(FontAwesome.TRASH_O);
			delete.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
					ConfirmDeleteWindow cdw = new ConfirmDeleteWindow(angebot);
					UI.getCurrent().addWindow(cdw);	
				}
			});
				
			userButtons.addComponent(change);
			userButtons.addComponent(delete);
			gridInfos.addComponent(userButtons, 0, 14);
        }
        
        //Deaktivieren- und Löschen-Button für Admin
        if((boolean) VaadinSession.getCurrent().getSession().getAttribute("login") && ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getAccessLevel() == 2){
    		HorizontalLayout adminButtons = new HorizontalLayout();
			Button deacitvate = new Button("Deaktivieren");
			deacitvate.setIcon(FontAwesome.SQUARE_O);
			deacitvate.addStyleName("BearbeitenButton");
			deacitvate.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
					angebot.setInactive(true);
					if(new OfferProvider().alterOffer(angebot)) {	
						String deactivationMessage = "<meta charset='utf-8'/><img src='http://193.196.7.216:8080/housing/APP/connector/0/12/source/dh.PNG'/><br/><br/><span style='color: #000000' 'font-family: Arial, sans-serif''font-size: 16pt' >Sehr geehrte Nutzerin, sehr geehrter Nutzer,"
								+"<br/><br/>Ihr Angebot \"" + angebot.getTitle() + "\" in der Wohnungsbörse der DHBW wurde von einem Portal-Administrator deaktiviert."
								+"<br/><br/>Dies kann verschiedene Gründe haben:"
								+"<br/>- Es liegen Bedenken über die Aktualität des Angebotes vor."
								+"<br/>- Es wurde Ihnen eine Aufforderung zur Anpassung des Angebotes geschickt, die Sie noch bearbeiten müssen."
								+"<br/>- Sie reagieren nicht auf Anfragen oder Benachrichtigungen durch die Portal-Administratoren und scheinen das betreffende Angebot aktuell nicht mehr zu pflegen."
								+"</span>"
								+"<br/><br/>Mit freundlichen Grüßen<br/>Ihr DHBW Wohnungsbörsen-Team<p/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 8pt' >Anschrift:<br/>DHBW Karlsruhe<br/>Baden-Wuerttemberg Cooperative State University Karlsruhe<br />Erzbergerstraße 121 . 76133 Karlsruhe <br />Postfach 10 01 36 . 76231 Karlsruhe   <br />Telefon +49.721.9735-5 <br />Telefax +49.721.9735-600 <br />E-Mail: dreischer@dhbw-karlsruhe.de<br /><br/><br/>Ansprechpartner:<br/> <br />Dr. Anita Dreischer<br /><br/><b>Copyright DHBW Karlsruhe. Alle Rechte vorbehalten.</b></span>";
						
						SendEMail.send(angebot.getOffer_idUser().getEmail(), "wohnungsboerse_dh@web.de", "Deaktivierung Ihres Angebots in der DHBW-Wohnungsbörse", deactivationMessage);
						
						Notification success = new Notification("Das Angebot wurde deaktiviert.", Type.HUMANIZED_MESSAGE);
						success.setStyleName("success");
						success.setDelayMsec(300);
						success.show(Page.getCurrent());
						
						Offer o = new OfferProvider().findById(angebot.getIdOffer());
						
						String name = "Einzelansicht";
						getUI().getNavigator().addView(name, new Einzelansicht(o)); // momentan angezeigtes Angebot soll übergeben werden...
						getUI().getNavigator().navigateTo(name);
						
					} else {
						Notification failDB = new Notification("Das Angebot konnte nicht deaktiviert werden.", Type.HUMANIZED_MESSAGE);
						failDB.setStyleName("failure");
						failDB.setDelayMsec(300);
						failDB.show(Page.getCurrent());
					}
				}
			});
			
			Button reactivate = new Button("Reaktivieren");
			reactivate.setIcon(FontAwesome.CHECK_SQUARE_O);
			reactivate.addStyleName("BearbeitenButton");
			reactivate.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
					angebot.setInactive(false);
					if(new OfferProvider().alterOffer(angebot)) {	
						
						String reactivationMessage = "<meta charset='utf-8'/><img src='http://193.196.7.216:8080/housing/APP/connector/0/12/source/dh.PNG'/><br/><br/><span style='color: #000000' 'font-family: Arial, sans-serif''font-size: 16pt' >Sehr geehrte Nutzerin, sehr geehrter Nutzer,"
								+"<br/><br/>Ihr Angebot \"" + angebot.getTitle() + "\" in der Wohnungsbörse der DHBW wurde von einem Portal-Administrator reaktiviert und kann nun wieder von allen Nutzern gesehen werden."
								+"</span>"
								+"<br/><br/>Mit freundlichen Grüßen<br/>Ihr DHBW Wohnungsbörsen-Team<p/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 8pt' >Anschrift:<br/>DHBW Karlsruhe<br/>Baden-Wuerttemberg Cooperative State University Karlsruhe<br />Erzbergerstraße 121 . 76133 Karlsruhe <br />Postfach 10 01 36 . 76231 Karlsruhe   <br />Telefon +49.721.9735-5 <br />Telefax +49.721.9735-600 <br />E-Mail: dreischer@dhbw-karlsruhe.de<br /><br/><br/>Ansprechpartner:<br/> <br />Dr. Anita Dreischer<br /><br/><b>Copyright DHBW Karlsruhe. Alle Rechte vorbehalten.</b></span>";
						
						SendEMail.send(angebot.getOffer_idUser().getEmail(), "wohnungsboerse_dh@web.de", "Reaktivierung Ihres Angebots in der DHBW-Wohnungsbörse", reactivationMessage);
						
						Notification success = new Notification("Das Angebot wurde wieder aktiviert.", Type.HUMANIZED_MESSAGE);
						success.setStyleName("success");
						success.setDelayMsec(300);
						success.show(Page.getCurrent());
						
						Offer o = new OfferProvider().findById(angebot.getIdOffer());
						
						String name = "Einzelansicht";
						getUI().getNavigator().addView(name, new Einzelansicht(o)); // momentan angezeigtes Angebot soll übergeben werden...
						getUI().getNavigator().navigateTo(name);
						
					} else {
						Notification failDB = new Notification("Das Angebot konnte nicht reaktiviert werden.", Type.HUMANIZED_MESSAGE);
						failDB.setStyleName("failure");
						failDB.setDelayMsec(300);
						failDB.show(Page.getCurrent());
					}
				}
			});
			
			Button delete = new Button("Angebot löschen");
			delete.setIcon(FontAwesome.TRASH_O);
			delete.addStyleName("loeschen");
			delete.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
					if(new OfferProvider().removeOffer(angebot)) {
						
						String deleteMessage = "<meta charset='utf-8'/><img src='http://193.196.7.216:8080/housing/APP/connector/0/12/source/dh.PNG'/><br/><br/><span style='color: #000000' 'font-family: Arial, sans-serif''font-size: 16pt' >Sehr geehrte Nutzerin, sehr geehrter Nutzer,"
								+"<br/><br/>Ihr Angebot \"" + angebot.getTitle() + "\" in der Wohnungsbörse der DHBW wurde von einem Portal-Administrator gelöscht."
								+"<br/><br/>Dies kann verschiedene Gründe haben:"
								+"<br/>- Es liegen Bedenken über die Korrektheit oder Legalität des Angebotes vor."
								+"<br/>- Geforderte inhaltliche Anpassungen des Wohnungsangebotes sind nach mehrmaliger Aufforderung nicht erfolgt."
								+"<br/>- Sie reagieren nicht auf Anfragen oder Benachrichtigungen vonseiten der Portal-Administratoren und Sie scheinen Ihr Profil sowie Ihre Angebote in der DHBW-Wohnungsbörse nicht mehr zu pflegen."
								+"</span>"
								+"<br/><br/>Mit freundlichen Grüßen<br/>Ihr DHBW Wohnungsbörsen-Team<p/><span style='color: #e2001a' 'font-family: Arial, sans-serif''font-size: 8pt' >Anschrift:<br/>DHBW Karlsruhe<br/>Baden-Wuerttemberg Cooperative State University Karlsruhe<br />Erzbergerstraße 121 . 76133 Karlsruhe <br />Postfach 10 01 36 . 76231 Karlsruhe   <br />Telefon +49.721.9735-5 <br />Telefax +49.721.9735-600 <br />E-Mail: dreischer@dhbw-karlsruhe.de<br /><br/><br/>Ansprechpartner:<br/> <br />Dr. Anita Dreischer<br /><br/><b>Copyright DHBW Karlsruhe. Alle Rechte vorbehalten.</b></span>";
						
						SendEMail.send(angebot.getOffer_idUser().getEmail(), "wohnungsboerse_dh@web.de", "Löschung Ihres Angebots in der DHBW-Wohnungsbörse", deleteMessage);
						
						Notification not = new Notification("Das Angebot wurde gelöscht und aus der Datenbank entfernt.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
						not.setStyleName("success");
						not.setDelayMsec(300);
						not.show(Page.getCurrent());
						
						List<Offer> allOffers = new OfferProvider().getAllOffers(((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getAccessLevel());
						
						String name = "Angebotsliste";
						getUI().getNavigator().addView(name, new Suchergebnis(allOffers));
						getUI().getNavigator().navigateTo(name);
						
					} else {
						Notification failDB = new Notification("Das Angebot konnte nicht gelöscht werden.", Type.HUMANIZED_MESSAGE);
						failDB.setStyleName("failure");
						failDB.setDelayMsec(300);
						failDB.show(Page.getCurrent());
					}
				}
			});
			
			if(angebot.isInactive()) {
				adminButtons.addComponent(reactivate);
			} else {
				adminButtons.addComponent(deacitvate);
			}
			adminButtons.addComponent(delete);
			gridInfos.addComponent(adminButtons, 0 , 15);
		
    	}
        
        HorizontalLayout buttons = new HorizontalLayout();
        
        //Anfrage-Button
        Button anfrage = new Button("Anfrage");
        anfrage.addStyleName("AnfrageButton");
        anfrage.setIcon(FontAwesome.MAIL_FORWARD);
        
        //Anbieter kontaktieren-Button
        Button anbieter = new Button("Anbieter kontaktieren");
        anbieter.addStyleName("AnfrageButton");
        anbieter.setIcon(FontAwesome.MAIL_FORWARD);
        
        //Anfrage-Button (für User) oder Anbieter kontaktieren-Button (für Admin)
        if(VaadinSession.getCurrent().getSession().getAttribute("login").equals(false) || ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getAccessLevel()!=2){
        	buttons.addComponent(anfrage);
        } else {
        	buttons.addComponent(anbieter);	
        }
        
        //Anfrage-Button deaktivieren bei deaktivierten Angeboten
        if(angebot.isInactive()){
        	anfrage.setEnabled(false);
        }
        
        anfrage.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				if(!(boolean) VaadinSession.getCurrent().getSession().getAttribute("login")) { //nicht eingeloggt
					Notification not = new Notification("Sie müssen sich als verifizierte DH Studentin / verifizierter DH-Student einloggen, um eine Anfrage zu einem Wohnungsangebot stellen zu können!",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
					not.setStyleName("failure");
					not.setDelayMsec(300);
					not.show(Page.getCurrent());
				} else if(((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getAccessLevel() != 1) { //nicht als DH-Student verifiziert
					Notification not = new Notification("Sie müssen sich als DH-Studentin / DH-Student verifizieren, um eine Anfrage zu einem Wohnungsangebot stellen zu können!",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
					not.setDelayMsec(300);
					not.setStyleName("failure");
					not.show(Page.getCurrent());
				} else if((boolean) VaadinSession.getCurrent().getSession().getAttribute("login")) { //eingeloggt als DH-Student
		        	  if(new RequestProvider().requestExists((User) VaadinSession.getCurrent().getSession().getAttribute("user"), angebot)) { //Anfrage bereits gesendet
		        		  Notification not = new Notification("Sie haben den Anbieter bereits kontaktiert",Type.HUMANIZED_MESSAGE);
		        		  not.setDelayMsec(300);
		        		  not.setStyleName("failure");
		        		  not.show(Page.getCurrent());
		              } else { //Anfrage senden
		            	  String name = "Anfrageformular";
		            	  getUI().getNavigator().addView(name, new Anfrageformular(angebot));
		            	  getUI().getNavigator().navigateTo(name);
		              }
				}
			}
        });
        
        anbieter.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				String name = "AdminanfrageWohnung";
				getUI().getNavigator().addView(name, new AdminanfrageWohnung(angebot));
				getUI().getNavigator().navigateTo(name);
			}
        });
        
		final FavoritProvider fp = new FavoritProvider();
	    
        //Favoriten-Button
        if(VaadinSession.getCurrent().getSession().getAttribute("login").equals(true) && !fp.favoritExists((User) VaadinSession.getCurrent().getSession().getAttribute("user"), angebot) && ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getAccessLevel()!=2) { //Favorit hinzufügen
        
        	Button favorit = new Button("Favorit hinzufügen");
        	favorit.setIcon(FontAwesome.PLUS_SQUARE_O);
        	favorit.addStyleName("AnfrageButton");
        	buttons.addComponent(favorit);
        	
        	favorit.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
        			
        			Favorit newFavorit = new Favorit();
        			newFavorit.setFavorit_idOffer(angebot);
        			newFavorit.setFavorit_idUser((User) VaadinSession.getCurrent().getSession().getAttribute("user"));
        			
        			new FavoritProvider().addFavorit(newFavorit);
        			
        			String name = "Meine Favoriten";
					getUI().getNavigator().addView(name, new Favoriten()); // momentan angezeigtes Angebot soll übergeben werden...
					getUI().getNavigator().navigateTo(name);
					
        			Notification not = new Notification("Das Angebot wurde zu Ihren Favoriten hinzugefügt.");
        			not.setStyleName("success");
        			not.setDelayMsec(300);
        			not.show(Page.getCurrent());
        		}
        	}); 
    	
        } else if(VaadinSession.getCurrent().getSession().getAttribute("login").equals(true) && fp.favoritExists((User) VaadinSession.getCurrent().getSession().getAttribute("user"), angebot)&& ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getAccessLevel()!=2) { //Favorit entfernen
        	Button removeFavorit = new Button("Favorit entfernen");
        	removeFavorit.setIcon(FontAwesome.TRASH_O);
        	removeFavorit.addStyleName("AnfrageButton");
        	buttons.addComponent(removeFavorit);
        	
        	removeFavorit.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
        			
        		    Favorit fav;
        			fav = fp.findByUserOffer((User) VaadinSession.getCurrent().getSession().getAttribute("user"), angebot);
        	        new FavoritProvider().removeFavorit(fav);
        	        
        	        String name = "Meine Favoriten";
        	        getUI().getNavigator().addView(name, new Favoriten()); // momentan angezeigtes Angebot soll übergeben werden...
					getUI().getNavigator().navigateTo(name);
        			
        			Notification not = new Notification("Das Angebot wurde aus Ihren Favoriten entfernt.");
        			not.setStyleName("success");
        			not.setDelayMsec(300);
        			not.show(Page.getCurrent());
        		}	
        	}); 
        }
        
        gridInfos.addComponent(buttons, 1, 14);
        
        //Anzeigen, wenn man Anbieter bereits kontaktiert hat
        if((boolean) VaadinSession.getCurrent().getSession().getAttribute("login") && ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getAccessLevel()!=2) {
        	
        	User u = (User) VaadinSession.getCurrent().getSession().getAttribute("user");
        	User updatedU = new UserProvider().findById(u.getIdUser()); //der in der Session hinterlegte User hat u.U. noch nicht die aktuellen Liste von Requests
            List<Request> requests;
            requests = updatedU.getRequests(); 

        	  boolean b = false;
        	  int in = 0;
              for(int i = 0; i<requests.size();i++) {
            	  if(requests.get(i).getRequest_idOffer().getIdOffer()==angebot.getIdOffer()) {
            		  b = true;
            		  in = i;
            	  }
              }
              
              if(b) {
            	  
            	Label anfrageTitel = new Label("Gesendete Anfrage");
            	anfrageTitel.setStyleName("ImportantTitle");
                gridInfos.addComponent(anfrageTitel,0,16);
                
                Label anfrageText = new Label("Sie haben folgenden Text an den Anbieter geschickt:");
                gridInfos.addComponent(anfrageText,0,17);
                
                TextArea anfrageMessage = new TextArea();
                String message = requests.get(in).getMessage();
               
                anfrageMessage.setValue(message);
                anfrageMessage.setEnabled(false);
                anfrageMessage.setWidth("338px");
                
                gridInfos.addComponent(anfrageMessage,1,17);
                
              }
        }
        content.addComponent(new Label());
        
        if((boolean) VaadinSession.getCurrent().getSession().getAttribute("login") && ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getAccessLevel() == 2) {
			
        	HorizontalLayout vl = new HorizontalLayout();
        	
        	Label user = new Label("Dieses Angebot wurde von folgendem Nutzer eingestellt: User-ID "+ angebot.getOffer_idUser().getIdUser());
  	        vl.addComponent(user);
  	        user.setStyleName("footer");
	        
			Button kontaktformularLink = new Button();
			kontaktformularLink.setStyleName("link");
			kontaktformularLink.setCaption("User-Profil anzeigen");
			kontaktformularLink.setImmediate(false);
			kontaktformularLink.setWidth("-1px");
			kontaktformularLink.setHeight("-1px");
			kontaktformularLink.addClickListener(new Button.ClickListener(){
				private static final long serialVersionUID = 1L;
				public void buttonClick(ClickEvent event) {
					String name = "UserProfil";
					getUI().getNavigator().addView(name, new UserProfil(angebot.getOffer_idUser()));
					getUI().getNavigator().navigateTo(name);
				}
			});
			vl.addComponent(kontaktformularLink);
			content.addComponent(vl);
        }
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}
