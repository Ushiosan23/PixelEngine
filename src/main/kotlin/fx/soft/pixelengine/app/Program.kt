package fx.soft.pixelengine.app

import fx.soft.pixelengine.ui.app.ApplicationBase
import java.awt.Dimension
import javax.swing.JFrame

class Program(args: Array<String>) : ApplicationBase(args) {

	override fun onLaunch(frame: JFrame) {
		frame.size = Dimension(500, 500)
		frame.isVisible = true
	}

}