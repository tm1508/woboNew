package com.example.housing;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.OfferProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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

	/**
	 * Sets the content.
	 */
	public void setContent() {

		content = new VerticalLayout();
		content.setMargin(true);

		// Titel + Adresse
		HorizontalLayout hl = new HorizontalLayout();
		hl.setWidth("60%");
		Label ltitel = new Label("Titel");
		ltitel.setWidth("10%");
		ltitel.addStyleName("AbschnittLabel");
		final TextField titel = new TextField();
		titel.setWidth("90%");
		titel.addStyleName("ImportantTitle");
		hl.addComponent(ltitel);
		hl.addComponent(titel);
		Label adress = new Label("Adresse");
		adress.addStyleName("AbschnittLabel");
		final TextField street = new TextField("Straße, Hausnummer");
		street.addStyleName("AngeboteTextField");
		HorizontalLayout hl0 = new HorizontalLayout();
		hl0.setWidth("50%");
		final TextField zip = new TextField("PLZ");
		zip.setWidth("50%");
		zip.addStyleName("AngeboteTextField");
		final TextField city = new TextField("Ort");
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
		isShared.setNullSelectionItemId("Wohnung");
		isShared.addItem("Wohnung");
		isShared.addItem("Zimmer");
		isShared.addItem("WG-Zimmer");
		isShared.addStyleName("AngeboteTextField");
		z1.addComponent(isShared);
		final TextField squareMetre = new TextField("Größe (in m²)");
		squareMetre.addStyleName("AngeboteTextField");
		z2.addComponent(squareMetre);
		final TextField roomMates = new TextField("Anzahl Mitbewohner:");
		roomMates.setValue("0");
		roomMates.addStyleName("AngeboteTextField");
		z3.addComponent(roomMates);
		Label date = new Label("Verfügbarkeit");
		final DateField startDate = new DateField("von:");
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
		genders.setNullSelectionItemId("egal");
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
				int type = 1;
				System.out.println("isShared: " + isShared.getValue() + "########################################"); //Test; immer null
				/*if (isShared.getValue().equals("Wohnung")) {
					type = 1;
				} else if (isShared.getValue().equals("Zimmer")) {
					type = 2;
				} else if (isShared.getValue().equals("WG-Zimmer")) {
					type = 3;
				}*/
				int gender = 1;
				System.out.println("gender: " + genders.getValue() + "###############################"); //Test; immer null
				/*if (genders.getValue().equals("männlich")) {
					gender = 2;
				} else if (genders.getValue().equals("weiblich")) {
					gender = 3;
				}*/
				OfferProvider of = new OfferProvider();
				Offer newOffer = new Offer();
				
				newOffer.setTitle(titel.getValue());
				newOffer.setStreet(street.getValue());
				newOffer.setZip(zip.getValue());
				newOffer.setCity(city.getValue());
				newOffer.setStartDate(startDate.getValue());
				newOffer.setEndDate(endDate.getValue());
				newOffer.setSquareMetre(Float.parseFloat(squareMetre.getValue()));
				newOffer.setPrice(Float.parseFloat(price.getValue()));
				newOffer.setType(type);
				newOffer.setNumberOfRoommate(Integer.parseInt(roomMates.getValue()));
				newOffer.setInternet(internet.getValue());
				newOffer.setFurnished(furnished.getValue());
				newOffer.setKitchen(kitchen.getValue());
				newOffer.setSmoker(smoker.getValue());
				newOffer.setPets(pets.getValue());
				newOffer.setGender(gender);
				newOffer.setText(text.getValue());
				newOffer.setBond(Float.parseFloat(bond.getValue()));
				newOffer.setInactive(inactive.getValue());
				newOffer.setOffer_idUser(VaadinSession.getCurrent().getAttribute(User.class));
				//newOffer.setLatitude(latitude);
				//newOffer.setLongitude(longitude);
				//newOffer.setPhotos();
				of.addOffer(newOffer);
				String name = "AngebotAnzeigen";
				getUI().getNavigator().addView(name, new AngebotAnzeigen(newOffer.getIdOffer()));
				getUI().getNavigator().navigateTo(name);
				
			}
		});

		content.addComponent(inactive);
		content.addComponent(save);
		

	}

}
