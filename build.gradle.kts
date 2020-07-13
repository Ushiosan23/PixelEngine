plugins {
	groovy
	java
	application
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.serialization") version "1.3.72"
}

// Base configuration
group = "fx.soft.pixelengine"
version = "0.0.1"

// Source repositories
repositories {
	mavenCentral()
	jcenter()
}

// Select dependencies
dependencies {
	// Groovy implementation
	implementation("org.codehaus.groovy:groovy-all:2.3.11")

	// Kotlin implementation
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-serialization:1.3.72")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.72")

	// Test implementation
	implementation("junit:junit:4.12")
}

tasks.jar {
	// Set manifest configuration
	manifest {
		attributes(
			"Main-Class" to "fx.soft.pixelengine.app.Program",
			"Specification-Title" to rootProject.name,
			"Specification-Version" to archiveVersion.get(),
			"Specification-Vendor" to "softfx"
		)
	}

	// Compile with all resource files
	from(configurations.compileClasspath.map { config ->
		config.map {
			if (it.isDirectory)
				it
			else
				zipTree(it)
		}
	})
}

// Configure tasks
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
	kotlinOptions.jvmTarget = "11"
	kotlinOptions.includeRuntime = true
}