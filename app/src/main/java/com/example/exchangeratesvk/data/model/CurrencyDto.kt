package com.example.exchangeratesvk.data.model

import com.google.gson.annotations.SerializedName

data class CurrencyDto(
    @SerializedName("base_code") val currencyName : String,
    @SerializedName("time_last_update_utc") val date : String,
    @SerializedName("conversion_rates") val allCurrenciesList : Map<String,String>
)