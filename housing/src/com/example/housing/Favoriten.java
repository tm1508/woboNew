package com.example.housing;

import com.example.housing.data.model.Favorit;
import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.FavoritProvider;
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
public class Favoriten extends CustomHorizontalLayout implements View{

	private static final long serialVersionUID = 1L;
	/** The content. */
	VerticalLayout content;
	
	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Instantiates a new favoriten.
	 */
	public Favoriten(){
		content = super.initCustomHorizontalLayout();
		setContent();		
	}
	
	/**
	 * Sets the content.
	 */
	public void setContent(){

		//Titel
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Meine gespeicherten Favoriten");
		title.addStyleName("title");
		content.addComponent(title);
				
		FavoritProvider fp = new FavoritProvider();
		List<Favorit> favs = fp.findFav(VaadinSession.getCurrent().getAttribute(User.class));
		int anzahl = favs.size();
		
		Label favoritenString = new Label("Sie haben " + anzahl + " Wohnungsangebote in Ihrer Favoriten-Liste gespeichert.");
		favoritenString.addStyleName("AbschnittLabel");
		content.addComponent(favoritenString);
		
		

		 for(int i = 0; i<anzahl;i++){

			 Offer o = favs.get(i).getFavorit_idOffer();
			content.addComponent(new Listenzeile(o));
		} 
		
		
	}


}
