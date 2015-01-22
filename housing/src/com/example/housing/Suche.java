package com.example.housing;

import java.util.Date;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.OfferProvider;
import com.example.housing.utility.Format;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Suche.
 */
public class Suche extends HorizontalLayout implements View{

	/** The content. */
	VerticalLayout content;
	
	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Instantiates a new suche.
	 */
	public Suche(){
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
	public void setContent(){
		
		//Titel
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Suche");
		title.addStyleName("title");
		content.addComponent(title);
		
		content = new VerticalLayout();

		content.setMargin(true);
		
		GridLayout gridSuche = new GridLayout(5,10); 
		gridSuche.setSpacing(true);
	//	gridSuche.setWidth("40%");
		gridSuche.addStyleName("LayoutSuche");
		
		//Stadt
		gridSuche.addComponent(new Label("Stadt:  "), 0 ,0);
		final TextField stadt = new TextField();
		gridSuche.addComponent(stadt,1 ,0);

		//Grï¿½ï¿½e
		gridSuche.addComponent(new Label("Quadratmeter:  "), 0 ,1);
		gridSuche.addComponent(new Label("von  "), 1 ,1);
		final TextField sucheVon = new TextField();
		gridSuche.addComponent(sucheVon, 2,1);
		gridSuche.addComponent(new Label("bis  "), 3 , 1);
		final TextField sucheBis = new TextField();
		gridSuche.addComponent(sucheBis, 4,1);
		
		//Preis
		gridSuche.addComponent(new Label("Preis:  "), 0 ,2);
		gridSuche.addComponent(new Label("von  "), 1 ,2);
		final TextField preisVon = new TextField();
		gridSuche.addComponent(preisVon, 2,2);
		gridSuche.addComponent(new Label("bis  "), 3 , 2);
		final TextField preisBis = new TextField();
		gridSuche.addComponent(preisBis, 4,2);
		
		//Zeitraum
		gridSuche.addComponent(new Label("Zeitraum:  "), 0 ,3);
		gridSuche.addComponent(new Label("von  "), 1 ,3);
		final PopupDateField zeitVon = new PopupDateField();
		gridSuche.addComponent(zeitVon, 2,3);
		gridSuche.addComponent(new Label("bis  "), 3 , 3);
		final PopupDateField zeitBis = new PopupDateField();
		gridSuche.addComponent(zeitBis, 4,3);
		
		//Art der Unterkunft
		gridSuche.addComponent(new Label("Unterkunft: "), 0 ,4);
		final CheckBox wohnung = new CheckBox("Wohnung");
		final CheckBox zimmer = new CheckBox("Zimmer");
		final CheckBox wg = new CheckBox("WG-Zimmer");
		gridSuche.addComponent(wohnung,2 ,4);
		gridSuche.addComponent(zimmer,3,4);
		gridSuche.addComponent(wg, 4 ,4);

		
		//Sonstiges
		gridSuche.addComponent(new Label("Sonstiges:"), 0 ,5);
		gridSuche.addComponent(new Label("Haustiere"), 1 ,5);
		final CheckBox haustiere = new CheckBox();
		gridSuche.addComponent(haustiere, 2 ,5);
		gridSuche.addComponent(new Label("Raucher"), 3 ,5);
		final CheckBox rauchen = new CheckBox();
		gridSuche.addComponent(rauchen, 4 ,5);
		gridSuche.addComponent(new Label("möbliert"), 1 ,6);
		final CheckBox moebliert = new CheckBox();
		gridSuche.addComponent(moebliert, 2, 6);
		gridSuche.addComponent(new Label("Küche"), 3 ,6);
		final CheckBox kueche = new CheckBox();
		gridSuche.addComponent(kueche, 4 ,6);
		gridSuche.addComponent(new Label("Internet"), 1 ,7);
		final CheckBox internet = new CheckBox();
		gridSuche.addComponent(internet, 2 ,7);
		

		Button suchButton = new Button("Suchen");
		suchButton.setIcon(FontAwesome.SEARCH);
		suchButton.addStyleName("SuchButton");
		gridSuche.addComponent(suchButton, 0 ,9);
		
		//Suchfunktion
	

		suchButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				int a = 7;
				if(wohnung.getValue()&&zimmer.getValue()&& wg.getValue()){
					a = 7;
				}
				else if(wohnung.getValue()&&zimmer.getValue()){
					a = 4;
				}else if (zimmer.getValue()&& wg.getValue()){
					a = 5;
				}else if (wg.getValue()&&wohnung.getValue()){
					a = 6;
				}else if(wohnung.getValue()){
					a = 1;
				}else if(zimmer.getValue()){
					a = 2;
				}else if(wg.getValue()){
					a = 3;
				}
				
				OfferProvider of = new OfferProvider();
				List<Offer> ergebnisse;				
				ergebnisse =
						of.filter(zeitVon.getValue(),
						zeitBis.getValue(),
						(new Format().floatFormat(sucheVon.getValue())),
								(new Format().floatFormat(sucheBis.getValue())), 
								(new Format().floatFormat(preisVon.getValue())),
								( new Format().floatFormat(preisBis.getValue())),
						a, internet.getValue(), moebliert.getValue(), kueche.getValue(),rauchen.getValue(),
						haustiere.getValue(),
						stadt.getValue());
				
				String name = "AngebotAnzeigen";
				getUI().getNavigator().addView(name, new Suchergebnis(ergebnisse));
				getUI().getNavigator().navigateTo(name);
			}
		});
		
		content.addComponent(gridSuche);

		
	}

}
