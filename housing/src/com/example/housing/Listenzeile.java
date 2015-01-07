package com.example.housing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.Photo;
import com.example.housing.utility.Format;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Buffered;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class Listenzeile extends CustomComponent {

	@AutoGenerated
	private VerticalLayout mainLayout;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public Listenzeile(final Offer o) {
		buildMainLayout();
		setCompositionRoot(mainLayout);

	
		GridLayout ergebnisLayout = new GridLayout(6, 3);
		ergebnisLayout.setMargin(false);
		ergebnisLayout.addStyleName("Listenzeile");
		// ergebnisLayout.setSizeFull();

		// pictures
		List<Photo> pictures;
		pictures = o.getPhotos();
		ImageInputStream iis = null;
		// falls Bilder zu der Wohnung vorhanden sind
		if (pictures.size() > 0) {
			Photo ph = pictures.get(0);
			byte[] by = ph.getPhoto();
			ByteArrayInputStream bis = new ByteArrayInputStream(by);
			Object source = bis;
			try {
				iis = ImageIO.createImageInputStream(source);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Buffered bufferedImage = null;
			try {
				bufferedImage = (Buffered) ImageIO.read(iis);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Image ii = (Image) bufferedImage;
			ergebnisLayout.addComponent(ii, 0, 0, 2, 2);
		} else {
			// TODO Kein bild vorhanden Bild
			String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
			FileResource resource = new FileResource(new File(basepath + "/WEB-INF/image/dh.jpg"));
			Image image = new Image("", resource);
			ergebnisLayout.addComponent(image, 0, 0, 2, 2);
			// String im = "Kein Bild vorhanden";
			// ergebnisLayout.addComponent(new Label(im),0,0,2,2);
		}

		String title = o.getTitle();
		Label l = new Label(title + " in " + o.getCity());
		l.setWidth("250px");
		ergebnisLayout.addComponent(l, 4, 0);
		float price = o.getPrice();
		Label l2 = new Label("Warmmiete: " + new Format().stringFormat(price) + " �");
		ergebnisLayout.addComponent(l2, 3, 1);

		float sm = o.getSquareMetre();
		String sSm = "Gr��e: " + new Format().stringFormat(sm) + " m�";
		Label lsm = new Label(sSm);
		lsm.setWidth("110px");
		ergebnisLayout.addComponent(lsm, 4, 1);

		int a = o.getType();
		String s;
		if (a == 3) {
			s = "Art: WG";
		} else if (a == 1) {
			s = "Art: Wohnung";
		} else if (a == 2) {
			s = "Art: Zimmer";
		} else {
			s = "";
		}
		Label ls = new Label(s);
		ls.setWidth("250px");
		ergebnisLayout.addComponent(ls, 5, 1);

		// Date start= new Date(2015,01,30);
		Date start = (Date) o.getStartDate();
		ergebnisLayout.addComponent(new Label("Startdatum: " + start.toString()), 3, 2);
		// Date end= new Date(2015,04,30);

		try {
			Date end = (Date) o.getEndDate();
			ergebnisLayout.addComponent(new Label("Enddatum: " + end.toString()), 5, 2);
		} catch (Exception e) {//tut nichts, da Enddatum keine Muss-Angabe ist
		}

		ergebnisLayout.addLayoutClickListener(new LayoutClickListener() {
			public void layoutClick(LayoutClickEvent event) {
				String name = "Einzelansicht";
				getUI().getNavigator().addView(name, new Einzelansicht(o));
				getUI().getNavigator().navigateTo(name);
			}
		});

		mainLayout.addComponent(ergebnisLayout);

	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// the main layout and components will be created here
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);

		mainLayout.setMargin(false);

		return mainLayout;

	}

}
