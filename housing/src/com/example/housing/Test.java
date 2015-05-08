package com.example.housing;


import java.util.ArrayList;
import java.util.List;

import com.example.housing.data.model.Favorit;
import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.FavoritProvider;
import com.example.housing.data.provider.UserProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class Test.
 */
public class Test extends VerticalLayout implements View{
	
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
	 * Instantiates a new test.
	 */
	public Test(){
		Navigation nav = new Navigation();
		addComponent(nav);
		
		setContent();
		addComponent(content);
		
		Footer f = new Footer();
		addComponent(f);
	}
	
	/**
	 * Sets the content.
	 */
	public void setContent(){
		
		content = new VerticalLayout();
		content.setMargin(true);
		/*Button button = new Button("Suche");
		button.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String s = "Suche";
				getUI().getNavigator().addView(s, new Suche());
				getUI().getNavigator().navigateTo(s);	
			}
		});
		content.addComponent(button);*/
		
		//TODO Inhalt einfügen
		
		//Datenbank-Test: Versuche User auszugeben aus Datenbank
		User user = new UserProvider().findById(Integer.parseInt("1"));
		List<Favorit> favs = user.getFavorits();/*new FavoritProvider().findByUser(user);*/
		Panel p = new Panel("DATENBANKABFRAGE:"
				+ "User: " + user.getEmail()
				+ "     Favorit(en): " + favs.get(0).getFavorit_idOffer().getTitle());
		VerticalLayout v = new VerticalLayout();
		v.setMargin(true);
		
		
        Table tabelle = new Table();
        tabelle.setSizeFull();
        tabelle.setSelectable(true);
        tabelle.setMultiSelect(true);
        tabelle.setImmediate(true);
        v.addComponent(tabelle);
		p.setContent(v);
		
		content.addComponent(p);

	}
}
