package fx.soft.pixelengine.data

import fx.soft.pixelengine.debug.printErr
import fx.soft.pixelengine.math.Position
import java.awt.Color
import java.awt.Dimension
import java.awt.image.BufferedImage
import java.io.File
import java.io.Serializable
import javax.imageio.ImageIO

/**
 * Image data class
 */
@Suppress("MemberVisibilityCanBePrivate")
class ImageData(_size: Dimension) : Serializable {

	/**
	 * Dimension data
	 */
	var size: Dimension = _size
		private set

	/**
	 * Image data
	 */
	private var data: MutableMap<Color, MutableList<Position>> = mutableMapOf()

	/**
	 * Add pixel data in data.
	 *
	 * @param color pixel color
	 * @param position pixel position
	 */
	fun add(color: Color, position: Position): Boolean {
		// Check position
		if (position.isNegative)
			return position.errorNegative()

		return if (positionExists(position)) {
			replaceData(color, position)
			true
		} else if (color.alpha == 0) {
			true
		} else if (!data.keys.contains(color)) {
			data[color] = mutableListOf(position)
			true
		} else {
			data[color]!!.add(position)
			true
		}
	}

	/**
	 * Add pixel data in data.
	 *
	 * @param pixelData pixel content data
	 */
	fun add(pixelData: PixelData): Boolean = add(pixelData.color, pixelData.position)

	/**
	 * Remove position in data.
	 *
	 * @param position target position
	 *
	 * @return [Boolean] with remove result
	 */
	fun remove(position: Position): Boolean {
		// Check position negative
		if (position.isNegative)
			return position.errorNegative()

		// Check if position exists
		if (!positionExists(position)) {
			printErr(Exception("Position not exists: $position"))
			return false
		}

		val colorPos = getColorPosition(position)!!
		val index = data[colorPos]!!.indexOf(position)

		data[colorPos]!!.removeAt(index)

		return true
	}

	/**
	 * Get image data. Clone image data and get clone data.
	 *
	 * @return [MutableList<Color, MutableList<Position>>] with image data
	 */
	fun getData(): MutableMap<Color, MutableList<Position>> {
		val imgData = ImageData(size)
		imgData.data = data

		return imgData.data
	}

	/**
	 * Check if position exists in data.
	 *
	 * @param position target position
	 *
	 * @return [Boolean] `true` if position exists `false` otherwise
	 */
	fun positionExists(position: Position): Boolean = getColorPosition(position) != null

	/**
	 * Get color position
	 *
	 * @param position target position
	 *
	 * @return [Color]
	 */
	fun getColorPosition(position: Position): Color? {
		// Check positions
		if (position.isNegative)
			position.errorNegative().also {
				return null
			}

		data.keys.forEach { color: Color ->
			val positions = data[color]!!

			positions.forEach { pos: Position ->
				if (position == pos)
					return color
			}
		}

		return null
	}

	/**
	 * Replace image data.
	 *
	 * @param color target new color
	 * @param position target position
	 */
	private fun replaceData(color: Color, position: Position) = getColorPosition(position)?.also { foundColor: Color ->
		val positionArr = data[foundColor]!!
		val contains = positionArr.indexOf(position)

		if (color == foundColor)
			return@also

		data[foundColor]!!.removeAt(contains)
		if (data.keys.contains(color)) {
			data[color] = mutableListOf(position)
		} else {
			data[color]!!.add(position)
		}
	}

	/**
	 * Override string method.
	 *
	 * @return [String] formatted object string
	 */
	override fun toString(): String {
		return "${this::class.qualifiedName}: $data"
	}

	/**
	 * Companion object image data
	 */
	companion object {

		/**
		 * Create image data from image
		 *
		 * @param file target file.
		 *
		 * @return [ImageData] with all image information or `null` if not exists
		 */
		fun createFromImage(file: File): ImageData? {
			// Check if file exists
			if (!file.exists()) {
				printErr(Exception("File not exists. $file"))
				return null
			}
			// Check if is directory
			if (file.isDirectory) {
				printErr(Exception("File cannot be directory. $file"))
				return null
			}

			// Create img
			val img = ImageIO.read(file)
			return getImageData(img)
		}

		/**
		 * Get image data. Read all file and create data.
		 *
		 * @param image buffered image to scan.
		 *
		 * @return [ImageData] with all image information
		 */
		private fun getImageData(image: BufferedImage): ImageData {
			val w = image.width
			val h = image.height
			val data = ImageData(Dimension(w, h))

			for (x in 0 until w) {
				for (y in 0 until h) {
					val pos = Position(x, y)
					val color = intToColor(image.getRGB(x, y))
					val pixelData = PixelData(color, pos)

					data.add(pixelData)
				}
			}

			return data
		}

		/**
		 * Parse int to color
		 *
		 * @param intColor target color
		 *
		 * @return [Color] parsed color
		 */
		private fun intToColor(intColor: Int): Color = Color(
			(intColor and 0x00ff0000) shr 16,
			(intColor and 0x0000ff00) shr 8,
			intColor and 0x000000ff,
			(intColor shr 24) and 0xff
		)

	}

}