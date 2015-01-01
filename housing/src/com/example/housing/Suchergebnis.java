package com.example.housing;

import java.io.File;
import java.sql.Date;

import javax.swing.GroupLayout.Alignment;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class Suchergebnis.
 */
public class Suchergebnis extends VerticalLayout implements View {
	
	/** The content. */
	VerticalLayout content;
	
	/**
	 * Instantiates a new suchergebnis.
	 */

	public Suchergebnis(){
	
	content = new VerticalLayout();


    content.setMargin(true);
	content.setSizeFull();
	content.setSpacing(true);
	
	Navigation nav = new Navigation();

	
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
	addComponent(nav);	
	addComponent(content);

	setContent();



	
	Footer f = new Footer();
	addComponent(f);
//	setComponentAlignment(f, com.vaadin.ui.Alignment.TOP_CENTER);
	//content.addComponent(f);
	}


	/**
	 * Sets the content.
	 */
	@SuppressWarnings("deprecation")
	public void setContent(){
		

			
		//titel
		String test = "Dies ist die Listenansicht";
		Label l = new Label(test);
		content.addComponent(l);
		


	}

	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}


