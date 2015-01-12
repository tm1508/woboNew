package com.example.housing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import com.example.housing.data.model.User;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
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
@SuppressWarnings("serial")
@Theme("housing")
@PreserveOnRefresh 
public class HousingUI extends UI {

	/** The navigator. */
	Navigator navigator;
	
	/**
	 * The Class Servlet.
	 */
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = HousingUI.class)
	
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
		}
		
		/* (non-Javadoc)
		 * @see com.vaadin.server.SessionDestroyListener#sessionDestroy(com.vaadin.server.SessionDestroyEvent)
		 */
		@Override
		public void sessionDestroy(SessionDestroyEvent event) {
			
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
		String[] msgs = request.getParameter("v-loc").split("/");//Request Parameter auslesen (wurde bei der Registrierung verschickt)
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
		//navigator.addView(name, new UploadTest());
		navigator.navigateTo(name);
		
		navigator.setErrorView(new ErrorPage());//Navigation zur Fehlerseite
		
		// Error Handler
		UI.getCurrent().setErrorHandler(new DefaultErrorHandler() {
		    @Override
		    public void error(com.vaadin.server.ErrorEvent event) {
		    	//Navigation zur Fehlerseite
				navigator.addView("Error", new ErrorPage());
				navigator.navigateTo("Error");
		        doDefault(event);//falls rotes Ausrufezeichen im Browser angezeigt werden soll
		    } 
		}); 
		
	}
	
}

