plugins {
    id("kotonoha.base")
    id("kotonoha.testing")
    id("kotonoha.maven-publish")
}

val projectVersion: String by project
version = projectVersion

dependencies {
    compileOnlyApi(libs.adventure.text.minimessage)
    api(projects.kotonohaAnnotations)

    testImplementation(libs.adventure.text.minimessage)
}
