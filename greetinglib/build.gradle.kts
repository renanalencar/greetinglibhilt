import org.gradle.kotlin.dsl.implementation

plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    id("kotlin-android")
    id("kotlin-kapt")
    id("maven-publish")
}

android {
    namespace = "br.com.renanalencar.greetinglib"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 35
        targetSdk = 36

        testInstrumentationRunner = "br.com.renanalencar.greetinglib.HiltTestRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.runtime)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    
    // Hilt dependencies - use 'api' to expose to library consumers
    api(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.robolectric)
    kaptTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components["release"])
                
                groupId = "br.com.renanalencar"
                artifactId = "greetinglib"
                version = project.findProperty("VERSION_NAME")?.toString() ?: "1.0.0"
                
                pom {
                    name.set("GreetingLib")
                    description.set("A modern Android library demonstrating advanced Hilt dependency injection patterns")
                    url.set("https://github.com/your-username/greetinglib")
                    
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    
                    developers {
                        developer {
                            id.set("renanalencar")
                            name.set("Renan Alencar")
                            email.set("your.email@example.com")
                        }
                    }
                    
                    scm {
                        connection.set("scm:git:git://github.com/your-username/greetinglib.git")
                        developerConnection.set("scm:git:ssh://github.com/your-username/greetinglib.git")
                        url.set("https://github.com/your-username/greetinglib/tree/main")
                    }
                }
                
                // Ensure Hilt dependencies are properly declared
                pom.withXml {
                    val dependenciesNode = asNode().appendNode("dependencies")
                    
                    // Add Hilt as a required dependency for consumers
                    val hiltNode = dependenciesNode.appendNode("dependency")
                    hiltNode.appendNode("groupId", "com.google.dagger")
                    hiltNode.appendNode("artifactId", "hilt-android")
                    hiltNode.appendNode("version", libs.versions.hilt.get())
                    hiltNode.appendNode("scope", "compile")
                    
                    // Add KAPT compiler as an annotation processor dependency
                    val kaptNode = dependenciesNode.appendNode("dependency")
                    kaptNode.appendNode("groupId", "com.google.dagger")
                    kaptNode.appendNode("artifactId", "hilt-android-compiler")
                    kaptNode.appendNode("version", libs.versions.hilt.get())
                    kaptNode.appendNode("scope", "provided")
                }
            }
        }
        
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/your-username/greetinglib")
                credentials {
                    username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                    password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
                }
            }
            
            maven {
                name = "SonatypeStaging"
                url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = project.findProperty("ossrhUsername") as String? ?: System.getenv("OSSRH_USERNAME")
                    password = project.findProperty("ossrhPassword") as String? ?: System.getenv("OSSRH_PASSWORD")
                }
            }
        }
    }
}