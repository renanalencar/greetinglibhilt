package br.com.renanalencar.greetinglib.domain.model

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for Greeting domain model.
 * Tests data class properties, equality, and string representation.
 */
class GreetingTest {

    @Test
    fun greeting_creation_should_store_message_correctly() {
        // Given
        val message = "Hello, World!"
        
        // When
        val greeting = Greeting(message)
        
        // Then
        assertEquals(message, greeting.message)
    }

    @Test
    fun greeting_equality_should_work_correctly() {
        // Given
        val message = "Hello, Test!"
        val greeting1 = Greeting(message)
        val greeting2 = Greeting(message)
        val greeting3 = Greeting("Different message")
        
        // Then
        assertEquals(greeting1, greeting2)
        assertNotEquals(greeting1, greeting3)
        assertEquals(greeting1.hashCode(), greeting2.hashCode())
        assertNotEquals(greeting1.hashCode(), greeting3.hashCode())
    }

    @Test
    fun greeting_toString_should_return_expected_format() {
        // Given
        val message = "Hello, toString!"
        val greeting = Greeting(message)
        
        // When
        val result = greeting.toString()
        
        // Then
        assertTrue(result.contains("Greeting"))
        assertTrue(result.contains(message))
    }

    @Test
    fun greeting_copy_should_work_correctly() {
        // Given
        val originalMessage = "Original message"
        val newMessage = "New message"
        val greeting = Greeting(originalMessage)
        
        // When
        val copiedGreeting = greeting.copy(message = newMessage)
        
        // Then
        assertEquals(originalMessage, greeting.message)
        assertEquals(newMessage, copiedGreeting.message)
        assertNotEquals(greeting, copiedGreeting)
    }

    @Test
    fun greeting_with_empty_message_should_work() {
        // Given
        val emptyMessage = ""
        
        // When
        val greeting = Greeting(emptyMessage)
        
        // Then
        assertEquals(emptyMessage, greeting.message)
    }

    @Test
    fun greeting_with_special_characters_should_work() {
        // Given
        val specialMessage = "Olá! こんにちは 🌍 @#$%^&*()"
        
        // When
        val greeting = Greeting(specialMessage)
        
        // Then
        assertEquals(specialMessage, greeting.message)
    }
}