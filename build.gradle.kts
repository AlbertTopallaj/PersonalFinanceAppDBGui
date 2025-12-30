plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "se.albert"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("se.albert.personalfinanceguidb.Main");
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

javafx {
    version = "21"
    modules("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("at.favre.lib:bcrypt:0.10.2")
}


