package com.example.housing;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Request;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.RequestProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Suche.
 */
public class AnfragenVerwalten extends CustomHorizontalLayout implements View{
	private static final long serialVersionUID = 1L;
	/** The content. */
	VerticalLayout content;
	
	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
	}

	public AnfragenVerwalten(){
		content = super.initCustomHorizontalLayout();
		setContent();
	}
	
	public void setContent(){
		
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Meine gesendeten Anfragen");
		title.addStyleName("title");
		content.addComponent(title);
		
		RequestProvider requestProv = new RequestProvider();
		List<Request> reqs;
		
		reqs = requestProv.findReq(VaadinSession.getCurrent().getAttribute(User.class));
		
		int anzahl = reqs.size();
		
		Label anfragenString = new Label("Sie haben zu folgenden " + anzahl + " Wohnungen bereits eine Anfrage gesendet.");
		anfragenString.addStyleName("AbschnittLabel");
		content.addComponent(anfragenString);
		
		for(int i = 0; i<anzahl; i++) {
			
			 Offer o = reqs.get(i).getRequest_idOffer();
			 content.addComponent(new Listenzeile(o));
			 
		}	
		
	}

}
