package fx.soft.pixelengine.utils.system;

import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * Path utilities
 */
public final class PathUtils {
	
	/**
	 * Regular expression to find all slashes
	 */
	public static final Pattern SLASH_FOUND = Pattern.compile("[/\\\\]");
	
	/**
	 * Get platform slash
	 */
	public static final String SLASH_REPLACE = Platform.getCurrent() == Platform.WINDOWS ? "\\\\" : "/";
	
	/**
	 * Replace all malformed slashes
	 *
	 * @param data target string to replace slashes
	 *
	 * @return {@link String} formatted
	 */
	private static String replaceSlashes(String data) {
		if (SLASH_FOUND.matcher(data).find())
			return SLASH_FOUND.matcher(data).replaceAll(SLASH_REPLACE);
		return data;
	}
	
	/**
	 * Join some path strings
	 *
	 * @param paths paths to join
	 *
	 * @return {@link String} with paths formatted
	 */
	public static String join(String... paths) {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < paths.length; i++) {
			String nextChar = (i + 1 >= paths.length) ? "" : paths[i + 1].substring(0, 1);
			String finalChar = paths[i].substring(paths[i].length() - 1);
			
			if (SLASH_FOUND.matcher(nextChar).find())
				if (SLASH_FOUND.matcher(finalChar).find())
					builder.append(paths[i], 0, paths[i].length() - 1);
				else
					builder.append(paths[i]);
			else
				builder.append(paths[i]).append(SLASH_REPLACE);
		}
		
		String result = replaceSlashes(builder.toString()).substring(0, builder.length() - 2);
		return Path.of(result, "").toString();
	}
	
}
