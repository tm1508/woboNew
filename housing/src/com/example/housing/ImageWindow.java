package com.example.housing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import com.example.housing.data.model.Offer;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class ImageWindow extends Window {
	private static final long serialVersionUID = 1L;
	
	// Großansicht der Bilder
	Image imageBig1;
	Image imageBig2;
	Image imageBig3;
	Image imageBig4;
	Image imageBig5;

	// kleine Ansicht der Bilder
	Image imageSmall1;
	Image imageSmall2;
	Image imageSmall3;
	Image imageSmall4;
	Image imageSmall5;

	// welches Bild wird gerade groß angezeigt?
	int activePicture = 1;

	public ImageWindow(Offer angebot) {
		super(" Bilder zu diesem Wohnungsangebot");
		initialisieren(angebot);
	}

	public void initialisieren(final Offer angebot) {
		// Window
		this.center();
		this.setIcon(FontAwesome.IMAGE);
		this.setResizable(false);
		this.setStyleName("image");

		// Rahmen für die Fotos erzeugen
		final VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		content.setStyleName("box");

		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();// Basepath

		final VerticalLayout layoutBigPicture = new VerticalLayout();// Layout für das große Bild

		// für jedes Bild den Pfad zum DefaultBild
		Resource resourceBig1 = new FileResource(new File(basepath+ "/WEB-INF/image/DefaultBild.jpg"));
		Resource resourceBig2 = new FileResource(new File(basepath+ "/WEB-INF/image/DefaultBild.jpg"));
		Resource resourceBig3 = new FileResource(new File(basepath+ "/WEB-INF/image/DefaultBild.jpg"));
		Resource resourceBig4 = new FileResource(new File(basepath+ "/WEB-INF/image/DefaultBild.jpg"));
		Resource resourceBig5 = new FileResource(new File(basepath+ "/WEB-INF/image/DefaultBild.jpg"));

		// Bilddateien aus dem Angebots-Objekt auslesen
		switch (angebot.getPhotos().size()) {
		case 5:
			resourceBig5 = new StreamResource(new StreamResource.StreamSource() {
				private static final long serialVersionUID = 1L;
				@Override
				public InputStream getStream() {
					return new ByteArrayInputStream(angebot.getPhotos().get(4).getPicture());
				}
			}, "Bild_5");

		case 4:
			resourceBig4 = new StreamResource(new StreamResource.StreamSource() {
				private static final long serialVersionUID = 1L;
				@Override
				public InputStream getStream() {
					return new ByteArrayInputStream(angebot.getPhotos().get(3).getPicture());
				}
			}, "Bild_4");

		case 3:
			resourceBig3 = new StreamResource(new StreamResource.StreamSource() {
				private static final long serialVersionUID = 1L;
				@Override
				public InputStream getStream() {
					return new ByteArrayInputStream(angebot.getPhotos().get(2).getPicture());
				}
			}, "Bild_3");

		case 2:
			resourceBig2 = new StreamResource(new StreamResource.StreamSource() {
				private static final long serialVersionUID = 1L;
				@Override
				public InputStream getStream() {
					return new ByteArrayInputStream(angebot.getPhotos().get(1).getPicture());
				}
			}, "Bild_2");

		case 1:
			resourceBig1 = new StreamResource(new StreamResource.StreamSource() {
				private static final long serialVersionUID = 1L;
				@Override
				public InputStream getStream() {
					return new ByteArrayInputStream(angebot.getPhotos().get(0).getPicture());
				}
			}, "Bild_1");
		}

		// Bilder erzeugen
		imageBig1 = new Image("", resourceBig1);
		imageBig1.setWidth("700px");// Größe festlegen
		imageBig1.setHeight("438px");
		imageBig1.setCaption(null);// keine Überschrift

		imageBig2 = new Image("", resourceBig2);
		imageBig2.setWidth("700px");
		imageBig2.setHeight("438px");
		imageBig2.setCaption(null);

		imageBig3 = new Image("", resourceBig3);
		imageBig3.setWidth("700px");
		imageBig3.setHeight("438px");
		imageBig3.setCaption(null);

		imageBig4 = new Image("", resourceBig4);
		imageBig4.setWidth("700px");
		imageBig4.setHeight("438px");
		imageBig4.setCaption(null);

		imageBig5 = new Image("", resourceBig5);
		imageBig5.setWidth("700px");
		imageBig5.setHeight("438px");
		imageBig5.setCaption(null);

		// Bilder zum Layout hinzufügen
		layoutBigPicture.addComponent(imageBig1);
		layoutBigPicture.addComponent(imageBig2);
		layoutBigPicture.addComponent(imageBig3);
		layoutBigPicture.addComponent(imageBig4);
		layoutBigPicture.addComponent(imageBig5);

		// alle Bilder (bis auf das erste) unsichtbar machen
		imageBig2.setVisible(false);
		imageBig3.setVisible(false);
		imageBig4.setVisible(false);
		imageBig5.setVisible(false);

		// Bilder mittig platzieren
		layoutBigPicture.setComponentAlignment(imageBig1, Alignment.MIDDLE_CENTER);
		layoutBigPicture.setComponentAlignment(imageBig2, Alignment.MIDDLE_CENTER);
		layoutBigPicture.setComponentAlignment(imageBig3, Alignment.MIDDLE_CENTER);
		layoutBigPicture.setComponentAlignment(imageBig4, Alignment.MIDDLE_CENTER);
		layoutBigPicture.setComponentAlignment(imageBig5, Alignment.MIDDLE_CENTER);

		// Layout für das große Bild hinzufügen
		layoutBigPicture.setStyleName("picture");
		content.addComponent(layoutBigPicture);

		final HorizontalLayout layoutSmallPicture = new HorizontalLayout();// kleine Bilder

		// Pfad zum default-Bild
		Resource resourceSmall1 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		Resource resourceSmall2 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		Resource resourceSmall3 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		Resource resourceSmall4 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		Resource resourceSmall5 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));

		// Bilddaten aus dem Angebot-Objekt auslesen
		switch (angebot.getPhotos().size()) {
		case 5:
			resourceSmall5 = new StreamResource(new StreamResource.StreamSource() {
				private static final long serialVersionUID = 1L;
				@Override
				public InputStream getStream() {
					return new ByteArrayInputStream(angebot.getPhotos().get(4).getPicture());
				}
			}, "Bild_5");

		case 4:
			resourceSmall4 = new StreamResource(new StreamResource.StreamSource() {
				private static final long serialVersionUID = 1L;
				@Override
				public InputStream getStream() {
					return new ByteArrayInputStream(angebot.getPhotos().get(3).getPicture());
				}
			}, "Bild_4");

		case 3:
			resourceSmall3 = new StreamResource(new StreamResource.StreamSource() {
				private static final long serialVersionUID = 1L;
				@Override
				public InputStream getStream() {
					return new ByteArrayInputStream(angebot.getPhotos().get(2).getPicture());
				}
			}, "Bild_3");

		case 2:
			resourceSmall2 = new StreamResource(new StreamResource.StreamSource() {
				private static final long serialVersionUID = 1L;
				@Override
				public InputStream getStream() {
					return new ByteArrayInputStream(angebot.getPhotos().get(1).getPicture());
				}
			}, "Bild_2");

		case 1:
			resourceSmall1 = new StreamResource(new StreamResource.StreamSource() {
				private static final long serialVersionUID = 1L;
				@Override
				public InputStream getStream() {
					return new ByteArrayInputStream(angebot.getPhotos().get(0).getPicture());
				}
			}, "Bild_1");
		}

		//Bilder erzeugen
		imageSmall1 = new Image("", resourceSmall1);
		imageSmall1.setWidth("50px");//Größe festlegen
		imageSmall1.setHeight("50px");
		imageSmall1.setStyleName("active");//Style festlegen (das erste Bild ist zu Beginn ausgewählt =active)
		imageSmall1.addClickListener(new ClickListener() {//beim Click auf das Bild
			private static final long serialVersionUID = 1L;
			@Override
			public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
				imageBig1.setVisible(true);//Bild 1 sichtbar machen
				imageBig1.addStyleName("animate");
				imageBig2.setVisible(false);//alle anderen Bilder unsichtbar machen
				imageBig3.setVisible(false);
				imageBig4.setVisible(false);
				imageBig5.setVisible(false);
				activePicture = 1;//Bild 1 als aktiv Kennzeichnen
				imageSmall1.setStyleName("active");//Style festlegen (das erste Bild ist ausgewählt =active)
				imageSmall2.setStyleName("im");//alle anderen Bilder: "normaler Style
				imageSmall3.setStyleName("im");
				imageSmall4.setStyleName("im");
				imageSmall5.setStyleName("im");

			}
		});

		imageSmall2 = new Image("", resourceSmall2);//für alle Bilder wiederholen
		imageSmall2.setWidth("50px");
		imageSmall2.setHeight("50px");
		imageSmall2.setStyleName("im");
		imageSmall2.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
				imageBig1.setVisible(false);
				imageBig2.setVisible(true);
				imageBig2.addStyleName("animate");
				imageBig3.setVisible(false);
				imageBig4.setVisible(false);
				imageBig5.setVisible(false);
				activePicture = 2;
				imageSmall1.setStyleName("im");
				imageSmall2.setStyleName("active");
				imageSmall3.setStyleName("im");
				imageSmall4.setStyleName("im");
				imageSmall5.setStyleName("im");
			}
		});

		imageSmall3 = new Image("", resourceSmall3);
		imageSmall3.setWidth("50px");
		imageSmall3.setHeight("50px");
		imageSmall3.setStyleName("im");
		imageSmall3.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
				imageBig1.setVisible(false);
				imageBig2.setVisible(false);
				imageBig3.setVisible(true);
				imageBig3.addStyleName("animate");
				imageBig4.setVisible(false);
				imageBig5.setVisible(false);
				activePicture = 3;
				imageSmall1.setStyleName("im");
				imageSmall2.setStyleName("im");
				imageSmall3.setStyleName("active");
				imageSmall4.setStyleName("im");
				imageSmall5.setStyleName("im");
			}
		});

		imageSmall4 = new Image("", resourceSmall4);
		imageSmall4.setWidth("50px");
		imageSmall4.setHeight("50px");
		imageSmall4.setStyleName("im");
		imageSmall4.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
				imageBig1.setVisible(false);
				imageBig2.setVisible(false);
				imageBig3.setVisible(false);
				imageBig4.setVisible(true);
				imageBig4.addStyleName("animate");
				imageBig5.setVisible(false);
				activePicture = 4;
				imageSmall1.setStyleName("im");
				imageSmall2.setStyleName("im");
				imageSmall3.setStyleName("im");
				imageSmall4.setStyleName("active");
				imageSmall5.setStyleName("im");
			}
		});

		imageSmall5 = new Image("", resourceSmall5);
		imageSmall5.setWidth("50px");
		imageSmall5.setHeight("50px");
		imageSmall5.setStyleName("im");
		imageSmall5.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
				imageBig1.setVisible(false);
				imageBig2.setVisible(false);
				imageBig3.setVisible(false);
				imageBig4.setVisible(false);
				imageBig5.setVisible(true);
				imageBig5.addStyleName("animate");
				activePicture = 5;
				imageSmall1.setStyleName("im");
				imageSmall2.setStyleName("im");
				imageSmall3.setStyleName("im");
				imageSmall4.setStyleName("im");
				imageSmall5.setStyleName("active");
			}
		});

		//Bilder hinzufügen
		layoutSmallPicture.addComponent(imageSmall1);
		layoutSmallPicture.addComponent(imageSmall2);
		layoutSmallPicture.addComponent(imageSmall3);
		layoutSmallPicture.addComponent(imageSmall4);
		layoutSmallPicture.addComponent(imageSmall5);

		//Panel als Platzhalter einfügen
		Panel p = new Panel();
		p.setWidth("285px");
		p.setCaption(null);
		layoutSmallPicture.addComponent(p);
		p.setStyleName("im");

		//Button zur Bildernavigation (zurück)
		Button left = new Button("");
		left.setDescription("vorheriges Bild");
		left.setIcon(FontAwesome.CHEVRON_LEFT);
		left.addStyleName("im");
		left.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				switch (activePicture) {//je nachdem welches Bild aktiv ist wird das vorherige aktiviert
					case 1: {
						// nichts machen!!!
						break;
					}
	
					case 2: {//gleiche Aktionen wie beim Click auf das Bild
						activePicture = 1;
						imageBig1.setVisible(true);
						imageBig1.addStyleName("animate");
						imageBig2.setVisible(false);
						imageBig3.setVisible(false);
						imageBig4.setVisible(false);
						imageBig5.setVisible(false);
						imageSmall1.setStyleName("active");
						imageSmall2.setStyleName("im");
						imageSmall3.setStyleName("im");
						imageSmall4.setStyleName("im");
						imageSmall5.setStyleName("im");
						break;
					}
	
					case 3: {
						activePicture = 2;
						imageBig1.setVisible(false);
						imageBig2.setVisible(true);
						imageBig2.addStyleName("animate");
						imageBig3.setVisible(false);
						imageBig4.setVisible(false);
						imageBig5.setVisible(false);
						imageSmall1.setStyleName("im");
						imageSmall2.setStyleName("active");
						imageSmall3.setStyleName("im");
						imageSmall4.setStyleName("im");
						imageSmall5.setStyleName("im");
						break;
					}
	
					case 4: {
						imageBig1.setVisible(false);
						imageBig2.setVisible(false);
						imageBig3.setVisible(true);
						imageBig3.addStyleName("animate");
						imageBig4.setVisible(false);
						imageBig5.setVisible(false);
						activePicture = 3;
						imageSmall1.setStyleName("im");
						imageSmall2.setStyleName("im");
						imageSmall3.setStyleName("active");
						imageSmall4.setStyleName("im");
						imageSmall5.setStyleName("im");
						break;
					}
	
					case 5: {
						imageBig1.setVisible(false);
						imageBig2.setVisible(false);
						imageBig3.setVisible(false);
						imageBig4.setVisible(true);
						imageBig4.addStyleName("animate");
						imageBig5.setVisible(false);
						activePicture = 4;
						imageSmall1.setStyleName("im");
						imageSmall2.setStyleName("im");
						imageSmall3.setStyleName("im");
						imageSmall4.setStyleName("active");
						imageSmall5.setStyleName("im");
						break;
					}
				}
			}
		});
		layoutSmallPicture.addComponent(left);//Button hinzufügen
		layoutSmallPicture.setComponentAlignment(left, Alignment.BOTTOM_RIGHT);//Button positionieren

		//Button zur Bildernavigation (vorwärts)
		Button right = new Button("");
		right.setDescription("nächstes Bild");
		right.setIcon(FontAwesome.CHEVRON_RIGHT);
		right.addStyleName("im");
		right.addClickListener(new Button.ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				switch (activePicture) {
					case 1: {//siehe oben
						activePicture = 2;
						imageBig1.setVisible(false);
						imageBig2.setVisible(true);
						imageBig2.addStyleName("animate");
						imageBig3.setVisible(false);
						imageBig4.setVisible(false);
						imageBig5.setVisible(false);
						imageSmall1.setStyleName("im");
						imageSmall2.setStyleName("active");
						imageSmall3.setStyleName("im");
						imageSmall4.setStyleName("im");
						imageSmall5.setStyleName("im");
						break;
					}
	
					case 2: {
						imageBig1.setVisible(false);
						imageBig2.setVisible(false);
						imageBig3.setVisible(true);
						imageBig3.addStyleName("animate");
						imageBig4.setVisible(false);
						imageBig5.setVisible(false);
						activePicture = 3;
						imageSmall1.setStyleName("im");
						imageSmall2.setStyleName("im");
						imageSmall3.setStyleName("active");
						imageSmall4.setStyleName("im");
						imageSmall5.setStyleName("im");
						break;
					}
	
					case 3: {
						imageBig1.setVisible(false);
						imageBig2.setVisible(false);
						imageBig3.setVisible(false);
						imageBig4.setVisible(true);
						imageBig4.addStyleName("animate");
						imageBig5.setVisible(false);
						activePicture = 4;
						imageSmall1.setStyleName("im");
						imageSmall2.setStyleName("im");
						imageSmall3.setStyleName("im");
						imageSmall4.setStyleName("active");
						imageSmall5.setStyleName("im");
						break;
					}
	
					case 4: {
						imageBig1.setVisible(false);
						imageBig2.setVisible(false);
						imageBig3.setVisible(false);
						imageBig4.setVisible(false);
						imageBig5.setVisible(true);
						imageBig5.addStyleName("animate");
						activePicture = 5;
						imageSmall1.setStyleName("im");
						imageSmall2.setStyleName("im");
						imageSmall3.setStyleName("im");
						imageSmall4.setStyleName("im");
						imageSmall5.setStyleName("active");
						break;
					}
	
					case 5: {
						//letztes Bild, es geht nicht weiter, tue nichts
						break;
					}
				}
			}
		});
		
		//Button hinzufügen
		layoutSmallPicture.addComponent(right);
		layoutSmallPicture.setComponentAlignment(right, Alignment.BOTTOM_RIGHT);//Button positionieren

		//Layout für kleine Bilder hinzufügen
		layoutSmallPicture.setStyleName("picture");
		content.addComponent(layoutSmallPicture);
		this.setContent(content);

		content.setExpandRatio(layoutSmallPicture, 1);//große Bilder bekommen mehr Platz
		content.setExpandRatio(layoutBigPicture, 4);
		
		imageSmall1.markAsDirty();
		imageSmall2.markAsDirty();
		imageSmall3.markAsDirty();
		imageSmall4.markAsDirty();
		imageSmall5.markAsDirty();
		
		imageBig1.markAsDirty();
		imageBig2.markAsDirty();
		imageBig3.markAsDirty();
		imageBig4.markAsDirty();
		imageBig5.markAsDirty();
	}
}
