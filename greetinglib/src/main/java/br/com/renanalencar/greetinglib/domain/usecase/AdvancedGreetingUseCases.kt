package br.com.renanalencar.greetinglib.domain.usecase

import br.com.renanalencar.greetinglib.domain.model.Greeting
import br.com.renanalencar.greetinglib.domain.qualifier.CasualGreeting
import br.com.renanalencar.greetinglib.domain.qualifier.FormalGreeting
import br.com.renanalencar.greetinglib.domain.repository.GreetingRepository
import javax.inject.Inject

/**
 * Use case for generating formal business greetings.
 * Demonstrates the use of Hilt qualifiers for dependency injection.
 */
class GetFormalGreetingUseCase @Inject constructor(
    @FormalGreeting private val repository: GreetingRepository
) {
    fun execute(name: String): Greeting = repository.getGreeting(name)
}

/**
 * Use case for generating casual, friendly greetings.
 * Demonstrates the use of Hilt qualifiers for dependency injection.
 */
class GetCasualGreetingUseCase @Inject constructor(
    @CasualGreeting private val repository: GreetingRepository
) {
    fun execute(name: String): Greeting = repository.getGreeting(name)
}

/**
 * Composite use case that demonstrates advanced Hilt features.
 * Shows how to inject multiple qualified dependencies.
 */
class GetContextualGreetingUseCase @Inject constructor(
    @FormalGreeting private val formalRepository: GreetingRepository,
    @CasualGreeting private val casualRepository: GreetingRepository
) {
    /**
     * Returns a greeting based on the specified formality level.
     * @param name The name to greet
     * @param isFormal Whether to use formal or casual greeting
     */
    fun execute(name: String, isFormal: Boolean = false): Greeting {
        return if (isFormal) {
            formalRepository.getGreeting(name)
        } else {
            casualRepository.getGreeting(name)
        }
    }
}