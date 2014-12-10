package com.example.housing;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class Startseite extends VerticalLayout implements View{

	VerticalLayout content;
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

		
	}
	
	public Startseite(){
		Navigation nav = new Navigation();
		addComponent(nav);
		
		setContent();
		addComponent(content);
		
		Footer f = new Footer();
		addComponent(f);
	}
	
	public void setContent(){
		
		content = new VerticalLayout();
		content.setMargin(true);
		Button button = new Button("Suche");
		button.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String s = "Suche";
				getUI().getNavigator().addView(s, new Suche());
				getUI().getNavigator().navigateTo(s);	
			}
		});
		content.addComponent(button);
		
		//TODO Inhalt einfügen
		
	}
	
	
}
