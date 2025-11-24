package br.com.renanalencar.greetinglib.domain.usecase

import br.com.renanalencar.greetinglib.domain.model.Greeting
import br.com.renanalencar.greetinglib.domain.repository.GreetingRepository
import org.junit.Test
import org.junit.Assert.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify

/**
 * Unit tests for advanced greeting use cases.
 * Tests qualifier-based dependency injection behavior.
 */
class AdvancedGreetingUseCasesTest {

    private val mockFormalRepository = mock<GreetingRepository>()
    private val mockCasualRepository = mock<GreetingRepository>()

    @Test
    fun getFormalGreetingUseCase_should_use_formal_repository() {
        // Given
        val useCase = GetFormalGreetingUseCase(mockFormalRepository)
        val name = "Mr. Smith"
        val expectedGreeting = Greeting("Good day, Mr. Smith. It is my pleasure to make your acquaintance.")
        whenever(mockFormalRepository.getGreeting(name)).thenReturn(expectedGreeting)

        // When
        val result = useCase.execute(name)

        // Then
        verify(mockFormalRepository).getGreeting(name)
        assertEquals(expectedGreeting, result)
    }

    @Test
    fun getCasualGreetingUseCase_should_use_casual_repository() {
        // Given
        val useCase = GetCasualGreetingUseCase(mockCasualRepository)
        val name = "Alex"
        val expectedGreeting = Greeting("Hey Alex! What's up? 👋")
        whenever(mockCasualRepository.getGreeting(name)).thenReturn(expectedGreeting)

        // When
        val result = useCase.execute(name)

        // Then
        verify(mockCasualRepository).getGreeting(name)
        assertEquals(expectedGreeting, result)
    }

    @Test
    fun getContextualGreetingUseCase_should_use_formal_when_requested() {
        // Given
        val useCase = GetContextualGreetingUseCase(mockFormalRepository, mockCasualRepository)
        val name = "Dr. Johnson"
        val formalGreeting = Greeting("Good day, Dr. Johnson. It is my pleasure to make your acquaintance.")
        whenever(mockFormalRepository.getGreeting(name)).thenReturn(formalGreeting)

        // When
        val result = useCase.execute(name, isFormal = true)

        // Then
        verify(mockFormalRepository).getGreeting(name)
        assertEquals(formalGreeting, result)
    }

    @Test
    fun getContextualGreetingUseCase_should_use_casual_by_default() {
        // Given
        val useCase = GetContextualGreetingUseCase(mockFormalRepository, mockCasualRepository)
        val name = "Sam"
        val casualGreeting = Greeting("Hey Sam! What's up? 👋")
        whenever(mockCasualRepository.getGreeting(name)).thenReturn(casualGreeting)

        // When
        val result = useCase.execute(name) // isFormal defaults to false

        // Then
        verify(mockCasualRepository).getGreeting(name)
        assertEquals(casualGreeting, result)
    }

    @Test
    fun getContextualGreetingUseCase_should_use_casual_when_explicitly_requested() {
        // Given
        val useCase = GetContextualGreetingUseCase(mockFormalRepository, mockCasualRepository)
        val name = "Taylor"
        val casualGreeting = Greeting("Hey Taylor! What's up? 👋")
        whenever(mockCasualRepository.getGreeting(name)).thenReturn(casualGreeting)

        // When
        val result = useCase.execute(name, isFormal = false)

        // Then
        verify(mockCasualRepository).getGreeting(name)
        assertEquals(casualGreeting, result)
    }
}