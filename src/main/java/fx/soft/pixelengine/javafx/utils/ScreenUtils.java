package fx.soft.pixelengine.javafx.utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Utilities for {@link Screen} class
 */
public final class ScreenUtils {
	
	/**
	 * This class cannot be instantiated, it's used for utilities only
	 */
	private ScreenUtils() {
	}
	
	/**
	 * Get primary screen
	 *
	 * @return primary {@link Screen} object
	 */
	public static Screen getPrimary() {
		return Screen.getPrimary();
	}
	
	/**
	 * Get primary screen size
	 *
	 * @return {@link Rectangle2D} with screen size information
	 *
	 * @see Screen#getBounds()
	 */
	public static Rectangle2D getPrimaryBounds() {
		return getPrimary().getBounds();
	}
	
	/**
	 * Get primary screen visual size (remove taskbar and toolbar size elements)
	 *
	 * @return {@link Rectangle2D} with screen visible size information
	 *
	 * @see Screen#getVisualBounds()
	 */
	public static Rectangle2D getPrimaryVisualBounds() {
		return getPrimary().getVisualBounds();
	}
	
	/**
	 * Get screen DPI
	 *
	 * @return screen DPI
	 */
	public static double getPrimaryDPI() {
		return getPrimary().getDpi();
	}
	
	/**
	 * Get best fit window size
	 *
	 * @return window target size
	 */
	public static Rectangle2D getBestWindowSize() {
		Rectangle2D visualB = getPrimaryVisualBounds();
		
		double fWidth = visualB.getWidth() * 80 / 100;
		double fHeight = fWidth / (16f / 10f);
		
		return new Rectangle2D(visualB.getMinX(), visualB.getMinY(), fWidth, fHeight);
	}
	
	/**
	 * Resize window to best size
	 *
	 * @param stage window to set size
	 */
	public static void updateWindowSize(Stage stage) {
		Rectangle2D rectangle = getBestWindowSize();
		
		stage.setWidth(rectangle.getWidth());
		stage.setHeight(rectangle.getHeight());
	}
	
}
