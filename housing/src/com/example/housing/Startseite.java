package com.example.housing;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class Startseite extends VerticalLayout implements View{

	VerticalLayout content;
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

		
	}
	
	public Startseite(){
		Navigation nav = new Navigation();
		addComponent(nav);
		setSizeFull();
		
		setContent();
		addComponent(content);
		
		Footer f = new Footer();
		addComponent(f);
	}
	
	public void setContent(){
		
		content = new VerticalLayout();
		content.setMargin(true);
		content.addComponent(new Button("test"));
		
		//TODO Inhalt einfügen
		
	}
	
	
}
