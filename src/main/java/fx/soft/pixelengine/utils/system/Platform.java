package fx.soft.pixelengine.utils.system;

import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * Platform enumerator <br/>
 * This class is used to detect OS Platform
 */
public enum Platform {
	WINDOWS("^(win|windows)"),
	LINUX("(nix|nux|linux)"),
	MACOS("(mac|unix|darwin)"),
	SOLARIS("(sol|ris|solaris)"),
	UNKNOWN(null);
	
	/**
	 * Regular expression
	 */
	public final Pattern regex;
	
	/**
	 * Enumerator constructor
	 *
	 * @param pattern target platform pattern
	 */
	Platform(@Nullable String pattern) {
		regex = pattern == null ? null : Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
	}
	
	/**
	 * Get current platform OS
	 *
	 * @return {@link Platform} OS enumerator
	 */
	public static Platform getCurrent() {
		String platformStr = System.getProperty("os.name").toLowerCase();
		
		
		for (Platform platform : values()) {
			if (platform.regex != null && platform.regex.matcher(platformStr).find())
				return platform;
		}
		
		return UNKNOWN;
	}
	
}
