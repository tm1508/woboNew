package com.example.housing;

import com.example.housing.data.model.User;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public abstract class CustomHorizontalLayout extends HorizontalLayout{
	private static final long serialVersionUID = 1L;

	
	public VerticalLayout initCustomHorizontalLayout() {
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
			if(VaadinSession.getCurrent().getSession().getAttribute("login").equals(true)){
				if(((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getAccessLevel()==2){//falls der User ein Admin ist
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
			VerticalLayout content = new VerticalLayout();
			content.setMargin(true);
			content.setWidth("100%");
			//setContent();//Methode zum befuellen des Inhalts aufrufen
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
		return content;
	}
	
	public abstract void setContent();

}
