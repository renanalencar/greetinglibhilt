package br.com.renanalencar.greetinglib.domain.usecase

import br.com.renanalencar.greetinglib.domain.model.Greeting
import br.com.renanalencar.greetinglib.domain.repository.GreetingRepository
import javax.inject.Inject

class GetGreetingUseCase @Inject constructor(
    private val repository: GreetingRepository
) {
    fun execute(name: String): Greeting = repository.getGreeting(name)
}