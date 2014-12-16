package com.example.housing;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class Suche extends VerticalLayout implements View{

	VerticalLayout content;
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public Suche(){
		Navigation nav = new Navigation();
		addComponent(nav);
		
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
			
		
		setContent();
		addComponent(content);
		
		Footer f = new Footer();
		addComponent(f);
		
		setContent();
		addComponent(content);
		
	
	}
	
	public void setContent(){
		
		content = new VerticalLayout();
		Label suche = new Label("Suche");
		suche.addStyleName("suchLabel");
		content.addComponent(suche);
		content.setMargin(true);
		
		GridLayout gridSuche = new GridLayout(5,7); 
		gridSuche.setSpacing(true);
	//	gridSuche.setWidth("40%");
		gridSuche.addStyleName("LayoutSuche");
		
		//Größe
		gridSuche.addComponent(new Label("Quadratmeter:  "), 0 ,0);
		gridSuche.addComponent(new Label("von  "), 1 ,0);
		TextField sucheVon = new TextField();
		gridSuche.addComponent(sucheVon, 2,0);
		gridSuche.addComponent(new Label("bis  "), 3 , 0);
		TextField sucheBis = new TextField();
		gridSuche.addComponent(sucheBis, 4,0);
		
		//Preis
		gridSuche.addComponent(new Label("Preis:  "), 0 ,1);
		gridSuche.addComponent(new Label("von  "), 1 ,1);
		TextField preisVon = new TextField();
		gridSuche.addComponent(preisVon, 2,1);
		gridSuche.addComponent(new Label("bis  "), 3 , 1);
		TextField preisBis = new TextField();
		gridSuche.addComponent(preisBis, 4,1);
		
		//Zeitraum
		gridSuche.addComponent(new Label("Zeitraum:  "), 0 ,2);
		gridSuche.addComponent(new Label("von  "), 1 ,2);
		PopupDateField zeitVon = new PopupDateField();
		gridSuche.addComponent(zeitVon, 2,2);
		gridSuche.addComponent(new Label("bis  "), 3 , 2);
		PopupDateField zeitBis = new PopupDateField();
		gridSuche.addComponent(zeitBis, 4,2);
		
		//Art der Unterkunft
		gridSuche.addComponent(new Label("Unterkunft:"), 0 ,3);
		gridSuche.addComponent(new Label("WG"), 1 ,3);
		CheckBox wg = new CheckBox();
		gridSuche.addComponent(wg, 2 ,3);
		gridSuche.addComponent(new Label("Wohnung"), 3 ,3);
		CheckBox wohnung = new CheckBox();
		gridSuche.addComponent(wohnung, 4 ,3);
		
		//Sonstiges
		gridSuche.addComponent(new Label("Sonstiges:"), 0 ,4);
		gridSuche.addComponent(new Label("Haustiere"), 1 ,4);
		CheckBox haustiere = new CheckBox();
		gridSuche.addComponent(haustiere, 2 ,4);
		gridSuche.addComponent(new Label("Raucher"), 3 ,4);
		CheckBox rauchen = new CheckBox();
		gridSuche.addComponent(rauchen, 4 ,4);
		gridSuche.addComponent(new Label("Möbliert"), 1 ,5);
		CheckBox möbliert = new CheckBox();
		gridSuche.addComponent(möbliert, 2 ,5);
		gridSuche.addComponent(new Label("Küche"), 3 ,5);
		CheckBox küche = new CheckBox();
		gridSuche.addComponent(küche, 4 ,5);
		
		Button suchButton = new Button("Suchen");
		suchButton.addStyleName("SuchButton");
		gridSuche.addComponent(suchButton, 0 ,6);
		
		
		content.addComponent(gridSuche);
		
		
//		HorizontalLayout h1 = new HorizontalLayout();
//		VerticalLayout v1 = new VerticalLayout();
//		VerticalLayout v2 = new VerticalLayout();
//		VerticalLayout v3 = new VerticalLayout();
//		VerticalLayout v4 = new VerticalLayout();
//		VerticalLayout v5 = new VerticalLayout();
//		HorizontalLayout h2 = new HorizontalLayout();
//		HorizontalLayout h3 = new HorizontalLayout();
//		HorizontalLayout h4 = new HorizontalLayout();
//		HorizontalLayout h5 = new HorizontalLayout();

//		
//		h1.setSpacing(true);
//		content.addComponent(h1);
//		h1.addComponent(v1);
//		v1.addComponent(new Label("Quadratmeter")); 
//		v1.addComponent(new Label("von")); 
//		v1.addComponent(new TextField());	
//		v1.addComponent(new Label("bis"));	
//		v1.addComponent(new TextField()); 
//		v1.addComponent(new Button("Suchen"));
//		h1.addComponent(v2);
//		v2.addComponent(new Label("Preis"));
//		v2.addComponent(new Label("von"));
//		v2.addComponent(new TextField());
//		v2.addComponent(new Label("bis"));
//		v2.addComponent(new TextField());
//		h1.addComponent(v3);
//		v3.addComponent(new Label("Zeitraum"));
//		v3.addComponent(new Label("von"));
//		v3.addComponent(new PopupDateField());
//		v3.addComponent(new Label("bis"));
//		v3.addComponent(new PopupDateField());
//			v4.setSpacing(true);
//			v5.setSpacing(true);
//			h2.setSpacing(true);
//			h3.setSpacing(true);
//			h4.setSpacing(true);
//			h5.setSpacing(true);
//		h1.addComponent(v4);
//		v4.addComponent(new Label("Art der Unterkunft"));
//		h2.setSpacing(true);
//		v4.addComponent(h2);
//		h2.addComponent(new Label("WG-Zimmer"));
//		h2.addComponent(new CheckBox());
//		v4.addComponent(h3);
//		h3.addComponent(new Label("Wohnung"));
//		h3.addComponent(new CheckBox());
//		h1.addComponent(v5);
//		v5.addComponent(new Label("Sonstiges"));
//		v5.addComponent(h4);
//		h4.addComponent(new Label("Haustiere erlaubt"));
//		h4.addComponent(new CheckBox());
//		v5.addComponent(h5);
//		h5.addComponent(new Label("Rauchen erlaubt"));
//		h5.addComponent(new CheckBox());
//		
		//TODO Inhalt einfügen
		
	}

}
