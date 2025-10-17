plugins {
    id("kotonoha.base")
    id("kotonoha.maven-publish")
}

val projectVersion: String by project
version = projectVersion

dependencies {
    compileOnlyApi(libs.adventure.text.minimessage)
}
