package com.example.housing;

import java.util.List;
import com.example.housing.data.model.Offer;
import com.example.housing.data.provider.OfferProvider;
import com.example.housing.utility.Format;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class Startseite extends CustomHorizontalLayout implements View{
	private static final long serialVersionUID = 1L;
	private VerticalLayout content;
	
	public Startseite(){
		content = super.initCustomHorizontalLayout();
		setContent();
	}
	
	public void setContent(){
				
		// title
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Willkommen bei der Wohnungsbörse der DHBW Karlsruhe");
		title.addStyleName("title");
		content.addComponent(title);
	
		//Horizontales Layout fuer Begrüßungstext und Suchfeld
		HorizontalLayout layoutTextUndSuche = new HorizontalLayout();
		layoutTextUndSuche.setWidth("100%");
		layoutTextUndSuche.setMargin(false);
		
		//Begrüßungstext
		Accordion textAccordion = new Accordion();
		textAccordion.setHeight("400px");
		textAccordion.setStyleName("startseite");
		
			//Tab 1
			VerticalLayout tab1Layout = new VerticalLayout();
	        tab1Layout.setMargin(true);
	        tab1Layout.addComponent(new Label("Herzlich Willkommen auf unserer Webseite!"));
	        	Label label = new Label("Auf unserer Webseite können Sie einen Nach- oder Zwischenmieter für Ihre Wohnung oder Ihr Zimmer finden. Als duale Studentin/ dualer Student der DHBW Karlsruhe können Sie die passende Wohnung oder das passende Zimmer finden (auch zur Zwischenmiete) und den Vermieter kontaktieren.");
	        	tab1Layout.addComponent(label);
	        textAccordion.addTab(tab1Layout, "Herzlich Willkommen",FontAwesome.HEART);//Tab 1 hinzufügen
         
	        //Tab 2
			VerticalLayout tab2Layout = new VerticalLayout();
	        tab2Layout.setMargin(true);
		        Label label_1 = new Label("Wenn Sie eine Wohnung vermieten wollen sind Sie bei uns genau richtig! Jeder registrierte Benutzer kann Wohnungen anbieten. Ihre Wohung oder Ihr Zimmer wird ausschließlich an Studierende der DHBW Karlsruhe vermietet. Registrieren Sie sich bitte hier.");
		        tab2Layout.addComponent(label_1);
		        
				//Link zu Registrierung
				Button registrierungLink = new Button();
				registrierungLink.setStyleName("link");
				registrierungLink.setCaption("Registrierung");
				registrierungLink.setImmediate(false);
				registrierungLink.setWidth("-1px");
				registrierungLink.setHeight("-1px");
				registrierungLink.setIcon(FontAwesome.EXTERNAL_LINK);
				registrierungLink.addClickListener(new Button.ClickListener(){
					private static final long serialVersionUID = 1L;
					public void buttonClick(ClickEvent event) {
						String name = "Registrierung";
						getUI().getNavigator().addView(name, new Registrierung());
						getUI().getNavigator().navigateTo(name);
					}
				});
				
				tab2Layout.addComponent(registrierungLink);
			textAccordion.addTab(tab2Layout, "Für Vermieter...",FontAwesome.HOME);//Tab 2 hinzufügen
        
	        //Tab 3
			VerticalLayout tab3Layout = new VerticalLayout();
	        tab3Layout.setMargin(true);
	        	tab3Layout.addComponent(new Label("Für alle, die eine Wohnung suchen...: Sie studieren an der DHBW Karlsruhe? Dann finden Sie bei uns die passende Wohnung oder das passende Zimmer. Wir haben ein großes Angebot an Zimmern und Wohnungen..."));
	
				//Link zu Registrierung
				Button registrierungLink_2 = new Button();
				registrierungLink_2.setStyleName("link");
				registrierungLink_2.setCaption("Registrierung");
				registrierungLink_2.setImmediate(false);
				registrierungLink_2.setWidth("-1px");
				registrierungLink_2.setHeight("-1px");
				registrierungLink_2.setIcon(FontAwesome.EXTERNAL_LINK);
				registrierungLink_2.addClickListener(new Button.ClickListener(){
					private static final long serialVersionUID = 1L;
					public void buttonClick(ClickEvent event) {
						String name = "Registrierung";
						getUI().getNavigator().addView(name, new Registrierung());
						getUI().getNavigator().navigateTo(name);
					}
				});
				
				tab3Layout.addComponent(registrierungLink_2);
				
			textAccordion.addTab(tab3Layout, "Für Mieter...",FontAwesome.SEARCH);//Tab 3 hinzufügen
        
	        //Tab 4
			VerticalLayout tab3layout = new VerticalLayout();
	        tab3layout.setMargin(true);
		        Label label_3 = new Label("Wir sind Studierende der DHBW Karlsruhe...");
		        Label label_4 = new Label("Sie haben Anregungen, Wünsche oder Kritik? Bitte kontaktieren Sie uns!");
		        tab3layout.addComponent(label_3);
		        tab3layout.addComponent(label_4);
		        
		        Link link_3 = new Link();
				link_3.setStyleName("text");
				link_3.setCaption("Kontakt");
				link_3.setImmediate(false);
				link_3.setWidth("-1px");
				link_3.setHeight("-1px");
				link_3.setIcon(FontAwesome.EXTERNAL_LINK);
				tab3layout.addComponent(link_3);
	        textAccordion.addTab(tab3layout, "Über uns...",FontAwesome.ENVELOPE);//Tab 4 hinzufügen
         
		layoutTextUndSuche.addComponent(textAccordion);//Begrüßungstext hinzufügen
		
		//Panel für Suchfeld
		Panel suchePanel = new Panel("Suche");
		suchePanel.setStyleName("startseite");
		//suchePanel.setWidth("632px");
		suchePanel.setHeight("400px");
		suchePanel.setIcon(FontAwesome.SEARCH);
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
				private static final long serialVersionUID = 1L;
				@Override
				public void buttonClick(ClickEvent event) {
					OfferProvider of = new OfferProvider();
					List<Offer> ergebnisse;				
					ergebnisse =
							of.filter(null,null,
							(Format.floatFormat("0.0")),
									(Format.floatFormat("0.0")), 
									(Format.floatFormat("0.0")),
									(Format.floatFormat("0.0")),
									7, 
									false, false, false, false,false,
							suchfeld.getValue());
					
					String name = "AngebotAnzeigen";
					getUI().getNavigator().addView(name, new Suchergebnis(ergebnisse));
					getUI().getNavigator().navigateTo(name);
										
				}
			});
			
		suchePanel.setContent(suche);
		layoutTextUndSuche.addComponent(suchePanel);//Panel hinzufügen
		
		content.addComponent(layoutTextUndSuche);//Begrüßungstext und Suchfeld hinzufügen
		
		//Neuste Angebote
		Panel panelNeusteANgebote = new Panel("Unsere neusten Angebote");
		panelNeusteANgebote.setStyleName("startseiteUnten");
		panelNeusteANgebote.setIcon(FontAwesome.HOME);
		panelNeusteANgebote.setWidth("100%");
		VerticalLayout layoutNuesteAnngebote = new VerticalLayout();
		layoutNuesteAnngebote.setMargin(true);
		
		List<Offer> latestOffers = new OfferProvider().getLatestOffers();
		for( Offer o : latestOffers) {
			layoutNuesteAnngebote.addComponent(new Listenzeile(o));
			layoutNuesteAnngebote.addComponent(new Label());
		}
		
        panelNeusteANgebote.setContent(layoutNuesteAnngebote);
		content.addComponent(panelNeusteANgebote);
    }
 
	@Override
	public void enter(ViewChangeEvent event) {
	}   
}