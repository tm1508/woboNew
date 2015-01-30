package com.example.housing;

import com.example.housing.data.model.Offer;
import com.vaadin.server.FontAwesome;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class MapWindow extends Window {

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
		final VerticalLayout hl = new VerticalLayout();
			
	    /** The kakola marker. */
	    GoogleMapMarker kakolaMarker = new GoogleMapMarker(
	            "Hier befindet sich die Wohnung", new LatLon(angebot.getLatitude().doubleValue(), angebot.getLongitude().doubleValue()),
	            false, null);
	   
        GoogleMap googleMap = new GoogleMap(null, null, null);
        googleMap.setCenter(new LatLon(angebot.getLatitude().doubleValue(), angebot.getLongitude().doubleValue()));
        googleMap.setZoom(10);
        //googleMap.setSizeFull();

        kakolaMarker.setAnimationEnabled(false);
        googleMap.addMarker(kakolaMarker);
        
        googleMap.setMinZoom(4);
        googleMap.setMaxZoom(16);

        googleMap.setHeight("473px");
        googleMap.setWidth("700px");
        hl.addComponent(googleMap);
	
	
		// Layout für das große Bild hinzufügen
		hl.setStyleName("picture");
		content.addComponent(hl);
		
		
		// kleine Bilder
				final HorizontalLayout hl1 = new HorizontalLayout();

		//Layout für kleine Bilder hinzufügen
		hl1.setStyleName("picture");
		content.addComponent(hl1);

		this.setContent(content);

		content.setExpandRatio(hl1, 1);//große Bilder bekommen mehr Platz
		content.setExpandRatio(hl, 4);

	

	}
}
