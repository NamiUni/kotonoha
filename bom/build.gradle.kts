plugins {
    id("kotonoha.base")
    id("kotonoha.maven-publish")
}

val projectVersion: String by project
version = projectVersion

dependencies {
    sequenceOf(
        "annotations",
        "translation",
//    "translation-extra-miniplaceholders"
        "resourcebundle-generator-processor",
    ).forEach {
        api(project(":kotonoha-$it"))
    }
}
