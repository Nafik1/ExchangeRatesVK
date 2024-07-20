package com.example.exchangeratesvk.di

import com.example.exchangeratesvk.data.Repository.ExchangeRateRepository
import com.example.exchangeratesvk.data.Repository.ExchangeRateRepositoryImpl
import com.example.exchangeratesvk.data.network.ApiFactory
import com.example.exchangeratesvk.data.network.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl : ExchangeRateRepositoryImpl) : ExchangeRateRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiservice
        }
    }
}