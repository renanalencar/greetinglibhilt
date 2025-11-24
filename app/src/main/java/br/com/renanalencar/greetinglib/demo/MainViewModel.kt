package br.com.renanalencar.greetinglib.demo

import androidx.lifecycle.ViewModel
import br.com.renanalencar.greetinglib.data.repository.GreetingRepositoryImpl
import br.com.renanalencar.greetinglib.domain.usecase.GetGreetingUseCase

class MainViewModel : ViewModel() {
    private val repository = GreetingRepositoryImpl()
    val useCase = GetGreetingUseCase(repository)
}