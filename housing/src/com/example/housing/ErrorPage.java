package com.example.housing;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class Error Page.
 * 
 * @author MWI Wohungsbörse 2014
 * @version 1.0
 * @see com.example.housing.HousingUI
 */
@SuppressWarnings("serial")
public class ErrorPage extends CustomHorizontalLayout implements View {

	/** The content. */
	private VerticalLayout content;// Layout fuer den Inhalt

	/** The title. */
	private Label titleLabel;

	/** The text. */
	private Label errorTextLabel;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener
	 * .ViewChangeEvent)
	 */
	@Override
	public void enter(ViewChangeEvent event) {

	}

	/**
	 * Instantiates a new ErrorPage.
	 */
	public ErrorPage() {
		content = super.initCustomHorizontalLayout();
		setContent();
	}

	/**
	 * Sets the Content of the page.
	 */
	public void setContent() {

		titleLabel = new Label();
		titleLabel.setImmediate(false);
		titleLabel.setWidth("-1px");
		titleLabel.setHeight("-1px");
		titleLabel.setValue("Es ist ein Fehler aufgetreten.");
		titleLabel.addStyleName("title");
		content.addComponent(titleLabel);

		errorTextLabel = new Label();
		errorTextLabel.setImmediate(false);
		errorTextLabel.setWidth("-1px");
		errorTextLabel.setHeight("500px");
		errorTextLabel
				.setValue("Das tut uns leid und hätte nicht passieren dürfen!<br/>"
						+ "<br/><br/> Der Fehler kann unter anderem folgende Ursachen haben:"
						+ "<br/><br/> - Sie wollten zu einer nicht verfügbaren Seite navigieren. Beispielsweise können Sie die Profilseite nur aufrufen, wenn Sie eingeloggt sind."
						+ "<br/><br/> - Das Hochladen eines Bildes hat den Fehler verursacht. Bitte versuchen Sie es später erneut."
						+ "<br/><br/> - Es gab einen Serverfehler. Sollte der Fehler häufiger auftreten, wenden Sie sich bitte an den Administrator.");
		errorTextLabel.setContentMode(ContentMode.HTML);
		content.addComponent(errorTextLabel);
	}
}
