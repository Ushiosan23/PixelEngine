package fx.soft.pixelengine.ui.app

import fx.soft.pixelengine.config.AppArguments
import java.awt.Dimension
import java.awt.Window
import java.awt.event.*
import javax.swing.JFrame
import javax.swing.WindowConstants
import kotlin.system.exitProcess

/**
 * Base application class
 */
abstract class ApplicationBase protected constructor(args: Array<String>) : IApplication {

	/**
	 * Current application window
	 */
	var currentWindow: JFrame = JFrame()
		protected set

	/**
	 * Application launch arguments
	 */
	var appRawArgs: Array<String> = args
		private set

	/**
	 * Program arguments
	 */
	var appArgs: Map<String, String> = AppArguments.parsedFromArgs(appRawArgs)

	/**
	 * Initialize object class
	 */
	init {
		currentWindow.defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
		currentWindow.addWindowListener(windowListener())
		currentWindow.addComponentListener(componentListener())
	}

	/**
	 * Called when application closed
	 */
	override fun onClose() {
		currentWindow.dispose()
		exitProcess(0)
	}

	/**
	 * Called when application window is resized
	 *
	 * @param dimension new window dimension
	 */
	override fun onResize(dimension: Dimension) {}

	/**
	 * Window Event
	 */
	private fun windowListener() = object : WindowAdapter() {

		/**
		 * Called when user click in close icon or any type of close window
		 */
		override fun windowClosing(e: WindowEvent?) {
			onClose()
		}

	}

	/**
	 * Window component listener
	 */
	private fun componentListener() = object : ComponentAdapter() {

		/**
		 * Called when window is resized
		 */
		override fun componentResized(e: ComponentEvent) {
			onResize((e.source as Window).size)
		}

	}

	/**
	 * Companion object
	 */
	companion object {

		/**
		 * Launch application with arguments
		 */
		inline fun <reified T : ApplicationBase> launch(args: Array<String>) {
			val app = T::class.constructors.first().call(args)
			app.onLaunch(app.currentWindow)
		}

	}
}