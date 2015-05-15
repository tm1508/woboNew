package com.example.housing;

import java.math.BigDecimal;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Photo;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.OfferProvider;
import com.example.housing.data.provider.PhotoProvider;
import com.example.housing.utility.Format;
import com.example.housing.utility.MapAddressConverter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.MarkerDragListener;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class AngebotErstellen.
 */
public class AngebotErstellen extends CustomHorizontalLayout implements View, Receiver, SucceededListener {

	private static final long serialVersionUID = 1L;

	/** The content. */
	private VerticalLayout content;
	
	private Double lat = null;
    private Double lon = null;
    private Label title;
    Fotos f;

	private Offer currentOffer;

	private List<Photo> newPhotos;

	public Offer getCurrentOffer() {
		return currentOffer;
	}

	public void setCurrentOffer(Offer currentOffer) {
		this.currentOffer = currentOffer;
	}

	public List<Photo> getNewPhotos() {
		return newPhotos;
	}

	public void setNewPhotos(List<Photo> newPhotos) {
		this.newPhotos = newPhotos;
	}

	private ByteArrayOutputStream tmpImg;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener
	 * .ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	/**
	 * Instantiates a new angebot erstellen.
	 */
	// neues Angebot erstellen
	public AngebotErstellen() {

		currentOffer = new Offer();
		currentOffer.setCity(" ");
		currentOffer.setStartDate(new Date());
		currentOffer.setTitle(" ");
		currentOffer.setStreet(" ");
		currentOffer.setZip(" ");
		currentOffer.setInactive(true);
		currentOffer.setOffer_idUser((User) VaadinSession.getCurrent().getSession().getAttribute("user"));
		new OfferProvider().addOffer(currentOffer);
		
		content = super.initCustomHorizontalLayout();
		setContent();
	}

	// bereits bestehendes Angebot bearbeiten
	public AngebotErstellen(Offer offer) {

		currentOffer = offer;
		newPhotos = new ArrayList();

		content = super.initCustomHorizontalLayout();
		setContent(offer);
	}

