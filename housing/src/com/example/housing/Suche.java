package com.example.housing;

import java.util.Date;

import com.example.housing.data.provider.OfferProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class Suche.
 */
public class Suche extends VerticalLayout implements View{

	/** The content. */
	VerticalLayout content;
	
	/* (non-Javadoc)
	 * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Instantiates a new suche.
	 */
	public Suche(){
		Navigation nav = new Navigation();
		addComponent(nav);
		
		NavigationPublic navPublic = new NavigationPublic();
		addComponent(navPublic);
		
		//falls der Benutzer eingelogt ist ver�ndert sich die Navigation
		if(VaadinSession.getCurrent().getAttribute("login").equals(true)){
			nav.setVisible(true);
			navPublic.setVisible(false);
		}else{
			nav.setVisible(false);
			navPublic.setVisible(true);
		}
			
		
		setContent();
		addComponent(content);
		
		Footer f = new Footer();
		addComponent(f);
	}
	
	/**
	 * Sets the content.
	 */
	public void setContent(){
		
		content = new VerticalLayout();
		Label suche = new Label("Suche");
		suche.addStyleName("suchLabel");
		content.addComponent(suche);
		content.setMargin(true);
		
		GridLayout gridSuche = new GridLayout(5,9); 
		gridSuche.setSpacing(true);
	//	gridSuche.setWidth("40%");
		gridSuche.addStyleName("LayoutSuche");
		
		//Stadt
		gridSuche.addComponent(new Label("Stadt:  "), 0 ,0);
		final TextField stadt = new TextField();
		gridSuche.addComponent(stadt,1 ,0);

		//Gr��e
		gridSuche.addComponent(new Label("Quadratmeter:  "), 0 ,1);
		gridSuche.addComponent(new Label("von  "), 1 ,1);
		final TextField sucheVon = new TextField();
		gridSuche.addComponent(sucheVon, 2,1);
		gridSuche.addComponent(new Label("bis  "), 3 , 1);
		final TextField sucheBis = new TextField();
		gridSuche.addComponent(sucheBis, 4,1);
		
		//Preis
		gridSuche.addComponent(new Label("Preis:  "), 0 ,2);
		gridSuche.addComponent(new Label("von  "), 1 ,2);
		final TextField preisVon = new TextField();
		gridSuche.addComponent(preisVon, 2,2);
		gridSuche.addComponent(new Label("bis  "), 3 , 2);
		final TextField preisBis = new TextField();
		gridSuche.addComponent(preisBis, 4,2);
		
		//Zeitraum
		gridSuche.addComponent(new Label("Zeitraum:  "), 0 ,3);
		gridSuche.addComponent(new Label("von  "), 1 ,3);
		final PopupDateField zeitVon = new PopupDateField();
		gridSuche.addComponent(zeitVon, 2,3);
		gridSuche.addComponent(new Label("bis  "), 3 , 3);
		final PopupDateField zeitBis = new PopupDateField();
		gridSuche.addComponent(zeitBis, 4,3);
		
		//Art der Unterkunft
		gridSuche.addComponent(new Label("Unterkunft:"), 0 ,4);
		gridSuche.addComponent(new Label("WG"), 1 ,4);
		final CheckBox wg = new CheckBox();
		gridSuche.addComponent(wg, 2 ,4);
		gridSuche.addComponent(new Label("Wohnung"), 3 ,4);
		final CheckBox wohnung = new CheckBox();
		gridSuche.addComponent(wohnung, 4 ,4);
		
		//Sonstiges
		gridSuche.addComponent(new Label("Sonstiges:"), 0 ,5);
		gridSuche.addComponent(new Label("Haustiere"), 1 ,5);
		final CheckBox haustiere = new CheckBox();
		gridSuche.addComponent(haustiere, 2 ,5);
		gridSuche.addComponent(new Label("Raucher"), 3 ,5);
		final CheckBox rauchen = new CheckBox();
		gridSuche.addComponent(rauchen, 4 ,5);
		gridSuche.addComponent(new Label("möbliert"), 1 ,6);
		final CheckBox moebliert = new CheckBox();
		gridSuche.addComponent(moebliert, 2, 6);
		gridSuche.addComponent(new Label("Küche"), 3 ,6);
		final CheckBox kueche = new CheckBox();
		gridSuche.addComponent(kueche, 4 ,6);
		gridSuche.addComponent(new Label("Internet"), 1 ,7);
		final CheckBox internet = new CheckBox();
		gridSuche.addComponent(internet, 2 ,7);
		
		Button suchButton = new Button("Suchen");
		suchButton.addStyleName("SuchButton");
		gridSuche.addComponent(suchButton, 0 ,8);
		
		//Suchfunktion
	

		suchButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				final int a;
				if(wg.getValue()){
					a = 1;
				}else if (wg.getValue()&& wohnung.getValue()){
					a = 2;
				}else if (wohnung.getValue()){
					a = 3;
				}else{
					a = 4;
					Notification.show("Bitte Art der Unterkunft w�hlen!");
				}

				
				OfferProvider of = new OfferProvider();
				of.filter(zeitVon.getValue(),
						zeitBis.getValue(),
						(sucheVon.getValue()=="") ? (float)0.0 : Float.parseFloat(sucheVon.getValue()),
						(sucheBis.getValue()=="") ? (float)0.0 : Float.parseFloat(sucheBis.getValue()), 
						(preisVon.getValue()=="") ? (float)0.0 : Float.parseFloat(preisVon.getValue()),
						(preisBis.getValue()=="") ? (float)0.0 : Float.parseFloat(preisBis.getValue()),
						a, internet.getValue(), moebliert.getValue(), kueche.getValue(),rauchen.getValue(),
						haustiere.getValue(),
						stadt.getValue());
			}
		});
		
		content.addComponent(gridSuche);
		
		
