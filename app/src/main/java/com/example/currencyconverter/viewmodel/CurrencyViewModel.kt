package com.example.currencyconverter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.local.entity.Currency
import com.example.currencyconverter.data.remote.RemoteDataSource
import com.example.currencyconverter.data.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor (
    private val currencyRepository: CurrencyRepository,
    private val remoteDataSource: RemoteDataSource
) : ViewModel() {

    private val _currencies = MutableStateFlow<List<String>>(emptyList())
    val currencies: StateFlow<List<String>> = _currencies.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRepository.getAllCurrencies()
                .map { list -> list.map { it.code } }
                .collect { currencyList ->
                    _currencies.value = currencyList
                    if (currencyList.isEmpty()) {
                        fetchCurrenciesFromApi()
                    }
                }
        }
    }

    fun fetchCurrenciesFromApi() {
        viewModelScope.launch(Dispatchers.IO) {
            val baseCurrency = "USD"
            val response = remoteDataSource.fetchExchangeRates(baseCurrency)
            response?.let {
                val newCurrencies = it.conversion_rates.keys.map { code -> Currency(code) }
                currencyRepository.insertCurrencies(newCurrencies)
            }
        }
    }
}
