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
include(":app:main")
include(":app:login")
include(":app:login:login-view")
include(":app:login:login-viewmodel")
include(":app:login:login-data")
include(":app:register")
include(":app:register:register-view")
include(":app:register:register-viewmodel")
include(":app:register:register-data")
include(":app:userMainScreen")
include(":app:userMainScreen:userMainScreen-view")
include(":app:userMainScreen:userMainScreen-viewModel")
include(":app:userMainScreen:userMainScreen-data")
//include(":app:adminMainScreen:adminMainScreen-data")
//include(":app:adminMainScreen:adminMainScreen-view")
//include(":app:adminMainScreen:adminMainScreen-viewModel")
include(":app:gameScreen")
include(":app:gameScreen:gameScreen-data")
include(":app:gameScreen:gameScreen-view")
include(":app:gameScreen:gameScreen-viewModel")
