package com.example.housing;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class Suche extends VerticalLayout implements View{

	VerticalLayout content;
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public Suche(){
		Navigation nav = new Navigation();
		addComponent(nav);
		
		setContent();
		addComponent(content);
		
		Footer f = new Footer();
		addComponent(f);
	}
	
	public void setContent(){
		
		content = new VerticalLayout();
		HorizontalLayout h1 = new HorizontalLayout();
		VerticalLayout v1 = new VerticalLayout();
		VerticalLayout v2 = new VerticalLayout();
		VerticalLayout v3 = new VerticalLayout();
		VerticalLayout v4 = new VerticalLayout();
		VerticalLayout v5 = new VerticalLayout();
		HorizontalLayout h2 = new HorizontalLayout();
		HorizontalLayout h3 = new HorizontalLayout();
		HorizontalLayout h4 = new HorizontalLayout();
		HorizontalLayout h5 = new HorizontalLayout();
		content.setMargin(true);
		
		h1.setSpacing(true);
		content.addComponent(h1);
		h1.addComponent(v1);
		v1.addComponent(new Label("Quadratmeter"));
		v1.addComponent(new Label("von"));
		v1.addComponent(new TextField());
		v1.addComponent(new Label("bis"));
		v1.addComponent(new TextField());
		v1.addComponent(new Button("Suchen"));
		h1.addComponent(v2);
		v2.addComponent(new Label("Preis"));
		v2.addComponent(new Label("von"));
		v2.addComponent(new TextField());
		v2.addComponent(new Label("bis"));
		v2.addComponent(new TextField());
		h1.addComponent(v3);
		v3.addComponent(new Label("Zeitraum"));
		v3.addComponent(new Label("von"));
		v3.addComponent(new PopupDateField());
		v3.addComponent(new Label("bis"));
		v3.addComponent(new PopupDateField());
			v4.setSpacing(true);
			v5.setSpacing(true);
			h2.setSpacing(true);
			h3.setSpacing(true);
			h4.setSpacing(true);
			h5.setSpacing(true);
		h1.addComponent(v4);
		v4.addComponent(new Label("Art der Unterkunft"));
	
		
		
		h2.setSpacing(true);
		v4.addComponent(h2);
		h2.addComponent(new Label("WG-Zimmer"));
		h2.addComponent(new CheckBox());
		v4.addComponent(h3);
		h3.addComponent(new Label("Wohnung"));
		h3.addComponent(new CheckBox());
		h1.addComponent(v5);
		v5.addComponent(new Label("Sonstiges"));
		v5.addComponent(h4);
		h4.addComponent(new Label("Haustiere erlaubt"));
		h4.addComponent(new CheckBox());
		v5.addComponent(h5);
		h5.addComponent(new Label("Rauchen erlaubt"));
		h5.addComponent(new CheckBox());
		
		//TODO Inhalt einfügen
		
	}

}
