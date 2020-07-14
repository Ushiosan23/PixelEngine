import fx.soft.pixelengine.i18n.Language
import org.junit.Before
import org.junit.Test
import java.io.File

class LanguageTest : ITest {

	private lateinit var jsonLang: Language

	@Before
	override fun initialize() {
		val jsonUrl = R.resource("jsonLang.json")
		jsonLang = Language(File(jsonUrl.toURI()))
	}

	@Test
	override fun runTest() {
		println(jsonLang)
	}

}