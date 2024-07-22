package com.example.exchangeratesvk.domain.entity

data class Currency(
    val currencyName : String,
    val date : String,
    val allCurrenciesList : Map<String,String>
)
