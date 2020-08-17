package fx.soft.pixelengine.javafx.utils;

import javafx.event.Event;
import org.jetbrains.annotations.NotNull;

public final class EventUtils {
	
	/**
	 * This class cannot be instance
	 */
	private EventUtils() {
	}
	
	/**
	 * Get event source
	 *
	 * @param event Emitted event
	 * @param <T>   Target class to cast
	 *
	 * @return {@link T} cast object or {@code null} if cast has an error
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSource(@NotNull Event event) {
		try {
			return (T) event.getSource();
		} catch (ClassCastException err) {
			err.printStackTrace();
			return null;
		}
	}
	
}
