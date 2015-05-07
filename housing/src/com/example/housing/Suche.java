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
import com.vaadin.server.VaadinSession;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.MarkerDragListener;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Suche.
 */
public class Suche extends HorizontalLayout implements View {

	/** The content. */
	VerticalLayout content;

	private Double lat = 0.0;
	private Double lon = 0.0;

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
		this.setWidth("100%");

		// linkes rotes Panel
		Panel p = new Panel();
		p.setWidth("100%");
		p.setHeight("100%");
		p.addStyleName("red");
		addComponent(p);
		this.setExpandRatio(p, 1);

		// mittlerer Teil der Seite
		VerticalLayout v = new VerticalLayout();

		// Navigation hinzufuegen
		Navigation nav = new Navigation();
		nav.setWidth("100%");
		nav.addStyleName("navigation");
		v.addComponent(nav);

		NavigationPublic navPublic = new NavigationPublic();
		v.addComponent(navPublic);

		NavigationAdmin navAdmin = new NavigationAdmin();
		v.addComponent(navAdmin);

		// falls der Benutzer eingelogt ist ver�ndert sich die Navigation
		if (VaadinSession.getCurrent().getAttribute("login").equals(true)) {
			if (VaadinSession.getCurrent().getAttribute(User.class)
					.getAccessLevel() == 2) {// falls der User ein Admin ist
				nav.setVisible(false);
				navPublic.setVisible(false);
				navAdmin.setVisible(true);// Admin-Navigation
			} else {// ansonsten: Naviagtion f�r eingeloggte Nutzer
				nav.setVisible(true);
				navPublic.setVisible(false);
				navAdmin.setVisible(false);
			}
		} else {// ansonsten Public Navigation (f�r alle)
			nav.setVisible(false);
			navPublic.setVisible(true);
			navAdmin.setVisible(false);
		}

		// Inhalt hinzufuegen
		content = new VerticalLayout();
		content.setMargin(true);
		content.setWidth("100%");
		setContent();// Methode zum befuellen des Inhalts aufrufen
		v.addComponent(content);

		// Footer hinzufuegen
		Footer f = new Footer();
		v.addComponent(f);

		// rotes Panel unter dem Footer
		Panel p2 = new Panel();
		p2.setWidth("100%");
		p2.addStyleName("red");
		p2.setHeight("30px");
		v.addComponent(p2);

		addComponent(v);
		this.setExpandRatio(v, 12);

