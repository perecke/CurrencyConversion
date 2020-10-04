package com.julia.szelesjulia.myapplication.network

import com.julia.szelesjulia.myapplication.datamodel.CurrencyModel
import com.julia.szelesjulia.myapplication.datamodel.CurrencyRateModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api interface
 */
interface CurrencyInterface {
    @GET("list")
    suspend fun getCurrenciesList(@Query("access_key") access_key: String
    ): Response<CurrencyModel>

    @GET("api/live")
    suspend fun getCurrencyRate(
        @Query("access_key") access_key: String
    ): Response<CurrencyRateModel>
}