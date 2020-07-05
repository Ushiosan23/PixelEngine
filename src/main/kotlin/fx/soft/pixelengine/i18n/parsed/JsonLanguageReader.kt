package fx.soft.pixelengine.i18n.parsed

import fx.soft.pixelengine.i18n.TranslationData
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.content
import kotlinx.serialization.json.float
import java.io.InputStream

/**
 * JsonLanguageReader used to parse json file language.
 */
@UnstableDefault
class JsonLanguageReader(stream: InputStream) : LanguageReader(stream) {

	/**
	 * Language code name
	 */
	override lateinit var name: String

	/**
	 * Json element data
	 */
	private val jsonData: JsonElement = Json.parseJson(getStreamData())

	/**
	 * Get language raw translations
	 *
	 * @return [Map] with raw data
	 */
	override fun getRawTranslations(): Map<String, TranslationData> {
		val map = mutableMapOf<String, TranslationData>()

		// Iterate all elements
		jsonData.jsonObject.forEach {
			try {
				it.value.jsonObject
			} catch (err: Exception) {
				map[it.key] = TranslationData.createTranslation(it.value.content)
			}
		}

		return map
	}

}