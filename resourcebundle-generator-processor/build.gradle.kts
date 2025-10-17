plugins {
    id("kotonoha.base")
    id("kotonoha.testing")
    id("kotonoha.maven-publish")
}

val projectVersion: String by rootProject
version = projectVersion

dependencies {
    implementation(projects.kotonohaAnnotations)
    compileOnly(libs.google.auto.service.annotations)
    annotationProcessor(libs.google.auto.service)

    testImplementation(libs.google.compile.testing)
}
