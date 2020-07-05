package fx.soft.pixelengine.i18n.parsed

import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

/**
 * Abstract class to read language
 */
abstract class LanguageReader(private val stream: InputStream) : ILanguageReader {

	/**
	 * Get language name
	 */
	abstract var name: String
		protected set

	/**
	 * Get stream content at string format.
	 *
	 * @return [String] with all content data.
	 */
	protected fun getStreamData(): String {
		val readerStream = InputStreamReader(stream, StandardCharsets.UTF_8)
		val builder = StringBuilder()

		val byte = 1024
		val buffer = CharArray(byte)
		var read: Int = readerStream.read(buffer, 0, buffer.size)

		while (read > 0) {
			builder.append(buffer, 0, read)
			read = readerStream.read(buffer, 0, buffer.size)
		}

		return builder.toString()
	}


}