package com.example.housing;

import java.util.List;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.OfferProvider;
import com.example.housing.data.provider.UserProvider;
import com.example.housing.utility.Format;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class Startseite.
 */
public class FAQ extends VerticalLayout implements View{

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
	 * Instantiates a new startseite.
	 */
	public FAQ(){
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
	}
	
	/**
	 * Sets the content.
	 */
	public void setContent(){
		
		content = new VerticalLayout();
		content.setMargin(true);
		content.addComponent(new Label("Hier finden Sie häufig gestellte Fragen und Antworten"));
	
		
		//Kategorien
		Accordion accordion = new Accordion();
		accordion.setStyleName("startseite");
	
		
			//Tab 1 ..zu Ihrem Account
			final VerticalLayout layout = new VerticalLayout();
			layout.setMargin(true);
			
	        //Unterkategorien zu Fragen zu Ihrem Account:
	        Accordion sub_accordion = new Accordion();
			sub_accordion.setStyleName("startseite");
			
			final Label label = new Label("Bitte versuchen Sie zunächst, sich mit Ihrer E-Mail-Adresse und Ihrem Passwort einzuloggen. \nWenn Sie Ihr Passwort vergessen haben, nutzen Sie die Funktion \"Passwort vergessen?\"");
			sub_accordion.addTab(label, "Ich kann mich nicht mehr einloggen. Was nun?", FontAwesome.QUESTION);
			
			//Unterkategorien zu Fragen zu Ihrem Account:
			final Label label_1 = new Label("Ihre Anmeldung läuft in einer Session. Sind Sie längere Zeit nicht aktiv, \nwird die Session beendet und Sie müssen sich erneut anmelden.");
			sub_accordion.addTab(label_1, "Was bedeutet \"Ihre Session ist abgelaufen\"?", FontAwesome.QUESTION);
			
			//Unterkategorien zu Fragen zu Ihrem Account:
			final Label label_2 = new Label("Um Ihr Account löschen zu können, müssen Sie sich zunächst einloggen. \nKlicken Sie anschließend auf die Registerkarte Persönliche Daten und Meine Profildaten. Dort finden Sie am Ende der Seite ein Button \"Profil löschen\". ");
			sub_accordion.addTab(label_2, "Wie lösche ich mein Account?", FontAwesome.QUESTION);
			
			layout.addComponent(sub_accordion);
	        accordion.addTab(layout, "zu Ihrem Accout",FontAwesome.THUMB_TACK);//Tab 1 hinzufügen
         
	        //Tab 2 zu Wohnungsangebote
			final VerticalLayout layout_1 = new VerticalLayout();
	        layout_1.setMargin(true);
	        //Unterkategorien zu Fragen zu Wohnungsangebote
	        Accordion sub_accordion_1 = new Accordion();
			sub_accordion_1.setStyleName("startseite");
			
			final Label label_3 = new Label("Um ein Angebot erstellen zu können, müssen Sie sich zunächst registrieren bzw. einloggen. \nKlicken Sie anschließend auf die Registerkarte Wohnung einstellen und Neue Wohnung anbieten.");
			sub_accordion_1.addTab(label_3, "Wie kann ich ein Wohnungsangebot erstellen?", FontAwesome.QUESTION);
			
			final Label label_4 = new Label("Um ein Angebot bearbeiten zu können, müssen Sie sich zunächst einloggen. \nKlicken Sie anschließend auf die Registerkarte Wohnung einstellen und Wohnungsangebote verwalten. \nNun erscheint eine Liste Ihrer Angebote. Wählen Sie das zu bearbeitende Angebot aus. \nAm Ende der Seite befindet sich ein Button \"Bearbeiten\".");
			sub_accordion_1.addTab(label_4, "Wie kann ich ein Wohnungsangebot bearbeiten?", FontAwesome.QUESTION);
			
			final Label label_5 = new Label("Um ein Angebot löschen zu können, müssen Sie sich zunächst einloggen. \nKlicken Sie anschließend auf die Registerkarte Wohnung einstellen und Wohnungsangebote verwalten. \nNun erscheint eine Liste Ihrer Angebote. Wählen Sie das gewünschte Angebot aus. \nAm Ende der Seite befindet sich ein Button \"Löschen\". Hinweis: Haben Sie vor das Angebot ggf. später wieder einzustellen ist es möglich, das Angebot für einen Zeitraum zu deaktivieren.");
			sub_accordion_1.addTab(label_5, "Wie kann ich ein Wohnungsangebot löschen?", FontAwesome.QUESTION);
			
			final Label label_6 = new Label("Um ein Angebot bearbeiten zu können, müssen Sie sich zunächst einloggen. \nKlicken Sie anschließend auf die Registerkarte Wohnung einstellen und Wohnungsangebote verwalten. \nNun erscheint eine Liste Ihrer Angebote. Wählen Sie das gewünschte Angebot aus. \nKlicken Sie den Button \"Bearbeiten\". Am Ende der Seite befindet sich ein Kästchen um das Angebot zu deaktivieren.");
			sub_accordion_1.addTab(label_6, "Wie kann ich ein Wohnungsangebot deaktivieren?", FontAwesome.QUESTION);
		        
			layout_1.addComponent(sub_accordion_1);
			accordion.addTab(layout_1, "zu Wohnungsangebote",FontAwesome.THUMB_TACK);//Tab 2 hinzufügen
        
	        //Tab 3
			final VerticalLayout layout_2 = new VerticalLayout();
	        layout_2.setMargin(true);
	        	layout_2.addComponent(new Label("Für alle, die eine Wohnung suchen...: Sie sind Dualer Student an der DHBW Karlsruhe? Dann finden Sie bei uns die passende Wohung oder das passende Zimmer. Wir haben ein großes Angebot an Zimmern und Wohnungen..."));
	
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
				
			accordion.addTab(layout_2, "Für Mieter...",FontAwesome.SEARCH);//Tab 3 hinzufügen
        
	        //Tab 4
			final VerticalLayout layout_3 = new VerticalLayout();
	        layout_3.setMargin(true);
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
	        accordion.addTab(layout_3, "Über uns...",FontAwesome.ENVELOPE);//Tab 4 hinzufügen
         
		content.addComponent(accordion);//verschiedene Kategorien hinzufügen
		

		
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
	
	

