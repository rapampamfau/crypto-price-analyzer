plugins {
    id("java")
}

group = "org.cryptopriceanalyzer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.seleniumhq.selenium:selenium-java:4.15.0")
    implementation("org.seleniumhq.selenium:selenium-chrome-driver:4.15.0")
    implementation("com.opencsv:opencsv:5.7.1")

}

tasks.test {
    useJUnitPlatform()
}