package com.example.housing;


import java.math.BigDecimal;
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
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class Suchergebnis.
 */
public class Suchergebnis extends CustomHorizontalLayout implements View {
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
		
		content = super.initCustomHorizontalLayout();
		setContent();
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
		
	    //Map-Button
        Button map = new Button("Karte anzeigen");
        map.setIcon(FontAwesome.MAP_MARKER);
        map.addStyleName("AnfrageButton");
        map.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				MapWindowSuchergebnisse w = new MapWindowSuchergebnisse(angebote);//neues Fenster mit Karte wird geöffnet
				UI.getCurrent().addWindow(w);
			}
        });
        content.addComponent(map);
        
        //Button wird deaktiviert, wenn der Nutzer kein DH Stud. ist
		if(VaadinSession.getCurrent().getSession().getAttribute("login").equals(true) && ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getAccessLevel() != 0) {
			//tue nichts
		}else{
			map.setEnabled(false);
			Label l = new Label("Die Kartenansicht ist nur für verifizierte DH-Studenten verfügbar.");
			content.addComponent(l);
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