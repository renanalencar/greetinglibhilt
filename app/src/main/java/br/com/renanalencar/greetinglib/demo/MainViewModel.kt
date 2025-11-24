package br.com.renanalencar.greetinglib.demo

import androidx.lifecycle.ViewModel
import br.com.renanalencar.greetinglib.domain.usecase.GetGreetingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val useCase: GetGreetingUseCase
) : ViewModel()