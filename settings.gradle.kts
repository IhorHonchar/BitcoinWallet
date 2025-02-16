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

rootProject.name = "BitcoinWallet"
include(":app")
include(":feature")
include(":feature:wallet")
include(":feature:wallet:data")
include(":feature:wallet:domain")
include(":feature:wallet:ui")
include(":navigation")
include(":core")
include(":core:ui")
include(":core:db")
include(":core:common")
