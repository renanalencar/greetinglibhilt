# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your library uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

##################################################################################################
# Hilt ProGuard Rules for GreetingLib Consumers
##################################################################################################

# Keep Hilt generated classes and components
-keep class dagger.hilt.** { *; }
-keep class **_HiltComponents$* { *; }
-keep class **_Impl { *; }
-keep class **_Factory { *; }
-keep class **_MembersInjector { *; }

# Keep Hilt annotations
-keep @dagger.hilt.* class *
-keep @javax.inject.* class *

# Keep classes with Hilt annotations
-keep @dagger.hilt.android.lifecycle.HiltViewModel class * { *; }
-keep @dagger.hilt.android.AndroidEntryPoint class * { *; }
-keep @dagger.hilt.InstallIn class * { *; }

# Keep GreetingLib specific Hilt generated code
-keep class br.com.renanalencar.greetinglib.di.** { *; }
-keep class **_HiltModules* { *; }

# Keep all classes that implement GreetingRepository
-keep interface br.com.renanalencar.greetinglib.domain.repository.GreetingRepository
-keep class * implements br.com.renanalencar.greetinglib.domain.repository.GreetingRepository { *; }

# Keep use cases with @Inject constructors
-keep class br.com.renanalencar.greetinglib.domain.usecase.** {
    public <init>(...);
}

# Keep qualifiers and annotations
-keep @interface br.com.renanalencar.greetinglib.domain.qualifier.**
-keep @interface br.com.renanalencar.greetinglib.domain.scope.**

# Preserve annotations for runtime
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# Keep Kotlin metadata for reflection
-keep class kotlin.Metadata { *; }

##################################################################################################
# Compose specific rules (since GreetingLib uses Compose)
##################################################################################################

# Keep Composable functions
-keep class br.com.renanalencar.greetinglib.presentation.** {
    *;
}

# Keep Compose runtime
-keep class androidx.compose.runtime.** { *; }

##################################################################################################
# General library rules
##################################################################################################

# Keep public API classes
-keep public class br.com.renanalencar.greetinglib.** {
    public protected *;
}

# Keep domain models
-keep class br.com.renanalencar.greetinglib.domain.model.** { *; }

# Suppress warnings for known safe operations
-dontwarn javax.annotation.**
-dontwarn org.jetbrains.annotations.**