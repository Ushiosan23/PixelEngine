package fx.soft.pixelengine.extensions

/**
 * Clean url and path slashes.
 *
 * @return [String] clean
 */
fun String.cleanPath(): String = if (this.substring(this.length - 1) == "/")
	this.substring(0 until this.length - 1)
else
	this