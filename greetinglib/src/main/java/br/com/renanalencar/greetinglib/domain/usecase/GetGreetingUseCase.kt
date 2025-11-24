package br.com.renanalencar.greetinglib.domain.usecase

import br.com.renanalencar.greetinglib.domain.model.Greeting
import br.com.renanalencar.greetinglib.domain.repository.GreetingRepository

class GetGreetingUseCase(private val repository: GreetingRepository) {
    fun execute(name: String): Greeting = repository.getGreeting(name)
}