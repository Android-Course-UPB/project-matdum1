package com.example.currencyconverter.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "exchange_rates",
    foreignKeys = [
        ForeignKey(entity = Currency::class, parentColumns = ["code"], childColumns = ["baseCurrency"]),
        ForeignKey(entity = Currency::class, parentColumns = ["code"], childColumns = ["targetCurrency"])
    ],
    indices = [Index(value = ["baseCurrency"]), Index(value = ["targetCurrency"])]
)
data class ExchangeRate(
    @PrimaryKey(autoGenerate = true) val id: Long = 1,
    val baseCurrency: String,
    val targetCurrency: String,
    val rate: Double,
    val timestamp: Long
)
