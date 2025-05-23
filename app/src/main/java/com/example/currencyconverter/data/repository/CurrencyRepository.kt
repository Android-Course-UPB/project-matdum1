package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.local.dao.CurrencyDao
import com.example.currencyconverter.data.local.entity.Currency
import kotlinx.coroutines.flow.Flow

class CurrencyRepository(
    private val currencyDao: CurrencyDao
) {

    fun getAllCurrencies(): Flow<List<Currency>> {
        return currencyDao.getAllCurrencies()
    }

    suspend fun insertCurrencies(currencies: List<Currency>) {
        currencyDao.insertCurrencies(currencies)
    }

}
