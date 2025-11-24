package br.com.renanalencar.greetinglib.di

import br.com.renanalencar.greetinglib.data.repository.GreetingRepositoryImpl
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
 * Integration tests for Hilt dependency injection in GreetingModule.
 * Tests that all dependencies are correctly wired.
 */
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class GreetingModuleTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var greetingRepository: GreetingRepository

    @Inject
    lateinit var getGreetingUseCase: GetGreetingUseCase

    @Test
    fun hilt_should_inject_greeting_repository() {
        // Given
        hiltRule.inject()

        // Then
        assertNotNull(greetingRepository)
        assertTrue(greetingRepository is GreetingRepositoryImpl)
    }

    @Test
    fun hilt_should_inject_get_greeting_use_case() {
        // Given
        hiltRule.inject()

        // Then
        assertNotNull(getGreetingUseCase)
    }

    @Test
    fun injected_dependencies_should_work_together() {
        // Given
        hiltRule.inject()
        val testName = "Hilt Test"

        // When
        val greeting = getGreetingUseCase.execute(testName)

        // Then
        assertNotNull(greeting)
        assertEquals("Hello, Hilt Test!", greeting.message)
    }

    @Test
    fun repository_should_be_singleton() {
        // Given
        hiltRule.inject()

        // When
        val repository1 = greetingRepository
        val repository2 = greetingRepository

        // Then
        assertSame(repository1, repository2)
    }
}