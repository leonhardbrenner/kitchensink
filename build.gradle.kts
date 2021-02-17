import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
//import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

val kotlinVersion = "1.4.0"
val serializationVersion = "1.0.0-RC"
val ktorVersion = "1.4.0"

plugins {
    kotlin("multiplatform") version "1.4.0"
    application //to run JVM part
    kotlin("plugin.serialization") version "1.4.0"
    //Not sure I enjoyed using this the last time it make me use ugly sql. Must be something more kotliny
    //id( "org.flywaydb.flyway") version "5.2.4"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
    mavenCentral()
    jcenter()
    maven("https://kotlin.bintray.com/kotlin-js-wrappers/") // react, styled, ...
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-js-wrappers") }
}

//apply(plugin="kotlin-kapt")

kotlin {
    jvm {
        withJava()
    }
    js {
        browser {
            binaries.executable()
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-serialization:$ktorVersion")
                implementation("io.ktor:ktor-server-core:$ktorVersion")
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("ch.qos.logback:logback-classic:1.2.3")
                implementation("io.ktor:ktor-websockets:$ktorVersion")
                implementation("org.litote.kmongo:kmongo-coroutine-serialization:4.1.1")
                implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.0")
                implementation("com.google.inject:guice:4.2.2")
                implementation("com.authzee.kotlinguice4:kotlin-guice:1.3.0")
                //TODO - investigate if this actually works
                implementation("io.mockk:mockk:1.10.4")

                //https://github.com/JetBrains/Exposed/wiki/Getting-Started
                //TODO - This belongs in gradle.properties
                val exposed_version = "0.25.1"
                implementation( "org.jetbrains.exposed:exposed-core:$exposed_version")
                implementation( "org.jetbrains.exposed:exposed-dao:$exposed_version")
                implementation( "org.jetbrains.exposed:exposed-jdbc:$exposed_version")
                implementation("com.h2database:h2:1.4.199")
                // for logging (StdOutSqlLogger), see
                // http://www.slf4j.org/codes.html#StaticLoggerBinder
                implementation("org.slf4j:slf4j-nop:1.7.30")
                implementation("org.jetbrains.exposed:exposed:0.12.1")
                implementation("com.zaxxer:HikariCP:2.7.8")
                implementation("org.postgresql:postgresql:42.2.2")
                //implementation("org.flywaydb:flyway-core:5.2.4")

                implementation("org.jetbrains.kotlin:kotlin-reflect")
                implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
                implementation("com.squareup:kotlinpoet:1.5.0")

                implementation("com.github.doyaaaaaken:kotlin-csv-jvm:0.15.0")
                implementation("com.github.doyaaaaaken:kotlin-csv-jvm:0.10.4")
                implementation("com.vhl.blackmo:kotlin-grass-jvm:0.3.0")

                //Consider removing this since we already have a json library
                // https://mvnrepository.com/artifact/io.dropwizard/dropwizard-jackson
                implementation("io.dropwizard:dropwizard-jackson:1.1.2")

            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("io.mockk:mockk:1.10.4")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:$ktorVersion") //include http&websockets

                //ktor client js json
                implementation("io.ktor:ktor-client-json-js:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization-js:$ktorVersion")

                implementation("org.jetbrains:kotlin-react:16.13.1-pre.110-kotlin-1.4.0")
                implementation("org.jetbrains:kotlin-react-dom:16.13.1-pre.110-kotlin-1.4.0")
                implementation(npm("react", "16.13.1"))
                implementation(npm("react-dom", "16.13.1"))

                implementation("org.jetbrains:kotlin-styled:1.0.0-pre.110-kotlin-$kotlinVersion")
                implementation(npm("styled-components", "~5.1.1"))
                implementation(npm("inline-style-prefixer", "~6.0.0"))

                /**
                 * Material UI support:
                 *     https://github.com/cfnz/muirwik
                 *     https://github.com/cfnz/muirwik-starterapp
                 */
                implementation("com.ccfraser.muirwik:muirwik-components:0.6.2")
                implementation(npm("react-player", "~2.6.0"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

    }
}

application {
    mainClassName = "ServerKt"
}

tasks.register<ModelGenerator>("generate")

// include JS artifacts in any JAR we generate
tasks.getByName<Jar>("jvmJar") {
    val taskName = if (project.hasProperty("isProduction")) {
        "jsBrowserProductionWebpack"
    } else {
        "jsBrowserDevelopmentWebpack"
    }
    val webpackTask = tasks.getByName<KotlinWebpack>(taskName)
    dependsOn(webpackTask) // make sure JS gets compiled first
    from(File(webpackTask.destinationDirectory, webpackTask.outputFileName)) // bring output file along into the JAR
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

distributions {
    main {
        contents {
            from("$buildDir/libs") {
                rename("${rootProject.name}-jvm", rootProject.name)
                into("lib")
            }
        }
    }
}

// Alias "installDist" as "stage" (for cloud providers)
tasks.create("stage") {
    dependsOn(tasks.getByName("installDist"))
}

tasks.getByName<JavaExec>("run") {
    classpath(tasks.getByName<Jar>("jvmJar")) // so that the JS artifacts generated by `jvmJar` can be found and served
}