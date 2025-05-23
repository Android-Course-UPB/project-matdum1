package com.example.currencyconverter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.remote.RemoteDataSource
import com.example.currencyconverter.data.repository.ExchangeRateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeRateViewModel @Inject constructor (
    private val exchangeRateRepository: ExchangeRateRepository,
    private val remoteDataSource: RemoteDataSource
) : ViewModel() {

    private val _conversionResult = MutableStateFlow<Double?>(null)
    val conversionResult: StateFlow<Double?> = _conversionResult.asStateFlow()

    fun convertCurrency(amount: Double, from: String, to: String) {
        viewModelScope.launch(Dispatchers.IO) {
            exchangeRateRepository.getExchangeRatesForCurrency(from).collect { exchangeRates ->
                val rate = exchangeRates.find { it.targetCurrency == to }?.rate
                if (rate != null) {
                    _conversionResult.emit(amount * rate)
                } else {
                    fetchExchangeRateFromApi(amount, from, to)
                }
            }
        }
    }

    fun clearConversionResult() {
        viewModelScope.launch {
            _conversionResult.emit(null)
        }
    }

    fun setConversionResult(result: Double) {
        viewModelScope.launch {
            _conversionResult.emit(result)
        }
    }

    private fun fetchExchangeRateFromApi(amount: Double, from: String, to: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = remoteDataSource.fetchExchangeRates(from)
            val rate = response?.conversion_rates?.get(to)
            if (rate != null) {
                _conversionResult.emit(amount * rate)
            } else {
                _conversionResult.emit(null)
            }
        }
    }
}
