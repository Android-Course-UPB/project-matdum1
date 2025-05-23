package com.example.currencyconverter.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey
    val id: Long = 1,
    var name: String = "",
    var defaultBaseCurrency: String = "USD",
    var defaultTargetCurrency: String = "EUR",
    var firstAccess: Boolean = true
)
