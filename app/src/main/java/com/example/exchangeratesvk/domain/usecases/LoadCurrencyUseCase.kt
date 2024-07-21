package com.example.exchangeratesvk.domain.usecases

import com.example.exchangeratesvk.data.Repository.ExchangeRateRepositoryImpl
import com.example.exchangeratesvk.domain.entity.Currency
import com.example.exchangeratesvk.domain.entity.ExhangeRateState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadCurrencyUseCase @Inject constructor(
    private val repositoryImpl: ExchangeRateRepositoryImpl
) {

    operator fun invoke() : Flow<ExhangeRateState<Currency>> {
        return repositoryImpl.getCurrencyList("USD")
    }
}