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
        maven("https://jitpack.io")
        google()
    }
}

rootProject.name = "Habit Tracker"
include(":app")
