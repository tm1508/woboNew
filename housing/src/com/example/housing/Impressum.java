package com.example.housing;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class Startseite.
 */
@SuppressWarnings("serial")
public class Impressum extends CustomHorizontalLayout implements View {

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
		content = super.initCustomHorizontalLayout();
		setContent();
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
				
				"Duale Hochschule Baden-W�rttemberg Karlsruhe <br/>"
				+ "Baden-Wuerttemberg Cooperative State University Karlsruhe<br/>"
				+ "Erzbergerstra�e 121 � 76133 Karlsruhe<br/>"
				+ "Postfach 10 01 36 � 76231 Karlsruhe<br/>"
				+ "Telefon +49 (0)721 97 35 - 5<br/>"
				+ "Fax +49 (0)721 97 35 - 600<br/>"
				+ "<br/>"
				+ "Umsatzsteuer-Identifikationsnummer gem�� � 5 Telemediengesetz: DE287664832<br/>"
				+ "Anschriftsdaten f�r o.g. Umsatzsteuer-Identifikationsnummer:<br/>"
				+ "Duale Hochschule<br/>"
				+ "Baden-W�rttemberg<br/>"
				+ "Pr�sidium<br/>"
				+ "Friedrichstr. 14<br/>"
				+ "70174 Stuttgart<br/>"
				+ "info@dhbw-karlsruhe.de<br/>"
				+ "<a href=http://www.dhbw-karlsruhe.de/>http://www.dhbw-karlsruhe.de</a><br/>"
				+ "<br/>"
				+ "Redaktion und Verantwortung f�r die Homepage:<br/>"
				+ "Duale Hochschule Baden-W�rttemberg Karlsruhe<br/>"
				+ "<br/>"
				+ "Haftungsausschluss<br/>"
				+ "<br/>"
				+ "1. Inhalt des Onlineangebotes<br/>"
				+ "Die Duale Hochschule Baden-W�rttemberg Karlsruhe �bernimmt keinerlei Gew�hr f�r die Aktualit�t, Korrektheit, Vollst�ndigkeit oder Qualit�t der bereitgestellten Informationen. Haftungsanspr�che gegen den Autor, welche sich auf Sch�den materieller oder ideeller Art beziehen, die durch die Nutzung oder Nichtnutzung der dargebotenen Informationen bzw. durch die Nutzung fehlerhafter und unvollst�ndiger Informationen verursacht wurden, sind grunds�tzlich ausgeschlossen, sofern seitens der Dualen Hochschule Baden-W�rttemberg Karlsruhe kein nachweislich vors�tzliches oder grob fahrl�ssiges Verschulden vorliegt."
				+ "Alle Angebote sind freibleibend und unverbindlich. Die Duale Hochschule Baden-W�rttemberg Karlsruhe beh�lt es sich ausdr�cklich vor, Teile der Seiten oder das gesamte Angebot ohne gesonderte Ank�ndigung zu ver�ndern, zu erg�nzen, zu l�schen oder die Ver�ffentlichung zeitweise oder endg�ltig einzustellen.<br/>"
				+ "<br/>"
				+ "2. Verweise und Links<br/>"
				+ "Bei direkten oder indirekten Verweisen auf fremde Internetseiten ('Links'), die au�erhalb des Verantwortungsbereiches der Dualen Hochschule Baden-W�rttemberg Karlsruhe liegen, w�rde eine Haftungsverpflichtung ausschlie�lich in dem Fall in Kraft treten, in dem die Duale Hochschule Baden-W�rttemberg Karlsruhe von den Inhalten Kenntnis hat und es ihr technisch m�glich und zumutbar w�re, die Nutzung im Falle rechtswidriger Inhalte zu verhindern."
				+ "Die Duale Hochschule Baden-W�rttemberg Karlsruhe erkl�rt hiermit ausdr�cklich, dass zum Zeitpunkt der Linksetzung keine illegalen Inhalte auf den zu verlinkenden Seiten erkennbar waren. Auf die aktuelle und zuk�nftige Gestaltung, die Inhalte oder die Urheberschaft der gelinkten/verkn�pften Seiten hat die Duale Hochschule Baden-W�rttemberg Karlsruhe keinerlei Einfluss. Deshalb distanziert sie sich hiermit ausdr�cklich von allen Inhalten aller gelinkten/verkn�pften Seiten, die nach der Linksetzung ver�ndert wurden. Diese Feststellung gilt f�r alle innerhalb des eigenen Internetangebotes gesetzten Links und Verweise. F�r illegale, fehlerhafte oder unvollst�ndige Inhalte und insbesondere f�r Sch�den, die aus der Nutzung oder Nichtnutzung solcherart dargebotener Informationen entstehen, haftet allein der Anbieter der Seite, auf welche verwiesen wurde, nicht derjenige, der �ber Links auf die jeweilige Ver�ffentlichung lediglich verweist.<br/>"
				+ "<br/>"
				+ "3. Urheber- und Kennzeichenrecht<br/>"
				+ "Die Duale Hochschule Baden-W�rttemberg Karlsruhe ist bestrebt, in allen Publikationen die Urheberrechte der verwendeten Grafiken, Tondokumente, Videosequenzen und Texte zu beachten, von ihr selbst erstellte Grafiken, Tondokumente, Videosequenzen und Texte zu nutzen oder auf lizenzfreie Grafiken, Tondokumente, Videosequenzen und Texte zur�ckzugreifen.<br/>"
				+ "Alle innerhalb des Internetangebotes genannten und ggf. durch Dritte gesch�tzten Marken- und Warenzeichen unterliegen uneingeschr�nkt den Bestimmungen des jeweils g�ltigen Kennzeichenrechts und den Besitzrechten der jeweiligen eingetragenen Eigent�mer. Allein aufgrund der blo�en Nennung ist nicht der Schluss zu ziehen, dass Markenzeichen nicht durch Rechte Dritter gesch�tzt sind.<br/>"
				+ "Das Copyright f�r ver�ffentlichte, von der Duale Hochschule Baden-W�rttemberg Karlsruhe selbst erstellte Objekte bleibt allein bei der Dualen Hochschule Baden-W�rttemberg Karlsruhe. Eine Vervielf�ltigung oder Verwendung solcher Grafiken, Tondokumente, Videosequenzen und Texte in anderen elektronischen oder gedruckten Publikationen ist ohne ausdr�ckliche Zustimmung der Dualen Hochschule Baden-W�rttemberg Karlsruhe nicht gestattet.<br/>"
				+ "<br/>"
				+ "4. Datenschutz<br/>"
				+ "Sofern innerhalb des Internetangebotes die M�glichkeit zur Eingabe pers�nlicher oder gesch�ftlicher Daten (Emailadressen, Namen, Anschriften) besteht, so erfolgt die Preisgabe dieser Daten seitens des Nutzers auf ausdr�cklich freiwilliger Basis.<br/>"
				+ "<br/>"
				+ "5. Rechtswirksamkeit dieses Haftungsausschlusses<br/>"
				+ "Dieser Haftungsausschluss ist als Teil des Internetangebotes zu betrachten, von dem aus auf diese Seite verwiesen wurde. Sofern Teile oder einzelne Formulierungen dieses Textes der geltenden Rechtslage nicht, nicht mehr oder nicht vollst�ndig entsprechen sollten, bleiben die �brigen Teile des Dokumentes in ihrem Inhalt und ihrer G�ltigkeit davon unber�hrt!<br/>");
				
		l.setContentMode(ContentMode.HTML);
		content.addComponent(l);// verschiedene Kategorien hinzuf�gen

	}

}
