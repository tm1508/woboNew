package com.example.housing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Photo;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.OfferProvider;
import com.example.housing.utility.Format;
import com.vaadin.data.Buffered;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class Suchergebnis.
 */
public class AngeboteVerwalten extends VerticalLayout implements View {

	/** The content. */
	VerticalLayout content;

	/**
	 * Instantiates a new angeboteverwalten.
	 */

	OfferProvider op = new OfferProvider();
	List<Offer> angebote = op.ownOffers(VaadinSession.getCurrent().getAttribute(User.class));
	// �bergabe der Ergebnis aus der Suche
	public AngeboteVerwalten() {
		content = new VerticalLayout();
		content.setMargin(true);
		content.setSizeFull();
		content.setSpacing(true);

		Navigation nav = new Navigation();

		NavigationPublic navPublic = new NavigationPublic();
		addComponent(navPublic);

		// falls der Benutzer eingelogt ist ver�ndert sich die Navigation
		if (VaadinSession.getCurrent().getAttribute("login").equals(true)) {
			nav.setVisible(true);
			navPublic.setVisible(false);
		} else {
			nav.setVisible(false);
			navPublic.setVisible(true);
		}

		addComponent(nav);

		setContent();
		addComponent(content);

		Footer f = new Footer();
		addComponent(f);
		// setComponentAlignment(f, com.vaadin.ui.Alignment.TOP_CENTER);
		// content.addComponent(f);
	}

	/**
	 * Sets the content.
	 */
	@SuppressWarnings("deprecation")
	public void setContent() {

		// Anzahl der gefundenen Ergebnisse
		int anzahl = angebote.size();
		System.out.println(anzahl);
		for (int i = 0; i < anzahl; i++) {
			final Offer o = angebote.get(i);

			content.addComponent(new Listenzeile(o));
		}

	}

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

}
