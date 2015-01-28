package com.example.housing;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.xml.transform.stream.StreamSource;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Photo;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.OfferProvider;
import com.example.housing.data.provider.PhotoProvider;
import com.example.housing.utility.Format;
import com.example.housing.utility.PhotoUploader;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Upload.FinishedEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class AngebotErstellen.
 */
public class AngebotErstellen extends HorizontalLayout implements View, Receiver, SucceededListener {

	/** The content. */
	private VerticalLayout content;

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
		currentOffer.setOffer_idUser(VaadinSession.getCurrent().getAttribute(User.class));
		new OfferProvider().addOffer(currentOffer);

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

	// bereits bestehendes Angebot bearbeiten
	public AngebotErstellen(Offer offer) {

		currentOffer = offer;
		newPhotos = new ArrayList();

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
			
			//falls der Benutzer eingelogt ist verändert sich die Navigation
			if(VaadinSession.getCurrent().getAttribute("login").equals(true)){
				nav.setVisible(true);
				navPublic.setVisible(false);
			}else{
				nav.setVisible(false);
				navPublic.setVisible(true);
			}
			
			//Inhalt hinzufuegen
			setContent(offer);//Methode zum befuellen des Inhalts aufrufen
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
	// Inhalt der Seite für neues Angebot
	public void setContent() {

		content = new VerticalLayout();
		content.setMargin(true);

		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Angebot erstellen");
		title.addStyleName("title");
		content.addComponent(title);
		
		// Titel + Adresse
		Label ltitel = new Label("Titel");
		ltitel.setWidth("10%");
		ltitel.addStyleName("AbschnittLabel");
		final TextField titel = new TextField();
		titel.setWidth("50%");
		titel.setRequired(true);
		titel.setRequiredError("Bitte geben Sie einen Titel an.");
		titel.setWidth("90%");
		titel.addStyleName("ImportantTitle");
		content.addComponent(ltitel);
		content.addComponent(titel);
		Label adress = new Label("Adresse");
		adress.addStyleName("AbschnittLabel");
		final TextField street = new TextField("Straße, Hausnummer");
		street.setRequired(true);
		street.setRequiredError("Bitte geben Sie Straße und Hausnummer an.");
		street.addStyleName("AngeboteTextField");
		HorizontalLayout hl0 = new HorizontalLayout();
		hl0.setWidth("50%");
		final TextField zip = new TextField("PLZ");
		zip.setRequired(true);
		zip.setRequiredError("Bitte geben Sie die Postleitzahl an.");
		zip.setWidth("50%");
		zip.addStyleName("AngeboteTextField");
		final TextField city = new TextField("Ort");
		city.setRequired(true);
		city.setRequiredError("Bitte geben Sie den Ort an.");
		city.addStyleName("AngeboteTextField");
		city.setWidth("50%");
		hl0.addComponent(zip);
		hl0.addComponent(city);

		content.addComponent(new Label());
		content.addComponent(adress);
		content.addComponent(new Label());
		content.addComponent(street);
		content.addComponent(hl0);
		content.addComponent(new Label());

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
		final TextField squareMetre = new TextField("Größe (in m²)");
		squareMetre.setRequired(true);
		squareMetre.setRequiredError("Bitte geben Sie die Größe in m² an.");
		squareMetre.addStyleName("AngeboteTextField");
		z2.addComponent(squareMetre);
		final TextField roomMates = new TextField("Anzahl Mitbewohner:");
		roomMates.setRequired(true);
		roomMates.setRequiredError("Bitte geben Sie die Anzahl an Mitbewohner an.");
		roomMates.setValue("0");
		roomMates.addStyleName("AngeboteTextField");
		z3.addComponent(roomMates);
		Label date = new Label("Verfügbarkeit");
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
		final TextField price = new TextField("Warmmiete:");
		price.setRequired(true);
		price.setRequiredError("Bitte geben Sie die Warmmiete an.");
		price.addStyleName("AngeboteTextField");
		z1.addComponent(price);
		final TextField bond = new TextField("Kaution:");
		bond.addStyleName("AngeboteTextField");
		z2.addComponent(bond);
		TextField cost = new TextField("Sonstige Kosten:");
		cost.addStyleName("AngeboteTextField");
		z3.addComponent(cost);

		content.addComponent(label);
		content.addComponent(new Label());
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
		final CheckBox internet = new CheckBox("Internet");
		internet.setWidth("20%");
		internet.addStyleName("AngeboteTextField");
		final CheckBox furnished = new CheckBox("Möbliert");
		furnished.setWidth("20%");
		furnished.addStyleName("AngeboteTextField");
		final CheckBox kitchen = new CheckBox("Küche");
		kitchen.setWidth("20%");
		kitchen.addStyleName("AngeboteTextField");
		final CheckBox smoker = new CheckBox("Raucher");
		smoker.setWidth("20%");
		smoker.addStyleName("AngeboteTextField");
		final CheckBox pets = new CheckBox("Haustiere");
		pets.setWidth("20%");
		pets.addStyleName("AngeboteTextField");
		final ComboBox genders = new ComboBox("Bevorzugtes Geschlecht:");
		genders.setRequired(true);
		genders.setRequiredError("Bitte geben Sie ein ob es ein bevorzugtes Geschlecht gibt.");
		genders.addItem("egal");
		genders.addItem("männlich");
		genders.addItem("weiblich");
		genders.addStyleName("AngeboteTextField");

		hl2.addComponent(internet);
		hl2.addComponent(furnished);
		hl2.addComponent(kitchen);
		hl2.addComponent(smoker);
		hl2.addComponent(pets);
		content.addComponent(angaben);
		content.addComponent(new Label());
		content.addComponent(hl2);
		content.addComponent(new Label());
		content.addComponent(genders);
		content.addComponent(new Label());

		// Anzeigetext + Wohnungsbilder
		Label anzeigetext = new Label("Anzeigetext");
		anzeigetext.addStyleName("AbschnittLabel");
		final RichTextArea text = new RichTextArea();
		text.setRequired(true);
		text.setRequiredError("Bitte geben Sie eine kurze Beschreibung des Angebots an.");
		text.setWidth("100%");
		Label bilder = new Label("Bilder hinzufügen(max. fünf)");
		bilder.addStyleName("AbschnittLabel");
		
		content.addComponent(anzeigetext);
		content.addComponent(new Label());
		content.addComponent(text);
		content.addComponent(new Label());
		content.addComponent(bilder);
		content.addComponent(new Label());
		Upload bilderup = new Upload("Foto hochladen", this); 
		bilderup.addSucceededListener(this);
		//Label max = new Label("Sie haben schon fünf Bilder hochgeladen");
	
		
		content.addComponent(bilderup);

		content.addComponent(new Label());

		// Button speichern/aktivieren/deaktivieren
		final CheckBox inactive = new CheckBox("deaktivieren");

		Button save = new Button("Speichern");
		save.setIcon(FontAwesome.SAVE);
		save.addStyleName("speichern");
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
					currentOffer.setOffer_idUser(VaadinSession.getCurrent().getAttribute(User.class));
					currentOffer.setTitle(titel.getValue());
					currentOffer.setStreet(street.getValue());
					currentOffer.setZip(zip.getValue());
					currentOffer.setCity(city.getValue());
					currentOffer.setStartDate(startDate.getValue());
					try { // überprüft ob ein Enddatum angegeben ist, da die
							// Angabe optional ist
						currentOffer.setEndDate(endDate.getValue());
					} catch (NullPointerException e) {
					}
					currentOffer.setSquareMetre(new Format().floatFormat(squareMetre.getValue()));
					currentOffer.setPrice(new Format().floatFormat(price.getValue()));
					currentOffer.setType(type);
					currentOffer.setNumberOfRoommate(Integer.parseInt(roomMates.getValue()));
					currentOffer.setInternet(internet.getValue());
					currentOffer.setFurnished(furnished.getValue());
					currentOffer.setKitchen(kitchen.getValue());
					currentOffer.setSmoker(smoker.getValue());
					currentOffer.setPets(pets.getValue());
					currentOffer.setGender(gender);
					currentOffer.setText(text.getValue());

					try {// überprüft ob eine Kaution angegeben ist, da die
							// Angabe optional ist
						currentOffer.setBond(new Format().floatFormat(bond.getValue()));
					} catch (NumberFormatException e) {
					}
					currentOffer.setInactive(inactive.getValue());
					// newOffer.setLatitude(latitude);
					// newOffer.setLongitude(longitude);
					// newOffer.setPhotos();
					new OfferProvider().alterOffer(currentOffer); // neues
																	// Angebot
																	// in
					// die DB schreiben
					Offer o = new OfferProvider().find(currentOffer.getIdOffer());
					
					VaadinSession.getCurrent().setAttribute("buttonClicked", true);
					
					String name = "Einzelansicht";
					getUI().getNavigator().addView(name, new Einzelansicht(o));
					getUI().getNavigator().navigateTo(name);

				} else
					// Sind nicht alle Mussfelder gefüllt, wird eine Nachricht
					// auf dem Bildschirm ausgegeben
					Notification.show("");
					Notification not1 = new Notification("Bitte füllen Sie alle Mussfelder*", Type.HUMANIZED_MESSAGE);
					not1.setStyleName("failure");
					not1.setDelayMsec(300);
					not1.show(Page.getCurrent());
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
				
				VaadinSession.getCurrent().setAttribute("buttonClicked", true);

				String name = "Startseite";
				getUI().getNavigator().addView(name, new Startseite());
				getUI().getNavigator().navigateTo(name);

			}
		});
		content.addComponent(inactive);
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponent(abbrechen);
		buttons.addComponent(save);
		content.addComponent(buttons);

	}

	public void setContent(final Offer offer) {

		content = new VerticalLayout();
		content.setMargin(true);
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Angebot bearbeiten");
		title.addStyleName("title");
		content.addComponent(title);
		// Titel + Adresse
		HorizontalLayout hl = new HorizontalLayout();
		hl.setWidth("60%");
		Label ltitel = new Label("Titel");
		ltitel.setWidth("10%");
		ltitel.addStyleName("AbschnittLabel");
		final TextField titel = new TextField();
		titel.setValue(offer.getTitle());
		titel.setRequired(true);
		titel.setRequiredError("Bitte geben Sie einen Titel an.");
		titel.setWidth("90%");
		titel.addStyleName("ImportantTitle");
		hl.addComponent(ltitel);
		hl.addComponent(titel);
		Label adress = new Label("Adresse");
		adress.addStyleName("AbschnittLabel");
		final TextField street = new TextField("Straße, Hausnummer");
		street.setValue(offer.getStreet());
		street.setRequired(true);
		street.setRequiredError("Bitte geben Sie Straße und Hausnummer an.");
		street.addStyleName("AngeboteTextField");
		HorizontalLayout hl0 = new HorizontalLayout();
		hl0.setWidth("50%");
		final TextField zip = new TextField("PLZ");
		zip.setValue(offer.getZip());
		zip.setRequired(true);
		zip.setRequiredError("Bitte geben Sie die Postleitzahl an.");
		zip.setWidth("50%");
		zip.addStyleName("AngeboteTextField");
		final TextField city = new TextField("Ort");
		city.setValue(offer.getCity());
		city.setRequired(true);
		city.setRequiredError("Bitte geben Sie den Ort an.");
		city.addStyleName("AngeboteTextField");
		city.setWidth("50%");
		hl0.addComponent(zip);
		hl0.addComponent(city);

		content.addComponent(hl);
		content.addComponent(new Label());
		content.addComponent(adress);
		content.addComponent(new Label());
		content.addComponent(street);
		content.addComponent(hl0);
		content.addComponent(new Label());

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
		isShared.select(art(offer));
		z1.addComponent(isShared);
		final TextField squareMetre = new TextField("Größe (in m²):");
		squareMetre.setValue(String.valueOf(offer.getSquareMetre()));
		squareMetre.setRequired(true);
		squareMetre.setRequiredError("Bitte geben Sie die Größe in m² an.");
		z2.addComponent(squareMetre);
		final TextField roomMates = new TextField("Anzahl Mitbewohner:");
		roomMates.setValue(String.valueOf(offer.getNumberOfRoommate()));
		roomMates.setRequired(true);
		roomMates.setRequiredError("Bitte geben Sie die Anzahl an Mitbewohner an.");
		roomMates.setValue("0");
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
		final TextField price = new TextField("Warmmiete inkl. regelmäßiger Nebenkosten (in €):");
		price.setValue(new Format().stringFormat(offer.getPrice()));
		price.setRequired(true);
		price.setRequiredError("Bitte geben Sie die Warmmiete an.");
		price.addStyleName("AngeboteTextField");
		z1.addComponent(price);
		final TextField bond = new TextField("Kaution (in €):");
		try {
			bond.setValue(new Format().stringFormat(offer.getBond()));
		} catch (Exception e) {
		}
		bond.addStyleName("AngeboteTextField");
		z2.addComponent(bond);
		TextField cost = new TextField("Sonstige einmalige Kosten (in €):");
		cost.addStyleName("AngeboteTextField");
		z3.addComponent(cost);

		content.addComponent(label);
		content.addComponent(new Label());
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
		final CheckBox internet = new CheckBox("Internetanschluss vorhanden:");
		internet.setValue(offer.isInternet());
		internet.setWidth("20%");
		internet.addStyleName("AngeboteTextField");
		final CheckBox furnished = new CheckBox("Möblierte Wohnung:");
		furnished.setValue(offer.isFurnished());
		furnished.setWidth("20%");
		furnished.addStyleName("AngeboteTextField");
		final CheckBox kitchen = new CheckBox("Küche vorhanden:");
		kitchen.setValue(offer.isKitchen());
		kitchen.setWidth("20%");
		kitchen.addStyleName("AngeboteTextField");
		final CheckBox smoker = new CheckBox("Raucher-Wohnung:");
		smoker.setValue(offer.isSmoker());
		smoker.setWidth("20%");
		smoker.addStyleName("AngeboteTextField");
		final CheckBox pets = new CheckBox("Haustiere erlaubt:");
		pets.setValue(offer.isPets());
		pets.setWidth("20%");
		pets.addStyleName("AngeboteTextField");
		final ComboBox genders = new ComboBox("Bevorzugtes Geschlecht:");
		genders.setRequired(true);
		genders.setRequiredError("Bitte geben Sie ein ob es ein bevorzugtes Geschlecht gibt.");
		genders.addItem("egal");
		genders.addItem("männlich");
		genders.addItem("weiblich");
		genders.select(gender(offer));
		genders.addStyleName("AngeboteTextField");

		hl2.addComponent(internet);
		hl2.addComponent(furnished);
		hl2.addComponent(kitchen);
		hl2.addComponent(smoker);
		hl2.addComponent(pets);
		content.addComponent(angaben);
		content.addComponent(new Label());
		content.addComponent(hl2);
		content.addComponent(new Label());
		content.addComponent(genders);
		content.addComponent(new Label());

		// Anzeigetext + Wohnungsbilder
		Label anzeigetext = new Label("Beschreibung");
		anzeigetext.addStyleName("AbschnittLabel");
		final RichTextArea text = new RichTextArea();
		text.setValue(offer.getText());
		text.setRequired(true);
		text.setRequiredError("Bitte geben Sie eine kurze Beschreibung des Angebots an.");
		text.setWidth("100%");
		content.addComponent(anzeigetext);
		content.addComponent(new Label());
		content.addComponent(text);
		content.addComponent(new Label());
		
		Label bilder = new Label("Bilder");
		bilder.addStyleName("AbschnittLabel");

		Upload bilderup = new Upload("Foto hochladen (max. 5 Fotos pro Angebot möglich!):", this);
		bilderup.addSucceededListener(this);
		
		content.addComponent(bilder);
		content.addComponent(new Label());
		
		content.addComponent(bilderup);
		content.addComponent(new Label());	
		

		// Button speichern/aktivieren/deaktivieren
		final CheckBox inactive = new CheckBox("deaktivieren");

		Button save = new Button("speichern");
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
					// currentOffer.setOffer_idUser(VaadinSession.getCurrent().getAttribute(User.class));
					// //hat sich nicht geändert
					// currentOffer.setIdOffer(offer.getIdOffer()); //hat sich
					// nicht geändert
					currentOffer.setTitle(titel.getValue());
					currentOffer.setStreet(street.getValue());
					currentOffer.setZip(zip.getValue());
					currentOffer.setCity(city.getValue());
					currentOffer.setStartDate(startDate.getValue());
					try { // überprüft ob ein Enddatum angegeben ist, da die
							// Angabe optional ist
						currentOffer.setEndDate(endDate.getValue());
					} catch (NullPointerException e) {
					}
					currentOffer.setSquareMetre(new Format().floatFormat(squareMetre.getValue()));
					currentOffer.setPrice(new Format().floatFormat(price.getValue()));
					currentOffer.setType(type);
					currentOffer.setNumberOfRoommate(Integer.parseInt(roomMates.getValue()));
					currentOffer.setInternet(internet.getValue());
					currentOffer.setFurnished(furnished.getValue());
					currentOffer.setKitchen(kitchen.getValue());
					currentOffer.setSmoker(smoker.getValue());
					currentOffer.setPets(pets.getValue());
					currentOffer.setGender(gender);
					currentOffer.setText(text.getValue());

					try {// überprüft ob eine Kaution angegeben ist, da die
							// Angabe optional ist
						currentOffer.setBond(new Format().floatFormat(bond.getValue()));
					} catch (NumberFormatException e) {
					}
					currentOffer.setInactive(inactive.getValue());
					// changedOffer.setLatitude(latitude);
					// changedOffer.setLongitude(longitude);
					// changedOffer.setPhotos();
					Offer o = new OfferProvider().find(currentOffer.getIdOffer());
					
					VaadinSession.getCurrent().setAttribute("buttonClicked", true);
					
					if (new OfferProvider().alterOffer(currentOffer)) {
						// neues Angebot in die DB schreiben
						String name = "Einzelansicht";
						getUI().getNavigator().addView(name, new Einzelansicht(o));
						getUI().getNavigator().navigateTo(name);
					} else {
						Notification not = new Notification("Das Angebot konnte nicht geändert werden.", Type.HUMANIZED_MESSAGE);
						not.setStyleName("failure");
						not.setDelayMsec(300);
						not.show(Page.getCurrent());
					}

				} else {
					// Sind nicht alle Mussfelder gefüllt, wird eine Nachricht
					// auf dem Bildschirm ausgegeben
					Notification not1 = new Notification("Bitte füllen Sie alle Mussfelder*", Type.HUMANIZED_MESSAGE);
					not1.setStyleName("failure");
					not1.setDelayMsec(300);
					not1.show(Page.getCurrent());
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

				PhotoProvider photoProv = new PhotoProvider();
				for (Photo p : newPhotos) {
					photoProv.removePhoto(p);
				}

				VaadinSession.getCurrent().setAttribute("buttonClicked", true);
				
				String name = "Einzelansicht";
				getUI().getNavigator().addView(name, new Einzelansicht(offer));
				getUI().getNavigator().navigateTo(name);

			}
		});
		content.addComponent(inactive);
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponent(save);
		buttons.addComponent(abbrechen);
		content.addComponent(buttons);

	}

	public String art(Offer offer) {
		String art = "";
		if (offer.getType() == 1)
			art = "Wohnung";
		else if (offer.getType() == 2)
			art = "Zimmer";
		else if (offer.getType() == 3)
			art = "WG-Zimmer";
		return art;
	}

	public String gender(Offer offer) {
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
			
			if(mimeType.contains("image")) {
				
				tmpImg = new ByteArrayOutputStream();
				return tmpImg;
				
			} else {
				
				//TODO Upload fails! (FailedEvent auslösen?)
				Notification not = new Notification("Bitte laden Sie eine Bilddatei hoch!", Type.HUMANIZED_MESSAGE);
				not.setStyleName("failure");
				not.setDelayMsec(300);
				not.show(Page.getCurrent());
				return null;
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
			
		}
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		OfferProvider o = new OfferProvider();

		if(o.findById(currentOffer.getIdOffer()).getPhotos()  == null || o.findById(currentOffer.getIdOffer()).getPhotos().size() < 5 ){
		
		System.out.println("Bild wurde hochgeladen");
		if (tmpImg != null) {
				
			//TODO Fehler: Bild ist leer
			byte[] tmpImgBytes = resizeImage(tmpImg.toByteArray());
			
			Photo newPhoto = new Photo();
			newPhoto.setPhoto_idOffer(currentOffer);
			//newPhoto.setPicture(tmpImg.toByteArray());
			newPhoto.setPicture(tmpImgBytes);
			
			
			try {
			
			
				newPhotos.add(newPhoto);
			
			}catch (NullPointerException ne) { //bei Angebot bearbeiten ist newPhotos nicht instantiiert
			}
			new PhotoProvider().addPhoto(newPhoto);
			
			//currentOffer = new OfferProvider().findById(currentOffer.getIdOffer());
			
		    }
			}else{
				Notification not = new Notification("Sie haben bereits fünf Bilder zu diesem Angebot hochgeladen",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
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
			
			ImageIO.write(imageBuffered, "jpg", out);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			return null;
		}
		
		return out.toByteArray();
		
	}

}
