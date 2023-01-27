plugins {
    id("java")
    id("maven-publish")
}

group = "dev.lightdream"
version = "2.4.0"

repositories {
    mavenCentral()
    maven ("https://repo.lightdream.dev/")
}

configurations.all {
    resolutionStrategy.cacheDynamicVersionsFor(10, "seconds")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // LightDream
    implementation("dev.lightdream:logger:3.1.0")
    implementation("dev.lightdream:lambda:3.8.0")

    // Google
    implementation("com.google.code.gson:gson:2.10")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

}

configurations.all {
    resolutionStrategy.cacheDynamicVersionsFor(10, "seconds")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<Jar> {
    archiveFileName.set("${rootProject.name}.jar")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        val gitlabURL = project.findProperty("gitlab.url") ?: ""
        val gitlabHeaderName = project.findProperty("gitlab.auth.header.name") ?: ""
        val gitlabHeaderValue = project.findProperty("gitlab.auth.header.value") ?: ""

        val githubURL = project.findProperty("github.url") ?: ""
        val githubUsername = project.findProperty("github.auth.username") ?: ""
        val githubPassword = project.findProperty("github.auth.password") ?: ""

        val selfURL = project.findProperty("self.url") ?: ""
        val selfUsername = project.findProperty("self.auth.username") ?: ""
        val selfPassword = project.findProperty("self.auth.password") ?: ""

        maven(url = gitlabURL as String) {
            name = "gitlab"
            credentials(HttpHeaderCredentials::class) {
                name = gitlabHeaderName as String
                value = gitlabHeaderValue as String
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }

        maven(url = githubURL as String) {
            name = "github"
            credentials(PasswordCredentials::class) {
                username = githubUsername as String
                password = githubPassword as String
            }
        }

        maven(url = selfURL as String) {
            name = "self"
            credentials(PasswordCredentials::class) {
                username = selfUsername as String
                password = selfPassword as String
            }
        }
    }
}

tasks.register("publishGitLab") {
    dependsOn("publishMavenPublicationToGitlabRepository")
    description = "Publishes to GitLab"
}

tasks.register("publishGitHub") {
    dependsOn("publishMavenPublicationToGithubRepository")
    description = "Publishes to GitHub"
}

tasks.register("publishSelf") {
    dependsOn("publishMavenPublicationToSelfRepository")
    description = "Publishes to Self hosted repository"
}
