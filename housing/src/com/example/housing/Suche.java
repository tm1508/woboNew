package com.example.housing;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.OfferProvider;
import com.example.housing.utility.Format;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
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
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

/**
 * The Class Suche.
 */
public class Suche extends CustomHorizontalLayout implements View {
	private static final long serialVersionUID = 1L;

	/** The content. */
	VerticalLayout content;

	private Double lat = 49.00705;
	private Double lon = 8.40287;
	private Double umkreis = 5.00;

	private PopupDateField zeitVon = new PopupDateField("von");

	private PopupDateField zeitBis = new PopupDateField("bis");

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
	 * Instantiates a new suche.
	 */
	public Suche() {
		content = super.initCustomHorizontalLayout();
		setContent();
	}

	/**
	 * Sets the content.
	 */
	public void setContent() {

		// Titel
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Suche nach Kriterien");
		title.addStyleName("title");
		content.addComponent(title);

		GridLayout tabelleAussen = new GridLayout(2, 1);
		tabelleAussen.setSpacing(true);

		GridLayout tabelleInnen = new GridLayout(3, 7);
		tabelleAussen.addComponent(tabelleInnen, 0, 0);

		// Stadt
		tabelleInnen.addComponent(new Label("Ort:  "), 0, 0);
		final TextField stadt = new TextField();
		tabelleInnen.addComponent(stadt, 1, 0);

		// Groesse
		tabelleInnen.addComponent(new Label("Größe (in m²):  "), 0, 1);
		final TextField sucheVon = new TextField("von");
		tabelleInnen.addComponent(sucheVon, 1, 1);
		final TextField sucheBis = new TextField("bis");
		tabelleInnen.addComponent(sucheBis, 2, 1);

		// Preis
		tabelleInnen.addComponent(new Label("Warmmiete (in €):  "), 0, 2);
		final TextField preisVon = new TextField("von");
		tabelleInnen.addComponent(preisVon, 1, 2);
		final TextField preisBis = new TextField("bis");
		tabelleInnen.addComponent(preisBis, 2, 2);

		// Zeitraum
		tabelleInnen.addComponent(new Label("Verfügbarkeit:  "), 0, 3);
		tabelleInnen.addComponent(zeitVon, 1, 3);
		tabelleInnen.addComponent(zeitBis, 2, 3);
		
		// Art der Unterkunft
		tabelleInnen.addComponent(new Label("Art der Unterkunft: "), 0, 4);
		final CheckBox wohnung = new CheckBox("Wohnung");
		final CheckBox zimmer = new CheckBox("Zimmer");
		final CheckBox wg = new CheckBox("WG-Zimmer");
		VerticalLayout verticalLayoutArtderUnterkunft = new VerticalLayout();
		verticalLayoutArtderUnterkunft.setSpacing(true);
		verticalLayoutArtderUnterkunft.addComponent(new Label());
		verticalLayoutArtderUnterkunft.addComponent(wohnung);
		verticalLayoutArtderUnterkunft.addComponent(zimmer);
		verticalLayoutArtderUnterkunft.addComponent(wg);
		verticalLayoutArtderUnterkunft.addComponent(new Label());
		tabelleInnen.addComponent(verticalLayoutArtderUnterkunft, 1, 4);

		// Sonstiges
		tabelleInnen.addComponent(new Label("Sonstige Merkmale:"), 0, 5);
		final CheckBox haustiere = new CheckBox("Haustiere erlaubt");
		final CheckBox rauchen = new CheckBox("Raucher-Wohnung");
		final CheckBox moebliert = new CheckBox("Möblierte Wohnung");
		final CheckBox kueche = new CheckBox("Küche vorhanden");
		final CheckBox internet = new CheckBox("Internetanschluss vorhanden");

		VerticalLayout verticalLayoutSonstiges = new VerticalLayout();
		verticalLayoutSonstiges.setSpacing(true);
		verticalLayoutSonstiges.addComponent(new Label());
		verticalLayoutSonstiges.addComponent(haustiere);
		verticalLayoutSonstiges.addComponent(rauchen);
		verticalLayoutSonstiges.addComponent(moebliert);
		verticalLayoutSonstiges.addComponent(kueche);
		verticalLayoutSonstiges.addComponent(internet);
		verticalLayoutSonstiges.addComponent(new Label());
		tabelleInnen.addComponent(verticalLayoutSonstiges, 1, 5);

		Button suchButton = new Button("Suchen");
		suchButton.setIcon(FontAwesome.SEARCH);
		suchButton.addStyleName("SuchButton");
		tabelleInnen.addComponent(suchButton, 0, 6);

		// Marker
		GoogleMapMarker kakolaMarker = new GoogleMapMarker("Karlsruhe",
				new LatLon(49.00705, 8.40287), true, null);

		// Maps
		final GoogleMap googleMap = new GoogleMap(null, null, null);
		googleMap.setCenter(new LatLon(49.00705, 8.40287));
		googleMap.setZoom(10);
		googleMap.setSizeFull();
		kakolaMarker.setAnimationEnabled(false);
		googleMap.addMarker(kakolaMarker);
		googleMap.setMinZoom(4);
		googleMap.setMaxZoom(16);
		googleMap.setHeight("500px");
		googleMap.setWidth("500px");
		googleMap.setVisible(false);

		// Listener für Marker
		googleMap.addMarkerDragListener(new MarkerDragListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void markerDragged(GoogleMapMarker draggedMarker,
					LatLon oldPosition) {
				lat = draggedMarker.getPosition().getLat();
				lon = draggedMarker.getPosition().getLon();
			}
		});
		
