package com.example.housing;

import java.io.File;
import java.sql.Date;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
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

public class Einzelansicht extends VerticalLayout implements View {
	VerticalLayout content;
	
	public Einzelansicht(){
	
	content = new VerticalLayout();
//	content = (VerticalLayout)this;
	content.setMargin(true);
	content.setSizeFull();
	content.setSpacing(true);

	Navigation nav = new Navigation();

	
	addComponent(content);
	content.addComponent(nav);
	setContent();



	
	Footer f = new Footer();
	//addComponent(f);
	content.addComponent(f);
	}


	@SuppressWarnings("deprecation")
	public void setContent(){
		


			
		//titel
		String titel = "Helle 3 ZKB Wohnung in Karlsruhe Oststadt";
		Label lTitel= new Label(titel);
		lTitel.addStyleName("ImportantTitle");
		content.addComponent(lTitel);
		
		//adresse
		String adress = "Teststraße 123";
		String zip = "76227";
		String city = "Karlsruhe";
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
	    startDate.setValue(new Date(2014,12,05));
	    startDate.setEnabled(false);
	    gridInfos.addComponent(new Label("Startdatum"), 0, 0);
	    gridInfos.addComponent(startDate, 1,0);
		    
	    DateField endDate = new DateField();
	    endDate.setValue(new Date(2015,02,18));
	    endDate.setEnabled(false);
	    gridInfos.addComponent(new Label("Enddatum"), 0,1);
	    gridInfos.addComponent(endDate, 1,1);
	    
	    //Size
		float sm =  (float) 80.12;
		String sSm = Float.toString(sm) + "m²";
		
        gridInfos.addComponent(new Label("Größe"),0,2);
        gridInfos.addComponent(new Label(sSm),1 , 2);
        
        //Price
    	float price =  (float) 400.50;
		String sPrice = Float.toString(price) + " €";
		
        gridInfos.addComponent(new Label("Warmmiete"),0,3);
        gridInfos.addComponent(new Label(sPrice),1 , 3);
        
        //IsShared
        CheckBox isShared = new CheckBox("");       
        gridInfos.addComponent(new Label("WG"), 0, 4);
        gridInfos.addComponent(isShared, 1, 4);
        isShared.setEnabled(false);
        int i = 1;
        isShared.setValue( i!= 0);
        
       
        //Number of Roomates 
        int number = 5;
        gridInfos.addComponent(new Label("Anzahl Mitbewohner"), 0, 5);
        gridInfos.addComponent(new Label(Integer.toString(number)), 1, 5);
        
        //Internet 
        CheckBox hasInternet = new CheckBox("");       
        gridInfos.addComponent(new Label("Internet"), 0, 6);
        gridInfos.addComponent(hasInternet, 1,6);
        hasInternet.setEnabled(false);
        int j = 1;
        hasInternet.setValue( j!= 0);
        
        //furnished       
        CheckBox isFurnished = new CheckBox("");       
        gridInfos.addComponent(new Label("Möbliert"), 0, 7);
        gridInfos.addComponent(isFurnished, 1,7);
        isFurnished.setEnabled(false);
        int k = 1;
        isFurnished.setValue( k!= 0);
        
        //kitchen       
        CheckBox useKitchen = new CheckBox("");       
        gridInfos.addComponent(new Label("Küchenmitbenutzung"), 0, 8);
        gridInfos.addComponent(useKitchen, 1,8);
        useKitchen.setEnabled(false);
        int c = 1;
        useKitchen.setValue( c!= 0);
       
        //smoker
        CheckBox smokingAllowed = new CheckBox("");       
        gridInfos.addComponent(new Label("Raucher"), 0, 9);
        gridInfos.addComponent(smokingAllowed, 1,9);
        smokingAllowed.setEnabled(false);
        int m = 0;
        smokingAllowed.setValue( m!= 0);
        
        //pets       
        CheckBox petsAllowed = new CheckBox("");       
        gridInfos.addComponent(new Label("Haustiere erlaubt"), 0, 10);
        gridInfos.addComponent(petsAllowed, 1,10);
        petsAllowed.setEnabled(false);
        int n = 1;
        petsAllowed.setValue( n!= 0);
        
        //male / female   
        Label maleFemale = new Label(""); 
        int q = 1;
        if( q!= 0){
    	   maleFemale.setValue("Frauen-WG");
        }
        if (q == 0){
    	   maleFemale.setValue("Männer-WG");
        }
        gridInfos.addComponent(new Label("Art der WG"), 0, 11);
        gridInfos.addComponent(maleFemale, 1,11);

        
        //bond
        float bond =  (float) 600.50;
		String sBond = Float.toString(bond)+ " €";
		
        Label lBond = new Label("Kaution");
        gridInfos.addComponent(lBond,0,12);
        gridInfos.addComponent(new Label(sBond),1 , 12);
        
        
        //text
        TextArea t = new TextArea();
        String text = "Dies ist eine schöne Wohnung";
       
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

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}

