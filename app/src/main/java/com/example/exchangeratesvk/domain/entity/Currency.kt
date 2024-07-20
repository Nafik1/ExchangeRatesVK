package com.example.exchangeratesvk.domain.entity

import com.google.gson.annotations.SerializedName

data class Currency(
    val currencyName : String,
    val date : String,
    val allCurrenciesList : Map<String,String>
)
