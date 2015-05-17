package com.example.housing;

import java.util.List;

import com.example.housing.data.model.User;
import com.example.housing.data.provider.UserProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;

public class UserProfil extends CustomHorizontalLayout implements View {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout content;
	private User u;
	
	private Label id;
	private Label prename;
	private Label lastname;
	private Label email;
	private Label accessLevel;

	public UserProfil(User u) {
		this.u = u;
		
		content = super.initCustomHorizontalLayout();
		setContent();
	}

	@Override
	public void setContent() {
		
		// title
		Label title = new Label();
		title.setImmediate(false);
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setValue("User-Profil, ID: " + u.getIdUser());
		title.addStyleName("title");
		content.addComponent(title);
		
		GridLayout gridProfile = new GridLayout(2,5);
		
		HorizontalLayout name = new HorizontalLayout();

		//id
		id = new Label();
		id.setValue(u.getIdUser().toString());
		gridProfile.addComponent(new Label("User-ID: "), 0, 0);
		gridProfile.addComponent(id, 1, 0);
		
		// prename
		prename = new Label();
		prename.setValue(u.getFirstname());
		gridProfile.addComponent(new Label("Vorname: "), 0, 1);
		gridProfile.addComponent(prename, 1, 1);

		// lastname
		lastname = new Label();
		lastname.setValue(u.getLastname());
		gridProfile.addComponent(new Label("Nachname: "), 0, 2);
		gridProfile.addComponent(lastname, 1, 2);
		
		// email
		email = new Label();
		email.setValue(u.getEmail());
		gridProfile.addComponent(new Label("E-Mail: "), 0, 3);
		gridProfile.addComponent(email, 1, 3);
		
		//accessLevel
		accessLevel = new Label();
		String level = "";
		switch(u.getAccessLevel()) {
			case 0:
				level = "Normaler User";
				break;
			case 1:
				level = "DH-Student";
				break;
			case 2:
				level = "Administrator";
				break;
		}
		accessLevel.setValue(level);
		gridProfile.addComponent(new Label("Berechtigungslevel: "), 0, 4);
		gridProfile.addComponent(accessLevel, 1, 4);
		
		VerticalLayout buttonZeilen = new VerticalLayout();
		
		HorizontalLayout buttonSpalten = new HorizontalLayout();
		
		Button dhStud = new Button();
		dhStud.setStyleName("BearbeitenButton");
		dhStud.setImmediate(true);
		dhStud.setDescription("Das Berechtigungslevel dieses Users auf DH-Student setzen.");
		dhStud.setWidth("-1px");
		dhStud.setHeight("-1px");
		dhStud.setVisible(true);
		dhStud.setIcon(FontAwesome.COGS);
		if(u.getAccessLevel() == 0) {
			dhStud.setCaption("Als DH-Student freischalten");
			dhStud.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					
					u.setAccessLevel(1);
					new UserProvider().alterUser(u);
					
					Notification notif = new Notification("Der User ist nun als DH-Student freigeschaltet!", Type.HUMANIZED_MESSAGE);
					notif.setDelayMsec(300);
					notif.setStyleName("success");
					notif.show(Page.getCurrent());
					
					User uNew = new UserProvider().findById(u.getIdUser());
					
					String name = "UserProfil";
					getUI().getNavigator().addView(name, new UserProfil(uNew));
					getUI().getNavigator().navigateTo(name);
					
				}
			});
		} else if (u.getAccessLevel() == 1) {
			dhStud.setCaption("DH-Studenten-Level widerrufen");
			dhStud.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					
					u.setAccessLevel(0);
					new UserProvider().alterUser(u);
					
					Notification notif = new Notification("Der User ist nicht mehr als DH-Student freigeschaltet!", Type.HUMANIZED_MESSAGE);
					notif.setDelayMsec(300);
					notif.setStyleName("success");
					notif.show(Page.getCurrent());
					
					User uNew = new UserProvider().findById(u.getIdUser());
					
					String name = "UserProfil";
					getUI().getNavigator().addView(name, new UserProfil(uNew));
					getUI().getNavigator().navigateTo(name);
					
				}
			});
		} else {
			dhStud.setCaption("Als DH-Student freischalten");
			dhStud.setEnabled(false);
		}
		buttonZeilen.addComponent(dhStud);
		
		Button contact = new Button();
		contact.setStyleName("BearbeitenButton");
		contact.setCaption("User kontaktieren");
		contact.setImmediate(true);
		contact.setDescription("Diesen User über ein Formular kontaktieren.");
		contact.setWidth("-1px");
		contact.setHeight("-1px");
		contact.setVisible(true);
		contact.setIcon(FontAwesome.MAIL_FORWARD);
		contact.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				String name = "AnfrageUser";
				getUI().getNavigator().addView(name, new AdminAnfrageUser(u));
				getUI().getNavigator().navigateTo(name);
				
			}
		});
		buttonSpalten.addComponent(contact);
		
		Button delete = new Button();
		delete.setStyleName("loeschen");
		delete.setCaption("User löschen");
		delete.setImmediate(true);
		delete.setDescription("Der User sowie alle seine Angebote und anderen Daten werden aus der Datenbank gelöscht.");
		delete.setWidth("-1px");
		delete.setHeight("-1px");
		delete.setVisible(true);
		delete.setIcon(FontAwesome.TRASH_O);
		delete.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				CheckWindow w = new CheckWindow();// Wollen Sie Ihr Profil
													// wirklich löschen?
				UI.getCurrent().addWindow(w);// neues Fenster hinzufügen
			}

			// Check Window
			class CheckWindow extends Window {

				public CheckWindow() {
					super("Wollen Sie diesen User wirklich löschen?");
					this.center();
					this.setHeight("50%");
					this.setWidth("30%");

					// Layout
					final VerticalLayout content = new VerticalLayout();
					content.setMargin(true);

					// Hinweistext
					Label l = new Label(
							"Wenn Sie den User löschen, werden alle seine Daten gelöscht (inklusive seiner angebotenen Wohnungen)!");
					content.addComponent(l);

					// Button "Ja"
					Button yes = new Button();
					yes.setStyleName("loeschen");
					yes.setCaption("Ja, diesen User löschen.");
					yes.setDescription("User löschen");
					yes.setIcon(FontAwesome.CHECK);
					yes.setWidth("-1px");
					yes.setHeight("-1px");
					content.addComponent(yes);
					yes.addClickListener(new Button.ClickListener() {
						public void buttonClick(ClickEvent event) {
							
							new UserProvider().removeUser(u);// User in der DB
																// löschen

							List<User> users = new UserProvider().getAllUsers();
							// Navigation zur Startseite
							String name = "UserListe";
							getUI().getNavigator().addView(name, new UserListe(users));
							getUI().getNavigator().navigateTo(name);

							// dieses Fenster schließen
							CheckWindow.this.close();

							// Meldung an den Nutzer
							Notification notif = new Notification("Der User wurde gelöscht!", Type.HUMANIZED_MESSAGE);
							notif.setDelayMsec(300);
							notif.setStyleName("success");
							notif.show(Page.getCurrent());
						}
					});

					// Abbrechen-Button
					Button no = new Button();
					no.setStyleName("loeschen");
					no.setCaption("Nein, nicht löschen.");
					no.setIcon(FontAwesome.MAIL_REPLY);
					no.setDescription("Diesen User nicht löschen");
					no.setWidth("-1px");
					no.setHeight("-1px");
					content.addComponent(no);
					no.addClickListener(new Button.ClickListener() {
						public void buttonClick(ClickEvent event) {
							CheckWindow.this.close();// Fenster schließen
						}
					});

					this.setContent(content);
				}

			}
		});
		
		buttonSpalten.addComponent(delete);
		
		buttonZeilen.addComponent(buttonSpalten);
		content.addComponent(buttonZeilen);
	}
	
	private void daten() {
		prename.setValue(((User) VaadinSession.getCurrent().getSession().getAttribute("user")).getFirstname());
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
