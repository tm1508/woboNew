package com.example.housing;

import java.util.List;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class UserListe extends CustomHorizontalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	VerticalLayout content;
	List<User> users;
	
	public UserListe(List<User> users) {
		
		this.users = users;
		
		content = super.initCustomHorizontalLayout();
		setContent();
		
	}

	@Override
	public void setContent() {
		
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Liste aller User");
		title.addStyleName("title");
		content.addComponent(title);
		
		if(users.isEmpty()) {
			
			Label ergebnisString = new Label("Ihre Suche ergab leider keine Treffer.");
			ergebnisString.addStyleName("AbschnittLabel");
			content.addComponent(ergebnisString);
			
		} else {
			
			//Anzahl der Treffer
			final Label ergebnisString = new Label("Ihre Suche ergab " + users.size()+" Treffer:");
			ergebnisString.addStyleName("AbschnittLabel");	
			content.addComponent(ergebnisString);
			
			content.addComponent(new Label());
			for(User u : users) {
				content.addComponent(new UserZeile(u));
			}
			
		}
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
