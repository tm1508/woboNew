package com.example.housing;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.VerticalLayout;

public class AdminAnfrageUser extends HorizontalLayout implements View {

	VerticalLayout content;
	User user;
	private RichTextArea text;
	
	public AdminAnfrageUser(User u) {
		
		this.user = u;
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
