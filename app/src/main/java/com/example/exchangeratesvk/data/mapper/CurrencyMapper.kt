package com.example.exchangeratesvk.data.mapper

import com.example.exchangeratesvk.data.model.CurrencyDto
import com.example.exchangeratesvk.domain.entity.Currency
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CurrencyMapper @Inject constructor() {

    fun mapCurrencyDtoToCurrency(currencyDto: CurrencyDto) : Currency {
        return Currency(
            currencyName = currencyDto.currencyName,
            date = formatDateString(currencyDto.date),
            allCurrenciesList = currencyDto.allCurrenciesList
        )
    }

    fun formatDateString(dateString: String): String {
        val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }
}