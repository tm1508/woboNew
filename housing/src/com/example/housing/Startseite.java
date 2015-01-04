package com.example.housing;

import com.example.housing.data.model.User;
import com.example.housing.data.provider.UserProvider;
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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class Startseite.
 */
public class Startseite extends VerticalLayout implements View{

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
	public Startseite(){
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
		/*Button button = new Button("Suche");
		button.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String s = "Suche";
				getUI().getNavigator().addView(s, new Suche());
				getUI().getNavigator().navigateTo(s);	
			}
		});
		content.addComponent(button);*/
		
		//TODO Inhalt einfügen
		
		
		//Horizontales Layout fuer Begrüßungstext und Suchfeld
		HorizontalLayout h = new HorizontalLayout();
		h.setWidth("100%");
		h.setMargin(false);
		
		//Begrüßungstext
		Accordion accordion = new Accordion();
		accordion.setHeight("400px");
		
			//Tab 1
			final VerticalLayout layout = new VerticalLayout();
	        layout.setMargin(true);
	        layout.addComponent(new Label("Herzlich Willkommen auf unserer Webseite!"));
	        	Label label = new Label("Auf unserer Webseite können Sie einen Nach- oder Zwischenmieter für Ihre Wohung oder Ihr Zimmer finden. Als Dualer Student der DHBW Karlsruhe können Sie die passende Wohung oder das passende Zimmer finden (auch zur Zwischenmiete) und den Vermieter kontaktieren.");
	        	layout.addComponent(label);
	        accordion.addTab(layout, "Herzlich Willkommen",FontAwesome.HEART);//Tab 1 hinzufügen
         
	        //Tab 2
			final VerticalLayout layout_1 = new VerticalLayout();
	        layout_1.setMargin(true);
		        Label label_1 = new Label("Wenn Sie eine Wohnung vermieten wollen sind Sie bei uns genau richtig! Jeder registrierte Benutzer kann Wohnungen anbieten. Registrieren Sie sich bitte hier. Ihre Wohung oder Ihr Zimmer wird ausschließlich an Studenten der DHBW Karlsruhe vermietet.");
		        layout_1.addComponent(label_1);
		        
		        Link link_1 = new Link();
				link_1.setStyleName("text");
				link_1.setCaption("Registrierung");
				link_1.setImmediate(false);
				link_1.setWidth("-1px");
				link_1.setHeight("-1px");
				link_1.setIcon(FontAwesome.EXTERNAL_LINK);
				layout_1.addComponent(link_1);
			accordion.addTab(layout_1, "Für Vermieter...",FontAwesome.HOME);//Tab 2 hinzufügen
        
	        //Tab 3
			final VerticalLayout layout_2 = new VerticalLayout();
	        layout_2.setMargin(true);
	        	layout_2.addComponent(new Label("Für alle, die eine Wohnung suchen...: Sie sind Dualer Student an der DHBW Karlsruhe? Dann finden Sie bei uns die passende Wohung oder das passende Zimmer. Wir haben ein großes Angebot an Zimmer und Wohnungen..."));
	
		        Link link_2 = new Link();
				link_2.setStyleName("text");
				link_2.setCaption("Registrierung");
				link_2.setImmediate(false);
				link_2.setWidth("-1px");
				link_2.setHeight("-1px");
				link_2.setIcon(FontAwesome.EXTERNAL_LINK);
				layout_2.addComponent(link_2);
			accordion.addTab(layout_2, "Für Mieter...",FontAwesome.SEARCH);//Tab 3 hinzufügen
        
	        //Tab 4
			final VerticalLayout layout_3 = new VerticalLayout();
	        layout_3.setMargin(true);
		        Label label_3 = new Label("Wir sind DH-Studenten der DHBW Karlsruhe...");
		        Label label_4 = new Label("Sie haben Anregungen, Wünsche oder Kritik? Bitte kontaktieren Sie uns! Falls Sie als Austauschstudent an die Duale Hochschule Karlsruhe kommen, bitte kontaktieren Sie den Administrator, damit Sie als Dualer Student freigeschaltet werden.");
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
         
		h.addComponent(accordion);//Begrüßungstext hinzufügen
		
		//Panel für Suchfeld
		Panel panel = new Panel("Suche");
		panel.setHeight("400px");
		VerticalLayout suche = new VerticalLayout();
		suche.setMargin(true);
		
			//Suchfeld
			TextField suchfeld = new TextField();
			suchfeld.setCaption("Suchbegriff eingeben...");
			suchfeld.setDescription("Bitte Suchbegriff eingeben");
			suchfeld.setWidth("220px");
			suchfeld.setHeight("-1px");
			suchfeld.addStyleName("textfield");
			suche.addComponent(suchfeld);
			
			//Button zum Starten derv Suche
			Button sucheStarten = new Button();
			sucheStarten.setCaption("Suche starten");
			sucheStarten.setDescription("Bitte Suchbegriff eingeben");
			sucheStarten.setWidth("-1px");
			sucheStarten.setHeight("-1px");
			sucheStarten.setIcon(FontAwesome.SEARCH);
			suche.addComponent(sucheStarten);

		panel.setContent(suche);
		h.addComponent(panel);//Panel hinzufügen
		
		content.addComponent(h);//Begrüßungstext und Suchfeld hinzufügen
		
		//Neuste Angebote
		Panel p = new Panel("Unsere neusten Angebote");
		VerticalLayout v = new VerticalLayout();
		v.setMargin(true);
		
		
        Table tabelle = new Table();
        tabelle.setSizeFull();
        tabelle.setSelectable(true);
        tabelle.setMultiSelect(true);
        tabelle.setImmediate(true);
        v.addComponent(tabelle);
		p.setContent(v);
		
		content.addComponent(p);
		
		/*
		//Datenbank-Test: Versuche User auszugeben aus Datenbank
		User user = new UserProvider().doQueryOneResult(Long.getLong("1"));
		Panel p = new Panel("User: " + user.getEmail());
		VerticalLayout v = new VerticalLayout();
		v.setMargin(true);
		
		
        Table tabelle = new Table();
        tabelle.setSizeFull();
        tabelle.setSelectable(true);
        tabelle.setMultiSelect(true);
        tabelle.setImmediate(true);
        v.addComponent(tabelle);
		p.setContent(v);
		
		content.addComponent(p);
		*/
		
	}
	
	
}
