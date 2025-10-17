@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "kotonoha"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

pluginManagement {
    includeBuild("build-logic")
}

sequenceOf(
    "annotations",
    "bom",
    "translation",
//    "translation-extra-miniplaceholders"
    "resourcebundle-generator-processor",
).forEach {
    include("kotonoha-$it")
    project(":kotonoha-$it").projectDir = file(it)
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