		//Umkreis
		final NativeSelect selectUmkreis = new NativeSelect("Umkreis in km");
		selectUmkreis.addItem(5.00);
		selectUmkreis.setItemCaption(5.00, "5 km");
		selectUmkreis.addItem(10.00);
		selectUmkreis.setItemCaption(10.00, "10 km");
		selectUmkreis.addItem(20.00);
		selectUmkreis.setItemCaption(20.00, "20 km");
		selectUmkreis.addItem(30.00);
		selectUmkreis.setItemCaption(30.00, "30 km");
		selectUmkreis.addItem(50.00);
		selectUmkreis.setItemCaption(50.00, "50 km");
		selectUmkreis.setVisible(false);
		
		selectUmkreis.setNullSelectionAllowed(false);
		selectUmkreis.setValue(5.00);
		selectUmkreis.setImmediate(true);
		selectUmkreis.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(final ValueChangeEvent event) {
				umkreis = (Double) event.getProperty().getValue();
			}
		});

		// Checkbox: Mit Karte suchen
		final CheckBox mitKarteSuchen = new CheckBox(
						"Alternativ auf der Karte suchen", false);
		mitKarteSuchen.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(final ValueChangeEvent event) {
				final boolean value = (boolean) event.getProperty().getValue();
				if (value == true) {// Anzeigen der Map und Leeren des "Ort"-Feldes
					stadt.setEnabled(false);
					stadt.setValue("");
					googleMap.setVisible(true);
					selectUmkreis.setVisible(true);
				} else {// Ausblednen der Map
					stadt.setEnabled(true);
					googleMap.setVisible(false);
					selectUmkreis.setVisible(false);
				}
			}
		});
		

		
		VerticalLayout verticalLayoutMaps = new VerticalLayout();
		verticalLayoutMaps.setSpacing(true);
		verticalLayoutMaps.addComponent(mitKarteSuchen);
		verticalLayoutMaps.addComponent(selectUmkreis);
		verticalLayoutMaps.addComponent(googleMap);
		tabelleAussen.addComponent(verticalLayoutMaps, 1, 0);

		// Suchfunktion
		suchButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("deprecation")
			public void buttonClick(ClickEvent event) {
				
				try { //falls in Zahlenfeder keine Zahlen eingetragen wurden
					
					Format.floatFormat(sucheVon.getValue());
					Format.floatFormat(sucheBis.getValue());
					Format.floatFormat(preisVon.getValue());
					Format.floatFormat(preisBis.getValue());
					
				} catch (NumberFormatException nfe) {
					
					Notification failNumberFormat = new Notification("Bitte überprüfen Sie Ihre Eingaben und benutzen Sie gültige Zahlenformate.");
					failNumberFormat.setDelayMsec(300);
					failNumberFormat.setStyleName("failure");
					failNumberFormat.show(Page.getCurrent());
					return;
					
				}
				
				//System.out.println(zeitVon.getValue().toString());
				//System.out.println(zeitBis.getValue().toString());
				
	/*			try{
					validateDate();
				}catch(IllegalArgumentException e){
					Notification failNumberFormat = new Notification("Bitte überprüfen Sie Ihre Eingaben und geben Sie ein gültiges Datum ein.");
					failNumberFormat.setDelayMsec(300);
					failNumberFormat.setStyleName("failure");
					failNumberFormat.show(Page.getCurrent());
					return;
				}catch(NullPointerException npe){
					//tue nichts (kein Plichtfeld!)
				}*/
				
				/*try{
					validateAfter();
				}catch(Exception e){
					Notification failNumberFormat = new Notification("Das Enddatum muss nach dem Startsatum liegen.");
					failNumberFormat.setDelayMsec(300);
					failNumberFormat.setStyleName("failure");
					failNumberFormat.show(Page.getCurrent());
					return;
				}*/
				
				
				int a = 7;
				if (wohnung.getValue() && zimmer.getValue() && wg.getValue()) {
					a = 7;
				} else if (wohnung.getValue() && zimmer.getValue()) {
					a = 4;
				} else if (zimmer.getValue() && wg.getValue()) {
					a = 5;
				} else if (wg.getValue() && wohnung.getValue()) {
					a = 6;
				} else if (wohnung.getValue()) {
					a = 1;
				} else if (zimmer.getValue()) {
					a = 2;
				} else if (wg.getValue()) {
					a = 3;
				}
				
				int accessLevel =  -1;
				if((boolean) VaadinSession.getCurrent().getSession().getAttribute("login")) {
					accessLevel = ((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getAccessLevel();
				}

				OfferProvider of = new OfferProvider();
				List<Offer> ergebnisse;
				ergebnisse = of.filter(zeitVon.getValue(), zeitBis.getValue(),
						(Format.floatFormat(sucheVon.getValue())),
						(Format.floatFormat(sucheBis.getValue())),
						(Format.floatFormat(preisVon.getValue())),
						(Format.floatFormat(preisBis.getValue())), a,
						internet.getValue(), moebliert.getValue(),
						kueche.getValue(), rauchen.getValue(),
						haustiere.getValue(), stadt.getValue(),
						accessLevel);

				//nur bei Suche über Karte
				if (mitKarteSuchen.booleanValue()) {
					ergebnisse = of.filterMaps(ergebnisse, umkreis, lat, lon);
				}

				String name = "AngebotAnzeigen";
				getUI().getNavigator().addView(name,
						new Suchergebnis(ergebnisse));
				getUI().getNavigator().navigateTo(name);
			}
		});

		content.addComponent(tabelleAussen);
	}
	
	private void validateDate(){
		System.out.println(zeitVon.getValue().toString());
		zeitVon.validate();
		zeitVon.setConversionError("Das Format ist nicht korrekt.");
		if(!zeitVon.getValue().toString().isEmpty() ){
			Format.dateFormat(zeitVon.getValue());
		}
		/*
		if(!zeitBis.getValue().toString().isEmpty() ){
			Format.dateFormat(zeitBis.getValue());
		}*/
		
		
	}
	
	private void validateAfter() throws Exception{
		if(!zeitVon.getValue().toString().isEmpty() && !zeitBis.getValue().toString().isEmpty()){
			if(zeitVon.getValue().after(zeitBis.getValue())){
				throw new Exception("Das Enddatum ist vor dem Anfangsdatum");
			}
		}
	}

}
