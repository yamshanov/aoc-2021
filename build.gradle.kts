plugins {
    kotlin("jvm") version "1.6.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

sourceSets {
    main {
        java.srcDirs("src/main")
    }
}

tasks.test {
    useJUnitPlatform()
}
