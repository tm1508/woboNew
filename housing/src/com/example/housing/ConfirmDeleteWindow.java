package com.example.housing;

import com.example.housing.data.model.Offer;
import com.example.housing.data.provider.OfferProvider;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfirmDeleteWindow.
 */
public class ConfirmDeleteWindow extends Window {
	
	/** The current offer. */
	private static Offer currentOffer;
	
	/**
	 * Instantiates a new confirm delete window.
	 *
	 * @param offer the offer
	 */
	public ConfirmDeleteWindow(Offer offer) {
		super("Bitte best�tigen Sie die L�schung...");
		currentOffer = offer;
		initialisieren();
	}
	
	/**
	 * Initialisieren.
	 */
	public void initialisieren() {
		this.center();
		this.setHeight("50%");
	    this.setWidth("30%");
	    
	    final VerticalLayout content = new VerticalLayout();
	    content.setMargin(true);
	    
	    //text
	    Label text = new Label();
		text.setImmediate(false);
		//text.setWidth("-1px");
		//text.setHeight("-1px");
		//TODO Zeilenumbruch
		text.setValue("Wollen Sie Ihr Angebot " + currentOffer.getTitle() + " wirklich unwiderruflich l�schen? \n Alternativ k�nnen Sie es auch deaktivieren, indem Sie das Angebot bearbeiten \n und dort den Haken bei \"deaktivieren\" setzen. \n Auf diese Weise k�nnen Sie das Angebot gegebenenfalls sp�ter wieder reaktivieren.");
		content.addComponent(text);
		
		HorizontalLayout buttons = new HorizontalLayout();
		
		//cancel-Button
		Button cancel = new Button();
		cancel.setCaption("Abbrechen");
		cancel.setImmediate(true);
		cancel.setDescription("Angebot endg�ltig l�schen");
		cancel.setWidth("-1px");
		cancel.setHeight("-1px");
		buttons.addComponent(cancel);
		cancel.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				Notification.show("Das Angebot wurde nicht gel�scht.", Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
				
				ConfirmDeleteWindow.this.close();
				
			}
		});
		
		//delete-Button
		Button delete = new Button();
		delete.setCaption("L�schen");
		delete.setImmediate(true);
		delete.setDescription("Angebot endg�ltig l�schen");
		delete.setWidth("-1px");
		delete.setHeight("-1px");
		buttons.addComponent(delete);
		delete.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				//TODO
				new OfferProvider().removeOffer(currentOffer);
				
				Notification.show("Das Angebot wurde gel�scht und aus der Datenbank entfernt.", Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
				
				String name = "Angebote verwalten";
				getUI().getNavigator().addView(name, new AngeboteVerwalten());
				getUI().getNavigator().navigateTo(name);
				
				ConfirmDeleteWindow.this.close();
				
			}
		});
	    
		content.addComponent(buttons);
		
	    this.setContent(content);
	    
	}

}
