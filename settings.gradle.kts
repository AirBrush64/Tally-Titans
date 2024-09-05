pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Tally Titans"
include(":app")
include(":app:login")
include(":app:login:login-view")
include(":app:main")
include(":app:login:login-viewmodel")
include(":app:login:login-data")
include(":app:mainScreen")
include(":app:register")
include(":app:register:register-view")
include(":app:register:register-viewmodel")
include(":app:register:register-data")
