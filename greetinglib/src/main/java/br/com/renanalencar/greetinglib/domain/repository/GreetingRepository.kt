package br.com.renanalencar.greetinglib.domain.repository

import br.com.renanalencar.greetinglib.domain.model.Greeting

interface GreetingRepository {
    fun getGreeting(name: String): Greeting
}