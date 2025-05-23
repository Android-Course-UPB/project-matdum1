package com.example.currencyconverter.data.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDataSource(private val exchangeRateApi: ExchangeRateApi) {
    private val API_TOKEN = "3265ae34bb1bf6c6d2884bed"

    suspend fun fetchExchangeRates(baseCurrency: String): ExchangeRateResponse? {
        return withContext(Dispatchers.IO) {
            try {
                exchangeRateApi.getExchangeRates(API_TOKEN, baseCurrency)
            } catch (e: Exception) {
                Log.e("RemoteDataSource", "Error fetching exchange rates")
                e.printStackTrace()
                null
            }
        }
    }
}