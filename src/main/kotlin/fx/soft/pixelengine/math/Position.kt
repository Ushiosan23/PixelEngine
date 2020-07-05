package fx.soft.pixelengine.math

import fx.soft.pixelengine.debug.printErr
import java.io.Serializable
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Default constructor.
 */
class Position() : Serializable {

	/**
	 * Horizontal position
	 */
	var x: Double = 0.0

	/**
	 * Vertical position
	 */
	var y: Double = 0.0

	/**
	 * Check if position is negative in any direction
	 */
	val isNegative: Boolean
		get() = (x < 0 || y < 0)

	/**
	 * Second constructor.
	 *
	 * @param x initial horizontal position
	 * @param y initial vertical position
	 */
	constructor(x: Number, y: Number) : this() {
		this.x = x.toDouble()
		this.y = y.toDouble()
	}

	/**
	 * Third constructor.
	 *
	 * @param xy initial [x] and [y] position.
	 */
	constructor(xy: Number) : this(xy, xy)

	/**
	 * Make action if position is negative in any direction
	 */
	fun errorNegative(): Boolean {
		printErr(Exception("Position Cannot be negative: $this"))
		return false
	}

	/**
	 * Operator plus function. (obj1 + obj2)
	 *
	 * @param other other position to add
	 */
	operator fun plus(other: Position): Position = Position(x + other.x, y + other.y)

	/**
	 * Operator plus function. (obj1 + number)
	 *
	 * @param other other number to add
	 */
	operator fun plus(other: Number): Position = Position(x + other.toDouble(), y + other.toDouble())

	/**
	 * Operator minus function. (obj1 - obj2)
	 *
	 * @param other other position to subtract
	 */
	operator fun minus(other: Position): Position = Position(x - other.x, y - other.y)

	/**
	 * Operator minus function. (obj1 - number)
	 *
	 * @param other other number to subtract
	 */
	operator fun minus(other: Number): Position = Position(x - other.toDouble(), y - other.toDouble())

	/**
	 * Operator times function. (obj1 * obj2)
	 *
	 * @param other other position to multiply
	 */
	operator fun times(other: Position): Position = Position(x * other.x, y * other.y)

	/**
	 * Operator times function. (obj1 * number)
	 *
	 * @param other other number to multiply
	 */
	operator fun times(other: Number): Position = Position(x * other.toDouble(), y * other.toDouble())

	/**
	 * Override operator equality.
	 *
	 * @param other another object
	 *
	 * @return [Boolean] equality result
	 */
	override fun equals(other: Any?): Boolean = when (other) {
		is Position -> (x == other.x && y == other.y)
		null -> false
		else -> false
	}

	/**
	 * Operator div function. (obj1 / obj2)
	 *
	 * @param other other position to divide
	 */
	operator fun div(other: Position): Position = Position(x / other.x, y / other.y)

	/**
	 * Operator div function. (obj1 / number)
	 *
	 * @param other other number to divide
	 */
	operator fun div(other: Number): Position = Position(x / other.toDouble(), y / other.toDouble())

	/**
	 * Get position in string format.
	 *
	 * @return [String] formatted position.
	 */
	override fun toString(): String {
		return "{x: $x, y: $y}"
	}

	/**
	 * Get object hashCode
	 *
	 * @return [Int] with hashcode
	 */
	override fun hashCode(): Int {
		var result = x.hashCode()
		result = 31 * result + y.hashCode()
		return result
	}

	/**
	 * Companion object with utilities.
	 */
	companion object {

		/**
		 * Linear interpolate between 2 positions.
		 *
		 * @param value current position
		 * @param target target position
		 * @param steep distance move each call
		 *
		 * @return [Position] result of linear interpolation
		 *
		 * @see [MathF.linearInterpolation]
		 */
		fun linearInterpolation(value: Position, target: Position, steep: Double): Position =
			Position(
				MathF.linearInterpolation(value.x, target.x, steep),
				MathF.linearInterpolation(value.y, target.y, steep)
			)

		/**
		 * Linear interpolate between 2 positions. This method is more precise.
		 *
		 * @param value current position
		 * @param target target position
		 * @param steep distance move each call
		 *
		 * @return [Position] result of linear interpolation
		 *
		 * @see [MathF.linearInterpolationPrecise]
		 */
		fun linearInterpolationPrecise(value: Position, target: Position, steep: Double): Position =
			Position(
				MathF.linearInterpolationPrecise(value.x, target.x, steep),
				MathF.linearInterpolationPrecise(value.y, target.y, steep)
			)

		/**
		 * Absolute distance between 2 positions
		 *
		 * @param from start position
		 * @param to target position
		 *
		 * @return [Position] with distance
		 */
		fun distancePos(from: Position, to: Position): Position =
			Position(
				abs(from.x - to.x),
				abs(from.y - to.y)
			)

		/**
		 * Absolute distance between 2 positions
		 *
		 * @param from start position
		 * @param to target position
		 *
		 * @return [Double] width distance in any position
		 */
		fun distanceTo(from: Position, to: Position): Double {
			val v1: Float = from.x.toFloat() - to.x.toFloat()
			val v2: Float = from.y.toFloat() - to.y.toFloat()

			return sqrt((v1 * v1) + (v2 * v2)).toDouble()
		}

	}

}