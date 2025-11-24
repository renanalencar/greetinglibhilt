package br.com.renanalencar.greetinglib.data.repository

import br.com.renanalencar.greetinglib.domain.model.Greeting
import br.com.renanalencar.greetinglib.domain.repository.GreetingRepository

class GreetingRepositoryImpl : GreetingRepository {
    override fun getGreeting(name: String) = Greeting("Hello, $name!")
}