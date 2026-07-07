plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    java
}

springBoot {
    mainClass.set("io.commercestacksolutions.priceproviderservice.BusinessExtApplication")
}

dependencies {
    implementation(project(":business-core"))
    implementation(project(":generated"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
}