//		HorizontalLayout h1 = new HorizontalLayout();
//		VerticalLayout v1 = new VerticalLayout();
//		VerticalLayout v2 = new VerticalLayout();
//		VerticalLayout v3 = new VerticalLayout();
//		VerticalLayout v4 = new VerticalLayout();
//		VerticalLayout v5 = new VerticalLayout();
//		HorizontalLayout h2 = new HorizontalLayout();
//		HorizontalLayout h3 = new HorizontalLayout();
//		HorizontalLayout h4 = new HorizontalLayout();
//		HorizontalLayout h5 = new HorizontalLayout();

//		
//		h1.setSpacing(true);
//		content.addComponent(h1);
//		h1.addComponent(v1);
//		v1.addComponent(new Label("Quadratmeter")); 
//		v1.addComponent(new Label("von")); 
//		v1.addComponent(new TextField());	
//		v1.addComponent(new Label("bis"));	
//		v1.addComponent(new TextField()); 
//		v1.addComponent(new Button("Suchen"));
//		h1.addComponent(v2);
//		v2.addComponent(new Label("Preis"));
//		v2.addComponent(new Label("von"));
//		v2.addComponent(new TextField());
//		v2.addComponent(new Label("bis"));
//		v2.addComponent(new TextField());
//		h1.addComponent(v3);
//		v3.addComponent(new Label("Zeitraum"));
//		v3.addComponent(new Label("von"));
//		v3.addComponent(new PopupDateField());
//		v3.addComponent(new Label("bis"));
//		v3.addComponent(new PopupDateField());
//			v4.setSpacing(true);
//			v5.setSpacing(true);
//			h2.setSpacing(true);
//			h3.setSpacing(true);
//			h4.setSpacing(true);
//			h5.setSpacing(true);
//		h1.addComponent(v4);
//		v4.addComponent(new Label("Art der Unterkunft"));
//		h2.setSpacing(true);
//		v4.addComponent(h2);
//		h2.addComponent(new Label("WG-Zimmer"));
//		h2.addComponent(new CheckBox());
//		v4.addComponent(h3);
//		h3.addComponent(new Label("Wohnung"));
//		h3.addComponent(new CheckBox());
//		h1.addComponent(v5);
//		v5.addComponent(new Label("Sonstiges"));
//		v5.addComponent(h4);
//		h4.addComponent(new Label("Haustiere erlaubt"));
//		h4.addComponent(new CheckBox());
//		v5.addComponent(h5);
//		h5.addComponent(new Label("Rauchen erlaubt"));
//		h5.addComponent(new CheckBox());
//		
		//TODO Inhalt einf�gen
		
	}

}
