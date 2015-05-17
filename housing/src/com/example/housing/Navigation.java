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
	MenuBar menuBar;
	
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
		Image image = new Image(null,resource);
		h.addComponent(image);
		
		if((boolean) VaadinSession.getCurrent().getSession().getAttribute("login")){
			Label l = new Label();
			l.setContentMode(ContentMode.HTML);
			l.setValue(""+FontAwesome.USER.getHtml()+"  Sie sind angemeldet als:    "+((User) (VaadinSession.getCurrent().getSession().getAttribute("user"))).getEmail());
			h.addComponent(l);
			h.setComponentAlignment(l, Alignment.BOTTOM_LEFT);
		}
		
		mainLayout.addComponent(h);
	
		
		
		//Navigation
		menuBar = new MenuBar();
		menuBar.setImmediate(false);
		menuBar.setHeight("43px");
	    menuBar.setSizeFull();
		
	    //Logout
		MenuBar.Command logout = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				
				try {
					VaadinSession.getCurrent().getLockInstance().lock();
					VaadinSession.getCurrent().getSession().setAttribute("login", false);
					VaadinSession.getCurrent().getSession().setAttribute("user", null);
				} finally {
					VaadinSession.getCurrent().getLockInstance().unlock();
				}
				
				String name = "Startseite";
				getUI().getNavigator().addView(name, new Startseite());
				getUI().getNavigator().navigateTo(name);
				
				Notification notif = new Notification("Logout erfolgreich.","Besuchen Sie uns bald wieder!", Type.HUMANIZED_MESSAGE);//Meldung an den Nutzer
				notif.setDelayMsec(300);
				notif.setStyleName("success");
				notif.show(Page.getCurrent());
			}  
		};
		
		//Startseite
		MenuBar.Command startseite = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "Startseite";
				getUI().getNavigator().addView(name, new Startseite());
				getUI().getNavigator().navigateTo(name);			
			}  
		};
		
		
		//Suche
		MenuBar.Command suche = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "Suche";
				getUI().getNavigator().addView(name, new Suche());
				getUI().getNavigator().navigateTo(name);		
			}  
		};
		
		//Angebot erstellen
		MenuBar.Command angebotErstellen = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "AngebotErstellen";
				getUI().getNavigator().addView(name, new AngebotErstellen());
				getUI().getNavigator().navigateTo(name);		
			}  
		};
		
		
		//Meine Angebote
		MenuBar.Command meineAngebote = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "AngeboteVerwalten";
				getUI().getNavigator().addView(name, new AngeboteVerwalten());
				getUI().getNavigator().navigateTo(name);	
			}  
		};
		
		//Profil
		MenuBar.Command profil = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "Profile";
				getUI().getNavigator().addView(name, new Profile());
				getUI().getNavigator().navigateTo(name);	
			}  
		};
		
		//Meine Favoriten
		MenuBar.Command meineFavoriten = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "Favoriten";
				getUI().getNavigator().addView(name, new Favoriten());
				getUI().getNavigator().navigateTo(name);	
			}  
		};
		
		//Meine Anfragen
		MenuBar.Command meineAnfragen = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "AnfragenVerwalten";
				getUI().getNavigator().addView(name, new AnfragenVerwalten());
				getUI().getNavigator().navigateTo(name);	
			}  
		};
		
//		//GoogleMaps
//		MenuBar.Command mycommand8 = new MenuBar.Command() {
//			public void menuSelected(MenuItem selectedItem) {
//				String name = "Maps";
//				getUI().getNavigator().addView(name, new Maps());
//				getUI().getNavigator().navigateTo(name);		
//			}  
//		};
		
		//FAQ / Hilfe
		MenuBar.Command faq = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String name = "FAQ";
				getUI().getNavigator().addView(name, new FAQ());
				getUI().getNavigator().navigateTo(name);			
			}  
		};
		
		//Alle Angebote
		MenuBar.Command alleAngebote = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				
				List<Offer> allOffers = new OfferProvider().getAllOffers();
				
				String name = "Angebotsliste";
				getUI().getNavigator().addView(name, new Suchergebnis(allOffers));
				getUI().getNavigator().navigateTo(name);	
			}
		};
					
		//Navigationsschaltflächen
		MenuItem nav0 = menuBar.addItem("Startseite", FontAwesome.HOME, startseite); //Startesite
		MenuItem nav1 = menuBar.addItem("Wohnung suchen", FontAwesome.SEARCH, null); //Suche
			nav1.addItem("Suche nach Kriterien", FontAwesome.SEARCH_PLUS, suche);
			nav1.addItem("Alle Wohnungsangebote", FontAwesome.LIST_UL, alleAngebote);
		MenuItem nav2 = menuBar.addItem("Wohnung anbieten", FontAwesome.HOME, null); 
			nav2.addItem("Neues Wohnungsangebot einstellen", FontAwesome.PENCIL, angebotErstellen); //AngebotErstellen
			nav2.addItem("Meine Wohnungsangebote verwalten", FontAwesome.WRENCH, meineAngebote); //AngebotAnzeigen
		MenuItem nav5 = menuBar.addItem("Persönliche Daten", FontAwesome.CHILD, null); 
			nav5.addItem("Meine Favoriten", FontAwesome.STAR_O, meineFavoriten);
			nav5.addItem("Meine Anfragen", FontAwesome.ENVELOPE_O, meineAnfragen);
			nav5.addItem("Meine Profildaten", FontAwesome.USER, profil); 
		MenuItem nav6 = menuBar.addItem("Logout", FontAwesome.UNLOCK_ALT, logout);//Navigation
		//MenuItem nav8 = menuBar_1.addItem("Maps", FontAwesome.MAP_MARKER, mycommand8);//Navigation
		MenuItem nav9 = menuBar.addItem("Hilfe", FontAwesome.QUESTION, faq);//Navigation zu FAQ
		
		mainLayout.addComponent(menuBar);			
		
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
