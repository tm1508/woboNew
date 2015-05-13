package com.example.housing;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import com.example.housing.data.model.Offer;
import com.example.housing.data.provider.OfferProvider;
import com.vaadin.server.FontAwesome;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.MarkerClickListener;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class MapWindowSuchergebnisse extends Window {
	private static final long serialVersionUID = 1L;

	public MapWindowSuchergebnisse(List<Offer> angebot) {
		super(" Standort zu diesem Wohnungsangebot");
		initialisieren(angebot);
	}

	public void initialisieren(final List<Offer> angebot) {
		// Window
		this.center();
		this.setIcon(FontAwesome.MAP_MARKER);
		this.setResizable(true);
		this.setStyleName("image");

		// Rahmen für die Fotos erzeugen
		final VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		content.setStyleName("box");
		
		// Layout für das große Bild
		final VerticalLayout kartenLayout = new VerticalLayout();
		
		GoogleMap googleMap = new GoogleMap(null, null, null);
        googleMap.setCenter(new LatLon(49.00705, 8.40287));
        googleMap.setZoom(10);

		
		Iterator<Offer> it = angebot.iterator();
		while(it.hasNext()){
			final Offer o = it.next();
			//Map-Marker
			//Button wird deaktiviert, wenn keine Standortangaben in der DB sind
			if(o.getLatitude()!=null && o.getLatitude()!=BigDecimal.valueOf(0.0)){
				GoogleMapMarker mapMarker = new GoogleMapMarker(
			            o.getTitle(), new LatLon(o.getLatitude().doubleValue(), o.getLongitude().doubleValue()),
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
				// TODO Auto-generated method stub
				long id = clickedMarker.getId();
				OfferProvider op = new OfferProvider();
				Offer currentOffer = op.find((int) id);
				String name = "Einzelansicht";
				getUI().getNavigator().addView(name, new Einzelansicht(currentOffer));
				getUI().getNavigator().navigateTo(name);
				MapWindowSuchergebnisse.this.close();
			}
        });
	   
        
        googleMap.setMinZoom(4);
        googleMap.setMaxZoom(16);

        googleMap.setHeight("473px");
        googleMap.setWidth("700px");
        kartenLayout.addComponent(googleMap);
		kartenLayout.setStyleName("picture");
		content.addComponent(kartenLayout);
		
		final HorizontalLayout layoutUnten = new HorizontalLayout();
		layoutUnten.setStyleName("picture");
		content.addComponent(layoutUnten);

		this.setContent(content);
		content.setExpandRatio(layoutUnten, 1);//oberes Layout bekommt mehr Platz
		content.setExpandRatio(kartenLayout, 4);
	}
}
