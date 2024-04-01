import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "com.experiment"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation ("org.jetbrains.kotlin:kotlin-test-junit:1.9.20")
}
tasks.test {
    useJUnitPlatform()
}

//kotlin {
////    jvm {
////        jvmToolchain(18)
////        withJava()
////    }
//
//    sourceSets {
//        val main by getting {
//            dependencies {
//                implementation(compose.desktop.currentOs)
//                implementation("com.darkrockstudios:mpfilepicker:2.1.0")
//                implementation("androidx.compose.material:material-icons-extended:1.6.0")
//                implementation("org.junit.jupiter:junit-jupiter:5.9.2")
//                api(compose.runtime)
//                api(compose.ui)
//                api(compose.foundation)
//                api(compose.material3)
//            }
//        }
//        val test by getting
//    }
//}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "calculator"
            packageVersion = "1.0.0"
        }
    }
}
