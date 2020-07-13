package fx.soft.pixelengine.i18n

import R
import fx.soft.pixelengine.debug.printErr
import fx.soft.pixelengine.system.OS
import fx.soft.pixelengine.ui.events.LanguageSelectorEvent
import fx.soft.pixelengine.ui.events.LanguageSelectorListener
import javax.swing.event.EventListenerList

/**
 * Language selector class.
 *
 * This class contain all possible languages in the project.
 */
class LanguageSelector(languageResources: List<R.AppResource>) {

	/**
	 * List with all listeners
	 */
	private val listenerList = EventListenerList()

	/**
	 * Default application language code
	 */
	private val appDefaultLanguageCode = "en"

	/**
	 * [LanguageSelector] current language. By default save system language.
	 */
	var currentLanguage = OS.LOCALE_CODE
		set(value) {
			val saveOld = field

			if (!getAvailableLanguages().contains(value.trim().toLowerCase())) {
				printErr(
					"Language \"$value\" is not found. Maybe you should use `getAvailableLanguages` method instead.",
					true
				)
				return
			}

			field = value.trim().toLowerCase()
			fireEvents(LanguageSelectorEvent(this, saveOld, field))
		}

	/**
	 * Variable with all languages
	 */
	private var languageList: MutableList<Language> = mutableListOf()

	/**
	 * Initialize object
	 */
	init {
		// iterate all languages
		languageResources.forEach {
			try {
				if (it.exists())
					languageList.add(Language(it.stream()!!, Language.getFileLanguageType(it.url())))
			} catch (err: Exception) {
				err.printStackTrace()
			}
		}
	}

	/**
	 * Get all language list. (only names)
	 *
	 * @return [List] with all possible languages
	 */
	fun getAvailableLanguages(): List<String> = languageList.map { it.name }

	/**
	 * Add event listener to current object.
	 *
	 * @param listener target listener
	 */
	fun addEventListener(listener: LanguageSelectorListener) {
		listenerList.add(LanguageSelectorListener::class.java, listener)
	}

	/**
	 * Remove listener
	 *
	 * @param listener target object to remove listener
	 */
	fun removeEventListener(listener: LanguageSelectorListener) {
		listenerList.remove(LanguageSelectorListener::class.java, listener)
	}

	/**
	 * Get translation or default value.
	 *
	 * @param property target translation property
	 * @param defaultValue a value to show if translation not exists.
	 *
	 * @return [TranslationData] with translation elements.
	 */
	operator fun get(property: String, defaultValue: Any) =
		getCurrentLanguage()[property, getDefaultLanguage()] ?: TranslationData.createTranslation(defaultValue)

	/**
	 * Get translation with default data
	 *
	 * @param property target translation property
	 *
	 * @return [TranslationData] with translation elements.
	 * @see [LanguageSelector.get]
	 */
	operator fun get(property: String) = this[property, ""]

	/**
	 * Get default language
	 *
	 * @return default app [Language]
	 */
	private fun getDefaultLanguage(): Language {
		val result = languageList.filter { it.name == appDefaultLanguageCode }
		return result.first()
	}

	/**
	 * Get current selected language
	 *
	 * @return current selected [Language] or default language if not exists
	 */
	private fun getCurrentLanguage(): Language {
		languageList.forEach {
			if (it.name == currentLanguage)
				return it
		}

		currentLanguage = appDefaultLanguageCode
		return getCurrentLanguage()
	}

	/**
	 * fire event in all listeners
	 */
	private fun fireEvents(evt: LanguageSelectorEvent) {
		listenerList.listenerList.forEach {
			if (it is LanguageSelectorListener)
				it.languageChanged(evt)
		}
	}

	/**
	 * Formatted object string
	 */
	override fun toString(): String {
		val codeNames = languageList.map { "${it.name}=>(${it.size})" }
		return "$codeNames"
	}

}