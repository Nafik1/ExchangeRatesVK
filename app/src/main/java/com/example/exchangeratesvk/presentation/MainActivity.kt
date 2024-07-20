package com.example.exchangeratesvk.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.exchangeratesvk.presentation.mainscreen.ExchangeViewModel
import com.example.exchangeratesvk.ui.theme.ExchangeRatesVKTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val component = getApplicationComponent()
            val viewModel : ExchangeViewModel = viewModel(factory = component.getViewModelFactory())
            ExchangeRatesVKTheme {
                BasicScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}
