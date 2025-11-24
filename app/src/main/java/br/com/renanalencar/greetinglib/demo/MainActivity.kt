package br.com.renanalencar.greetinglib.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.renanalencar.greetinglib.data.repository.GreetingRepositoryImpl
import br.com.renanalencar.greetinglib.demo.ui.theme.GreetinglibTheme
import br.com.renanalencar.greetinglib.domain.usecase.GetGreetingUseCase
import br.com.renanalencar.greetinglib.presentation.GreetingComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreetinglibTheme {
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent() {
    val viewModel: MainViewModel = viewModel()
    
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            GreetingComponent(name = "John", useCase = viewModel.useCase)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    GreetinglibTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                GreetingComponent(
                    name = "John", 
                    useCase = GetGreetingUseCase(GreetingRepositoryImpl())
                )
            }
        }
    }
}