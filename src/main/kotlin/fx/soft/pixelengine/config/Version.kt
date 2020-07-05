package fx.soft.pixelengine.config

/**
 * Primary constructor.
 *
 * @param _major version major, used to numerate versions
 * @param _minor version minor, used to numerate features
 * @param _revision version revision, used to numerate fixed bugs or refactor code
 */
@Suppress("MemberVisibilityCanBePrivate")
data class Version(
	private val _major: Int,
	private val _minor: Int? = null,
	private val _revision: Int? = null
) {

	/**
	 * Major version
	 */
	val major: Int = _major

	/**
	 * Minor version
	 */
	val minor: Int = _minor ?: 0

	/**
	 * Revision version
	 */
	val revision: Int = _revision ?: 0

	/**
	 * Get version string
	 *
	 * @return [String] version with format `M.m.r`
	 */
	override fun toString(): String {
		return if (_revision == null)
			arrayOf(major, minor).joinToString(".")
		else
			arrayOf(major, minor, revision).joinToString(".")
	}

	/**
	 * Companion object with static methods
	 */
	companion object {

		/**
		 * Get version object with data.
		 *
		 * @param text [String] to parse
		 * @example `Version.parse("10.2.18839")` => `10.2.18839`
		 *
		 * @return [Version] with data or `version` if is not valid version
		 */
		fun parse(text: CharSequence): Version? {
			val reg = Regex("^(\\d+\\.)?(\\d+\\.)?(\\*|\\d+)$")

			if (!reg.containsMatchIn(text))
				return null

			val split = text.split(".")

			if (split.size == 1)
				return Version(split[0].toInt())
			if (split.size == 2)
				return Version(split[0].toInt(), split[1].toInt())
			if (split.size > 2)
				return Version(split[0].toInt(), split[1].toInt(), split[2].toInt())

			return null
		}

	}
}