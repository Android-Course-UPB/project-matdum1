package com.example.currencyconverter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.currencyconverter.data.local.entity.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings WHERE id = 1")
    fun getSettings(): Flow<Settings?>

    @Insert(onConflict = REPLACE)
    suspend fun save(settings: Settings)

    @Query("UPDATE settings SET name = :name WHERE id = 1")
    suspend fun updateName(name: String)

    @Query("UPDATE settings SET defaultBaseCurrency = :currency WHERE id = 1")
    suspend fun updateDefaultBaseCurrency(currency: String)

    @Query("UPDATE settings SET defaultTargetCurrency = :currency WHERE id = 1")
    suspend fun updateDefaultTargetCurrency(currency: String)
}