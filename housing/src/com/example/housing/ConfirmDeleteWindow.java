package com.example.housing;

import com.example.housing.data.model.Offer;
import com.example.housing.data.provider.OfferProvider;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;

public class ConfirmDeleteWindow extends Window {
	private static final long serialVersionUID = 1L;
	
	private static Offer currentOffer;
	
	public ConfirmDeleteWindow(Offer offer) {
		super("Bitte bestätigen Sie die Löschung...");
		currentOffer = offer;
		initialisieren();
	}
	
	public void initialisieren() {
		this.center();
		this.setHeight("50%");
	    this.setWidth("30%");
	    
	    final VerticalLayout content = new VerticalLayout();
	    content.setMargin(true);
	    content.setSizeUndefined();
	    
	    //text
	    Label text = new Label();
		text.setImmediate(false);
		//text.setWidth("-1px");
		//text.setHeight("-1px");
		text.setValue("Wollen Sie Ihr Angebot " + currentOffer.getTitle() + " wirklich unwiderruflich löschen? Alternativ können Sie es lediglich vorübergehend deaktivieren. Auf diese Weise können Sie das Angebot gegebenenfalls später wieder reaktivieren.");
		content.addComponent(text);
		
		HorizontalLayout buttons = new HorizontalLayout();
		
		//cancel-Button
		Button cancel = new Button();
		cancel.setIcon(FontAwesome.MAIL_REPLY);
		cancel.setStyleName("BearbeitenButton");
		cancel.setCaption("Abbrechen");
		cancel.setImmediate(true);
		cancel.setDescription("Angebot endgültig löschen");
		cancel.setWidth("-1px");
		cancel.setHeight("-1px");
		cancel.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				Notification not = new Notification("Das Angebot wurde nicht gelöscht.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
				not.setStyleName("failure");
				not.setDelayMsec(300);
				not.show(Page.getCurrent());
				ConfirmDeleteWindow.this.close();
				
			}
		});
		
		//deaktivieren-Button
		Button deactivate = new Button();
		deactivate.setIcon(FontAwesome.SQUARE_O);
		deactivate.setStyleName("BearbeitenButton");
		deactivate.setCaption("Deaktivieren");
		deactivate.setImmediate(true);
		deactivate.setDescription("Angebot vorübergehend deaktivieren");
		deactivate.setWidth("-1px");
		deactivate.setHeight("-1px");
		deactivate.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				currentOffer.setInactive(true);
				
				if(new OfferProvider().alterOffer(currentOffer)) {
					
					Notification not = new Notification("Das Angebot wurde deaktiviert.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
					not.setStyleName("success");
					not.setDelayMsec(300);
					not.show(Page.getCurrent());
					
					Offer o = new OfferProvider().findById(currentOffer.getIdOffer());
					
					String name = "Einzelansicht";
					getUI().getNavigator().addView(name, new Einzelansicht(o));
					getUI().getNavigator().navigateTo(name);
					
					ConfirmDeleteWindow.this.close();
					
				} else {
					
					Notification failDB = new Notification("Das Angebot konnte nicht deaktiviert werden.", Type.HUMANIZED_MESSAGE);
					failDB.setStyleName("failure");
					failDB.setDelayMsec(300);
					failDB.show(Page.getCurrent());
					
					ConfirmDeleteWindow.this.close();
					
				}
				
			}
		});
		
		//delete-Button
		Button delete = new Button();
		delete.setIcon(FontAwesome.TRASH_O);
		delete.setStyleName("loeschen");
		delete.setCaption("Löschen");
		delete.setImmediate(true);
		delete.setDescription("Angebot endgültig löschen");
		delete.setWidth("-1px");
		delete.setHeight("-1px");
		delete.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				if(new OfferProvider().removeOffer(currentOffer)) {
					
					Notification not = new Notification("Das Angebot wurde gelöscht und aus der Datenbank entfernt.",Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
					not.setStyleName("success");
					not.setDelayMsec(300);
					not.show(Page.getCurrent());
					
					String name = "Angebote verwalten";
					getUI().getNavigator().addView(name, new AngeboteVerwalten());
					getUI().getNavigator().navigateTo(name);
					
					ConfirmDeleteWindow.this.close();
					
				} else {
					
					Notification failDB = new Notification("Das Angebot konnte nicht gelöscht werden.", Type.HUMANIZED_MESSAGE);
					failDB.setStyleName("failure");
					failDB.setDelayMsec(300);
					failDB.show(Page.getCurrent());
					
					ConfirmDeleteWindow.this.close();
					
				}
				
			}
		});
		
		buttons.addComponent(delete);
		buttons.addComponent(deactivate);
		buttons.addComponent(cancel);
		content.addComponent(buttons);
		
	    this.setContent(content);
	    
	}

}
