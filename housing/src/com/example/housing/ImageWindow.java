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

// TODO: Auto-generated Javadoc
/**
 * The Class LoginWindow.
 * @author MWI Wohungsb�rse 2014
 * @version 1.0
 * @see com.example.housing.Registrierung
 */
@SuppressWarnings("serial")
public class ImageWindow extends Window{
	
	//Gro�ansicht der Bilder
	Image image00;
	Image image22;
	Image image33;
	Image image44;
	Image image55;
	
	//kleine Ansicht der Bilder
	Image image;
	Image image2;
	Image image3;
	Image image4;
	Image image5;
	
	//welches Bild wird gerade gro� angezeigt?
	int active=1;
	
	/**
	 * Instantiates a new login window.
	 */
	public ImageWindow(Offer angebot) {
		super("Bilder zu diesem Wohnungsangebot");
		initialisieren(angebot);
	}
	
	/**
	 * Initialisieren.
	 * @see com.vaadin.ui.Window
	 */
	public void initialisieren(final Offer angebot){
		this.center();
		this.setStyleName("image");
	    
	    final VerticalLayout content = new VerticalLayout();
	    content.setMargin(true);
	    content.setStyleName("box");
	    
	    String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	    
	    final VerticalLayout hl = new VerticalLayout();
		
	
		Resource resource00 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		Resource resource22 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		Resource resource33 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		Resource resource44 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		Resource resource55 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		
		switch(angebot.getPhotos().size()) {
			case 5:
				
				resource55 = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(4).getPhoto());
					}
				}, "Bild_5");
				
			case 4:
				
				resource44 = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(3).getPhoto());
					}
				}, "Bild_4");
				
			case 3:
				
				resource33 = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(2).getPhoto());
					}
				}, "Bild_3");
				
			case 2:
				
				resource22 = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(1).getPhoto());
					}
				}, "Bild_2");
				
			case 1:
				
				resource00 = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(0).getPhoto());
					}
				}, "Bild_1");
				
		}
		
		
		
		
		image00 = new Image("",resource00);
		image00.setWidth("700px");
		image00.setHeight("438px");
		image00.setCaption(null);
		
		
		
		image22 = new Image("",resource22);
		image22.setWidth("700px");
		image22.setHeight("438px");
		image22.setCaption(null);
		
		
		image33 = new Image("",resource33);
		image33.setWidth("700px");
		image33.setHeight("438px");
		image33.setCaption(null);
		
		image44 = new Image("",resource44);
		image44.setWidth("700px");
		image44.setHeight("438px");
		image44.setCaption(null);
		
		image55 = new Image("",resource55);
		image55.setWidth("700px");
		image55.setHeight("438px");
		image55.setCaption(null);
    
		hl.addComponent(image00);
		hl.addComponent(image22);
		hl.addComponent(image33);
		hl.addComponent(image44);
		hl.addComponent(image55);
		
		image22.setVisible(false);
		image33.setVisible(false);
		image44.setVisible(false);
		image55.setVisible(false);
		
		
		hl.setComponentAlignment(image00, Alignment.MIDDLE_CENTER);
		hl.setComponentAlignment(image22, Alignment.MIDDLE_CENTER);
		hl.setComponentAlignment(image33, Alignment.MIDDLE_CENTER);
		hl.setComponentAlignment(image44, Alignment.MIDDLE_CENTER);
		hl.setComponentAlignment(image55, Alignment.MIDDLE_CENTER);
		
		/*hl.addComponent(image2);
		hl.addComponent(image3);
		hl.addComponent(image4);
		hl.addComponent(image5);*/
		
		hl.setStyleName("picture");
	
		content.addComponent(hl);
		
	    final HorizontalLayout hl1 = new HorizontalLayout();
	    
	    
		Resource resource = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		Resource resource2 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		Resource resource3 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		Resource resource4 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		Resource resource5 = new FileResource(new File(basepath + "/WEB-INF/image/DefaultBild.jpg"));
		
		switch(angebot.getPhotos().size()) {
			case 5:
				
				resource5 = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(4).getPhoto());
					}
				}, "Bild_5");
				
			case 4:
				
				resource4 = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(3).getPhoto());
					}
				}, "Bild_4");
				
			case 3:
				
				resource3 = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(2).getPhoto());
					}
				}, "Bild_3");
				
			case 2:
				
				resource2 = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(1).getPhoto());
					}
				}, "Bild_2");
				
			case 1:
				
				resource = new StreamResource(new StreamResource.StreamSource() {
					@Override
					public InputStream getStream(){
						return new ByteArrayInputStream(angebot.getPhotos().get(0).getPhoto());
					}
				}, "Bild_1");
				
		}

			
			image = new Image("",resource);
			image.setWidth("50px");
			image.setHeight("50px");
			image.setStyleName("active");
			//image.setIcon(FontAwesome.PLUS);
			image.addClickListener(new ClickListener() {
			@Override
			public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
				image00.setVisible(true);
				image00.addStyleName("animate");
				
				image22.setVisible(false);
				image33.setVisible(false);
				image44.setVisible(false);
				image55.setVisible(false);
				
				active=1;
				image.setStyleName("active");
				image2.setStyleName("im");
				image3.setStyleName("im");
				image4.setStyleName("im");
				image5.setStyleName("im");
				
			}
			});
			
			image2 = new Image("",resource2);
			image2.setWidth("50px");
			image2.setHeight("50px");
			image2.setStyleName("im");
			image2.addClickListener(new ClickListener() {
			@Override
			public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
				image00.setVisible(false);

				image22.setVisible(true);
				image22.addStyleName("animate");
				
				image33.setVisible(false);
				image44.setVisible(false);
				image55.setVisible(false);
				
				active=2;
				
				image.setStyleName("im");
				image2.setStyleName("active");
				image3.setStyleName("im");
				image4.setStyleName("im");
				image5.setStyleName("im");
			}
			});
			
			image3 = new Image("",resource3);
			image3.setWidth("50px");
			image3.setHeight("50px");
			image3.setStyleName("im");
			image3.addClickListener(new ClickListener() {
			@Override
			public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
				image00.setVisible(false);
				image22.setVisible(false);

				image33.setVisible(true);
				image33.addStyleName("animate");
				
				image44.setVisible(false);
				image55.setVisible(false);
				
				active=3;
				
				image.setStyleName("im");
				image2.setStyleName("im");
				image3.setStyleName("active");
				image4.setStyleName("im");
				image5.setStyleName("im");
			}
			});
			
			image4 = new Image("",resource4);
			image4.setWidth("50px");
			image4.setHeight("50px");
			image4.setStyleName("im");
			image4.addClickListener(new ClickListener() {
			@Override
			public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
				image00.setVisible(false);
				image22.setVisible(false);
				image33.setVisible(false);
				
				image44.setVisible(true);
				image44.addStyleName("animate");
				
				image55.setVisible(false);
				active=4;
				
				image.setStyleName("im");
				image2.setStyleName("im");
				image3.setStyleName("im");
				image4.setStyleName("active");
				image5.setStyleName("im");
			}
			});
			
			image5 = new Image("",resource5);
			image5.setWidth("50px");
			image5.setHeight("50px");
			image5.setStyleName("im");
			image5.addClickListener(new ClickListener() {
			@Override
			public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
				image00.setVisible(false);
				image22.setVisible(false);
				image33.setVisible(false);
				image44.setVisible(false);
			
				image55.setVisible(true);
				image55.addStyleName("animate");
				active=5;
				
				image.setStyleName("im");
				image2.setStyleName("im");
				image3.setStyleName("im");
				image4.setStyleName("im");
				image5.setStyleName("active");
			}
			});
	    
			hl1.addComponent(image);
			hl1.addComponent(image2);
			hl1.addComponent(image3);
			hl1.addComponent(image4);
			hl1.addComponent(image5);
			
			
			Panel p =new Panel();
			p.setWidth("285px");
			p.setCaption(null);
			hl1.addComponent(p);
			p.setStyleName("im");
			
			
		    //Plus-Button
	        Button plus = new Button("");
	        plus.setIcon(FontAwesome.CHEVRON_LEFT);
	        plus.addStyleName("im");
	        plus.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					
					switch(active){
						case 1:{
							//nichts machen!!!
							break;
						}
					
						case 2:{
							active=1;
							image00.setVisible(true);
							image00.addStyleName("animate");
							
							image22.setVisible(false);
							image33.setVisible(false);
							image44.setVisible(false);
							image55.setVisible(false);
							
							
							image.setStyleName("active");
							image2.setStyleName("im");
							image3.setStyleName("im");
							image4.setStyleName("im");
							image5.setStyleName("im");
							break;
						}
					
						case 3:{
							active=2;
							image00.setVisible(false);

							image22.setVisible(true);
							image22.addStyleName("animate");
							
							image33.setVisible(false);
							image44.setVisible(false);
							image55.setVisible(false);
							
							
							image.setStyleName("im");
							image2.setStyleName("active");
							image3.setStyleName("im");
							image4.setStyleName("im");
							image5.setStyleName("im");
							break;
						
						}
						
						case 4:{
							image00.setVisible(false);
							image22.setVisible(false);

							image33.setVisible(true);
							image33.addStyleName("animate");
							
							image44.setVisible(false);
							image55.setVisible(false);
							
							active=3;
							
							image.setStyleName("im");
							image2.setStyleName("im");
							image3.setStyleName("active");
							image4.setStyleName("im");
							image5.setStyleName("im");
							break;
						}
						
						case 5:{
							image00.setVisible(false);
							image22.setVisible(false);
							image33.setVisible(false);
							
							image44.setVisible(true);
							image44.addStyleName("animate");
							
							image55.setVisible(false);
							active=4;
							
							image.setStyleName("im");
							image2.setStyleName("im");
							image3.setStyleName("im");
							image4.setStyleName("active");
							image5.setStyleName("im");
							break;
						}
					}
				}
	        });
	        hl1.addComponent(plus);
	        hl1.setComponentAlignment(plus, Alignment.BOTTOM_RIGHT);
			
		    //Plus-Button
	        Button plus1 = new Button("");
	        plus1.setIcon(FontAwesome.CHEVRON_RIGHT);
	        plus1.addStyleName("im");
	        plus1.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					switch(active){
					case 1:{
						active=2;
						image00.setVisible(false);

						image22.setVisible(true);
						image22.addStyleName("animate");
						
						image33.setVisible(false);
						image44.setVisible(false);
						image55.setVisible(false);
						
						
						image.setStyleName("im");
						image2.setStyleName("active");
						image3.setStyleName("im");
						image4.setStyleName("im");
						image5.setStyleName("im");
						break;
					}
				
					case 2:{
						image00.setVisible(false);
						image22.setVisible(false);

						image33.setVisible(true);
						image33.addStyleName("animate");
						
						image44.setVisible(false);
						image55.setVisible(false);
						
						active=3;
						
						image.setStyleName("im");
						image2.setStyleName("im");
						image3.setStyleName("active");
						image4.setStyleName("im");
						image5.setStyleName("im");
						break;
					}
				
					case 3:{
						image00.setVisible(false);
						image22.setVisible(false);
						image33.setVisible(false);
						
						image44.setVisible(true);
						image44.addStyleName("animate");
						
						image55.setVisible(false);
						active=4;
						
						image.setStyleName("im");
						image2.setStyleName("im");
						image3.setStyleName("im");
						image4.setStyleName("active");
						image5.setStyleName("im");
						break;
					
					}
					
					case 4:{
						image00.setVisible(false);
						image22.setVisible(false);
						image33.setVisible(false);
						image44.setVisible(false);
					
						image55.setVisible(true);
						image55.addStyleName("animate");
						active=5;
						
						image.setStyleName("im");
						image2.setStyleName("im");
						image3.setStyleName("im");
						image4.setStyleName("im");
						image5.setStyleName("active");
						break;
					}
					
					case 5:{
						break;
					}
				}
				}
	        });
	        
	        hl1.addComponent(plus1);
	        hl1.setComponentAlignment(plus1, Alignment.BOTTOM_RIGHT);
			
			
			hl1.setStyleName("picture");
			content.addComponent(hl1);
			
			
		
			
		this.setContent(content); 
		
		content.setExpandRatio(hl1, 1);
		content.setExpandRatio(hl, 4);
						
	}	
}
