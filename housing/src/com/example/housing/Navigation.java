package com.example.housing;

import java.io.File;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class Navigation.
 */
public class Navigation extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	/** The main layout. */
	@AutoGenerated
	private VerticalLayout mainLayout;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	MenuBar menuBar_1;
	
	/**
	 * Instantiates a new navigation.
	 */
	public Navigation() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		
		//Bild
		
		HorizontalLayout h = new HorizontalLayout();
		
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/image/dh.jpg"));
		Image image = new Image("",resource);
		h.addComponent(image);
		
		mainLayout.addComponent(h);
	
		
		
		//Navigation
		menuBar_1 = new MenuBar();
		menuBar_1.setImmediate(false);
		menuBar_1.setHeight("43px");
	
		
		MenuBar.Command mycommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				
				//TODO 
				VaadinSession.getCurrent().setAttribute("login", false);
				Notification.show("Logout erfolgreich.", Type.HUMANIZED_MESSAGE);
				Page.getCurrent().reload();
			}  
		};
		
		MenuBar.Command mycommand1 = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "Startseite";
				getUI().getNavigator().addView(name, new Startseite());
				getUI().getNavigator().navigateTo(name);
				
			}  
		};
		
		
		//Navigator navigator = new Navigator(null, h);
		MenuBar.Command mycommand2 = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "Suche";
				getUI().getNavigator().addView(name, new Suche());
				getUI().getNavigator().navigateTo(name);
				
			}  
		};
		
		MenuBar.Command mycommand3 = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "AngebotErstellen";
				getUI().getNavigator().addView(name, new AngebotErstellen());
				getUI().getNavigator().navigateTo(name);
				
			}  
		};
		
		
		//Navigator navigator = new Navigator(null, h);
		MenuBar.Command mycommand4 = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "AngebotAnzeigen";
				getUI().getNavigator().addView(name, new AngebotAnzeigen());
				getUI().getNavigator().navigateTo(name);
				
			}  
		};
		
		MenuBar.Command mycommand5 = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "Profile";
				getUI().getNavigator().addView(name, new Profile());
				getUI().getNavigator().navigateTo(name);
				
			}  
		};
		
		
		
				
			
				
		
					
		//Navigationsschaltflächen
		MenuItem nav0 = menuBar_1.addItem("Startseite", FontAwesome.HOME, mycommand1); //Startesite
		MenuItem nav1 = menuBar_1.addItem("Suche", FontAwesome.SEARCH, mycommand2); //Suche
		MenuItem nav2 = menuBar_1.addItem("Wohnung einstellen", FontAwesome.HOME, null); 
			nav2.addItem("Neue Wohnung anbieten", FontAwesome.PENCIL, mycommand3); //AngebotErstellen
			nav2.addItem("Wohnungsangebote verwalten", FontAwesome.WRENCH, mycommand4); //AngebotAnzeigen
		MenuItem nav5 = menuBar_1.addItem("Persönliche Daten", FontAwesome.CHILD, null); 
			nav5.addItem("Meine Favoriten", FontAwesome.STAR_O, null);
			nav5.addItem("Meine Anfragen", FontAwesome.STAR_O, null);
			nav5.addItem("Meine Profildaten", FontAwesome.USER, mycommand5); 
		MenuItem nav6 = menuBar_1.addItem("Logout", FontAwesome.UNLOCK_ALT, mycommand);//Navigation
		
		mainLayout.addComponent(menuBar_1);
		
		

					
		
	}

	/**
	 * Builds the main layout.
	 *
	 * @return the vertical layout
	 */
	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);

		mainLayout.setMargin(true);
		
	
		
		return mainLayout;
	}

}