	/**
	 * Sets the content.
	 */
	// Inhalt der Seite für neues Angebot
	public void setContent() {


		title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Wohnungsangebot erstellen");
		title.addStyleName("title");
		
		content.addComponent(title);
		

        
		// Titel, Map, Adresse
		Label ltitel = new Label("Titel");
		ltitel.setWidth("10%");
		ltitel.addStyleName("AbschnittLabel");
		final TextField titel = new TextField();
		titel.setRequired(true);
		titel.setRequiredError("Bitte geben Sie einen Titel an.");
		titel.setWidth("80%");
		titel.addStyleName("ImportantTitle");
		content.addComponent(ltitel);
		content.addComponent(titel);
		content.addComponent(new Label());
		
	
		
		Label adress = new Label("Adresse");
		adress.addStyleName("AbschnittLabel");
		final TextField street = new TextField("Straße, Hausnummer:");
		street.setRequired(true);
		street.setRequiredError("Bitte geben Sie Straße und Hausnummer an.");
		street.addStyleName("AngeboteTextField");
		HorizontalLayout hl0 = new HorizontalLayout();
		hl0.setWidth("50%");
		final TextField zip = new TextField("PLZ:");
		zip.setRequired(true);
		zip.setRequiredError("Bitte geben Sie die Postleitzahl an.");
		zip.setWidth("50%");
		zip.addStyleName("AngeboteTextField");
		final TextField city = new TextField("Ort:");
		city.setRequired(true);
		city.setRequiredError("Bitte geben Sie den Ort an.");
		city.addStyleName("AngeboteTextField");
		city.setWidth("50%");
		hl0.addComponent(zip);
		hl0.addComponent(city);
		
		content.addComponent(ltitel);
		content.addComponent(titel);
		content.addComponent(new Label());
		content.addComponent(adress);
		//content.addComponent(new Label());
		content.addComponent(street);
		content.addComponent(hl0);
		content.addComponent(new Label());
		
		final CheckBox mitKarteSuchen = new CheckBox(
				"Alternativ auf der Karte suchen", false);
		content.addComponent(mitKarteSuchen);
		
	   //Google Maps
	    GoogleMapMarker mapMarker = new GoogleMapMarker(
	            "Karlsruhe", new LatLon(49.00705, 8.40287),
	            true, null);
	    
		final GoogleMap googleMap = new GoogleMap(null, null, null);
        googleMap.setCenter(new LatLon(49.00705, 8.40287));
        googleMap.setZoom(10);
        googleMap.setVisible(false);
        googleMap.setSizeFull();
        mapMarker.setAnimationEnabled(false);
        googleMap.addMarker(mapMarker);
        googleMap.setMinZoom(4);
        googleMap.setMaxZoom(16);
        googleMap.setHeight("500px");
        googleMap.setWidth("500px");
        content.addComponent(googleMap);
        content.addComponent(new Label());
        
        googleMap.addMarkerDragListener(new MarkerDragListener() {
			@Override
			public void markerDragged(GoogleMapMarker draggedMarker,
					LatLon oldPosition) {
				lat = draggedMarker.getPosition().getLat();
				lon = draggedMarker.getPosition().getLon();		
				
				List<String> l = MapAddressConverter.getAddressFromLatLon(lat, lon);
				street.setValue(l.get(0));
				zip.setValue(l.get(1));
				city.setValue(l.get(2));
			}
        });
        
    	mitKarteSuchen.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(final ValueChangeEvent event) {
				final boolean value = (boolean) event.getProperty().getValue();
				if (value == true) {// Anzeigen der Moodle Felder sobald das
									// Kontrollkästchen angekreuzt wird
					city.setEnabled(false);
					city.setValue("");
					
					street.setValue("");
					street.setEnabled(false);
					zip.setValue("");
					zip.setEnabled(false);
					
					googleMap.setVisible(true);
				} else {// ausblednen der Felder wenn das Kästchen nicht
						// angekreuzt ist
					city.setEnabled(true);
					street.setEnabled(true);
					zip.setEnabled(true);
					googleMap.setVisible(false);
				}
			}
		});
        
		// Allgemeine Informationen
		HorizontalLayout label = new HorizontalLayout();
		label.setWidth("100%");
		HorizontalLayout z1 = new HorizontalLayout();
		z1.setWidth("100%");
		HorizontalLayout z2 = new HorizontalLayout();
		z2.setWidth("100%");
		HorizontalLayout z3 = new HorizontalLayout();
		z3.setWidth("100%");
		Label allgInfo = new Label("Allgemeine Angaben zur Wohnung");
		allgInfo.addStyleName("AbschnittLabel");
		label.addComponent(allgInfo);
		final ComboBox isShared = new ComboBox("Art");
		isShared.setRequired(true);
		isShared.setRequiredError("Bitte geben sie die Art des Angebots an.");
		isShared.addItem("Wohnung");
		isShared.addItem("Zimmer");
		isShared.addItem("WG-Zimmer");
		isShared.addStyleName("AngeboteTextField");
		z1.addComponent(isShared);
		final TextField squareMetre = new TextField("Größe (in m²):");
		squareMetre.setRequired(true);
		squareMetre.setRequiredError("Bitte geben Sie die Größe in m² an.");
		squareMetre.addStyleName("AngeboteTextField");
		z2.addComponent(squareMetre);
		final TextField roomMates = new TextField("Anzahl Mitbewohner:");
		roomMates.setRequired(true);
		roomMates.setRequiredError("Bitte geben Sie die Anzahl an Mitbewohner an.");
		//roomMates.setValue("0");
		roomMates.addStyleName("AngeboteTextField");
		z3.addComponent(roomMates);
		Label date = new Label("Verfügbarkeit");
		date.addStyleName("AbschnittLabel");
		final DateField startDate = new DateField("von:");
		startDate.setRequired(true);
		startDate.setRequiredError("Bitte geben Sie ein Startdatum an.");
		final DateField endDate = new DateField("bis:");
		HorizontalLayout hl1 = new HorizontalLayout();
		startDate.addStyleName("AngeboteTextField");
		endDate.addStyleName("AngeboteTextField");
		hl1.setWidth("50%");
		hl1.addComponent(startDate);
		hl1.addComponent(endDate);

		// Kosten
		Label costs = new Label("Kosten");
		costs.addStyleName("AbschnittLabel");
		label.addComponent(costs);
		final TextField price = new TextField("Warmmiete inkl. Nebenkosten (in €):");
		price.setRequired(true);
		price.setRequiredError("Bitte geben Sie die Warmmiete an.");
		price.addStyleName("AngeboteTextField");
		z1.addComponent(price);
		final TextField bond = new TextField("Kaution (in €):");
		bond.addStyleName("AngeboteTextField");
		z2.addComponent(bond);
		//TextField cost = new TextField("Sonstige einmalige Kosten (in €):");
		//cost.addStyleName("AngeboteTextField");
		//z3.addComponent(cost);

		content.addComponent(label);
		//content.addComponent(new Label());
		content.addComponent(z1);
		content.addComponent(z2);
		content.addComponent(z3);
		content.addComponent(new Label());

		content.addComponent(date);
		content.addComponent(hl1);
		content.addComponent(new Label());

		// weitere Angaben
		Label angaben = new Label("Weitere Details");
		angaben.addStyleName("AbschnittLabel");
		
		final CheckBox internet = new CheckBox("Internetanschluss vorhanden");
		internet.setWidth("20%");
		internet.addStyleName("AngeboteTextField");
		final CheckBox furnished = new CheckBox("Möblierte Wohnung");
		furnished.setWidth("20%");
		furnished.addStyleName("AngeboteTextField");
		final CheckBox kitchen = new CheckBox("Küche vorhanden");
		kitchen.setWidth("20%");
		kitchen.addStyleName("AngeboteTextField");
		final CheckBox smoker = new CheckBox("Raucher-Wohnung");
		smoker.setWidth("20%");
		smoker.addStyleName("AngeboteTextField");
		final CheckBox pets = new CheckBox("Haustiere erlaubt");
		pets.setWidth("20%");
		pets.addStyleName("AngeboteTextField");
		final ComboBox genders = new ComboBox("Bevorzugtes Geschlecht:");
		genders.setRequired(true);
		genders.setRequiredError("Bitte geben Sie gegebenenfalls ein bevorzugtes Geschlecht an.");
		genders.addItem("egal");
		genders.addItem("männlich");
		genders.addItem("weiblich");
		genders.addStyleName("AngeboteTextField");

		HorizontalLayout hl2 = new HorizontalLayout();
		hl2.setWidth("100%");
		hl2.addComponent(internet);
		hl2.addComponent(furnished);
		hl2.addComponent(kitchen);
		hl2.addComponent(smoker);
		hl2.addComponent(pets);
		
		content.addComponent(angaben);
		//content.addComponent(new Label());
		content.addComponent(hl2);
		content.addComponent(new Label());
		content.addComponent(genders);
		content.addComponent(new Label());

		// Anzeigetext + Wohnungsbilder
		Label anzeigetext = new Label("Beschreibung");
		anzeigetext.addStyleName("AbschnittLabel");
		final RichTextArea text = new RichTextArea();
		text.setRequired(true);
		text.setRequiredError("Bitte geben Sie eine kurze Beschreibung des Angebots an.");
		text.setWidth("100%");
		
		content.addComponent(anzeigetext);
		//content.addComponent(new Label());
		content.addComponent(text);
		content.addComponent(new Label());
		
		/*Label bilder = new Label("Bilder");
		bilder.addStyleName("AbschnittLabel");
		
		Upload bilderup = new Upload("Fotos hochladen (max. 5 Fotos pro Angebot):", this);
		bilderup.addSucceededListener(this);
		
		content.addComponent(bilder);
		//content.addComponent(new Label());
		content.addComponent(bilderup);
		content.addComponent(new Label());*/
		


		// Button speichern/aktivieren/deaktivieren
		final CheckBox inactive = new CheckBox("deaktivieren");
		
		content.addComponent(inactive);
		content.addComponent(new Label());
		
		//Buttons
		Button save = new Button("Speichern");
		save.setIcon(FontAwesome.SAVE);
		save.addStyleName("BearbeitenButton");
		save.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				// Überprüfung ob alle Mussfelder gefüllt sind
				boolean valid = true; // fals ein Mussfeld nicht gefüllt ist
										// wird valid = false gesetzt
				try {
					titel.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					street.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					zip.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					city.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					genders.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					isShared.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					squareMetre.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					price.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					roomMates.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					bond.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					text.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}

				// Überprüft welche Art Angebot erstellt wird und setzt
				// entsprechenden int-Wert
				int type = 1;
				try {
					String shared = (String) isShared.getValue();
					if (shared.equals("Wohnung")) {
						type = 1;
					} else if (shared.equals("Zimmer")) {
						type = 2;
					} else if (shared.equals("WG-Zimmer")) {
						type = 3;
					}
				} catch (NullPointerException e) {// tut nichts, fängt nur
													// NullPointerException ab
				}

				// Überprüft ob es ein bevorzugtes Geschlecht gibt und setzt
				// entsprechenden int-Wert
				int gender = 1;
				try {
					String gend = (String) genders.getValue();
					if (gend.equals("egal")) {
						gender = 1;
					} else if (gend.equals("männlich")) {
						gender = 2;
					} else if (gend.equals("weiblich")) {
						gender = 3;
					}
				} catch (NullPointerException e) {// tut nichts, fängt nur
													// NullPointerException ab
				}

				if (valid) {// sind alle Mussfelder gefüllt, wird ein neues
							// Angebot erstellt
					try { //falls in Zahlenfeder keine Zahlen eingetragen wurden
						
						currentOffer.setSquareMetre(Format.floatFormat(squareMetre.getValue()));
						currentOffer.setPrice(Format.floatFormat(price.getValue()));
						currentOffer.setBond(Format.floatFormat(bond.getValue()));
						
					} catch (NumberFormatException nfe) {
						
						Notification failNumberFormat = new Notification("Bitte überprüfen Sie Ihre Eingaben und benutzen Sie gültige Zahlenformate.");
						failNumberFormat.setDelayMsec(300);
						failNumberFormat.setStyleName("failure");
						failNumberFormat.show(Page.getCurrent());
						return;
						
					}
					
					try { //falls in Anzahl Mitbewohner keine richtige Zahl eingetragen wurde
						
						currentOffer.setNumberOfRoommate(Integer.parseInt(roomMates.getValue()));
						
					} catch (Exception e) {
						
						Notification failNumberFormat = new Notification("Bitte überprüfen Sie Ihre Eingaben.");
						failNumberFormat.setDelayMsec(300);
						failNumberFormat.setStyleName("failure");
						failNumberFormat.show(Page.getCurrent());
						return;
						
					}
					
					try { //Start-Datum validieren
						
						Format.dateFormat(startDate.getValue());
						currentOffer.setStartDate(startDate.getValue());
						
					} catch (Exception e) {
						if(e.getClass().equals(NullPointerException.class)) {
							
							Notification failNotFilled = new Notification("Bitte füllen Sie alle Mussfelder*", Type.HUMANIZED_MESSAGE);
							failNotFilled.setStyleName("failure");
							failNotFilled.setDelayMsec(300);
							failNotFilled.show(Page.getCurrent());
							return;
						
						} else {
							
							Notification failNotFilled = new Notification("Bitte geben Sie ein gültiges Datum an!", Type.HUMANIZED_MESSAGE);
							failNotFilled.setStyleName("failure");
							failNotFilled.setDelayMsec(300);
							failNotFilled.show(Page.getCurrent());
							return;
							
						}	
					}
					try { //End-Datum validieren
						
						Format.dateFormat(endDate.getValue());
						currentOffer.setEndDate(endDate.getValue());
						
					} catch (Exception e) {
						if(e.getClass().equals(NullPointerException.class)) {
							//nix, da End-Datum optional ist
						} else {
							
							Notification failNotFilled = new Notification("Bitte geben Sie ein gültiges Datum an!", Type.HUMANIZED_MESSAGE);
							failNotFilled.setStyleName("failure");
							failNotFilled.setDelayMsec(300);
							failNotFilled.show(Page.getCurrent());
							return;
							
						}	
					}
					
					currentOffer.setOffer_idUser((User) VaadinSession.getCurrent().getSession().getAttribute("user"));
					currentOffer.setTitle(titel.getValue());
					currentOffer.setStreet(street.getValue());
					currentOffer.setZip(zip.getValue());
					currentOffer.setCity(city.getValue());
					
					currentOffer.setType(type);
					currentOffer.setInternet(internet.getValue());
					currentOffer.setFurnished(furnished.getValue());
					currentOffer.setKitchen(kitchen.getValue());
					currentOffer.setSmoker(smoker.getValue());
					currentOffer.setPets(pets.getValue());
					currentOffer.setGender(gender);
					
					//Beschreibungstext aufbereiten
			        String t = text.getValue().replace("<br>", "\n");
			        String tx = Jsoup.parse(t).text();
					currentOffer.setText(tx);
					
					currentOffer.setInactive(inactive.getValue());
					
					if(lat != null){
						currentOffer.setLatitude(BigDecimal.valueOf(lat));
						currentOffer.setLongitude(BigDecimal.valueOf(lon));
					}else{
						String adresse = street.getValue()+","+zip.getValue()+","+city.getValue();
						System.out.println(adresse);
						adresse = adresse.replaceAll(" ", "");
						System.out.println(adresse);
						LatLon l = MapAddressConverter.getLatLonFromAddress(adresse);
						currentOffer.setLatitude(BigDecimal.valueOf(l.getLat()));
						currentOffer.setLongitude(BigDecimal.valueOf(l.getLon()));
					}
					
					
					try {
						VaadinSession.getCurrent().getLockInstance().lock();
						VaadinSession.getCurrent().getSession().setAttribute("buttonClicked", true);
					} finally {
						VaadinSession.getCurrent().getLockInstance().unlock();
					}
					
					
					if (new OfferProvider().alterOffer(currentOffer)) { //neues Angebot in die DB schreiben
						
						Offer o = new OfferProvider().find(currentOffer.getIdOffer());
						
						Notification success = new Notification("Ihr neues Angebot wurde gespeichert.", Type.HUMANIZED_MESSAGE);
						success.setStyleName("success");
						success.setDelayMsec(300);
						success.show(Page.getCurrent());
						
						String name = "Einzelansicht";
						getUI().getNavigator().addView(name, new Einzelansicht(o));
						getUI().getNavigator().navigateTo(name);
						
					} else { //Fehler beim DB-Zugriff
						
						Notification failDB = new Notification("Das Angebot konnte nicht gespeichert werden.", Type.HUMANIZED_MESSAGE);
						failDB.setStyleName("failure");
						failDB.setDelayMsec(300);
						failDB.show(Page.getCurrent());
						
					}

				} else {
					
					// Sind nicht alle Mussfelder gefüllt, wird eine Nachricht
					// auf dem Bildschirm ausgegeben
					//Notification.show("");
					Notification failNotFilled = new Notification("Bitte füllen Sie alle Mussfelder*", Type.HUMANIZED_MESSAGE);
					failNotFilled.setStyleName("failure");
					failNotFilled.setDelayMsec(300);
					failNotFilled.show(Page.getCurrent());
					
				}
					
			}
		});
		
		Button abbrechen = new Button();
		abbrechen.setIcon(FontAwesome.MAIL_REPLY);
		abbrechen.addStyleName("BearbeitenButton");
		abbrechen.setCaption("Abbrechen");
		abbrechen.setImmediate(true);
		abbrechen.setDescription("Abbrechen der Bearbeitung. Ihre Änderungen werden nicht gespeichert.");
		abbrechen.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {

				new OfferProvider().removeOffer(currentOffer);
				
				Notification success = new Notification("Ihr Angebot wurde nicht gespeichert.", Type.HUMANIZED_MESSAGE);
				success.setStyleName("success");
				success.setDelayMsec(300);
				success.show(Page.getCurrent());
				
				try {
					VaadinSession.getCurrent().getLockInstance().lock();
					VaadinSession.getCurrent().getSession().setAttribute("buttonClicked", true);
				} finally {
					VaadinSession.getCurrent().getLockInstance().unlock();
				}

				String name = "Startseite";
				getUI().getNavigator().addView(name, new Startseite());
				getUI().getNavigator().navigateTo(name);

			}
		});
		
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponent(save);
		buttons.addComponent(abbrechen);
		content.addComponent(buttons);
		
		f = new Fotos(currentOffer, this);
		content.addComponent(f);
			

	}

	public void setContent(final Offer offer) {
		title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Wohnungsangebot bearbeiten");
		title.addStyleName("title");
		
		content.addComponent(title);
		
		// Titel + Adresse
		Label ltitel = new Label("Titel");
		ltitel.setWidth("10%");
		ltitel.addStyleName("AbschnittLabel");
		final TextField titel = new TextField();
		titel.setValue(offer.getTitle());
		titel.setRequired(true);
		titel.setRequiredError("Bitte geben Sie einen Titel an.");
		titel.setWidth("80%");
		
		Label adress = new Label("Adresse");
		adress.addStyleName("AbschnittLabel");
		final TextField street = new TextField("Straße, Hausnummer");
		street.setValue(offer.getStreet());
		street.setRequired(true);
		street.setRequiredError("Bitte geben Sie Straße und Hausnummer an.");
		street.addStyleName("AngeboteTextField");
		street.setEnabled(false); //nicht mehr zu ändern
		HorizontalLayout hl0 = new HorizontalLayout();
		hl0.setWidth("50%");
		final TextField zip = new TextField("PLZ");
		zip.setValue(offer.getZip());
		zip.setRequired(true);
		zip.setRequiredError("Bitte geben Sie die Postleitzahl an.");
		zip.setWidth("50%");
		zip.addStyleName("AngeboteTextField");
		zip.setEnabled(false); //nicht mehr zu ändern
		final TextField city = new TextField("Ort");
		city.setValue(offer.getCity());
		city.setRequired(true);
		city.setRequiredError("Bitte geben Sie den Ort an.");
		city.addStyleName("AngeboteTextField");
		city.setWidth("50%");
		city.setEnabled(false); //nicht mehr zu ändern
		hl0.addComponent(zip);
		hl0.addComponent(city);

		content.addComponent(ltitel);
		content.addComponent(titel);
		content.addComponent(new Label());
		content.addComponent(adress);
		content.addComponent(street);
		content.addComponent(hl0);
		content.addComponent(new Label());
		
		//GoogleMaps
		if(offer.getLatitude()!=null && offer.getLatitude()!=BigDecimal.valueOf(0.0)){

		    /** The kakola marker. */
		    GoogleMapMarker kakolaMarker = new GoogleMapMarker(
		            "Hier befindet sich die Wohnung", new LatLon(offer.getLatitude().doubleValue(), offer.getLongitude().doubleValue()),
		            false, null);
		    

	        
	        GoogleMap googleMap = new GoogleMap(null, null, null);
	        googleMap.setCenter(new LatLon(offer.getLatitude().doubleValue(), offer.getLongitude().doubleValue()));
	        googleMap.setZoom(10);
	        googleMap.setSizeFull();

	        kakolaMarker.setAnimationEnabled(false);
	        googleMap.addMarker(kakolaMarker);
	        
	        googleMap.setMinZoom(4);
	        googleMap.setMaxZoom(16);
	        googleMap.setHeight("500px");
	        googleMap.setWidth("500px");
	        content.addComponent(googleMap);
	        
	        googleMap.addMarkerDragListener(new MarkerDragListener() {
				@Override
				public void markerDragged(GoogleMapMarker draggedMarker,
						LatLon oldPosition) {
					lat = draggedMarker.getPosition().getLat();
					lon = draggedMarker.getPosition().getLon();
					// TODO Auto-generated method stub
					System.out.println(draggedMarker.getPosition().getLat()+"---"+draggedMarker.getPosition().getLon());
					
				}
	        });

		}else{
			 /** The kakola marker. */
		    GoogleMapMarker kakolaMarker = new GoogleMapMarker(
		            "Standort ändern", new LatLon(49.00705, 8.40287),
		            false, null);
		    

	        
	        GoogleMap googleMap = new GoogleMap(null, null, null);
	        googleMap.setCenter(new LatLon(49.00705, 8.40287));
	        googleMap.setZoom(10);
	        googleMap.setSizeFull();

	        kakolaMarker.setAnimationEnabled(false);
	        googleMap.addMarker(kakolaMarker);
	        
	        googleMap.setMinZoom(4);
	        googleMap.setMaxZoom(16);
	        googleMap.setHeight("500px");
	        googleMap.setWidth("500px");
	        content.addComponent(googleMap);
	        
	        googleMap.addMarkerDragListener(new MarkerDragListener() {
				@Override
				public void markerDragged(GoogleMapMarker draggedMarker,
						LatLon oldPosition) {
					lat = draggedMarker.getPosition().getLat();
					lon = draggedMarker.getPosition().getLon();
					// TODO Auto-generated method stub
					System.out.println(draggedMarker.getPosition().getLat()+"---"+draggedMarker.getPosition().getLon());
					
				}
	        });
		}
        

		// Allgemeine Informationen
		HorizontalLayout label = new HorizontalLayout();
		label.setWidth("100%");
		HorizontalLayout z1 = new HorizontalLayout();
		z1.setWidth("100%");
		HorizontalLayout z2 = new HorizontalLayout();
		z2.setWidth("100%");
		HorizontalLayout z3 = new HorizontalLayout();
		z3.setWidth("100%");
		Label allgInfo = new Label("Allgemeine Angaben zur Wohnung");
		allgInfo.addStyleName("AbschnittLabel");
		label.addComponent(allgInfo);
		final ComboBox isShared = new ComboBox("Art der Unterkunft:");
		isShared.setRequired(true);
		isShared.setRequiredError("Bitte geben sie die Art des Angebots an.");
		isShared.addItem("Wohnung");
		isShared.addItem("Zimmer");
		isShared.addItem("WG-Zimmer");
		isShared.addStyleName("AngeboteTextField");
		isShared.select(decodeType(offer));
		z1.addComponent(isShared);
		final TextField squareMetre = new TextField("Größe (in m²):");
		squareMetre.setValue(Format.stringFormat(offer.getSquareMetre()));
		squareMetre.setRequired(true);
		squareMetre.setRequiredError("Bitte geben Sie die Größe in m² an.");
		z2.addComponent(squareMetre);
		final TextField roomMates = new TextField("Anzahl Mitbewohner:");
		roomMates.setValue(String.valueOf(offer.getNumberOfRoommate()));
		roomMates.setRequired(true);
		roomMates.setRequiredError("Bitte geben Sie die Anzahl an Mitbewohner an.");
		roomMates.setValue(String.valueOf(offer.getNumberOfRoommate()));
		roomMates.addStyleName("AngeboteTextField");
		z3.addComponent(roomMates);
		Label date = new Label("Verfügbarkeit");
		date.addStyleName("AbschnittLabel");
		final DateField startDate = new DateField("von:");
		startDate.setValue(offer.getStartDate());
		startDate.setRequired(true);
		startDate.setRequiredError("Bitte geben Sie ein Startdatum an.");
		final DateField endDate = new DateField("bis:");
		try {
			endDate.setValue(offer.getEndDate());
		} catch (Exception e) {
		}
		HorizontalLayout hl1 = new HorizontalLayout();
		startDate.addStyleName("AngeboteTextField");
		endDate.addStyleName("AngeboteTextField");
		hl1.setWidth("50%");
		hl1.addComponent(startDate);
		hl1.addComponent(endDate);

		// Kosten
		Label costs = new Label("Kosten");
		costs.addStyleName("AbschnittLabel");
		label.addComponent(costs);
		final TextField price = new TextField("Warmmiete inkl. Nebenkosten (in €):");
		price.setValue(Format.euroFormat(offer.getPrice()));
		price.setRequired(true);
		price.setRequiredError("Bitte geben Sie die Warmmiete an.");
		price.addStyleName("AngeboteTextField");
		z1.addComponent(price);
		final TextField bond = new TextField("Kaution (in €):");
		try {
			bond.setValue(Format.euroFormat(offer.getBond()));
		} catch (Exception e) {
		}
		bond.addStyleName("AngeboteTextField");
		z2.addComponent(bond);
		//TextField cost = new TextField("Sonstige einmalige Kosten (in €):");
		//cost.addStyleName("AngeboteTextField");
		//z3.addComponent(cost);

		content.addComponent(label);
		//content.addComponent(new Label());
		content.addComponent(z1);
		content.addComponent(z2);
		content.addComponent(z3);
		content.addComponent(new Label());

		content.addComponent(date);
		content.addComponent(hl1);
		content.addComponent(new Label());

		// weitere Angaben
		Label angaben = new Label("Weitere Details");
		angaben.addStyleName("AbschnittLabel");
		HorizontalLayout hl2 = new HorizontalLayout();
		hl2.setWidth("100%");
		final CheckBox internet = new CheckBox("Internetanschluss vorhanden");
		internet.setValue(offer.isInternet());
		internet.setWidth("20%");
		internet.addStyleName("AngeboteTextField");
		final CheckBox furnished = new CheckBox("Möblierte Wohnung");
		furnished.setValue(offer.isFurnished());
		furnished.setWidth("20%");
		furnished.addStyleName("AngeboteTextField");
		final CheckBox kitchen = new CheckBox("Küche vorhanden");
		kitchen.setValue(offer.isKitchen());
		kitchen.setWidth("20%");
		kitchen.addStyleName("AngeboteTextField");
		final CheckBox smoker = new CheckBox("Raucher-Wohnung");
		smoker.setValue(offer.isSmoker());
		smoker.setWidth("20%");
		smoker.addStyleName("AngeboteTextField");
		final CheckBox pets = new CheckBox("Haustiere erlaubt");
		pets.setValue(offer.isPets());
		pets.setWidth("20%");
		pets.addStyleName("AngeboteTextField");
		final ComboBox genders = new ComboBox("Bevorzugtes Geschlecht:");
		genders.setRequired(true);
		genders.setRequiredError("Bitte geben Sie gegebenenfalls ein bevorzugtes Geschlecht an.");
		genders.addItem("egal");
		genders.addItem("männlich");
		genders.addItem("weiblich");
		genders.select(decodeGender(offer));
		genders.addStyleName("AngeboteTextField");

		hl2.addComponent(internet);
		hl2.addComponent(furnished);
		hl2.addComponent(kitchen);
		hl2.addComponent(smoker);
		hl2.addComponent(pets);
		content.addComponent(angaben);
		//content.addComponent(new Label());
		content.addComponent(hl2);
		content.addComponent(new Label());
		content.addComponent(genders);
		content.addComponent(new Label());

		// Anzeigetext
		Label anzeigetext = new Label("Beschreibung");
		anzeigetext.addStyleName("AbschnittLabel");
		final RichTextArea text = new RichTextArea();
		text.setValue(offer.getText());
		text.setRequired(true);
		text.setRequiredError("Bitte geben Sie eine kurze Beschreibung des Angebots an.");
		text.setWidth("100%");
		
		content.addComponent(anzeigetext);
		//content.addComponent(new Label());
		content.addComponent(text);
		content.addComponent(new Label());
		
	/*	//Bilder
		Label bilder = new Label("Bilder hinzufügen");
		bilder.addStyleName("AbschnittLabel");

		Upload bilderup = new Upload("Fotos hochladen (max. 5 Fotos pro Angebot):", this);
		bilderup.addSucceededListener(this);
				
		content.addComponent(bilder);
		//content.addComponent(new Label());
		content.addComponent(bilderup);
		content.addComponent(new Label());		
		
		//Bilder Löschen
		Label bilderLoeschen = new Label("Aktuelle Bilder");
		bilderLoeschen.addStyleName("AbschnittLabel");
		content.addComponent(bilderLoeschen);
		List<Photo> photo = offer.getPhotos();
		Iterator<Photo> it = photo.iterator();
		if(!it.hasNext()){
			content.addComponent(new Label("Es wurden noch keine Bilder zu diesem Angebot hochgeladen."));
		}else{
			while(it.hasNext()){
				Fotozeile f = new Fotozeile(it.next(), offer);
				content.addComponent(f);
			}
		}*/
		content.addComponent(new Label());	

		// Button speichern/aktivieren/deaktivieren
		final CheckBox inactive = new CheckBox("deaktivieren");
		inactive.setValue(offer.isInactive());
		
		content.addComponent(inactive);
		content.addComponent(new Label());

		Button save = new Button("Speichern");
		save.setIcon(FontAwesome.SAVE);
		save.addStyleName("BearbeitenButton");
		save.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				// Überprüfung ob alle Mussfelder gefüllt sind
				boolean valid = true; // fals ein Mussfeld nicht gefüllt ist
										// wird valid = false gesetzt
				try {
					titel.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					street.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					zip.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					city.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					startDate.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					genders.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					isShared.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					squareMetre.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					price.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					roomMates.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					bond.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				try {
					text.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}

				// Überprüft welche Art Angebot erstellt wird und setzt
				// entsprechenden int-Wert
				int type = 1;
				try {
					String shared = (String) isShared.getValue();
					if (shared.equals("Wohnung")) {
						type = 1;
					} else if (shared.equals("Zimmer")) {
						type = 2;
					} else if (shared.equals("WG-Zimmer")) {
						type = 3;
					}
				} catch (NullPointerException e) {// tut nichts, fängt nur NullPointerException ab
				}

				// Überprüft ob es ein bevorzugtes Geschlecht gibt und setzt entsprechenden int-Wert
				int gender = 1;
				try {
					String gend = (String) genders.getValue();
					if (gend.equals("egal")) {
						gender = 1;
					} else if (gend.equals("männlich")) {
						gender = 2;
					} else if (gend.equals("weiblich")) {
						gender = 3;
					}
				} catch (NullPointerException e) {// tut nichts, fängt nur
													// NullPointerException ab
				}

				if (valid) {// sind alle Mussfelder gefüllt, wird ein neues
							// Angebot erstellt
					
					try { //Zahlen formatieren
						
						currentOffer.setSquareMetre(Format.floatFormat(squareMetre.getValue()));
						currentOffer.setPrice(Format.floatFormat(price.getValue()));
						currentOffer.setBond(Format.floatFormat(bond.getValue()));
						
					} catch (NumberFormatException nfe) { //falls in Zahlenfelder keine Zahlen eingetragen wurden
						
						Notification not = new Notification("Bitte überprüfen Sie Ihre Eingaben und benutzen Sie gültige Zahlenformate.");
						not.setDelayMsec(300);
						not.setStyleName("failure");
						not.show(Page.getCurrent());
						return;
						
					}
					
					try {
						
						currentOffer.setNumberOfRoommate(Integer.parseInt(roomMates.getValue()));
						
					} catch(Exception e) {
						
						Notification not = new Notification("Bitte überprüfen Sie Ihre Eingaben.");
						not.setDelayMsec(300);
						not.setStyleName("failure");
						not.show(Page.getCurrent());
						return;
					}
					
					try { //Start-Datum validieren
						
						Format.dateFormat(startDate.getValue());
						currentOffer.setStartDate(startDate.getValue());
						
					} catch (Exception e) {
						if(e.getClass().equals(NullPointerException.class)) {
							
							Notification failNotFilled = new Notification("Bitte füllen Sie alle Mussfelder*", Type.HUMANIZED_MESSAGE);
							failNotFilled.setStyleName("failure");
							failNotFilled.setDelayMsec(300);
							failNotFilled.show(Page.getCurrent());
							return;
						
						} else {
							
							Notification failNotFilled = new Notification("Bitte geben Sie ein gültiges Datum an!", Type.HUMANIZED_MESSAGE);
							failNotFilled.setStyleName("failure");
							failNotFilled.setDelayMsec(300);
							failNotFilled.show(Page.getCurrent());
							return;
							
						}	
					}
					try { //End-Datum validieren
						
						Format.dateFormat(endDate.getValue());
						currentOffer.setEndDate(endDate.getValue());
						
					} catch (Exception e) {
						if(e.getClass().equals(NullPointerException.class)) {
							//nix, da End-Datum optional ist
						} else {
							
							Notification failNotFilled = new Notification("Bitte geben Sie ein gültiges Datum an!", Type.HUMANIZED_MESSAGE);
							failNotFilled.setStyleName("failure");
							failNotFilled.setDelayMsec(300);
							failNotFilled.show(Page.getCurrent());
							return;
							
						}	
					}
					
					currentOffer.setTitle(titel.getValue());
					
					currentOffer.setType(type);
					currentOffer.setInternet(internet.getValue());
					currentOffer.setFurnished(furnished.getValue());
					currentOffer.setKitchen(kitchen.getValue());
					currentOffer.setSmoker(smoker.getValue());
					currentOffer.setPets(pets.getValue());
					currentOffer.setGender(gender);
			        String t = text.getValue().replace("<br>", "\n");
			        String tx = Jsoup.parse(t).text();
					currentOffer.setText(tx);
					currentOffer.setInactive(inactive.getValue());

					if(lat != null){
						currentOffer.setLatitude(BigDecimal.valueOf(lat));
						currentOffer.setLongitude(BigDecimal.valueOf(lon));
					}
					
					try {
						VaadinSession.getCurrent().getLockInstance().lock();
						VaadinSession.getCurrent().getSession().setAttribute("buttonClicked", true);
					} finally {
						VaadinSession.getCurrent().getLockInstance().unlock();
					}
					
					if (new OfferProvider().alterOffer(currentOffer)) { //neues Angebot in die DB schreiben
						
						Offer o = new OfferProvider().find(currentOffer.getIdOffer());
						
						Notification success = new Notification("Ihre Änderungen an diesem Angebot wurden gespeichert.", Type.HUMANIZED_MESSAGE);
						success.setStyleName("success");
						success.setDelayMsec(300);
						success.show(Page.getCurrent());
						
						String name = "Einzelansicht";
						getUI().getNavigator().addView(name, new Einzelansicht(o));
						getUI().getNavigator().navigateTo(name);
						
					} else { //Fehler beim DB-Zugriff
						
						Notification failDB = new Notification("Das Angebot konnte nicht geändert werden.", Type.HUMANIZED_MESSAGE);
						failDB.setStyleName("failure");
						failDB.setDelayMsec(300);
						failDB.show(Page.getCurrent());
						
					}

				} else {
					
					// Sind nicht alle Mussfelder gefüllt, wird eine Nachricht
					// auf dem Bildschirm ausgegeben
					Notification failNotFilled = new Notification("Bitte füllen Sie alle Mussfelder*", Type.HUMANIZED_MESSAGE);
					failNotFilled.setStyleName("failure");
					failNotFilled.setDelayMsec(300);
					failNotFilled.show(Page.getCurrent());
					
				}
			}
		});

		Button abbrechen = new Button();
		abbrechen.setIcon(FontAwesome.MAIL_REPLY);
		abbrechen.addStyleName("BearbeitenButton");
		abbrechen.setCaption("Abbrechen");
		abbrechen.setImmediate(true);
		abbrechen.setDescription("Abbrechen der Bearbeitung. Ihre Änderungen werden nicht gespeichert.");
		abbrechen.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {

				PhotoProvider photoProv = new PhotoProvider();
				for (Photo p : newPhotos) {
					photoProv.removePhoto(p);
				}
				
				try {
					VaadinSession.getCurrent().getLockInstance().lock();
					VaadinSession.getCurrent().getSession().setAttribute("buttonClicked", true);
				} finally {
					VaadinSession.getCurrent().getLockInstance().unlock();
				}
				
				Notification success = new Notification("Ihre Änderungen an diesem Angebot wurden nicht gespeichert.", Type.HUMANIZED_MESSAGE);
				success.setStyleName("success");
				success.setDelayMsec(300);
				success.show(Page.getCurrent());
				
				Offer o = new OfferProvider().findById(currentOffer.getIdOffer());
				
				String name = "Einzelansicht";
				getUI().getNavigator().addView(name, new Einzelansicht(o));
				getUI().getNavigator().navigateTo(name);

			}
		});
		
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponent(save);
		buttons.addComponent(abbrechen);
		content.addComponent(buttons);

		
		f = new Fotos(currentOffer, this);
		f.setImmediate(true);
		content.addComponent(f);
			
	}

	public String decodeType(Offer offer) {
		String art = "";
		if (offer.getType() == 1)
			art = "Wohnung";
		else if (offer.getType() == 2)
			art = "Zimmer";
		else if (offer.getType() == 3)
			art = "WG-Zimmer";
		return art;
	}

	public String decodeGender(Offer offer) {
		String gender = "";
		if (offer.getGender() == 1)
			gender = "egal";
		else if (offer.getGender() == 2)
			gender = "männlich";
		else if (offer.getGender() == 3)
			gender = "weiblich";
		return gender;
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		
		try {
			
			if (mimeType.contains("image")) {
				
				tmpImg = new ByteArrayOutputStream();
				return tmpImg;
				
			} else {
				
				Notification failFileFormat = new Notification("Bitte laden Sie eine Bilddatei hoch!", Type.HUMANIZED_MESSAGE);
				failFileFormat.setStyleName("failure");
				failFileFormat.setDelayMsec(300);
				failFileFormat.show(Page.getCurrent());
				return null;
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
			
		}
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		
		OfferProvider offerProv = new OfferProvider();

		if(offerProv.findById(currentOffer.getIdOffer()).getPhotos() == null || offerProv.findById(currentOffer.getIdOffer()).getPhotos().size() < 5 ) {
		
			if (tmpImg != null) { // ist null, wenn kein Bild-Dateiformat hochgeladen wurde (siehe Methode receiveUpload)
				
				byte[] tmpImgBytes = tmpImg.toByteArray();
				
				//Größe prüfen
				if(tmpImgBytes.length > 1050000){
					
					Notification failFileSize = new Notification("Bitte laden Sie eine kleinere Bilddatei hoch (max. 1MB)!", Type.HUMANIZED_MESSAGE);
					failFileSize.setStyleName("failure");
					failFileSize.setDelayMsec(300);
					failFileSize.show(Page.getCurrent());
					return;
					
				}
				
				Photo newPhoto = new Photo();
				newPhoto.setPhoto_idOffer(currentOffer);
				newPhoto.setPicture(tmpImgBytes);
				
				try {
					
					newPhotos.add(newPhoto);
			
				}catch (NullPointerException ne) { //bei Angebot bearbeiten ist newPhotos nicht instantiiert	
				}
				
				new PhotoProvider().addPhoto(newPhoto);
				
				Notification not = new Notification("Bild wurde erfolgreich hochgeladen!", Type.HUMANIZED_MESSAGE);
				not.setStyleName("success");
				not.setDelayMsec(300);
				not.show(Page.getCurrent());
				
				OfferProvider op = new OfferProvider();
				currentOffer = op.find(currentOffer.getIdOffer());
				
				Fotos newFotos = new Fotos(currentOffer, this);
				//this.replaceComponent(f, newFotos);
				this.removeComponent(f);
				this.addComponent(newFotos);

		    }
			
		} else {
			
			Notification not = new Notification("Sie haben bereits fünf Bilder zu diesem Angebot hochgeladen.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
			not.setDelayMsec(300);
			not.setStyleName("failure");
			not.show(Page.getCurrent());
			
		} 
			
	}

	private byte[] resizeImage(byte[] imageData) {
		
		ByteArrayInputStream in = new ByteArrayInputStream(imageData);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Image imageWorkingCopy;
		int width = 700;
		int height = 483;
		try {
			
			imageWorkingCopy = ImageIO.read(in);
			
		} catch (IOException e) {

			e.printStackTrace();
			return null;
			
		}
		
		// TODO nur wenn Bild größer ist
		Image imageScaled = imageWorkingCopy.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage imageBuffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		imageBuffered.getGraphics().drawImage(imageScaled, 0, 0, null);
		
		try {
			
			ImageIO.write(imageBuffered, " ", out);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			return null;
		}
		
		return out.toByteArray();
		
	}

}
