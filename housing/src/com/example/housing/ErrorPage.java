package com.example.housing;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ErrorPage extends CustomHorizontalLayout implements View {
	private static final long serialVersionUID = 1L;

	private VerticalLayout content;// Layout fuer den Inhalt
	private Label titleLabel;
	private Label errorTextLabel;

	@Override
	public void enter(ViewChangeEvent event) {
	}

	public ErrorPage() {
		content = super.initCustomHorizontalLayout();
		setContent();
	}

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
				.setValue("Das tut uns leid und h�tte nicht passieren d�rfen!<br/>"
						+ "<br/><br/> Der Fehler kann unter anderem folgende Ursachen haben:"
						+ "<br/><br/> -   Sie wollten zu einer nicht verf�gbaren Seite navigieren. Beispielsweise k�nnen Sie die Profilseite nur aufrufen, wenn Sie eingeloggt sind."
						+ "<br/><br/> -   Das Hochladen eines Bildes hat den Fehler verursacht. Bitte versuchen Sie es sp�ter erneut."
						+ "<br/><br/> -   Es gab einen Serverfehler. Sollte der Fehler h�ufiger auftreten, wenden Sie sich bitte an den Administrator.");
		errorTextLabel.setContentMode(ContentMode.HTML);
		content.addComponent(errorTextLabel);
	}
}
