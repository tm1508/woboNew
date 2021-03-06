package com.example.housing;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Photo;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;

public class Fotozeile extends CustomComponent {
	private static final long serialVersionUID = 1L;
	private VerticalLayout mainLayout;

	public Fotozeile(final Photo photo, final Offer offer) {
		//Layout
		buildMainLayout();
		setCompositionRoot(mainLayout);
		HorizontalLayout ergebnisLayout = new HorizontalLayout();
		ergebnisLayout.setMargin(false);
		ergebnisLayout.setHeight("100px");

		//Bild
		StreamResource resource = new StreamResource(
				new StreamResource.StreamSource() {
					private static final long serialVersionUID = 1L;
					@Override
					public InputStream getStream() {
						InputStream bais = new ByteArrayInputStream(photo.getPicture());
						return bais;
					}
				}, "Bild_1");
		resource.setCacheTime(0);

		Image image = new Image(null, resource);
		image.setHeight("80px");
		image.setWidth("80px");
		image.markAsDirty();
		ergebnisLayout.addComponent(image);
		ergebnisLayout.addComponent(new Label());

		//Loeschen Button
		Button loeschenButton = new Button("Dieses Bild l�schen");
		ergebnisLayout.addComponent(loeschenButton);

		loeschenButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				Notification success = new Notification("Das Bild wurde gel�scht.", Type.HUMANIZED_MESSAGE);
				success.setStyleName("success");
				success.setDelayMsec(300);
				success.show(Page.getCurrent());
			}
		});
		
		mainLayout.addComponent(ergebnisLayout);
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setMargin(false);
		return mainLayout;
	}
}