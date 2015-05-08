package com.example.housing;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Button.ClickEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class Footer.
 */
public class Footer extends CustomComponent {
	private static final long serialVersionUID = 1L;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	/** The main layout. */
	@AutoGenerated
	private HorizontalLayout mainLayout;
	
	@AutoGenerated
	private Link dhbwHomepageLink;
	
	@AutoGenerated
	private Label copyrightLabel;
	
	@AutoGenerated
	private Button kontaktformularLink;
	
	@AutoGenerated
	private Link link_2;
	
	@AutoGenerated
	private Button impressumLink;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public Footer() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		mainLayout.setStyleName("footer");
	}
	
	/**
	 * Builds the main layout.
	 *
	 * @return the horizontal layout
	 */
	@AutoGenerated
	private HorizontalLayout buildMainLayout() {
		mainLayout = new HorizontalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		
		copyrightLabel = new Label();
		copyrightLabel.setImmediate(false);
		copyrightLabel.setWidth("-1px");
		copyrightLabel.setHeight("-1px");
		copyrightLabel.setValue("� DHBW Karlsruhe");
		copyrightLabel.setStyleName("footer");
		copyrightLabel.setSizeFull();
		mainLayout.addComponent(copyrightLabel);
		
		dhbwHomepageLink = new Link("DHBW Karlsruhe", new ExternalResource("https://www.dhbw-karlsruhe.de"));
		dhbwHomepageLink.setDescription("Internetseite der DHBW Karlsruhe");
		dhbwHomepageLink.setImmediate(false);
		dhbwHomepageLink.setWidth("-1px");
		dhbwHomepageLink.setHeight("15px");
		dhbwHomepageLink.setStyleName("footer");
		mainLayout.addComponent(dhbwHomepageLink);
		
		impressumLink = new Button();
		impressumLink.setCaption("Impressum");
		impressumLink.setStyleName("link");
		impressumLink.setImmediate(false);
		impressumLink.setWidth("-1px");
		impressumLink.setHeight("-1px");
		impressumLink.addClickListener(new Button.ClickListener(){
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				String name = "Impressum";
				getUI().getNavigator().addView(name, new Impressum());
				getUI().getNavigator().navigateTo(name);
			}
		});
		mainLayout.addComponent(impressumLink);
		
		kontaktformularLink = new Button();
		kontaktformularLink.setStyleName("link");
		kontaktformularLink.setCaption("Kontakt");
		kontaktformularLink.setImmediate(false);
		kontaktformularLink.setWidth("-1px");
		kontaktformularLink.setHeight("-1px");
		kontaktformularLink.addClickListener(new Button.ClickListener(){
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				String name = "Kontaktformular";
				getUI().getNavigator().addView(name, new Kontaktformular());
				getUI().getNavigator().navigateTo(name);
			}
		});
		mainLayout.addComponent(kontaktformularLink);

		return mainLayout;
	}
}
