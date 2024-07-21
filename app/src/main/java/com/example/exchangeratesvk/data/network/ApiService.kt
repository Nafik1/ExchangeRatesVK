package com.example.exchangeratesvk.data.network

import com.example.exchangeratesvk.BuildConfig
import com.example.exchangeratesvk.data.model.CurrencyDto
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("${BuildConfig.API_KEY}/latest/{currency}")
    suspend fun getCurrencyList(
         @Path("currency") currencyName : String
    ) : CurrencyDto

}