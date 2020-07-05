package fx.soft.pixelengine.data

import fx.soft.pixelengine.math.Position
import java.awt.Color

/**
 * Pixel data information object.
 *
 * @param color pixel color.
 * @param position pixel position.
 */
data class PixelData(
	val color: Color,
	val position: Position
)