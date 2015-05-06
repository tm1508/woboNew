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
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Suche.
 */
public class AnfragenVerwalten extends HorizontalLayout implements View{

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
		
		this.setWidth("100%");
		
		//linkes rotes Panel
		Panel p = new Panel();
		p.setWidth("100%");
		p.setHeight("100%");
		p.addStyleName("red");
		addComponent(p);
		this.setExpandRatio(p, 1);
		
		//mittlerer Teil der Seite
		VerticalLayout v = new VerticalLayout();
				
			//Navigation hinzufuegen
			Navigation nav = new Navigation();
			nav.setWidth("100%");
			nav.addStyleName("navigation");
			v.addComponent(nav);
			
			NavigationPublic navPublic = new NavigationPublic();
			v.addComponent(navPublic);
			
			NavigationAdmin navAdmin = new NavigationAdmin();
			v.addComponent(navAdmin);
			
			//falls der Benutzer eingelogt ist verändert sich die Navigation
			if(VaadinSession.getCurrent().getAttribute("login").equals(true)){
				if(VaadinSession.getCurrent().getAttribute(User.class).getAccessLevel()==2){//falls der User ein Admin ist
					nav.setVisible(false);
					navPublic.setVisible(false);
					navAdmin.setVisible(true);//Admin-Navigation
				}else{//ansonsten: Naviagtion für eingeloggte Nutzer
					nav.setVisible(true);
					navPublic.setVisible(false);
					navAdmin.setVisible(false);
				}
			}else{//ansonsten Public Navigation (für alle)
				nav.setVisible(false);
				navPublic.setVisible(true);
				navAdmin.setVisible(false);
			}
			
			//Inhalt hinzufuegen
			content = new VerticalLayout();
			content.setMargin(true);
			content.setWidth("100%");
			setContent();//Methode zum befuellen des Inhalts aufrufen
			v.addComponent(content);
			
			//Footer hinzufuegen
			Footer f = new Footer();
			v.addComponent(f);
			
			//rotes Panel unter dem Footer
			Panel p2 = new Panel();
			p2.setWidth("100%");
			p2.addStyleName("red");
			p2.setHeight("30px");
			v.addComponent(p2);
	
		addComponent(v);
		this.setExpandRatio(v, 12);
		
		//rotes rechtes Panel
		Panel p1 = new Panel();
		p1.setWidth("100%");
		p1.addStyleName("red");
		p1.setHeight("100%");
		addComponent(p1);
		this.setExpandRatio(p1, 1);
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
