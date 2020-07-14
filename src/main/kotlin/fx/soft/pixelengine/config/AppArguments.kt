package fx.soft.pixelengine.config

object AppArguments {

	/**
	 * Get argument value.
	 *
	 * @param value target value to parse
	 */
	private fun getKeyVal(value: String): Pair<String, String> {
		val split = value.split("=")

		if (split.size == 1)
			return value to ""

		return split.first() to split.last()
	}

	/**
	 * Parsed app arguments.
	 *
	 * @param args target app arguments.
	 * @return [Map] with arguments => {exp1=1, exp2=, exp3=hello}
	 */
	fun parsedFromArgs(args: Array<String>): Map<String, String> {
		val result = mutableMapOf<String, String>()

		// Iterate arguments
		args.forEach { arg ->
			if (arg.trim().isNotEmpty()) {
				val parsed = AppArguments.getKeyVal(arg)
				result[parsed.first] = parsed.second
			}
		}

		return result
	}

}