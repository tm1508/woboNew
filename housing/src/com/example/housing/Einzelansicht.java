package com.example.housing;

import java.io.File;
import java.sql.Date;

import javax.swing.GroupLayout.Alignment;

import com.example.housing.data.model.Offer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
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
	
	/**
	 * Instantiates a new einzelansicht.
	 */
	
	Offer angebot;
	public Einzelansicht(Offer einzelAngebot){
	this.angebot = einzelAngebot;
		
	content = new VerticalLayout();
//	content = (VerticalLayout)this;

    content.setMargin(true);
	content.setSizeFull();
	content.setSpacing(true);
	
	Navigation nav = new Navigation();


//	content.addComponent(nav);

	
	NavigationPublic navPublic = new NavigationPublic();
	addComponent(navPublic);
	
	//falls der Benutzer eingelogt ist verändert sich die Navigation
	if(VaadinSession.getCurrent().getAttribute("login").equals(true)){
		nav.setVisible(true);
		navPublic.setVisible(false);
	}else{
		nav.setVisible(false);
		navPublic.setVisible(true);
	}
	addComponent(nav);	
	addComponent(content);

	setContent();



	
	Footer f = new Footer();
	addComponent(f);
//	setComponentAlignment(f, com.vaadin.ui.Alignment.TOP_CENTER);
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
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/image/Bad.jpg"));
		FileResource resource2 = new FileResource(new File(basepath + "/WEB-INF/image/P1000595.jpg"));
		FileResource resource3 = new FileResource(new File(basepath + "/WEB-INF/image/Wohnbereich.jpg"));
		FileResource resource4 = new FileResource(new File(basepath + "/WEB-INF/image/Wohnzimmer.jpg"));
		Image image = new Image("",resource2);
		image.setWidth("388px");
		image.setHeight("314px");
		Image image2 = new Image("",resource);
		image2.setWidth("169px");
		image2.setHeight("144px");
		Image image3 = new Image("",resource3);
		image3.setWidth("169px");
		image3.setHeight("144px");
		Image image4 = new Image("",resource4);
		image4.setWidth("169px");
		image4.setHeight("144px");
		Image image5 = new Image("",resource);
		image5.setWidth("169px");
		image5.setHeight("144px");
		    gridPictures.addComponent(image, 0, 0, 3, 3);
		    gridPictures.addComponent(image2, 4, 0 ,5,1);
		    gridPictures.addComponent(image3, 6, 0, 7,1);
		    gridPictures.addComponent(image4, 4, 2, 5,3);
		    gridPictures.addComponent(image5, 6, 2, 7,3);
		    gridPictures.setWidth("40%");

		
		    
		GridLayout gridInfos = new GridLayout(2,15); 
		gridInfos.setWidth("60%");
		content.addComponent(gridInfos);
		//Date
		
	    DateField startDate = new DateField();
	    startDate.setValue(angebot.getStartDate());
	    startDate.setEnabled(false);
	    gridInfos.addComponent(new Label("Startdatum"), 0, 0);
	    gridInfos.addComponent(startDate, 1,0);
		    
	    DateField endDate = new DateField();
	    endDate.setValue(angebot.getEndDate());
	    endDate.setEnabled(false);
	    gridInfos.addComponent(new Label("Enddatum"), 0,1);
	    gridInfos.addComponent(endDate, 1,1);
	    
	    //Size
		float sm = angebot.getSquareMetre();
		String sSm = Float.toString(sm) + "m²";
		
        gridInfos.addComponent(new Label("Größe"),0,2);
        gridInfos.addComponent(new Label(sSm),1 , 2);
        
        //Price
    	float price =  angebot.getPrice();
		String sPrice = Float.toString(price) + " €";
		
        gridInfos.addComponent(new Label("Warmmiete"),0,3);
        gridInfos.addComponent(new Label(sPrice),1 , 3);
        
        //IsShared       
    	int a = angebot.getType();
		String s;
		if(a ==1){
			s = "WG";
		}else if(a ==2){
			s = "Wohnung";
		}else{
			s="";
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
        int q = angebot.getGender();
        if( q!= 0){
    	   maleFemale.setValue("Frauen-WG");
        }
        if (q == 0){
    	   maleFemale.setValue("Männer-WG");
        }
        gridInfos.addComponent(new Label("Art der WG"), 0, 11);
        gridInfos.addComponent(maleFemale, 1,11);

        
        //bond
        float bond =  angebot.getBond();
		String sBond = Float.toString(bond)+ " €";
		
        Label lBond = new Label("Kaution");
        gridInfos.addComponent(lBond,0,12);
        gridInfos.addComponent(new Label(sBond),1 , 12);
        
        
        //text
        TextArea t = new TextArea();
        String text = angebot.getText();
       
        t.setValue(text);
        t.setEnabled(false);
        gridInfos.addComponent(new Label("Beschreibung  "), 0, 13);
        gridInfos.addComponent(t, 1,13);
        
        
        Button bearbeiten = new Button("Bearbeiten");
        bearbeiten.addStyleName("BearbeitenButton");
        gridInfos.addComponent(bearbeiten, 0 , 14);
        
        Button anfrage = new Button("Anfrage");
        anfrage.addStyleName("AnfrageButton");
        gridInfos.addComponent(anfrage, 1 , 14);


	}

	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}

