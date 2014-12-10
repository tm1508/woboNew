package com.example.housing;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("housing")
public class HousingUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = HousingUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
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
		layout.setMargin(true);
		setContent(layout);

		Navigation nav = new Navigation();
		layout.addComponent(nav);
		setSizeFull();
		
	
		
		h1.setSpacing(true);
		layout.addComponent(h1);
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

		h2.addComponent(new Label("Zimmer"));

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
		
		
		Footer f = new Footer();
		layout.addComponent(f);
	}
	


}
