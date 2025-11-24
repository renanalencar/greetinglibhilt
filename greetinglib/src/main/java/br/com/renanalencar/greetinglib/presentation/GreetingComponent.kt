package br.com.renanalencar.greetinglib.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.renanalencar.greetinglib.data.repository.GreetingRepositoryImpl
import br.com.renanalencar.greetinglib.domain.usecase.GetGreetingUseCase

@Composable
fun GreetingComponent(name: String, useCase: GetGreetingUseCase) {
    val greeting = useCase.execute(name)
    Text(text = greeting.message)
}

@Preview(showBackground = true)
@Composable
fun GreetingComponentPreview() {
    MaterialTheme {
        Surface {
            GreetingComponent(
                name = "Android",
                useCase = GetGreetingUseCase(GreetingRepositoryImpl())
            )
        }
    }
}