package com.example.housing;

import java.util.Date;

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
import com.vaadin.ui.GridLayout;
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
	public AngebotAnzeigen(Offer offer) {
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
		setContent(offer);
		addComponent(content);

		Footer f = new Footer();
		addComponent(f);
	}

	/*public AngebotAnzeigen() { //nicht unbedingt notwendig
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
	}*/
	/**
	 * Sets the content.
	 */	
	public void setContent(final Offer offer) {

		content = new VerticalLayout();
		content.setMargin(true);

		GridLayout gridAngebot = new GridLayout(5,16); 
		gridAngebot.setSpacing(true);
		//gridSuche.setWidth("40%");
		//gridAngebot.addStyleName("AngebotAnzeigen");
		
		// Titel + Adresse
		Label ltitel = new Label("Titel");
		ltitel.addStyleName("AbschnittLabel");
		
		Label titel = new Label();
		titel.setValue(offer.getTitle());
		gridAngebot.addComponent(ltitel, 0,0);
		gridAngebot.addComponent(titel, 1, 0);
		

		Label adress = new Label("Adresse");
		adress.addStyleName("AbschnittLabel");
		gridAngebot.addComponent(adress, 0, 1);
		
		Label street = new Label("Straße, Hausnummer");
		Label streetValue = new Label(offer.getStreet());
		gridAngebot.addComponent(street,0,2);
		gridAngebot.addComponent(streetValue, 1, 2);
		
		
		Label zip = new Label("PLZ");
		Label zipValue = new Label(offer.getZip());
		gridAngebot.addComponent(zip, 0, 3);
		gridAngebot.addComponent(zipValue,1,3);
		Label city = new Label("Ort");
		Label cityValue = new Label(offer.getCity());
		gridAngebot.addComponent(city, 2, 3);
		gridAngebot.addComponent(cityValue,3,3);

		// Allgemeine Informationen		
		Label allgInfo = new Label("Allgemeine Angaben");
		allgInfo.addStyleName("AbschnittLabel");
		gridAngebot.addComponent(allgInfo,0,4);

		Label isShared = new Label("Art");
		Label isSharedValue = new Label();
		if(offer.getType()==1)
			isSharedValue.setValue("Wohnung");
		else if(offer.getType()==2)
			isSharedValue.setValue("Zimmer");
		else if(offer.getType()==3)
			isSharedValue.setValue("WG-Zimmer");

		gridAngebot.addComponent(isShared,0,5);
		gridAngebot.addComponent(isSharedValue,1,5);
		
		Label squareMetre = new Label("Größe (in m²)");		
		Label squareMetreValue = new Label("26"); 
		gridAngebot.addComponent(squareMetre,0,6);
		gridAngebot.addComponent(squareMetreValue,1,6);
		
		Label roomMates = new Label("Anzahl Mitbewohner:");
		Label roomMatesValue= new Label(String.valueOf(offer.getNumberOfRoommate()));
		gridAngebot.addComponent(roomMates,0,7);
		gridAngebot.addComponent(roomMatesValue,1,7);		
		
		// Kosten
		Label costs = new Label("Kosten");
		costs.addStyleName("AbschnittLabel");
		gridAngebot.addComponent(costs, 3,4);
		
		Label price = new Label("Warmmiete:");
		Label priceValue = new Label(String.valueOf(offer.getPrice()));
		gridAngebot.addComponent(price, 3, 5);
		gridAngebot.addComponent(priceValue, 4, 5);
		
		Label bond = new Label("Kaution:");
		Label bondValue = new Label(String.valueOf(offer.getBond()));
		gridAngebot.addComponent(bond,3,6);
		gridAngebot.addComponent(bondValue,4,6);

		//Verfügbarkeit
		Label date = new Label("Verfügbarkeit");
		date.addStyleName("AbschnittLabel");
		DateField startDate = new DateField("von:");
		startDate.setValue(offer.getStartDate());
		startDate.setEnabled(false);
		DateField endDate = new DateField("bis:");
		endDate.setValue(offer.getEndDate());
		endDate.setEnabled(false);
		gridAngebot.addComponent(date,0,8);
		gridAngebot.addComponent(startDate,0,9);
		gridAngebot.addComponent(endDate,2,9);

		// weitere Angaben
		Label angaben = new Label("Weitere Details");
		angaben.addStyleName("AbschnittLabel");
		gridAngebot.addComponent(angaben,0,10);
		
		CheckBox internet = new CheckBox("Internet");
		internet.setValue(offer.isInternet());
		internet.setReadOnly(true);
		CheckBox furnished = new CheckBox("Möbliert");
		furnished.setValue(offer.isFurnished());
		furnished.setReadOnly(true);
		CheckBox kitchen = new CheckBox("Küche");
		kitchen.setValue(offer.isKitchen());
		kitchen.setReadOnly(true);
		CheckBox smoker = new CheckBox("Raucher");
		smoker.setValue(offer.isSmoker());
		smoker.setReadOnly(true);
		CheckBox pets = new CheckBox("Haustiere");
		pets.setValue(offer.isPets());
		pets.setReadOnly(true);
		Label genders = new Label("Bevorzugtes Geschlecht:");
		Label gendersValue = new Label();
		if(offer.getGender()==1)
			gendersValue.setValue("egal");
		else if(offer.getGender()==2)
			gendersValue.setValue("männlich");
		else if(offer.getGender()==3)
			gendersValue.setValue("weiblich");
		


		gridAngebot.addComponent(internet,0,12);
		gridAngebot.addComponent(furnished,1,12);
		gridAngebot.addComponent(kitchen,2,12);
		gridAngebot.addComponent(smoker,3,12);
		gridAngebot.addComponent(pets,4,12);
		gridAngebot.addComponent(genders,0,11);
		gridAngebot.addComponent(gendersValue,1,11);

		// Anzeigetext
		Label anzeigetext = new Label("Anzeigetext");
		anzeigetext.addStyleName("AbschnittLabel");
		Label text = new Label(offer.getText());
		text.setWidth("100%");
		text.setEnabled(false);
		
		gridAngebot.addComponent(anzeigetext,0,13);
		content.addComponent(gridAngebot);
		content.addComponent(text);


		Button change = new Button("bearbeiten");
		change.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				String name = "AngebotErstellen";
				getUI().getNavigator().addView(name, new AngebotErstellen(offer)); // momentan angezeigtes Angebot soll übergeben werden...
				getUI().getNavigator().navigateTo(name);
			}
		});
		
		content.addComponent(new Label());
		content.addComponent(change);
		

	}
	
	/*public void setContent() {

		content = new VerticalLayout();
		content.setMargin(true);

		GridLayout gridAngebot = new GridLayout(5,16); 
		gridAngebot.setSpacing(true);
		//gridSuche.setWidth("40%");
		//gridAngebot.addStyleName("AngebotAnzeigen");
		
		// Titel + Adresse
		Label ltitel = new Label("Titel");
		ltitel.addStyleName("AbschnittLabel");
		
		Label titel = new Label();
		titel.setValue("Schönste Wohnung in Karlsruhe");
		gridAngebot.addComponent(ltitel, 0,0);
		gridAngebot.addComponent(titel, 1, 0);
		

		Label adress = new Label("Adresse");
		adress.addStyleName("AbschnittLabel");
		gridAngebot.addComponent(adress, 0, 1);
		
		Label street = new Label("Straße, Hausnummer");
		Label streetValue = new Label("Morgenstraße 100");
		gridAngebot.addComponent(street,0,2);
		gridAngebot.addComponent(streetValue, 1, 2);
		
		
		Label zip = new Label("PLZ");
		Label zipValue = new Label("76137");
		gridAngebot.addComponent(zip, 0, 3);
		gridAngebot.addComponent(zipValue,1,3);
		Label city = new Label("Ort");
		Label cityValue = new Label("Karlsruhe");
		gridAngebot.addComponent(city, 2, 3);
		gridAngebot.addComponent(cityValue,3,3);

		// Allgemeine Informationen		
		Label allgInfo = new Label("Allgemeine Angaben");
		allgInfo.addStyleName("AbschnittLabel");
		gridAngebot.addComponent(allgInfo,0,4);

		Label isShared = new Label("Art");
		Label isSharedValue = new Label("Wohnung");
		gridAngebot.addComponent(isShared,0,5);
		gridAngebot.addComponent(isSharedValue,1,5);
		
		Label squareMetre = new Label("Größe (in m²)");		
		Label squareMetreValue = new Label("26"); 
		gridAngebot.addComponent(squareMetre,0,6);
		gridAngebot.addComponent(squareMetreValue,1,6);
		
		Label roomMates = new Label("Anzahl Mitbewohner:");
		Label roomMatesValue= new Label("0");
		gridAngebot.addComponent(roomMates,0,7);
		gridAngebot.addComponent(roomMatesValue,1,7);		
		
		// Kosten
		Label costs = new Label("Kosten");
		costs.addStyleName("AbschnittLabel");
		gridAngebot.addComponent(costs, 3,4);
		
		Label price = new Label("Warmmiete:");
		Label priceValue = new Label("400");
		gridAngebot.addComponent(price, 3, 5);
		gridAngebot.addComponent(priceValue, 4, 5);
		
		Label bond = new Label("Kaution:");
		Label bondValue = new Label("600");
		gridAngebot.addComponent(bond,3,6);
		gridAngebot.addComponent(bondValue,4,6);

		//Verfügbarkeit
		Label date = new Label("Verfügbarkeit");
		date.addStyleName("AbschnittLabel");
		DateField startDate = new DateField("von:");
		startDate.setValue(new Date());
		startDate.setEnabled(false);
		DateField endDate = new DateField("bis:");
		endDate.setValue(new Date());
		endDate.setEnabled(false);
		gridAngebot.addComponent(date,0,8);
		gridAngebot.addComponent(startDate,0,9);
		gridAngebot.addComponent(endDate,2,9);

		// weitere Angaben
		Label angaben = new Label("Weitere Details");
		angaben.addStyleName("AbschnittLabel");
		gridAngebot.addComponent(angaben,0,10);
		
		CheckBox internet = new CheckBox("Internet");
		internet.setValue(true);
		internet.setReadOnly(true);
		CheckBox furnished = new CheckBox("Möbliert");
		furnished.setValue(false);
		furnished.setReadOnly(true);
		CheckBox kitchen = new CheckBox("Küche");
		kitchen.setValue(true);
		kitchen.setReadOnly(true);
		CheckBox smoker = new CheckBox("Raucher");
		smoker.setValue(false);
		smoker.setReadOnly(true);
		CheckBox pets = new CheckBox("Haustiere");
		pets.setValue(false);
		pets.setReadOnly(true);
		Label genders = new Label("Bevorzugtes Geschlecht:");
		Label gendersValue = new Label("egal");


		gridAngebot.addComponent(internet,0,12);
		gridAngebot.addComponent(furnished,1,12);
		gridAngebot.addComponent(kitchen,2,12);
		gridAngebot.addComponent(smoker,3,12);
		gridAngebot.addComponent(pets,4,12);
		gridAngebot.addComponent(genders,0,11);
		gridAngebot.addComponent(gendersValue,1,11);

		// Anzeigetext
		Label anzeigetext = new Label("Anzeigetext");
		anzeigetext.addStyleName("AbschnittLabel");
		Label text = new Label("Hallo \n die Wohnung hat eine optimale Lage, mitten im Herzen der Südstadt....");
		text.setWidth("100%");
		text.setEnabled(false);
		
		gridAngebot.addComponent(anzeigetext,0,13);
		content.addComponent(gridAngebot);
		content.addComponent(text);


		Button change = new Button("bearbeiten");
		change.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				String name = "AngebotErstellen";
				getUI().getNavigator().addView(name, new AngebotErstellen()); // momentan angezeigtes Angebot soll übergeben werden...
				getUI().getNavigator().navigateTo(name);
			}
		});
		
		content.addComponent(new Label());
		content.addComponent(change);
		

	}*/


}
