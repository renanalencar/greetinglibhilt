package br.com.renanalencar.greetinglib.demo.di

import br.com.renanalencar.greetinglib.domain.repository.GreetingRepository
import br.com.renanalencar.greetinglib.domain.usecase.GetGreetingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGetGreetingUseCase(repository: GreetingRepository): GetGreetingUseCase {
        return GetGreetingUseCase(repository)
    }
}