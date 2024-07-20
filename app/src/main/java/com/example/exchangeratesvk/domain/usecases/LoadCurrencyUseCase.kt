package com.example.exchangeratesvk.domain.usecases

import com.example.exchangeratesvk.data.Repository.ExchangeRateRepositoryImpl
import javax.inject.Inject

class LoadCurrencyUseCase @Inject constructor(
    private val repositoryImpl: ExchangeRateRepositoryImpl
) {

}