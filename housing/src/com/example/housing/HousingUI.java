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

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionDestroyEvent;
import com.vaadin.server.SessionDestroyListener;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class HousingUI.
 */
@SuppressWarnings("serial")
@Theme("housing")
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
		}
		
		/* (non-Javadoc)
		 * @see com.vaadin.server.SessionInitListener#sessionInit(com.vaadin.server.SessionInitEvent)
		 */
		@Override
		public void sessionInit(SessionInitEvent event)throws ServiceException {
			event.getSession().setAttribute("login", false);
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
		
		navigator = new Navigator(this, this);
		String name = "Startseite";
		
		navigator.addView(name, new Startseite());
		navigator.navigateTo(name);
	}

}
