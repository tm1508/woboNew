package com.example.housing;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class LayoutPage extends HorizontalLayout implements View{
	
	/** The content. */
	private VerticalLayout content;//Layout fuer den Inhalt
	
	/** The title. */
	private Label title;
	
	/** The text. */
	private Label text;
	
	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		
	}
	
	/**
	 * Instantiates a new ErrorPage.
	 */
	public LayoutPage(){
		this.setWidth("100%");
		
		Panel p = new Panel();
		p.setWidth("100%");
		p.addStyleName("red");
		p.setHeight("100%");
		addComponent(p);
		this.setExpandRatio(p, 1);
		
		VerticalLayout v = new VerticalLayout();
			
		
		//Navigation hinzufuegen
		Navigation nav = new Navigation();
		nav.setWidth("100%");
		nav.addStyleName("navigation");
		v.addComponent(nav);
		
		NavigationPublic navPublic = new NavigationPublic();
		v.addComponent(navPublic);
		
		//falls der Benutzer eingelogt ist verändert sich die Navigation
		if(VaadinSession.getCurrent().getAttribute("login").equals(true)){
			nav.setVisible(true);
			navPublic.setVisible(false);
		}else{
			nav.setVisible(false);
			navPublic.setVisible(true);
		}
		
		//Inhalt hinzufuegen
		content = new VerticalLayout();
		content.setMargin(true);
		content.setWidth("100%");
		setContent();//Methode zum befuellen des Inhalts aufrufen
		v.addComponent(content);
		//v.setExpandRatio(content, 20);
		
		//Footer hinzufuegen
		Footer f = new Footer();
		v.addComponent(f);
		
		Panel p2 = new Panel();
		p2.setWidth("100%");
		p2.addStyleName("red");
		p2.setHeight("30px");
		
		v.addComponent(p2);
	
		addComponent(v);
		this.setExpandRatio(v, 12);
		
		Panel p1 = new Panel();
		p1.setWidth("100%");
		p1.addStyleName("red");
		p1.setHeight("100%");
		addComponent(p1);
		this.setExpandRatio(p1, 1);
		

	}
	
	/**
	 * Sets the Content of the page.
	 */
	public void setContent(){
				
		// title
		title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Seite zum Testen für das Layout.");
		title.addStyleName("title");
		content.addComponent(title);
				
		
		
		// text
		text = new Label();
		text.setImmediate(false);
		text.setWidth("-1px");
		text.setHeight("500px");
		text.setValue("Inhalt der Seite....<br/>");
		
		text.setContentMode(ContentMode.HTML);
		content.addComponent(text);
	}
	
	
}
