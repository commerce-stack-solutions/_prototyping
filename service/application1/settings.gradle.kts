pluginManagement {
    includeBuild("../platform/cdf-plugin")
}

rootProject.name = "application1"

include("business-ext")
include("generated")

include("commons")
project(":commons").projectDir = file("../platform/commons")

include("business-core")
project(":business-core").projectDir = file("../platform/business-core")
