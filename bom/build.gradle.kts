plugins {
    id("kotonoha.base")
    id("kotonoha.maven-publish")
}

val projectVersion: String by project
version = projectVersion

dependencies {
    sequenceOf(
        "annotations",
        "translatable-message",
//    "translation-extra-miniplaceholders"
        "resourcebundle-generator-processor",
    ).forEach {
        api(project(":kotonoha-$it"))
    }
}
