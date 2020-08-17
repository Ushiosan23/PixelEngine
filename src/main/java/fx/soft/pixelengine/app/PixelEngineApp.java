package fx.soft.pixelengine.app;

import fx.soft.pixelengine.R;
import fx.soft.pixelengine.javafx.BaseApplication;
import fx.soft.pixelengine.javafx.utils.ScreenUtils;
import javafx.stage.Stage;

import java.util.PropertyResourceBundle;

/**
 * Pixel engine application class
 *
 * @see BaseApplication
 */
public class PixelEngineApp extends BaseApplication {
	
	/**
	 * Application method start <br/>
	 * Initialize application thread and configure main stage
	 *
	 * @param primaryStage main application stage
	 *
	 * @throws Exception error to load all resources
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Initialize properties
		current = this;
		currentStage = primaryStage;
		defaultBundle = new PropertyResourceBundle(R.defaultstrings.getInputStream());
		// Configure window stage
		currentStage.setResizable(false);
		// Set scene and icon
		changeScene(
			R.fxml_homescreen.getURL(),
			getLanguageBundleFrom("en", "en"),
			-1,
			-1,
			null
		);
		changeIcon(R.icons_icon.getInputStream());
		changeTitle(defaultBundle.getString("app.name"));
		// Set styles to scene
		addStylesheet(
			currentStage.getScene(),
			R.css_base.location
		);
		// Set size
		ScreenUtils.updateWindowSize(currentStage);
		// Last configuration
		currentStage.centerOnScreen();
		currentStage.show();
	}
	
}
