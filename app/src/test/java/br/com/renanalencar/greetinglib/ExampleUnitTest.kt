package br.com.renanalencar.greetinglib.demo

import br.com.renanalencar.greetinglib.data.repository.GreetingRepositoryImpl
import br.com.renanalencar.greetinglib.domain.usecase.GetGreetingUseCase
import org.junit.Test
import org.junit.Assert.*

/**
 * Demo app integration unit test.
 * Tests that the app integrates correctly with the greeting library.
 */
class AppIntegrationUnitTest {
    
    @Test
    fun app_should_integrate_with_greetinglib_correctly() {
        // Given - Create dependencies manually (like in the old manual DI)
        val repository = GreetingRepositoryImpl()
        val useCase = GetGreetingUseCase(repository)
        val viewModel = MainViewModel(useCase)
        
        // When
        val greeting = viewModel.useCase.execute("Demo App")
        
        // Then
        assertNotNull(greeting)
        assertEquals("Hello, Demo App!", greeting.message)
    }
    
    @Test
    fun app_should_handle_android_specific_greeting() {
        // Given
        val repository = GreetingRepositoryImpl()
        val useCase = GetGreetingUseCase(repository)
        val viewModel = MainViewModel(useCase)
        
        // When
        val greeting = viewModel.useCase.execute("Android")
        
        // Then
        assertEquals("Hello, Android!", greeting.message)
    }
}