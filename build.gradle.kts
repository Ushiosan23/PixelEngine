// Application plugins
plugins {
	application
	java
	id("org.openjfx.javafxplugin") version "0.0.8"
}

// Global variables
val applicationMainClass = "fx.soft.pixelengine.app.Main"
val applicationVendor = "Ushiosan23"

// Application configuration
group = "fx.soft.pixelengine"
version = "0.0.1"

// Source repositories
repositories {
	mavenCentral()
	jcenter()
}

// Application configuration
application {
	mainClassName = applicationMainClass
}

// Source compatibility
configure<JavaPluginConvention> {
	sourceCompatibility = JavaVersion.VERSION_11
}

// JavaFX Configuration
javafx {
	version = "11"
	modules = arrayListOf(
		"javafx.base",
		"javafx.controls",
		"javafx.fxml",
		"javafx.graphics",
		"javafx.media",
		"javafx.swing",
		"javafx.web"
	)
}

// Jar configuration
tasks.jar {
	// Jar file manifest configuration
	manifest {
		attributes(
			"Main-Class" to applicationMainClass,
			"Specification-Title" to rootProject.name,
			"Specification-Version" to archiveVersion.get(),
			"Specification-Vendor" to applicationVendor
		)
	}
	
	// Jar file resources
	from(configurations.compileClasspath.map { config ->
		config.map { item ->
			if (item.isDirectory) item else zipTree(item)
		}
	})
}

// Project dependencies
dependencies {
	// JDBC sqlite
	implementation("org.xerial:sqlite-jdbc:3.32.3")
	implementation("org.jetbrains:annotations:19.0.0")
	// Test dependencies
	implementation("junit", "junit", "4.12")
}
