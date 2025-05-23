package com.example.currencyconverter.data.remote

data class ExchangeRateResponse(
    val conversion_rates: Map<String, Double>
)
