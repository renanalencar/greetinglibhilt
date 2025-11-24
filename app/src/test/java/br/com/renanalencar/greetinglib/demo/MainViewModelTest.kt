package br.com.renanalencar.greetinglib.demo

import br.com.renanalencar.greetinglib.domain.model.Greeting
import br.com.renanalencar.greetinglib.domain.usecase.GetGreetingUseCase
import org.junit.Test
import org.junit.Assert.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * Unit tests for MainViewModel.
 * Tests ViewModel initialization and use case integration.
 */
class MainViewModelTest {

    private val mockUseCase = mock<GetGreetingUseCase>()
    private val viewModel = MainViewModel(mockUseCase)

    @Test
    fun viewModel_should_initialize_with_use_case() {
        // Then
        assertNotNull(viewModel.useCase)
        assertEquals(mockUseCase, viewModel.useCase)
    }

    @Test
    fun viewModel_use_case_should_work_correctly() {
        // Given
        val testName = "ViewModel Test"
        val expectedGreeting = Greeting("Hello, ViewModel Test!")
        whenever(mockUseCase.execute(testName)).thenReturn(expectedGreeting)

        // When
        val result = viewModel.useCase.execute(testName)

        // Then
        assertEquals(expectedGreeting, result)
    }

    @Test
    fun viewModel_should_handle_different_greetings() {
        // Given
        val names = listOf("Alice", "Bob", "Charlie")
        val greetings = names.map { Greeting("Hello, $it!") }
        
        names.forEachIndexed { index, name ->
            whenever(mockUseCase.execute(name)).thenReturn(greetings[index])
        }

        // When & Then
        names.forEachIndexed { index, name ->
            val result = viewModel.useCase.execute(name)
            assertEquals(greetings[index], result)
        }
    }

    @Test
    fun viewModel_should_be_lifecycle_aware() {
        // Then
        assertTrue(viewModel is androidx.lifecycle.ViewModel)
    }
}