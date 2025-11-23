pluginManagement {
    repositories {
        maven {
            url = uri("http://repo.maven.apache.org/maven2/")
            isAllowInsecureProtocol = true
        }
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("http://repo.maven.apache.org/maven2/")
            isAllowInsecureProtocol = true
        }
    }
}

rootProject.name = "test"