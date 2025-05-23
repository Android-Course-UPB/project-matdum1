package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.local.dao.ExchangeRateDao
import com.example.currencyconverter.data.local.entity.ExchangeRate
import kotlinx.coroutines.flow.Flow


class ExchangeRateRepository(
    private val exchangeRateDao: ExchangeRateDao
) {

    fun getExchangeRatesForCurrency(baseCurrency: String): Flow<List<ExchangeRate>> {
        return exchangeRateDao.getExchangeRatesForCurrency(baseCurrency)
    }

    fun insertExchangeRates(rates: List<ExchangeRate>) {
        exchangeRateDao.insertExchangeRates(rates)
    }
}