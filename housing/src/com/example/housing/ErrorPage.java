package com.example.housing;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class Error Page.
 * 
 * @author MWI Wohungsbörse 2014
 * @version 1.0
 * @see com.example.housing.HousingUI
 */
@SuppressWarnings("serial")
public class ErrorPage extends VerticalLayout implements View{
	
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
	public ErrorPage(){
		setMargin(true);
		
		//Navigation hinzufuegen
		Navigation nav = new Navigation();
		nav.setWidth("100%");
		nav.addStyleName("navigation");
		addComponent(nav);
		
		NavigationPublic navPublic = new NavigationPublic();
		addComponent(navPublic);
		
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
		addComponent(content);
		
		//Footer hinzufuegen
		Footer f = new Footer();
		addComponent(f);
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
		title.setValue("Es ist ein Fehler aufgetreten.");
		title.addStyleName("title");
		content.addComponent(title);
				
		
		
		// text
		text = new Label();
		text.setImmediate(false);
		text.setWidth("-1px");
		text.setHeight("-1px");
		text.setValue("Tut uns leid, das hätte nicht passieren dürfen.<br/>"
				+"<br/><br/> Der Fehler kann folgende Ursachen haben:"
				+"<br/><br/> - Sie wollten zu einer nicht verfügbaren Seite navigieren. Beispielsweise können Sie die Profilseite nur aufrufen, wenn Sie eingeloggt sind."
				+"<br/><br/> - Das hochladen eines Bildes hat den Fehler verursacht.  Bitte versuchen Sie es später erneut."
				+"<br/><br/> - Es gab einen Serverfehler. Wenn der Fehler öfters auftritt wenden Sie sich bitte an den Administrator.");
		
		text.setContentMode(ContentMode.HTML);
		content.addComponent(text);
	}
	
	
}
