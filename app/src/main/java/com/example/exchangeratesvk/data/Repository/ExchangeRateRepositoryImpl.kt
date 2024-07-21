package com.example.exchangeratesvk.data.Repository

import com.example.exchangeratesvk.data.mapper.CurrencyMapper
import com.example.exchangeratesvk.data.model.CurrencyDto
import com.example.exchangeratesvk.data.network.ApiFactory
import com.example.exchangeratesvk.data.network.ApiService
import com.example.exchangeratesvk.domain.entity.Currency
import com.example.exchangeratesvk.domain.entity.ExhangeRateState
import com.example.exchangeratesvk.domain.entity.asResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class ExchangeRateRepositoryImpl @Inject constructor(
    private val apiservice : ApiService,
    private val mapper : CurrencyMapper
) : ExchangeRateRepository {

    override fun getCurrencyList(currencyName: String): Flow<ExhangeRateState<Currency>> {
        return flow {
            emit(apiservice.getCurrencyList(currencyName))
        }.map { mapper.mapCurrencyDtoToCurrency(it) }
            .asResult()
    }
}