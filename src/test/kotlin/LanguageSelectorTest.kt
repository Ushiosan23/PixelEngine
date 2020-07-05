import fx.soft.pixelengine.i18n.LanguageSelector
import org.junit.Test

class LanguageSelectorTest : ITest {

	@Test
	override fun runTest() {
		val allLanguages = R.getLanguageResources()
		val languageSelector = LanguageSelector(allLanguages.toList())


		println(languageSelector)
		println(languageSelector["verbose_name"])
		languageSelector.currentLanguage = "es"
		println(languageSelector["verbose_name"])
	}

}