plugins {
    id("kotonoha.base")
    id("kotonoha.maven-publish")
}

val projectVersion: String by project
version = projectVersion

dependencies {
    sequenceOf(
        "annotations",
        "resourcebundle-generator-processor",
        "translatable-message",
        "translatable-message-extra-miniplaceholders",
        "translator",
    ).forEach {
        api(project(":kotonoha-$it"))
    }
}
