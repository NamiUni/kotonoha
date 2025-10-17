plugins {
    id("java-library")
    id("checkstyle")
    id("com.diffplug.spotless")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

checkstyle {
    toolVersion = libs.versions.checkstyle.get()
    configDirectory = rootProject.file(".checkstyle")
}

spotless {
    java {
        licenseHeaderFile(rootProject.file("LICENSE_HEADER"))
    }
}

tasks.compileJava {
    options.compilerArgs.add("-parameters")
    options.compilerArgs.add("-Xlint:-processing")
}

dependencies {
    api(libs.jspecify)
}
