package com.example.currencyconverter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyconverter.data.local.dao.CurrencyDao
import com.example.currencyconverter.data.local.dao.ExchangeRateDao
import com.example.currencyconverter.data.local.dao.SettingsDao
import com.example.currencyconverter.data.local.entity.Currency
import com.example.currencyconverter.data.local.entity.ExchangeRate
import com.example.currencyconverter.data.local.entity.Settings

@Database(
    entities = [
        Currency::class,
        ExchangeRate::class,
        Settings::class],
    version = 9,
    exportSchema = false
)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun exchangeRateDao(): ExchangeRateDao
    abstract fun settingsDao(): SettingsDao
}