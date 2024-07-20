package com.example.exchangeratesvk.data.network

import com.example.exchangeratesvk.data.model.CurrencyDto
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("203f61e43eba18fbbf01da8e/latest/{currency}")
    suspend fun getCurrencyList(
         @Path("currency") currencyName : String
    ) : CurrencyDto

    companion object {
        private const val param = "USD"
    }
}