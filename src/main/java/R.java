import fx.soft.pixelengine.system.OS;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public final class R {

	public static final AppResource languages = new AppResource("i18n");
	public static final AppResource baseConfig = new AppResource("baseConfig.xml");

	/**
	 * Get all language resource.
	 *
	 * @return list with language resources.
	 */
	@NotNull
	public static AppResource[] getLanguageResources() {
		Regex regex = new Regex("strings\\.(xml|json)");
		List<URL> urlLanguages = OS.Path.INSTANCE.walkURL(languages.url(), regex, false);
		AppResource[] result = new AppResource[urlLanguages.size()];

		for (int i = 0; i < urlLanguages.size(); i++) {
			result[i] = new AppResource(urlLanguages.get(i));
		}

		return result;
	}

	/**
	 * Internal method to get resource url
	 *
	 * @param resource resource name
	 * @return {@link URL} with data
	 */
	public static URL resource(String resource) {
		return ClassLoader.getSystemClassLoader().getResource(resource);
	}

	/**
	 * Internal method to get resource stream
	 *
	 * @param resource resource name
	 * @return {@link InputStream} with data
	 */
	public static InputStream resourceStream(String resource) {
		return ClassLoader.getSystemClassLoader().getResourceAsStream(resource);
	}

	/**
	 * Resource class
	 */
	public static final class AppResource {

		/**
		 * Save resource name
		 */
		private final String resName;

		/**
		 * Save url resource
		 */
		private URL url = null;

		/**
		 * AppResource class constructor
		 *
		 * @param name resource path
		 */
		protected AppResource(String name) {
			this.resName = name;
		}

		/**
		 * Second constructor.
		 *
		 * @param resource target resource to read
		 */
		protected AppResource(@NotNull URL resource) {
			this(resource.getPath());
			this.url = resource;
		}

		/**
		 * Check if resource exists
		 *
		 * @return {@link Boolean} with exists result.
		 */
		public boolean exists() {
			return url() != null;
		}

		/**
		 * Get resource stream.
		 *
		 * @return {@link InputStream} resource file or null if not exists
		 * Check console for error information
		 */
		@Nullable
		public InputStream stream() {
			try {
				if (url != null)
					return url.openStream();
				else
					return resourceStream(resName);
			} catch (Exception err) {
				err.printStackTrace();
			}
			return null;
		}

		/**
		 * Get resource url
		 *
		 * @return {@link URL} resource file or null if not exists
		 */
		public URL url() {
			if (url != null)
				return url;
			else
				return resource(resName);
		}

	}

}
