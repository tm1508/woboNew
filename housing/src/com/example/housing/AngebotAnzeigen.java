package com.example.housing;

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

public class AngebotAnzeigen extends VerticalLayout implements View {

	
	VerticalLayout content;

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public AngebotAnzeigen() {
		Navigation nav = new Navigation();
		addComponent(nav);
		//setSizeFull();
		NavigationPublic navPublic = new NavigationPublic();
		addComponent(navPublic);
		
		//falls der Benutzer eingelogt ist ver�ndert sich die Navigation
		if(VaadinSession.getCurrent().getAttribute("login").equals(true)){
			nav.setVisible(true);
			navPublic.setVisible(false);
		}else{
			nav.setVisible(false);
			navPublic.setVisible(true);
		}

		setContent();
		addComponent(content);

		Footer f = new Footer();
		addComponent(f);
	}

	public void setContent() {

		content = new VerticalLayout();
		content.setMargin(true);

		// Titel + Adresse
		HorizontalLayout hl = new HorizontalLayout();
		//hl.setWidth("100%");
		Label ltitel = new Label("Titel");
		ltitel.setWidth("10%");
		TextField titel = new TextField();
		titel.setEnabled(false);
		titel.setWidth("90%");
		hl.addComponent(ltitel);
		hl.addComponent(titel);
		Label adress = new Label("Adresse");
		adress.setEnabled(false);
		TextField street = new TextField("Stra�e, Hausnummer");
		street.setEnabled(false);
		HorizontalLayout hl0 = new HorizontalLayout();
		hl0.setWidth("50%");
		TextField zip = new TextField("PLZ");
		zip.setWidth("50%");
		zip.setEnabled(false);
		TextField city = new TextField("Ort");
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
		isShared.setEnabled(false);
		z1.addComponent(isShared);
		TextField squareMetre = new TextField("Gr��e (in m�)");
		squareMetre.setEnabled(false);
		z2.addComponent(squareMetre);
		TextField roomMates = new TextField("Anzahl Mitbewohner:");
		roomMates.setEnabled(false);
		z3.addComponent(roomMates);
		Label date = new Label("Verf�gbarkeit");
		DateField startDate = new DateField("von:");
		startDate.setEnabled(false);
		DateField endDate = new DateField("bis:");
		endDate.setEnabled(false);
		HorizontalLayout hl1 = new HorizontalLayout();
		hl1.setWidth("50%");
		hl1.addComponent(startDate);
		hl1.addComponent(endDate);
		


		
		//Kosten
		Label costs = new Label("Kosten");
		label.addComponent(costs);
		TextField price = new TextField("Warmmiete:");
		price.setEnabled(false);
		z1.addComponent(price);
		TextField bond = new TextField ("Kaution:");
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
		
		//weitere Angaben
		Label angaben = new Label("Weitere Details");
		HorizontalLayout hl2 = new HorizontalLayout();
		hl2.setWidth("100%");
		CheckBox internet = new CheckBox("Internet");
		internet.setWidth("20%");
		internet.setEnabled(false);
		CheckBox furnished = new CheckBox("M�bliert");
		furnished.setWidth("20%");
		furnished.setEnabled(false);
		CheckBox kitchen = new CheckBox("K�che");
		kitchen.setWidth("20%");
		kitchen.setEnabled(false);
		CheckBox smoker = new CheckBox("Raucher");
		smoker.setWidth("20%");
		smoker.setEnabled(false);
		CheckBox pets = new CheckBox("Haustiere");
		pets.setWidth("20%");
		pets.setEnabled(false);
		ComboBox gender = new ComboBox("Bevorzugtes Geschlecht:");
		gender.addItem("m�nnlich");
		gender.addItem("weiblich");
		gender.setEnabled(false);
		
		hl2.addComponent(internet);
		hl2.addComponent(furnished);
		hl2.addComponent(kitchen);
		hl2.addComponent(smoker);
		hl2.addComponent(pets);
		content.addComponent(angaben);
		content.addComponent(new Label());
		content.addComponent(hl2);
		content.addComponent(new Label());
		content.addComponent(gender);
		content.addComponent(new Label());
		
		//Anzeigetext
		Label anzeigetext = new Label("Anzeigetext");
		RichTextArea text = new RichTextArea();
		text.setWidth("100%");
		text.setEnabled(false);
		
		content.addComponent(anzeigetext);
		content.addComponent(new Label());
		content.addComponent(text);
		content.addComponent(new Label());
		
		HorizontalLayout buttons = new HorizontalLayout();
		Button save = new Button("bearbeiten");
		Button activate = new Button ("deaktivieren");
		activate.setVisible(false);
		
		buttons.addComponent(activate);
		buttons.addComponent(save);		
		content.addComponent(buttons);
		
	}

}
