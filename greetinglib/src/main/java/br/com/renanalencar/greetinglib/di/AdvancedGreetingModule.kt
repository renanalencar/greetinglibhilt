package br.com.renanalencar.greetinglib.di

import br.com.renanalencar.greetinglib.data.repository.CasualGreetingRepositoryImpl
import br.com.renanalencar.greetinglib.data.repository.FormalGreetingRepositoryImpl
import br.com.renanalencar.greetinglib.data.repository.GreetingRepositoryImpl
import br.com.renanalencar.greetinglib.domain.qualifier.CasualGreeting
import br.com.renanalencar.greetinglib.domain.qualifier.DefaultGreeting
import br.com.renanalencar.greetinglib.domain.qualifier.FormalGreeting
import br.com.renanalencar.greetinglib.domain.repository.GreetingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Advanced Hilt module demonstrating qualifiers and scopes.
 * Provides multiple implementations of GreetingRepository with different characteristics.
 */
@Module
@InstallIn(SingletonComponent::class)
object AdvancedGreetingModule {

    @Provides
    @Singleton
    @DefaultGreeting
    fun provideDefaultGreetingRepository(): GreetingRepository = GreetingRepositoryImpl()

    @Provides
    @Singleton
    @FormalGreeting
    fun provideFormalGreetingRepository(): GreetingRepository = FormalGreetingRepositoryImpl()

    @Provides
    @Singleton
    @CasualGreeting
    fun provideCasualGreetingRepository(): GreetingRepository = CasualGreetingRepositoryImpl()
}