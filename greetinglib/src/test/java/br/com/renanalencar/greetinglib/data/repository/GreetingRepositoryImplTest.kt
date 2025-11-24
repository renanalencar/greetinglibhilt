package br.com.renanalencar.greetinglib.data.repository

import br.com.renanalencar.greetinglib.domain.model.Greeting
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for GreetingRepositoryImpl.
 * Tests repository implementation behavior.
 */
class GreetingRepositoryImplTest {

    private val repository = GreetingRepositoryImpl()

    @Test
    fun getGreeting_should_return_greeting_with_correct_format() {
        // Given
        val name = "World"
        val expectedMessage = "Hello, World!"

        // When
        val greeting = repository.getGreeting(name)

        // Then
        assertNotNull(greeting)
        assertEquals(expectedMessage, greeting.message)
    }

    @Test
    fun getGreeting_with_empty_name_should_handle_gracefully() {
        // Given
        val emptyName = ""
        val expectedMessage = "Hello, !"

        // When
        val greeting = repository.getGreeting(emptyName)

        // Then
        assertNotNull(greeting)
        assertEquals(expectedMessage, greeting.message)
    }

    @Test
    fun getGreeting_with_special_characters_should_work() {
        // Given
        val specialName = "João & María"
        val expectedMessage = "Hello, João & María!"

        // When
        val greeting = repository.getGreeting(specialName)

        // Then
        assertNotNull(greeting)
        assertEquals(expectedMessage, greeting.message)
    }

    @Test
    fun getGreeting_with_very_long_name_should_work() {
        // Given
        val longName = "A".repeat(1000)
        val expectedMessage = "Hello, $longName!"

        // When
        val greeting = repository.getGreeting(longName)

        // Then
        assertNotNull(greeting)
        assertEquals(expectedMessage, greeting.message)
    }

    @Test
    fun getGreeting_multiple_calls_should_return_consistent_results() {
        // Given
        val name = "TestUser"
        val expectedMessage = "Hello, TestUser!"

        // When
        val greeting1 = repository.getGreeting(name)
        val greeting2 = repository.getGreeting(name)

        // Then
        assertEquals(greeting1, greeting2)
        assertEquals(expectedMessage, greeting1.message)
        assertEquals(expectedMessage, greeting2.message)
    }

    @Test
    fun getGreeting_with_different_names_should_return_different_greetings() {
        // Given
        val name1 = "Alice"
        val name2 = "Bob"

        // When
        val greeting1 = repository.getGreeting(name1)
        val greeting2 = repository.getGreeting(name2)

        // Then
        assertNotEquals(greeting1, greeting2)
        assertEquals("Hello, Alice!", greeting1.message)
        assertEquals("Hello, Bob!", greeting2.message)
    }

    @Test
    fun getGreeting_with_unicode_characters_should_work() {
        // Given
        val unicodeName = "こんにちは 🌍"
        val expectedMessage = "Hello, こんにちは 🌍!"

        // When
        val greeting = repository.getGreeting(unicodeName)

        // Then
        assertNotNull(greeting)
        assertEquals(expectedMessage, greeting.message)
    }
}