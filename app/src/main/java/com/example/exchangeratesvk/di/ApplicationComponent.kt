package com.example.exchangeratesvk.di

import android.content.Context
import com.example.exchangeratesvk.presentation.MainActivity
import com.example.exchangeratesvk.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Inject

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun getViewModelFactory() : ViewModelFactory

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context : Context
        ) : ApplicationComponent
    }

}