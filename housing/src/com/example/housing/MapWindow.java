package com.example.housing;

import com.example.housing.data.model.Offer;
import com.vaadin.server.FontAwesome;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


public class MapWindow extends Window {
	private static final long serialVersionUID = 1L;

	public MapWindow(Offer angebot) {
		super(" Standort zu diesem Wohnungsangebot");
		initialisieren(angebot);
	}

	public void initialisieren(final Offer angebot) {
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
			
	    //Map-Marker
	    GoogleMapMarker mapMarker = new GoogleMapMarker(
	            "Hier befindet sich die Wohnung", new LatLon(angebot.getLatitude().doubleValue(), angebot.getLongitude().doubleValue()),
	            false, null);
	   
        GoogleMap googleMap = new GoogleMap(null, null, null);
        googleMap.setCenter(new LatLon(angebot.getLatitude().doubleValue(), angebot.getLongitude().doubleValue()));
        googleMap.setZoom(10);

        mapMarker.setAnimationEnabled(false);
        googleMap.addMarker(mapMarker);
        
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
