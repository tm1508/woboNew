package com.example.housing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;

import javax.swing.GroupLayout.Alignment;

import com.example.housing.data.model.Favorit;
import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.example.housing.data.model.Request;
import com.example.housing.data.provider.FavoritProvider;
import com.example.housing.data.provider.PhotoProvider;
import com.example.housing.utility.Format;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
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
public class Einzelansicht extends VerticalLayout implements View {
	
	/** The content. */
	VerticalLayout content;
	
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
//		content = (VerticalLayout)this;
		
		content.setMargin(true);
		content.setSizeFull();
		content.setSpacing(true);
		
		Navigation nav = new Navigation();
		addComponent(nav);
		
		NavigationPublic navPublic = new NavigationPublic();
		addComponent(navPublic);
		
		//Listenzeile list = new Listenzeile();
		//addComponent(list);
		
		//falls der Benutzer eingelogt ist verändert sich die Navigation
		if(VaadinSession.getCurrent().getAttribute("login").equals(true)){
			nav.setVisible(true);
			navPublic.setVisible(false);
		}else{
			nav.setVisible(false);
			navPublic.setVisible(true);
		}
			
		setContent();
		addComponent(content);

		Footer f = new Footer();
		addComponent(f);
//		setComponentAlignment(f, com.vaadin.ui.Alignment.TOP_CENTER);
		//content.addComponent(f);
	}


	/**
	 * Sets the content.
	 */
	@SuppressWarnings("deprecation")
	public void setContent(){
			
		//titel
		String titel = angebot.getTitle();
		Label lTitel= new Label(titel + " in " +angebot.getCity());
		lTitel.addStyleName("ImportantTitle");
		content.addComponent(lTitel);
		
		//adresse
		String adress = angebot.getStreet();
		String zip = angebot.getZip();
		String city = angebot.getCity();
		Label lAdress = new Label(adress + "   " + zip + "   " + city);

		content.addComponent(lAdress);
		
		//pictures
		GridLayout gridPictures = new GridLayout(8, 8);
		gridPictures.setMargin(false);
		content.addComponent(gridPictures);
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		
		Resource resource = new FileResource(new File(basepath + "/WEB-INF/image/dh.jpg"));
		Resource resource2 = new FileResource(new File(basepath + "/WEB-INF/image/dh.jpg"));
		Resource resource3 = new FileResource(new File(basepath + "/WEB-INF/image/dh.jpg"));
		Resource resource4 = new FileResource(new File(basepath + "/WEB-INF/image/dh.jpg"));
		Resource resource5 = new FileResource(new File(basepath + "/WEB-INF/image/dh.jpg"));
		
		switch(angebot.getPhotos().size()) {
			case 5:
				
				resource5 = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(4).getPhoto());
					}
				}, "Bild_5");
				
			case 4:
				
				resource4 = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(3).getPhoto());
					}
				}, "Bild_4");
				
			case 3:
				
				resource3 = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(2).getPhoto());
					}
				}, "Bild_3");
				
			case 2:
				
				resource2 = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(1).getPhoto());
					}
				}, "Bild_2");
				
			case 1:
				
				resource = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(0).getPhoto());
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
		    
		GridLayout gridInfos = new GridLayout(2,16); 
	//	gridInfos.setWidth("60%");
		content.addComponent(gridInfos);
		
		//Date
		
	    DateField startDate = new DateField();
	    startDate.setValue(angebot.getStartDate());
	    startDate.setEnabled(false);
	    Label start = new Label("Startdatum");
	    start.setWidth("388px");
	    gridInfos.addComponent(start, 0, 0);
	    gridInfos.addComponent(startDate, 1,0);

		    
	    DateField endDate = new DateField();
	    endDate.setValue(angebot.getEndDate());
	    endDate.setEnabled(false);
	    gridInfos.addComponent(new Label("Enddatum"), 0,1);
	    gridInfos.addComponent(endDate, 1,1);
	    
	    //Size
		float sm = angebot.getSquareMetre();
		String sSm = new Format().stringFormat(sm) + "m²";
		
        gridInfos.addComponent(new Label("Größe"),0,2);
        gridInfos.addComponent(new Label(sSm),1 , 2);
        
        //Price
    	float price =  angebot.getPrice();
		String sPrice = new Format().stringEuro(price) + " €";
		
        gridInfos.addComponent(new Label("Warmmiete"),0,3);
        gridInfos.addComponent(new Label(sPrice),1 , 3);
        
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
        gridInfos.addComponent(new Label("Art der Unterkunft: "), 0, 4);
        gridInfos.addComponent(new Label(s), 1, 4);
     
        //Number of Roomates 
        int number = angebot.getNumberOfRoommate();
        gridInfos.addComponent(new Label("Anzahl Mitbewohner"), 0, 5);
        gridInfos.addComponent(new Label(Integer.toString(number)), 1, 5);
        
        //Internet 
        CheckBox hasInternet = new CheckBox("");       
        gridInfos.addComponent(new Label("Internet"), 0, 6);
        gridInfos.addComponent(hasInternet, 1,6);
        hasInternet.setEnabled(false);
        hasInternet.setValue( angebot.isInternet());
        
        //furnished       
        CheckBox isFurnished = new CheckBox("");       
        gridInfos.addComponent(new Label("Möbliert"), 0, 7);
        gridInfos.addComponent(isFurnished, 1,7);
        isFurnished.setEnabled(false);
        isFurnished.setValue( angebot.isFurnished());
        
        //kitchen       
        CheckBox useKitchen = new CheckBox("");       
        gridInfos.addComponent(new Label("Küchenmitbenutzung"), 0, 8);
        gridInfos.addComponent(useKitchen, 1,8);
        useKitchen.setEnabled(false);
        useKitchen.setValue( angebot.isKitchen());
       
        //smoker
        CheckBox smokingAllowed = new CheckBox("");       
        gridInfos.addComponent(new Label("Raucher"), 0, 9);
        gridInfos.addComponent(smokingAllowed, 1,9);
        smokingAllowed.setEnabled(false);
        smokingAllowed.setValue(angebot.isSmoker());
        
        //pets       
        CheckBox petsAllowed = new CheckBox("");       
        gridInfos.addComponent(new Label("Haustiere erlaubt"), 0, 10);
        gridInfos.addComponent(petsAllowed, 1,10);
        petsAllowed.setEnabled(false);
        petsAllowed.setValue(angebot.isPets());
        
        //male / female   
        Label maleFemale = new Label(""); 
        int g = angebot.getGender();
        if( g==1){
    	   maleFemale.setValue("egal");
        }
        else if (g == 2){
    	   maleFemale.setValue("männlich");
        }
        else if ( g == 3)
        	maleFemale.setValue("weiblich");
        gridInfos.addComponent(new Label("Bevorzugtes Geschlecht"), 0, 11);
        gridInfos.addComponent(maleFemale, 1,11);

        
        //bond
        float bond =  angebot.getBond();
		String sBond = new Format().stringEuro(bond) + " €";
		
        Label lBond = new Label("Kaution");
        gridInfos.addComponent(lBond,0,12);
        gridInfos.addComponent(new Label(sBond),1 , 12);
        
        
        //text
        TextArea t = new TextArea();
        String text = angebot.getText();
       
        t.setValue(text);
        t.setEnabled(false);
        t.setWidth("338px");
        gridInfos.addComponent(new Label("Beschreibung  "), 0, 13);
        gridInfos.addComponent(t, 1,13);
    
        
        //Bearbeiten- und Löschen-Button
        if(VaadinSession.getCurrent().getAttribute("login").equals(true)){
        	if(VaadinSession.getCurrent().getAttribute(User.class).getEmail().equals(angebot.getOffer_idUser().getEmail())){
        		
        		HorizontalLayout userButtons = new HorizontalLayout();
        		
				Button change = new Button("Bearbeiten");
				change.addStyleName("BearbeitenButton");
				change.addClickListener(new Button.ClickListener() {
					public void buttonClick(ClickEvent event) {
						String name = "AngebotErstellen";
						getUI().getNavigator().addView(name, new AngebotErstellen(angebot)); // momentan angezeigtes Angebot soll übergeben werden...
						getUI().getNavigator().navigateTo(name);
					}
				});
				
				Button delete = new Button("Angebot löschen");
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
        buttons.addComponent(anfrage);
        
        anfrage.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				if(VaadinSession.getCurrent().getAttribute("login").equals(false)) {
					Notification.show("Sie müssen sich als verifizierter DH-Student einloggen, um eine Anfrage zu einem Wohnungsangebot stellen zu können!", Type.HUMANIZED_MESSAGE);
				} else if (VaadinSession.getCurrent().getAttribute(User.class).getAccessLevel() != 1) {
					Notification.show("Sie müssen sich als DH-Student verifizieren, um eine Anfrage zu einem Wohnungsangebot stellen zu können!", Type.HUMANIZED_MESSAGE);
				} else {
					String name = "Anfrageformular";
					getUI().getNavigator().addView(name, new Anfrageformular(angebot));
					getUI().getNavigator().navigateTo(name);
				}
			}
		});
        
        
        //Favoriten-Button
        if(VaadinSession.getCurrent().getAttribute("login").equals(true)){
        
        	Button favorit = new Button("Favorit");
        	favorit.addStyleName("AnfrageButton");
        	buttons.addComponent(favorit);
        	
        	favorit.addClickListener(new Button.ClickListener() {
        		public void buttonClick(ClickEvent event) {
        			Favorit newFavorit = new Favorit();
        			newFavorit.setFavorit_idOffer(angebot);
        			newFavorit.setFavorit_idUser(VaadinSession.getCurrent().getAttribute(User.class));
        			
        			new FavoritProvider().addFavorit(newFavorit);
        			Notification not = new Notification("Das Angebot wurde zu Ihren Favoriten hinzugefügt."); //TODO
        			not.show(Page.getCurrent());
        			
        		}	
        	}); 
    	
        }
        

        
        gridInfos.addComponent(buttons, 1, 14);
        
        
        //Anzeigen, wenn man Anbieter bereits kontaktiert hat       
        if(VaadinSession.getCurrent().getAttribute("login").equals(true)){
        	  List<Request> r;
              r= VaadinSession.getCurrent().getAttribute(User.class).getRequests();  
        	  if(r.contains(angebot)){
        		int b = r.indexOf(angebot);
        		Request re = r.get(b);
              	Label l = new Label("Sie haben den Anbieter bereits kontaktiert mit dem folgenden Text: " +re.getMessage());
              	gridInfos.addComponent(l,0,15);
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

