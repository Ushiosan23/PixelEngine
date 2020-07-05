package fx.soft.pixelengine.debug

import java.lang.Exception

/**
 * Print error in console.
 */
fun printErr() = System.err.println()

/**
 * Print error message in console.
 *
 * @param message Error message to show
 * @param createException create exception message and show stacktrace message
 */
fun printErr(message: Any? = null, createException: Boolean) = if (createException)
	(if (message == null) Exception() else Exception(message.toString())).printStackTrace(System.err)
else System.err.println(message)

/**
 * Print error message in console.
 *
 * @param message Error message
 */
fun printErr(message: Any? = null) = printErr(message, false)

/**
 * Print error message in console.
 *
 * @param message Exception message
 */
fun printErr(message: Throwable) = message.printStackTrace(System.err)