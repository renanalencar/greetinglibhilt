# Advanced Hilt Patterns in GreetingLib

This document demonstrates advanced Hilt dependency injection patterns implemented in the GreetingLib project.

## 🏷️ Qualifiers

### What are Qualifiers?
Qualifiers allow you to distinguish between multiple implementations of the same interface. When you have different implementations that serve different purposes, qualifiers help Hilt know which one to inject.

### Implementation

We've implemented three qualifiers for different greeting styles:

```kotlin
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FormalGreeting

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CasualGreeting

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultGreeting
```

### Usage in Modules

```kotlin
@Provides
@Singleton
@FormalGreeting
fun provideFormalGreetingRepository(): GreetingRepository = FormalGreetingRepositoryImpl()

@Provides
@Singleton
@CasualGreeting
fun provideCasualGreetingRepository(): GreetingRepository = CasualGreetingRepositoryImpl()
```

### Usage in Use Cases

```kotlin
class GetFormalGreetingUseCase @Inject constructor(
    @FormalGreeting private val repository: GreetingRepository
)

class GetCasualGreetingUseCase @Inject constructor(
    @CasualGreeting private val repository: GreetingRepository
)
```

### Composite Usage

```kotlin
class GetContextualGreetingUseCase @Inject constructor(
    @FormalGreeting private val formalRepository: GreetingRepository,
    @CasualGreeting private val casualRepository: GreetingRepository
) {
    fun execute(name: String, isFormal: Boolean = false): Greeting {
        return if (isFormal) {
            formalRepository.getGreeting(name)
        } else {
            casualRepository.getGreeting(name)
        }
    }
}
```

## 🔧 Scopes

### What are Scopes?
Scopes control the lifetime of dependencies. Different scopes ensure that objects live for appropriate durations.

### Built-in Scopes Used
- `@Singleton`: Lives for the entire application lifetime
- `@ActivityRetained`: Lives for activity lifetime (survives configuration changes)
- `@ActivityScoped`: Lives for activity lifecycle
- `@ViewModelScoped`: Lives for ViewModel lifecycle

### Custom Scope Implementation

```kotlin
@Scope
@Retention(AnnotationRetention.BINARY)
annotation class UserSessionScoped
```

This custom scope could be used for dependencies that should live for the duration of a user session.

## 📋 Benefits of Advanced Patterns

### 1. **Multiple Implementations**
- **Formal greetings**: Professional business context
- **Casual greetings**: Friendly social context
- **Default greetings**: Standard application behavior

### 2. **Testability**
- Each qualifier can be mocked independently
- Easy to test different scenarios
- Clear separation of concerns

### 3. **Flexibility**
- Runtime decision making (formal vs casual)
- Easy to add new greeting types
- Composable patterns

### 4. **Performance**
- Appropriate scoping prevents unnecessary object creation
- Memory management through lifecycle-aware scopes
- Singleton pattern for expensive operations

## 🧪 Testing Strategy

### Unit Tests for Qualifiers
```kotlin
@Test
fun getFormalGreetingUseCase_should_use_formal_repository() {
    val useCase = GetFormalGreetingUseCase(mockFormalRepository)
    val result = useCase.execute("Dr. Smith")
    verify(mockFormalRepository).getGreeting("Dr. Smith")
}
```

### Integration Tests with Hilt
```kotlin
@HiltAndroidTest
class AdvancedGreetingIntegrationTest {
    @Inject
    @FormalGreeting
    lateinit var formalRepository: GreetingRepository
    
    @Inject
    @CasualGreeting
    lateinit var casualRepository: GreetingRepository
}
```

## 🔄 Migration Path

### From Simple to Advanced
1. **Start with basic injection**: Single implementation
2. **Add qualifiers**: Multiple implementations of same interface
3. **Implement custom scopes**: Lifecycle management
4. **Add assisted injection**: Dynamic parameters (future enhancement)

### Backwards Compatibility
- All existing code continues to work
- Gradual adoption of advanced patterns
- No breaking changes to public API

## 📈 Performance Considerations

### Build Time Optimizations
- KAPT incremental compilation enabled
- Worker API usage for parallel processing
- Classpath optimizations

### Runtime Optimizations
- Singleton scope for expensive operations
- Lazy initialization where appropriate
- Memory-efficient scoping

## 🚀 Future Enhancements

### Assisted Injection
```kotlin
@AssistedInject
class AdvancedGreetingUseCase @AssistedInject constructor(
    @Assisted private val greetingStyle: GreetingStyle,
    private val repository: GreetingRepository
) {
    @AssistedFactory
    interface Factory {
        fun create(greetingStyle: GreetingStyle): AdvancedGreetingUseCase
    }
}
```

### Component Dependencies
- Cross-module dependency injection
- Feature-based modularization
- Plugin architecture support

## 📚 Resources

- [Hilt Official Documentation](https://dagger.dev/hilt/)
- [Dependency Injection Best Practices](https://developer.android.com/training/dependency-injection)
- [Testing with Hilt](https://developer.android.com/training/dependency-injection/hilt-testing)