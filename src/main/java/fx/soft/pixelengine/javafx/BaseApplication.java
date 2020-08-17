package fx.soft.pixelengine.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Base application model class
 */
public abstract class BaseApplication extends Application {
	
	/**
	 * Application instance object
	 */
	public static BaseApplication current;
	
	/**
	 * Application window stage
	 */
	public Stage currentStage;
	
	/**
	 * Save default bundle resource
	 */
	public PropertyResourceBundle defaultBundle;
	
	/**
	 * Change stage icon
	 *
	 * @param image Target stage icon
	 */
	public void changeIcon(Image image) {
		if (image == null)
			return;
		
		// Clear all icons
		currentStage.getIcons().clear();
		currentStage.getIcons().add(image);
	}
	
	/**
	 * Change stage icon from stream
	 *
	 * @param imageStream Target stage icon
	 */
	public void changeIcon(InputStream imageStream) {
		if (imageStream == null)
			return;
		
		changeIcon(new Image(imageStream));
	}
	
	/**
	 * Change stage title
	 *
	 * @param title Target title stage
	 */
	public void changeTitle(@NotNull String title) {
		currentStage.setTitle(title);
	}
	
	/**
	 * Change stage scene from url location
	 *
	 * @param location Scene url location
	 * @param bundle   Resource bundle object
	 * @param width    Scene width
	 * @param height   Scene height
	 * @param fill     Scene fill
	 *
	 * @throws IOException error if scene not exists
	 */
	public void changeScene(
		@NotNull URL location,
		@Nullable ResourceBundle bundle,
		double width,
		double height,
		Paint fill
	) throws IOException {
		Parent root = FXMLLoader.load(location, bundle);
		Scene scene = new Scene(root, width, height, fill);
		currentStage.setScene(scene);
	}
	
	/**
	 * Change stage scene from url location
	 *
	 * @param location Scene url location
	 * @param bundle   Resource bundle object
	 *
	 * @throws IOException error if scene not exists
	 */
	public void changeScene(
		@NotNull URL location,
		@Nullable ResourceBundle bundle
	) throws IOException {
		changeScene(location, bundle, -1, -1, null);
	}
	
	/**
	 * Change stage scene from url location
	 *
	 * @param location Scene url location
	 * @param width    Scene width
	 * @param height   Scene height
	 * @param fill     Scene fill
	 *
	 * @throws IOException error if scene not exists
	 */
	public void changeScene(
		@NotNull URL location,
		double width,
		double height,
		Paint fill
	) throws IOException {
		changeScene(location, null, width, height, fill);
	}
	
	/**
	 * Change stage scene from url location
	 *
	 * @param location Scene url location
	 * @param width    Scene width
	 * @param height   Scene height
	 *
	 * @throws IOException error if scene not exists
	 */
	public void changeScene(
		@NotNull URL location,
		double width,
		double height
	) throws IOException {
		changeScene(location, null, width, height, null);
	}
	
	/**
	 * Call method in JavaFX thread
	 *
	 * @param runnable Action to run
	 *
	 * @see Platform#runLater(Runnable)
	 */
	public void runLater(Runnable runnable) {
		Platform.runLater(runnable);
	}
	
	/**
	 * Create stage owner from main stage.
	 *
	 * @return {@link Stage} attached to main stage
	 */
	public Stage createStage() {
		final Stage resultStage = new Stage();
		
		if (currentStage != null) {
			resultStage.initOwner(currentStage);
			if (currentStage.getIcons().size() != 0)
				resultStage.getIcons().add(currentStage.getIcons().get(0));
		}
		
		return resultStage;
	}
	
	/**
	 * Get resource bundle from location
	 *
	 * @param locale   Target locale language
	 * @param location Target properties location
	 *
	 * @return {@link ResourceBundle} with data
	 */
	@Nullable
	public ResourceBundle getLanguageBundleFrom(Locale locale, String location) {
		try {
			String targetLocation = "i18n/";
			targetLocation += location;
			return ResourceBundle.getBundle(targetLocation, locale);
		} catch (Exception err) {
			err.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Get resource bundle from location
	 *
	 * @param locale   Target language bundle
	 * @param location Target properties location
	 *
	 * @return {@link ResourceBundle} with data
	 */
	public ResourceBundle getLanguageBundleFrom(String locale, String location) {
		Locale l = new Locale(locale);
		return getLanguageBundleFrom(l, location);
	}
	
	/**
	 * Set scene stylesheets
	 *
	 * @param targetScene Scene to add stylesheets
	 * @param styles      All styles to add
	 */
	public void addStylesheet(Scene targetScene, String... styles) {
		targetScene.getStylesheets().addAll(styles);
	}
	
	/**
	 * Remove all selected stylesheets
	 *
	 * @param targetScene Scene to remove stylesheets
	 * @param styles      All styles to remove
	 */
	public void removeStylesheet(Scene targetScene, String... styles) {
		targetScene.getStylesheets().removeAll(styles);
	}
	
}
