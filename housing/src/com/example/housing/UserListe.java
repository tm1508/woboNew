package com.example.housing;

import java.util.List;

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
	
	public void UserListe(List<User> users) {
		
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
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
