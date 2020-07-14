package fx.soft.pixelengine.ui.app

import java.awt.Dimension
import javax.swing.JFrame

/**
 * Create application interface
 */
interface IApplication {

	/**
	 * Called when application is ready to show.
	 *
	 * @param frame Application window
	 */
	fun onLaunch(frame: JFrame)

	/**
	 * Called when application is closed.
	 */
	fun onClose()

	/**
	 * Called when window is resized
	 */
	fun onResize(dimension: Dimension)

}