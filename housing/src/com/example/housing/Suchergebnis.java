package com.example.housing;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.OfferProvider;
import com.example.housing.utility.SortByMonatsmiete;
import com.example.housing.utility.SortByOfferTime;
import com.example.housing.utility.SortByTitle;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.MarkerClickListener;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class Suchergebnis extends CustomHorizontalLayout implements View {
	private static final long serialVersionUID = 1L;

	private VerticalLayout content;
	private String sort= null;//Auswahlbox für Sortierung der Liste
	private List<Offer> angebote;
	final GoogleMap googleMap = new GoogleMap(null, null, null);
//	final Button karteEinblenden = new Button("Karte einblenden");
//	final Button karteausblenden = new Button("Karte ausblenden");
	final CheckBox karteAnzeigen = new CheckBox("Ergebnisse auf der Karte anzeigen", false);

	
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
		
		if(angebote.isEmpty()) {
			
			Label ergebnisString = new Label("Ihre Suche ergab leider keine Treffer.");
			ergebnisString.addStyleName("AbschnittLabel");
			content.addComponent(ergebnisString);
			content.addComponent(new Label());
		
			Button back = new Button("Zurück zur Suche");
			back.setIcon(FontAwesome.BACKWARD);
			back.addStyleName("SuchButton");
			back.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					String name = "Suche";
					getUI().getNavigator().addView(name, new Suche());
					getUI().getNavigator().navigateTo(name);
				}
			});
			content.addComponent(back);
			
		} else {
			//Anzahl der Treffer
			final Label ergebnisString = new Label("Ihre Suche ergab " + angebote.size()+" Treffer.");
			ergebnisString.addStyleName("AbschnittLabel");	
			content.addComponent(ergebnisString);
			
			//Checkbox Karte
			karteAnzeigen.addValueChangeListener(new ValueChangeListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void valueChange(final ValueChangeEvent event) {
					final boolean value = (boolean) event.getProperty().getValue();
					if(value) {
						googleMap.setVisible(true);
					} else {
						googleMap.setVisible(false);
					}
				}
			});
			content.addComponent(karteAnzeigen);
			
			//Karte
    		Iterator<Offer> it = angebote.iterator();
    		while(it.hasNext()){
    			final Offer o = it.next();
    			//Map-Marker
    			//Button wird deaktiviert, wenn keine Standortangaben in der DB sind
    			if(o.getLatitude()!=null && o.getLatitude()!=BigDecimal.valueOf(0.0)){
    				GoogleMapMarker mapMarker = new GoogleMapMarker(
    			            "ID "+ String.valueOf(o.getIdOffer())+ ": "+o.getTitle(), new LatLon(o.getLatitude().doubleValue(), o.getLongitude().doubleValue()),
    			            false, null);
    				mapMarker.setId(o.getIdOffer());
    		        mapMarker.setAnimationEnabled(false);
    		        
    		        googleMap.addMarker(mapMarker);
    			}
    		}

            googleMap.addMarkerClickListener(new MarkerClickListener() {
    			private static final long serialVersionUID = 1L;

    			@Override
    			public void markerClicked(GoogleMapMarker clickedMarker) {
    				long id = clickedMarker.getId();
    				OfferProvider op = new OfferProvider();
    				Offer currentOffer = op.find((int) id);
    				String name = "Einzelansicht";
    				getUI().getNavigator().addView(name, new Einzelansicht(currentOffer));
    				getUI().getNavigator().navigateTo(name);
    			
    			}
            });
    	   
     		googleMap.setCenter(new LatLon(49.00705, 8.40287));
            googleMap.setZoom(10);
            googleMap.setMinZoom(4);
            googleMap.setMaxZoom(16);

            googleMap.setHeight("473px");
            googleMap.setWidth("700px");
            
            googleMap.setVisible(false);
            
            content.addComponent(googleMap);
            content.addComponent(new Label());
			
			//Sortieren der Liste
			final NativeSelect sortBy = new NativeSelect("Sortieren der Ergebnisse nach");
			sortBy.setNullSelectionAllowed(false);
			sortBy.addItem(1);
			sortBy.setItemCaption(1, "Titel (alphabetisch)");
			sortBy.addItem(2);
			sortBy.setItemCaption(2, "Datum des Angebots (neustes zuerst)");
			sortBy.addItem(3);
			sortBy.setItemCaption(3, "Monatsmiete (billigste zuerst)");
			sortBy.addItem(4);
			sortBy.setItemCaption(4, "Titel (alphabetisch, invers)");
			sortBy.addItem(5);
			sortBy.setItemCaption(5, "Datum des Angebots (ältestes zuerst)");
			sortBy.addItem(6);
			sortBy.setItemCaption(6, "Monatsmiete (teuerstes zuerst)");
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
							Collections.reverse(angebote);
							reloadPage();
						}
						if(sortBy.getValue().equals(3)){
							Collections.sort(angebote, new SortByMonatsmiete());
							reloadPage();
						}
						if(sortBy.getValue().equals(4)){
							Collections.sort(angebote, new SortByTitle());
							Collections.reverse(angebote);
							reloadPage();
						}
						if(sortBy.getValue().equals(5)){
							Collections.sort(angebote, new SortByOfferTime());
							
							reloadPage();
						}
						if(sortBy.getValue().equals(6)){
							Collections.sort(angebote, new SortByMonatsmiete());
							Collections.reverse(angebote);
							reloadPage();
						}
					}
				}
				
				public void reloadPage(){//alle Komponenten der Seite müssen neu geladen werden
					boolean showMap = karteAnzeigen.getValue();
					content.removeAllComponents();
					content.addComponent(ergebnisString);
//					content.addComponent(new Label());
					karteAnzeigen.setValue(showMap);
					content.addComponent(karteAnzeigen);
					googleMap.setVisible(showMap);
					content.addComponent(googleMap);
					content.addComponent(new Label());
					content.addComponent(sortBy);
					content.addComponent(new Label());
//					content.addComponent(karteEinblenden);
//					karteEinblenden.setVisible(true);
//					karteausblenden.setVisible(false);
//					content.addComponent(karteausblenden);
//					content.addComponent(new Label());

		        	//Button wird deaktiviert, wenn der Nutzer kein DH Stud. ist
		    		if((boolean) VaadinSession.getCurrent().getSession().getAttribute("login") && ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getAccessLevel() != 0) {
		    			//tue nichts
		    		}else{
		    			karteAnzeigen.setEnabled(false);
		    			karteAnzeigen.setDescription("Die Kartenansicht ist nur für verifizierte DH-Studenten verfügbar.");
		    			googleMap.setVisible(false);
//		    			Label l = new Label("Die Kartenansicht ist nur für verifizierte DH-Studenten verfügbar.");
//		    			content.addComponent(l);
		    		}
		    		
					for(Offer o : angebote) {
						content.addComponent(new Listenzeile(o));
					}
				}
			});
         	
