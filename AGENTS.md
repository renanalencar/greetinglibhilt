# Agent Guidelines for Greetinglib Android Project

## Build/Test Commands
- Build project: `./gradlew build`
- Run unit tests: `./gradlew test`
- Run single test class: `./gradlew test --tests "ExampleUnitTest"`
- Run instrumented tests: `./gradlew connectedAndroidTest`
- Clean build: `./gradlew clean`
- Assemble APK: `./gradlew assembleDebug`

## Project Structure
- `/greetinglib/` - Main library module (Android library)
- `/app/` - Demo application module (uses greetinglib)
- Clean Architecture: domain (models, repositories, use cases), data (repository implementations), presentation (Compose UI)

## Code Style Guidelines
- **Package structure**: `br.com.renanalencar.greetinglib.{layer}.{feature}`
- **Naming**: PascalCase for classes, camelCase for functions/properties, snake_case for test functions
- **Imports**: Group by standard library, Android, third-party, project imports
- **Types**: Use Kotlin data classes for models, interfaces for repositories
- **Functions**: Single expression functions when possible (e.g., `fun getGreeting(name: String) = Greeting("Olá, $name!")`)
- **Compose**: Use `@Composable` functions with descriptive names (e.g., `GreetingComponent`)
- **Dependencies**: Use constructor injection for use cases and repositories

## Configuration
- Target SDK: 36, Min SDK: 35
- Kotlin version: 2.0.21, Java version: 11
- Compose BOM: 2024.09.00
- Test runner: AndroidJUnitRunner