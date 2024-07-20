package com.example.exchangeratesvk.presentation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.exchangeratesvk.di.ApplicationComponent
import com.example.exchangeratesvk.di.DaggerApplicationComponent

class ExchangeApplication : Application() {

    val component : ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}

@Composable
fun getApplicationComponent() : ApplicationComponent {
    return (LocalContext.current.applicationContext as ExchangeApplication).component
}