package com.example.housing;

import java.io.File;
import java.util.List;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.OfferProvider;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class Navigation.
 */
@SuppressWarnings("serial")
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
	@SuppressWarnings("unused")
	public Navigation() {
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
		
		if((boolean) VaadinSession.getCurrent().getAttribute("login")){
			Label l = new Label();
			l.setContentMode(ContentMode.HTML);
			l.setValue(""+FontAwesome.USER.getHtml()+"  Sie sind angemeldet als:    "+VaadinSession.getCurrent().getAttribute(User.class).getEmail());
			h.addComponent(l);
			h.setComponentAlignment(l, Alignment.BOTTOM_LEFT);
		}
		
		mainLayout.addComponent(h);
	
		
		
		//Navigation
		menuBar_1 = new MenuBar();
		menuBar_1.setImmediate(false);
		menuBar_1.setHeight("43px");
	    menuBar_1.setSizeFull();
		
		MenuBar.Command mycommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
 
				VaadinSession.getCurrent().setAttribute("login", false);
				VaadinSession.getCurrent().setAttribute(User.class, null);
				
				String name = "Startseite";
				getUI().getNavigator().addView(name, new Startseite());
				getUI().getNavigator().navigateTo(name);
				
				//TODO
				System.out.println("Session ID" + VaadinSession.getCurrent().getSession().getId());
				
				Notification notif = new Notification("Logout erfolgreich.","Besuchen Sie uns bald wieder!", Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
				notif.setDelayMsec(300);
				notif.setStyleName("success");
				notif.show(Page.getCurrent());
			}  
		};
		
		MenuBar.Command mycommand1 = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "Startseite";
				getUI().getNavigator().addView(name, new Startseite());
				getUI().getNavigator().navigateTo(name);
				//TODO
				System.out.println("Session ID" + VaadinSession.getCurrent().getSession().getId());
				
				
				//TODO
				System.out.println("Session-ID: " + VaadinSession.getCurrent().getSession().getId());
				
			}  
		};
		
		
		//Navigator navigator = new Navigator(null, h);
		MenuBar.Command mycommand2 = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "Suche";
				getUI().getNavigator().addView(name, new Suche());
				getUI().getNavigator().navigateTo(name);
				
				//TODO
				System.out.println("Session ID" + VaadinSession.getCurrent().getSession().getId());
				
			}  
		};
		
		MenuBar.Command mycommand3 = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "AngebotErstellen";
				getUI().getNavigator().addView(name, new AngebotErstellen());
				getUI().getNavigator().navigateTo(name);
				
				//TODO
				System.out.println("Session ID" + VaadinSession.getCurrent().getSession().getId());
				
			}  
		};
		
		
		//Navigator navigator = new Navigator(null, h);
		MenuBar.Command mycommand4 = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "AngeboteVerwalten";
				getUI().getNavigator().addView(name, new AngeboteVerwalten());
				getUI().getNavigator().navigateTo(name);

				//TODO
				System.out.println("Session ID" + VaadinSession.getCurrent().getSession().getId());
				
			}  
		};
		
		MenuBar.Command mycommand5 = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "Profile";
				getUI().getNavigator().addView(name, new Profile());
				getUI().getNavigator().navigateTo(name);
				
				//TODO
				System.out.println("Session ID" + VaadinSession.getCurrent().getSession().getId());
				
			}  
		};
		
		MenuBar.Command mycommand6 = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "Favoriten";
				getUI().getNavigator().addView(name, new Favoriten());
				getUI().getNavigator().navigateTo(name);
				
				//TODO
				System.out.println("Session ID" + VaadinSession.getCurrent().getSession().getId());
				
			}  
		};
		
		MenuBar.Command mycommand7 = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "AnfragenVerwalten";
				getUI().getNavigator().addView(name, new AnfragenVerwalten());
				getUI().getNavigator().navigateTo(name);
				
				//TODO
				System.out.println("Session ID" + VaadinSession.getCurrent().getSession().getId());
				
			}  
		};
		
		MenuBar.Command mycommand8 = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "Maps";
				getUI().getNavigator().addView(name, new Maps());
				getUI().getNavigator().navigateTo(name);
				
				//TODO
				System.out.println("Session ID" + VaadinSession.getCurrent().getSession().getId());
				
			}  
		};
			
		MenuBar.Command mycommand9 = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "FAQ";
				getUI().getNavigator().addView(name, new FAQ());
				getUI().getNavigator().navigateTo(name);
				
				//TODO
				System.out.println("Session ID" + VaadinSession.getCurrent().getSession().getId());
				
			}  
		};
		
		MenuBar.Command mycommand10 = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				
				List<Offer> allOffers = new OfferProvider().getAllOffers();
				
				String name = "Angebotsliste";
				getUI().getNavigator().addView(name, new Suchergebnis(allOffers));
				getUI().getNavigator().navigateTo(name);
				
				//TODO
				System.out.println("Session ID" + VaadinSession.getCurrent().getSession().getId());
				
			}
		};
					
		//Navigationsschaltflächen
		MenuItem nav0 = menuBar_1.addItem("Startseite", FontAwesome.HOME, mycommand1); //Startesite
		MenuItem nav1 = menuBar_1.addItem("Wohnung suchen", FontAwesome.SEARCH, null); //Suche
			nav1.addItem("Suche nach Kriterien", FontAwesome.SEARCH, mycommand2);
			nav1.addItem("Alle Wohnungsangebote", FontAwesome.TH_LIST, mycommand10);
		MenuItem nav2 = menuBar_1.addItem("Wohnung anbieten", FontAwesome.HOME, null); 
			nav2.addItem("Neues Wohnungsangebot einstellen", FontAwesome.PENCIL, mycommand3); //AngebotErstellen
			nav2.addItem("Wohnungsangebote verwalten", FontAwesome.WRENCH, mycommand4); //AngebotAnzeigen
		MenuItem nav5 = menuBar_1.addItem("Persönliche Daten", FontAwesome.CHILD, null); 
			nav5.addItem("Meine Favoriten", FontAwesome.STAR_O, mycommand6);
			nav5.addItem("Meine Anfragen", FontAwesome.STAR_O, mycommand7);
			nav5.addItem("Meine Profildaten", FontAwesome.USER, mycommand5); 
		MenuItem nav6 = menuBar_1.addItem("Logout", FontAwesome.UNLOCK_ALT, mycommand);//Navigation
		//MenuItem nav8 = menuBar_1.addItem("Maps", FontAwesome.MAP_MARKER, mycommand8);//Navigation
		MenuItem nav9 = menuBar_1.addItem("Hilfe", FontAwesome.QUESTION, mycommand9);//Navigation zu FAQ
		
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
