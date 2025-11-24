package br.com.renanalencar.greetinglib

import br.com.renanalencar.greetinglib.data.repository.GreetingRepositoryImpl
import br.com.renanalencar.greetinglib.domain.usecase.GetGreetingUseCase
import org.junit.Test
import org.junit.Assert.*

/**
 * Library integration unit test.
 * Tests that the library components work together correctly.
 */
class LibraryIntegrationTest {
    
    @Test
    fun library_integration_should_work_correctly() {
        // Given
        val repository = GreetingRepositoryImpl()
        val useCase = GetGreetingUseCase(repository)
        val testName = "Library Test"
        
        // When
        val greeting = useCase.execute(testName)
        
        // Then
        assertNotNull(greeting)
        assertEquals("Hello, Library Test!", greeting.message)
    }
    
    @Test
    fun library_should_handle_edge_cases() {
        // Given
        val repository = GreetingRepositoryImpl()
        val useCase = GetGreetingUseCase(repository)
        
        // When & Then
        val emptyGreeting = useCase.execute("")
        assertEquals("Hello, !", emptyGreeting.message)
        
        val specialGreeting = useCase.execute("Test & More")
        assertEquals("Hello, Test & More!", specialGreeting.message)
    }
}