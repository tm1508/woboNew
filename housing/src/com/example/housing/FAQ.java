package com.example.housing;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class FAQ extends CustomHorizontalLayout implements View {
	private static final long serialVersionUID = 1L;

	private VerticalLayout content;

	@Override
	public void enter(ViewChangeEvent event) {
	}

	public FAQ() {
		content = super.initCustomHorizontalLayout();
		setContent();
	}

	public void setContent() {

		//Titel
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("FAQs");
		title.addStyleName("title");
		content.addComponent(title);

		// Kategorien
		Accordion accordion = new Accordion();
		accordion.setStyleName("startseite");

		accordion.addTab(new Label(),"Hier finden Sie häufig gestellte Fragen und Antworten",FontAwesome.CHEVRON_CIRCLE_DOWN);
		// Tab 1 zu Ihrem Account
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);

		// Unterkategorien zu Fragen zu Ihrem Account:
		Accordion sub_accordion = new Accordion();
		sub_accordion.setStyleName("startseite");

		final Label label = new Label(
				"Bitte versuchen Sie zunächst, sich mit Ihrer E-Mail-Adresse und Ihrem Passwort einzuloggen. \nWenn Sie Ihr Passwort vergessen haben, nutzen Sie die Funktion \"Passwort vergessen?\"");
		sub_accordion.addTab(label,
				"Ich kann mich nicht mehr einloggen. Was nun?",
				FontAwesome.QUESTION);

		// Unterkategorien zu Fragen zu Ihrem Account:
		final Label label_1 = new Label(
				"Ihre Anmeldung läuft in einer Session. Sind Sie längere Zeit nicht aktiv, \nwird die Session beendet und Sie müssen sich erneut anmelden.");
		sub_accordion.addTab(label_1,
				"Was bedeutet \"Ihre Session ist abgelaufen\"?",
				FontAwesome.QUESTION);

		// Unterkategorien zu Fragen zu Ihrem Account:
		final Label label_2 = new Label(
				"Um Ihr Account löschen zu können, müssen Sie sich zunächst einloggen. \nKlicken Sie anschließend auf die Registerkarte Persönliche Daten und Meine Profildaten. Dort finden Sie am Ende der Seite ein Button \"Profil löschen\". ");
		sub_accordion.addTab(label_2, "Wie lösche ich mein Account?",
				FontAwesome.QUESTION);

		layout.addComponent(sub_accordion);
		accordion.addTab(layout, "zu Ihrem Accout", FontAwesome.THUMB_TACK);// Tab
																			// 1
																			// hinzufügen

		// Tab 2 zu Wohnungsangebote als Anbieter
		final VerticalLayout layout_1 = new VerticalLayout();
		layout_1.setMargin(true);
		// Unterkategorien zu Fragen zu Wohnungsangebote als Anbieter
		Accordion sub_accordion_1 = new Accordion();
		sub_accordion_1.setStyleName("startseite");

		final Label label_3 = new Label(
				"Um ein Angebot erstellen zu können, müssen Sie sich zunächst registrieren bzw. einloggen. \nKlicken Sie anschließend auf die Registerkarte Wohnung einstellen und Neue Wohnung anbieten.");
		sub_accordion_1.addTab(label_3,
				"Wie kann ich ein Wohnungsangebot erstellen?",
				FontAwesome.QUESTION);

		final Label label_4 = new Label(
				"Um ein Angebot bearbeiten zu können, müssen Sie sich zunächst einloggen. \nKlicken Sie anschließend auf die Registerkarte Wohnung einstellen und Wohnungsangebote verwalten. \nNun erscheint eine Liste Ihrer Angebote. Wählen Sie das zu bearbeitende Angebot aus. \nAm Ende der Seite befindet sich ein Button \"Bearbeiten\".");
		sub_accordion_1.addTab(label_4,
				"Wie kann ich ein Wohnungsangebot bearbeiten?",
				FontAwesome.QUESTION);

		final Label label_5 = new Label(
				"Um ein Angebot löschen zu können, müssen Sie sich zunächst einloggen. \nKlicken Sie anschließend auf die Registerkarte Wohnung einstellen und Wohnungsangebote verwalten. \nNun erscheint eine Liste Ihrer Angebote. Wählen Sie das gewünschte Angebot aus. \nAm Ende der Seite befindet sich ein Button \"Löschen\". Hinweis: Haben Sie vor das Angebot ggf. später wieder einzustellen ist es möglich, das Angebot für einen Zeitraum zu deaktivieren.");
		sub_accordion_1.addTab(label_5,
				"Wie kann ich ein Wohnungsangebot löschen?",
				FontAwesome.QUESTION);

		final Label label_6 = new Label(
				"Um ein Angebot deaktivieren zu können, müssen Sie sich zunächst einloggen. \nKlicken Sie anschließend auf die Registerkarte Wohnung einstellen und Wohnungsangebote verwalten. \nNun erscheint eine Liste Ihrer Angebote. Wählen Sie das gewünschte Angebot aus. \nKlicken Sie den Button \"Bearbeiten\". Am Ende der Seite befindet sich ein Kästchen um das Angebot zu deaktivieren.");
		sub_accordion_1.addTab(label_6,
				"Wie kann ich ein Wohnungsangebot deaktivieren?",
				FontAwesome.QUESTION);

		layout_1.addComponent(sub_accordion_1);
		accordion.addTab(layout_1, "zu Wohnungsangebote als Anbieter",
				FontAwesome.THUMB_TACK);// Tab 2 hinzufügen

		// Tab 3 zu Wohnungsangebote
		final VerticalLayout layout_2 = new VerticalLayout();
		layout_2.setMargin(true);
		// Unterkategorien zu Fragen zu Wohnungsangebote
		Accordion sub_accordion_2 = new Accordion();
		sub_accordion_2.setStyleName("startseite");

		final Label label_8 = new Label(
				"Ist ein Angebot nicht mehr aktuell und der Anbieter löscht dieses Angebot, wird es automatisch aus allen Listen gelöscht.");
		sub_accordion_2
				.addTab(label_8,
						"Warum ist ein Wohnungsangebot in meinen Favoriten verschwunden?",
						FontAwesome.QUESTION);

		final Label label_9 = new Label(
				"Um ein Anfrage senden zu können, müssen Sie sich zunächst als DH-Studentin / DH-Student verifizieren. \nKlicken Sie anschließend auf das gewünschte Angebot. Am Ende der Seite befindet sich ein Button \"Anfragen\", drücken Sie diesen. \nNun kommen Sie zum Anfrageformular. In dem Textfeld können Sie nun Ihre Anfrage formulieren und mit einem Klick auf den Button \"Anfrage versenden\" wird die Anfrage an den Anbieter weitergeleitet.");
		sub_accordion_2.addTab(label_9,
				"Wie kann ich eine Anfrage für ein Wohnungsangebot senden?",
				FontAwesome.QUESTION);

		final Label label_10 = new Label(
				"Hat der Anbieter eines Angebots genug Anfragen oder steht das Angebot aus anderen Gründen zur Zeit nicht zur Verfügung, \nhat der Anbieter die Möglichkeit das Angebot zu deaktivieren und zu einem späteren Zeitpunkt wieder zu aktivieren. \nJedoch kann während ein Angebot deaktiviert ist keine Anfrage gestellt werden.");
		sub_accordion_2
				.addTab(label_10,
						"Warum kann ich zu einem Wohnungsangebot keine Anfrage senden?",
						FontAwesome.QUESTION);

		final Label label_11 = new Label(
				"Wenn Sie zu einem Angebot bereits eine Anfrage gestellt haben, wird eine erneute Anfrage für das selbige Angebot nicht versendet. Da der Anbieter bereits eine Anfrage erhalten hat und wir damit sicher stellen wollen, dass die Anwendung nicht missbraucht wird.");
		sub_accordion_2.addTab(label_11,
				"Warum warum wird meine Anfrage nicht versendet?",
				FontAwesome.QUESTION);

		final Label label_12 = new Label(
				"Um zu sehen welche Angebot Sie bereits angefragt haben müssen Sie sich vorerst einloggen. Klicken sie auf die Registrierkarte \"Persönliche Daten\" und auf \"Meine Anfragen\". Nun erscheint eine Liste von Wohnungsangebote, die Sie bereits angefragt haben.");
		sub_accordion_2.addTab(label_12,
				"Woher weiß ich, welche Angebote ich bereits angefragt habe?",
				FontAwesome.QUESTION);

		layout_2.addComponent(sub_accordion_2);
		accordion.addTab(layout_2, "zu Wohnungsangebote allgemein",
				FontAwesome.THUMB_TACK);// Tab 3 hinzufügen

		// Tab 4
		final VerticalLayout layout_3 = new VerticalLayout();
		layout_3.setMargin(true);
		final Label label_13 = new Label("Sollten Ihre Fragen nicht beantwortet sein, wenden Sie sich bitte an uns:");
		//Link zum Fenster "Passwort vergessen"
		Button link_3 = new Button();
		link_3.setStyleName("link");
		link_3.setCaption("Kontakt");
		link_3.setImmediate(false);
		link_3.setWidth("-1px");
		link_3.setHeight("-1px");
		link_3.setIcon(FontAwesome.EXTERNAL_LINK);
		link_3.addClickListener(new Button.ClickListener(){
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				String name = "Kontaktformular";
				getUI().getNavigator().addView(name, new Kontaktformular());
				getUI().getNavigator().navigateTo(name);
			}
		});
		layout_3.addComponent(label_13);
		layout_3.addComponent(link_3);

		accordion.addTab(layout_3, "Mehr Hilfe?", FontAwesome.THUMB_TACK);// Tab 4 hinzufügen

		content.addComponent(accordion);// verschiedene Kategorien hinzufügen
	}
}
