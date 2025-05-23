package com.example.currencyconverter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.currencyconverter.data.local.entity.ExchangeRate
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeRateDao {
    @Insert(onConflict = REPLACE)
    fun insertExchangeRates(rates: List<ExchangeRate>)

    @Query("SELECT * FROM exchange_rates WHERE baseCurrency = :currencyCode")
    fun getExchangeRatesForCurrency(currencyCode: String): Flow<List<ExchangeRate>>
}