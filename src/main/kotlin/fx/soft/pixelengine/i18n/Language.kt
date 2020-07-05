package fx.soft.pixelengine.i18n

import fx.soft.pixelengine.extensions.cleanPath
import fx.soft.pixelengine.i18n.parsed.JsonLanguageReader
import fx.soft.pixelengine.i18n.parsed.LanguageReader
import fx.soft.pixelengine.i18n.parsed.XMLLanguageReader
import fx.soft.pixelengine.system.OS
import kotlinx.serialization.UnstableDefault
import java.io.File
import java.io.InputStream
import java.net.URL
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * Language translation class.
 * This class create language data and save all translations for target language.
 *
 * @param stream document stream.
 */
class Language(private val stream: InputStream, lType: LanguageType?) {

	/**
	 * Type of language file type
	 */
	enum class LanguageType(val extension: String, val clazz: KClass<*>) {
		XML(".xml", XMLLanguageReader::class),

		@UnstableDefault
		JSON(".json", JsonLanguageReader::class)
	}

	/**
	 * Second constructor
	 *
	 * @param file target document language.
	 */
	constructor(file: File) : this(file.inputStream(), getFileLanguageType(file))

	/**
	 * Language name
	 */
	var name: String
		private set

	/**
	 * Get language property size
	 */
	var size: Int = 0
		get() = translations.size
		private set

	/**
	 * Language translations
	 */
	private var translations: MutableMap<String, TranslationData>

	/**
	 * Initialize object method
	 */
	init {
		// Check language type
		if (lType == null)
			throw Exception("LanguageType cannot be null.")
		if (!lType.clazz.isSubclassOf(LanguageReader::class))
			throw Exception("\"${lType.clazz.qualifiedName}\" is not valid reader.")

		// Create type
		val lTypeOf = LanguageType.valueOf(lType.name)
		val clazzOf = lTypeOf.clazz.constructors.first().call(stream) as LanguageReader

		// Cast translations
		translations = clazzOf.getRawTranslations() as MutableMap<String, TranslationData>
		name = translations["name"]!!.get()
	}

	/**
	 * Operator to get translation language.
	 *
	 * @param label target translation label
	 * @return [TranslationData] content or `null` if not exists.
	 */
	operator fun get(label: String): TranslationData? {
		// Check if translation exists
		if (translations.containsKey(label))
			return translations[label]

		// return null value
		return null
	}

	/**
	 * Operator to get translation language.
	 *
	 * @param label target translation label
	 * @return [TranslationData] content or `null` if not exists. Change to second language if it's necessary.
	 */
	operator fun get(label: String, secondLanguage: Language): TranslationData? {
		// Check if translation exists
		if (translations.containsKey(label))
			return translations[label]

		// try to get second language translation
		return secondLanguage[label]
	}

	/**
	 * Create format string object
	 */
	override fun toString(): String {
		return "${this::class.qualifiedName}: ${this.name} -> ${this.translations}"
	}

	/**
	 * Companion with static methods
	 */
	companion object {

		/**
		 * Get file language type
		 *
		 * @param file file location
		 *
		 * @return [LanguageType] with result or `null` if file not exists or is not valid
		 */
		fun getFileLanguageType(file: File): LanguageType? {
			if (file.isDirectory)
				return null

			val filename = file.name
			val index = filename.lastIndexOf('.')

			if (index == -1)
				return null

			val extension = filename.substring(index)
			LanguageType.values().forEach {
				if (it.extension == extension)
					return it
			}

			return null
		}

		/**
		 * Get url file type
		 */
		fun getFileLanguageType(file: URL): LanguageType? {
			val name = OS.Path.basename(file.path)
			val index = name.lastIndexOf('.')

			if (index == -1)
				return null

			val extension = name.substring(index)
			LanguageType.values().forEach {
				if (it.extension == extension)
					return it
			}

			return null
		}

	}
}