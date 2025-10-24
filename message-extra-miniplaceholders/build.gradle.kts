plugins {
    id("kotonoha.base")
    id("kotonoha.testing")
    id("kotonoha.maven-publish")
}

val projectVersion: String by project
version = projectVersion

dependencies {
    compileOnly(projects.kotonohaMessage)
    compileOnlyApi(libs.mini.placeholders)

    testImplementation(libs.adventure.text.minimessage)
    testImplementation(libs.mini.placeholders)
    testImplementation(projects.kotonohaMessage)
}
