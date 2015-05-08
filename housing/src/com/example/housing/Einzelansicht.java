package com.example.housing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.swing.GroupLayout.Alignment;

import com.example.housing.data.model.Favorit;
import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.example.housing.data.model.Request;
import com.example.housing.data.provider.FavoritProvider;
import com.example.housing.data.provider.PhotoProvider;
import com.example.housing.data.provider.RequestProvider;
import com.example.housing.utility.Format;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.MarkerDragListener;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.*;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class Einzelansicht.
 */
public class Einzelansicht extends HorizontalLayout implements View {
	
	/** The content. */
	VerticalLayout content;
	 static Double lat = 0.0;
     static Double lon = 0.0;
	
	/** The angebot. */
	Offer angebot;
	
	/**
	 * Instantiates a new einzelansicht.
	 *
	 * @param einzelAngebot the einzel angebot
	 */
	public Einzelansicht(Offer einzelAngebot){
		this.angebot = einzelAngebot;
		
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


	/**
	 * Sets the content.
	 */
	@SuppressWarnings("deprecation")
	public void setContent(){
		
		//titel	
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue(angebot.getTitle());
		title.addStyleName("title");
		content.addComponent(title);
				
		Label lTitel= new Label(" Ort: " +angebot.getCity());
		lTitel.addStyleName("ImportantTitle");
		content.addComponent(title );
		content.addComponent(lTitel);
		
		//Adresse wird nur verifizierten Studenten bzw. Admin angezeigt
		if(VaadinSession.getCurrent().getAttribute("login").equals(true) && VaadinSession.getCurrent().getAttribute(User.class).getAccessLevel() != 0) { 
				 
			//adresse
			String street = angebot.getStreet();
			String zip = angebot.getZip();
			String city = angebot.getCity();
			Label lAdress = new Label("Straße: " + street + "  PLZ: " + zip + "  Ort: " + city);
			
			content.addComponent(lAdress);
			
		} else {
		
			String zip = angebot.getZip();
			 
			Label lAdress = new Label("PLZ: " + zip + "          Die volle Adresse ist nur für verifizierte Studenten sichtbar.");
			 
			content.addComponent(lAdress);
			 
		 }
		

	    //Map-Button
        Button map = new Button("Karte anzeigen");
        map.setIcon(FontAwesome.MAP_MARKER);
        map.addStyleName("AnfrageButton");
        map.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				MapWindow w = new MapWindow(angebot);//neues Fenster mit Karte wird geöffnet
				UI.getCurrent().addWindow(w);
			}
        });
        content.addComponent(map);
        
        //Button wird deaktiviert, wenn der Nutzer kein DH Stud. ist
		if(VaadinSession.getCurrent().getAttribute("login").equals(true) && VaadinSession.getCurrent().getAttribute(User.class).getAccessLevel() != 0) {
			//tue nichts
		}else{
			map.setEnabled(false);
			Label l = new Label("Die Kartenansicht ist nur für verifizierte DH-Studenten verfügbar.");
			content.addComponent(l);
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
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(4).getPicture());
					}
				}, "Bild_5");
				
			case 4:
				
				resource4 = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(3).getPicture());
					}
				}, "Bild_4");
				
			case 3:
				
				resource3 = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(2).getPicture());
					}
				}, "Bild_3");
				
			case 2:
				
				resource2 = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(1).getPicture());
					}
				}, "Bild_2");
				
			case 1:
				
				resource = new StreamResource(new StreamResource.StreamSource() {
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
			public void buttonClick(ClickEvent event) {
				ImageWindow w = new ImageWindow(angebot);
				UI.getCurrent().addWindow(w);
			}
        });
        content.addComponent(plus);
        
		final GridLayout gridInfos = new GridLayout(2,17); 
	//	gridInfos.setWidth("60%");
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
        gridInfos.addComponent(new Label(sPrice + " €"),1 , 3);
        
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
        gridInfos.addComponent(new Label("Art der Unterkunft:"), 0, 4);
        gridInfos.addComponent(new Label(s), 1, 4);
     
        //Number of Roomates 
        int number = angebot.getNumberOfRoommate();
        gridInfos.addComponent(new Label("Anzahl Mitbewohner:"), 0, 5);
        gridInfos.addComponent(new Label(Integer.toString(number)), 1, 5);
        
        //Internet 
        CheckBox hasInternet = new CheckBox("");       
        gridInfos.addComponent(new Label("Internetanschluss vorhanden:"), 0, 6);
        gridInfos.addComponent(hasInternet, 1,6);
        hasInternet.setEnabled(false);
        hasInternet.setValue( angebot.isInternet());
        
        //furnished       
        CheckBox isFurnished = new CheckBox("");       
        gridInfos.addComponent(new Label("Möblierte Wohnung:"), 0, 7);
        gridInfos.addComponent(isFurnished, 1,7);
        isFurnished.setEnabled(false);
        isFurnished.setValue( angebot.isFurnished());
        
        //kitchen       
        CheckBox useKitchen = new CheckBox("");       
        gridInfos.addComponent(new Label("Küche vorhanden:"), 0, 8);
        gridInfos.addComponent(useKitchen, 1,8);
        useKitchen.setEnabled(false);
        useKitchen.setValue( angebot.isKitchen());
       
        //smoker
        CheckBox smokingAllowed = new CheckBox("");       
        gridInfos.addComponent(new Label("Raucher-Wohnung:"), 0, 9);
        gridInfos.addComponent(smokingAllowed, 1,9);
        smokingAllowed.setEnabled(false);
        smokingAllowed.setValue(angebot.isSmoker());
        
        //pets       
        CheckBox petsAllowed = new CheckBox("");       
        gridInfos.addComponent(new Label("Haustiere erlaubt:"), 0, 10);
        gridInfos.addComponent(petsAllowed, 1,10);
        petsAllowed.setEnabled(false);
        petsAllowed.setValue(angebot.isPets());
        
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
        gridInfos.addComponent(new Label("Bevorzugtes Geschlecht:"), 0, 11);
        gridInfos.addComponent(maleFemale, 1,11);

        
        //bond
        float bond =  angebot.getBond();
		String sBond = Format.euroFormat(bond) + " €";
		
        Label lBond = new Label("Kaution:");
        gridInfos.addComponent(lBond,0,12);
        gridInfos.addComponent(new Label(sBond),1 , 12);
        
        
        //text
        TextArea t = new TextArea();
        String text = angebot.getText();
       
        t.setValue(text);
        t.setEnabled(false);
        t.setWidth("338px");
        gridInfos.addComponent(new Label("Beschreibung:"), 0, 13);
        gridInfos.addComponent(t, 1,13);
    
        
        //Bearbeiten- und Löschen-Button
        if(VaadinSession.getCurrent().getAttribute("login").equals(true)){
        	if(VaadinSession.getCurrent().getAttribute(User.class).getEmail().equals(angebot.getOffer_idUser().getEmail())|| VaadinSession.getCurrent().getAttribute(User.class).getAccessLevel()==2){
        		
        		HorizontalLayout userButtons = new HorizontalLayout();
        		
				Button change = new Button("Bearbeiten");
				change.setIcon(FontAwesome.PENCIL);
				change.addStyleName("BearbeitenButton");
				change.addClickListener(new Button.ClickListener() {
					public void buttonClick(ClickEvent event) {
						String name = "AngebotErstellen";
						getUI().getNavigator().addView(name, new AngebotErstellen(angebot)); // momentan angezeigtes Angebot soll übergeben werden...
						getUI().getNavigator().navigateTo(name);
					}
				});
				
				Button delete = new Button("Angebot löschen");
				delete.setIcon(FontAwesome.TRASH_O);
				delete.addStyleName("BearbeitenButton");
				delete.addClickListener(new Button.ClickListener() {
					public void buttonClick(ClickEvent event) {
						ConfirmDeleteWindow cdw = new ConfirmDeleteWindow(angebot);
						UI.getCurrent().addWindow(cdw);	
					}
				});
				
				userButtons.addComponent(change);
				userButtons.addComponent(delete);
				
				gridInfos.addComponent(userButtons, 0 , 14);
			
        	}
        }
        
        HorizontalLayout buttons = new HorizontalLayout();
        
        //Anfrage-Button
        Button anfrage = new Button("Anfrage");
        anfrage.addStyleName("AnfrageButton");
        anfrage.setIcon(FontAwesome.MAIL_FORWARD);
        
        Button anbieter = new Button("Anbieter kontaktieren");
        anbieter.addStyleName("AnfrageButton");
        anfrage.setIcon(FontAwesome.MAIL_FORWARD);
        
        if(VaadinSession.getCurrent().getAttribute("login").equals(false)|| VaadinSession.getCurrent().getAttribute(User.class).getAccessLevel()!=2){
        buttons.addComponent(anfrage);
        }else{
        buttons.addComponent(anbieter);	
        }
        if(angebot.isInactive()){
        	anfrage.setEnabled(false);
        }
        
        anfrage.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				if(VaadinSession.getCurrent().getAttribute("login").equals(false)) {
					Notification not = new Notification("Sie müssen sich als verifizierter DH-Student einloggen, um eine Anfrage zu einem Wohnungsangebot stellen zu können!",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
					not.setStyleName("failure");
					not.setDelayMsec(300);
					not.show(Page.getCurrent());
				} else if (VaadinSession.getCurrent().getAttribute(User.class).getAccessLevel() != 1) {
					Notification not = new Notification("Sie müssen sich als DH-Student verifizieren, um eine Anfrage zu einem Wohnungsangebot stellen zu können!",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
					not.setDelayMsec(300);
					not.setStyleName("failure");
					not.show(Page.getCurrent());
				} else if(VaadinSession.getCurrent().getAttribute("login").equals(true)){
		        	 
		        	  if(new RequestProvider().requestExists(VaadinSession.getCurrent().getAttribute(User.class), angebot)){
		        		
		        		  Notification not = new Notification("Sie haben den Anbieter bereits kontaktiert",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
							not.setDelayMsec(300);
							not.setStyleName("failure");
							not.show(Page.getCurrent());//+re.getMessage());
		              	
		              } else {
					String name = "Anfrageformular";
					getUI().getNavigator().addView(name, new Anfrageformular(angebot));
					getUI().getNavigator().navigateTo(name);
				}
				}
			}
		});
        
        anbieter.addClickListener(new Button.ClickListener(){
			public void buttonClick(ClickEvent event) {
				String name = "AdminanfrageWohnung";
				
				getUI().getNavigator().addView(name, new AdminanfrageWohnung(angebot));
				getUI().getNavigator().navigateTo(name);
			
			}
		});

			
				
				
				
				
         
        
		final FavoritProvider fp = new FavoritProvider();

	    
        //Favoriten-Button TODO
        if(VaadinSession.getCurrent().getAttribute("login").equals(true) && !fp.favoritExists(VaadinSession.getCurrent().getAttribute(User.class), angebot)&& VaadinSession.getCurrent().getAttribute(User.class).getAccessLevel()!=2){
        
        	Button favorit = new Button("Favorit hinzufügen");
        	favorit.setIcon(FontAwesome.PLUS_SQUARE_O);
        	favorit.addStyleName("AnfrageButton");
        	buttons.addComponent(favorit);
        	
        	favorit.addClickListener(new Button.ClickListener() {
        		public void buttonClick(ClickEvent event) {
        			Favorit newFavorit = new Favorit();
        			newFavorit.setFavorit_idOffer(angebot);
        			newFavorit.setFavorit_idUser(VaadinSession.getCurrent().getAttribute(User.class));
        			
        			new FavoritProvider().addFavorit(newFavorit);
        			
        			String name = "Meine Favoriten";
					getUI().getNavigator().addView(name, new Favoriten()); // momentan angezeigtes Angebot soll übergeben werden...
					getUI().getNavigator().navigateTo(name);
					
        			Notification not = new Notification("Das Angebot wurde zu Ihren Favoriten hinzugefügt.");
        			not.setStyleName("success");
					not.setIcon(FontAwesome.CHECK_SQUARE_O);
        			not.setDelayMsec(300);
        			not.show(Page.getCurrent());
        			
        		}	
        	}); 
    	
        }else if(VaadinSession.getCurrent().getAttribute("login").equals(true) && fp.favoritExists(VaadinSession.getCurrent().getAttribute(User.class), angebot)&& VaadinSession.getCurrent().getAttribute(User.class).getAccessLevel()!=2){
        	
        	Button removeFavorit = new Button("Favorit entfernen");
        	removeFavorit.setIcon(FontAwesome.TRASH_O);
        	removeFavorit.addStyleName("AnfrageButton");
        	buttons.addComponent(removeFavorit);
        	
        	removeFavorit.addClickListener(new Button.ClickListener() {
        		public void buttonClick(ClickEvent event) {
        			
        		    Favorit fav;
        			fav = fp.findByUserOffer(VaadinSession.getCurrent().getAttribute(User.class), angebot);
        	        new FavoritProvider().removeFavorit(fav);
        	        
        	        String name = "Meine Favoriten";
        	        getUI().getNavigator().addView(name, new Favoriten()); // momentan angezeigtes Angebot soll übergeben werden...
					getUI().getNavigator().navigateTo(name);
        			
        			Notification not = new Notification("Das Angebot wurde aus Ihren Favoriten entfernt.");
        			not.setStyleName("success");
        			not.setIcon(FontAwesome.CHECK_SQUARE_O);
        			not.setDelayMsec(300);
        			not.show(Page.getCurrent());
        			
        		}	
        	}); 
        	
        }
        

        
        gridInfos.addComponent(buttons, 1, 14);
        
        
        //Anzeigen, wenn man Anbieter bereits kontaktiert hat       

        
  
        //Anzeigen, wenn man Anbieter bereits kontaktiert hat       

        
          

        if(VaadinSession.getCurrent().getAttribute("login").equals(true)&& VaadinSession.getCurrent().getAttribute(User.class).getAccessLevel()!=2) {
        	
        	User u = VaadinSession.getCurrent().getAttribute(User.class);
            List<Request> requests;
            requests = u.getRequests(); 

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
                gridInfos.addComponent(anfrageTitel,0,15);
                
                Label anfrageText = new Label("Sie haben folgenden Text an den Anbieter geschickt:");
                gridInfos.addComponent(anfrageText,0,16);
                
                TextArea anfrageMessage = new TextArea();
                String message = requests.get(in).getMessage();
               
                anfrageMessage.setValue(message);
                anfrageMessage.setEnabled(false);
                anfrageMessage.setWidth("338px");
                
                gridInfos.addComponent(anfrageMessage,1,16);
                
              }

        }

	}

	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}

