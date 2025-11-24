package br.com.renanalencar.greetinglib.demo

import br.com.renanalencar.greetinglib.domain.repository.GreetingRepository
import br.com.renanalencar.greetinglib.domain.usecase.GetGreetingUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Integration tests for Hilt dependency injection in the demo app.
 * Tests that all dependencies from both modules are correctly wired.
 */
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class AppIntegrationTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var greetingRepository: GreetingRepository

    @Inject
    lateinit var getGreetingUseCase: GetGreetingUseCase

    @Test
    fun hilt_should_inject_all_dependencies() {
        // Given
        hiltRule.inject()

        // Then
        assertNotNull(greetingRepository)
        assertNotNull(getGreetingUseCase)
    }

    @Test
    fun end_to_end_greeting_flow_should_work() {
        // Given
        hiltRule.inject()
        val testName = "Integration Test"

        // When
        val greeting = getGreetingUseCase.execute(testName)

        // Then
        assertNotNull(greeting)
        assertEquals("Hello, Integration Test!", greeting.message)
    }

    @Test
    fun dependencies_should_be_properly_wired() {
        // Given
        hiltRule.inject()

        // When
        val directGreeting = greetingRepository.getGreeting("Direct")
        val useCaseGreeting = getGreetingUseCase.execute("UseCase")

        // Then
        assertEquals("Hello, Direct!", directGreeting.message)
        assertEquals("Hello, UseCase!", useCaseGreeting.message)
    }
}