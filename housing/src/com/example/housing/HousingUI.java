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

@Theme("housing")
@PreserveOnRefresh
public class HousingUI extends UI {
	private static final long serialVersionUID = 1L;

	private Navigator navigator;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = HousingUI.class, widgetset = "com.example.housing.HousingWidgetset", resourceCacheTime = 10)
	public static class Servlet extends VaadinServlet implements SessionInitListener, SessionDestroyListener{
		private static final long serialVersionUID = 1L;

		@Override
		protected void servletInitialized() throws ServletException {
			//Initialisieren der Session
			super.servletInitialized();
			getService().addSessionInitListener(this);
			getService().addSessionDestroyListener(this);
			
			//Ver�ndern der Fehlermeldungen (z.B. f�r eine abgelaufene Session")
			getService().setSystemMessagesProvider(
				    new SystemMessagesProvider() {
					private static final long serialVersionUID = 1L;

					@Override 
				    public SystemMessages getSystemMessages(
				        SystemMessagesInfo systemMessagesInfo) {
				        CustomizedSystemMessages messages =
				                new CustomizedSystemMessages();
				  		//Fehlertexte		        
				        messages.setSessionExpiredCaption("Ihre Session ist abgelaufen.");
				        messages.setSessionExpiredMessage("Bitte laden Sie die Seite neu oder dr�cken Sie ESC. Sie m�ssen sich gegebenenfalls erneut einloggen.");
				        messages.setCommunicationErrorCaption("Ein Kommunikationsproblem ist aufgetreten.");
				        messages.setCommunicationErrorMessage("Bitte notieren Sie sich Ihre noch nicht gespeicherten Eingaben.");
				        messages.setInternalErrorCaption("Ein Serverproblem ist aufgetreten.");
				        messages.setInternalErrorMessage("Bitte versuchen Sie es sp�ter erneut. Sollte der Fehler erneut auftreten, informieren Sie bitte den Administrator.");
				        return messages;
				    }
				});	
		}
		
		@Override
		public void sessionInit(SessionInitEvent event) throws ServiceException {
			try {
				event.getSession().getLockInstance().lock();
				event.getSession().getSession().setAttribute("login", false);//Ist ein Nutzer eingeloggt? true=ja, false=nein
				event.getSession().getSession().setAttribute("activated", "");//speichern des Requestparameters f�r die Aktivierung
				event.getSession().getSession().setAttribute("user", null);//evtl. speichern des eingeloggten Users	
				event.getSession().getSession().setAttribute("buttonClicked", false);
			} finally {
				event.getSession().getLockInstance().unlock();
			}
		}
		
		@Override
		public void sessionDestroy(SessionDestroyEvent event) {
			if((boolean) event.getSession().getSession().getAttribute("login")) {
				
				//inaktive, "leere" Angebote des Users l�schen
				User u = (User) event.getSession().getSession().getAttribute("user");
				
				OfferProvider offerProv = new OfferProvider();
				List<Offer> failedOffers = offerProv.findFailedOffersByUser(u);
				for (Offer o : failedOffers) {
					offerProv.removeOffer(o);
				}
				//TODO hochgeladene Bilder l�schen bei Angebot bearbeiten
			}
		}
	}

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
			
		String[] msgs = request.getParameter("v-loc").split("#!Startseite/");//Request Parameter auslesen (wurde bei der Registrierung verschickt)
		String param="";
		for(int i=0; i<msgs.length; i++){
			System.out.println(msgs[i]);
			param =msgs[i];
		}
		
		try {
			VaadinSession.getCurrent().getLockInstance().lock();
			VaadinSession.getCurrent().getSession().setAttribute("activated", param);
		} finally {
			VaadinSession.getCurrent().getLockInstance().unlock();
		}
		
		//Navigation zur Startseite
		navigator = new Navigator(this, this);
		String name = "Startseite";
		navigator.addView(name, new Startseite());
		navigator.navigateTo(name);
		
		navigator.addViewChangeListener(new ViewChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				
				//AngebotErstellen ohne Button verlassen
				//TODO: Button als Ausl�ser ausschlie�en!!!
				if(event.getOldView().getClass().toString().equals("class com.example.housing.AngebotErstellen") && !((boolean) VaadinSession.getCurrent().getSession().getAttribute("buttonClicked"))) {		
					if(((AngebotErstellen) event.getOldView()).getNewPhotos() != null) {
						//TODO alle Fotos wieder l�schen
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
				VaadinSession.getCurrent().getSession().setAttribute("buttonClicked", false);
			}
		});
		
		// Error Handler
		UI.getCurrent().setErrorHandler(new DefaultErrorHandler() {
			private static final long serialVersionUID = 1L;
			@Override
		    public void error(com.vaadin.server.ErrorEvent event) {
		    	//Navigation zur Fehlerseite
				navigator.addView("Error", new ErrorPage(event));
				navigator.navigateTo("Error");
				
				//auskommentieren, damit der User die Fehlermeldungen nicht angezeigt bekommt
				//doDefault(event);//falls rotes Ausrufezeichen im Browser angezeigt werden soll
		    } 
		});   
	}
}

