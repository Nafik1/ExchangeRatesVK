package com.example.exchangeratesvk.di

import androidx.lifecycle.ViewModel
import com.example.exchangeratesvk.presentation.mainscreen.ExchangeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(ExchangeViewModel::class)
    @Binds
    fun bindExchangeViewModel(viewModel : ExchangeViewModel) : ViewModel
}