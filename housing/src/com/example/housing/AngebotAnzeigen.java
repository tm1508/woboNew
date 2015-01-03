package com.example.housing;

import java.util.Date;

import com.example.housing.data.model.Offer;
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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class AngebotAnzeigen.
 */
public class AngebotAnzeigen extends VerticalLayout implements View {

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
	 * Instantiates a new angebot anzeigen.
	 */
	public AngebotAnzeigen(int idOffer) {
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
		OfferProvider op = new OfferProvider();
		Offer offer = op.findById(idOffer);
		setContent(offer);
		addComponent(content);

		Footer f = new Footer();
		addComponent(f);
	}

	public AngebotAnzeigen() { //nicht unbedingt notwendig
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
	public void setContent(Offer offer) {

		content = new VerticalLayout();
		content.setMargin(true);

		// Titel + Adresse
		HorizontalLayout hl = new HorizontalLayout();
		// hl.setWidth("100%");
		Label ltitel = new Label("Titel");
		ltitel.setWidth("10%");
		TextField titel = new TextField();
		titel.setValue(offer.getTitle());
		titel.setEnabled(false);
		titel.setWidth("90%");
		hl.addComponent(ltitel);
		hl.addComponent(titel);
		Label adress = new Label("Adresse");
		adress.setEnabled(false);
		TextField street = new TextField("Straße, Hausnummer");
		street.setValue(offer.getStreet());
		street.setEnabled(false);
		HorizontalLayout hl0 = new HorizontalLayout();
		hl0.setWidth("50%");
		TextField zip = new TextField("PLZ");
		zip.setValue(offer.getZip());
		zip.setWidth("50%");
		zip.setEnabled(false);
		TextField city = new TextField("Ort");
		city.setValue(offer.getCity());
		city.setWidth("50%");
		city.setEnabled(false);
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
		label.addComponent(allgInfo);
		ComboBox isShared = new ComboBox("Art");
		isShared.addItem("Wohnung");
		isShared.addItem("Zimmer");
		isShared.addItem("WG-Zimmer");
		if(offer.getType()==1)
			isShared.setNullSelectionItemId("Wohnung");
		else if(offer.getType()==2)
			isShared.setNullSelectionItemId("Zimmer");
		else if(offer.getType()==3)
			isShared.setNullSelectionItemId("WG-Zimmer");		
		isShared.setEnabled(false);
		z1.addComponent(isShared);
		
		TextField squareMetre = new TextField("Größe (in m²)");		
		squareMetre.setValue(String.valueOf(offer.getSquareMetre())); 
		squareMetre.setEnabled(false);
		z2.addComponent(squareMetre);
		
		TextField roomMates = new TextField("Anzahl Mitbewohner:");
		roomMates.setValue(String.valueOf(offer.getNumberOfRoommate()));
		roomMates.setEnabled(false);
		z3.addComponent(roomMates);
		
		Label date = new Label("Verfügbarkeit");
		DateField startDate = new DateField("von:");
		startDate.setValue(offer.getStartDate());
		startDate.setEnabled(false);
		DateField endDate = new DateField("bis:");
		endDate.setValue(offer.getEndDate());
		endDate.setEnabled(false);
		HorizontalLayout hl1 = new HorizontalLayout();
		hl1.setWidth("50%");
		hl1.addComponent(startDate);
		hl1.addComponent(endDate);

		// Kosten
		Label costs = new Label("Kosten");
		label.addComponent(costs);
		TextField price = new TextField("Warmmiete:");
		price.setValue(String.valueOf(offer.getPrice()));
		price.setEnabled(false);
		z1.addComponent(price);
		TextField bond = new TextField("Kaution:");
		price.setValue(String.valueOf(offer.getBond()));
		bond.setEnabled(false);
		z2.addComponent(bond);
		TextField cost = new TextField("Sonstige Kosten:");
		cost.setEnabled(false);
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
		HorizontalLayout hl2 = new HorizontalLayout();
		hl2.setWidth("100%");
		CheckBox internet = new CheckBox("Internet");
		internet.setValue(offer.isInternet());
		internet.setWidth("20%");
		internet.setEnabled(false);
		CheckBox furnished = new CheckBox("Möbliert");
		furnished.setValue(offer.isFurnished());
		furnished.setWidth("20%");
		furnished.setEnabled(false);
		CheckBox kitchen = new CheckBox("Küche");
		kitchen.setValue(offer.isKitchen());
		kitchen.setWidth("20%");
		kitchen.setEnabled(false);
		CheckBox smoker = new CheckBox("Raucher");
		smoker.setValue(offer.isSmoker());
		smoker.setWidth("20%");
		smoker.setEnabled(false);
		CheckBox pets = new CheckBox("Haustiere");
		pets.setValue(offer.isPets());
		pets.setWidth("20%");
		pets.setEnabled(false);
		ComboBox genders = new ComboBox("Bevorzugtes Geschlecht:");
		genders.addItem("egal");
		genders.addItem("männlich");
		genders.addItem("weiblich");
		if(offer.getGender()==1)
			genders.setNullSelectionItemId("egal");
		else if(offer.getGender()==2)
			genders.setNullSelectionItemId("männlich");
		else if(offer.getGender()==3)
			genders.setNullSelectionItemId("weiblich");
		genders.setEnabled(false);

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

		// Anzeigetext
		Label anzeigetext = new Label("Anzeigetext");
		Label text = new Label(offer.getText());
		text.setWidth("100%");
		text.setEnabled(false);

		content.addComponent(anzeigetext);
		content.addComponent(new Label());
		content.addComponent(text);
		content.addComponent(new Label());

		HorizontalLayout buttons = new HorizontalLayout();
		Button change = new Button("bearbeiten");
		change.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				String name = "AngebotErstellen";
				getUI().getNavigator().addView(name, new AngebotErstellen()); // momentan angezeigtes Angebot soll übergeben werden...
				getUI().getNavigator().navigateTo(name);
			}
		});

		buttons.addComponent(change);
		content.addComponent(buttons);

	}
	public void setContent() {

		content = new VerticalLayout();
		content.setMargin(true);

		// Titel + Adresse
		HorizontalLayout hl = new HorizontalLayout();
		// hl.setWidth("100%");
		Label ltitel = new Label("Titel");
		ltitel.setWidth("10%");
		TextField titel = new TextField();
		titel.setValue("Schönste Wohnung in Karlsruhe");
		titel.setEnabled(false);
		titel.setWidth("90%");
		hl.addComponent(ltitel);
		hl.addComponent(titel);
		Label adress = new Label("Adresse");
		adress.setEnabled(false);
		Label street = new Label("Straße, Hausnummer");
		street.setValue("Morgenstraße 100");
		street.setEnabled(false);
		HorizontalLayout hl0 = new HorizontalLayout();
		hl0.setWidth("50%");
		TextField zip = new TextField("PLZ");
		zip.setValue("76137");
		zip.setWidth("50%");
		zip.setEnabled(false);
		TextField city = new TextField("Ort");
		city.setValue("Karlsruhe");
		city.setWidth("50%");
		city.setEnabled(false);
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
		label.addComponent(allgInfo);
		ComboBox isShared = new ComboBox("Art");
		isShared.addItem("Wohnung");
		isShared.addItem("Zimmer");
		isShared.addItem("WG-Zimmer");
		if(1==1)
			isShared.setNullSelectionItemId("Wohnung");
		else if(1==2)
			isShared.setNullSelectionItemId("Zimmer");
		else if(1==3)
			isShared.setNullSelectionItemId("WG-Zimmer");		
		isShared.setEnabled(false);
		z1.addComponent(isShared);
		
		TextField squareMetre = new TextField("Größe (in m²)");		
		squareMetre.setValue("26"); 
		squareMetre.setEnabled(false);
		z2.addComponent(squareMetre);
		
		TextField roomMates = new TextField("Anzahl Mitbewohner:");
		roomMates.setValue("0");
		roomMates.setEnabled(false);
		z3.addComponent(roomMates);
		
		Label date = new Label("Verfügbarkeit");
		DateField startDate = new DateField("von:");
		startDate.setValue(new Date());
		startDate.setEnabled(false);
		DateField endDate = new DateField("bis:");
		endDate.setValue(new Date());
		endDate.setEnabled(false);
		HorizontalLayout hl1 = new HorizontalLayout();
		hl1.setWidth("50%");
		hl1.addComponent(startDate);
		hl1.addComponent(endDate);

		// Kosten
		Label costs = new Label("Kosten");
		label.addComponent(costs);
		TextField price = new TextField("Warmmiete:");
		price.setValue("400");
		price.setEnabled(false);
		z1.addComponent(price);
		TextField bond = new TextField("Kaution:");
		price.setValue("550");
		bond.setEnabled(false);
		z2.addComponent(bond);
		TextField cost = new TextField("Sonstige Kosten:");
		cost.setEnabled(false);
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
		HorizontalLayout hl2 = new HorizontalLayout();
		hl2.setWidth("100%");
		CheckBox internet = new CheckBox("Internet");
		internet.setValue(true);
		internet.setWidth("20%");
		internet.setEnabled(false);
		CheckBox furnished = new CheckBox("Möbliert");
		furnished.setValue(false);
		furnished.setWidth("20%");
		furnished.setEnabled(false);
		CheckBox kitchen = new CheckBox("Küche");
		kitchen.setValue(true);
		kitchen.setWidth("20%");
		kitchen.setEnabled(false);
		CheckBox smoker = new CheckBox("Raucher");
		smoker.setValue(false);
		smoker.setWidth("20%");
		smoker.setEnabled(false);
		CheckBox pets = new CheckBox("Haustiere");
		pets.setValue(false);
		pets.setWidth("20%");
		pets.setEnabled(false);
		ComboBox genders = new ComboBox("Bevorzugtes Geschlecht:");
		genders.addItem("egal");
		genders.addItem("männlich");
		genders.addItem("weiblich");
		if(1==1)
			genders.setNullSelectionItemId("egal");
		else if(1==2)
			genders.setNullSelectionItemId("männlich");
		else if(1==3)
			genders.setNullSelectionItemId("weiblich");
		genders.setEnabled(false);

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

		// Anzeigetext
		Label anzeigetext = new Label("Anzeigetext");
		Label text = new Label("Hallo \n die Wohnung hat eine optimale Lage, mitten im Herzen der Südstadt....");
		text.setWidth("100%");
		text.setEnabled(false);

		content.addComponent(anzeigetext);
		content.addComponent(new Label());
		content.addComponent(text);
		content.addComponent(new Label());

		HorizontalLayout buttons = new HorizontalLayout();
		Button change = new Button("bearbeiten");
		change.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				String name = "AngebotErstellen";
				getUI().getNavigator().addView(name, new AngebotErstellen()); // momentan angezeigtes Angebot soll übergeben werden...
				getUI().getNavigator().navigateTo(name);
			}
		});

		buttons.addComponent(change);
		content.addComponent(buttons);

	}


}
