package br.com.renanalencar.greetinglib.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.renanalencar.greetinglib.data.repository.GreetingRepositoryImpl
import br.com.renanalencar.greetinglib.domain.usecase.GetGreetingUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented tests for GreetingComponent UI.
 * Tests Compose UI behavior with real Hilt dependencies.
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class GreetingComponentInstrumentedTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun greetingComponent_should_display_greeting() {
        // Given
        hiltRule.inject()
        val useCase = GetGreetingUseCase(GreetingRepositoryImpl())
        val testName = "UI Test"

        // When
        composeTestRule.setContent {
            GreetingComponent(useCase = useCase, name = testName)
        }

        // Then
        composeTestRule.onNodeWithText("Hello, UI Test!").assertIsDisplayed()
    }

    @Test
    fun greetingComponent_with_empty_name_should_display_correctly() {
        // Given
        hiltRule.inject()
        val useCase = GetGreetingUseCase(GreetingRepositoryImpl())
        val emptyName = ""

        // When
        composeTestRule.setContent {
            GreetingComponent(useCase = useCase, name = emptyName)
        }

        // Then
        composeTestRule.onNodeWithText("Hello, !").assertIsDisplayed()
    }

    @Test
    fun greetingComponent_with_special_characters_should_display_correctly() {
        // Given
        hiltRule.inject()
        val useCase = GetGreetingUseCase(GreetingRepositoryImpl())
        val specialName = "José & María 🌍"

        // When
        composeTestRule.setContent {
            GreetingComponent(useCase = useCase, name = specialName)
        }

        // Then
        composeTestRule.onNodeWithText("Hello, José & María 🌍!").assertIsDisplayed()
    }
}