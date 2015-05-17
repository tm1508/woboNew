package com.example.housing;

import com.example.housing.data.model.User;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ErrorPage extends CustomHorizontalLayout implements View {
	private static final long serialVersionUID = 1L;

	private VerticalLayout content;// Layout fuer den Inhalt
	private Label titleLabel;
	private Label errorTextLabel;
	private com.vaadin.server.ErrorEvent error;

	@Override
	public void enter(ViewChangeEvent event) {
	}

	public ErrorPage(com.vaadin.server.ErrorEvent error) {
		this.error = error;
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
				.setValue("Das tut uns leid und hätte nicht passieren dürfen!"
						+ "<br/><br/> Der Fehler kann unter anderem folgende Ursachen haben:"
						+ "<br/>- Sie wollten zu einer nicht verfügbaren Seite navigieren. Beispielsweise können Sie die Profilseite nur aufrufen, wenn Sie eingeloggt sind."
						+ "<br/>- Das Hochladen eines Bildes hat den Fehler verursacht. Bitte versuchen Sie es später erneut."
						+ "<br/>- Es gab einen Serverfehler. Sollte der Fehler häufiger auftreten, wenden Sie sich bitte an den Administrator.");
		errorTextLabel.setContentMode(ContentMode.HTML);
		content.addComponent(errorTextLabel);
		
		error.getThrowable().printStackTrace();
		
		if(((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getAccessLevel()==2) {
			content.addComponent(new Label());
			content.addComponent(new Label("Technische Fehlermeldung:"));
			String errorStackTrace = error.getThrowable().getStackTrace().toString();
			content.addComponent(new Label(errorStackTrace));
		}
	}
}
