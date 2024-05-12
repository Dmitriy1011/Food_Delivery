pluginManagement {
    repositories {
        google()
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

rootProject.name = "Nti_Test"
include(":app")
include(":data")
include(":data:api")
include(":data:impl")
include(":domain")
include(":domain:api")
include(":domain:impl")
include(":domain:models")
include(":features")
include(":core")
include(":core:ui")
include(":features:catalog")
