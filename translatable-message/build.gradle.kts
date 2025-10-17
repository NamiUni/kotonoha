plugins {
    id("kotonoha.base")
    id("kotonoha.testing")
    id("kotonoha.maven-publish")
}

val projectVersion: String by project
version = projectVersion

dependencies {
    compileOnly(libs.adventure.text.minimessage)
    api(libs.geantyref)
    api(projects.kotonohaAnnotations)

    testImplementation(libs.adventure.text.minimessage)
    testImplementation(libs.adventure.text.serializer.plain)
    testImplementation(libs.jspecify)
}
