package br.com.renanalencar.greetinglib.data.repository

import br.com.renanalencar.greetinglib.domain.model.Greeting
import br.com.renanalencar.greetinglib.domain.repository.GreetingRepository

/**
 * Formal implementation of GreetingRepository.
 * Provides professional, formal greeting messages.
 */
class FormalGreetingRepositoryImpl : GreetingRepository {
    override fun getGreeting(name: String) = Greeting("Good day, $name. It is my pleasure to make your acquaintance.")
}

/**
 * Casual implementation of GreetingRepository.
 * Provides friendly, informal greeting messages.
 */
class CasualGreetingRepositoryImpl : GreetingRepository {
    override fun getGreeting(name: String) = Greeting("Hey $name! What's up? 👋")
}