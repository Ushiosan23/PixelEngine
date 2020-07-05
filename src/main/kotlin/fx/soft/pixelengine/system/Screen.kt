package fx.soft.pixelengine.system

import java.awt.Dimension
import java.awt.GraphicsEnvironment
import java.awt.Insets
import java.awt.Toolkit

/**
 * Screen utilities.
 */
@Suppress("MemberVisibilityCanBePrivate")
object Screen {

	/**
	 * Save default toolkit.
	 *
	 * @see [Toolkit]
	 */
	private val toolkit: Toolkit = Toolkit.getDefaultToolkit()

	/**
	 * Get screen size.
	 *
	 * @return [Dimension]
	 */
	fun getSize(): Dimension = toolkit.screenSize

	/**
	 * Get screen resolution in dots-per-inch.
	 *
	 * @return [Int] Current screen resolution in dots-per-inch.
	 */
	fun getResolution(): Int = toolkit.screenResolution

	/**
	 * Get insets of screen.
	 *
	 * @return [Insets] get current insets (in pixels) of screen.
	 */
	fun getInsets(): Insets = toolkit.getScreenInsets(
		GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice.defaultConfiguration
	)

	/**
	 * Get Work area. Get only screen work area.
	 *
	 * @return [Dimension] get screen size without toolbars and taskbar size.
	 */
	fun getWorkAreaSize(): Dimension {
		val size = getSize()

		val insets = getInsets()
		val insetsD = Dimension(
			insets.left + insets.right,
			insets.top + insets.bottom
		)

		return Dimension(
			size.width - insetsD.width,
			size.height - insetsD.height
		)
	}

}