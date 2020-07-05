package fx.soft.pixelengine.i18n.parsed

import fx.soft.pixelengine.debug.printErr
import fx.soft.pixelengine.i18n.TranslationData
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.Exception

/**
 * XMLLanguageReader used to parse xml file language.
 */
class XMLLanguageReader(stream: InputStream) : LanguageReader(stream) {

	/**
	 * Get language name
	 */
	override lateinit var name: String

	/**
	 * Save all node list
	 */
	private var document: Document

	/**
	 * Initialize object
	 */
	init {
		val reader = DocumentBuilderFactory.newInstance()
		val builder = reader.newDocumentBuilder()

		document = builder.parse(stream)
	}

	/**
	 * Create and get all raw translations
	 */
	override fun getRawTranslations(): Map<String, TranslationData> {
		val root = document.documentElement
		val nameCode = root.getAttribute("name")
		val verboseName = root.getAttribute("verbose-name")
		val map = mutableMapOf<String, TranslationData>()

		map["name"] = TranslationData.createTranslation(nameCode)
		map["verbose_name"] = TranslationData.createTranslation(verboseName)

		val nodeTranslations = root.getElementsByTagName("string")
		for (i in 0 until nodeTranslations.length) {
			parseTranslation(nodeTranslations.item(i))?.run {
				map[this.first] = this.second
			}
		}

		return map
	}

	/**
	 * Parse node translation
	 *
	 * @param node to translate
	 *
	 * @return [Pair] with translation data
	 */
	private fun parseTranslation(node: Node): Pair<String, TranslationData>? {
		if (!node.hasAttributes()) {
			printErr(Exception("Node have not any attributes."))
			return null
		}

		val elementNode = node as Element
		val name = elementNode.getAttribute("name")
		val translation = elementNode.textContent.trim()

		if (name.trim().isEmpty()) {
			printErr(Exception("Node have not \"name\" attribute. $translation"))
			return null
		}

		return Pair(name, TranslationData.createTranslation(translation))
	}

}