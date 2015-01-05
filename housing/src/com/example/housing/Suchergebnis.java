package com.example.housing;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Photo;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
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
	
	//�bergabe der Ergebnis aus der Suche
	public Suchergebnis(List<Offer> offers){
		
		this.angebote = offers;
		
	content = new VerticalLayout();


    content.setMargin(true);
	content.setSizeFull();
	content.setSpacing(true);
	
	Navigation nav = new Navigation();

	
	NavigationPublic navPublic = new NavigationPublic();
	addComponent(navPublic);
	
	//falls der Benutzer eingelogt ist ver�ndert sich die Navigation
	if(VaadinSession.getCurrent().getAttribute("login").equals(true)){
		nav.setVisible(true);
		navPublic.setVisible(false);
	}else{
		nav.setVisible(false);
		navPublic.setVisible(true);
	}
	addComponent(nav);	
	

	setContent();
	addComponent(content);


	
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
		int anzahl = angebote.size();
		System.out.println(anzahl);
		for(int i = 0; i<anzahl;i++){
			final Offer o = angebote.get(i);
		
	

		GridLayout ergebnisLayout = new GridLayout(6,3);
		ergebnisLayout.setMargin(false);
		//pictures
//		List<Photo> pictures;
//		pictures = o.getPhotos();
//		if(pictures.size()>0){
//		Photo ph = pictures.get(0);
//		byte[] by = ph.getPhoto();
//		ImageIcon im2 = new ImageIcon(by);		
//		ergebnisLayout.addComponent((Component) im2,0,0,2,2);
//		}else{
			//TODO
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/image/dh.jpg"));
		Image image = new Image("",resource);
		ergebnisLayout.addComponent(image,0,0,2,2);
//		String im = "Kein Bild vorhanden";
//		ergebnisLayout.addComponent(new Label(im),0,0,2,2);
//		}
		
		String title = o.getTitle();
		Label l = new Label(title + " in "+ o.getCity());
		ergebnisLayout.addComponent(l,4,0);
		float price = o.getPrice();		
		Label l2 = new Label(Float.toString(price)+ " �");
		ergebnisLayout.addComponent(l2,3,1);
		
		float sm = o.getSquareMetre();
		String sSm = Float.toString(sm) + " m�";
		ergebnisLayout.addComponent(new Label(sSm),4,1);
		
		int a = o.getType();
		String s;
		if(a ==1){
			s = "WG";
		}else if(a ==2){
			s = "Wohnung";
		}else{
			s="";
		}
		ergebnisLayout.addComponent(new Label(s),5,1);
		
		
//		Date start= new Date(2015,01,30);
		Date start = (Date) o.getStartDate();
		ergebnisLayout.addComponent(new Label(start.toString()),3,2);
//		Date end= new Date(2015,04,30);
		//TODO: Wenn end-Datum nicht gesetzt ist, pr�fen!
		Date end = (Date) o.getEndDate();
		ergebnisLayout.addComponent(new Label(end.toString()),5,2);
		
		ergebnisLayout.addLayoutClickListener(new LayoutClickListener(){
			 public void layoutClick(LayoutClickEvent event) {
				 
				 	
					String name = "Einzelansicht";
					getUI().getNavigator().addView(name, new Einzelansicht(o));
					getUI().getNavigator().navigateTo(name);
	            }
		});
		
		content.addComponent(ergebnisLayout);
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


