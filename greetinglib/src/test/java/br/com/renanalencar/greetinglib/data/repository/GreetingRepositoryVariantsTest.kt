package br.com.renanalencar.greetinglib.data.repository

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for greeting repository variants.
 * Tests different greeting implementations.
 */
class GreetingRepositoryVariantsTest {

    @Test
    fun formalGreetingRepositoryImpl_should_provide_formal_greetings() {
        // Given
        val repository = FormalGreetingRepositoryImpl()
        val name = "Dr. Anderson"

        // When
        val greeting = repository.getGreeting(name)

        // Then
        assertNotNull(greeting)
        assertTrue(greeting.message.contains("Good day"))
        assertTrue(greeting.message.contains("Dr. Anderson"))
        assertTrue(greeting.message.contains("pleasure"))
        assertEquals("Good day, Dr. Anderson. It is my pleasure to make your acquaintance.", greeting.message)
    }

    @Test
    fun casualGreetingRepositoryImpl_should_provide_casual_greetings() {
        // Given
        val repository = CasualGreetingRepositoryImpl()
        val name = "Chris"

        // When
        val greeting = repository.getGreeting(name)

        // Then
        assertNotNull(greeting)
        assertTrue(greeting.message.contains("Hey"))
        assertTrue(greeting.message.contains("Chris"))
        assertTrue(greeting.message.contains("👋"))
        assertEquals("Hey Chris! What's up? 👋", greeting.message)
    }

    @Test
    fun formal_and_casual_repositories_should_produce_different_greetings() {
        // Given
        val formalRepository = FormalGreetingRepositoryImpl()
        val casualRepository = CasualGreetingRepositoryImpl()
        val name = "Jordan"

        // When
        val formalGreeting = formalRepository.getGreeting(name)
        val casualGreeting = casualRepository.getGreeting(name)

        // Then
        assertNotEquals(formalGreeting.message, casualGreeting.message)
        assertTrue(formalGreeting.message.length > casualGreeting.message.length)
    }

    @Test
    fun repositories_should_handle_empty_names() {
        // Given
        val formalRepository = FormalGreetingRepositoryImpl()
        val casualRepository = CasualGreetingRepositoryImpl()
        val emptyName = ""

        // When
        val formalGreeting = formalRepository.getGreeting(emptyName)
        val casualGreeting = casualRepository.getGreeting(emptyName)

        // Then
        assertNotNull(formalGreeting)
        assertNotNull(casualGreeting)
        assertTrue(formalGreeting.message.contains("Good day"))
        assertTrue(casualGreeting.message.contains("Hey"))
    }

    @Test
    fun repositories_should_handle_special_characters() {
        // Given
        val formalRepository = FormalGreetingRepositoryImpl()
        val casualRepository = CasualGreetingRepositoryImpl()
        val specialName = "José & María"

        // When
        val formalGreeting = formalRepository.getGreeting(specialName)
        val casualGreeting = casualRepository.getGreeting(specialName)

        // Then
        assertTrue(formalGreeting.message.contains(specialName))
        assertTrue(casualGreeting.message.contains(specialName))
    }
}