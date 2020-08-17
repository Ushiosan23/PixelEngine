package fx.soft.pixelengine.javafx.controls;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class FXButtonResource extends Button {
	
	/**
	 * Creates a button with an empty string for its label.
	 */
	public FXButtonResource() {
		super();
	}
	
	/**
	 * Creates a button with the specified text as its label.
	 *
	 * @param text A text string for its label.
	 */
	public FXButtonResource(String text) {
		super(text);
	}
	
	/**
	 * Creates a button with the specified text and icon for its label.
	 *
	 * @param text    A text string for its label.
	 * @param graphic the icon for its label.
	 */
	public FXButtonResource(String text, Node graphic) {
		super(text, graphic);
	}
	
	/**
	 * Resource location
	 */
	private StringProperty resource;
	
	/**
	 * Get resource property
	 *
	 * @return Property with value
	 */
	public StringProperty getResourceProperty() {
		if (resource == null)
			resource = new SimpleStringProperty("");
		
		return resource;
	}
	
	/**
	 * Set button resource
	 *
	 * @param resource Target resource to load
	 */
	public void setResource(String resource) {
		getResourceProperty().setValue(resource);
	}
	
	/**
	 * Get string resource
	 *
	 * @return {@link String} resource
	 */
	public String getResource() {
		return getResourceProperty().get();
	}
	
}
