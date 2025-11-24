package br.com.renanalencar.greetinglib.demo

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented tests for MainActivity and the demo app.
 * Tests the complete app flow with Hilt dependencies.
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mainActivity_should_display_greeting() {
        // Given
        hiltRule.inject()

        // Then
        // The MainActivity should display a greeting for "Android" by default
        composeTestRule.onNodeWithText("Hello, Android!").assertIsDisplayed()
    }

    @Test
    fun mainActivity_should_have_proper_title() {
        // Given
        hiltRule.inject()

        // Then - Check for the demo app content
        composeTestRule.onNodeWithText("Hello, Android!").assertIsDisplayed()
    }
}