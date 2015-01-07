package com.example.housing;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.OfferProvider;
import com.example.housing.utility.Format;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class AngebotErstellen.
 */
public class AngebotErstellen extends VerticalLayout implements View {

	/** The content. */
	VerticalLayout content;

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
		Navigation nav = new Navigation();
		addComponent(nav);
		// setSizeFull();

		NavigationPublic navPublic = new NavigationPublic();
		addComponent(navPublic);

		// falls der Benutzer eingelogt ist verändert sich die Navigation
		if (VaadinSession.getCurrent().getAttribute("login").equals(true)) {
			nav.setVisible(true);
			navPublic.setVisible(false);
		} else {
			nav.setVisible(false);
			navPublic.setVisible(true);
		}

		setContent();
		addComponent(content);

		Footer f = new Footer();
		addComponent(f);
	}

	// bereits bestehendes Angebot bearbeiten
	public AngebotErstellen(Offer offer) {
		Navigation nav = new Navigation();
		addComponent(nav);
		// setSizeFull();

		NavigationPublic navPublic = new NavigationPublic();
		addComponent(navPublic);

		// falls der Benutzer eingelogt ist verändert sich die Navigation
		if (VaadinSession.getCurrent().getAttribute("login").equals(true)) {
			nav.setVisible(true);
			navPublic.setVisible(false);
		} else {
			nav.setVisible(false);
			navPublic.setVisible(true);
		}

		setContent(offer);
		addComponent(content);

		Footer f = new Footer();
		addComponent(f);
	}

	/**
	 * Sets the content.
	 */
	// Inhalt der Seite für neues Angebot
	public void setContent() {

		content = new VerticalLayout();
		content.setMargin(true);

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
		Label bilder = new Label("Bilder hinzufügen");
		bilder.addStyleName("AbschnittLabel");
		Upload bilderup = new Upload();
		bilderup.setButtonCaption("hochladen");

		content.addComponent(anzeigetext);
		content.addComponent(new Label());
		content.addComponent(text);
		content.addComponent(new Label());
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
					Offer newOffer = new Offer();
					newOffer.setOffer_idUser(VaadinSession.getCurrent().getAttribute(User.class));
					newOffer.setTitle(titel.getValue());
					newOffer.setStreet(street.getValue());
					newOffer.setZip(zip.getValue());
					newOffer.setCity(city.getValue());
					newOffer.setStartDate(startDate.getValue());
					try { // überprüft ob ein Enddatum angegeben ist, da die
							// Angabe optional ist
						newOffer.setEndDate(endDate.getValue());
					} catch (NullPointerException e) {
					}
					newOffer.setSquareMetre(new Format().floatFormat(squareMetre.getValue()));
					newOffer.setPrice(new Format().floatFormat(price.getValue()));
					newOffer.setType(type);
					newOffer.setNumberOfRoommate(Integer.parseInt(roomMates.getValue()));
					newOffer.setInternet(internet.getValue());
					newOffer.setFurnished(furnished.getValue());
					newOffer.setKitchen(kitchen.getValue());
					newOffer.setSmoker(smoker.getValue());
					newOffer.setPets(pets.getValue());
					newOffer.setGender(gender);
					newOffer.setText(text.getValue());

					try {// überprüft ob eine Kaution angegeben ist, da die
							// Angabe optional ist
						newOffer.setBond(Float.parseFloat(bond.getValue()));
					} catch (NumberFormatException e) {
					}
					newOffer.setInactive(inactive.getValue());
					// newOffer.setLatitude(latitude);
					// newOffer.setLongitude(longitude);
					// newOffer.setPhotos();
					new OfferProvider().addOffer(newOffer); // neues Angebot in
															// die DB schreiben
					String name = "AngebotAnzeigen";
					getUI().getNavigator().addView(name, new AngebotAnzeigen(newOffer));
					getUI().getNavigator().navigateTo(name);

				} else
					// Sind nicht alle Mussfelder gefüllt, wird eine Nachricht
					// auf dem Bildschirm ausgegeben
					Notification.show("Bitte füllen Sie alle Mussfelder*");
			}
		});
		Button abbrechen = new Button();
		abbrechen.addStyleName("BearbeitenButton");
		abbrechen.setCaption("Abbrechen");
		abbrechen.setImmediate(true);
		abbrechen.setDescription("Abbrechen der Bearbeitung. Ihre Änderungen werden nicht gespeichert.");
		abbrechen.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				String name = "Startseite";
				getUI().getNavigator().addView(name, new Startseite());
				getUI().getNavigator().navigateTo(name);

			}
		});
		content.addComponent(inactive);
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponent(save);
		buttons.addComponent(abbrechen);
		content.addComponent(buttons);

	}

	public void setContent(final Offer offer) {

		content = new VerticalLayout();
		content.setMargin(true);

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
		final ComboBox isShared = new ComboBox("Art");
		isShared.setRequired(true);
		isShared.setRequiredError("Bitte geben sie die Art des Angebots an.");
		isShared.addItem("Wohnung");
		isShared.addItem("Zimmer");
		isShared.addItem("WG-Zimmer");
		isShared.addStyleName("AngeboteTextField");
		isShared.select(art(offer));
		z1.addComponent(isShared);
		final TextField squareMetre = new TextField("Größe (in m²)");
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
		final TextField price = new TextField("Warmmiete:");
		price.setValue(String.valueOf(offer.getPrice()));
		price.setRequired(true);
		price.setRequiredError("Bitte geben Sie die Warmmiete an.");
		price.addStyleName("AngeboteTextField");
		z1.addComponent(price);
		final TextField bond = new TextField("Kaution:");
		try {
			bond.setValue(String.valueOf(offer.getBond()));
		} catch (Exception e) {
		}
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
		internet.setValue(offer.isInternet());
		internet.setWidth("20%");
		internet.addStyleName("AngeboteTextField");
		final CheckBox furnished = new CheckBox("Möbliert");
		furnished.setValue(offer.isFurnished());
		furnished.setWidth("20%");
		furnished.addStyleName("AngeboteTextField");
		final CheckBox kitchen = new CheckBox("Küche");
		kitchen.setValue(offer.isKitchen());
		kitchen.setWidth("20%");
		kitchen.addStyleName("AngeboteTextField");
		final CheckBox smoker = new CheckBox("Raucher");
		smoker.setValue(offer.isSmoker());
		smoker.setWidth("20%");
		smoker.addStyleName("AngeboteTextField");
		final CheckBox pets = new CheckBox("Haustiere");
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
		Label anzeigetext = new Label("Anzeigetext");
		anzeigetext.addStyleName("AbschnittLabel");
		final RichTextArea text = new RichTextArea();
		text.setValue(offer.getText());
		text.setRequired(true);
		text.setRequiredError("Bitte geben Sie eine kurze Beschreibun des Angebots an.");
		text.setWidth("100%");
		Label bilder = new Label("Bilder hinzufügen");
		bilder.addStyleName("AbschnittLabel");
		Upload bilderup = new Upload();
		bilderup.setButtonCaption("hochladen");

		content.addComponent(anzeigetext);
		content.addComponent(new Label());
		content.addComponent(text);
		content.addComponent(new Label());
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
					Offer changedOffer = new Offer();
					changedOffer.setOffer_idUser(VaadinSession.getCurrent().getAttribute(User.class));
					changedOffer.setIdOffer(offer.getIdOffer());
					changedOffer.setTitle(titel.getValue());
					changedOffer.setStreet(street.getValue());
					changedOffer.setZip(zip.getValue());
					changedOffer.setCity(city.getValue());
					changedOffer.setStartDate(startDate.getValue());
					try { // überprüft ob ein Enddatum angegeben ist, da die
							// Angabe optional ist
						changedOffer.setEndDate(endDate.getValue());
					} catch (NullPointerException e) {
					}
					changedOffer.setSquareMetre(new Format().floatFormat(squareMetre.getValue()));
					changedOffer.setPrice(new Format().floatFormat(price.getValue()));
					changedOffer.setType(type);
					changedOffer.setNumberOfRoommate(Integer.parseInt(roomMates.getValue()));
					changedOffer.setInternet(internet.getValue());
					changedOffer.setFurnished(furnished.getValue());
					changedOffer.setKitchen(kitchen.getValue());
					changedOffer.setSmoker(smoker.getValue());
					changedOffer.setPets(pets.getValue());
					changedOffer.setGender(gender);
					changedOffer.setText(text.getValue());

					try {// überprüft ob eine Kaution angegeben ist, da die
							// Angabe optional ist
						changedOffer.setBond(Float.parseFloat(bond.getValue()));
					} catch (NumberFormatException e) {
					}
					changedOffer.setInactive(inactive.getValue());
					// changedOffer.setLatitude(latitude);
					// changedOffer.setLongitude(longitude);
					// changedOffer.setPhotos();
					if (new OfferProvider().alterOffer(changedOffer)) {
						// neues Angebot in die DB schreiben
						String name = "AngebotAnzeigen";
						getUI().getNavigator().addView(name, new AngebotAnzeigen(changedOffer));
						getUI().getNavigator().navigateTo(name);
					} else {
						Notification.show("Das Angebot konnte nicht geändert werden.");
					}

				} else
					// Sind nicht alle Mussfelder gefüllt, wird eine Nachricht
					// auf dem Bildschirm ausgegeben
					Notification.show("Bitte füllen Sie alle Mussfelder*");
			}
		});

		Button abbrechen = new Button();
		abbrechen.addStyleName("BearbeitenButton");
		abbrechen.setCaption("Abbrechen");
		abbrechen.setImmediate(true);
		abbrechen.setDescription("Abbrechen der Bearbeitung. Ihre Änderungen werden nicht gespeichert.");
		abbrechen.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
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

}