//         	karteEinblenden.setIcon(FontAwesome.MAP_MARKER);
//         	content.addComponent(karteEinblenden);
//         	karteEinblenden.setVisible(false);
//             
//    		
//    		karteausblenden.setIcon(FontAwesome.MAP_MARKER);
//    		content.addComponent(karteausblenden);
//    		karteausblenden.addClickListener(new Button.ClickListener() {
//    			private static final long serialVersionUID = 1L;
//
//    			@Override
//    			public void buttonClick(ClickEvent event) {
//    				
//    				
//    					googleMap.setVisible(false);
//    					karteausblenden.setVisible(false);
//    			
//    					karteEinblenden.setVisible(true);
//  
//    				
//    			}
//    		});
//    		
//    		
//    		karteEinblenden.addClickListener(new Button.ClickListener() {
//    			private static final long serialVersionUID = 1L;
//
//
//    			@Override
//    			public void buttonClick(ClickEvent event) {
//    					googleMap.setVisible(true);
//    					karteausblenden.setVisible(true);
//    					karteEinblenden.setVisible(false);
//    			}
//    		});
        	
        	//Button wird deaktiviert, wenn der Nutzer kein DH Stud. ist
    		if((boolean) VaadinSession.getCurrent().getSession().getAttribute("login") && ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getAccessLevel() != 0) {
    			//tue nichts
    		}else{
    			karteAnzeigen.setEnabled(false);
    			karteAnzeigen.setDescription("Die Kartenansicht ist nur für verifizierte DH-Studenten verfügbar.");
    			googleMap.setVisible(false);
//    			Label l = new Label("Die Kartenansicht ist nur für verifizierte DH-Studenten verfügbar.");
//    			content.addComponent(l);
    		}
		
			content.addComponent(new Label());
			
			for(Offer o : angebote) {
				content.addComponent(new Listenzeile(o));
			}
		
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}