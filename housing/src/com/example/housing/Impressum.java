package com.example.housing;

import java.util.List;

import com.example.housing.data.model.Offer;
import com.example.housing.data.model.User;
import com.example.housing.data.provider.OfferProvider;
import com.example.housing.data.provider.UserProvider;
import com.example.housing.utility.Format;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class Startseite.
 */
public class Impressum extends HorizontalLayout implements View {

	/** The content. */
	VerticalLayout content;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener
	 * .ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	/**
	 * Instantiates a new startseite.
	 */
	public Impressum() {
		this.setWidth("100%");

		// linkes rotes Panel
		Panel p = new Panel();
		p.setWidth("100%");
		p.setHeight("100%");
		p.addStyleName("red");
		addComponent(p);
		this.setExpandRatio(p, 1);

		// mittlerer Teil der Seite
		VerticalLayout v = new VerticalLayout();

		// Navigation hinzufuegen
		Navigation nav = new Navigation();
		nav.setWidth("100%");
		nav.addStyleName("navigation");
		v.addComponent(nav);

		NavigationPublic navPublic = new NavigationPublic();
		v.addComponent(navPublic);

		// falls der Benutzer eingelogt ist verändert sich die Navigation
		if (VaadinSession.getCurrent().getAttribute("login").equals(true)) {
			nav.setVisible(true);
			navPublic.setVisible(false);
		} else {
			nav.setVisible(false);
			navPublic.setVisible(true);
		}

		// Inhalt hinzufuegen
		content = new VerticalLayout();
		content.setMargin(true);
		content.setWidth("100%");
		setContent();// Methode zum befuellen des Inhalts aufrufen
		v.addComponent(content);
	

		// Footer hinzufuegen
		Footer f = new Footer();
		v.addComponent(f);

		// rotes Panel unter dem Footer
		Panel p2 = new Panel();
		p2.setWidth("100%");
		p2.addStyleName("red");
		p2.setHeight("30px");
		v.addComponent(p2);
	

		addComponent(v);
		this.setExpandRatio(v, 12);

		// rotes rechtes Panel
		Panel p1 = new Panel();
		p1.setWidth("100%");
		p1.addStyleName("red");
		p1.setHeight("100%");
		addComponent(p1);
		this.setExpandRatio(p1, 1);
	}

