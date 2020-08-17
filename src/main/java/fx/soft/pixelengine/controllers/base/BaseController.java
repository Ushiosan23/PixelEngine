package fx.soft.pixelengine.controllers.base;

import fx.soft.pixelengine.javafx.BaseApplication;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class BaseController implements Initializable {
	
	/**
	 * Save current application context
	 */
	protected BaseApplication context;
	
	/**
	 * Save controller location
	 */
	protected URL location;
	
	/**
	 * Save resource bundle controller
	 */
	protected ResourceBundle bundle;
	
	/**
	 * Called to initialize a controller after its root elements has been completely processed
	 *
	 * @param location  view location
	 * @param resources bundle resources (internationalization)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.context = BaseApplication.current;
		this.location = location;
		this.bundle = resources;
		lateInit();
	}
	
	/**
	 * Called after initialize object
	 */
	protected abstract void lateInit();
	
}
