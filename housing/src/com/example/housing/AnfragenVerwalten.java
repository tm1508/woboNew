package com.example.housing;

import java.util.Date;

import com.example.housing.data.model.Favorit;
import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Request;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.FavoritProvider;
import com.example.housing.data.provider.OfferProvider;
import com.example.housing.data.provider.RequestProvider;
import com.example.housing.utility.Format;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Suche.
 */
public class AnfragenVerwalten extends VerticalLayout implements View{

	/** The content. */
	VerticalLayout content;
	
	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	public AnfragenVerwalten(){
		
		 content = new VerticalLayout();
		
    	content.setMargin(true);
		content.setSizeFull();
		content.setSpacing(true); 
		
		Navigation nav = new Navigation();
		addComponent(nav);
		
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
	
	public void setContent(){
		
	
		RequestProvider rp = new RequestProvider();
		List<Request> reqs;
		
		reqs = rp.findReq(VaadinSession.getCurrent().getAttribute(User.class));
		
		int anzahl = reqs.size();
		System.out.println(anzahl);
		 for(int i = 0; i<anzahl;i++){
			
			 Offer o = reqs.get(i).getRequest_idOffer();
			 content.addComponent(new Listenzeile(o));
		} 
			
		
	}


}
