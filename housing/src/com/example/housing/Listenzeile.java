package com.example.housing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class Listenzeile.
 */
public class Listenzeile extends CustomComponent {

	/** The main layout. */
	@AutoGenerated
	private VerticalLayout mainLayout;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 *
	 * @param o the o
	 */
	public Listenzeile(final Offer o) {
		buildMainLayout();
		setCompositionRoot(mainLayout);

	
		GridLayout ergebnisLayout = new GridLayout(6, 3);
		ergebnisLayout.setMargin(false);
		ergebnisLayout.addStyleName("Listenzeile");
		ergebnisLayout.setSizeFull();
		ergebnisLayout.setHeight("150px");

		// pictures
		final List<Photo> pictures;
		pictures = o.getPhotos();
		
		// falls Bilder zu der Wohnung vorhanden sind
		if (pictures.size() > 0) {
			
			StreamResource resource = new StreamResource(new StreamResource.StreamSource() {
				@Override
				public InputStream getStream(){
					InputStream bais = new ByteArrayInputStream(pictures.get(0).getPicture());
					return bais;
				}
												
			}, "Bild_1");
			resource.setCacheTime(0);
						
			Image image = new Image(null, resource);
			image.setHeight("100%"); //100% der Listenzeilen-H�he
			image.setWidth("30%");
			image.markAsDirty();
	
		
			ergebnisLayout.addComponent(image, 0, 0, 2, 2);
			/*Float dynamicWidthImageCalc = (float) (image.getHeight() * 1.45);
			System.out.println(Math.round(dynamicWidthImageCalc));
			Integer dynamicWidthImage = Integer.valueOf(Math.round(dynamicWidthImageCalc));
			System.out.println(dynamicWidthImage);
			image.setWidth(dynamicWidthImage.toString() + "px");
			System.out.println("Bild-Breite: " + dynamicWidthImage.toString()); // Test
			ergebnisLayout.addComponent(image, 0, 0, 2, 2);
			Float dynamicWidthRestCalc = ergebnisLayout.getWidth() - image.getWidth();
			Integer dynamicWidthRest = Integer.valueOf(Math.round(dynamicWidthRestCalc));
			Component c = ergebnisLayout.getComponent(0, 2);
			c.setWidth(dynamicWidthRest.toString() + "px");
			System.out.println("Rest-Breite: " + dynamicWidthRest.toString()); // Test
			*/			
			Component c = ergebnisLayout.getComponent(0, 2);
			c.setHeight("100%");
			c.setWidth("70%");
			
		} else {
			
			// TODO Standardbild
			String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
			FileResource resource = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
			Image image = new Image("", resource);
			//image.setWidth("100px");
			//image.setHeight("85px");
			ergebnisLayout.addComponent(image, 0, 0, 2, 2);
			Component c = ergebnisLayout.getComponent(0, 2);
			c.setWidth("70%");
			c.setHeight("100%");
			// String im = "Kein Bild vorhanden";
			// ergebnisLayout.addComponent(new Label(im),0,0,2,2);
			
		}

		String title = o.getTitle();
		Label l = new Label(title + " Ort " + o.getCity());
		l.addStyleName("ImportantTitle");
		l.setWidth("100%");
		l.setHeight("20%");
		ergebnisLayout.addComponent(l, 3, 0, 5, 0);

		Label l2 = new Label("Warmmiete: " + Format.euroFormat(o.getPrice()) + " �");
		l2.setWidth("110px");
		ergebnisLayout.addComponent(l2, 3, 1);

		float sm = o.getSquareMetre();
		String sSm = "Gr��e: " + Format.stringFormat(sm) + " m�";
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
		ls.setWidth("100px");
		ergebnisLayout.addComponent(ls, 5, 1);

	
		Date start = (Date) o.getStartDate();
		String dateS = Format.dateFormat(start);
		ergebnisLayout.addComponent(new Label("Startdatum: " +  dateS ), 3, 2);

		try {
			Date end = (Date) o.getEndDate();
			String dateSs = Format.dateFormat(end);
			ergebnisLayout.addComponent(new Label("Enddatum: " + dateSs), 5, 2);
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

	/**
	 * Builds the main layout.
	 *
	 * @return the vertical layout
	 */
	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// the main layout and components will be created here
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);

		mainLayout.setMargin(false);

		return mainLayout;

	}

}
