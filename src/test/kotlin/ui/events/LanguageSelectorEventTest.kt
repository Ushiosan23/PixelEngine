package ui.events

import ITest
import fx.soft.pixelengine.i18n.LanguageSelector
import fx.soft.pixelengine.ui.events.LanguageSelectorEvent
import fx.soft.pixelengine.ui.events.LanguageSelectorListener
import org.junit.Before
import org.junit.Test
import java.awt.Dimension
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel

class LanguageSelectorEventTest : ITest, LanguageSelectorListener {

	private lateinit var languages: LanguageSelector
	private lateinit var window: JFrame

	override fun languageChanged(event: LanguageSelectorEvent) {
		println(event)
	}

	@Before
	override fun initialize() {
		languages = LanguageSelector(R.getLanguageResources().toList())
		languages.addEventListener(this)

		window = JFrame("Example Language event")
	}

	@Test
	override fun runTest() {
		window.contentPane = JPanel()
		window.size = Dimension(400, 400)
		window.setLocationRelativeTo(null)

		val button = JButton("Click me")
		button.addActionListener {
			languages.currentLanguage = "es"
		}

		window.contentPane.add(button)
		window.isVisible = true
		Thread.sleep(5000)
	}

}