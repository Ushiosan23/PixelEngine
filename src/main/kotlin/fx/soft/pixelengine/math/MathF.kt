package fx.soft.pixelengine.math

import kotlin.math.abs

/**
 * Math utilities
 */
object MathF {

	/**
	 * Distance between 2 numbers
	 *
	 * @param from start number
	 * @param to target number
	 *
	 * @return [Double] number with absolute distance
	 */
	fun distanceTo(from: Number, to: Number): Double = abs(from.toDouble() - to.toDouble())

	/**
	 * Get distance between 2 numbers
	 *
	 * @param from start number
	 * @param to target number
	 *
	 * @return [T] number with absolute distance
	 */
	inline fun <reified T : Number> distanceTo(from: Number, to: Number): T =
		abs(from.toDouble() - to.toDouble()) as T

	/**
	 * Linear interpolation between 2 numbers.
	 *
	 * @param value current value.
	 * @param target destination value.
	 * @param step how many distance in each steep.
	 *
	 * @return [Float] result of steep.
	 * @see [Linear Interpolation](https://en.wikipedia.org/wiki/Linear_interpolation#Programming_language_support)
	 */
	fun linearInterpolation(value: Number, target: Number, step: Number): Float =
		value.toFloat() + step.toFloat() * (target.toFloat() - value.toFloat())

	/**
	 * Linear interpolation between 2 numbers. But more precise.
	 *
	 * @param value current value.
	 * @param target destination value.
	 * @param step how many distance in each steep.
	 *
	 * @return [Float] result of steep.
	 * @see [Linear Interpolation](https://en.wikipedia.org/wiki/Linear_interpolation#Programming_language_support)
	 */
	fun linearInterpolationPrecise(value: Number, target: Number, step: Number): Float =
		(1f - step.toFloat()) * value.toFloat() + step.toFloat() * target.toFloat()

}