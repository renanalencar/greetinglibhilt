package br.com.renanalencar.greetinglib.di

import br.com.renanalencar.greetinglib.data.repository.GreetingRepositoryImpl
import br.com.renanalencar.greetinglib.domain.repository.GreetingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.InstallIn
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GreetingModule {

    @Provides
    @Singleton
    fun provideGreetingRepository(): GreetingRepository = GreetingRepositoryImpl()
}