package com.example.housing;

import java.util.List;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.OfferProvider;
import com.example.housing.utility.Format;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class Startseite.
 */
public class Startseite extends HorizontalLayout implements View{

	/** The content. */
	VerticalLayout content;
	
	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub	
	}
	
	
	public Startseite(){
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
			
			//falls der Benutzer eingelogt ist ver�ndert sich die Navigation
			if(VaadinSession.getCurrent().getAttribute("login").equals(true)){
				if(VaadinSession.getCurrent().getAttribute(User.class).getAccessLevel()==2){//falls der User ein Admin ist
					nav.setVisible(false);
					navPublic.setVisible(false);
					navAdmin.setVisible(true);//Admin-Navigation
				}else{//ansonsten: Naviagtion f�r eingeloggte Nutzer
					nav.setVisible(true);
					navPublic.setVisible(false);
					navAdmin.setVisible(false);
				}
			}else{//ansonsten Public Navigation (f�r alle)
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
		
		content = new VerticalLayout();
		content.setMargin(true);
		
	
		
		//Horizontales Layout fuer Begr��ungstext und Suchfeld
		HorizontalLayout h = new HorizontalLayout();
		h.setWidth("100%");
		h.setMargin(false);
		
		//Begr��ungstext
		Accordion accordion = new Accordion();
		accordion.setHeight("400px");
		accordion.setStyleName("startseite");
	
		
			//Tab 1
			final VerticalLayout layout = new VerticalLayout();
	        layout.setMargin(true);
	        layout.addComponent(new Label("Herzlich Willkommen auf unserer Webseite!"));
	        	Label label = new Label("Auf unserer Webseite k�nnen Sie einen Nach- oder Zwischenmieter f�r Ihre Wohung oder Ihr Zimmer finden. Als Dualer Student der DHBW Karlsruhe k�nnen Sie die passende Wohung oder das passende Zimmer finden (auch zur Zwischenmiete) und den Vermieter kontaktieren.");
	        	layout.addComponent(label);
	        accordion.addTab(layout, "Herzlich Willkommen",FontAwesome.HEART);//Tab 1 hinzuf�gen
         
	        //Tab 2
			final VerticalLayout layout_1 = new VerticalLayout();
	        layout_1.setMargin(true);
		        Label label_1 = new Label("Wenn Sie eine Wohnung vermieten wollen sind Sie bei uns genau richtig! Jeder registrierte Benutzer kann Wohnungen anbieten. Registrieren Sie sich bitte hier. Ihre Wohung oder Ihr Zimmer wird ausschlie�lich an Studenten der DHBW Karlsruhe vermietet.");
		        layout_1.addComponent(label_1);
		        
				//Link zu Registrierung
				Button link_1 = new Button();
				link_1.setStyleName("link");
				link_1.setCaption("Registrierung");
				link_1.setImmediate(false);
				link_1.setWidth("-1px");
				link_1.setHeight("-1px");
				link_1.setIcon(FontAwesome.EXTERNAL_LINK);
				link_1.addClickListener(new Button.ClickListener(){
					public void buttonClick(ClickEvent event) {
						String name = "Registrierung";
						getUI().getNavigator().addView(name, new Registrierung());
						getUI().getNavigator().navigateTo(name);
					}
				});
				
				layout_1.addComponent(link_1);
				
			accordion.addTab(layout_1, "F�r Vermieter...",FontAwesome.HOME);//Tab 2 hinzuf�gen
        
	        //Tab 3
			final VerticalLayout layout_2 = new VerticalLayout();
	        layout_2.setMargin(true);
	        	layout_2.addComponent(new Label("F�r alle, die eine Wohnung suchen...: Sie sind Dualer Student an der DHBW Karlsruhe? Dann finden Sie bei uns die passende Wohung oder das passende Zimmer. Wir haben ein gro�es Angebot an Zimmern und Wohnungen..."));
	
				//Link zu Registrierung
				Button link_2 = new Button();
				link_2.setStyleName("link");
				link_2.setCaption("Registrierung");
				link_2.setImmediate(false);
				link_2.setWidth("-1px");
				link_2.setHeight("-1px");
				link_2.setIcon(FontAwesome.EXTERNAL_LINK);
				link_2.addClickListener(new Button.ClickListener(){
					public void buttonClick(ClickEvent event) {
						String name = "Registrierung";
						getUI().getNavigator().addView(name, new Registrierung());
						getUI().getNavigator().navigateTo(name);
					}
				});
				
				layout_2.addComponent(link_2);
				
			accordion.addTab(layout_2, "F�r Mieter...",FontAwesome.SEARCH);//Tab 3 hinzuf�gen
        
	        //Tab 4
			final VerticalLayout layout_3 = new VerticalLayout();
	        layout_3.setMargin(true);
		        Label label_3 = new Label("Wir sind DH-Studenten der DHBW Karlsruhe...");
		        Label label_4 = new Label("Sie haben Anregungen, W�nsche oder Kritik? Bitte kontaktieren Sie uns! Falls Sie als Austauschstudent an die Duale Hochschule Karlsruhe kommen, bitte kontaktieren Sie den Administrator, damit Sie als Dualer Student freigeschaltet werden.");
		        layout_3.addComponent(label_3);
		        layout_3.addComponent(label_4);
		        
		        Link link_3 = new Link();
				link_3.setStyleName("text");
				link_3.setCaption("Kontakt");
				link_3.setImmediate(false);
				link_3.setWidth("-1px");
				link_3.setHeight("-1px");
				link_3.setIcon(FontAwesome.EXTERNAL_LINK);
				layout_3.addComponent(link_3);
	        accordion.addTab(layout_3, "�ber uns...",FontAwesome.ENVELOPE);//Tab 4 hinzuf�gen
         
		h.addComponent(accordion);//Begr��ungstext hinzuf�gen
		
		//Panel f�r Suchfeld
		Panel panel = new Panel("Suche");
		panel.setStyleName("startseite");
		panel.setHeight("400px");
		panel.setIcon(FontAwesome.SEARCH);
		VerticalLayout suche = new VerticalLayout();
		suche.setMargin(true);
		
			//Suchfeld
			final TextField suchfeld = new TextField();
			suchfeld.setCaption("Stadt eingeben...");
			suchfeld.setDescription("Bitte Stadt eingeben");
			suchfeld.setInputPrompt("Bsp. Karlsruhe");
			suchfeld.setWidth("220px");
			suchfeld.setHeight("-1px");
			suchfeld.addStyleName("textfield");
			suche.addComponent(suchfeld);
			
			//Button zum Starten der Suche
			Button sucheStarten = new Button();
			sucheStarten.setStyleName("AnfrageButton");
			sucheStarten.setCaption("Suche starten");
			sucheStarten.setDescription("Suche starten");
			sucheStarten.setWidth("-1px");
			sucheStarten.setHeight("-1px");
			sucheStarten.setIcon(FontAwesome.SEARCH);
			suche.addComponent(sucheStarten);
			sucheStarten.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					
					OfferProvider of = new OfferProvider();
					List<Offer> ergebnisse;				
					ergebnisse =
							of.filter(null,null,
							(new Format().floatFormat("0.0")),
									(new Format().floatFormat("0.0")), 
									(new Format().floatFormat("0.0")),
									( new Format().floatFormat("0.0")),
									7, 
									false, false, false, false,false,
							suchfeld.getValue());
					
					String name = "AngebotAnzeigen";
					getUI().getNavigator().addView(name, new Suchergebnis(ergebnisse));
					getUI().getNavigator().navigateTo(name);
										
				}
			});
			
			


		panel.setContent(suche);
		h.addComponent(panel);//Panel hinzuf�gen
		
		content.addComponent(h);//Begr��ungstext und Suchfeld hinzuf�gen
		
		//Neuste Angebote
		Panel p = new Panel("Unsere neusten Angebote");
		p.setStyleName("startseite");
		p.setWidth("100%");
		VerticalLayout v = new VerticalLayout();
		v.setMargin(true);
		
		List<Offer> latestOffers = new OfferProvider().getLatestOffers();
		for( Offer o : latestOffers) {
			v.addComponent(new Listenzeile(o));
		}
		
        p.setContent(v);
		
		content.addComponent(p);
 
    }
 
    
}
	
	

