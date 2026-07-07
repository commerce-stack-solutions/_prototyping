plugins {
    id("org.springframework.boot") version "4.1.0" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

allprojects {
    group = "io.commercestacksolutions"
    version = "0.0.1-SNAPSHOT"
}

tasks.register("bootRun") {
    group = "application"
    description = "Delegates to :business-ext:bootRun"
    dependsOn(":business-ext:bootRun")
}

subprojects {
    apply(plugin = "java")

    if (name == "business-core") {
        afterEvaluate {
            tasks.named("bootRun") {
                enabled = false
                group = "disabled"
            }
            tasks.named("bootJar") {
                enabled = false
                group = "disabled"
            }
        }
    }

    configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }

    repositories {
        mavenCentral()
    }
}
