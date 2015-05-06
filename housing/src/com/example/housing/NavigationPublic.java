package com.example.housing;

import java.io.File;
import java.util.List;

import com.example.housing.data.model.Offer;
import com.example.housing.data.provider.OfferProvider;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;


// TODO: Auto-generated Javadoc
/**
 * The Class NavigationPublic.
 */
@SuppressWarnings("serial")
public class NavigationPublic extends CustomComponent {

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
	 * Instantiates a new navigation public.
	 */
	@SuppressWarnings("unused")
	public NavigationPublic() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		mainLayout.setStyleName("navigation");
		// TODO add user code here
		
		//Bild
		
		
		HorizontalLayout h = new HorizontalLayout();
		
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/image/dh.PNG"));
		
		Image image = new Image("",resource);
		h.addComponent(image);
		
		mainLayout.addComponent(image);
	
		
		
		//Navigation
		menuBar_1 = new MenuBar();
		menuBar_1.setImmediate(false);
		menuBar_1.setHeight("43px");
		menuBar_1.setSizeFull();
	
		
		MenuBar.Command mycommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				LoginWindow w = new LoginWindow();
				UI.getCurrent().addWindow(w);
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
				String name = "Registrierung";
				getUI().getNavigator().addView(name, new Registrierung());
				getUI().getNavigator().navigateTo(name);
				
			}  
		};
		
		MenuBar.Command mycommand4 = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "FAQ";
				getUI().getNavigator().addView(name, new FAQ());
				getUI().getNavigator().navigateTo(name);
				
			}  
		};
		
		MenuBar.Command mycommand5 = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				
				List<Offer> allOffers = new OfferProvider().getAllOffers();
				
				String name = "Angebotsliste";
				getUI().getNavigator().addView(name, new Suchergebnis(allOffers));
				getUI().getNavigator().navigateTo(name);
			}
		};
					
		//Navigationsschaltflächen
		MenuItem nav0 = menuBar_1.addItem("Startseite", FontAwesome.HOME, mycommand1);
		MenuItem nav1 = menuBar_1.addItem("Wohnung suchen", FontAwesome.SEARCH, null); //Suche
			nav1.addItem("Suche nach Kriterien", FontAwesome.SEARCH, mycommand2);
			nav1.addItem("Alle Wohnungsangebote", FontAwesome.TH_LIST, mycommand5);
		MenuItem nav2 = menuBar_1.addItem("Registrierung", FontAwesome.UNLOCK_ALT, mycommand3);//Navigation
		MenuItem nav3 = menuBar_1.addItem("Login", FontAwesome.UNLOCK_ALT, mycommand);//Navigation
		MenuItem nav4 = menuBar_1.addItem("Hilfe", FontAwesome.QUESTION, mycommand4);//Navigation zu FAQ
	
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
