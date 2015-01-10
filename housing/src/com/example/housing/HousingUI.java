package com.example.housing;

import java.util.Map;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;



















import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Page;
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
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

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
			super.servletInitialized();
			getService().addSessionInitListener(this);
			getService().addSessionDestroyListener(this);
			
			getService().setSystemMessagesProvider(
				    new SystemMessagesProvider() {
				    @Override 
				    public SystemMessages getSystemMessages(
				        SystemMessagesInfo systemMessagesInfo) {
				        CustomizedSystemMessages messages =
				                new CustomizedSystemMessages();
				        messages.setCommunicationErrorCaption("Comm Err");
				        messages.setCommunicationErrorMessage("This is bad.");
				        messages.setCommunicationErrorNotificationEnabled(true);
				        messages.setCommunicationErrorURL("http://vaadin.com/");
				        
				        messages.setSessionExpiredCaption("Ihre Session ist abgelaufen.");
				        messages.setSessionExpiredMessage("Bitte laden Sie die Seite neu oder drücken Sie ESC. Sie müssen sich eventuell erneut einloggen.");
				        messages.setInternalErrorCaption("Es ist ei Fehler aufgetreten");
				        
				        return messages;
				    }
				});
			
			
			
			
			
		}
		
		/* (non-Javadoc)
		 * @see com.vaadin.server.SessionInitListener#sessionInit(com.vaadin.server.SessionInitEvent)
		 */
		@Override
		public void sessionInit(SessionInitEvent event)throws ServiceException {
			event.getSession().setAttribute("login", false);
			event.getSession().setAttribute("activated", "");
			event.getSession().setAttribute(User.class, null);
			
			
			
			
			
				
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
		
		
		navigator = new Navigator(this, this);
		String name = "Startseite";
		
		navigator.addView(name, new Startseite());
		navigator.navigateTo(name);
		
		
		navigator.setErrorView(new ErrorPage());
		
		// Configure the error handler for the UI
		UI.getCurrent().setErrorHandler(new DefaultErrorHandler() {
		    @Override
		    public void error(com.vaadin.server.ErrorEvent event) {
		        // Find the final cause
		        String cause = "<b>The click failed because:</b><br/>";
		        for (Throwable t = event.getThrowable(); t != null;
		             t = t.getCause())
		            if (t.getCause() == null) // We're at final cause
		                cause += t.getClass().getName() + "<br/>";
		        
		        // Display the error message in a custom fashion
		        System.out.println("Fehler!!!!!!!!!!!!!!!!!!!!!!!!!!"+cause);
		        
				navigator.addView("Error", new ErrorPage());
				navigator.navigateTo("Error");
		        // Do the default error handling (optional)
		        //doDefault(event);
		    } 
		});
		
	}
	
	 @Override
	    public void refresh(VaadinRequest request) {
		  System.out.println(request.getContextPath());
		  System.out.println("ok");
	 }
	


}

