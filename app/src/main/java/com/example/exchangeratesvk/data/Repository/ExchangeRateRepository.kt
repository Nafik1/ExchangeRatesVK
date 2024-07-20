package com.example.exchangeratesvk.data.Repository

import com.example.exchangeratesvk.domain.entity.Currency
import com.example.exchangeratesvk.domain.entity.ExhangeRateState
import kotlinx.coroutines.flow.Flow

interface ExchangeRateRepository {

    fun getCurrencyList(currencyName : String) : Flow<ExhangeRateState<Currency>>
}