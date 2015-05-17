package com.example.housing;

import java.util.List;
import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.OfferProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class Suchergebnis.
 */
public class AngeboteVerwalten extends CustomHorizontalLayout implements View {
	private static final long serialVersionUID = 1L;

	/** The content. */
	VerticalLayout content;

	/**
	 * Instantiates a new angeboteverwalten.
	 */

	OfferProvider op = new OfferProvider();
	List<Offer> angebote = op.findByUser((User) VaadinSession.getCurrent().getSession().getAttribute("user"));
	// Übergabe der Ergebnis aus der Suche
	public AngeboteVerwalten() {
		content = super.initCustomHorizontalLayout();
		setContent();
	}

	/**
	 * Sets the content.
	 */
	public void setContent() {
		
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Meine Wohnungsangebote");
		title.addStyleName("title");
		content.addComponent(title);

		// Anzahl der gefundenen Ergebnisse
		int anzahl = angebote.size();
		Label eigeneAngeboteString = new Label("Sie haben folgende " + anzahl + " Wohnungsangebote eingestellt.");
		eigeneAngeboteString.addStyleName("AbschnittLabel");
		content.addComponent(eigeneAngeboteString);
		
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