		// rotes rechtes Panel
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
	public void setContent() {

		// Titel
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Suche");
		title.addStyleName("title");
		content.addComponent(title);

		GridLayout grid1 = new GridLayout(2, 1);
		grid1.setSpacing(true);

		GridLayout grid2 = new GridLayout(3, 7);
		grid1.addComponent(grid2, 0, 0);

		// Stadt
		grid2.addComponent(new Label("Ort:  "), 0, 0);
		final TextField stadt = new TextField();
		grid2.addComponent(stadt, 1, 0);

		// Groesse
		grid2.addComponent(new Label("Gr��e (in m�):  "), 0, 1);
		final TextField sucheVon = new TextField("von");
		grid2.addComponent(sucheVon, 1, 1);
		final TextField sucheBis = new TextField("bis");
		grid2.addComponent(sucheBis, 2, 1);

		// Preis
		grid2.addComponent(new Label("Warmmiete (in �):  "), 0, 2);
		final TextField preisVon = new TextField("von");
		grid2.addComponent(preisVon, 1, 2);
		final TextField preisBis = new TextField("bis");
		grid2.addComponent(preisBis, 2, 2);

		// Zeitraum
		grid2.addComponent(new Label("Verf�gbarkeit:  "), 0, 3);
		final PopupDateField zeitVon = new PopupDateField("von");
		grid2.addComponent(zeitVon, 1, 3);
		final PopupDateField zeitBis = new PopupDateField("bis");
		grid2.addComponent(zeitBis, 2, 3);

		// Art der Unterkunft
		grid2.addComponent(new Label("Art der Unterkunft: "), 0, 4);
		final CheckBox wohnung = new CheckBox("Wohnung");
		final CheckBox zimmer = new CheckBox("Zimmer");
		final CheckBox wg = new CheckBox("WG-Zimmer");
		VerticalLayout h = new VerticalLayout();
		h.setSpacing(true);
		h.addComponent(new Label());
		h.addComponent(wohnung);
		h.addComponent(zimmer);
		h.addComponent(wg);
		h.addComponent(new Label());
		grid2.addComponent(h, 1, 4);

		// Sonstiges
		grid2.addComponent(new Label("Sonstige Merkmale:"), 0, 5);
		final CheckBox haustiere = new CheckBox("Haustiere erlaubt");
		final CheckBox rauchen = new CheckBox("Raucher-Wohnung");
		final CheckBox moebliert = new CheckBox("M�blierte Wohnung");
		final CheckBox kueche = new CheckBox("K�che vorhanden");
		final CheckBox internet = new CheckBox("Internetanschluss vorhanden");

		VerticalLayout h2 = new VerticalLayout();
		h2.setSpacing(true);
		h2.addComponent(new Label());
		h2.addComponent(haustiere);
		h2.addComponent(rauchen);
		h2.addComponent(moebliert);
		h2.addComponent(kueche);
		h2.addComponent(internet);
		h2.addComponent(new Label());
		grid2.addComponent(h2, 1, 5);

		Button suchButton = new Button("Suchen");
		suchButton.setIcon(FontAwesome.SEARCH);
		suchButton.addStyleName("SuchButton");
		grid2.addComponent(suchButton, 0, 6);

		// Rechte Spalte

		// Maps

		// ** The kakola marker. *//*
		GoogleMapMarker kakolaMarker = new GoogleMapMarker("Karlsruhe",
				new LatLon(49.00705, 8.40287), true, null);

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

		final CheckBox b = new CheckBox("Statdessen auf der Karte Suchen",
				false);

		b.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(final ValueChangeEvent event) {
				final boolean value = (boolean) event.getProperty().getValue();

				if (value == true) {// Anzeigen der Moodle Felder sobald das
									// Kontrollk�stchen angekreuzt wird
					stadt.setEnabled(false);
					stadt.setValue("");
					googleMap.setVisible(true);

				} else {// ausblednen der Felder wenn das K�stchen nicht
						// angekreuzt ist
					stadt.setEnabled(true);
					googleMap.setVisible(false);
				}
			}
		});

		VerticalLayout h3 = new VerticalLayout();
		h3.setSpacing(true);
		h3.addComponent(b);
		h3.addComponent(googleMap);

		grid1.addComponent(h3, 1, 0);

		googleMap.setVisible(false);

		googleMap.addMarkerDragListener(new MarkerDragListener() {
			@Override
			public void markerDragged(GoogleMapMarker draggedMarker,
					LatLon oldPosition) {
				lat = draggedMarker.getPosition().getLat();
				lon = draggedMarker.getPosition().getLon();
				// TODO Auto-generated method stub
				System.out.println(draggedMarker.getPosition().getLat() + "---"
						+ draggedMarker.getPosition().getLon());

			}
		});

		// Suchfunktion

		suchButton.addClickListener(new Button.ClickListener() {
			@SuppressWarnings("deprecation")
			public void buttonClick(ClickEvent event) {
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

				OfferProvider of = new OfferProvider();
				List<Offer> ergebnisse;
				ergebnisse = of.filter(zeitVon.getValue(), zeitBis.getValue(),
						(Format.floatFormat(sucheVon.getValue())),
						(Format.floatFormat(sucheBis.getValue())),
						(Format.floatFormat(preisVon.getValue())),
						(Format.floatFormat(preisBis.getValue())), a,
						internet.getValue(), moebliert.getValue(),
						kueche.getValue(), rauchen.getValue(),
						haustiere.getValue(), stadt.getValue());

				if (b.booleanValue()) {
					ergebnisse = of.filterMaps(ergebnisse, 10.00, lat, lon);
				}

				String name = "AngebotAnzeigen";
				getUI().getNavigator().addView(name,
						new Suchergebnis(ergebnisse));
				getUI().getNavigator().navigateTo(name);
			}
		});

		content.addComponent(grid1);

	}

}
