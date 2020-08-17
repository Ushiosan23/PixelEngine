// This file is generated automatically.
package fx.soft.pixelengine;

import java.net.URL;
import java.io.InputStream;
import org.jetbrains.annotations.NotNull;

/**
 * Resource class manager
 */
public final class R {

	// Resource location defaultStrings.properties
	public static final Resource defaultstrings = new Resource("defaultStrings.properties");

	// Resource location css/base.css
	public static final Resource css_base = new Resource("css/base.css");

	// Resource location fxml/HomeScreen.fxml
	public static final Resource fxml_homescreen = new Resource("fxml/HomeScreen.fxml");

	// Resource location fxml/dialogs/ProjectDialogLocation.fxml
	public static final Resource fxml_dialogs_projectdialoglocation = new Resource("fxml/dialogs/ProjectDialogLocation.fxml");

	// Resource location fxml/imports/HomeLogo.fxml
	public static final Resource fxml_imports_homelogo = new Resource("fxml/imports/HomeLogo.fxml");

	// Resource location fxml/sections/home/Discover.fxml
	public static final Resource fxml_sections_home_discover = new Resource("fxml/sections/home/Discover.fxml");

	// Resource location fxml/sections/home/Examples.fxml
	public static final Resource fxml_sections_home_examples = new Resource("fxml/sections/home/Examples.fxml");

	// Resource location fxml/sections/home/Home.fxml
	public static final Resource fxml_sections_home_home = new Resource("fxml/sections/home/Home.fxml");

	// Resource location fxml/sections/home/Learn.fxml
	public static final Resource fxml_sections_home_learn = new Resource("fxml/sections/home/Learn.fxml");

	// Resource location i18n/en.properties
	public static final Resource i18n_en = new Resource("i18n/en.properties");

	// Resource location i18n/es.properties
	public static final Resource i18n_es = new Resource("i18n/es.properties");

	// Resource location icons/icon.png
	public static final Resource icons_icon = new Resource("icons/icon.png");

	/**
	 * Resource class used to manage resource files
	 */
	public static class Resource {

		/**
		 * Resource location
		 */
		public final String location;

		/**
		 * Resource class loader
		 */
		private final ClassLoader classLoader;

		/**
		 * Resource constructor
		 * 
		 * @param name relative resource path
		 */
		public Resource(@NotNull String name) {
			location = name;
			classLoader = ClassLoader.getSystemClassLoader();
		}

		/**
		 * Get resource url location
		 * 
		 * @return {@link URL} resource url object
		 */
		public URL getURL() {
			return classLoader.getResource(location);
		}

		/**
		 * Get resource input stream
		 * 
		 * @return {@link InputStream} resource stream
		 */
		public InputStream getInputStream() {
			try {
				return classLoader.getResourceAsStream(location);
			} catch (Exception err) {
				return null;
			}
		}

	}

}
