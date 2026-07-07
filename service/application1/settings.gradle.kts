pluginManagement {
    includeBuild("../platform/cdf-plugin")
}

rootProject.name = "application1"

include("commons")
project(":commons").projectDir = file("../platform/commons")

include("generated")

include("business-core")
project(":business-core").projectDir = file("../platform/business-core")

include("business-ext")