	/**
	 * Sets the content.
	 */
	public void setContent() {

		content = new VerticalLayout();
		content.setMargin(true);
		
		// title
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("Impressum");
		title.addStyleName("title");
		content.addComponent(title);

		Label l = new Label(
				
				"Duale Hochschule Baden-Württemberg Karlsruhe <br/>"
				+ "Baden-Wuerttemberg Cooperative State University Karlsruhe<br/>"
				+ "Erzbergerstraße 121 • 76133 Karlsruhe<br/>"
				+ "Postfach 10 01 36 • 76231 Karlsruhe<br/>"
				+ "Telefon +49 (0)721 97 35 - 5<br/>"
				+ "Fax +49 (0)721 97 35 - 600<br/>"
				+ "<br/>"
				+ "Umsatzsteuer-Identifikationsnummer gemäß § 5 Telemediengesetz: DE287664832<br/>"
				+ "Anschriftsdaten für o.g. Umsatzsteuer-Identifikationsnummer:<br/>"
				+ "Duale Hochschule<br/>"
				+ "Baden-Württemberg<br/>"
				+ "Präsidium<br/>"
				+ "Friedrichstr. 14<br/>"
				+ "70174 Stuttgart<br/>"
				+ "info@dhbw-karlsruhe.de<br/>"
				+ "<a href=http://www.dhbw-karlsruhe.de/>http://www.dhbw-karlsruhe.de</a><br/>"
				+ "<br/>"
				+ "Redaktion und Verantwortung für die Homepage:<br/>"
				+ "Duale Hochschule Baden-Württemberg Karlsruhe<br/>"
				+ "<br/>"
				+ "Haftungsausschluss<br/>"
				+ "<br/>"
				+ "1. Inhalt des Onlineangebotes<br/>"
				+ "Die Duale Hochschule Baden-Württemberg Karlsruhe übernimmt keinerlei Gewähr für die Aktualität, Korrektheit, Vollständigkeit oder Qualität der bereitgestellten Informationen. Haftungsansprüche gegen den Autor, welche sich auf Schäden materieller oder ideeller Art beziehen, die durch die Nutzung oder Nichtnutzung der dargebotenen Informationen bzw. durch die Nutzung fehlerhafter und unvollständiger Informationen verursacht wurden, sind grundsätzlich ausgeschlossen, sofern seitens der Dualen Hochschule Baden-Württemberg Karlsruhe kein nachweislich vorsätzliches oder grob fahrlässiges Verschulden vorliegt."
				+ "Alle Angebote sind freibleibend und unverbindlich. Die Duale Hochschule Baden-Württemberg Karlsruhe behält es sich ausdrücklich vor, Teile der Seiten oder das gesamte Angebot ohne gesonderte Ankündigung zu verändern, zu ergänzen, zu löschen oder die Veröffentlichung zeitweise oder endgültig einzustellen.<br/>"
				+ "<br/>"
				+ "2. Verweise und Links<br/>"
				+ "Bei direkten oder indirekten Verweisen auf fremde Internetseiten ('Links'), die außerhalb des Verantwortungsbereiches der Dualen Hochschule Baden-Württemberg Karlsruhe liegen, würde eine Haftungsverpflichtung ausschließlich in dem Fall in Kraft treten, in dem die Duale Hochschule Baden-Württemberg Karlsruhe von den Inhalten Kenntnis hat und es ihr technisch möglich und zumutbar wäre, die Nutzung im Falle rechtswidriger Inhalte zu verhindern."
				+ "Die Duale Hochschule Baden-Württemberg Karlsruhe erklärt hiermit ausdrücklich, dass zum Zeitpunkt der Linksetzung keine illegalen Inhalte auf den zu verlinkenden Seiten erkennbar waren. Auf die aktuelle und zukünftige Gestaltung, die Inhalte oder die Urheberschaft der gelinkten/verknüpften Seiten hat die Duale Hochschule Baden-Württemberg Karlsruhe keinerlei Einfluss. Deshalb distanziert sie sich hiermit ausdrücklich von allen Inhalten aller gelinkten/verknüpften Seiten, die nach der Linksetzung verändert wurden. Diese Feststellung gilt für alle innerhalb des eigenen Internetangebotes gesetzten Links und Verweise. Für illegale, fehlerhafte oder unvollständige Inhalte und insbesondere für Schäden, die aus der Nutzung oder Nichtnutzung solcherart dargebotener Informationen entstehen, haftet allein der Anbieter der Seite, auf welche verwiesen wurde, nicht derjenige, der über Links auf die jeweilige Veröffentlichung lediglich verweist.<br/>"
				+ "<br/>"
				+ "3. Urheber- und Kennzeichenrecht<br/>"
				+ "Die Duale Hochschule Baden-Württemberg Karlsruhe ist bestrebt, in allen Publikationen die Urheberrechte der verwendeten Grafiken, Tondokumente, Videosequenzen und Texte zu beachten, von ihr selbst erstellte Grafiken, Tondokumente, Videosequenzen und Texte zu nutzen oder auf lizenzfreie Grafiken, Tondokumente, Videosequenzen und Texte zurückzugreifen.<br/>"
				+ "Alle innerhalb des Internetangebotes genannten und ggf. durch Dritte geschützten Marken- und Warenzeichen unterliegen uneingeschränkt den Bestimmungen des jeweils gültigen Kennzeichenrechts und den Besitzrechten der jeweiligen eingetragenen Eigentümer. Allein aufgrund der bloßen Nennung ist nicht der Schluss zu ziehen, dass Markenzeichen nicht durch Rechte Dritter geschützt sind.<br/>"
				+ "Das Copyright für veröffentlichte, von der Duale Hochschule Baden-Württemberg Karlsruhe selbst erstellte Objekte bleibt allein bei der Dualen Hochschule Baden-Württemberg Karlsruhe. Eine Vervielfältigung oder Verwendung solcher Grafiken, Tondokumente, Videosequenzen und Texte in anderen elektronischen oder gedruckten Publikationen ist ohne ausdrückliche Zustimmung der Dualen Hochschule Baden-Württemberg Karlsruhe nicht gestattet.<br/>"
				+ "<br/>"
				+ "4. Datenschutz<br/>"
				+ "Sofern innerhalb des Internetangebotes die Möglichkeit zur Eingabe persönlicher oder geschäftlicher Daten (Emailadressen, Namen, Anschriften) besteht, so erfolgt die Preisgabe dieser Daten seitens des Nutzers auf ausdrücklich freiwilliger Basis.<br/>"
				+ "<br/>"
				+ "5. Rechtswirksamkeit dieses Haftungsausschlusses<br/>"
				+ "Dieser Haftungsausschluss ist als Teil des Internetangebotes zu betrachten, von dem aus auf diese Seite verwiesen wurde. Sofern Teile oder einzelne Formulierungen dieses Textes der geltenden Rechtslage nicht, nicht mehr oder nicht vollständig entsprechen sollten, bleiben die übrigen Teile des Dokumentes in ihrem Inhalt und ihrer Gültigkeit davon unberührt!<br/>");
				
		l.setContentMode(ContentMode.HTML);
		content.addComponent(l);// verschiedene Kategorien hinzufügen

	}

}
