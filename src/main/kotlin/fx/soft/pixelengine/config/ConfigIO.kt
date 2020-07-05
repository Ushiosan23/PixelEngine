package fx.soft.pixelengine.config

import java.io.*

class ConfigIO<T : Serializable>(_data: T) {

	/**
	 * Object data
	 */
	var data: T = _data

	/**
	 * Write data in file
	 *
	 * @param file target file to write
	 */
	@Suppress("MemberVisibilityCanBePrivate")
	fun write(file: File): Boolean {
		try {
			val output = FileOutputStream(file)
			val objectStream = ObjectOutputStream(output)

			objectStream.writeObject(data)
		} catch (error: Exception) {
			error.printStackTrace()
			return false
		}

		return true
	}

	/**
	 * Write data in file
	 *
	 * @param file target file to write
	 */
	fun write(file: String): Boolean = write(File(file))

	/**
	 * Read data from file
	 *
	 * @param file target read file
	 */
	@Suppress("MemberVisibilityCanBePrivate", "UNCHECKED_CAST")
	fun read(file: File): T? {
		try {
			val input = FileInputStream(file)
			val objectStream = ObjectInputStream(input)

			return objectStream.readObject() as T
		} catch (err: Exception) {
			err.printStackTrace()
		}

		return null
	}

	/**
	 * Read data from file
	 *
	 * @param file target read file
	 */
	fun read(file: String) = read(File(file))

	/**
	 * Companion object
	 */
	companion object {

		/**
		 * Read data from file
		 *
		 * @param file target read file
		 */
		inline fun <reified T : Serializable> sRead(file: File): T? {
			try {
				val input = FileInputStream(file)
				val objectStream = ObjectInputStream(input)

				return objectStream.readObject() as T
			} catch (err: Exception) {
				err.printStackTrace()
			}

			return null
		}

		/**
		 * Read data from file
		 *
		 * @param file target read file
		 */
		inline fun <reified T : Serializable> sRead(file: String): T? = sRead(File(file))

	}
}