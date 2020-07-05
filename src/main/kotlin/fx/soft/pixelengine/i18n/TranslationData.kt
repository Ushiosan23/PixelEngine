package fx.soft.pixelengine.i18n

import java.util.*

/**
 * Translation data.
 *
 * This class is used to save translation and have methods to format translations if is necessary.
 */
data class TranslationData(
	private val translationNode: Map<String, *>
) {

	/**
	 * Save raw translation data
	 */
	private var rawTranslation: String = (translationNode["content"] as String).trim()

	/**
	 * Get formatted data.
	 *
	 * @param attributes format attributes
	 *
	 * @return [String] formatted with attributes
	 */
	fun get(vararg attributes: Any): String {
		return try {
			rawTranslation.format(*attributes)
		} catch (err: Exception) {
			rawTranslation
		}
	}

	/**
	 * Get translation and prevent show format elements.
	 *
	 * @param attributes target translations attributes
	 *
	 * @return [String] translation without error and error translations formats
	 */
	fun getHide(vararg attributes: Any): String {
		return try {
			rawTranslation.format(*attributes)
		} catch (err: MissingFormatArgumentException) {
			rawTranslation = rawTranslation.replace(err.formatSpecifier, "")
			getHide(*attributes)
		} catch (err: Exception) {
			get(*attributes)
		}
	}

	/**
	 * Get formatted string object
	 */
	override fun toString(): String {
		return rawTranslation
	}

	/**
	 * Companion object
	 */
	companion object {

		/**
		 * Create translation
		 */
		fun createTranslation(data: Any): TranslationData = TranslationData(
			mapOf(
				"content" to data
			)
		)

	}

}