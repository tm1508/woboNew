package com.example.housing;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class Suchergebnis.
 */
public class Suchergebnis extends VerticalLayout implements View {
	
	/** The content. */
	VerticalLayout content;
	
	/**
	 * Instantiates a new suchergebnis.
	 */

	List<Offer> angebote; 
	//Übergabe der Ergebnis aus der Suche
	public Suchergebnis(List<Offer> offer){
	this.angebote = offer;
		
	content = new VerticalLayout();


    content.setMargin(true);
	content.setSizeFull();
	content.setSpacing(true);
	
	Navigation nav = new Navigation();

	
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
		// Anzahl der gefundenen Ergebnisse
		int anzahl = 5;
				//angebote.size();

		GridLayout ergebnisLayout = new GridLayout(6,3);
		ergebnisLayout.setMargin(false);
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/image/dh.jpg"));
		Image image = new Image("",resource);
		ergebnisLayout.addComponent(image,0,0,2,2);
		String title = "Schöne TestWohnung";
		Label l = new Label(title);
		ergebnisLayout.addComponent(l,4,0);
		float price = (float) 400.20;		
		Label l2 = new Label(Float.toString(price)+ "€");
		ergebnisLayout.addComponent(l2,3,1);
		
		float sm =  (float) 80.12;
		String sSm = Float.toString(sm) + "m²";
		ergebnisLayout.addComponent(new Label(sSm),4,1);
		int a = 2;
		String s;
		if(a ==1){
			s = "WG";
		}else if(a ==2){
			s = "Wohnung";
		}else{
			s="";
		}
		ergebnisLayout.addComponent(new Label(s),5,1);
		Date start= new Date(2015,01,30);
		ergebnisLayout.addComponent(new Label(start.toString()),3,2);
		Date end= new Date(2015,04,30);
		ergebnisLayout.addComponent(new Label(end.toString()),5,2);
//		GridLayout list = new GridLayout(1,anzahl);
//		List<GridLayout> liste_ergebnisse = new ArrayList<GridLayout>();
		VerticalLayout hl = new VerticalLayout();
		for(int i = 0; i<anzahl;i++){
			
			hl.addComponent(ergebnisLayout);
		//	hl.addComponent(new Label("Zahl" + i));
		}
//		for(int i = 0; i<anzahl;i++){
//			//list = new GridLayout(1,i);
//			list.addComponent(liste_ergebnisse.get(i),0,i);
//			
//		}

		content.addComponent(hl);

		
		


	}

	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}


