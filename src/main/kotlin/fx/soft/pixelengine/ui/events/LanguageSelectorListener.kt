package fx.soft.pixelengine.ui.events

import java.util.*

interface LanguageSelectorListener : EventListener {

	fun languageChanged(event: LanguageSelectorEvent)

}