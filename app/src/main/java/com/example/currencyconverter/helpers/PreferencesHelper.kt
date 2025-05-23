package com.example.currencyconverter.helpers

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class PreferencesHelper @Inject constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    val darkModeFlow: StateFlow<Boolean> = callbackFlow {
        trySend(sharedPreferences.getBoolean("dark_mode", false))

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == "dark_mode") {
                trySend(sharedPreferences.getBoolean("dark_mode", false))
            }
        }

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }.stateIn(
        CoroutineScope(Dispatchers.IO),
        SharingStarted.WhileSubscribed(5000),
        sharedPreferences.getBoolean("dark_mode", false)
    )

    fun toggleDarkMode(isDarkMode: Boolean) {
        sharedPreferences.edit {
            putBoolean("dark_mode", isDarkMode)
        }
    }
}