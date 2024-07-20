package com.example.exchangeratesvk.data.mapper

import com.example.exchangeratesvk.data.model.CurrencyDto
import com.example.exchangeratesvk.domain.entity.Currency
import javax.inject.Inject

class CurrencyMapper @Inject constructor() {

    fun mapCurrencyDtoToCurrency(currencyDto: CurrencyDto) : Currency {
        return Currency(
            currencyName = currencyDto.currencyName,
            date = currencyDto.date,
            allCurrenciesList = currencyDto.allCurrenciesList
        )
    }
}