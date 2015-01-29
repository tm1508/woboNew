package com.example.housing;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.example.housing.data.model.*;
import com.example.housing.data.provider.*;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionDestroyEvent;
import com.vaadin.server.SessionDestroyListener;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.SystemMessagesInfo;
import com.vaadin.server.SystemMessagesProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class HousingUI.
 */

@Theme("housing")
@PreserveOnRefresh
public class HousingUI extends UI {

	/** The navigator. */
	Navigator navigator;
	
	/**
	 * The Class Servlet.
	 */
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = HousingUI.class, widgetset = "com.example.housing.HousingWidgetset")
	
	public static class Servlet extends VaadinServlet implements SessionInitListener, SessionDestroyListener{

		/* (non-Javadoc)
		 * @see com.vaadin.server.VaadinServlet#servletInitialized()
		 */
		@Override
		protected void servletInitialized() throws ServletException {
			//Initialisieren der Session
			super.servletInitialized();
			getService().addSessionInitListener(this);
			getService().addSessionDestroyListener(this);
			
			//Verändern der Fehlermeldungen (z.B. für eine abgelaufene Session")
			getService().setSystemMessagesProvider(
				    new SystemMessagesProvider() {
				    @Override 
				    public SystemMessages getSystemMessages(
				        SystemMessagesInfo systemMessagesInfo) {
				        CustomizedSystemMessages messages =
				                new CustomizedSystemMessages();
				  		//Fehlertexte		        
				        messages.setSessionExpiredCaption("Ihre Session ist abgelaufen.");
				        messages.setSessionExpiredMessage("Bitte laden Sie die Seite neu oder drücken Sie ESC. Sie müssen sich eventuell erneut einloggen.");
				        messages.setCommunicationErrorCaption("Ein Kommunikationsproblem ist aufgetreten.");
				        messages.setCommunicationErrorMessage("Bitte notieren Sie sich Ihre noch nicht gespeicherten Eingaben.");
				        messages.setInternalErrorCaption("Ein Serverproblem ist aufgetreten.");
				        messages.setInternalErrorMessage("Bitte versuchen Sie es später erneut. Sollte der Fehler erneut auftreten, informieren Sie bitte den Administrator.");
				        return messages;
				    }
				});	
		}
		
		/* (non-Javadoc)
		 * @see com.vaadin.server.SessionInitListener#sessionInit(com.vaadin.server.SessionInitEvent)
		 */
		@Override
		public void sessionInit(SessionInitEvent event)throws ServiceException {
			event.getSession().setAttribute("login", false);//Ist ein Nutzer eingeloggt? true=ja, false=nein
			event.getSession().setAttribute("activated", "");//speichern des Requestparameters für die Aktivierung
			event.getSession().setAttribute(User.class, null);//evtl. speichern des eingeloggten Users	
			event.getSession().setAttribute("buttonClicked", false);
			
		}
		
		/* (non-Javadoc)
		 * @see com.vaadin.server.SessionDestroyListener#sessionDestroy(com.vaadin.server.SessionDestroyEvent)
		 */
		@Override
		public void sessionDestroy(SessionDestroyEvent event) {
			
			if((boolean) event.getSession().getAttribute("login")) {
				
				//inaktive, "leere" Angebote des Users löschen
				User u = event.getSession().getAttribute(User.class);
				
				OfferProvider offerProv = new OfferProvider();
				List<Offer> failedOffers = offerProv.findFailedOffersByUser(u);
				for (Offer o : failedOffers) {
					offerProv.removeOffer(o);
				}
				
				//TODO hochgeladene Bilder löschen bei Angebot bearbeiten
				
			}
			
		}
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
	 */
	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
			
		System.out.println(request.getParameter("v-loc"));
		String[] msgs = request.getParameter("v-loc").split("#!Startseite/");//Request Parameter auslesen (wurde bei der Registrierung verschickt)
		String param="";
		for(int i=0; i<msgs.length; i++){
			System.out.println(msgs[i]);
			param =msgs[i];
		}
		VaadinSession.getCurrent().setAttribute("activated", param);
		
		//Navigation zur Startseite
		navigator = new Navigator(this, this);
		String name = "Startseite";
		navigator.addView(name, new Startseite());
		navigator.navigateTo(name);
		
		//navigator.setErrorView(new ErrorPage(null));//Navigation zur Fehlerseite
		navigator.addViewChangeListener(new ViewChangeListener() {
			
			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				
				//AngebotErstellen ohne Button verlassen
				//TODO: Button als Auslöser ausschließen!!!
				if(event.getOldView().getClass().toString().equals("class com.example.housing.AngebotErstellen") && !((boolean) VaadinSession.getCurrent().getAttribute("buttonClicked"))) {		
					if(((AngebotErstellen) event.getOldView()).getNewPhotos() != null) {
						//TODO alle Fotos wieder löschen
						PhotoProvider photoProv = new PhotoProvider();
						List<Photo> photos = ((AngebotErstellen) event.getOldView()).getNewPhotos();
						for(Photo p : photos) {
							photoProv.removePhoto(p);
						}
						((AngebotErstellen) event.getOldView()).setNewPhotos(null);
						
					} else {
						
						Offer o = new OfferProvider().findById(((AngebotErstellen) event.getOldView()).getCurrentOffer().getIdOffer());
						new OfferProvider().removeOffer(o);
						
					}
				}
				return true;
			} 
			
			@Override
			public void afterViewChange(ViewChangeEvent event) {
				
				VaadinSession.getCurrent().setAttribute("buttonClicked", false);
				
			}
		});
		
		// Error Handler
		UI.getCurrent().setErrorHandler(new DefaultErrorHandler() {
		    @Override
		    public void error(com.vaadin.server.ErrorEvent event) {
		    	//Navigation zur Fehlerseite
				navigator.addView("Error", new ErrorPage(event));
				navigator.navigateTo("Error");
				
				//TODO auskommentieren, damit der User die Fehlermeldungen nicht bekommt
		        doDefault(event);//falls rotes Ausrufezeichen im Browser angezeigt werden soll
		    } 
		});   
		
	}
	
}

