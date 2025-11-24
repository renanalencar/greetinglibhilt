package br.com.renanalencar.greetinglib.domain.qualifier

import javax.inject.Qualifier

/**
 * Hilt qualifier to distinguish between different greeting formats.
 * This allows multiple implementations of the same interface to coexist.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FormalGreeting

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CasualGreeting

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultGreeting