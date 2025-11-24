package br.com.renanalencar.greetinglib.domain.usecase

import br.com.renanalencar.greetinglib.domain.model.Greeting
import br.com.renanalencar.greetinglib.domain.repository.GreetingRepository
import org.junit.Test
import org.junit.Assert.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify

/**
 * Unit tests for GetGreetingUseCase.
 * Tests use case functionality with mocked repository.
 */
class GetGreetingUseCaseTest {

    private val mockRepository = mock<GreetingRepository>()
    private val useCase = GetGreetingUseCase(mockRepository)

    @Test
    fun execute_should_call_repository_and_return_greeting() {
        // Given
        val name = "John"
        val expectedGreeting = Greeting("Olá, John!")
        whenever(mockRepository.getGreeting(name)).thenReturn(expectedGreeting)

        // When
        val result = useCase.execute(name)

        // Then
        verify(mockRepository).getGreeting(name)
        assertEquals(expectedGreeting, result)
    }

    @Test
    fun execute_with_empty_name_should_pass_to_repository() {
        // Given
        val emptyName = ""
        val expectedGreeting = Greeting("Olá, !")
        whenever(mockRepository.getGreeting(emptyName)).thenReturn(expectedGreeting)

        // When
        val result = useCase.execute(emptyName)

        // Then
        verify(mockRepository).getGreeting(emptyName)
        assertEquals(expectedGreeting, result)
    }

    @Test
    fun execute_with_special_characters_should_work() {
        // Given
        val specialName = "María José"
        val expectedGreeting = Greeting("Olá, María José!")
        whenever(mockRepository.getGreeting(specialName)).thenReturn(expectedGreeting)

        // When
        val result = useCase.execute(specialName)

        // Then
        verify(mockRepository).getGreeting(specialName)
        assertEquals(expectedGreeting, result)
    }

    @Test
    fun execute_multiple_calls_should_delegate_to_repository() {
        // Given
        val names = listOf("Alice", "Bob", "Charlie")
        val greetings = names.map { Greeting("Olá, $it!") }
        
        names.forEachIndexed { index, name ->
            whenever(mockRepository.getGreeting(name)).thenReturn(greetings[index])
        }

        // When
        val results = names.map { useCase.execute(it) }

        // Then
        names.forEach { name ->
            verify(mockRepository).getGreeting(name)
        }
        assertEquals(greetings, results)
    }
}