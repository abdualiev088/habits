pluginManagement {
    repositories {
        jcenter()
        maven("https://jitpack.io")
        google()
        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        jcenter()
        google()
        maven("https://jitpack.io")

    }
}

rootProject.name = "Habit Tracker"
include(":app")
 