import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar
plugins {
    id("java-library")
    id("com.vanniktech.maven.publish")
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    configure(
        JavaLibrary(
            javadocJar = JavadocJar.Javadoc(),
            sourcesJar = true,
        )
    )

    pom {
        name.set("kotonoha")
        description.set("An Adventure-focused library.")
        url.set("https://github.com/NamiUni/kotonoha")
        licenses {
            license {
                name.set("The MIT License")
                url.set("https://opensource.org/licenses/mit-license.php")
            }
        }
        developers {
            developer {
                id.set("NamiUni")
                name.set("Namiu (うにたろう)")
            }
        }
        scm {
            url.set("https://github.com/NamiUni/kotonoha/")
            connection.set("scm:git:git://github.com/NamiUni/kotonoha.git")
            developerConnection.set("scm:git:ssh://git@github.com/NamiUni/kotonoha.git")
        }
    }
}
