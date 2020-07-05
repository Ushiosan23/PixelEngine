package fx.soft.pixelengine.system

import fx.soft.pixelengine.config.Version
import fx.soft.pixelengine.extensions.cleanPath
import java.awt.Desktop
import java.awt.Toolkit
import java.io.File
import java.net.URI
import java.net.URL
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

/**
 * Operative system utilities.
 */
object OS {

	/**
	 * Directory file separator. `\` for Windows OS and `/` for unix OS
	 */
	val DIRECTORY_SEPARATOR: String = System.getProperty("file.separator")

	/**
	 * Get current user name in your OS.
	 */
	val USER_NAME: String = System.getProperty("user.name")

	/**
	 * Directory user home.
	 */
	val HOME: File = File(System.getProperty("user.home"))

	/**
	 * Platform OS
	 */
	val PLATFORM: Platform = getPlatform()

	/**
	 * Arch OS
	 */
	val ARCH: Arch = getArch()

	/**
	 * Version OS
	 */
	val VERSION: Version = getVersion()

	/**
	 * Native toolkit object
	 */
	val TOOLKIT: Toolkit = Toolkit.getDefaultToolkit()

	/**
	 * Get system locale code name
	 *
	 * @example `es -> Spanish | en -> English | jp -> Japanese`
	 */
	val LOCALE_CODE: String = Locale.getDefault().language

	/**
	 * Open file in specific location by native filesystem app.
	 *
	 * @param location target open location.
	 */
	fun fileSystem(location: File? = null): Boolean {
		val desktop = Desktop.getDesktop()

		if (location == null) {
			desktop.open(HOME)
			return true
		}

		if (!location.exists())
			return false

		return if (location.isDirectory) {
			desktop.open(location)
			true
		} else {
			desktop.open(location.parentFile)
			true
		}
	}

	/**
	 * Open default browser with specific url.
	 *
	 * @param url target url to open
	 */
	fun browse(url: URI): Boolean {
		val desktop = Desktop.getDesktop()

		return try {
			desktop.browse(url)
			true
		} catch (e: Exception) {
			e.printStackTrace()
			false
		}
	}

	/**
	 * Open default browser with specific url.
	 *
	 * @param url target url to open
	 */
	fun browse(url: String): Boolean = try {
		browse(URI(url))
	} catch (e: Exception) {
		e.printStackTrace()
		false
	}

	/**
	 * Get os platform.
	 *
	 * @return [Platform] os. Possible values (Windows, Linux, MacOS, Solaris and Unknown)
	 */
	private fun getPlatform(): Platform {
		val sysName: String = System.getProperty("os.name").toLowerCase()

		Platform.values().forEach {
			if (it.regex == null)
				return@forEach

			if (it.regex.containsMatchIn(sysName))
				return it
		}

		return Platform.Unknown
	}

	/**
	 * Get process architecture
	 *
	 * @return [Arch] os. Possible values (X64, X86, ARM_64, ARM, UNKNOWN)
	 */
	private fun getArch(): Arch {
		val archS = System.getProperty("os.arch")

		return when {
			Regex("(x64|amd64)$", RegexOption.IGNORE_CASE).containsMatchIn(archS) -> Arch.X64
			Regex("(x86|86)$", RegexOption.IGNORE_CASE).containsMatchIn(archS) -> Arch.X86
			Regex("(arm64|64)", RegexOption.IGNORE_CASE).containsMatchIn(archS) -> Arch.ARM_64
			Regex("(arm)", RegexOption.IGNORE_CASE).containsMatchIn(archS) -> Arch.ARM
			else -> Arch.UNKNOWN
		}
	}

	/**
	 * Get OS version
	 *
	 * @return [Version] os.
	 */
	private fun getVersion(): Version {
		val osV = System.getProperty("os.version")
		return Version.parse(osV) ?: Version(0, 0, 0)
	}

	/**
	 * Platform enum. List all support jdk platforms.
	 *
	 * @param regex used to determinate platform
	 */
	enum class Platform(val regex: Regex?) {
		Windows(Regex("^(win|windows)", RegexOption.IGNORE_CASE)),
		Linux(Regex("(nix|nux|linux)", RegexOption.IGNORE_CASE)),
		MacOS(Regex("(mac|unix|darwin)", RegexOption.IGNORE_CASE)),
		Solaris(Regex("(sol|ris|solaris)", RegexOption.IGNORE_CASE)),
		Unknown(null)
	}

