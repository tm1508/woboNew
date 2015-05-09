package com.example.housing;


import java.util.Collections;
import java.util.List;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.example.housing.utility.SortByMonatsmiete;
import com.example.housing.utility.SortByOfferTime;
import com.example.housing.utility.SortByTitle;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class Suchergebnis.
 */
public class Suchergebnis extends HorizontalLayout implements View {
	private static final long serialVersionUID = 1L;

	VerticalLayout content;
	String sort= null;//Auswahlbox für Sortierung der Liste
	List<Offer> angebote; 
	
	//Übergabe der Ergebnis aus der Suche
	/**
	 * Instantiates a new suchergebnis.
	 *
	 * @param offers the offers
	 */
	public Suchergebnis(List<Offer> offers){
		this.angebote = offers;
		
		content = new VerticalLayout();
    	content.setMargin(true);
		content.setSizeFull();
		content.setSpacing(true);
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
		
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Suchergebnisse");
		title.addStyleName("title");
		content.addComponent(title);
		
		if(angebote.size()==0) {
			
			Label ergebnisString = new Label("Ihre Suche ergab leider keine Treffer.");
			ergebnisString.addStyleName("AbschnittLabel");
			content.addComponent(ergebnisString);
			
		} else {
			//Anzahl der Treffer
			final Label ergebnisString = new Label("Ihre Suche ergab " + angebote.size()+" Treffer:");
			ergebnisString.addStyleName("AbschnittLabel");	
			content.addComponent(ergebnisString);
			
			//Sortieren der Liste
			final NativeSelect sortBy = new NativeSelect("Sortieren der Ergebnisse nach");
			sortBy.setNullSelectionAllowed(false);
			sortBy.addItem(1);
			sortBy.setItemCaption(1, "Titel (alphabetisch)");
			sortBy.addItem(2);
			sortBy.setItemCaption(2, "Datum des Angebots (neustes zuerst)");
			sortBy.addItem(3);
			sortBy.setItemCaption(3, "Monatsmiete (billigste zuerst)");
			sortBy.setValue(2);
			content.addComponent(sortBy);
			sortBy.addValueChangeListener(new ValueChangeListener() {
				private static final long serialVersionUID = 1L;
				@Override
				public void valueChange(final ValueChangeEvent event) {
					sort = event.getProperty().getValue().toString();
					if(sort != null){
						if(sortBy.getValue().equals(1)){
							Collections.sort(angebote, new SortByTitle());
							reloadPage();
						}
						if(sortBy.getValue().equals(2)){
							Collections.sort(angebote, new SortByOfferTime());
							reloadPage();
						}
						if(sortBy.getValue().equals(3)){
							Collections.sort(angebote, new SortByMonatsmiete());
							reloadPage();
						}
					}
				}
				
				public void reloadPage(){//alle Komponenten der Seite müssen neu geladen werden
					content.removeAllComponents();
					content.addComponent(ergebnisString);
					content.addComponent(sortBy);
					content.addComponent(new Label());
					for(Offer o : angebote) {
						content.addComponent(new Listenzeile(o));
					}
				}
			});

		}
		
		content.addComponent(new Label());
		for(Offer o : angebote) {
			content.addComponent(new Listenzeile(o));
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}