package com.example.housing;

import java.util.ArrayList;

import com.example.housing.data.model.User;
import com.example.housing.data.provider.UserProvider;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class UserSucheNachId extends CustomHorizontalLayout implements View {
	private static final long serialVersionUID = 1L;
	private VerticalLayout content;
	
	public UserSucheNachId() {
		content = super.initCustomHorizontalLayout();
		setContent();
	}

	@Override
	public void setContent() {
		// Titel
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Suche nach User-ID");
		title.addStyleName("title");
		content.addComponent(title);
		
		GridLayout tabelle = new GridLayout(2, 2);
		tabelle.setSpacing(true);
		
		// ID
		tabelle.addComponent(new Label("User-ID:  "), 0, 0);
		final TextField id = new TextField();
		id.setRequired(true);
		id.setRequiredError("Bitte geben Sie eine ID ein.");
		tabelle.addComponent(id, 1, 0);
		
		Button suchButton = new Button("Suchen");
		suchButton.setIcon(FontAwesome.SEARCH);
		suchButton.addStyleName("SuchButton");
		tabelle.addComponent(suchButton, 0, 1);
		suchButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				boolean valid = true; // fals ein Mussfeld nicht gefüllt ist
				Integer searchedId;
				// wird valid = false gesetzt
				try {
					id.validate();
				} catch (InvalidValueException e) {
					valid = false;
				}
				
				if(valid) {
					
					try {
						
						searchedId = Integer.parseInt(id.getValue());
						
					} catch(Exception e) {
						
						Notification not = new Notification("Bitte geben Sie eine gültige User-ID ein.");
						not.setDelayMsec(300);
						not.setStyleName("failure");
						not.show(Page.getCurrent());
						return;
					}
					
				} else {
					
					Notification not = new Notification("Bitte geben Sie eine User-ID ein, um einen User zu suchen.");
					not.setDelayMsec(300);
					not.setStyleName("failure");
					not.show(Page.getCurrent());
					return;
					
				}
				
				User u = new UserProvider().findById(searchedId);
				if(u == null) {
					
					String name = "UserListe";
					getUI().getNavigator().addView(name, new UserListe(new ArrayList<User>()));
					getUI().getNavigator().navigateTo(name);
					
				} else {
					
					String name = "UserProfil";
					getUI().getNavigator().addView(name, new UserProfil(u));
					getUI().getNavigator().navigateTo(name);
					
				}
				
			}
		});
		
		content.addComponent(tabelle);
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
	}

}