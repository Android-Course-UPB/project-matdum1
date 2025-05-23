package com.example.currencyconverter.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.local.entity.Settings
import com.example.currencyconverter.data.repository.SettingsRepository
import com.example.currencyconverter.helpers.PreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor (
    private val settingsRepository: SettingsRepository,
    private val preferencesHelper: PreferencesHelper
): ViewModel() {

    private val _settings = MutableStateFlow(Settings())
    val settings: StateFlow<Settings> get() = _settings

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    val darkMode: StateFlow<Boolean> = preferencesHelper.darkModeFlow

    init {
        refreshSettings()
    }

    fun refreshSettings() {
        viewModelScope.launch {
            _isLoading.value = true
            settingsRepository.getSettings().collect {
                _settings.value = it
                _isLoading.value = false
            }
        }
    }

    fun save(settings: Settings) {
        viewModelScope.launch {
            settingsRepository.save(settings)
            Log.d("SettingsViewModel", "Settings saved: $settings")
        }
    }

    fun updateName(name: String) {
        viewModelScope.launch {
            settingsRepository.updateName(name)
            _settings.value = _settings.value.copy(name = name)
        }
    }

    fun updateDefaultBaseCurrency(currency: String) {
        viewModelScope.launch {
            settingsRepository.updateDefaultBaseCurrency(currency)
            _settings.value = _settings.value.copy(defaultBaseCurrency = currency)
        }
    }

    fun updateDefaultTargetCurrency(currency: String) {
        viewModelScope.launch {
            settingsRepository.updateDefaultTargetCurrency(currency)
            _settings.value = _settings.value.copy(defaultTargetCurrency = currency)

        }
    }

    fun toggleDarkMode(isDarkMode: Boolean) {
        preferencesHelper.toggleDarkMode(isDarkMode)
    }
}