# CI/CD Pipeline Documentation

This document describes the comprehensive CI/CD setup for the GreetingLib Android project with Hilt dependency injection.

## 📋 Overview

Our CI/CD pipeline consists of three main workflows designed to handle Hilt dependencies properly:

1. **CI Pipeline** (`ci.yml`) - Continuous integration for all pushes and PRs
2. **Release Pipeline** (`release.yml`) - Automated release process
3. **Security Pipeline** (`security.yml`) - Dependency and security scanning

## 🔄 CI Pipeline (`ci.yml`)

### Triggers
- Push to `main` or `develop` branches
- Pull requests to `main` branch

### Jobs

#### 1. Unit Tests
- **Purpose**: Run comprehensive unit tests including Hilt integration tests
- **Hilt Considerations**: 
  - Proper KAPT configuration for test compilation
  - Robolectric setup for Android framework dependencies
  - Custom test runners for Hilt testing

#### 2. Build Library
- **Purpose**: Build both library AAR and demo app
- **Optimizations**:
  - Gradle parallel execution
  - KAPT worker API usage
  - Incremental compilation

#### 3. Static Analysis
- **Purpose**: Run lint and code quality checks
- **Includes**: Android lint, Kotlin compiler warnings

#### 4. Hilt Validation
- **Purpose**: Specific validation for Hilt setup
- **Checks**:
  - KAPT annotation processing completion
  - Generated Hilt component verification
  - Integration test execution

#### 5. Instrumented Tests
- **Purpose**: Run UI and integration tests on Android emulator
- **Features**:
  - AVD caching for performance
  - Custom Hilt test runners
  - Compose UI testing

#### 6. Performance Check
- **Purpose**: Monitor build performance and KAPT processing time
- **Metrics**:
  - Cold build time
  - Warm build time
  - KAPT processing duration

## 🚀 Release Pipeline (`release.yml`)

### Triggers
- Git tags matching `v*` pattern
- Manual workflow dispatch with version input

### Release Process

1. **Validation**
   - Semantic version format check
   - Full test suite execution
   - Lint analysis

2. **Build Release**
   - Release AAR generation
   - Demo app APK building
   - Documentation generation

3. **GitHub Release**
   - Automated release notes generation
   - Artifact uploading with checksums
   - Version tagging

4. **Publishing** (Placeholder)
   - Maven Central publishing setup
   - GitHub Packages integration

### Release Artifacts
- `greetinglib-release.aar` - Main library
- `greetinglib-demo-{version}.apk` - Demo application
- `greetinglib-docs-{version}.tar.gz` - API documentation
- `checksums.txt` - File integrity verification

## 🔒 Security Pipeline (`security.yml`)

### Schedule
- Weekly automated runs (Mondays at 9 AM UTC)
- Triggered by dependency file changes

### Security Checks

#### 1. Dependency Vulnerability Scanning
- **Tools**: Trivy vulnerability scanner
- **Scope**: All project dependencies including Hilt
- **Output**: SARIF format for GitHub Security tab

#### 2. Build Security Analysis
- **Checks**:
  - Hardcoded secrets detection
  - Insecure repository URLs
  - Forced dependency versions

#### 3. Hilt-Specific Security
- **Validations**:
  - Proper annotation usage
  - Test isolation verification
  - Singleton scope analysis
  - Dependency tree security

#### 4. Gradle Wrapper Validation
- **Purpose**: Ensure build tool integrity
- **Check**: Official Gradle wrapper validation

## ⚡ Performance Optimizations

### Gradle Configuration
```properties
# gradle.properties
org.gradle.parallel=true
org.gradle.configuration-cache=true
org.gradle.caching=true
kapt.incremental=true
kapt.use.worker.api=true
kapt.include.compile.classpath=false
```

### CI Environment Variables
```yaml
env:
  GRADLE_OPTS: -Dorg.gradle.daemon=false -Dorg.gradle.parallel=true
  KAPT_OPTS: -Dkapt.incremental=true -Dkapt.use.worker.api=true
```

### Caching Strategy
- **Gradle Dependencies**: Cached based on lockfiles
- **Android SDK**: Cached across builds
- **AVD Images**: Cached for instrumented tests
- **Build Cache**: Enabled for faster rebuilds

## 🧪 Hilt Testing Strategy

### Unit Test Configuration
```yaml
- name: Run unit tests
  run: ./gradlew testDebugUnitTest --no-daemon --stacktrace
```

### Integration Test Setup
```yaml
- name: Validate Hilt setup
  run: |
    ./gradlew :greetinglib:kaptDebugKotlin --no-daemon --info
    find . -name "*Hilt*" -type f | head -10
```

### Custom Test Runners
- `HiltTestRunner` for library module
- `HiltTestRunner` for app module
- Robolectric configuration for Android framework

## 📊 Monitoring & Reporting

### Artifacts Generated
1. **Test Reports**: JUnit XML and HTML reports
2. **Lint Reports**: XML and HTML format
3. **Performance Reports**: Gradle build profiles
4. **Security Reports**: Vulnerability scan results
5. **Coverage Reports**: Jacoco test coverage

### Notifications
- ✅ Success notifications with release links
- ❌ Failure notifications with error logs
- 📊 Performance regression alerts

## 🔧 Maintenance

### Weekly Tasks (Automated)
- Dependency vulnerability scanning
- Security analysis updates
- Performance baseline updates

### Manual Tasks
- Review security scan results
- Update dependency versions
- Performance optimization reviews

### Version Management
- Semantic versioning enforcement
- Automated changelog generation
- Release artifact validation

## 🛠️ Troubleshooting

### Common Issues

#### KAPT Timeout
```yaml
# Increase KAPT memory allocation
GRADLE_OPTS: -Xmx4g -Dorg.gradle.jvmargs=-Xmx4g
```

#### Test Failures
```bash
# Run specific test categories
./gradlew testDebugUnitTest --tests "*Hilt*" --info
```

#### Build Performance
```bash
# Profile build performance
./gradlew assembleDebug --profile --info
```

### Debug Commands
```bash
# Check generated Hilt files
find . -name "*Hilt*" -type f

# Validate dependency tree
./gradlew dependencies --configuration debugRuntimeClasspath

# Check KAPT processing
./gradlew kaptDebugKotlin --info
```

## 📚 References

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Gradle Build Tool](https://gradle.org/guides/)
- [Hilt Testing Guide](https://developer.android.com/training/dependency-injection/hilt-testing)
- [Android CI/CD Best Practices](https://developer.android.com/studio/build/optimize-your-build)