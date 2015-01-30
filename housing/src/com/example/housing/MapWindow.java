package com.example.housing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import com.example.housing.data.model.Offer;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinService;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginWindow.
 * 
 * @author MWI Wohungsbörse 2014
 * @version 1.0
 * @see com.example.housing.Registrierung
 */
/*
 * StyleNames (CSS) v-panel-box
 */
@SuppressWarnings("serial")
public class MapWindow extends Window {

	/**
	 * Instantiates a new login window.
	 */
	public MapWindow(Offer angebot) {
		super(" Standort zu diesem Wohnungsangebot");
		initialisieren(angebot);
	}

	/**
	 * Initialisieren.
	 * 
	 * @see com.vaadin.ui.Window
	 */
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
