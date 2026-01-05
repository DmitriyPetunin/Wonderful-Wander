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

rootProject.name = "Wonderful Wander"
include(":app")
include(":core:data")
include(":core:network")
include(":core:domain")
include(":core:base")
include(":core:navigation")
include(":core:baseUi")

// Feature modules
include(":feature:auth:api")
include(":feature:auth:impl")
include(":feature:profile:api")
include(":feature:profile:impl")
include(":feature:post:api")
include(":feature:post:impl")
include(":feature:walk:api")
include(":feature:walk:impl")
include(":feature:map:api")
include(":feature:map:impl")
