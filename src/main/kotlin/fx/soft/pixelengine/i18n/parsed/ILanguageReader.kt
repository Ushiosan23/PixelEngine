package fx.soft.pixelengine.i18n.parsed

import fx.soft.pixelengine.i18n.TranslationData

/**
 * Language interface to make rules for reader classes
 */
interface ILanguageReader {

	/**
	 * Get all translations nodes
	 */
	fun getRawTranslations(): Map<String, TranslationData>

}