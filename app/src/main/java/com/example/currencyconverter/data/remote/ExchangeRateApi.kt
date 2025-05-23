package com.example.currencyconverter.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRateApi {
    @GET("/v6/{apiKey}/latest/{baseCurrency}")
    suspend fun getExchangeRates(
        @Path("apiKey") apiKey: String,
        @Path("baseCurrency") baseCurrency: String
    ): ExchangeRateResponse
}