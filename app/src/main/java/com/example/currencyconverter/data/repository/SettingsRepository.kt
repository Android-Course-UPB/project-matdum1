package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.local.dao.SettingsDao
import com.example.currencyconverter.data.local.entity.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(
    private val settingsDao: SettingsDao
) {

    fun getSettings(): Flow<Settings> = settingsDao.getSettings()
            .map { it ?: Settings() }

    suspend fun save(settings: Settings) {
        settingsDao.save(settings)
    }

    suspend fun updateName(name: String) {
        settingsDao.updateName(name)
    }

    suspend fun updateDefaultBaseCurrency(currency: String) {
        settingsDao.updateDefaultBaseCurrency(currency)
    }

    suspend fun updateDefaultTargetCurrency(currency: String) {
        settingsDao.updateDefaultTargetCurrency(currency)
    }
}