package fx.soft.pixelengine.ui.events

import java.util.*

/**
 * Language selector event.
 *
 * @param oldValue value before change
 * @param newValue value after change
 */
@Suppress("MemberVisibilityCanBePrivate")
class LanguageSelectorEvent(
	source: Any,
	val oldValue: String,
	val newValue: String
) : EventObject(source) {

	/**
	 * [LanguageSelectorEvent] string format
	 */
	override fun toString(): String {
		return """
			${this::class.qualifiedName} ${mapOf<String, Any>(
			"source" to source::class.qualifiedName!!,
			"oldValue" to oldValue,
			"newValue" to newValue
		)}
		""".trimIndent()
	}

}