	/**
	 * Arch enum. List all os architectures.
	 */
	enum class Arch {
		X64,
		X86,
		ARM_64,
		ARM,
		UNKNOWN
	}

	/**
	 * Path utilities
	 */
	object Path {

		/**
		 * Regex to found backslash and forward slash
		 */
		private val SLASH_FOUND = Regex("[/\\\\]")

		/**
		 * Target replace slash
		 */
		private val REPLACE_SLASH = if (getPlatform() == Platform.Windows) "\\\\" else "/"

		/**
		 * Replace path slash
		 *
		 * @param data target data to replace
		 */
		private fun replaceSlashes(data: CharSequence): String = if (SLASH_FOUND.containsMatchIn(data)) {
			data.replace(SLASH_FOUND, REPLACE_SLASH)
		} else {
			data.toString()
		}

		/**
		 * Get jar file query. This contain path of resource file jar.
		 *
		 * @param uri target uri
		 *
		 * @return [String] with jar query
		 *
		 * @example `jar:file:/Path/to/your/jar/file.jar!/query` => `/query`
		 */
		private fun getJarQuery(uri: URI): String {
			val indexOf = uri.toString().lastIndexOf('!')

			// Check if scheme file is jar
			if (uri.scheme != "jar")
				return ""
			// Check if uri has coincidences.
			if (indexOf == -1)
				return "/"

			// Get query jar file
			return uri.toString().substring(indexOf + 1)
		}

		/**
		 * Get file basename
		 *
		 * @param path target path to get basename
		 *
		 * @return [String] with basename
		 */
		fun basename(path: String): String = path
			.replace(SLASH_FOUND, "/")
			.cleanPath()
			.replace(Regex(".*/"), "")

		/**
		 * Replace all slashes to platform slash
		 *
		 * @param items target items to join
		 */
		fun join(vararg items: String): String {
			val result = StringBuilder()

			items.forEachIndexed { i: Int, item: String ->
				val nextFirstChar = if (i + 1 >= items.size) "" else items[i + 1].substring(0)
				val finalChar = item.substring(item.length - 1)

				if (SLASH_FOUND.containsMatchIn(nextFirstChar)) {
					if (SLASH_FOUND.containsMatchIn(finalChar)) {
						result.append(item.substring(0 until item.length - 1))
					} else {
						result.append(item)
					}
				} else {
					result.append(item + DIRECTORY_SEPARATOR)
				}

			}

			return replaceSlashes(result.toString()).substring(0 until result.length - 1)
		}

		/**
		 * Find all files in url resource.
		 *
		 * @param path target url location
		 *
		 * @return [List] with all files.
		 */
		fun walkURL(path: URL): List<URL> {
			val uri = path.toURI() // Create uri object
			val resourceLocation: java.nio.file.Path = if (uri.scheme == "jar") {
				// create new filesystem for jar files
				val fileSystem = FileSystems.newFileSystem(uri, mutableMapOf<String, Any>())
				// save resource query
				val query = getJarQuery(uri)
				// return path to get resources
				fileSystem.getPath(query)
			} else {
				// Get paths if resource is a physical file (no jar)
				Paths.get(uri)
			}

			// create walker
			val result = mutableListOf<URL>()
			val directoryWalk = Files.walk(resourceLocation)

			// Iterate all files
			for (item in directoryWalk.iterator()) {
				// Add resource to list
				result.add(item.toUri().toURL())
			}

			return result
		}

		/**
		 * Find all files in url resource and only return the results in [regex] rule.
		 *
		 * @param path target url location
		 * @param regex rule to get file
		 * @param basename parameter if only you can check basename file location. By default is false.
		 *
		 * @return [List] with all file results.
		 */
		fun walkURL(path: URL, regex: Regex, basename: Boolean = false): List<URL> = walkURL(path).filter {
			if (basename)
				regex.containsMatchIn(basename(it.path))
			else
				regex.containsMatchIn(it.path)
		}


	}

}