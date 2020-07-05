import fx.soft.pixelengine.system.OS
import org.junit.Test

class OSTest : ITest {

	@Test
	override fun runTest() {
		println("Directory Separator: ${OS.DIRECTORY_SEPARATOR}")
		println("User name: ${OS.USER_NAME}")
		println("HOME: ${OS.HOME}")
		println("PLATFORM: ${OS.PLATFORM}")
		println("ARCH: ${OS.ARCH}")
		println("OS VERSION: ${OS.VERSION}")
		println("TOOLKIT: ${OS.TOOLKIT}")
		println("LOCALE CODE: ${OS.LOCALE_CODE}")

		OS.fileSystem()
		OS.browse("https://www.github.com/Ushiosan23")
	}

}