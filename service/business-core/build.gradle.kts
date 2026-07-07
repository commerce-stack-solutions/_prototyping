import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    `java-library`
}

dependencies {
    api(project(":commons"))
    api(project(":generated"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
}

// Produce a regular jar (no classifier) for use as a dependency in business-ext,
// and a separately classified executable boot jar for standalone use.
tasks.named<BootJar>("bootJar") {
    archiveClassifier.set("exec")
}

tasks.named<Jar>("jar") {
    archiveClassifier.set("")
}
