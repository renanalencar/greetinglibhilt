package br.com.renanalencar.greetinglib.domain.scope

import javax.inject.Scope

/**
 * Custom Hilt scope for user session-related dependencies.
 * Dependencies with this scope will live for the duration of a user session.
 */
@Scope
@Retention(AnnotationRetention.BINARY)
annotation class UserSessionScoped