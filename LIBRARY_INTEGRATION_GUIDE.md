# GreetingLib Integration Guide

This guide explains how to integrate GreetingLib with Hilt dependency injection into your Android project.

## 📦 Library Distribution

GreetingLib is distributed as an Android Archive (AAR) with proper Hilt dependency management.

### What's Included
- **Core Library**: GreetingLib AAR with Hilt components
- **Source Code**: Complete Kotlin source files
- **Documentation**: Javadoc/KDoc documentation
- **ProGuard Rules**: Consumer rules for proper obfuscation
- **Hilt Modules**: Pre-configured dependency injection modules

## 🚀 Quick Integration

### Step 1: Add Hilt to Your Project

Add Hilt plugin to your project's `build.gradle.kts`:

```kotlin
plugins {
    id("com.google.dagger.hilt.android") version "2.50"
    id("kotlin-kapt")
}
```

### Step 2: Add GreetingLib Dependency

Add to your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("br.com.renanalencar:greetinglib:1.1.0")
    kapt("com.google.dagger:hilt-android-compiler:2.50")
}
```

### Step 3: Configure Your Application

Update your Application class:

```kotlin
@HiltAndroidApp
class MyApplication : Application() {
    // Your application code
}
```

Update your `AndroidManifest.xml`:

```xml
<application
    android:name=".MyApplication"
    ...>
</application>
```

### Step 4: Use in Activities/Fragments

```kotlin
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    @Inject
    lateinit var getGreetingUseCase: GetGreetingUseCase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Use the injected dependency
        val greeting = getGreetingUseCase.execute("World")
        Log.d("Greeting", greeting.message) // "Hello, World!"
    }
}
```

## 🎯 Advanced Integration

### Using with ViewModels

```kotlin
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getGreetingUseCase: GetGreetingUseCase
) : ViewModel() {
    
    fun getGreeting(name: String): String {
        return getGreetingUseCase.execute(name).message
    }
}
```

### Using in Compose

```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val viewModel: MainViewModel = hiltViewModel()
    
    // Use GreetingLib component directly
    GreetingComponent(
        useCase = // injected automatically,
        name = "Compose"
    )
}
```

### Advanced Hilt Features

#### Using Qualifiers

```kotlin
class MyViewModel @Inject constructor(
    @FormalGreeting private val formalRepository: GreetingRepository,
    @CasualGreeting private val casualRepository: GreetingRepository
) : ViewModel() {
    
    fun getBusinessGreeting(name: String) = formalRepository.getGreeting(name)
    fun getFriendlyGreeting(name: String) = casualRepository.getGreeting(name)
}
```

#### Using Contextual Greetings

```kotlin
class MyService @Inject constructor(
    private val contextualUseCase: GetContextualGreetingUseCase
) {
    
    fun getGreeting(name: String, isBusinessContext: Boolean): String {
        return contextualUseCase.execute(name, isFormal = isBusinessContext).message
    }
}
```

## 🧪 Testing Integration

### Unit Testing

```kotlin
@HiltAndroidTest
class MyFeatureTest {
    
    @get:Rule
    val hiltRule = HiltAndroidRule(this)
    
    @Inject
    lateinit var getGreetingUseCase: GetGreetingUseCase
    
    @Before
    fun init() {
        hiltRule.inject()
    }
    
    @Test
    fun testGreetingFeature() {
        val result = getGreetingUseCase.execute("Test")
        assertEquals("Hello, Test!", result.message)
    }
}
```

### Custom Test Modules

```kotlin
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [GreetingModule::class]
)
object TestGreetingModule {
    
    @Provides
    @Singleton
    fun provideTestGreetingRepository(): GreetingRepository {
        return mockk<GreetingRepository> {
            every { getGreeting(any()) } returns Greeting("Test greeting")
        }
    }
}
```

## 🛠️ Build Configuration

### ProGuard/R8 Configuration

The library includes consumer ProGuard rules, but you can add additional rules if needed:

```proguard
# Additional rules for your app
-keep class com.yourpackage.** { *; }

# If you extend GreetingLib classes
-keep class * extends br.com.renanalencar.greetinglib.** { *; }
```

### Version Catalog (recommended)

Add to your `gradle/libs.versions.toml`:

```toml
[versions]
greetinglib = "1.1.0"
hilt = "2.50"

[libraries]
greetinglib = { group = "br.com.renanalencar", name = "greetinglib", version.ref = "greetinglib" }
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-compiler = { group = "com.google.dagger", name = "hilt-android-compiler", version.ref = "hilt" }

[plugins]
hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
```

## 🔧 Troubleshooting

### Common Issues

#### 1. Missing @HiltAndroidApp
```
Error: @AndroidEntryPoint class MyActivity is missing @HiltAndroidApp annotation
```

**Solution**: Add `@HiltAndroidApp` to your Application class.

#### 2. KAPT Configuration
```
Error: Hilt compilation failed
```

**Solution**: Ensure you have the correct KAPT configuration:

```kotlin
kapt {
    correctErrorTypes = true
}
```

#### 3. ProGuard Issues
```
Error: Class not found exception at runtime
```

**Solution**: The library includes consumer ProGuard rules, but verify they're being applied:

```kotlin
android {
    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
```

### Debug Steps

1. **Check Hilt Setup**:
   ```bash
   ./gradlew :app:dependencies | grep hilt
   ```

2. **Verify KAPT Processing**:
   ```bash
   ./gradlew :app:kaptDebugKotlin --info
   ```

3. **Check Generated Files**:
   ```bash
   find app/build -name "*Hilt*" -type f
   ```

## 📚 API Reference

### Core Classes

- `GetGreetingUseCase`: Main use case for generating greetings
- `GreetingRepository`: Interface for greeting data sources
- `Greeting`: Data model for greeting messages
- `GreetingComponent`: Compose UI component

### Advanced Classes

- `GetFormalGreetingUseCase`: Business formal greetings
- `GetCasualGreetingUseCase`: Friendly casual greetings
- `GetContextualGreetingUseCase`: Context-aware greetings

### Qualifiers

- `@DefaultGreeting`: Standard greeting implementation
- `@FormalGreeting`: Professional business greetings
- `@CasualGreeting`: Friendly social greetings

### Modules

- `GreetingModule`: Basic Hilt module
- `AdvancedGreetingModule`: Advanced features with qualifiers

## 🔄 Migration Guide

### From Manual DI

If you're migrating from manual dependency injection:

1. Remove manual factory classes
2. Add Hilt annotations to your Application
3. Replace manual injection with `@Inject` constructors
4. Update test setup to use Hilt test modules

### Version Updates

When updating GreetingLib versions:

1. Check the changelog for breaking changes
2. Update ProGuard rules if necessary
3. Run full test suite after integration
4. Verify all Hilt modules are working correctly

## 🤝 Support

For issues and questions:

1. Check the [troubleshooting section](#troubleshooting)
2. Review the [advanced patterns documentation](./ADVANCED_HILT_PATTERNS.md)
3. Create an issue on the GitHub repository
4. Check Hilt documentation for framework-specific issues

## 📋 Requirements

- **Android SDK**: 35+ (compileSdk 36)
- **Kotlin**: 2.0.21+
- **Hilt**: 2.50+
- **Java**: 11+

## 🏗️ Architecture Integration

GreetingLib follows Clean Architecture principles and integrates seamlessly with:

- **MVVM**: ViewModel injection with `@HiltViewModel`
- **Clean Architecture**: Domain/Data/Presentation layers
- **Repository Pattern**: Interface-based data access
- **Use Case Pattern**: Business logic encapsulation
- **Dependency Injection**: Hilt-based DI container

For more advanced usage patterns, see [ADVANCED_HILT_PATTERNS.md](./ADVANCED_HILT_PATTERNS.md).